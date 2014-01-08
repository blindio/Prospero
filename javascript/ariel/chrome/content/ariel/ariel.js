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
