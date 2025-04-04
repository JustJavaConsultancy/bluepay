document.addEventListener("DOMContentLoaded", function () {
    var ctx = document.getElementById("myBarChart").getContext("2d");
    var bankCurrentAmountText = document.getElementById("bankCurrentAmount").innerText;
    var bankCurrentAmountObject = JSON.parse(bankCurrentAmountText);
    var bankCurrentAmountList = bankCurrentAmountObject.map(amount => parseFloat(amount));

    var bankCurrentDateText = document.getElementById("bankCurrentDate").innerText;
    console.log(bankCurrentDateText)
    var bankCurrentDateObject = JSON.parse(bankCurrentDateText);
    var bankCurrentDateList = bankCurrentDateObject.map(dateObj => "April " + dateObj);


    // Create the bar chart
    var myBarChart = new Chart(ctx, {
        type: "bar", // Bar chart type
        data: {
            labels: bankCurrentDateList, // X-axis labels
            datasets: [{
                label: "Revenue", // Label for the dataset
                data: bankCurrentAmountList, // Y-axis data
                backgroundColor: [
                    "#e6f0ff"
                ],
                borderColor: [
                     "#b3d1ff"
                ],
                borderWidth: 2
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true, // Start the Y-axis at 0
                    max: 100000
                }
            }
        }
    });
}
);