// import * as Cookies from "../utils/js.cookie";

$(document).ready(function() {

    // on certain links save the scroll postion.
    $('.saveScrollPosition').on("click", function (e) {
        e.preventDefault();
        let currentYOffset = window.pageYOffset;  // save current page postion.
        Cookies.set('jumpToScrollPosition', currentYOffset);

        let raceTable = document.getElementById("raceTable").style.display;
        let raceTableMin = document.getElementById("raceTableMin").style.display;

        Cookies.set('raceTableStatus', raceTable);
        Cookies.set('raceTableMinStatus', raceTableMin);

        let body = document.getElementsByTagName('body')[0];
        Cookies.set('bodyClass', body.className);

        if(!$(this).attr("data-confirm")) {  // if there is no data-confirm on this link then trigger the click. else we have issues.
            let url = this.href;
            window.location = url;
            //$(this).trigger('click');  // continue with click event.
            location.reload(true);
        }
    });

    // check if is saved body class to define expanded or collapsed navigation menu
    if (Cookies.get('bodyClass').toString() !== "undefined") {

        let body = document.getElementsByTagName('body')[0];
        let bodyClass = Cookies.get('bodyClass');
        $(body).removeClass("nav-md");
        $(body).addClass(bodyClass);
        Cookies.remove('bodyClass');
    }

    // check if raceTable should be hidden or shown
    if (Cookies.get('raceTableStatus') !== "undefined") {

        let raceTable = document.getElementById("raceTable");
        raceTable.style.display = Cookies.get('raceTableStatus');
        Cookies.remove('raceTableStatus');
    }

    // check if compact raceTable should be hidden or shown
    if (Cookies.get('raceTableMinStatus') !== "undefined") {

        let raceTableMin = document.getElementById("raceTableMin");
        raceTableMin.style.display = Cookies.get('raceTableMinStatus');
        Cookies.remove('raceTableMinStatus');
    }

    // check if we should jump to position.
    if(Cookies.get('jumpToScrollPosition') !== "undefined") {


        let jumpTo = Cookies.get('jumpToScrollPosition');
        window.scrollTo(0, jumpTo);
        Cookies.remove('jumpToScrollPosition');  // and delete cookie so we don't jump again.
    }
});