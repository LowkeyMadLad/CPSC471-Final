console.log("loaded");
const playerStats = document.getElementById("user-profile");

document.getElementById("create-account-submit").addEventListener("click", onAccountCreation, false);
document.getElementById("login-submit").addEventListener("click", onAccountLogin, false);
// fetch("https://localhost:433//account/getaccount/" + playerUsername, {
//             method: "GET",
//             headers: {
//                 "Content-type": "application/json; charset=UTF-8"
//             }
//         }) .then((response) => response.json()).then((json) => {
//             console.log(json)
//             playerStats.innerHTML = formatHTML(json.username, json.displayname, json.wins, json.losses, json.mmr)
//         });

// function formatHTML(username, displayname, wins, losses, mmr){
//     var htmlCard = `
//     <div id="player-stats" class="player-stats-format">

//     </div>
//     `
// }
// create account by storing variables
// pass via post method to DB
// Get response, if success, reload page to login.

async function onAccountCreation(){
    var username = document.getElementById("create-username").value;
    var password = document.getElementById("create-password").value;
    var displayname = document.getElementById("create-displayName").value;
    console.log(username)
    console.log(password)
    console.log(displayname)

    let list = [username, password, displayname];
    try {
        var string = await postAccountCreate(list);
    } catch (error) {
        console.error(error)
        return;
    }
    console.log(string);
    document.getElementById("create-fields").innerHTML = formatConfirmReload();
    window.alert("Account Creation was Successful")
    location.reload();
}

function formatConfirmReload() {
    htmlCard = `
    <p style="font-size: 24; text-align: center;">Account Successfully Created. Reloading Page.</p>
    `
    return htmlCard
}

async function postAccountCreate(list){
    let obj;
    const result = fetch("https://localhost:433/account/create", {
        method: "POST",
        body: JSON.stringify(list),
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        }
    });

    obj = (await result).json();

    console.log(obj);

    return obj;
}

async function onAccountLogin(){
    var username = document.getElementById("login-username").value;
    var password = document.getElementById("login-password").value;
    let list = [username, password];

    var result = await loginAccount(list);
    var loggedIn = JSON.parse(result);
    if (loggedIn) {
        console.log("Account Login successful");
        document.getElementById("loginAndSignUp").style.display = "none";
        accountDetails = await getAccountInfo(username);
        console.log(accountDetails);
        infodisplay = document.getElementById("account-info");
        infodisplay.innerHTML += formatLogin(username, accountDetails[1], accountDetails[2], accountDetails[3], accountDetails[4]);
        matchHistoryDetails(username);
    } else {
        window.alert("Login was unsuccessful");
    }
    
}
// get uuid's
// call replaygame

async function matchHistoryDetails(username){
    var uuids = await getUUIDs(username)
    matchHistory = document.getElementById("match-history");
    for (let i = 0; i < uuids.length; i++) {
        var gameString = await getGamesById(uuids[i]);
        matchHistory.innerHTML += formatHistory(uuids[i], gameString);
    }
}



async function getGamesById(uuid){
    let obj;
    const result = fetch("https://localhost:433/account/gamesbyid/" + uuid, {
        method: "GET",
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        }
    });

    obj = (await result).json();

    console.log(obj);

    return obj;
}

async function getUUIDs(username){
    let obj;
    const result = fetch("https://localhost:433/account/games/" + username, {
        method: "GET",
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        }
    });

    obj = (await result).json();

    console.log(obj);

    return obj;
}

async function getAccountInfo(username){
    let obj;
    const result = fetch("https://localhost:433/account/getaccount/" + username, {
        method: "GET",
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        }
    });

    obj = (await result).json();

    console.log(obj);

    return obj;
}

function formatHistory(uuid, gamelog){
    htmlCard = `
    <div class="history-box">
        <p class="history-align">UUID: ${uuid}</p>
        <div class="action-box history-align">
            ${gamelog}
        </div>
    </div>
    `
    return htmlCard
}

function formatLogin(username, displayname, wins, losses, mmr){
    htmlCard = `
    <div class="player-stats">
        <p style="font-size: 24px;">Player Username: ${username}</p>
        <p style="font-size: 24px;">Player Display Name: ${displayname}</p>
        <p style="font-size: 24px;">Player Wins: ${wins}</p>
        <p style="font-size: 24px;">Player Losses: ${losses}</p>
        <p style="font-size: 24px;">Player MMR: ${mmr}</p>
    </div>
    `
    return htmlCard
}



async function loginAccount(list){
    let obj;
    const result = fetch("https://localhost:433/account/login", {
        method: "POST",
        body: JSON.stringify(list),
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        }
    });

    obj = (await result).json();

    console.log(obj);

    return obj;
}


// check account is successfully logged in. 

// loginBtn.addEventListener("click", () => {
// //will add functionality once we know how were going to do it 
//   const username = document.getElementById("username").value;
//   const password = document.getElementById("password").value;

//   if (username && password) {
//     // Display user data on the page
//     document.getElementById("user-name").textContent = username;
//     document.getElementById("user-mmr").textContent = "1200";
//     document.getElementById("user-games-played").textContent = "50";
//     document.getElementById("user-wins").textContent = "30";
//     document.getElementById("user-losses").textContent = "20";

//     // Hide login form and show user profile
//     loginForm.classList.add("hidden");
//     userProfile.classList.remove("hidden");
//   }
// });

// logoutBtn.addEventListener("click", () => {
//   // Clear the form inputs
//   document.getElementById("username").value = "";
//   document.getElementById("password").value = "";

//   // Hide user profile and show login form
//   userProfile.classList.add("hidden");
//   loginForm.classList.remove("hidden");
// });
