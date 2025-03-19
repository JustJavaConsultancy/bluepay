document.addEventListener("DOMContentLoaded", function () {

    function populateCountrySelect(selectElement, selectedCountry, callback) {
        fetch("https://restcountries.com/v3.1/all")
            .then(response => response.json())
            .then(countries => {
                countries.sort((a, b) => (a.name?.common || "").localeCompare(b.name?.common || ""));
                selectElement.innerHTML = '';

                if (!selectedCountry) {
                    const defaultOption = document.createElement("option");
                    defaultOption.value = "";
                    defaultOption.textContent = "Select a country";
                    selectElement.appendChild(defaultOption);
                }

                countries.forEach(country => {
                    const option = document.createElement("option");
                    const countryCode = country.cca2;

                    option.value = countryCode;
                    option.textContent = country.name?.common || 'Unknown Country';

                    if (countryCode === selectedCountry) {
                        option.selected = true;
                    }

                    selectElement.appendChild(option);
                });

                if (callback) callback(); // Ensure populateCities runs AFTER population
            })
            .catch(error => console.error("Error fetching countries:", error));
    }


    // Get all select elements
    const countrySelect1 = document.getElementById("country1");
    const countrySelect2 = document.getElementById("country2");
    const countrySelect3 = document.getElementById("country3");

    // Populate each select box with data
    populateCountrySelect(countrySelect1, countrySelect1.getAttribute("data-selected"));
    populateCountrySelect(countrySelect2, countrySelect2.getAttribute("data-selected"), populateCities); // Ensuring it runs after population
    populateCountrySelect(countrySelect3, countrySelect3.getAttribute("data-selected"));


    function populateCities() {
        const countrySelect = document.getElementById("country2");
        const citySelect = document.getElementById("cities1");
        const selectedState = citySelect.getAttribute("data-selected");
        citySelect.innerHTML = '<option value="">-- Select City --</option>'; // Clear existing options

        if (countrySelect.value === "NG") { // NG is the country code for Nigeria
            const nigerianCities = ["Lagos", "Abuja", "Kano", "Port Harcourt", "Ibadan", "Benin City"];
            nigerianCities.forEach(city => {
                let option = document.createElement("option");
                option.value = city;
                option.textContent = city;
                citySelect.appendChild(option);
                if (city === selectedState) {
                    option.selected = true;
                }

                citySelect.appendChild(option);
            });

        }

    }


    document.getElementById('country2').addEventListener('change', populateCities)
    const step1 = document.getElementById("form1");
    const step2 = document.getElementById("form2");
    const step3 = document.getElementById("form3");
    const step4 = document.getElementById("form4");
    const step5 = document.getElementById("form5");
    const step6 = document.getElementById("form6");
    const nextBtn = document.getElementById("next1");
    const nextBtn2 = document.getElementById("next2");
    const nextBtn3 = document.getElementById("next3");
    const nextBtn4 = document.getElementById("next4");
    const submitBtn = document.getElementById("submitBtn");

// Get all required inputs for each step
    const step1Inputs = step1.querySelectorAll("input, select");
    const step2Inputs = step2.querySelectorAll("input[required], select[required]");
    const step3Inputs = step3.querySelectorAll("input[required], select[required]");
    const step4Inputs = step4.querySelectorAll("input[required], select[required]");
    const step5Inputs = step5.querySelectorAll("input[required], select[required]");

// Validation functions
    function validateStep1() {
        let isValid = true;
        step1Inputs.forEach(input => {
            if (input.type === "checkbox") {
                if (!input.checked) isValid = false;
            } else {
                if (!input.value.trim() || input.value === "Select") isValid = false;
            }
        });
        nextBtn.disabled = !isValid;
    }

    function validateStep2() {
        let isValid = true;
        step2Inputs.forEach(input => {
            if (input.type === "checkbox") {
                // Handle checkbox validation if needed
            } else {
                if (!input.value.trim() || input.value === "Select") isValid = false;
            }
        });
        nextBtn2.disabled = !isValid;
    }

    function validateStep3() {
        let isValid = true;
        step3Inputs.forEach(input => {
            if (input.disabled) return; // Skip disabled fields
            if (input.type === "checkbox") {
                // Handle checkbox validation if needed
            } else {
                if (!input.value.trim() || input.value === "Select") isValid = false;
            }
        });
        nextBtn3.disabled = !isValid;
    }

    function validateStep4() {
        let isValid = true;
        step4Inputs.forEach(input => {
            if (input.type === "checkbox") {
                // Handle checkbox validation if needed
            } else {
                if (!input.value.trim() || input.value === "Select") isValid = false;
            }
        });
        nextBtn4.disabled = !isValid;
    }

    function validateStep5() {
        let isValid = true;
        step5Inputs.forEach(input => {
            if (input.type === "checkbox") {
                if (!input.checked) isValid = false;
            } else {
                if (!input.value.trim() || input.value === "Select") isValid = false;
            }
        });
        submitBtn.disabled = !isValid;
    }

// Attach event listeners to inputs for validation
    step1Inputs.forEach(input => input.addEventListener("input", validateStep1));
    step2Inputs.forEach(input => input.addEventListener("input", validateStep2));
    step3Inputs.forEach(input => input.addEventListener("input", validateStep3));
    step4Inputs.forEach(input => input.addEventListener("input", validateStep4));
    step5Inputs.forEach(input => input.addEventListener("input", validateStep5));

// Scroll to top function
    function scrollToTop() {
        const content = document.querySelector(".content");
        if (content && content.scrollHeight > content.clientHeight) {
            content.offsetHeight; // Force reflow
            if ('scrollBehavior' in document.documentElement.style) {
                content.scrollTo({top: 0, behavior: "smooth"});
            } else {
                content.scrollTo(0, 0); // Fallback for older browsers
            }
        } else {
            document.body.offsetHeight; // Force reflow
            if ('scrollBehavior' in document.documentElement.style) {
                window.scrollTo({top: 0, behavior: "smooth"});
            } else {
                window.scrollTo(0, 0); // Fallback for older browsers
            }
        }
    }

// Progress bar functions
    function increaseProgress() {
        const numberElement = document.getElementById("increase");
        const bar = document.getElementById("progress-bar");
        const currentNumber = parseInt(numberElement.textContent);
        numberElement.textContent = currentNumber + 20;
        bar.style.width = `${currentNumber + 20}%`;
    }

    function decreaseProgress() {
        const numberElement = document.getElementById("increase");
        const bar = document.getElementById("progress-bar");
        const currentNumber = parseInt(numberElement.textContent);
        numberElement.textContent = currentNumber - 20;
        bar.style.width = `${currentNumber - 20}%`;
    }

// Navigation between steps
    nextBtn.addEventListener("click", function (e) {
        e.preventDefault();
        step1.style.display = "none";
        step2.style.display = "block";
        step3.style.display = "none";
        step4.style.display = "none";
        step5.style.display = "none";
        $(".cb1").prop("checked", true);
        increaseProgress();
        scrollToTop();
    });

    $('#prev1').on('click', function (e) {
        e.preventDefault();
        step1.style.display = "block";
        step2.style.display = "none";
        step3.style.display = "none";
        step4.style.display = "none";
        step5.style.display = "none";
        $(".cb1").prop("checked", false);
        decreaseProgress();
        scrollToTop();
    });

    nextBtn2.addEventListener("click", function (e) {
        e.preventDefault();
        step2.style.display = "none";
        step1.style.display = "none";
        step3.style.display = "block";
        step4.style.display = "none";
        step5.style.display = "none";
        $(".cb2").prop("checked", true);
        increaseProgress();
        scrollToTop();
    });

    $('#prev2').on('click', function (e) {
        e.preventDefault();
        step2.style.display = "block";
        step1.style.display = "none";
        step3.style.display = "none";
        step4.style.display = "none";
        step5.style.display = "none";
        $(".cb2").prop("checked", false);
        decreaseProgress();
        scrollToTop();
    });

    nextBtn3.addEventListener("click", function (e) {
        e.preventDefault();
        step2.style.display = "none";
        step1.style.display = "none";
        step3.style.display = "none";
        step4.style.display = "block";
        step5.style.display = "none";
        $(".cb3").prop("checked", true);
        increaseProgress();
        scrollToTop();
    });

    $('#prev3').on('click', function (e) {
        e.preventDefault();
        step2.style.display = "none";
        step1.style.display = "none";
        step3.style.display = "block";
        step4.style.display = "none";
        step5.style.display = "none";
        $(".cb3").prop("checked", false);
        decreaseProgress();
        scrollToTop();
    });

    nextBtn4.addEventListener("click", function (e) {
        e.preventDefault();
        step2.style.display = "none";
        step1.style.display = "none";
        step3.style.display = "none";
        step4.style.display = "none";
        step5.style.display = "block";
        $(".cb4").prop("checked", true);
        increaseProgress();
        scrollToTop();
    });

    $('#prev4').on('click', function (e) {
        e.preventDefault();
        step2.style.display = "none";
        step1.style.display = "none";
        step3.style.display = "none";
        step4.style.display = "block";
        step5.style.display = "none";
        step6.style.display = "none";
        $(".cb4").prop("checked", false);
        decreaseProgress();
        scrollToTop();
    });

    submitBtn.addEventListener("click", function (e) {
        e.preventDefault();
        document.querySelector("#form6 div").classList.remove("hideSummary")
        step1.style.display = "none";
        step3.style.display = "none";
        step4.style.display = "none";
        step5.style.display = "none";
        $(".cb5").prop("checked", true);
        increaseProgress();
        scrollToTop();
    });
    $('#prev5').on('click', function (e) {
        e.preventDefault();
        step2.style.display = "none";
        step1.style.display = "none";
        step3.style.display = "none";
        step4.style.display = "none";
        step5.style.display = "block";
        document.querySelector("#form6 div").classList.add("hideSummary")
        $(".cb5").prop("checked", false);
        decreaseProgress();
        scrollToTop();
    });


    $('#toggleCheckbox').change(function () {
        if ($(this).is(':checked')) {
            $('.toggle-fields').hide();
            $('.toggle-fields input').prop('disabled', true).removeAttr('required');
        } else {
            $('.toggle-fields').show();
            $('.toggle-fields input').prop('disabled', false).attr('required', 'required');
        }
        validateStep3(); // Revalidate after toggling
    });
});