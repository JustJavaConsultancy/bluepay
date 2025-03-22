document.addEventListener("DOMContentLoaded", function () {
    let fileArray = []; // Store selected files as File objects
    const MAX_FILES = 6;
    const MAX_SIZE = 5 * 1024 * 1024; // 5MB

    const fileInput = document.getElementById('fileInput');
    const previewContainer = document.getElementById('previewContainer');
    const submitButton = document.getElementById('createProduct');

    fileInput.addEventListener('change', function(event) {
        const files = Array.from(event.target.files);
        if (files.length === 0) return;

        for (let i = 0; i < files.length; i++) {
            if (!files[i] || fileArray.length >= MAX_FILES) break;

            if (files[i].size > MAX_SIZE) {
                alert("File size must not exceed 5MB.");
                continue;
            }

            if (files[i].type.startsWith("image/") || files[i].type.startsWith("video/")) {
                fileArray.push(files[i]); // Store the actual File object
                const reader = new FileReader();
                reader.onload = function(e) {
                    addFileToPreview(e.target.result, files[i].type, files[i].name);
                };
                reader.readAsDataURL(files[i]);
            } else {
                alert("Only images and videos are allowed.");
            }
        }

        event.target.value = ''; // Reset file input
        if (fileArray.length >= 0) {
            document.getElementById('createProduct').disabled = false;
        } else {
            document.getElementById('createProduct').disabled = true;
        }
    });

    function addFileToPreview(src, type, name) {
        if (fileArray.length >= MAX_FILES) {
            alert("Maximum of 6 files allowed.");
            return;
        }

        const fileId = "file_" + Date.now(); // Unique ID
        const div = document.createElement('div');
        div.classList.add('file-box');
        div.setAttribute('id', fileId);

        let content = type.startsWith("image/") ?
            `<img src="${src}" alt="${name}">` :
            `<video id="video_${fileId}" src="${src}"></video>
               <button type="button" class="play-btn" aria-label="Play/Pause" data-video="video_${fileId}">
                   <i class="bi bi-play-fill"></i>
               </button>`;

        div.innerHTML = `
            <button type="button" class="delete-btn" aria-label="Delete" data-delete="${fileId}">
                <i class="bi bi-trash"></i>
            </button>
            ${content}
        `;

        previewContainer.appendChild(div);
    }

    previewContainer.addEventListener('click', function(event) {
        if (event.target.closest('.delete-btn')) {
            const fileId = event.target.closest('.delete-btn').getAttribute('data-delete');
            removeFile(fileId);
        }

        if (event.target.closest('.play-btn')) {
            const videoId = event.target.closest('.play-btn').getAttribute('data-video');
            togglePlayPause(videoId, event.target.closest('.play-btn'));
        }
    });

    window.removeFile = function(id) {
        fileArray = fileArray.filter(file => `file_${file.lastModified}` !== id);
        const element = document.getElementById(id);
        if (element) {
            element.remove();
        }
        if (fileArray.length >= 0) {
            document.getElementById('createProduct').disabled = false;
        } else {
            document.getElementById('createProduct').disabled = true;
        }
    };

    window.togglePlayPause = function(videoId, btn) {
        const video = document.getElementById(videoId);
        if (video.paused) {
            video.play();
            btn.innerHTML = '<i class="bi bi-pause-fill"></i>';
        } else {
            video.pause();
            btn.innerHTML = '<i class="bi bi-play-fill"></i>';
        }

        video.addEventListener('ended', function() {
            btn.innerHTML = '<i class="bi bi-play-fill"></i>';
        });
    };

    function toggleSubmitButton() {
        submitButton.disabled = fileArray.length === 0;
    }


    document.getElementById('addProductForm').addEventListener('submit', function(event) {
        event.preventDefault(); // Prevent the default form submission

        // Gather form data
        const formData = new FormData();
            formData.append('productName', document.getElementById('productName').value);
            formData.append('productDescription', document.getElementById('productDescription').value);
            formData.append('productPrice', document.getElementById('productPrice').value);
            formData.append('productQuantity', document.getElementById('productQuantity').value);
            formData.append('productQuantityNum', document.getElementById('productQuantityNum').value);
            formData.append('physicalProducts', document.getElementById('flexSwitchCheckDefault').checked);

        // Append files if any
        let fileNames = [];
        for (let i = 0; i < fileArray.length; i++) {
            formData.append('productImageNames', fileArray[i]);  // Append files directly
        }

        let submitBtn = document.getElementById("createProduct");
        submitBtn.disabled = true;
        submitBtn.textContent = "Submitting...";
        // Send form data using Fetch API
        fetch('/addNewProduct', {
            method: 'POST',
            body: formData
        })
            .then(response => response.json())
            .then(data => {
                console.log('Success:', data);
                location.reload();
                // Handle success response
            })
            .catch((error) => {
                console.error('Error:', error);
                // Handle error
            });
    });
});
