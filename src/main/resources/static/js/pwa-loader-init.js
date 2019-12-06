var pwaloaderfirstscreen = document.createElement("div");
pwaloaderfirstscreen.setAttribute("id", "pwaloaderfirstscreen");
pwaloaderfirstscreen.style.cssText = "background-color:white;position:absolute;width:100%;height:100%;top:0;left:0;z-index:2147483640";
document.addEventListener('readystatechange', (event) => {
    if (document.readyState === "interactive") {
        document.body.insertBefore(pwaloaderfirstscreen, document.body.firstChild);
    }
});
//# sourceMappingURL=pwa-loader-init.js.map