const accordionButtons = document.querySelectorAll(".accordion-button");

accordionButtons.forEach(button => {
    button.addEventListener("click", function () {
        const parentItem = this.parentElement.parentElement;
        const content = parentItem.querySelector(".accordion-content");
        const allItems = document.querySelectorAll(".accordion-item");

        allItems.forEach(item => {
            if (item !== parentItem) {
                item.classList.remove("active");
                const otherContent = item.querySelector(".accordion-content");
                otherContent.style.maxHeight = "0";
                otherContent.style.opacity = "0";
                otherContent.style.padding = "0 15px";
            }
        });

        if (parentItem.classList.contains("active")) {
            content.style.maxHeight = "0";
            content.style.opacity = "0";
            content.style.padding = "0 15px";
        } else {
            content.style.maxHeight = content.scrollHeight + "px";
            content.style.opacity = "1";
            content.style.padding = "15px";
        }

        parentItem.classList.toggle("active");
    });
});