const XUL_NS = "http://www.mozilla.org/keymaster/gatekeeper/there.is.only.xul";

function onLoad() {
    // Force support for adjustable opacity in classes
    jQuery.support.opacity = true;
    storage.init();
    browser.init();
    editor.init("java-editor");
    elementbox.init("elements-box");
    
    props.init();
    props.buildPropsBox();
    //window.open("chrome://inspector/content/inspector.xul", "", "chrome");
     //startSQLiteBrowser();
}

function startSQLiteBrowser() {
    var getWorkingDir= Components.classes["@mozilla.org/file/directory_service;1"]
        .getService(Components.interfaces.nsIProperties).get("CurProcD", Components.interfaces.nsIFile);
    var lFile = Components.classes["@mozilla.org/file/local;1"].createInstance(Components.interfaces.nsILocalFile);
    var lPath = getWorkingDir.path + "\\sqlitebrowser_200_b1_win\\SQLite Database Browser 2.0 b1.exe";
    
    lFile.initWithPath(lPath);
    var process = Components.classes["@mozilla.org/process/util;1"].createInstance(Components.interfaces.nsIProcess);
    process.init(lFile);
    
    Components.utils.import("resource://gre/modules/FileUtils.jsm");

    var dbFile = FileUtils.getFile("ProfD", [ "ariel.sqlite" ]);
    var args = [dbFile.path];
    process.run(false, args, args.length);
}