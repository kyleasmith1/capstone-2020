document.body.prepend(dynamicButton);
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

function createClassroomDivElement(classroom) {
    let domparser = new DOMParser();
    let doc = domparser.parseFromString(`
            <div class="classroom">
                <h2>${classroom.entity.propertyMap.subject}</h2>
                <p><a href="form.html">Classroom Link</a></p>
            </div>
            `, "text/html");
    return doc.body;
}
