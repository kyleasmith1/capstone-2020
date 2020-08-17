document.getElementById("signout").prepend(dynamicButton);
window.addEventListener('authorized', getLessons);

function createLesson(type, title, description) {
    console.log("Title:" + title);
    console.log("Description:" + description);

    var lessonData = JSON.stringify({"title": title, "description": description});
    console.log("LESSON DATA: " + lessonData);
    fetch("/lesson", {method: "POST", headers: new Headers({ID_TOKEN}), body: lessonData}).then((resp) => {
        if (resp.ok){
            console.log("Lesson created!");
        } else {
            console.log("Error has occured");
        }
    });
}

// TODO: Add GET on the next PR
function getLessons() {
    fetch("/lesson", {method: "GET", headers: new Headers({ID_TOKEN})}).then(response => response.json()).then((lessonsList) => {
        
    });
}

function createLessonDivElement(lesson) {
    let domparser = new DOMParser();
    let doc = domparser.parseFromString(``, "text/html");
    return doc.body;
}