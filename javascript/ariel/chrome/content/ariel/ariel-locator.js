function Locator(val) {
    this.seleneseLocator = val; 
}

Locator.convertXPathToSeleneseLocator = function(xpath) {
    var selLocator;
    if (xpath) {
        var regex = /^\/\/\*\[@id\='(.+)'\]$/;
        var result = xpath.match(regex)
        if ( result != null ) {
            selLocator = result[1]; 
        } else if (xpath.substr(0,2) == "//") {
            selLocator = xpath;
        } else {
            selLocator = "xpath=" + xpath;
        }
    }
    return selLocator;    
} 

Locator.convertSeleneseLocatorToXPath = function(seleneseLocator) {
    var xpathLocator = undefined;

    if (seleneseLocator) {
        if (seleneseLocator.substr(0,6) == "xpath=") {
            xpathLocator = seleneseLocator.substr(6);
        } else if (seleneseLocator.substr(0,2) == "//") {
            xpathLocator = seleneseLocator;
        } else {
            xpathLocator = "//*[@id='" + seleneseLocator + "']";
        }
    }
    return xpathLocator;
}

Locator.getXPathFromNode = function(node) {
    var result = "";
    var stop = false;

    var parent = browser.doc;
    while (node && node != parent && !stop) {
        var str = "";
        var position = Locator.getNodePosition(node);
        switch (node.nodeType) {
            case Node.DOCUMENT_NODE:
                break;
            case Node.ATTRIBUTE_NODE:
                str = "@" + node.name;
                break;
            case Node.COMMENT_NODE:
                str = "comment()";
                break;
            case Node.TEXT_NODE:
                str = "text()";
                break;
            case Node.ELEMENT_NODE:

                var name = Locator.getTagName(node);

                if(node.id && node.id != "") {
                    str = "//*[@id='" + node.id + "']";
                    position = null;
                    stop = true;
                } else {
                    str = name;
                }

                break;
        }

        result = str + (position ? "[" + position + "]" : "") + (result? "/": "") + result;

        if(node instanceof Attr) node = node.ownerElement;
        else node = node.parentNode;

    }

    return result;
}

Locator.getSeleneseLocatorFromNode = function(node) {
    var xpath = Locator.getXPathFromNode(node);
    return Locator.convertXPathToSeleneseLocator(xpath);
}

Locator.getNodePosition = function(node) {
    if (!node.parentNode)
        return null;

    var siblings = node.parentNode.childNodes;
    var count = 0;
    var position;

    for (var i = 0; i < siblings.length; i++) {
        var object = siblings[i];
        if(object.nodeType == node.nodeType && object.nodeName == node.nodeName) {
            count++;
            if(object == node) position = count;
        }
    }

    if (count > 1)
        return position;
    else
        return null;
}

Locator.isHtmlDocument = function(doc) {
    return doc.contentType === 'text/html';
}

Locator.getTagName = function(node) {
    var ns = node.namespaceURI;
    var prefix = node.lookupPrefix(ns);

    // if an element has a namespace it needs a prefix
    if(ns != null && !prefix) {
        prefix = Locator.getPrefixFromNS(ns);
    }

    var name = node.localName;
    if (Locator.isHtmlDocument(node.ownerDocument)) {
        // lower case only for HTML document
        return name.toLowerCase();
    } else {
        return (prefix? prefix + ':': '') + name;
    }
}

Locator.getPrefixFromNS = function(ns) {
    return ns.replace(/.*[^\w](\w+)[^\w]*$/, "$1");
}

Locator.prototype = {
        get seleneseLocator() {
            return this.__seleneseLocator
        },
        set seleneseLocator(selLoc) {
            this.__seleneseLocator = selLoc;
            this.__jquery = undefined;
            this.__xpath = undefined;
        },
        get xpathLocator() {
            if (! this.__xpath) {
                this.__xpath = Locator.convertSeleneseLocatorToXPath(this.seleneseLocator);
            }
            return this.__xpath;
        },
        set xpathLocator(xpathLocator) {
            this.seleneseLocator = Locator.convertXPathToSeleneseLocator(xpathLocator);
        },
        get jquery() {
            if (! this.__jquery) {
                this.__jquery = this.generateJQuery();
            }
            return this.__jquery;
        }
};

Locator.prototype.generateJQuery = function() {
    var xpathResult = browser.doc.evaluate(this.xpathLocator, browser.doc, null, XPathResult.UNORDERED_NODE_SNAPSHOT_TYPE, null );
    var nodes = $();

    for (var curNodeIdx = 0; curNodeIdx < xpathResult.snapshotLength; curNodeIdx++) {
        currNode = xpathResult.snapshotItem(curNodeIdx);
        nodes = nodes.add(currNode);
    }
    return nodes;
};

function AggregateLocator(val) {
    Locator.apply(this, [ val ]);
};
AggregateLocator.prototype = Object.create(Locator.prototype);
AggregateLocator.prototype.constructor = AggregateLocator;
Object.defineProperty(AggregateLocator.prototype,
        "xpathLocator", {
    get:    function() {
        if (! this.__xpath) {
            this.__xpath = AggregateLocator.convertSeleneseLocatorToXPath(this.seleneseLocator);
        }
        return this.__xpath;
    },
    set: function(xpathLocator) {
        this.seleneseLocator = AggregateLocator.convertXPathToSeleneseLocator(xpathLocator);
    }
});

AggregateLocator.convertXPathToSeleneseLocator = function(xpath) {
    var selLocator;
    if (xpath) {
        var regex = /^\/\/\*\[\s*starts-with\s*\(\s*@id\s*\,\s*'(.+)(\.0)?'\s*\]$/;
        var result = xpath.match(regex)
        if ( result != null ) {
            selLocator = result[1]; 
        }
    }
    return selLocator;    
} 

AggregateLocator.convertSeleneseLocatorToXPath = function(seleneseLocator) {
    var xpathLocator = undefined;
    if (seleneseLocator) {
        xpathLocator = "//*[starts-with(@id,'" + seleneseLocator + "')]";
    }
    return xpathLocator;
}