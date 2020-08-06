var CLIENT_ID = config.CLIENT_ID;
var API_KEY = config.API_KEY;
var DISCOVERY_DOCS = ["https://script.googleapis.com/$discovery/rest?version=v1"];
var SCOPES = 'https://www.googleapis.com/auth/forms';

var [dynamicButton, authorizeButton, signoutButton] = createDynamicSigninButton();

function createDynamicSigninButton() {
    console.log("Creating buttons");
    var dynamicButton = document.createElement("div");
    var loginButton = dynamicButton.appendChild(createLoginButton());
    var logoutButton = dynamicButton.appendChild(createLogoutButton());
    // document.getElementById("auth").appendChild(loginButton);
    // document.getElementById("auth").appendChild(logoutButton);
    return [dynamicButton, loginButton, logoutButton];
}


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
        // Sign in and listen for sign-in state changes.
        updateSigninStatus(gapi.auth2.getAuthInstance().isSignedIn.get());
        gapi.auth2.getAuthInstance().isSignedIn.listen(updateSigninStatus);
        
        authorizeButton.onclick = gapi.auth2.getAuthInstance().signIn;
        signoutButton.onclick = gapi.auth2.getAuthInstance().signOut;
    }, function(error) {
        console.log("Error has occured: " + error);
    });
}

/**
*  Called when the signed in status changes, to update the UI
*  appropriately.
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
