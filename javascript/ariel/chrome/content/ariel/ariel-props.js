var props = {
        root: null,
        rows: null,
        compositeClass: new PageObjectClass()
};

props.init = function() {
    props.root = document.getElementById('props');
    props.rows = document.getElementById('props-rows');
    //var classPanel = document.getElementById('pageobject-class-panel');
    //classPanel.addEventListener("change", props.onPageObjectClassChange, false);
}

props.getCompositeClassType = function() {
    return document.getElementById("composite-class").value;
}

props.setCompositeClassType = function(compClassType) {
    if (props.compositeClassTypes[compClassType]) {
        document.getElementById("composite-class").value = compClassType;
    } else {
        throw Error("Unknown Composite Class Type: " + compClassType);
    }
}

props.compositeClassTypes = {
        ELEMENTGROUP: "ElementGroup",
        PAGEOBJECT: "PageObject"
}

props.properties = {
        CLASS: "class",
        SUPERCLASS: "superclass",
        PACKAGE: "package"
}

props.getPackage = function() {
    return props.getTextbox(BaseCompositeClass.ID_PACKAGE_NAME);
}

props.getClass = function() {
    return props.getTextbox(BaseCompositeClass.ID_CLASS_NAME);
}

props.getSuperClass = function() {
    return props.getTextbox(BaseCompositeClass.ID_SUPERCLASS_NAME);
}

props.getTextbox = function(property) {
    var element = props.getPropertyElement(property);
    return element.value;
}

props.setPackage = function(newPackage) {
    props.setTextbox(newPackage, BaseCompositeClass.ID_PACKAGE_NAME);
}

props.setClass = function(newClass) {
    props.setTextbox(newClass, BaseCompositeClass.ID_CLASS_NAME);
}

props.setSuperClass = function(newSuperClass) {
    props.setTextbox(newSuperClass, BaseCompositeClass.ID_SUPERCLASS_NAME);
}

props.setTextbox = function(newValue, property) {
    var element = props.getPropertyElement(property);
    prevValue = element.value;
    element.value = newValue;
    props.triggerChangeValueMutationEvent(element, prevValue, newValue);
}

props.triggerChangeValueMutationEvent = function(element, prevValue, newValue) {
    var valAttr = element.getAttributeNode ("value");
    var eventObject = document.createEvent('MutationEvent');
    eventObject.initMutationEvent ("change", true, true, valAttr, prevValue, newValue, "value", MutationEvent.MODIFICATION);
    var allowed = element.dispatchEvent(eventObject);
    if(! allowed) {
      throw Error("preventDefault called on event over element with id=" + element.id + " changing value from=" 
              + prevValue + " to=" + newValue);
    }  
}

props.getPropertyElement = function(propertyId) {
    returnElem = document.getElementById(propertyId);
    if(! returnElem) {
        throw Error("Cannot find an element with the id: " + propertyId);
    }
    return returnElem;
}


props.clearPropsBox = function() {
    var compositeClassTypeRow = null;
    while(props.rows.hasChildNodes()) {
        var curChild = props.rows.firstChild;
        if (curChild.getAttribute("id") == "props-composite-classtype-row") {
            compositeClassTypeRow = curChild;
        }
        props.rows.removeChild(curChild);
    }
    
    props.rows.appendChild(compositeClassTypeRow);
}

props.buildPropsBox = function() {
    var selectedItem = document.getElementById("composite-class").selectedItem;
    switch(selectedItem.value) {
        case "PAGEOBJECT":
            props.compositeClass = new PageObjectClass();
            break;
        case "ELEMENTGROUP":
            props.compositeClass = new ElementGroupClass();
            break;
    }
    props.compositeClass.buildPropsBox();
}