var CLIENT_ID = '914921573408-h9jd30r94b1a3kqr5dg2t7bq9v670sbj.apps.googleusercontent.com';
var API_KEY = 'AIzaSyCbowMYfGfEdWI-aWtY2OkrcTwH9ad2iGw';
var DISCOVERY_DOCS = ["https://script.googleapis.com/$discovery/rest?version=v1"];
var SCOPES = 'https://www.googleapis.com/auth/forms';

document.getElementById("auth").appendChild(createLoginButton());
document.getElementById("auth").appendChild(createLogoutButton());
var authorizeButton = document.getElementById('authorize_button');
var signoutButton = document.getElementById('signout_button');

/**
*  On load, called to load the auth2 library and API client library.
*/
function handleClientLoad() {
    gapi.load('client:auth2', initClient);
}

/**
*  Initializes the API client library and sets up sign-in state
*  listeners.
*/
function initClient() {
    gapi.client.init({
        apiKey: API_KEY,
        clientId: CLIENT_ID,
        discoveryDocs: DISCOVERY_DOCS,
        scope: SCOPES
    }).then(function () {
        // Listen for sign-in state changes.
        gapi.auth2.getAuthInstance().isSignedIn.listen(updateSigninStatus);
        
        // Handle the initial sign-in state.
        updateSigninStatus(gapi.auth2.getAuthInstance().isSignedIn.get());
        authorizeButton.onclick = handleAuthClick;
        signoutButton.onclick = handleSignoutClick;
    }, function(error) {
        appendPre(JSON.stringify(error, null, 2));
    });
}

/**
*  Called when the signed in status changes, to update the UI
*  appropriately. After a sign-in, the API is called.
*/
function updateSigninStatus(isSignedIn) {
    if (isSignedIn) {
        authorizeButton.style.display = 'none';
        signoutButton.style.display = 'block';
    } else {
        authorizeButton.style.display = 'block';
        signoutButton.style.display = 'none';
    }
}

function handleAuthClick() {
    gapi.auth2.getAuthInstance().signIn();
}

function handleSignoutClick() {
    gapi.auth2.getAuthInstance().signOut();
}

function createLoginButton(){
    const loginButtonElement = document.createElement('button');
    loginButtonElement.id = 'authorize_button';
    loginButtonElement.style.display = "none";
    loginButtonElement.innerText = "Authorize";
    return loginButtonElement;
}

function createLogoutButton(){
    const logoutButtonElement = document.createElement('button');
    logoutButtonElement.id = 'signout_button';
    logoutButtonElement.style.display = "none";
    logoutButtonElement.innerText = "Sign Out";
    return logoutButtonElement;
}

/**
* Append a pre element to the body containing the given message
* as its text node. Used to display the results of the API call.
*
* @param {string} message Text to be placed in pre element.
*/
function appendPre(message) {
    var pre = document.getElementById('content');
    var textContent = document.createTextNode(message + '\n');
    pre.appendChild(textContent);
}