document.body.prepend(dynamicButton);

function callScriptFunction() {
    var scriptId = "1Z_td2xr1Hq9loDzSdojcCS_3qFwKJR3apBuR2zmcyUVpdhqvfJWyMMYZ";
  
    // Call the Apps Script API run method
    //   'scriptId' is the URL parameter that states what script to run
    //   'resource' describes the run request body (with the function name
    //              to execute)
    gapi.client.script.scripts.run({
    'scriptId': scriptId,
    'resource': {
        "function": "createForm",
        "parameters": [],
        "devMode": true
    }
    }).then(function(resp) {
        if (resp.result?.error?.status != null) {
            // The API encountered a problem before the script started executing
            console.log('Error calling API: ' + result);
        } else if (resp.result?.error != null) {
            // The API executed, but the script returned an error.
            console.log("Script error message: " + result.error);
        } else { 
            fetch("/form-handler", {method: "POST", body: JSON.stringify(resp.result.response.result)});  
        }
    }).then((resp) => {
        if (resp.ok){
            getForms();
        } else {
            alert("Error has occured");
        }
    });
}

function getForms() {
    fetch("/form-handler").then(response => response.json()).then((formsList) => {
        const formElement = document.getElementById("form-container");
        formElement.innerHTML = "";
        for (form of formsList) {
            formElement.appendChild(createTeacherFormElement(form.editUrl));
            formElement.appendChild(createStudentFormElement(form.Url)); 
        };
    });
}

function createTeacherFormElement(editUrl) { 
    const aElement = document.createElement("a");
    aElement.innerText = "Form Edit Page";
    aElement.href = editUrl;
    return aElement;
}

function createStudentFormElement(Url) {
    const iframeElement = document.createElement("iframe");
    iframeElement.src = Url;
    return iframeElement;
}