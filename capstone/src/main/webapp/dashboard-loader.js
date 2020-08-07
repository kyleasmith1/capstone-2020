document.body.prepend(dynamicButton);

function createClassroom() {
    var auth2 = gapi.auth2.getAuthInstance();
    var name = "";
    var email = "";
    if (auth2.isSignedIn.get()) {
        name = auth2.currentUser.get().getBasicProfile().getName();
        email = auth2.currentUser.get().getBasicProfile().getEmail();
    }

    var subject = document.querySelector('#subject').value;
    var classroomData = JSON.stringify({ "nickname": name, "userId": email, "subject": subject });
    fetch("/dashboard-handler", {method: "POST", body: classroomData}).then((resp) => {
        if (resp.ok){
            console.log("Classroom Created!");
        } else {
            console.log("Error has occured");
        }
    });
}
