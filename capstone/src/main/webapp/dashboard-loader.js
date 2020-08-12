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
    fetch("/dashboard-handler", {method: "POST", headers: new Headers({id_token}), body: classroomData}).then((resp) => {
        if (resp.ok){
            getClassrooms();
        } else {
            console.log("Error has occured");
        }
    });
}

function getClassrooms() {
    fetch("/dashboard-handler", {method: "GET", headers: new Headers({id_token})}).then(response => response.json()).then((classroomsList) => {
        const classroomElement = document.getElementById("classroom-container");
        classroomElement.innerHTML = "";
        for (classroom of classroomsList) {
            classroomElement.appendChild(createClassroomDivElement(classroom));
        };
    });
}

function createClassroomDivElement(classroom) {
    const divElement = document.createElement("div");
    const h2Element = document.createElement("h2");
    const liElement = document.createElement("li");
    const pElement = document.createElement("p");
    const aElement = document.createElement("a");
    
    h2Element.innerText = classroom.entity.propertyMap.subject;
    pElement.innerText = "Classroom ID : " + classroom.entity.key.id;
    liElement.innerText = "Teacher ID : " + classroom.entity.propertyMap.teacher.id;
    aElement.href = "form.html";
    aElement.innerText = "Classroom Link";

    divElement.appendChild(h2Element);
    divElement.appendChild(liElement);
    divElement.appendChild(pElement);
    divElement.appendChild(aElement);

    return divElement;
}
