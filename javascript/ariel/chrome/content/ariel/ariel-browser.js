var browser = {
        frame: null,
        url: null,
        get win() { return browser.frame.contentWindow },
        get doc() { return browser.frame.contentDocument },
        get loc() { return browser.win.location },
        
        urlLocator: "browser-url",
        frameLocator: "browser-frame", 
        widthValueLocator: "browser-width",
        heightValueLocator: "browser-height",
        browserDimsLocator: "browser-dims",
        widthBoxLocator: "browser-box-width",
        heightBoxLocator: "browser-box-height",
        userAgentTypeLocator: "browser-useragent-type",
        userAgentTypeDesktop: "desktop",
        userAgentTypeMobile: "mobile",
        mozPrefUserAgentOverride: "general.useragent.override",
        mobileUserAgentString: "Mozilla/5.0 (Linux; U; Android 4.0.3; de-de; Galaxy S II Build/GRJ22) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30",
        defaultUserAgent: null,
        currentUserAgent: null
}

browser.init = function(frameLocator, urlLocator) {
        browser.frame = document.getElementById(browser.frameLocator);
        browser.url = document.getElementById(browser.urlLocator);
        if (browser.frame) {
            browser.frame.addEventListener("DOMContentLoaded", browser.onLoad, false);
            browser.frame.addEventListener("mouseover", browser.onMouseover, false);
            browser.frame.addEventListener("mouseout", browser.onMouseout, false);
            browser.frame.addEventListener("mousemove", browser.onMousemove, false);
            browser.frame.addEventListener("mouseover", browser.onMouseover, false);
            browser.url.value = browser.win.location;
            browser.url.addEventListener("change", browser.onUrlChange, false);
            browser.frame.setAttribute("src", storage.getLastUrl());
        } 
        
}

browser.onLoad = function(event) {
    browser.url.value = browser.win.location;
    var fileref = browser.doc.createElement("link");
    fileref.setAttribute("rel", "stylesheet");
    fileref.setAttribute("type", "text/css");
    fileref.setAttribute("href", "resource://ariel/ariel.css");
    browser.doc.getElementsByTagName("head")[0].appendChild(fileref);
}

browser.onUrlChange = function(event) {
    try {
        var newUrl = browser.url.value; 
        browser.frame.loadURI(newUrl);
        storage.updateDefault("lastUrl", newUrl);
    } catch (e) {
        throw Error("exception on page " + e);
    }
}

browser.onLoadButton = function(event) {
    var userAgentType = document.getElementById(browser.userAgentTypeLocator).value;
    
    if (userAgentType == browser.userAgentTypeMobile) {
        browser.setUserAgent(browser.mobileUserAgentString);
    } else {
        browser.unSetUserAgent();
    }
    
    browser.parseSizeDims();
    browser.onUrlChange(event);
}


browser.parseSizeDims = function() {
    var dimsStr = document.getElementById(browser.browserDimsLocator).value.trim();
    var pattern = /\s*(\d+)\D+(\d+)\s*/;
    var result = dimsStr.match(pattern);
    if (result) {
        var width = result[1];
        var height = result[2];
    
        if (width && height) {
            browser.adjustSizeDimension("width", width, browser.widthBoxLocator);
            browser.adjustSizeDimension("height", height, browser.heightBoxLocator);
            storage.updateBrowserDim(width + "x" + height);
        }
    } else {
        browser.adjustSizeDimension("width", null, browser.widthBoxLocator);
        browser.adjustSizeDimension("height", null, browser.heightBoxLocator);
    }
}

browser.adjustSizeDimension = function(dimName, val, boxLocator) {
    var box = document.getElementById(boxLocator);
    
    if (val && val > 0) {
        box.setAttribute("flex", "0");
        box.setAttribute(dimName, val);
    } else {
        box.setAttribute("flex", "1");
        box.setAttribute(dimName, "0"); 
    }
}

browser.updateSizeDimsAutocomplete = function() {
    var browserDimsField = document.getElementById(browser.browserDimsLocator);
    browserDimsField.setAttribute("autocompletesearchparam", storage.getBrowserDimsJSON());
}

browser.onContextMenu = function(event) {
    BaseElement.buildElement(event.target.parentNode.triggerNode);
}

browser.getUserAgent = function() {
    return navigator.userAgent;
}

browser.getPrefService = function() {
    return Components.classes["@mozilla.org/preferences-service;1"].getService(Components.interfaces.nsIPrefService).getBranch("");
}

browser.setUserAgent = function(userAgentString) {
    var prefs = browser.getPrefService();
    var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);
    str.data = userAgentString;
    prefs.setComplexValue(browser.mozPrefUserAgentOverride, Components.interfaces.nsISupportsString, str);
}

browser.unSetUserAgent = function() {
    if(browser.getPrefService().prefHasUserValue(browser.mozPrefUserAgentOverride)) {
        browser.getPrefService().clearUserPref(browser.mozPrefUserAgentOverride);
    } 
}


browser.onMouseover = function(event) {
    var thisnode = $(event.target);
    
    // Cannot use temporary style class because
    // when building the element's Locator object it will use the style class
    // as the Locator which is transient
    if (type(event.target) != "XULElement") {
        thisnode.data("oldOutline", thisnode.css("outline"))
        var borderCss = "solid red 2px";  
        thisnode.css("outline", borderCss);
    }
    
    var nodeLocator;
    // first time we mouseover this element build its Locator and set it as a
    // property
    if (! thisnode.data('locator')) {
        nodeLocator = new Locator(Locator.getSeleneseLocatorFromNode(thisnode.get(0)));
        thisnode.data('locator', nodeLocator);
    // afterwards use the property
    } else {
        nodeLocator = thisnode.data('locator');
    }
    
    
    // this is the same behavior as in browser.onMouseout
    // but i am doing it twice because i want to handle the case
    // of the onMOuseOver event triggering before onMouseOut
    $("#arielTooltip", browser.doc).remove();
    /*
     * var tooltip = browser.doc.getElementById("arielToolTip"); if (tooltip) {
     * tooltip.parentNode.removeChild(tooltip); }
     */
    
    // create a new paragraph element, give its text the selenese locator from
    // the current node's and add to the body
    $('<p id="arielTooltip" ></p>',browser.doc).text(nodeLocator.seleneseLocator).appendTo($('body',$(browser.doc))).fadeIn('slow');
    // 
    //$("#arielTooltip", browser.doc).addClass("tooltip");
    var tooltipCss = { 
            'tooltopdisplay' : 'none',
            'position':'absolute',
            'border' : '1px solid #333',
            'background-color' : '#161616',
            'border-radius' : '5px',
            'padding' : '10px',
            'color' : '#fff',
            'font-size' : '12px Arial'
    };
    $("#arielTooltip", browser.doc).css(tooltipCss);
}

browser.onMouseout = function(event) {
    // remove highlighting from current element and remove its tooltip.
    if (type(event.target) != "XULElement") {
        $(event.target).css("outline", $(event.target).data("oldOutline"));
    }
    $("#arielTooltip", browser.doc).remove();
    /*
     * var tooltip = browser.doc.getElementById("arielToolTip"); if (tooltip) {
     * tooltip.parentNode.removeChild(tooltip); }
     */
}

browser.onMousemove = function (event) {
    var mousex = event.pageX - 20; // Get X coordinates
    var mousey = event.pageY + 10; // Get Y coordinates
    $('#arielTooltip', browser.doc).css({ top: mousey, left: mousex });
}