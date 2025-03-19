document.addEventListener('DOMContentLoaded', function () {
    const buttons = document.querySelectorAll('.saveCompliance');

    buttons.forEach(button => {
        button.disabled = false; // Enable the button
    });
});