var editor = {
        frame: null,
        get win() { return editor.frame.contentWindow },
        get doc() { return editor.frame.contentDocument },
        get body() { return editor.frame.contentDocument.body },
        get code() { return editor.doc.getElementById("java-editor-code") }
}

editor.init = function(frameLocator) {
        editor.frame = document.getElementById(frameLocator);
        if (editor.frame) {
            editor.frame.makeEditable("text", false);
            // turn off spellcheck
            editor.frame.getEditor(editor.win).setSpellcheckUserOverride(false);
        }  
}

editor.newln = "\n";
editor.tab = "\t";


editor.escaped = {
        opengeneric: "&lt;",
        closegeneric: "&gt;"
};

editor.unescaped = {
        opengeneric: "<",
        closegeneric: ">"
};


editor.buildClassInitString = function() {
    var className = props.getClass();
    var superClassName = props.getSuperClass();

    var classInitStr = "public class " + className;
    classInitStr += " extends " + superClassName;
    classInitStr += " {" + editor.newln + editor.newln;
    return classInitStr;
}
 
editor.buildLocConstString = function() {
    var elements = BaseElement.getOrderedElements();
    var locConstStr = "";
    for (curElemIdx in elements) {
        var curLocConstStr = editor.tab + elements[curElemIdx].genJavaLocatorConstString() + editor.newln;
        locConstStr += curLocConstStr;
    }
    locConstStr += editor.newln;
    return locConstStr;
}

editor.buildDeclarationString = function() {
    var elements = BaseElement.getOrderedElements();
    var declarationStr = "";
    for (curElemIdx in elements) {
        var curDeclarationStr = editor.tab + elements[curElemIdx].genJavaDeclarationString() + editor.newln;
        declarationStr += curDeclarationStr;
    }
    declarationStr += editor.newln;
    return declarationStr;
}


editor.buildConstructorString = function() {
    var constructorStr = editor.tab + "/**" + editor.newln + editor.tab + " * Constructor" + editor.newln + editor.tab + " */" + editor.newln;
    constructorStr += editor.tab + "public " + props.getClass() + "( String url ) {" + editor.newln;
    constructorStr += editor.tab + editor.tab + "super( url );" + editor.newln;
    var elements = BaseElement.getOrderedElements();
    for (curElemIdx in elements) {
        var curConstStr = editor.tab + editor.tab + elements[curElemIdx].genJavaInitializationString() + editor.newln;
        constructorStr += curConstStr;
    }
    constructorStr += editor.tab + "}" + editor.newln + editor.newln;
    constructorStr += editor.tab + "public " + props.getClass() + "( ) {" + editor.newln;
    constructorStr += editor.tab + editor.tab + "this( null );" + editor.newln;
    constructorStr += editor.tab + "}" + editor.newln + editor.newln;
    return constructorStr;
}

editor.buildGetterString = function() {
    var constructorStr = "";
    var elements = BaseElement.getOrderedElements();
    for (curElemIdx in elements) {
        var curConstStr = elements[curElemIdx].genJavaGetterString();
        constructorStr += curConstStr;
    }
    return constructorStr;
}

editor.buildJava = function() {
    
    var startCode = "<pre id=\"java-editor-code\">";
    var stopCode = "</pre>";
    var packageStr = "package " + props.getPackage() + ";" + editor.newln + editor.newln;
    var importStr = "import org.prospero.core.elements.*;" + editor.newln
                        + "import org.prospero.core.elementgroup.*;" + editor.newln + editor.newln;
    
    var javaString = startCode + packageStr + importStr + editor.buildClassInitString() + editor.buildLocConstString() 
    javaString +=  editor.buildDeclarationString() + editor.buildConstructorString() + editor.buildGetterString();
    javaString += "}" + editor.newln + stopCode;
    
    editor.body.innerHTML = javaString;
}

editor.unescapeJava = function(java) {
    var unescapedOG = java.replace(new RegExp(editor.escaped.opengeneric, "g"), editor.unescaped.opengeneric);
    var unescapedCG = unescapedOG.replace(new RegExp(editor.escaped.closegeneric, "g"), editor.unescaped.closegeneric);
    return unescapedCG;
}

editor.escapeJava = function(java) {
    var escapedOG = java.replace(new RegExp(editor.unescaped.opengeneric, "g"), editor.escaped.opengeneric);
    var escapedCG = escapedOG.replace(new RegExp(editor.unescaped.closegeneric, "g"), editor.escaped.closegeneric);
    return escapedCG;
}

editor.exportJava = function() {
    var java = editor.doc.getElementById("java-editor-code").innerHTML;
    var unescapedJava = editor.unescapeJava(java);
    
    var filePickerInterface = Components.interfaces.nsIFilePicker;
    var fileSelector = Components.classes["@mozilla.org/filepicker;1"]
           .createInstance(filePickerInterface);
    fileSelector.init(window, "Select a destination", filePickerInterface.modeSave);
    fileSelector.appendFilter("Java Files", "*.java");
    fileSelector.appendFilters(filePickerInterface.filterAll);
    fileSelector.defaultString = props.getClass();
    fileSelector.defaultExtension = "java";
    var rv = fileSelector.show( );    
    if (( rv == filePickerInterface.returnOK ) || ( rv == filePickerInterface.returnReplace )) {
      var selectedFile = fileSelector.file;
      var outFileStream =
         Components.classes["@mozilla.org/network/file-output-stream;1"]
              .createInstance(Components.interfaces.nsIFileOutputStream);
      outFileStream.init(selectedFile, 0x02 | 0x08 | 0x20, 0664, 0);
                  // write, create, truncate
      outFileStream.write(unescapedJava, unescapedJava.toString(  ).length);
      outFileStream.close(  );
     }
}

editor.importJava = function (  ) {
    try { // try block
        var theEditor = document.getElementById("memoEditor");

        var filePickerInterface = Components.interfaces.nsIFilePicker;
        var fileSelector = Components.classes["@mozilla.org/filepicker;1"]
        .createInstance(filePickerInterface);
        fileSelector.init(window, "Select java source file", filePickerInterface.modeOpen);
        fileSelector.appendFilter("Java Files", "*.java");
        fileSelector.appendFilters(filePickerInterface.filterAll);
        fileSelector.defaultString = props.getClass();
        fileSelector.defaultExtension = "java";
        if (fileSelector.show(  ) == filePickerInterface.returnOK) {
            Components.utils.import("resource://gre/modules/NetUtil.jsm");

            retVal = NetUtil.asyncFetch(fileSelector.file, function(inputStream, status) {  
                if (!Components.isSuccessCode(status)) {  
                    // Handle error!
                    return;  
                }  

                // The file data is contained within inputStream.
                // You can read it into a string with
                var java = NetUtil.readInputStreamToString(inputStream, inputStream.available());
                var escapedJava = editor.escapeJava(java);
                var startCode = "<pre id=\"java-editor-code\">";
                var stopCode = "</pre>";
                var javaHtml = startCode + escapedJava + stopCode;
                editor.body.innerHTML = javaHtml;
                editor.rebuildFromJava();
            })
        }
    } // try block
    catch (e) {
        throw Error("Read Note from File exception: " + e);
    }
}

       

editor.rebuildFromJava = function() {
    var java = editor.doc.getElementById("java-editor-code").innerHTML;
    if (java) {
        
        BaseElement.cleanElements();
        var codeLines = java.split(editor.newln);

        var locatorConstants = {};
        var elementData = {};

        var packageDeclarationRegex = /^\s*package\s+(\S+)\s*;/;
        var classDefRegex = /^\s*public.+class\s+(\w+)\s*/;
        var classDefSuperClassRegex = /\s+extends\s+(\w+)\s*/;
        var locatorConstantDefRegex = /^\s*public\s+static\s+final\s+String\s+(\w+)\s*\=\s*(\S+)\s*;/
        var defaultElementInstatiationRegex = /^\s*(\w+)\s*\=\s*new\s+(\w+)\s*\(\s*(\S+)\s*\)\s*;/;
        var genericElementInstatiationRegex = /^\s*(\w+)\s*\=\s*new\s+(\w+)\s*&lt;\s*(\w+)\s*&gt;\s*\(\s*(\S+)\s*,\s*(\w+)\.class\s*\)\s*;/;
        var elementGroupSequenceExtendedInstatiationRegex = /^\s*(\w+)\s*\=\s*new\s+(ElementGroupSequence)\s*&lt;\s*(\w+)\s*&gt;\s*\(\s*(\S+)\s*,\s*(\w+)\.class\s*\,\s*(\d+)\s*\)\s*;/;

        for (curLineIdx in codeLines) {
            var curLine = codeLines[curLineIdx];
            var result = curLine.match(locatorConstantDefRegex);
            if (result != null) {
                var locatorConstStr = result[1], selLoc = result[2];
                locatorConstants[locatorConstStr] = selLoc;
                continue;
            }

            result = curLine.match(defaultElementInstatiationRegex);
            if (result != null) {
                var elemName = result[1];
                elementData[elemName] = result.slice(2);
                continue;
            }

            result = curLine.match(genericElementInstatiationRegex);
            if (result != null) {
                var elemName = result[1];
                elementData[elemName] = [ result[2], result[4], result[3] ];
                continue;
            }

            result = curLine.match(packageDeclarationRegex);
            if (result != null) {
                var packageName = result[1];
                props.setPackage(packageName);
                continue;
            }

            result = curLine.match(classDefRegex);
            if (result != null) {
                var javaClassName = result[1];
                
                if(storage.cache.elementgroups[javaClassName]) {
                    props.setCompositeClassType("ELEMENTGROUP");
                } else {
                    props.setCompositeClassType("PAGEOBJECT"); 
                }
                
                props.buildPropsBox();
                
                props.setClass(javaClassName);
                result = curLine.match(classDefSuperClassRegex);
                if (result != null) {
                    var javaSuperClass = result[1];
                    props.setSuperClass(javaSuperClass);
                } else {
                    props.setSuperClass("");
                }

                continue;
            }

            // no match, continue to next line
        }
        
        for(var elemName in elementData) {
            var subClassName = elementData[elemName][0];
            var locatorConstStr = elementData[elemName][1];
            var javaReturnClass = elementData[elemName][2];
            var subClassConstructor = BaseElement.getElementClass(subClassName);
            // if the String for subClassName is not registered, first check to see if it is
            // 'ElementGroup' or 'ElementGroupSequence'
            if (! subClassConstructor) {
                subClassConstructor = ElementAggregate.getElementClass(subClassName);
                
                // otherwise assume it is the name of an ElememtGroup
                if(! subClassConstructor) {
                    subClassConstructor = ElementAggregate.getElementClass("ElementGroup");
                    javaReturnClass = subClassName;
                }
            }
            
            var seleneseLocator = locatorConstStr;
            if(locatorConstants[locatorConstStr]) {
                seleneseLocator = locatorConstants[locatorConstStr];
                seleneseLocator = seleneseLocator.replace(/"/g,"");
            }

            var newElem = new subClassConstructor(seleneseLocator, elemName, locatorConstStr, javaReturnClass);
        }
        

        elementbox.rebuild();
    }
}

editor.clearAll = function() {
    var startCode = "<pre id=\"java-editor-code\">";
    var stopCode = "</pre>";
    var javaHtml = startCode + stopCode;
    editor.body.innerHTML = javaHtml;
    BaseElement.cleanElements();
    elementbox.rebuild();
}