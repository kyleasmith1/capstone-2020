document.getElementById("signout").prepend(dynamicButton);
window.addEventListener('authorized', getLessons);

const FORM = "form";
const VIDEO = "video";
const CONTENT = "content";
const IMAGE = "image";
const ROOM_ID = "room_id";
const ACTION = "action";
const FOLLOW = "follow";
const UNFOLLOW = "unfollow";

const followEndpoint = createEndpoint("/follow", FOLLOW);
const unfollowEndpoint = createEndpoint("/follow", UNFOLLOW);
const lessonEndpoint = createEndpoint("/lesson", UNFOLLOW);

function createEndpoint(file_name, follow_status) {
    const endpoint = new URL(file_name, window.location.href);
    endpoint.searchParams.set(ROOM_ID, getRoomId());
    endpoint.searchParams.set(ACTION, follow_status);
    return endpoint;
}

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
            return fetch(lessonEndpoint, {method: "POST", headers: new Headers({ID_TOKEN}), body: formData});   
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
    fetch(lessonEndpoint, {method: "POST", headers: new Headers({ID_TOKEN}), body: videoData}).then((resp) => {
        if (resp.ok){
            getLessons();
        } else {
            alert("Error has occured");
        }
    });
}

function createImage(title, description, url) {
    var imageData = JSON.stringify({"type": IMAGE, "title": "title", "description": "description", "url": "url"});
    fetch(lessonEndpoint, {method: "POST", headers: new Headers({ID_TOKEN}), body: imageData}).then((resp) => {
        if (resp.ok){
            getLessons();
        } else {
            alert("Error has occured");
        }
    });
}

function createContent(title, description, content, urls) {
    var contentData = JSON.stringify({"type": CONTENT, "title": "title", "description": "description", "content": "content", "urls": urls.split(", ")});
    fetch(lessonEndpoint, {method: "POST", headers: new Headers({ID_TOKEN}), body: contentData}).then((resp) => {
        if (resp.ok){
            getLessons();
        } else {
            alert("Error has occured");
        }
    });
}

function getLessons() {
    fetch(lessonEndpoint, {method: "GET", headers: new Headers({ID_TOKEN})}).then(response => response.json()).then((lessonsList) => {
        const lessonElement = document.getElementById("lesson-container");
        lessonElement.innerHTML = "";
        for (lesson of lessonsList) {
            lessonElement.appendChild(createLessonDivElement(lesson));
        };
    });
}

function getRoomId() {
    return new URL(window.location.href).searchParams.get("room_id");
}

function followRoom() {
    return fetch(followEndpoint, {method: "POST", headers: new Headers({ID_TOKEN})}); 
}

function unfollowRoom() {
    return fetch(unfollowEndpoint, {method: "POST", headers: new Headers({ID_TOKEN})});
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