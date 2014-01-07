var storage = {
    file : null,
    conn : null
}

storage.init = function() {
    Components.utils.import("resource://gre/modules/Services.jsm");
    Components.utils.import("resource://gre/modules/FileUtils.jsm");

    storage.file = FileUtils
            .getFile("ProfD", [ "ariel.sqlite" ]);

    storage.conn = Services.storage.openDatabase(storage.file);
    if (!storage.conn.connectionReady) {
        var backupfilename = "ariel-" + Date.now() + ".sqlite"
        Services.storage.backupDatabaseFile(storage.file, "backupfilename");
    }

    for ( var curTable in storage.tables) {
        if (!storage.conn.tableExists(curTable)) {
            storage.conn.createTable(curTable, storage.tables[curTable]);
        }
    }
    
    storage.checkInitialDefaults();
    storage.rebuildDefaultsCache();
    storage.checkInitialPackages();
    storage.rebuildPackagesCache();
    storage.checkInitialPageObjects();
    storage.rebuildPageObjectsCache();
    storage.checkInitialElementGroups();
    storage.rebuildElementGroupsCache();
    storage.rebuildBrowserDimsCache();
}

storage.cache = {
    packages : {},
    pageobjects : {},
    elementgroups : {},
    browserdims : [],
    defaults : {}
}

storage.lastUsed = {
        pageobjects: {
            subclass: "",
            superclass: "",
            classpackage: ""
        },
        elementgroups: {
            subclass: "",
            superclass: "",
            classpackage: ""
        }
}

storage.lastUsedTypes = {
        PRIMARY: 1,
        SUPER: 2
};

storage.objects = {};

storage.objects.Package = function(name, last_used_pageobjects, last_used_elementgroups) {
    this.name = name;
    this.last_used_pageobjects = last_used_pageobjects;
    this.last_used_elementgroups = last_used_elementgroups;
}

storage.objects.PageObject = function(name, last_used, last_used_super) {
    this.name = name;
    this.last_used = last_used;
    this.last_used_super = last_used_super;
}

storage.objects.ElementGroup = function(name, locatorbase, last_used, last_used_super) {
    this.name = name;
    this.locatorbase = locatorbase;
    this.last_used = last_used;
    this.last_used_super = last_used_super;
}

storage.objects.Default = function(name, value) {
    this.name = name;
    this.value = value;
}

storage.callbacks = {};

storage.callbacks.handleGenericError = function(aError) {
    throw Error("Db Error: " + aError.message);
}

storage.callbacks.handleGenericCompletion = function(aReason) {
    if (aReason != Components.interfaces.mozIStorageStatementCallback.REASON_FINISHED)
        throw Error("DB Query canceled or aborted!");
}

storage.callbacks.handleGenericResult = function(aResultSet) {} 

storage.callbacks.handleRebuildPackageCacheResult = function(aResultSet) {
    var newPackages = {};
    var row, packageName, lastUsedPageObject, lastUsedElementGroup, latestPageObjectName, latestElementGroupName, latestPageObjectTimestamp = 0, latestElementGroupTimestamp = 0;
    for (row = aResultSet.getNextRow(); row; row = aResultSet.getNextRow()) {
        packageName = row.getResultByName("name");
        lastUsedPageObject = row.getResultByName("last_used_pageobjects");
        lastUsedElementGroup = row.getResultByName("last_used_elementgroups");
        
        newPackages[packageName] = new storage.objects.Package(packageName,
                lastUsedPageObject, lastUsedElementGroup);
        if (lastUsedPageObject > latestPageObjectTimestamp) {
            latestPageObjectTimestamp = lastUsedPageObject;
            latestPageObjectName = packageName;
        }
        if (lastUsedElementGroup > latestElementGroupTimestamp) {
            latestElementGroupTimestamp = lastUsedElementGroup;
            latestElementGroupName = packageName;
        }
    }
    storage.cache.packages = newPackages;
    storage.lastUsed.pageobjects.classpackage = latestPageObjectName;
    storage.lastUsed.elementgroups.classpackage = latestElementGroupName;
    props.buildPropsBox();
}

storage.callbacks.handleRebuildElementGroupsCache = function(aResultSet) {
    var newElementGroups = {};
    var row, elemGrpName, locatorbase, lastUsed, latestName, latestTimestamp = 0;
    var lastUsedSuper, latestSuperName, latestSuperTimestamp = 0;
    for (row = aResultSet.getNextRow(); row; row = aResultSet.getNextRow()) {
        elemGrpName = row.getResultByName("name");
        locatorbase = row.getResultByName("locatorbase");
        lastUsed = row.getResultByName("last_used");
        lastUsedSuper = row.getResultByName("last_used_super");
        newElementGroups[elemGrpName] = new storage.objects.ElementGroup(
                elemGrpName, locatorbase, lastUsed, lastUsedSuper);
        if (lastUsed > latestTimestamp) {
            latestTimestamp = lastUsed;
            latestName = elemGrpName;
        }
        if (lastUsedSuper > latestSuperTimestamp) {
            latestSuperTimestamp = lastUsedSuper;
            latestSuperName = elemGrpName;
        }
    }
    storage.cache.elementgroups = newElementGroups;
    storage.lastUsed.elementgroups.subclass = latestName;
    storage.lastUsed.elementgroups.superclass = latestSuperName;
    props.buildPropsBox();
}

storage.callbacks.handleRebuildPageObjectsCache = function(aResultSet) {
    var newPageObjects = {};
    var row, objectName, lastUsed, latestName, latestTimestamp = 0;
    var lastUsedSuper, latestSuperName, latestSuperTimestamp = 0;
    for (row = aResultSet.getNextRow(); row; row = aResultSet.getNextRow()) {
        objectName = row.getResultByName("name");
        lastUsed = row.getResultByName("last_used");
        lastUsedSuper = row.getResultByName("last_used_super");
        newPageObjects[objectName] = new storage.objects.PageObject(objectName,
                lastUsed, lastUsedSuper);
        if (lastUsed > latestTimestamp) {
            latestTimestamp = lastUsed;
            latestName = objectName;
        }
        if (lastUsedSuper > latestSuperTimestamp) {
            latestSuperTimestamp = lastUsedSuper;
            latestSuperName = objectName;
        }
    }
    storage.cache.pageobjects = newPageObjects;
    storage.lastUsed.pageobjects.subclass = latestName;
    storage.lastUsed.pageobjects.superclass = latestSuperName;
    props.buildPropsBox();
}

storage.callbacks.handleRebuildDefaultsCache = function(aResultSet) {
    var newDefaults = {};
    var row, name, value;
    for (row = aResultSet.getNextRow(); row; row = aResultSet.getNextRow()) {
        name = row.getResultByName("name");
        value = row.getResultByName("value");
        newDefaults[name] = new storage.objects.Default(name,
                value);
    }
    storage.cache.defaults = newDefaults;
    props.buildPropsBox();
    
}

storage.callbacks.handleRebuildBrowserDimsCache = function(aResultSet) {
    var newBrowserDims = [];
    var row, dims;
    for (row = aResultSet.getNextRow(); row; row = aResultSet.getNextRow()) {
        dims = row.getResultByName("dims");
        newBrowserDims.push(dims);
    }
    storage.cache.browserdims = newBrowserDims;
    browser.updateSizeDimsAutocomplete();  
}

storage.callbacks.handleCheckInitialPackages = function(aResultSet) {
    var newDefaults = {};
    var row, count;
    for (row = aResultSet.getNextRow(); row; row = aResultSet.getNextRow()) {
        count = row.getResultByName("thecount");
    }
    if (count == 0) {
        storage.updatePackage("org.prospero", true);
        storage.updatePackage("org.prospero", false);
    }   
}

storage.callbacks.handleCheckInitialPageObjects = function(aResultSet) {
    var newDefaults = {};
    var row, count;
    for (row = aResultSet.getNextRow(); row; row = aResultSet.getNextRow()) {
        count = row.getResultByName("thecount");
    }
    if (count == 0) {
        storage.updatePageObject("BasePageObject", storage.lastUsedTypes.SUPER);
        storage.updatePageObject("NewPageObject", storage.lastUsedTypes.PRIMARY);
    }   
}

storage.callbacks.handleCheckInitialElementGroups = function(aResultSet) {
    var newDefaults = {};
    var row, count;
    for (row = aResultSet.getNextRow(); row; row = aResultSet.getNextRow()) {
        count = row.getResultByName("thecount");
    }
    if (count == 0) {
        storage.updateElementGroup("BaseElementGroup", storage.lastUsedTypes.SUPER, "");
        storage.updateElementGroup("NewElementGroup", storage.lastUsedTypes.PRIMARY, "sidious.");
    }   
}

storage.callbacks.handleCheckInitialDefaults = function(aResultSet) {
    var newDefaults = {};
    var row, count;
    for (row = aResultSet.getNextRow(); row; row = aResultSet.getNextRow()) {
        count = row.getResultByName("thecount");
    }
    if (count == 0) {
        storage.updateDefault("locatorbaseprefix", "sidious");
    }   
}

storage.callbacks.handleUpdatePackageCompletion = function(aResultSet) {
    storage.rebuildPackagesCache();
}

storage.callbacks.handleUpdateElementGroupCompletion = function(aResultSet) {
    storage.rebuildElementGroupsCache();  
}

storage.callbacks.handleUpdatePageObjectCompletion = function(aResultSet) {
    storage.rebuildPageObjectsCache();
}

storage.callbacks.handleUpdateDefaultCompletion = function(aResultSet) {
    storage.rebuildDefaultsCache();
}

storage.callbacks.handleUpdateBrowserDimsCompletion = function(aResultSet) {
    storage.rebuildBrowserDimsCache();
}

storage.rebuildPackagesCache = function() {
    var stmt = storage.conn
            .createStatement("SELECT * FROM packages");
    stmt.executeAsync({
        handleResult : storage.callbacks.handleRebuildPackageCacheResult,
        handleError : storage.callbacks.handleGenericError,
        handleCompletion : storage.callbacks.handleGenericCompletion
    });
}

storage.rebuildElementGroupsCache = function() {
    var stmt = storage.conn
            .createStatement("SELECT * FROM elementgroups");
    stmt.executeAsync({
        handleResult : storage.callbacks.handleRebuildElementGroupsCache,
        handleError : storage.callbacks.handleGenericError,
        handleCompletion : storage.callbacks.handleGenericCompletion
    });
}

storage.rebuildPageObjectsCache = function() {
    var stmt = storage.conn
            .createStatement("SELECT * FROM pageobjects");
    stmt.executeAsync({
        handleResult : storage.callbacks.handleRebuildPageObjectsCache,
        handleError : storage.callbacks.handleGenericError,
        handleCompletion : storage.callbacks.handleGenericCompletion
    });
}

storage.rebuildDefaultsCache = function() {
    var stmt = storage.conn
            .createStatement("SELECT * FROM defaults");
    stmt.executeAsync({
        handleResult : storage.callbacks.handleRebuildDefaultsCache,
        handleError : storage.callbacks.handleGenericError,
        handleCompletion : storage.callbacks.handleGenericCompletion
    });
}

storage.rebuildBrowserDimsCache = function() {
    var stmt = storage.conn
            .createStatement("SELECT * FROM browserdims");
    stmt.executeAsync({
        handleResult : storage.callbacks.handleRebuildBrowserDimsCache,
        handleError : storage.callbacks.handleGenericError,
        handleCompletion : storage.callbacks.handleGenericCompletion
    });
}

storage.updatePackage = function(packageName, usedElementGroup) {
    var stmt = null;
    if (storage.cache.packages[packageName]) {
        var lastUsedToUpdate = usedElementGroup ?  "last_used_elementgroups" : "last_used_pageobjects";
        var sql = "UPDATE packages SET " + lastUsedToUpdate + " = :lastUsed WHERE name = :packageName";
        stmt = storage.conn.createStatement(sql);
        stmt.params.lastUsed = Date.now();
        stmt.params.packageName = packageName;    
    } else {
        stmt = storage.conn.createStatement("INSERT OR IGNORE INTO packages (name, last_used_pageobjects, last_used_elementgroups) "
                + "VALUES (:packageName, :lastUsedPageObjects, :lastUsedElementGroups)");
        stmt.params.packageName = packageName;
        var lastUsed = Date.now();
        if (usedElementGroup) {
            stmt.params.lastUsedPageObjects = 0;
            stmt.params.lastUsedElementGroups = lastUsed;
        } else {
            stmt.params.lastUsedPageObjects = lastUsed;
            stmt.params.lastUsedElementGroups = 0;
        }
    }
    
    stmt.executeAsync({
        handleResult : storage.callbacks.handleGenericResult,
        handleError : storage.callbacks.handleGenericError,
        handleCompletion : storage.callbacks.handleUpdatePackageCompletion
    });
}

storage.updateElementGroup = function(name, lastUsedType, locatorBase) {
    var pageObjectExists = storage.cache.pageobjects[name];
    var elemGroupExists = storage.cache.elementgroups[name]
    if (pageObjectExists && !elemGroupExists) {
        var conf = confirm("An ElementGroup with the name '" + name + "' already exists. Continue to use this name?")
    } else if (!pageObjectExists || conf) {
        var stmt = null;
        locatorBase = locatorBase ? locatorBase : "";
        if (elemGroupExists) {
            // if they are updating the locator base then update it
            if (locatorBase) {
                stmt = storage.conn.createStatement("UPDATE elementgroups SET locatorbase = :locatorBase WHERE name = :elementGroupName");
                stmt.params.locatorBase = locatorBase;
                stmt.params.elementGroupName = name;
            } else if ( lastUsedType == storage.lastUsedTypes.PRIMARY || lastUsedType == storage.lastUsedTypes.SUPER ) {
                var lastUsedToUpdate = lastUsedType == storage.lastUsedTypes.SUPER ?  "last_used_super" : "last_used";
                var sql = "UPDATE elementgroups SET " + lastUsedToUpdate + " = :lastUsed WHERE name = :elementGroupName";
                stmt = storage.conn.createStatement(sql);
                stmt.params.lastUsed = Date.now();
                stmt.params.elementGroupName = name;
                
                // Do not update the ElementGroup
            } else {
                return;
            }     
            // element group does not exist insert it
        } else {
            var newLocatorBase = storage.cache.defaults.locatorbaseprefix.value + ".";
            stmt = storage.conn.createStatement("INSERT OR IGNORE INTO elementgroups (name, locatorbase, last_used, last_used_super) "
                    + "VALUES (:elementGroupName, :locatorBase, :lastUsed, :lastUsedSuper)");
            var lastUsed = Date.now();
            stmt.params.elementGroupName = name;
            stmt.params.locatorBase = newLocatorBase;
            if (lastUsedType == storage.lastUsedTypes.SUPER) {
                stmt.params.lastUsed = 0;
                stmt.params.lastUsedSuper = lastUsed;
            } else if (lastUsedType == storage.lastUsedTypes.PRIMARY)  {
                stmt.params.lastUsed = lastUsed;
                stmt.params.lastUsedSuper = 0;
            } else {
                stmt.params.lastUsed = 0;
                stmt.params.lastUsedSuper = 0;
            }
        }
        
        stmt.executeAsync({
            handleResult : storage.callbacks.handleGenericResult,
            handleError : storage.callbacks.handleGenericError,
            handleCompletion : storage.callbacks.handleUpdateElementGroupCompletion
        });
    }
}

storage.updatePageObject = function(name, lastUsedType) {
    var pageObjectExists = storage.cache.pageobjects[name];
    var elemGroupExists = storage.cache.elementgroups[name]
    if (elemGroupExists && !pageObjectExists) {
        var conf = confirm("An ElementGroup with the name '" + name + "' already exists. Continue to use this name?")
    } else if (!elemGroupExists || conf) {
        var stmt = null;
        if (pageObjectExists &&
                ( lastUsedType == storage.lastUsedTypes.PRIMARY || lastUsedType == storage.lastUsedTypes.SUPER ) ) {
            var lastUsedToUpdate = lastUsedType == storage.lastUsedTypes.SUPER ?  "last_used_super" : "last_used";
            var sql = "UPDATE pageobjects SET " + lastUsedToUpdate + " = :lastUsed WHERE name = :pageObjectName";
            stmt = storage.conn.createStatement(sql);
            stmt.params.pageObjectName = name;
            stmt.params.lastUsed = Date.now();
        } else {
            stmt = storage.conn.createStatement("INSERT OR IGNORE INTO pageobjects (name, last_used, last_used_super) "
                    + "VALUES (:pageObjectName, :lastUsed, :lastUsedSuper)");
            var lastUsed = Date.now();
            stmt.params.pageObjectName = name;
            if (lastUsedType == storage.lastUsedTypes.SUPER) {
                stmt.params.lastUsed = 0;
                stmt.params.lastUsedSuper = lastUsed;
            } else if (lastUsedType == storage.lastUsedTypes.PRIMARY) {
                stmt.params.lastUsed = lastUsed;
                stmt.params.lastUsedSuper = 0;
            } else {
                stmt.params.lastUsed = 0;
                stmt.params.lastUsedSuper = 0;   
            }
        }
        stmt.executeAsync({
            handleResult : storage.callbacks.handleGenericResult,
            handleError : storage.callbacks.handleGenericError,
            handleCompletion : storage.callbacks.handleUpdatePageObjectCompletion
        });
    }
}

storage.updateDefault = function(defaultName, defaultValue) {
    var stmt = null;
    defaultValue = defaultValue ? defaultValue : "";
    
    if (storage.cache.defaults[defaultName]) {
        if (defaultValue || !(storage.cache.defaults[defaultName].value)) {
            stmt = storage.conn.createStatement("UPDATE defaults SET value = :defaultValue WHERE name = :defaultName");
            stmt.params.defaultValue = defaultValue;
            stmt.params.defaultName = defaultName;
        }
    } else {
        stmt = storage.conn.createStatement("INSERT OR IGNORE INTO defaults (name, value) VALUES (:defaultName, :defaultValue)");
        stmt.params.defaultName = defaultName;
        stmt.params.defaultValue = defaultValue;
    }
    
    stmt.executeAsync({
        handleResult : storage.callbacks.handleGenericResult,
        handleError : storage.callbacks.handleGenericError,
        handleCompletion : storage.callbacks.handleUpdateDefaultCompletion
    });
}


storage.updateBrowserDim = function(dims) {
    var stmt = null;
    dims = dims ? dims : "";
    
    if (dims) {
        stmt = storage.conn.createStatement("INSERT OR IGNORE INTO browserdims (dims) VALUES (:dims)");
        stmt.params.dims = dims;
    }

    stmt.executeAsync({
        handleResult : storage.callbacks.handleGenericResult,
        handleError : storage.callbacks.handleGenericError,
        handleCompletion : storage.callbacks.handleUpdateBrowserDimsCompletion
    });
}


//TODO: make this no longer async
storage.checkInitialPackages = function() {
    var stmt = storage.conn.createStatement("SELECT COUNT(*) AS thecount FROM packages");
    stmt.executeAsync({
        handleResult : storage.callbacks.handleCheckInitialPackages,
        handleError : storage.callbacks.handleGenericError,
        handleCompletion : storage.callbacks.handleGenericCompletion
    });
}

//TODO: make this no longer async
storage.checkInitialPageObjects = function() {
    var stmt = storage.conn.createStatement("SELECT COUNT(*) AS thecount FROM pageobjects");
    stmt.executeAsync({
        handleResult : storage.callbacks.handleCheckInitialPageObjects,
        handleError : storage.callbacks.handleGenericError,
        handleCompletion : storage.callbacks.handleGenericCompletion
    });
}

//TODO: make this no longer async
storage.checkInitialElementGroups = function() {
    var stmt = storage.conn.createStatement("SELECT COUNT(*) AS thecount FROM elementgroups");
    stmt.executeAsync({
        handleResult : storage.callbacks.handleCheckInitialElementGroups,
        handleError : storage.callbacks.handleGenericError,
        handleCompletion : storage.callbacks.handleGenericCompletion
    });
}

storage.checkInitialDefaults = function() {
    var stmt = storage.conn.createStatement("SELECT COUNT(*) AS thecount FROM defaults");
    stmt.executeAsync({
        handleResult : storage.callbacks.handleCheckInitialDefaults,
        handleError : storage.callbacks.handleGenericError,
        handleCompletion : storage.callbacks.handleGenericCompletion
    });
}


storage.getPackageNames = function() {
    return Object.keys(storage.cache.packages).sort();
}

storage.getPageObjectNames = function() {
    return Object.keys(storage.cache.pageobjects).sort();
}

storage.getElementGroupNames = function() {
    return Object.keys(storage.cache.elementgroups).sort();
}

storage.getBrowserDims = function() {
    return storage.cache.browserdims.sort();
}

storage.getPackageNamesJSON = function() {
    return JSON.stringify(storage.getPackageNames());
}

storage.getPageObjectNamesJSON = function() {
    return JSON.stringify(storage.getPageObjectNames());
}

storage.getElementGroupNamesJSON = function() {
    return JSON.stringify(storage.getElementGroupNames());
}

storage.getBrowserDimsJSON = function() {
    return JSON.stringify(storage.getBrowserDims());
}

storage.tables = {
    packages : '"name" VARCHAR(128) NOT NULL PRIMARY KEY,\
                         "last_used_pageobjects" UNSIGNED BIG INT NOT NULL,\
                         "last_used_elementgroups" UNSIGNED BIG INT NOT NULL',
    pageobjects : '"name" VARCHAR(128) NOT NULL PRIMARY KEY,\
                         "last_used" UNSIGNED BIG INT NOT NULL,\
                         "last_used_super" UNSIGNED BIG INT NOT NULL',  
    elementgroups : '"name" VARCHAR(128) NOT NULL PRIMARY KEY,\
                         "locatorbase" VARCHAR(128) NOT NULL,\
                         "last_used" UNSIGNED BIG INT NOT NULL,\
                         "last_used_super" UNSIGNED BIG INT NOT NULL',
    defaults : '"name" VARCHAR(128) NOT NULL PRIMARY KEY,\
                         "value" VARCHAR(128) NOT NULL',
    browserdims : '"dims" VARCHAR(50) NOT NULL PRIMARY KEY'                    
}

/**
 * Returns the last entered URL.
 * 
 * Note: this is a synchronous databse operation and should only be used
 * in an init() function to avaoid serious performance degradation 
 */
storage.getLastUrl = function() {
    var stmt = storage.conn.createStatement("SELECT value FROM defaults WHERE name = 'lastUrl'");
    var lastUrl;
    while (stmt.executeStep()) {
        lastUrl = stmt.row.value;
    }
    lastUrl ? lastUrl : "about:blank";
    return lastUrl;
}