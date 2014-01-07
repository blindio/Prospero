function ElementAggregate(seleneseLocator, name, locatorConstString, returnElementGroupClass) {
    BaseElement.apply(this, [seleneseLocator, name, locatorConstString, returnElementGroupClass]);
}
ElementAggregate.prototype = Object.create(BaseElement.prototype);
ElementAggregate.prototype.constructor = ElementAggregate;
ElementAggregate.subClasses = {};

ElementAggregate.prototype.updateElementClass = function(newElemClassName) {
    if (ElementAggregate.subClasses[newElemClassName]) {
        //var selenseLocator = Locator.getSeleneseLocatorFromNode(this.node);
        BaseElement.removeElement(this);
        var newElem = new ElementAggregate.subClasses[newElemClassName](this.seleneseLocator, this.name, this.locatorConstString);
    } else {
        throw Error("Element Class does not exist " + newElemClassName);
    }
}

ElementAggregate.prototype.getElementClasses = function() {
    return ElementAggregate.getElementClasses();
}

ElementAggregate.prototype.isNodeAggregate = function() {
    return true;
}

ElementAggregate.prototype.buildLocator = function(seleneseLocator) {
    return new AggregateLocator(seleneseLocator);
}

ElementAggregate.prototype.getElementBoxItemTintClass = function() {
    var browserElementCount = this.countElements();
    var tintClass;
    switch(browserElementCount) {
        case undefined:  
        case 0:
            tintClass = "elementboxtint redtint";
            break;
        default:
            tintClass = "elementboxtint lightgreentint";
            break;        
    }
    return tintClass;
}

ElementAggregate.prototype.createElementBoxItem = function() {
    var elementItem = BaseElement.prototype.createElementBoxItem.apply(this);
    var elementRows = elementItem.getElementsByTagName("rows").item(0);
    var retClassRow = this.buildJavaReturnClassItemRow("ElementGroup Class :",
            storage.getElementGroupNamesJSON());
    elementRows.appendChild(retClassRow);    
    
    return elementItem;
}

ElementAggregate.prototype.updateJavaReturnClass = function(value) {
    if (value != this.javaReturnClass) {
        this.javaReturnClass = value;
        storage.updateElementGroup(this.javaReturnClass);
    }
}

ElementAggregate.prototype.tintElement = function() {
    var nodes = this.nodes;
    if (nodes) {
        nodes.wrap('<span class="elementtint lightgreentint"></span>');
    }
}

ElementAggregate.registerSubClass = function(subClass) {
    if (ElementAggregate.prototype.isPrototypeOf(subClass.prototype)) {
        if (! ElementAggregate.subClasses[subClass.getName()]) {
            ElementAggregate.subClasses[subClass.getName()] = subClass;    
        } else {
            throw Error("Element Subclass named " + subClass.getName() + " already exists");
        }
    } else {
        throw Error("ElementAggregate.prototype.isPrototypeOf(" + subClass.getName() + ".prototype) Failed");
    }
}

ElementAggregate.getElementClass = function(subClassName) {
    if (ElementAggregate.subClasses[subClassName]) {
        return ElementAggregate.subClasses[subClassName]
    } else {
        return undefined;
    }
}

ElementAggregate.getElementClasses = function() {
    var returnArray = [];
    for(curSubClass in ElementAggregate.subClasses)
        if (ElementAggregate.subClasses.hasOwnProperty(curSubClass))
            returnArray.push(ElementAggregate.subClasses[curSubClass]);
    returnArray.sort(nameorder);
    return returnArray;
}

function ElementGroup(seleneseLocator, name, locatorConstString, returnElementGroupClass) {
    ElementAggregate.apply(this, [seleneseLocator, name, locatorConstString, returnElementGroupClass]);
}
ElementGroup.prototype = Object.create(ElementAggregate.prototype)
ElementGroup.prototype.constructor = ElementGroup;
ElementGroup.elementNameCount = 1;
ElementGroup.htmlElementTypes = [];
ElementGroup.htmlInputElementTypes = [];
ElementAggregate.registerSubClass(ElementGroup);

ElementGroup.prototype.genJavaDeclarationString = function() {
    var javaDeclStr =  "private ";
    if (this.javaReturnClass) {
        javaDeclStr += this.javaReturnClass;
    } else {
        javaDeclStr += this.constructor.getName();
    }
    javaDeclStr += " " + this.name + ";";
    return javaDeclStr;
}

ElementGroup.prototype.genJavaInitializationString = function() {
    var javaInitStr = this.name + " = new ";
    if (this.javaReturnClass) {
        javaInitStr += this.javaReturnClass;
    } else {
        javaInitStr += this.constructor.getName();
    }
    javaInitStr += "(" + this.locatorConstString + ");";
    return javaInitStr;
}

ElementGroup.prototype.genJavaGetterString = function() {
    var elemNameFirstLetter = this.name.substr(0,1);
    var elemNameRemaining = this.name.substr(1);
    var getterString = editor.tab + "public "; 
    if (this.javaReturnClass) {
        getterString += this.javaReturnClass;
    } else {
        getterString += this.constructor.getName();
    }
    getterString += " get" + elemNameFirstLetter.toUpperCase() + elemNameRemaining + "( ) {" + editor.newln;
    getterString += editor.tab + editor.tab + "return " + this.name + ";" + editor.newln + editor.tab + "}" + editor.newln + editor.newln;
    return getterString;
}

function ElementGroupSequence(seleneseLocator, name, locatorConstString, returnElementGroupClass) {
    ElementAggregate.apply(this, [seleneseLocator, name, locatorConstString, returnElementGroupClass]);
}
ElementGroupSequence.prototype = Object.create(ElementAggregate.prototype)
ElementGroupSequence.prototype.constructor = ElementGroupSequence;
ElementGroupSequence.elementNameCount = 1;
ElementGroupSequence.htmlElementTypes = [];
ElementGroupSequence.htmlInputElementTypes = [];
ElementAggregate.registerSubClass(ElementGroupSequence);
Object.defineProperty(ElementGroupSequence.prototype,
        "seleneseLocator", {
    get:    function() {
        if (this.__locator) {
            var selLoc =  this.__locator.seleneseLocator;
            if (selLoc) {
                return selLoc.substr(0, selLoc.indexOf(".0"));
            }   
        }
        return undefined;
    },
    set:    function(selLoc) {
        if (selLoc != this.seleneseLocator) {
            var oldLoc = this.seleneseLocator;
            BaseElement.removeElement(this);
            this.__locator = this.buildLocator(selLoc + ".0");
            try {
                BaseElement.addElement(this);
            } catch (err) {
                alert(err);
                this.__locator = this.buildLocator(oldLoc + ".0");
                BaseElement.addElement(this);
            }
        }
    }
});