var elementbox = {
        box: null
}

elementbox.init = function(locator) {
    elementbox.box = document.getElementById(locator);    
}

elementbox.onItemContextMenu = function(event) {
    var elemName = elementbox.getElementName(event.target.parentNode.triggerNode);
    var jsElemObj = BaseElement.getElementByName(elemName);
    BaseElement.removeElement(jsElemObj);
    elementbox.rebuild();
}

elementbox.onNewElementGroupContextMenu = function(event) {
    var newElemGrp = new ElementGroup();
    try {
        BaseElement.addElement(newElemGrp);
        elementbox.rebuild();
    } catch (error) {
        throw Error(error.message)
    }    
}

elementbox.onNewElementGroupSequenceContextMenu = function(event) {
    var newSeq = new ElementGroupSequence();
    try {
        BaseElement.addElement(newSeq);
        elementbox.rebuild();
    } catch (error) {
        throw Error(error.message)
    }
}

elementbox.getElementItemNode = function(node) {
    if (node.tagName && node.tagName.toLowerCase() == "richlistitem") {
        return node;
    } else {
        return elementbox.getElementItemNode(node.parentNode);
    }
}

elementbox.getElementName = function(node) {
    var elemItemNode = elementbox.getElementItemNode(node);
    return elemItemNode.getAttribute("data-elementname");
}

elementbox.getUpdateType = function(row) {
    var parentRowIdx = elementbox.view.getParentIndex(row);
    return parentRowIdx == -1 ? 0 : row - parentRowIdx;
}

elementbox.rebuild = function() {
    while(elementbox.box.hasChildNodes()){
        elementbox.box.removeChild(elementbox.box.firstChild);
    }
    $(".elementtint > *", browser.doc).unwrap();
    var elements = BaseElement.getOrderedElements();
    for (curElem in elements) {
        curElemBoxItem = elements[curElem].createElementBoxItem();
        elementbox.box.appendChild(curElemBoxItem);
        elements[curElem].tintElement();
    }
}