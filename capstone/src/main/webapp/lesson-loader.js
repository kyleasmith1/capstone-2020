document.getElementById("signout").prepend(dynamicButton);

const FORM = "form";
const VIDEO = "video";
const CONTENT = "content";
const IMAGE = "image";

// Will take out any hard coded values in a future PR
function createForm(title, description) {
    var scriptId = config.SCRIPT_ID;

    gapi.client.script.scripts.run({
    'scriptId': scriptId,
    'resource': {
        "function": "createForm",
        "parameters": [],
        "devMode": false
    }
    }).then(function(resp) {
        if (resp.result?.error?.status != null) {
            console.error('Error calling API: ' + result);
        } else if (resp.result?.error != null) {
            console.log("Script error message: " + result.error);
        } else { 
            var formData = JSON.stringify({"type": FORM, "title": "title", "description": "description", "editUrl": resp.result.response.result.editUrl, "url": resp.result.response.result.url});            
            return fetch("/lesson", {method: "POST", headers: new Headers({ID_TOKEN}), body: formData});  
        }
    }).then((resp) => {
        if (resp.ok){
            console.log("Form created!");
        } else {
            alert("Error has occured");
        }
    });
}

function createVideo(title, description, url) {
    var videoData = JSON.stringify({"type": VIDEO, "title": "title", "description": "description", "url": "url"});
    fetch("/lesson", {method: "POST", headers: new Headers({ID_TOKEN}), body: videoData}).then((resp) => {
        if (resp.ok){
            console.log("Video created!");
        } else {
            alert("Error has occured");
        }
    });
}

function createImage(title, description, url) {
    var imageData = JSON.stringify({"type": IMAGE, "title": "title", "description": "description", "url": "url"});
    fetch("/lesson", {method: "POST", headers: new Headers({ID_TOKEN}), body: imageData}).then((resp) => {
        if (resp.ok){
            console.log("Image created!");
        } else {
            alert("Error has occured");
        }
    });
}

function createContent(title, description, content, urls) {
    var contentData = JSON.stringify({"type": CONTENT, "title": "title", "description": "description", "content": "content", "urls": urls.split(", ")});
    fetch("/lesson", {method: "POST", headers: new Headers({ID_TOKEN}), body: contentData}).then((resp) => {
        if (resp.ok){
            console.log("Content created!");
        } else {
            alert("Error has occured");
        }
    });
}