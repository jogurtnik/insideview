$(document).ready(function() {

    // get default table with detailed race data
    let raceTable = document.getElementById('raceTable');

    // get table bodies of default detailed race data table
    let raceTbody = raceTable.getElementsByTagName('tbody');

    // iterate through odd indexes of table bodies to set conditional formatting for cells
    for (let tBodyIndex=1, len=raceTbody.length; tBodyIndex<len; tBodyIndex+=2) {

        let raceRows = raceTbody[tBodyIndex].getElementsByTagName('tr');
        for (let rowIndex=0, len=raceRows.length; rowIndex<len; rowIndex++) {

            let cells = raceRows[rowIndex].getElementsByTagName('td');
            for (let cellIndex = 0, len=cells.length; cellIndex<len; cellIndex++) {

                if (cellIndex > 1 && cellIndex%2 === 0 && cellIndex < 16) {

                    let cellValue = parseFloat(cells[cellIndex].innerHTML);

                    if (cellValue > 5) {
                        cells[cellIndex].style.backgroundColor = '#99ccff';

                    } else if (cellValue >= 2 && cellValue <= 5) {
                        cells[cellIndex].style.backgroundColor = '#ccffcc';

                    } else if (cellValue > 0 && cellValue < 2.5) {
                        cells[cellIndex].style.backgroundColor = '#ffff99';

                    } else if (cellValue < 0 && cellValue > -2.5) {
                        cells[cellIndex].style.backgroundColor = '#ffcc99';

                    } else if (cellValue <= -2.5) {
                        cells[cellIndex].style.backgroundColor = '#ff99cc';

                    }

                } else if (cellIndex >= 16 && cellIndex < 19) {

                    let cellValue = parseFloat(cells[cellIndex].innerHTML);

                    if (cellValue > 5) {
                        cells[cellIndex].style.backgroundColor = '#99ccff';

                    } else if (cellValue >= 2.5 && cellValue <= 5) {
                        cells[cellIndex].style.backgroundColor = '#ccffcc';

                    } else if (cellValue > 0 && cellValue < 2.5) {
                        cells[cellIndex].style.backgroundColor = '#ffff99';

                    } else if (cellValue < 0 && cellValue > -2.5) {
                        cells[cellIndex].style.backgroundColor = '#ffcc99';

                    } else if (cellValue <= -2.5) {
                        cells[cellIndex].style.backgroundColor = '#ff99cc';

                    }
                } else if (cellIndex === 19) {

                    let cellValue = cells[cellIndex].innerHTML;

                    if (cellValue == 'Won') {
                        cells[cellIndex].style.backgroundColor = '#3ED578';
                        cells[0].style.backgroundColor = '#3ED578';
                    } else if (cellValue == 'Placed') {
                        cells[cellIndex].style.backgroundColor = '#f2dac1';
                        cells[0].style.backgroundColor = '#f2dac1';
                    }
                } else if (cellIndex === 20) {

                    let cellValue = parseFloat(cells[cellIndex].innerHTML);

                    if (cellValue < 0) {
                        cells[cellIndex].style.color = '#ff0033'
                    }
                }
            }
        }
    }

    // get table with minimum race data appended with jockey and trainer data
    let raceTableMin = document.getElementById('raceTableMin');

    // get table bodies of minimum race data table
    let raceTbodyMin = raceTableMin.getElementsByTagName('tbody');

    // iterate through odd indexes of table bodies and set conditional formatting for cells
    for (let tBodyMinIndex=1, len=raceTbodyMin.length; tBodyMinIndex<len; tBodyMinIndex+=2) {

        let raceRowsMin = raceTbodyMin[tBodyMinIndex].getElementsByTagName('tr');
        for (let rowIndex=0, len=raceRowsMin.length; rowIndex<len; rowIndex++) {

            let cells = raceRowsMin[rowIndex].getElementsByTagName('td');
            for (let cellIndex = 0, len=cells.length; cellIndex<len; cellIndex++) {

                if (cellIndex > 1 && cellIndex%2 === 0 && cellIndex < 9) {

                    let cellValue = parseFloat(cells[cellIndex].innerHTML);

                    if (cellValue > 5) {
                        cells[cellIndex].style.backgroundColor = '#99ccff';

                    } else if (cellValue >= 2 && cellValue <= 5) {
                        cells[cellIndex].style.backgroundColor = '#ccffcc';

                    } else if (cellValue > 0 && cellValue < 2.5) {
                        cells[cellIndex].style.backgroundColor = '#ffff99';

                    } else if (cellValue < 0 && cellValue > -2.5) {
                        cells[cellIndex].style.backgroundColor = '#ffcc99';

                    } else if (cellValue <= -2.5) {
                        cells[cellIndex].style.backgroundColor = '#ff99cc';

                    }

                } else if (cellIndex === 7) {

                    let cellValue = parseFloat(cells[cellIndex].innerHTML);

                    if (cellValue > 5) {
                        cells[cellIndex].style.backgroundColor = '#99ccff';

                    } else if (cellValue >= 2.5 && cellValue <= 5) {
                        cells[cellIndex].style.backgroundColor = '#ccffcc';

                    } else if (cellValue > 0 && cellValue < 2.5) {
                        cells[cellIndex].style.backgroundColor = '#ffff99';

                    } else if (cellValue < 0 && cellValue > -2.5) {
                        cells[cellIndex].style.backgroundColor = '#ffcc99';

                    } else if (cellValue <= -2.5) {
                        cells[cellIndex].style.backgroundColor = '#ff99cc';

                    }
                } else if (cellIndex === 9) {

                    let cellValue = cells[cellIndex].innerHTML;

                    if (cellValue == 'Won') {
                        cells[cellIndex].style.backgroundColor = '#3ED578';
                        cells[0].style.backgroundColor = '#3ED578';
                        cells[14].style.backgroundColor = '#3ED578';
                        cells[20].style.backgroundColor = '#3ED578';
                    } else if (cellValue == 'Placed') {
                        cells[cellIndex].style.backgroundColor = '#f2dac1';
                        cells[0].style.backgroundColor = '#f2dac1';
                        cells[14].style.backgroundColor = '#f2dac1';
                        cells[20].style.backgroundColor = '#f2dac1';
                    }
                } else if (cellIndex === 10 || cellIndex === 18) {

                    let cellValue = parseFloat(cells[cellIndex].innerHTML);

                    if (cellValue < 0) {
                        cells[cellIndex].style.color = '#ff0033'
                    }
                }
            }
        }

    }



});

function toggleTables() {

    let raceTable = document.getElementById("raceTable");
    let raceTableMin = document.getElementById("raceTableMin");

    if (raceTable.style.display === "table") {
        raceTable.style.display = "none";
        raceTableMin.style.display = "table";
    } else {
        raceTable.style.display = "table";
        raceTableMin.style.display = "none";
    }
}

