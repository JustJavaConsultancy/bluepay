
        $(document).ready(function() {
            let fileInput = $('#fileInput');
            let uploadText = $('#uploadText');
            let uploadArea = $('#uploadArea');

            fileInput.on('change', function(event) {
                let file = event.target.files[0];
                if (file) {
                    uploadText.text(`Selected File: ${file.name}`);
                }
            });

            uploadArea.on('dragover', function(event) {
                event.preventDefault();
                uploadArea.css('background-color', '#e9ecef');
            });

            uploadArea.on('dragleave', function() {
                uploadArea.css('background-color', '#f8f9fa');
            });

            uploadArea.on('drop', function(event) {
                event.preventDefault();
                uploadArea.css('background-color', '#f8f9fa');
                let file = event.originalEvent.dataTransfer.files[0];
                if (file) {
                    uploadText.text(`Selected File: ${file.name}`);
                    fileInput[0].files = event.originalEvent.dataTransfer.files;
                }
            });
        });
    
