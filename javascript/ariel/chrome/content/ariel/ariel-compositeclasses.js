#-------------------------------------------------------------------------------
# Copyright 2014 S. Thorson Little
# 
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
# 
#   http://www.apache.org/licenses/LICENSE-2.0
# 
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#-------------------------------------------------------------------------------
function BaseCompositeClass() { };

BaseCompositeClass.prototype = {
        get autocomplete() { return undefined; },
        get className() { return undefined; },
        get superClassName() { return undefined; },
        get classPackage() { return undefined; }
}

BaseCompositeClass.ID_CLASS_ROW = "props-class-row";
BaseCompositeClass.ID_CLASS_NAME = "props-class-name";
BaseCompositeClass.ID_SUPERCLASS_ROW = "props-superclass-row";
BaseCompositeClass.ID_SUPERCLASS_NAME = "props-superclass-name";
BaseCompositeClass.ID_PACKAGE_ROW = "props-package-row";
BaseCompositeClass.ID_PACKAGE_NAME = "props-package-name";


BaseCompositeClass.prototype.buildPropsBox = function() {
    props.clearPropsBox();

    var classRow = document.createElement("row");
    classRow.setAttribute("id", BaseCompositeClass.ID_CLASS_ROW);
    var classLabel = document.createElement("label");
    classLabel.setAttribute("value", "Class Name:");
    var classTextbox = document.createElement("textbox");
    classTextbox.setAttribute("id", BaseCompositeClass.ID_CLASS_NAME);
    classTextbox.setAttribute("type","autocomplete");
    classTextbox.setAttribute("autocompletesearch","basic-autocomplete");
    classTextbox.setAttribute("autocompletesearchparam", this.autocomplete);
    classTextbox.setAttribute("value", this.className);
    classTextbox.addEventListener("change", this.onClassChange, false);
    
    classRow.appendChild(classLabel);
    classRow.appendChild(classTextbox);
    props.rows.appendChild(classRow);
    
    var superClassRow = document.createElement("row");
    superClassRow.setAttribute("id", BaseCompositeClass.ID_SUPERCLASS_ROW );
    var superClassLabel = document.createElement("label");
    superClassLabel.setAttribute("value", "Super Class:");
    var superClassTextbox = document.createElement("textbox");
    superClassTextbox.setAttribute("id", BaseCompositeClass.ID_SUPERCLASS_NAME);
    superClassTextbox.setAttribute("type","autocomplete");
    superClassTextbox.setAttribute("autocompletesearch","basic-autocomplete");
    superClassTextbox.setAttribute("autocompletesearchparam", this.autocomplete);
    superClassTextbox.setAttribute("value", this.superClassName);
    superClassTextbox.addEventListener("change", this.onSuperClassChange, false);
    
    superClassRow.appendChild(superClassLabel);
    superClassRow.appendChild(superClassTextbox);
    props.rows.appendChild(superClassRow);
    
    var packageRow = document.createElement("row");
    packageRow.setAttribute("id", BaseCompositeClass.ID_PACKAGE_ROW);
    var packageLabel = document.createElement("label");
    packageLabel.setAttribute("value", "Package:");
    var packageTextbox = document.createElement("textbox");
    packageTextbox.setAttribute("id", BaseCompositeClass.ID_PACKAGE_NAME);
    packageTextbox.setAttribute("type","autocomplete");
    packageTextbox.setAttribute("autocompletesearch","basic-autocomplete");
    packageTextbox.setAttribute("autocompletesearchparam",storage.getPackageNamesJSON());
    packageTextbox.setAttribute("value", this.classPackage );
    packageTextbox.addEventListener("change", this.onPackageChange, false);
    
    packageRow.appendChild(packageLabel);
    packageRow.appendChild(packageTextbox);
    props.rows.appendChild(packageRow);
    
}


function PageObjectClass() {
    BaseCompositeClass.apply(this, [ ]);
};
PageObjectClass.prototype = Object.create(BaseCompositeClass.prototype);
PageObjectClass.prototype.constructor = PageObjectClass;
Object.defineProperties(PageObjectClass.prototype, {
    "autocomplete": { get: function() { return storage.getPageObjectNamesJSON(); } },
    "className": { get: function() { return storage.lastUsed.pageobjects.subclass; } },
    "superClassName": { get: function() { return storage.lastUsed.pageobjects.superclass; } },
    "classPackage": { get: function() { return storage.lastUsed.pageobjects.classpackage; } }   
});

PageObjectClass.prototype.onClassChange = function() {
    try {
        var newClass = document.getElementById(BaseCompositeClass.ID_CLASS_NAME).value; 
        storage.updatePageObject(newClass, storage.lastUsedTypes.PRIMARY );
    } catch (e) {
        throw Error("exception on page " + e);
    }
}

PageObjectClass.prototype.onSuperClassChange = function() {
    try {
        var newClass = document.getElementById(BaseCompositeClass.ID_SUPERCLASS_NAME).value; 
        storage.updatePageObject(newClass, storage.lastUsedTypes.SUPER );
    } catch (e) {
        throw Error("exception on page " + e);
    }
}

PageObjectClass.prototype.onPackageChange = function() {
    try {
        var newPackage = document.getElementById(BaseCompositeClass.ID_PACKAGE_NAME).value; 
        storage.updatePackage(newPackage);
    } catch (e) {
        throw Error("exception on page " + e);
    }
}


function ElementGroupClass() {
    BaseCompositeClass.apply(this, [ ]);
};

ElementGroupClass.ID_BASELOC_ROW = "props-elemgrp-baseloc-row";
ElementGroupClass.ID_BASELOC_NAME = "props-elemgrp-baseloc-name";

ElementGroupClass.prototype = Object.create(BaseCompositeClass.prototype);
ElementGroupClass.prototype.constructor = ElementGroupClass;
Object.defineProperties(ElementGroupClass.prototype, {
    "autocomplete": { get: function() { return storage.getElementGroupNamesJSON(); } },
    "className": { get: function() { return storage.lastUsed.elementgroups.subclass; } },
    "superClassName": { get: function() { return storage.lastUsed.elementgroups.superclass; } },
    "classPackage": { get: function() { return storage.lastUsed.elementgroups.classpackage; } }  
});

ElementGroupClass.prototype.onClassChange = function() {
    try {
        var newClass = document.getElementById(BaseCompositeClass.ID_CLASS_NAME).value; 
        storage.updateElementGroup(newClass, storage.lastUsedTypes.PRIMARY, false);
    } catch (e) {
        throw Error("exception on page " + e);
    }
}

ElementGroupClass.prototype.onSuperClassChange = function() {
    try {
        var newClass = document.getElementById(BaseCompositeClass.ID_SUPERCLASS_NAME).value; 
        storage.updateElementGroup(newClass, storage.lastUsedTypes.SUPER, false);
    } catch (e) {
        throw Error("exception on page " + e);
    }
}

ElementGroupClass.prototype.onPackageChange = function() {
    try {
        var newPackage = document.getElementById(BaseCompositeClass.ID_PACKAGE_NAME).value; 
        storage.updatePackage(newPackage, true);
    } catch (e) {
        throw Error("exception on page " + e);
    }
}

ElementGroupClass.prototype.onBaseLocatorChange = function() {
    try {
        var elemGroupClass = document.getElementById(BaseCompositeClass.ID_CLASS_NAME).value;
        var newLocatorBase = document.getElementById(ElementGroupClass.ID_BASELOC_NAME).value; 
        storage.updateElementGroup(elemGroupClass, storage.lastUsedTypes.PRIMARY, newLocatorBase);
    } catch (e) {
        throw Error("exception on page " + e);
    }
}

ElementGroupClass.prototype.buildPropsBox = function() {
    BaseCompositeClass.prototype.buildPropsBox.apply(this);
    
    var baseLocatorRow = document.createElement("row");
    baseLocatorRow.setAttribute("id", ElementGroupClass.ID_BASELOC_ROW);
    var baseLocatorLabel = document.createElement("label");
    baseLocatorLabel.setAttribute("value", "Locator Base:");
    var baseLocatorTextbox = document.createElement("textbox");
    baseLocatorTextbox.setAttribute("id", ElementGroupClass.ID_BASELOC_NAME);
    baseLocatorTextbox.setAttribute("value", storage.cache.elementgroups[ this.className ].locatorbase );
    baseLocatorTextbox.addEventListener("change", this.onBaseLocatorChange, false);
    
    baseLocatorRow.appendChild(baseLocatorLabel);
    baseLocatorRow.appendChild(baseLocatorTextbox);;
    props.rows.appendChild(baseLocatorRow);
}
