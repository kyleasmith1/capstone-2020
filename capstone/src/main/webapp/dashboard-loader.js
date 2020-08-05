function createClassroom() {
    let subject = document.querySelector('#subject').value;
    var classroomData = JSON.stringify({ "subject": subject });
    console.log(subject);
    console.log(classroomData);
    fetch("/dashboard-handler", {method: "POST", body: classroomData}).then((resp) => {
        console.log(resp);
    });

}

// function getClassroom() {
//     console.log("Getting Classroom");    
// }



// .then((response) =>{
//         console.log(response);
//         if (resp.ok){
//             getClassroom();
//         } else {
//             alert("Error has occured");
//         }
//     });