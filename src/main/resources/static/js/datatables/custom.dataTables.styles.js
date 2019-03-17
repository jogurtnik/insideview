$(document).ready(function() {

    init_DataTables();

});

function init_DataTables() {

    let domCommand = 'Blfrtip';
    let buttonsMap = [
        {
            extend: "copy",
            className: "btn-sm"
        },
        {
            extend: "csv",
            className: "btn-sm"
        },
        {
            extend: "excel",
            className: "btn-sm"
        },
        {
            extend: "pdfHtml5",
            className: "btn-sm"
        },
        {
            extend: "print",
            className: "btn-sm"
        },
    ];

    $('.dataTableRace').DataTable({

        dom: domCommand,
        buttons: buttonsMap,

        rowCallback: raceCellsColorFormatting

    });

    $('#dataTableRunners').DataTable({
        "ordering": false,
        dom: domCommand,
        buttons: buttonsMap,
        rowCallback: runnerCellsColorFormatting
    });

    function raceCellsColorFormatting(row, data) {

        // conditional background-color formatting for HorseName column
        if (data[7] == 'Won') {
            $(row).find('td:eq(0)').css('background-color', '#3ED578');
            $(row).find('td:eq(7)').css('background-color', '#3ED578');
        } else if (data[7] == 'Placed') {
            $(row).find('td:eq(0)').css('background-color', '#f2dac1');
            $(row).find('td:eq(7)').css('background-color', '#f2dac1');
        }

        // conditional background-color formatting for MovAM column
        if (data[2] > 5) {
            $(row).find('td:eq(2)').css('background-color', '#99ccff');

        } else if (data[2] >= 2.5 && data[2] <= 5) {
            $(row).find('td:eq(2)').css('background-color', '#ccffcc');

        } else if (data[2] > 0 && data[2] < 2.5) {
            $(row).find('td:eq(2)').css('background-color', '#ffff99');

        } else if (data[2] < 0 && data[2] > -2.5) {
            $(row).find('td:eq(2)').css('background-color', '#ffcc99');

        } else if (data[2] <= -2.5) {
            $(row).find('td:eq(2)').css('background-color', '#ff99cc');
        }

        // conditional background-color formatting for Mov1 column
        if (data[4] > 5) {
            $(row).find('td:eq(4)').css('background-color', '#99ccff');

        } else if (data[4] >= 2.5 && data[4] <= 5) {
            $(row).find('td:eq(4)').css('background-color', '#ccffcc');

        } else if (data[4] > 0 && data[4] < 2.5) {
            $(row).find('td:eq(4)').css('background-color', '#ffff99');

        } else if (data[4] < 0 && data[4] > -2.5) {
            $(row).find('td:eq(4)').css('background-color', '#ffcc99');

        } else if (data[4] <= -2.5) {
            $(row).find('td:eq(4)').css('background-color', '#ff99cc');
        }

        // conditional background-color formatting for Mean column
        if (data[5] > 5) {
            $(row).find('td:eq(5)').css('background-color', '#99ccff');

        } else if (data[5] >= 2.5 && data[5] <= 5) {
            $(row).find('td:eq(5)').css('background-color', '#ccffcc');

        } else if (data[5] > 0 && data[5] < 2.5) {
            $(row).find('td:eq(5)').css('background-color', '#ffff99');

        } else if (data[5] < 0 && data[5] > -2.5) {
            $(row).find('td:eq(5)').css('background-color', '#ffcc99');

        } else if (data[5] <= -2.5) {
            $(row).find('td:eq(5)').css('background-color', '#ff99cc');
        }

        // conditional background-color formatting for 321 column
        if (data[6] > 5) {
            $(row).find('td:eq(6)').css('background-color', '#99ccff');

        } else if (data[6] >= 2.5 && data[6] <= 5) {
            $(row).find('td:eq(6)').css('background-color', '#ccffcc');

        } else if (data[6] > 0 && data[6] < 2.5) {
            $(row).find('td:eq(6)').css('background-color', '#ffff99');

        } else if (data[6] < 0 && data[6] > -2.5) {
            $(row).find('td:eq(6)').css('background-color', '#ffcc99');

        } else if (data[6] <= -2.5) {
            $(row).find('td:eq(6)').css('background-color', '#ff99cc');
        }
    }

    function runnerCellsColorFormatting(row, data) {

        // conditional background-color formatting for HorseName column
        if (data[9] == 'Won') {
            $(row).find('td:eq(2)').css('background-color', '#3ED578');
            $(row).find('td:eq(9)').css('background-color', '#3ED578');
        } else if (data[9] == 'Placed') {
            $(row).find('td:eq(2)').css('background-color', '#f2dac1');
            $(row).find('td:eq(9)').css('background-color', '#f2dac1');
        }

        // conditional background-color formatting for MovAM column
        if (data[4] > 5) {
            $(row).find('td:eq(4)').css('background-color', '#99ccff');

        } else if (data[4] >= 2.5 && data[2] <= 5) {
            $(row).find('td:eq(4)').css('background-color', '#ccffcc');

        } else if (data[4] > 0 && data[2] < 2.5) {
            $(row).find('td:eq(4)').css('background-color', '#ffff99');

        } else if (data[4] < 0 && data[2] > -2.5) {
            $(row).find('td:eq(4)').css('background-color', '#ffcc99');

        } else if (data[4] <= -2.5) {
            $(row).find('td:eq(4)').css('background-color', '#ff99cc');
        }

        // conditional background-color formatting for Mov1 column
        if (data[6] > 5) {
            $(row).find('td:eq(6)').css('background-color', '#99ccff');

        } else if (data[6] >= 2.5 && data[4] <= 5) {
            $(row).find('td:eq(6)').css('background-color', '#ccffcc');

        } else if (data[6] > 0 && data[4] < 2.5) {
            $(row).find('td:eq(6)').css('background-color', '#ffff99');

        } else if (data[6] < 0 && data[4] > -2.5) {
            $(row).find('td:eq(6)').css('background-color', '#ffcc99');

        } else if (data[6] <= -2.5) {
            $(row).find('td:eq(6)').css('background-color', '#ff99cc');
        }

        // conditional background-color formatting for Mean column
        if (data[7] > 5) {
            $(row).find('td:eq(7)').css('background-color', '#99ccff');

        } else if (data[7] >= 2.5 && data[5] <= 5) {
            $(row).find('td:eq(7)').css('background-color', '#ccffcc');

        } else if (data[7] > 0 && data[5] < 2.5) {
            $(row).find('td:eq(7)').css('background-color', '#ffff99');

        } else if (data[7] < 0 && data[5] > -2.5) {
            $(row).find('td:eq(7)').css('background-color', '#ffcc99');

        } else if (data[7] <= -2.5) {
            $(row).find('td:eq(7)').css('background-color', '#ff99cc');
        }

        // conditional background-color formatting for 321 column
        if (data[8] > 5) {
            $(row).find('td:eq(8)').css('background-color', '#99ccff');

        } else if (data[8] >= 2.5 && data[6] <= 5) {
            $(row).find('td:eq(8)').css('background-color', '#ccffcc');

        } else if (data[8] > 0 && data[6] < 2.5) {
            $(row).find('td:eq(8)').css('background-color', '#ffff99');

        } else if (data[8] < 0 && data[6] > -2.5) {
            $(row).find('td:eq(8)').css('background-color', '#ffcc99');

        } else if (data[8] <= -2.5) {
            $(row).find('td:eq(8)').css('background-color', '#ff99cc');
        }
    }

}