document.addEventListener("DOMContentLoaded", function () {
 const toggleDropdown = document.getElementById("toggleDropdown");
 const dropdownMenu = document.querySelector(".dropdown-logout");

 toggleDropdown.addEventListener("click", function (event) {
  event.stopPropagation(); // Prevent closing when clicking the toggle
  dropdownMenu.style.display = dropdownMenu.style.display === "block" ? "none" : "block";

  // Change arrow icon
  const icon = this.querySelector("i");
  icon.classList.toggle("bi-chevron-down");
  icon.classList.toggle("bi-chevron-up");
 });

 // Close dropdown when clicking outside
 document.addEventListener("click", function (event) {
  if (!event.target.closest(".position-relative")) {
   dropdownMenu.style.display = "none";
   const icon = toggleDropdown.querySelector("i");
   icon.classList.add("bi-chevron-down");
   icon.classList.remove("bi-chevron-up");
  }
 });
});