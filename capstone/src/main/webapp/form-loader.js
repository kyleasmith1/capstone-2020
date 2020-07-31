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
            fetch("/form-handler", {method: "POST", body: formData});  
        }
    });
}