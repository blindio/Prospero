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
function BaseElement(seleneseLocator, name, locatorConstString, javaReturnClass) {
    this.name = name ? name : this.genNewName();
    this.locatorConstString = locatorConstString ? locatorConstString : this.genLocatorConstString();
    this.javaReturnClass = javaReturnClass;
    this.seleneseLocator = seleneseLocator;
};

BaseElement.eventHandlers = {};

BaseElement.defaultElement = undefined;

BaseElement.eventHandlers.updateName = function(event) {
    var newElementName = event.target.value;
    var origElementName = elementbox.getElementName(event.target);
    var elementObj = BaseElement.getElementByName(origElementName);
    elementObj.name = newElementName;
    elementbox.rebuild();
}

BaseElement.eventHandlers.updateAutoName = function(event) {
    var origElementName = elementbox.getElementName(event.target.parentNode.triggerNode);
    var elementObj = BaseElement.getElementByName(origElementName);
    var newElementName = elementObj.genNewName();
    elementObj.name = newElementName;
    elementbox.rebuild();
}

BaseElement.eventHandlers.updateElementClass = function(event) {
    var newElementClass = event.target.label;
    var elementName = elementbox.getElementName(event.target);
    var elementObj = BaseElement.getElementByName(elementName);
    elementObj.updateElementClass(newElementClass);
    elementbox.rebuild();      
}

BaseElement.eventHandlers.updateLocator = function(event) {
    var newSeleneseLocator = event.target.value;
    var elementName = elementbox.getElementName(event.target);
    var elementObj = BaseElement.getElementByName(elementName);
    elementObj.seleneseLocator = newSeleneseLocator;
    elementbox.rebuild();    
}

BaseElement.eventHandlers.updateLocatorConstStr = function(event) {
    var newLocatorConstStr = event.target.value;
    var elementName = elementbox.getElementName(event.target);
    var elementObj = BaseElement.getElementByName(elementName);
    elementObj.locatorConstString = newLocatorConstStr;
    elementbox.rebuild();    
}

BaseElement.eventHandlers.autoUpdateLocatorConstStr = function(event) {
    var elementName = elementbox.getElementName(event.target.parentNode.triggerNode);
    var elementObj = BaseElement.getElementByName(elementName);
    var newLocatorConstStr = elementObj.genLocatorConstString();
    elementObj.locatorConstString = newLocatorConstStr;
    elementbox.rebuild(); 
}

BaseElement.eventHandlers.updateJavaReturnClass = function(event) {
    var newReturnClass = event.target.value;
    var elementName = elementbox.getElementName(event.target);
    var elementObj = BaseElement.getElementByName(elementName);
    elementObj.updateJavaReturnClass(newReturnClass);
    elementbox.rebuild();    
}


BaseElement.subClasses = {};

BaseElement.elements = {};
BaseElement.locatorToElementMap = {};

BaseElement.addElement = function(element) {    
    if (! element.seleneseLocator) {
        BaseElement.elements[element.name] = element;
    } else {
        if (element.node) {
            // TODO: need to keep a ref to the node
            if(element.node.className.substr(0,4) == "tint" && type(element.node) == "HTMLSpanElement" && element.node.childElementCount) {
                element = element.node.firstElementChild;
            }
        }
        
        var err = BaseElement.findDuplicateElement(element);
        if (err) {
            throw new Error(err);
        } else {
            if (element.seleneseLocator) {
                BaseElement.locatorToElementMap[element.seleneseLocator] = element;
            }
            BaseElement.elements[element.name] = element;
        }
    }
}

BaseElement.findDuplicateElement = function(element) {
    var err = "";
    if (element.seleneseLocator && BaseElement.locatorToElementMap[element.seleneseLocator]) {
        err += "BaseElement.elements already contains an Element with the locator: " + element.seleneseLocator + "; ";            
    }
    
    if (BaseElement.elements[element.name]) {
        err += "BaseElement.elements already contains an Element named: " + element.name + "; ";
    }
    return err;
}

BaseElement.removeElement = function(element) {
    if (element.seleneseLocator) {
        delete BaseElement.locatorToElementMap[element.seleneseLocator];
    }
    delete BaseElement.elements[element.name];
}

BaseElement.cleanElements = function() {
    for(curElem in BaseElement.elements) {
        if (BaseElement.elements.hasOwnProperty(curElem)) {
            BaseElement.removeElement(BaseElement.elements[curElem]);
        }
    }
}

BaseElement.getElement = function(element) {
    return BaseElement.getElementByName(element.name);
}

BaseElement.getElementByName = function(elemName) {
    return BaseElement.elements[elemName];
}

BaseElement.getElementByLocator = function(elemLocator) {
    return BaseElement.locatorToElementMap[elemLocator];
}

BaseElement.getOrderedElements = function() {
    var returnArray = [];
    for(curElem in BaseElement.elements) {
        if (BaseElement.elements.hasOwnProperty(curElem)) {
            returnArray.push(BaseElement.elements[curElem]);
        }
    }
    returnArray.sort(nameorder);
    return returnArray;
}

BaseElement.registerSubClass = function(subClass) {
    if (BaseElement.prototype.isPrototypeOf(subClass.prototype)) {
        if (! BaseElement.subClasses[subClass.getName()]) {
            BaseElement.subClasses[subClass.getName()] = subClass;    
        } else {
            throw Error("Element Subclass named " + subClass.getName() + " already exists");
        }
    } else {
        throw Error("BaseElement.prototype.isPrototypeOf(" + subClass.getName() + ".prototype) Failed");
    }
}

BaseElement.getElementClass = function(subClassName) {
    if (BaseElement.subClasses[subClassName]) {
        return BaseElement.subClasses[subClassName]
    } else {
        return undefined;
    }
}

BaseElement.getElementClasses = function() {
    var returnArray = [];
    for(curSubClass in BaseElement.subClasses)
        if (BaseElement.subClasses.hasOwnProperty(curSubClass))
            returnArray.push(BaseElement.subClasses[curSubClass]);
    returnArray.sort(nameorder);
    return returnArray;
}

BaseElement.elementNameCount = function() { abstractMethod() },

BaseElement.buildElement = function(node) {
    var elemConstructor = BaseElement.getElementClassForNode(node);
    var newElem = new elemConstructor(Locator.getSeleneseLocatorFromNode(node));
    //BaseElement.addElement(newElem);
    elementbox.rebuild();
    
}

BaseElement.getElementClassForNode = function(node) {
    if (type(node) == "HTMLInputElement") {
        for(var curElemClassName in BaseElement.subClasses)
            for (var curInputType in BaseElement.subClasses[curElemClassName].htmlInputElementTypes) {
                if (node.type == BaseElement.subClasses[curElemClassName].htmlInputElementTypes[curInputType]) {
                    return BaseElement.subClasses[curElemClassName];
                }
            }
    } else {
        for(var curElemClassName in BaseElement.subClasses)     
            for (var curHtmlType in BaseElement.subClasses[curElemClassName].htmlElementTypes)
                if (type(node) == BaseElement.subClasses[curElemClassName].htmlElementTypes[curHtmlType])
                    return BaseElement.subClasses[curElemClassName];
    }
    // default is TextLabel
    return BaseElement.defaultElement;
}

BaseElement.prototype = {        
        get nodes() {
            if (this.__locator) {
                return this.__locator.jquery;
            } else {
                return undefined;
            }
        },
        get node() {
            if (this.nodes) {
                return this.nodes.get(0);
            } else {
                return undefined;
            }    
        },
        get xpathLocator() {
            if (this.__locator) {
                return this.__locator.xpathLocator;
            } else {
                return undefined;
            }
        },
        get seleneseLocator() {
            if (this.__locator) {
                return this.__locator.seleneseLocator;
            } else {
                return undefined;
            }
        },
        set seleneseLocator(selLoc) {
            if (selLoc != this.seleneseLocator) {
                var oldLoc = this.seleneseLocator;
                BaseElement.removeElement(this);
                this.__locator = this.buildLocator(selLoc);
                try {
                    BaseElement.addElement(this);
                } catch (err) {
                    alert(err);
                    this.__locator = this.buildLocator(oldLoc);
                    BaseElement.addElement(this);
                }
            }
        }, 
        get name() {
            return this.__name;
        },
        set name(newName) {
            if (newName != this.name) {
                var oldName = this.name;
                var oldConstStr = this.locatorConstString;
                BaseElement.removeElement(this);
                this.__name = newName;
                try {
                    BaseElement.addElement(this);
                } catch (err) {
                    alert(err);
                    this.__name = oldName;
                    BaseElement.addElement(this);
                }    
            }
        }
}

BaseElement.prototype.isNodeAggregate = function() {
    return false;
}

BaseElement.prototype.buildLocator = function(seleneseLocator) {
    return new Locator(seleneseLocator);
}

BaseElement.prototype.countElements = function() {
    if (this.nodes) {
        return this.nodes.size();
    }
    return 0;
}

BaseElement.prototype.getName= function() {
    return this.name;
}

BaseElement.prototype.updateElementClass = function(newElemClassName) {
    if (BaseElement.subClasses[newElemClassName]) {
        BaseElement.removeElement(this);
        var newElem = new BaseElement.subClasses[newElemClassName](this.seleneseLocator, this.name, this.locatorConstString);
    } else {
        throw Error("Element Class does not exist " + newElemClassName);
    }
}

BaseElement.prototype.updateJavaReturnClass = function(value) {
    // do nothing
}

BaseElement.prototype.genJavaDeclarationString = function() {
    var javaDeclStr =  "private " + this.constructor.getName();
    if (this.javaReturnClass) {
        javaDeclStr += editor.escaped.opengeneric + this.javaReturnClass + editor.escaped.closegeneric;
    }
    javaDeclStr += " " + this.name + ";";
    return javaDeclStr;
}

BaseElement.prototype.genJavaInitializationString = function() {
    var javaInitStr = this.name + " = new " + this.constructor.getName()
    if (this.javaReturnClass) {
        javaInitStr += editor.escaped.opengeneric + this.javaReturnClass + editor.escaped.closegeneric;
    }
    javaInitStr += "(" + this.locatorConstString;
    if (this.javaReturnClass) {
        javaInitStr += ", " + this.javaReturnClass + ".class"
    }
    javaInitStr += ");";
    return javaInitStr;
}

BaseElement.prototype.genJavaGetterString = function() {
    var elemNameFirstLetter = this.name.substr(0,1);
    var elemNameRemaining = this.name.substr(1);
    var getterString = editor.tab + "public " + this.constructor.getName();
    if (this.javaReturnClass) {
        getterString += editor.escaped.opengeneric + this.javaReturnClass + editor.escaped.closegeneric;
    }
    getterString += " get" + elemNameFirstLetter.toUpperCase() + elemNameRemaining + "( ) {" + editor.newln;
    getterString += editor.tab + editor.tab + "return " + this.name + ";" + editor.newln + editor.tab + "}" + editor.newln + editor.newln;
    return getterString;
}

BaseElement.prototype.genNewName = function() {
    return "new" + this.newElementIndex() + this.constructor.getName();
}

BaseElement.prototype.genLocatorConstString = function() {
    return "LOC_" + this.constructor.getName().toUpperCase() + "_" + this.name.toUpperCase();
}

BaseElement.prototype.newElementIndex = function() {
    return this.constructor.elementNameCount++;
}

BaseElement.prototype.genJavaLocatorConstString = function() {
    return "public static final String " + this.locatorConstString + " = \"" + this.seleneseLocator + "\";"
}

BaseElement.prototype.getElementClasses = function() {
    return BaseElement.getElementClasses();
}

BaseElement.prototype.tintElement = function() {
    var jquery = this.nodes;
    if (jquery) {
        if (jquery.size() == 1) {
            jquery.wrap('<span class="elementtint greentint"></span>'); 
        } else if (jquery.size() == 1) {
            jquery.wrap('<span class="elementtint yellowtint"></span>');
        }
    }
}

BaseElement.prototype.createElementBoxItem = function() {
    var elementItem = document.createElement("richlistitem");
    elementItem.setAttribute("container", "true");
    elementItem.setAttribute("data-elementname", this.name);
    
    var elementGroupBox = document.createElement("groupbox");
    elementGroupBox.setAttribute("flex", "1");
    elementItem.appendChild(elementGroupBox);
    
    var elementGrid = document.createElement("grid");
    elementGrid.setAttribute("flex", "1");
    elementGroupBox.appendChild(elementGrid);
    
    elementColumns = document.createElement("columns");
    elementGrid.appendChild(elementColumns);
    
    elementRows = document.createElement("rows");
    elementRows.setAttribute("context", "elementBoxItemContextMenu"); 
    elementGrid.appendChild(elementRows);
    
    elementFieldColumn = document.createElement("column");
    elementColumns.appendChild(elementFieldColumn);
    
    elementValueColumn = document.createElement("column");
    elementValueColumn.setAttribute("flex", "1");
    elementColumns.appendChild(elementValueColumn);
    

    var nameRow = document.createElement("row");
    var nameField = document.createElement("label");
    nameField.setAttribute("value", "Name :");
    
    var nameValue = document.createElement("textbox");
    nameValue.setAttribute("value", this.name);
    nameValue.setAttribute("context", "autoUpdateNameContextMenu");
    nameValue.addEventListener("change", BaseElement.eventHandlers.updateName, false);
    nameRow.appendChild(nameField);
    nameRow.appendChild(nameValue);
    elementRows.appendChild(nameRow);
    
    var classRow = document.createElement("row"); 
    var classField = document.createElement("label");
    classField.setAttribute("value", "Element Class :");
    var classValue = document.createElement("menulist");
    var classValuePopup = document.createElement("menupopup");
    classValue.appendChild(classValuePopup);
    
    var subClasses = this.getElementClasses();
    for (curSubClass in subClasses) {
        curMenuItem = document.createElement("menuitem");
        curSubClassName = subClasses[curSubClass].getName();
        curMenuItem.setAttribute("label",curSubClassName);
        if (curSubClassName.toUpperCase() == type(this).toUpperCase()) {
            curMenuItem.setAttribute("selected", "true");
        }
        classValuePopup.appendChild(curMenuItem);
    }
    classValue.addEventListener("command", BaseElement.eventHandlers.updateElementClass, false);
    classRow.appendChild(classField);
    classRow.appendChild(classValue);
    elementRows.appendChild(classRow);
      
    var locRow = document.createElement("row");
    var locField = document.createElement("label");
    locField.setAttribute("value", "Locator :");
    var locValue = document.createElement("textbox");
    locValue.setAttribute("value", this.seleneseLocator);
    locValue.addEventListener("change", BaseElement.eventHandlers.updateLocator, false);
    locRow.appendChild(locField);
    locRow.appendChild(locValue);
    elementRows.appendChild(locRow);        

    var locConstStrRow = document.createElement("row");
    var locConstStrField = document.createElement("label");
    locConstStrField.setAttribute("value", "Locator Constant :");
    var locConstStrValue = document.createElement("textbox");
    locConstStrValue.setAttribute("value", this.locatorConstString);
    locConstStrValue.setAttribute("context", "autoUpdateLocatorConstStrContextMenu");
    locConstStrValue.addEventListener("change", BaseElement.eventHandlers.updateLocatorConstStr, false);
    locConstStrRow.appendChild(locConstStrField);
    locConstStrRow.appendChild(locConstStrValue);
    elementRows.appendChild(locConstStrRow); 
    
    elementGroupBox.setAttribute("class", this.getElementBoxItemTintClass());

    return elementItem;
}

BaseElement.prototype.buildJavaReturnClassItemRow = function(fieldLabel, autoCompleteArray) {
    var retClassRow = document.createElement("row");
    var retClassField = document.createElement("label");
    retClassField.setAttribute("value", fieldLabel);

    var retClassValue = document.createElement("textbox");
    retClassValue.setAttribute("type","autocomplete");
    retClassValue.setAttribute("autocompletesearch","basic-autocomplete");
    retClassValue.setAttribute("autocompletesearchparam", autoCompleteArray);
    retClassValue.addEventListener("change", BaseElement.eventHandlers.updateJavaReturnClass, false);
    if (this.javaReturnClass) {
        retClassValue.setAttribute("value", this.javaReturnClass);
    }

    retClassRow.appendChild(retClassField);
    retClassRow.appendChild(retClassValue);
    
    return retClassRow;
}

BaseElement.prototype.getElementBoxItemTintClass = function() {
    var browserElementCount = this.countElements();
    var tintClass;
    switch(browserElementCount) {
        case 0:
            tintClass = "elementboxtint redtint";
            break;
        case 1:
            tintClass = "elementboxtint greentint";
            break;
        default:
            tintClass = "elementboxtint yellowtint";
            break;        
    }
    return tintClass;
}
