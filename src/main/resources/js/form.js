document.addEventListener("DOMContentLoaded", function () {
    const step1 = document.getElementById("form1");
    const step2 = document.getElementById("form2");
    const step3 = document.getElementById("form3");
    const step4 = document.getElementById("form4");
    const step5 = document.getElementById("form5");
    const nextlink = document.getElementById("link1")
    const nextBtn = document.getElementById("next1");
    const nextBtn2 = document.getElementById("next2");
    const nextBtn3 = document.getElementById("next3");
    const nextBtn4 = document.getElementById("next4");
    const submitBtn = document.getElementById("submitBtn");
    const step5Inputs = step5.querySelectorAll("input[required], select[required]");
    const step4Inputs = step4.querySelectorAll("input[required], select[required]");
    const step3Inputs = step3.querySelectorAll("input[required], select[required]");
    const step2Inputs = step2.querySelectorAll("input[required], select[required]");
    const step1Inputs = step1.querySelectorAll("input, select");

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

            } else {
                if (!input.value.trim() || input.value === "Select") isValid = false;
            }
        });
        nextBtn2.disabled = !isValid;
    }
    function validateStep3() {
        let isValid = true;
        step3Inputs.forEach(input => {
            if (input.type === "checkbox") {

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
    step1Inputs.forEach(input => input.addEventListener("input", validateStep1));
    step2Inputs.forEach(input => input.addEventListener("input", validateStep2));
    step3Inputs.forEach(input => input.addEventListener("input", validateStep3));
    step4Inputs.forEach(input => input.addEventListener("input", validateStep4));
    step5Inputs.forEach(input => input.addEventListener("input", validateStep5));


    function scrollToTop() {
        setTimeout(() => {
            window.scrollTo({ top: 0, behavior: "smooth" });
        }, 100); // Delay to ensure the new step is visible first
    }
    function increaseProgress() {
        let numberElement = document.getElementById("increase");
        let bar = document.getElementById("progress-bar")
        let currentNumber = parseInt(numberElement.textContent);
        numberElement.textContent = currentNumber + 20;
        bar.style.width = `${currentNumber + 20}%`;
    }
    function decreaseProgress(){
        let numberElement = document.getElementById("increase");
        let bar = document.getElementById("progress-bar")
        let currentNumber = parseInt(numberElement.textContent);
        numberElement.textContent = currentNumber - 20;
        bar.style.width = `${currentNumber - 20}%`;
    }



    nextBtn.addEventListener("click", function (e) {
        e.preventDefault();
        step1.style.display = "none";
        step2.style.display = "block";
        step3.style.display = "none"
        step4.style.display = "none";
        step5.style.display = "none";
        $(".cb1").prop("checked", true);
        increaseProgress()
        window.scrollTo({ top: 0, behavior: "smooth" });
    });

    $('#prev1').on('click', function(e) {
        e.preventDefault();
        step1.style.display = "block";
        step2.style.display = "none";
        step3.style.display = "none";
        step4.style.display = "none";
        step5.style.display = "none";
        $(".cb1").prop("checked", false);
        decreaseProgress()
        window.scrollTo({ top: 0, behavior: "smooth" });
    })

    nextBtn2.addEventListener("click", function (e) {
        e.preventDefault();
        step2.style.display = "none";
        step1.style.display = "none";
        step3.style.display = "block";
        step4.style.display = "none";
        step5.style.display = "none";
        $(".cb2").prop("checked", true);
        increaseProgress()
        window.scrollTo({ top: 0, behavior: "smooth" });
    });

    $('#prev2').on('click', function(e) {
        e.preventDefault();
        step2.style.display = "block";
        step1.style.display = "none";
        step3.style.display = "none";
        step4.style.display = "none";
        step5.style.display = "none";
        $(".cb2").prop("checked", false);
        decreaseProgress()
        window.scrollTo({ top: 0, behavior: "smooth" });
    })
    nextBtn3.addEventListener("click", function (e) {
        e.preventDefault();
        step2.style.display = "none";
        step1.style.display = "none";
        step3.style.display = "none";
        step4.style.display = "block";
        step5.style.display = "none";
        $(".cb3").prop("checked", true);
        increaseProgress()
        window.scrollTo({ top: 0, behavior: "smooth" });
    });

    $('#prev3').on('click', function(e) {
        e.preventDefault();
        step2.style.display = "none";
        step1.style.display = "none";
        step3.style.display = "block";
        step4.style.display = "none";
        step5.style.display = "none";
        $(".cb3").prop("checked", false);
        decreaseProgress()
        window.scrollTo({ top: 0, behavior: "smooth" });
    })
    nextBtn4.addEventListener("click", function (e) {
        e.preventDefault();
        step2.style.display = "none";
        step1.style.display = "none";
        step3.style.display = "none";
        step4.style.display = "none";
        step5.style.display = "block";
        $(".cb4").prop("checked", true);
        increaseProgress()
        window.scrollTo({ top: 0, behavior: "smooth" });
    });

    $('#prev4').on('click', function(e) {
        e.preventDefault();
        step2.style.display = "none";
        step1.style.display = "none";
        step3.style.display = "none";
        step4.style.display = "block";
        step5.style.display = "none";
        $(".cb4").prop("checked", false);
        decreaseProgress()
        window.scrollTo({ top: 0, behavior: "smooth" });
    })

})

