document.body.prepend(dynamicButton);

function createClassroom(subject) {
    var auth2 = gapi.auth2.getAuthInstance();
    var name = "";
    var email = "";
    var id_token = "";
    if (auth2.isSignedIn.get()) {
        name = auth2.currentUser.get().getBasicProfile().getName();
        email = auth2.currentUser.get().getBasicProfile().getEmail();
        id_token = gapi.auth2.getAuthInstance().currentUser.get().getAuthResponse().id_token;
    }

    var classroomData = JSON.stringify({ "nickname": name, "userId": email, "subject": subject });
<<<<<<< HEAD
    fetch("/dashboard-handler", {method: "POST", headers: new Headers({id_token}), body: classroomData}).then((resp) => {
=======
    fetch("/dashboard", {method: "POST", body: classroomData}).then((resp) => {
>>>>>>> 53ad7dc8f1095bd19754945bbf65d09d33716f6a
        if (resp.ok){
            getClassrooms();
        } else {
            console.log("Error has occured");
        }
    });
}

function getClassrooms() {
<<<<<<< HEAD
    fetch("/dashboard-handler", {method: "GET", headers: new Headers({id_token})}).then(response => response.json()).then((classroomsList) => {
=======
    fetch("/dashboard").then(response => response.json()).then((classroomsList) => {
>>>>>>> 53ad7dc8f1095bd19754945bbf65d09d33716f6a
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
