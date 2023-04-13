const playerStats = document.getElementById("user-profile");
const playerUsername = document.getElementById("session-username").getAttribute("Value");


fetch("https://localhost:433//account/getaccount/" + playerUsername, {
            method: "GET",
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            }
        }) .then((response) => response.json()).then((json) => {
            console.log(json)
            playerStats.innerHTML = formatHTML(json.username, json.displayname, json.wins, json.losses, json.mmr)
        });

function formatHTML(username, displayname, wins, losses, mmr){
    var htmlCard = `
    <div id="player-stats" class="player-stats-format">
        <p style="font-size: 24px;">Player Username: ${username}</p>
        <p style="font-size: 24px;">Player Display Name: ${displayname}</p>
        <p style="font-size: 24px;">Player Wins: ${wins}</p>
        <p style="font-size: 24px;">Player Losses: ${losses}</p>
        <p style="font-size: 24px;">Player MMR: ${mmr}</p>
    </div>
    `
}

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
