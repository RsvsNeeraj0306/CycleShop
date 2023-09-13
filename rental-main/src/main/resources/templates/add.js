// app.js

$(document).ready(function () {

    // Function to fetch and display the list of cycles
    function getCyclesList() {
        $.ajax({
            url: '/api/cycles',
            method: 'GET',
            success: function (data) {
                var cycleList = $('#cycle-list-items');
                cycleList.empty();
                $.each(data, function (index, cycle) {
                    cycleList.append('<li>' + cycle.cycleName + '</li>');
                });
            }
        });
    }

    // Function to fetch and display the list of borrowed cycles
    function getBorrowedCyclesList() {
        $.ajax({
            url: '/api/cycles/borrowed',
            method: 'GET',
            success: function (data) {
                var borrowedCycleList = $('#borrowed-cycle-list');
                borrowedCycleList.empty();
                $.each(data, function (index, borrowedCycle) {
                    borrowedCycleList.append('<li>' + borrowedCycle.cycleName + '</li>');
                });
            }
        });
    }

    // Function to add a cycle
    $('#add-cycle-button').click(function () {
        var newCycle = {
            cycleName: 'New Cycle', // You can customize this data
            stock: 1
        };

        $.ajax({
            url: '/api/cycles/add',
            method: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(newCycle),
            success: function () {
                getCyclesList(); // Refresh the cycles list after adding
            }
        });
    });

    // Function to add stock to a cycle
    $('#add-stock-button').click(function () {
        // Customize the cycle ID as needed
        var cycleId = 1;

        $.ajax({
            url: '/api/cycles/addStock/' + cycleId,
            method: 'PUT',
            success: function () {
                getCyclesList(); // Refresh the cycles list after adding stock
            }
        });
    });

    // Fetch and display initial data
    getCyclesList();
    getBorrowedCyclesList();

});
