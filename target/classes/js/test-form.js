import { Form,MarkdownRenderer } from "@bpmn-io/form-js-viewer";


const form = new Form({
    container: document.querySelector("#form-container"),
});

let formId=null;
let modelSchema = document.getElementById("schemaId");

if(modelSchema!=null){

    const schema = JSON.parse(modelSchema.getAttribute("form-schema"));

    //const tableData = JSON.parse(modelSchema.getAttribute("form-data"));
    const tableData = [
        { serialNumber: "1", name: "John Doe", discription: "Software Engineer" },
        { serialNumber: "2", name: "Jane Smith", discription: "Project Manager" },
        { serialNumber: "3", name: "Alice Johnson", discription: "UX Designer" }
    ];

        await form.importSchema(schema,tableData);

}

