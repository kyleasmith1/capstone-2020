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
        var result = resp.result;
        if (result.error && result.error.status) {
            // The API encountered a problem before the script started executing
            appendPre('Error calling API:');
            appendPre(JSON.stringify(result, null, 2));
        } else if (result.error) {
            // The API executed, but the script returned an error.
            var error = result.error.details[0];
            appendPre('Script error message: ' + error.errorMessage);

            if (error.scriptStackTraceElements) {
                appendPre('Script error stacktrace:');
                for (var i = 0; i < error.scriptStackTraceElements.length; i++) {
                    var trace = error.scriptStackTraceElements[i];
                    appendPre('\t' + trace.function + ':' + trace.lineNumber);
                }
            }
        } else { 
            formData = JSON.stringify(result.response.result);
            fetch("/form-handler", {method: "POST", body: formData}).then((response) => {
                console.log(response)
                console.log(JSON.stringify(response));
                return response.text();
            });   
        }
    });
}

function getForms() {
    fetch("/form-handler").then(response => response.json()).then((formsList) => {
        console.log(formsList);
        const formElement = document.getElementById("form-container");
        formsList.forEach((form) => {
            formElement.appendChild(createTeacherFormElement(form.editURL));
            formElement.appendChild(createStudentFormElement(form.URL)); 
        });
    });
}

function createTeacherFormElement(editURL) { 
    const aElement = document.createElement("a");
    aElement.innerText = "Form Edit Page";
    aElement.href = editURL;
    return aElement;
}

function createStudentFormElement(URL) {
    const iframeElement = document.createElement("iframe");
    iframeElement.src = URL;
    return iframeElement;
}



