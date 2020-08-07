function createClassroom() {
    var auth2 = gapi.auth2.getAuthInstance();
    var name = "";
    var email = "";
    if (auth2.isSignedIn.get()) {
        var name = auth2.currentUser.get().getBasicProfile().getName();
        var email = auth2.currentUser.get().getBasicProfile().getEmail();
    }

    var subject = document.querySelector('#subject').value;
    var classroomData = JSON.stringify({ "nickname": name, "userId": email, "subject": subject });
    fetch("/dashboard-handler", {method: "POST", body: classroomData}).then((resp) => {
        if (resp.ok){
            getClassrooms();
        } else {
            console.log("Error has occured");
        }
    });
}

function getClassrooms() {
    fetch("/dashboard-handler").then(response => response.json()).then((classroomsList) => {
        const classroomElement = document.getElementById("classroom-container");
        classroomElement.innerHTML = "";
        classroomsList.forEach((classroom) => {
            console.log(classroom);
            classroomElement.appendChild(createClassroomDivElement(classroom));
        });
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
