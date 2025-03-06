import { Form,MarkdownRenderer } from "@bpmn-io/form-js-viewer";
const form = new Form({
    container: document.querySelector("#form-container"),
});

let formId=null;
let modelSchema = document.getElementById("schemaId");

if(modelSchema!=null){

    const schema = JSON.parse(modelSchema.getAttribute("form-schema"));

        const data = JSON.parse(modelSchema.getAttribute("form-data"));
    if(data!=null) {

        await form.importSchema(schema,data);

    }else {

        await form.importSchema(schema);
    }

    formId =schema.id

}
const markdownRenderer = new MarkdownRenderer();
document.querySelectorAll(".fjs-form-text, label, .fjs-form-field-text").forEach((element) => {
    element.innerHTML = markdownRenderer.render(element.innerHTML);
});



// Listen for the submit event
form.on('submit', async ({ data, errors }) => {
    if (errors && Object.keys(errors).length > 0) {
        console.error("Validation Errors:", errors);
        alert("Please fix validation errors before submitting.");
        return;
    }

    //console.log("Form Data:", data); // Debugging

    try {
        var url='/submit-form/12345';
        if(formId.includes("_") && formId.split('_')[0]=='Start'){
                url='/process/'+formId.split('_')[1];
        }

        const response = await fetch(url, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            //window.location.reload();
            alert("Form submitted successfully!");
        } else {
            const errorData = await response.json();
            console.error("Error:", errorData);
            alert("Failed to submit form.");
        }

    } catch (error) {
        console.error("Submission error:", error);
        alert("Submission failed.");
    }
});