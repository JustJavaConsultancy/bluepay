document.addEventListener('DOMContentLoaded', function () {
    let startDate = null;
    let endDate = null;

    const startDatePicker = document.querySelector(".start-date");
    const endDatePicker = document.querySelector(".end-date");
    const downloadButton = document.querySelector('.download-button'); // Store in a variable if needed

    const tableRows = document.querySelectorAll("#orders-table-body tr");
    const tableDataArray = [];

    tableRows.forEach(row => {
        const rowData = [];
        row.querySelectorAll("td").forEach(cell => {
            rowData.push(cell.textContent.trim());
        });
        tableDataArray.push(rowData);
    });

    downloadButton.addEventListener("click", handleDownload);

    if (startDatePicker) {
        startDatePicker.addEventListener("click", () =>
            createDatePicker(startDatePicker, "start")
        );
    }

    if (endDatePicker) {
        endDatePicker.addEventListener("click", () =>
            createDatePicker(endDatePicker, "end")
        );
    }

    function createDatePicker(element, type) {
        const existingPicker = document.querySelector(".date-picker-container");
        if (existingPicker) existingPicker.remove();

        const datePickerContainer = document.createElement("div");
        datePickerContainer.className = "date-picker-container";

        const dateInput = document.createElement("input");
        dateInput.type = "date";
        dateInput.className = "date-input";

        if (type === "start" && startDate) {
            dateInput.value = startDate;
        } else if (type === "end" && endDate) {
            dateInput.value = endDate;
        }

        dateInput.addEventListener("change", (e) => {
            const selectedDate = e.target.value;
            if (type === "start") {
                startDate = selectedDate;
            } else {
                endDate = selectedDate;
            }

            const pTag = element.querySelector("span");
            if (pTag) {
                pTag.textContent = formatDateForDisplay(selectedDate) || (type === "start" ? "Start Date" : "End Date");
            }

            datePickerContainer.remove();
        });

        const closeButton = document.createElement("button");
        closeButton.textContent = "×";
        closeButton.className = "date-picker-close";
        closeButton.addEventListener("click", () => datePickerContainer.remove());

        datePickerContainer.appendChild(dateInput);
        datePickerContainer.appendChild(closeButton);

        const rect = element.getBoundingClientRect();
        datePickerContainer.style.position = "absolute";
        datePickerContainer.style.top = `${rect.bottom + window.scrollY}px`;
        datePickerContainer.style.left = `${rect.left + window.scrollX}px`;

        document.body.appendChild(datePickerContainer);

        dateInput.focus();
    }

    function formatDateForDisplay(dateString) {
        if (!dateString) return "";
        const date = new Date(dateString);
        return date.toLocaleDateString("en-US", { year: "numeric", month: "short", day: "numeric" });
    }
    function handleDownload() {
        // Create CSV content
        let csvContent = "data:text/csv;charset=utf-8,";

        // Add headers
        csvContent += "ID,Customer,Product,Quantity,Amount,Timestamp,Email,Phone\n";

        // Add data rows
        tableDataArray.forEach((order) => {
            const row = [
                order[0],
                order[1],
                order[2],
                order[3],
                order[4],
                order[5],
                order[6],
                order[7],
            ].join(",");
            csvContent += row + "\n";
        });

        // Create download link
        const encodedUri = encodeURI(csvContent);
        const link = document.createElement("a");
        link.setAttribute("href", encodedUri);
        link.setAttribute("download", "orders_export.csv");
        document.body.appendChild(link);

        // Trigger download
        link.click();

        // Clean up
        document.body.removeChild(link);
    }
});
