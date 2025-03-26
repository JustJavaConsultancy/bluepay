 import 'bootstrap';
import 'bootstrap/dist/js/bootstrap.bundle.min.js'; // Ensures Bootstrap JS is fully loaded
import 'htmx.org';
import flatpickr from 'flatpickr';
import './orderViewing';
import 'scss/app.scss';
import './task-form';
import './chat';
import './form';
import './document';
import './compliance';
import './dateValidation';
import './logOut';
import './complianceButton';
import './productImage';
import './dropdrown';


// Function to initialize datepickers
function initDatepicker() {
  document.querySelectorAll('.js-datepicker, .js-timepicker, .js-datetimepicker').forEach(($item) => {
    const flatpickrConfig = {
      allowInput: true,
      time_24hr: true,
      enableSeconds: true
    };
    if ($item.classList.contains('js-datepicker')) {
      flatpickrConfig.dateFormat = 'Y-m-d';
    } else if ($item.classList.contains('js-timepicker')) {
      flatpickrConfig.enableTime = true;
      flatpickrConfig.noCalendar = true;
      flatpickrConfig.dateFormat = 'H:i:S';
    } else { // datetimepicker
      flatpickrConfig.enableTime = true;
      flatpickrConfig.altInput = true;
      flatpickrConfig.altFormat = 'Y-m-d H:i:S';
      flatpickrConfig.dateFormat = 'Y-m-dTH:i:S';
      // workaround label issue
      flatpickrConfig.onReady = function() {
        const id = this.input.id;
        this.input.id = null;
        this.altInput.id = id;
      };
    }
    flatpickr($item, flatpickrConfig);
  });
}

// Initialize Bootstrap tooltips and popovers if needed
document.addEventListener('DOMContentLoaded', () => {
  initDatepicker();

  // Bootstrap Tooltips Initialization
  const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
  tooltipTriggerList.forEach(tooltipTriggerEl => {
    new bootstrap.Tooltip(tooltipTriggerEl);
  });

  // Bootstrap Popovers Initialization
  const popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'));
  popoverTriggerList.forEach(popoverTriggerEl => {
    new bootstrap.Popover(popoverTriggerEl);
  });
});

// Ensure datepickers initialize after htmx swaps
document.addEventListener('htmx:afterSwap', initDatepicker);
