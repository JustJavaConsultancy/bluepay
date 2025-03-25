document.addEventListener("DOMContentLoaded", function () {
    let fileInput = document.getElementById("fileInput");
    let uploadText = document.getElementById("uploadText");
    let uploadArea = document.getElementById("uploadArea");

    console.log(uploadArea);

    fileInput.addEventListener("change", function (event) {
        let file = event.target.files[0];
        if (file) {
            uploadText.textContent = `Selected File: ${file.name}`;
        }

    });

    uploadArea.addEventListener("dragover", function (event) {
        event.preventDefault();
        uploadArea.style.backgroundColor = "#e9ecef";
    });

    uploadArea.addEventListener("dragleave", function () {
        uploadArea.style.backgroundColor = "#f8f9fa";
    });

    uploadArea.addEventListener("drop", function (event) {
        event.preventDefault();
        uploadArea.style.backgroundColor = "#f8f9fa";
        let file = event.dataTransfer.files[0];
        if (file) {
            uploadText.textContent = `Selected File: ${file.name}`;
            fileInput.files = event.dataTransfer.files;
        }
    });
});
