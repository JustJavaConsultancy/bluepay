document.addEventListener("DOMContentLoaded", function () {
    const dropdowns = document.querySelectorAll(".customdropdown");

    dropdowns.forEach(dropdown => {
        const button = dropdown.querySelector("button");
        const menu = dropdown.querySelector(".customdropdown-menu");

        // Toggle dropdown on button click
        button.addEventListener("click", function (event) {
            event.stopPropagation();
            closeAllDropdowns();
            dropdown.classList.toggle("active");
        });

        // Close dropdown when clicking outside
        document.addEventListener("click", function (event) {
            if (!dropdown.contains(event.target)) {
                dropdown.classList.remove("active");
            }
        });

        // Close all other dropdowns when opening a new one
        function closeAllDropdowns() {
            dropdowns.forEach(d => {
                if (d !== dropdown) {
                    d.classList.remove("active");
                }
            });
        }
    });
});
