function createClassroom() {
    var auth2 = gapi.auth2.getAuthInstance();
    var name = "";
    var email = "";
    if (auth2.isSignedIn.get()) {
        var name = auth2.currentUser.get().getBasicProfile().getName();
        var email = auth2.currentUser.get().getBasicProfile().getEmail();
    }

    var subject = document.querySelector('#subject').value;
    var classroomData = JSON.stringify({ "name": name, "email": email, "subject": subject });
    fetch("/dashboard-handler", {method: "POST", body: classroomData}).then((resp) => {
        if (resp.ok){
            getClassrooms();
        } else {
            alert("Error has occured");
        }
    });
}

function getClassrooms() {
    fetch("/dashboard-handler").then(response => response.json()).then((classroomsList) => {
        const classroomElement = document.getElementById("classroom-container");
        classroomElement.innerHTML = "";
        classroomsList.forEach((classroom) => {
            console.log(classroom);
            formElement.appendChild(createTeacherFormElement(form.editURL));
            formElement.appendChild(createStudentFormElement(form.URL)); 
        });
    });
}

// function createClassroomElement(classroom) { 
//     const divElement = document.createElement("div");
//     const h2Element = document.createElement("h2");
//     const imgElement = document.createElement("img");
//     const pElement = document.createElement("p");
    
//     h2Element.innerText = memepost.author;
//     pElement.innerText = memepost.description;
//     imgElement.src = memepost.imageUrl;

//     divElement.appendChild(h2Element);
//     divElement.appendChild(imgElement);
//     divElement.appendChild(pElement);

//     return divElement;
// }


// function createImageContainer(memepost) {
//     const divElement = document.createElement("div");
//     const h2Element = document.createElement("h2");
//     const imgElement = document.createElement("img");
//     const pElement = document.createElement("p");
    
//     h2Element.innerText = memepost.author;
//     pElement.innerText = memepost.description;
//     imgElement.src = memepost.imageUrl;

//     divElement.appendChild(h2Element);
//     divElement.appendChild(imgElement);
//     divElement.appendChild(pElement);

//     return divElement;
// }