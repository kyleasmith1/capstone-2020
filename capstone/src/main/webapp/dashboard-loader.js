document.getElementById("signout").prepend(dynamicButton);
window.addEventListener('authorized', getClassrooms);

function createClassroom(subject) {
    var auth2 = gapi.auth2.getAuthInstance();
    var name = "";
    var email = "";

    if (auth2.isSignedIn.get()) {
        name = auth2.currentUser.get().getBasicProfile().getName();
        email = auth2.currentUser.get().getBasicProfile().getEmail();
    }

    var classroomData = JSON.stringify({ "nickname": name, "userId": email, "subject": subject });
    fetch("/dashboard", {method: "POST", headers: new Headers({ID_TOKEN}), body: classroomData}).then((resp) => {
        if (resp.ok){
            getClassrooms();
        } else {
            console.log("Error has occured");
        }
    });
}

function getClassrooms() {
    fetch("/dashboard", {method: "GET", headers: new Headers({ID_TOKEN})}).then(response => response.json()).then((classroomsList) => {
        const classroomElement = document.getElementById("classroom-container");
        classroomElement.innerHTML = "";
        for (classroom of classroomsList) {
            classroomElement.appendChild(createClassroomDivElement(classroom));
        };
    });
}

// TODO: Replace dummy values with real values in future PR
function createClassroomDivElement(classroom) {
    let domparser = new DOMParser();
    let doc = domparser.parseFromString(`
            <div class="card margin margin-left">
                <img class="card-img-top" src="/assets/soundwave.svg" alt="Room Card">
                <div class="card-body text-center">
                    <h5 class="card-title">${classroom.entity.propertyMap.subject}</h5>
                    <a class="card-text small-text" href="#">Kyle Smith</a>
                    <div class="card-text small-text">Followers: Infinite</div>
                    <div class="card-text small-text">Tag(s): </div>
                    <div class="small-spacing-bottom"></div>
                    <button type="button" class="btn btn-default" onclick="window.location.href='form.html'">View</button>
                </div>
            </div>
            `, "text/html");
    return doc.body;
}