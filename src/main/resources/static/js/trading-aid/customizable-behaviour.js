$(document).ready(function() {
    // make all links with data-confirm prompt the user first.
    $('[data-confirm]').on("click", function (e) {
        e.preventDefault();
        var msg = $(this).data("confirm");
        if(confirm(msg)==true) {
            var url = this.href;
            if(url.length>0) window.location = url;
            return true;
        }
        return false;
    });

    // on certain links save the scroll postion.
    $('.saveScrollPostion').on("click", function (e) {
        e.preventDefault();
        var currentYOffset = window.pageYOffset;  // save current page postion.
        Cookies.set('jumpToScrollPostion', currentYOffset);
        if(!$(this).attr("data-confirm")) {  // if there is no data-confirm on this link then trigger the click. else we have issues.
            var url = this.href;
            window.location = url;
            //$(this).trigger('click');  // continue with click event.
        }
    });

    // check if we should jump to position.
    if(Cookies.get('jumpToScrollPostion') !== "undefined") {
        var jumpTo = Cookies.get('jumpToScrollPostion');
        window.scrollTo(0, jumpTo);
        Cookies.remove('jumpToScrollPostion');  // and delete cookie so we don't jump again.
    }
});