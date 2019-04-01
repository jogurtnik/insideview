$(document).ready(function() {

    var raceTable = document.getElementById('raceTable');
    var raceTbody = raceTable.getElementsByTagName('tbody')[0];
    var raceRows = raceTbody.getElementsByTagName('tr');
    
    for (let rowIndex=0, len=raceRows.length; rowIndex<len; rowIndex++) {
        
        let cells = raceRows[rowIndex].getElementsByTagName('td');

        for (let cellIndex = 0, len=cells.length; cellIndex<len; cellIndex++) {

            if (cellIndex > 3 && cellIndex%2 === 0 && cellIndex < 18) {

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

            } else if (cellIndex >= 18 && cellIndex < 21) {

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
            } else if (cellIndex === 21) {

                let cellValue = cells[cellIndex].innerHTML;
                
                if (cellValue == 'Won') {
                    cells[cellIndex].style.backgroundColor = '#3ED578';
                    cells[0].style.backgroundColor = '#3ED578';
                } else if (cellValue == 'Placed') {
                    cells[cellIndex].style.backgroundColor = '#f2dac1';
                    cells[0].style.backgroundColor = '#f2dac1';
                }
            } else if (cellIndex === 22) {

                let cellValue = parseFloat(cells[cellIndex].innerHTML);
                
                if (cellValue < 0) {
                    cells[cellIndex].style.color = '#ff0033'
                }
            }
        }
    }

    var raceTableMin = document.getElementById('raceTableMin');
    var raceTbodyMin = raceTableMin.getElementsByTagName('tbody');
    var raceRowsMin = raceTbodyMin[0].getElementsByTagName('tr');

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
                    cells[16].style.backgroundColor = '#3ED578';
                    cells[22].style.backgroundColor = '#3ED578';
                } else if (cellValue == 'Placed') {
                    cells[cellIndex].style.backgroundColor = '#f2dac1';
                    cells[0].style.backgroundColor = '#f2dac1';
                    cells[16].style.backgroundColor = '#f2dac1';
                    cells[22].style.backgroundColor = '#f2dac1';
                }
            } else if (cellIndex === 10 || cellIndex === 20) {

                let cellValue = parseFloat(cells[cellIndex].innerHTML);

                if (cellValue < 0) {
                    cells[cellIndex].style.color = '#ff0033'
                }
            }
        }
    }

});