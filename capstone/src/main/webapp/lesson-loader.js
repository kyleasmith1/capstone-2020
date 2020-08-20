document.getElementById("signout").prepend(dynamicButton);
window.addEventListener('authorized', getLessons);

const FORM = "form";
const VIDEO = "video";
const CONTENT = "content";
const IMAGE = "image";

const queryString = "/lesson?room_id=" + getRoomId() + "&action=" + getJoinStatus();

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
            console.log('Error calling API: ' + result);
        } else if (resp.result?.error != null) {
            console.log("Script error message: " + result.error);
        } else { 
            var formData = JSON.stringify({"type": FORM, "title": "title", "description": "description", "editUrl": resp.result.response.result.editUrl, "url": resp.result.response.result.url});          
            return fetch(queryString, {method: "POST", headers: new Headers({ID_TOKEN}), body: formData});  
        }
    }).then((resp) => {
        if (resp.ok){
            getLessons();
        } else {
            alert("Error has occured");
        }
    });
}

function createVideo(title, description, url) {
    var videoData = JSON.stringify({"type": VIDEO, "title": "title", "description": "description", "url": "url"});
    fetch(queryString, {method: "POST", headers: new Headers({ID_TOKEN}), body: videoData}).then((resp) => {
        if (resp.ok){
            getLessons();
        } else {
            alert("Error has occured");
        }
    });
}

function createImage(title, description, url) {
    var imageData = JSON.stringify({"type": IMAGE, "title": "title", "description": "description", "url": "url"});
    fetch(queryString, {method: "POST", headers: new Headers({ID_TOKEN}), body: imageData}).then((resp) => {
        if (resp.ok){
            getLessons();
        } else {
            alert("Error has occured");
        }
    });
}

function createContent(title, description, content, urls) {
    var contentData = JSON.stringify({"type": CONTENT, "title": "title", "description": "description", "content": "content", "urls": urls.split(", ")});
    fetch(queryString, {method: "POST", headers: new Headers({ID_TOKEN}), body: contentData}).then((resp) => {
        if (resp.ok){
            getLessons();
        } else {
            alert("Error has occured");
        }
    });
}

function getLessons() {
    fetch(queryString, {method: "GET", headers: new Headers({ID_TOKEN})}).then(response => response.json()).then((lessonsList) => {
        const lessonElement = document.getElementById("lesson-container");
        lessonElement.innerHTML = "";
        for (lesson of lessonsList) {
            lessonElement.appendChild(createLessonDivElement(lesson));
        };
    });
}

function joinRoom() {
    console.log("Room joined!");
    return fetch("/join?room_id=" + getRoomId() + "&action=join", {method: "POST", headers: new Headers({ID_TOKEN})}); 
}

function unjoinRoom() {
    console.log("User has left the room!");
    return fetch("/join?room_id=" + getRoomId() + "&action=unjoin",
    {method: "POST", headers: new Headers({ID_TOKEN})});
}

function getRoomId() {
    return new URL(window.location.href).searchParams.get("room_id");
}

function getJoinStatus() {
    var action = new URL(window.location.href).searchParams.get("action")
    if (action == null) {
        return "unjoin";
    }
    return action;
}

function createLessonDivElement(lesson) {
    let domparser = new DOMParser();
    let doc = domparser.parseFromString(`
            <div class="card border-danger margin margin-left">
                <img class="card-img-top" src="/assets/soundwave.svg" alt="Lesson Card">
                <div class="card-body text-center">
                    <h5 class="card-title" id="lesson-title"></h5>
                    <a class="card-text small-text" href="#">Kyle Smith</a>
                    <div class="card-text small-text">Followers: Infinite</div>
                    <div class="card-text small-text">Tag(s): </div>
                    <div class="small-spacing-bottom"></div>
                    <button type="button" class="btn btn-default" onclick="window.location.href='#'">Open</button>
                </div>
            </div>
            `, "text/html");
            
    doc.getElementById("lesson-title").innerText = lesson.entity.propertyMap.title
    return doc.body;
}