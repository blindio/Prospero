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
function CheckBox(seleneseLocator, name, locatorConstString) {
    BaseElement.apply(this, [seleneseLocator, name, locatorConstString]);
}
CheckBox.prototype = Object.create(BaseElement.prototype)
CheckBox.prototype.constructor = CheckBox;
CheckBox.elementNameCount = 1;
CheckBox.htmlElementTypes = [];
CheckBox.htmlInputElementTypes = ["checkbox"];
BaseElement.registerSubClass(CheckBox);

function Image(seleneseLocator, name, locatorConstString) {
    BaseElement.apply(this, [seleneseLocator, name, locatorConstString]);
}
Image.prototype = Object.create(BaseElement.prototype)
Image.prototype.constructor = Image;
Image.elementNameCount = 1;
Image.htmlElementTypes = ["HTMLImageElement"];
Image.htmlInputElementTypes = ["image"];
BaseElement.registerSubClass(Image);

function PageLink(seleneseLocator, name, locatorConstString, returnPageClass) {
    BaseElement.apply(this, [seleneseLocator, name, locatorConstString, returnPageClass]);
}
PageLink.prototype = Object.create(BaseElement.prototype)
PageLink.prototype.constructor = PageLink;
PageLink.elementNameCount = 1;
PageLink.htmlElementTypes = ["HTMLLinkElement", "HTMLAnchorElement"];
PageLink.htmlInputElementTypes = ["submit", "button"];
BaseElement.registerSubClass(PageLink);

PageLink.prototype.createElementBoxItem = function() {
    var elementItem = BaseElement.prototype.createElementBoxItem.apply(this);
    var elementRows = elementItem.getElementsByTagName("rows").item(0);
    var retClassRow = this.buildJavaReturnClassItemRow("PageObject Class :",
            storage.getPageObjectNamesJSON());
    elementRows.appendChild(retClassRow);    
    
    return elementItem;
}

PageLink.prototype.updateJavaReturnClass = function(value) {
    if (value != this.javaReturnClass) {
        this.javaReturnClass = value;
        storage.updatePageObject(this.javaReturnClass);
    }
}

function RadioButton(seleneseLocator, name, locatorConstString) {
    BaseElement.apply(this, [seleneseLocator, name, locatorConstString]);
}
RadioButton.prototype = Object.create(BaseElement.prototype)
RadioButton.prototype.constructor = RadioButton;
RadioButton.elementNameCount = 1;
RadioButton.htmlElementTypes = [];
RadioButton.htmlInputElementTypes = ["radio"];
BaseElement.registerSubClass(RadioButton);

function SelectField(seleneseLocator, name, locatorConstString) {
    BaseElement.apply(this, [seleneseLocator, name, locatorConstString]);
}
SelectField.prototype = Object.create(BaseElement.prototype)
SelectField.prototype.constructor = SelectField;
SelectField.elementNameCount = 1;
SelectField.htmlElementTypes = ["HTMLSelectElement"];
SelectField.htmlInputElementTypes = [];
BaseElement.registerSubClass(SelectField);

function TextField(seleneseLocator, name, locatorConstString) {
    BaseElement.apply(this, [seleneseLocator, name, locatorConstString]);
}
TextField.prototype = Object.create(BaseElement.prototype)
TextField.prototype.constructor = TextField;
TextField.elementNameCount = 1;
TextField.htmlElementTypes = [];
TextField.htmlInputElementTypes = ["text", "password"];
BaseElement.registerSubClass(TextField);

function TextLabel(seleneseLocator, name, locatorConstString) {
    BaseElement.apply(this, [seleneseLocator, name, locatorConstString]);
}
TextLabel.prototype = Object.create(BaseElement.prototype)
TextLabel.prototype.constructor = TextLabel;
TextLabel.elementNameCount = 1;
TextLabel.htmlElementTypes = [];
TextLabel.htmlInputElementTypes = [];
BaseElement.registerSubClass(TextLabel);
BaseElement.defaultElement = TextLabel;

function YUIAutoComplete(seleneseLocator, name, locatorConstString) {
    BaseElement.apply(this, [seleneseLocator, name, locatorConstString]);
}
YUIAutoComplete.prototype = Object.create(BaseElement.prototype)
YUIAutoComplete.prototype.constructor = YUIAutoComplete;
YUIAutoComplete.elementNameCount = 1;
YUIAutoComplete.htmlElementTypes = [];
YUIAutoComplete.htmlInputElementTypes = [];
BaseElement.registerSubClass(YUIAutoComplete);
YUIAutoComplete.__inputSuffix = "Input";
YUIAutoComplete.__testInputSuffix = function(str) {
    if (str.lastIndexOf(YUIAutoComplete.__inputSuffix) + YUIAutoComplete.__inputSuffix.length == str.length) {
        return true;
    }
    return false;
}
YUIAutoComplete.__buildStringWithInputSuffix = function(str) {
    if (YUIAutoComplete.__testInputSuffix(str)) {
        return str;
    }
    return str + YUIAutoComplete.__inputSuffix;
}
YUIAutoComplete.prototype.updateElementClass = function(newElemClassName) {
    if (BaseElement.subClasses[newElemClassName]) {
        BaseElement.removeElement(this);
        var newElem = new BaseElement.subClasses[newElemClassName](YUIAutoComplete.__buildStringWithInputSuffix(this.seleneseLocator),
                this.name, this.locatorConstString);
    } else {
        throw Error("Element Class does not exist " + newElemClassName);
    }
}
Object.defineProperty(YUIAutoComplete.prototype,
        "seleneseLocator", {
    get:    function() {
        if (this.__locator) {
            var selLoc =  this.__locator.seleneseLocator;
            if (selLoc) {
                if (YUIAutoComplete.__testInputSuffix(selLoc)) {
                    return selLoc.substr(0, selLoc.indexOf("Input"));
                } else {
                    return selLoc;
                }
            }   
        }
        return undefined;
    },
    set:    function(selLoc) {
        if (selLoc != this.seleneseLocator) {
            var oldLoc = this.seleneseLocator;
            var inputSuffix = "Input"
            BaseElement.removeElement(this);
            this.__locator = this.buildLocator(YUIAutoComplete.__buildStringWithInputSuffix(selLoc));
            try {
                BaseElement.addElement(this);
            } catch (err) {
                alert(err);
                this.__locator = this.buildLocator(YUIAutoComplete.__buildStringWithInputSuffix(oldLoc));
                BaseElement.addElement(this);
            }
        }
    }
});


function YUIMenuExpand(seleneseLocator, name, locatorConstString) {
    BaseElement.apply(this, [seleneseLocator, name, locatorConstString]);
}
YUIMenuExpand.prototype = Object.create(BaseElement.prototype)
YUIMenuExpand.prototype.constructor = YUIMenuExpand;
YUIMenuExpand.elementNameCount = 1;
YUIMenuExpand.htmlElementTypes = [];
YUIMenuExpand.htmlInputElementTypes = [];
BaseElement.registerSubClass(YUIMenuExpand);
