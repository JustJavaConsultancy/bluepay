
document.addEventListener("DOMContentLoaded", function () {
    // Select the progress inflow container div
    var inflowContainer = document.getElementById("progressInflow-container");
    var inflowCurrentValue = Number(document.getElementById("progressInflow-text").innerText);

    var outflowContainer = document.getElementById("progressOutflow-container");
    var outflowCurrentValue = Number(document.getElementById("progressOutflow-text").innerText);


    // Initialize the circular progress bar
    var inflowCircle = new ProgressBar.Circle(inflowContainer, {
        strokeWidth: 8,
        color: "#98FF98", // Bootstrap primary color
        trailColor: "#e0e0e0",
        duration: 2000, // Animation duration in milliseconds
        easing: "easeInOut",
        text: {
            value: inflowCurrentValue + "%",
            className: "progress-text"
        },
    });

    let inflowAnimateValue = inflowCurrentValue / 100;
    inflowCircle.animate(inflowAnimateValue);

//    initialize circular outflow progress bar
    var inflowCircle = new ProgressBar.Circle(outflowContainer, {
            strokeWidth: 8,
            color: "#98FF98", // Bootstrap primary color
            trailColor: "#e0e0e0",
            duration: 2000, // Animation duration in milliseconds
            easing: "easeInOut",
            text: {
                value: outflowCurrentValue + "%",
                className: "progress-text"
            },
        });

        let outflowAnimateValue = outflowCurrentValue / 100;
        inflowCircle.animate(outflowAnimateValue);
});


