document.addEventListener("DOMContentLoaded", function () {
    let dobInput = document.getElementById("dob");

    // Calculate the max date (18 years ago from today)
    let today = new Date();
    let minDate = new Date();
    minDate.setFullYear(today.getFullYear() - 18);

    // Set max attribute to the input field
    dobInput.setAttribute("max", minDate.toISOString().split("T")[0]);

    // Validate on input change
    dobInput.addEventListener("change", function () {
        let selectedDate = new Date(this.value);
        if (selectedDate > minDate) {
            this.value = ""; // Clear invalid input
        }
    });
});