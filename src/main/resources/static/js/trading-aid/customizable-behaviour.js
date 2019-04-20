// import * as Cookies from "../utils/js.cookie";

$(document).ready(function() {

    // on certain links save the scroll postion.
    $('.saveScrollPosition').on("click", function (e) {
        e.preventDefault();
        let currentYOffset = window.pageYOffset;  // save current page postion.
        Cookies.set('jumpToScrollPostion', currentYOffset);

        let raceTable = document.getElementById("raceTable").style.display;
        let raceTableMin = document.getElementById("raceTableMin").style.display;

        Cookies.set('raceTableStatus', raceTable);
        Cookies.set('raceTableMinStatus', raceTableMin);

        if(!$(this).attr("data-confirm")) {  // if there is no data-confirm on this link then trigger the click. else we have issues.
            let url = this.href;
            window.location = url;
            //$(this).trigger('click');  // continue with click event.
            location.reload(true);
        }
    });

    // check if we should jump to position.
    if(Cookies.get('jumpToScrollPostion') !== "undefined") {

        let raceTable = document.getElementById("raceTable");
        let raceTableMin = document.getElementById("raceTableMin");

        raceTable.style.display = Cookies.get('raceTableStatus');
        raceTableMin.style.display = Cookies.get('raceTableMinStatus');

        let jumpTo = Cookies.get('jumpToScrollPostion');
        window.scrollTo(0, jumpTo);
        Cookies.remove('jumpToScrollPostion');  // and delete cookie so we don't jump again.
    }
});