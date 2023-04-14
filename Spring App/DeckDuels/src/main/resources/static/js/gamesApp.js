console.log("loaded");
const usernamePlayer = "dannyp";
const cardhandIDPlayer = "103";
const cardbodyIDPlayer = "3";

var p1turn = true;
var p1DefendLastTurn = false;
var p2DefendLastTurn = false;
var gameEnd = false;
var turn = 0;

async function runStart(){
    gameEnd = false;
    let list = [usernamePlayer, cardbodyIDPlayer, cardhandIDPlayer];
    var temp = await fetchGameStart(list);
    var bothand = temp[1];
    var botbody = temp[0];
    try {
        var cardbothand = await fectchCard(bothand);
        var cardbotbody = await fectchCard(botbody);
        var cardp1hand = await fectchCard(cardhandIDPlayer);
        var cardp1body = await fectchCard(cardbodyIDPlayer);
    } catch (error) {
        console.error(error);
    }
    
    document.getElementById("game-main").innerHTML = getPageLayout(cardbothand, cardbotbody, cardp1hand, cardp1body);
    document.getElementById("p1-attack").addEventListener("click", playerAttack, false);
    document.getElementById("p1-defend").addEventListener("click", playerDefend, false);
    hpUpdate();
}
async function fectchCard(id){
    let card;
    try {
        const result = fetch("https://localhost:433/cards/getcard/"+ id, {
            method: "get",
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            }
        });
        card = (await result).json();
        return card;
    } catch (error) {
        console.error(error);
    }
    
}

async function fetchGameStart(list){
    let obj;
    const result = fetch("https://localhost:433/game/start", {
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


function getPageLayout(cardbothand, cardbotbody, cardp1hand, cardp1body){
    var htmlCard = `
    <div class="left-player inline-players">
        <h1 class="player-text">${usernamePlayer}</h1>
        <div class="grid-container">
            <div class="grid-item player-photo-holder">
                <img src="img/logo.jpg" id="photo" class="player-photo-actual">
            </div>
            <div class="grid-item hand-card-display">
                <img src="img/${cardhandIDPlayer}.jpg" id="${cardhandIDPlayer}" class="card-actual">
            </div>
            <div class="grid-item body-card-display">
                <img src="img/${cardbodyIDPlayer}.jpg" id="s${cardbodyIDPlayer}" class="card-actual">
            </div>
            <div class="grid-item hand-card-stats-display">
                <h5 class="card-name-text">${cardp1hand.name}</h5>
                <p class="card-text">Melee: ${cardp1hand.meleeStat}</p>
                <p class="card-text">Range: ${cardp1hand.rangeStat}</p>
                <p class="card-text">Guard: ${cardp1hand.guardStat}</p>
            </div>
            <div class="grid-item body-card-stats-display">
                <h5 class="card-name-text">${cardp1body.name}</h5>
                <p class="card-text">Melee: ${cardp1body.meleeStat}</p>
                <p class="card-text">Range: ${cardp1body.rangeStat}</p>
                <p class="card-text">Guard: ${cardp1body.guardStat}</p>
            </div>
        </div>
        <div class="player-one-interaction-box" id="p1-interact">
            <div class="hp-bar">
                <div class="p1-hp-bar" id="p1-hp-bar">HP: x/x</div>
            </div>
            <div class="buttons-display" id="p1-button-display">
                <button class="button-attack button-interact" id="p1-attack">ATTACK</button>
                <button class="button-defend button-interact" id="p1-defend">DEFEND</button>
            </div>
        </div>
    </div>

    <div class="right-player inline-players">
        <h1 class="player-text">Robert N. Gerald</h1>
        <div class="grid-container">
            <div class="grid-item player-photo-holder">
                <img src="img/botIMG.jpg" id="photo" class="player-photo-actual">
            </div>
            <div class="grid-item hand-card-display">
                <img src="img/${cardbothand.id}.jpg" id="${cardbothand.id}" class="card-actual">
            </div>
            <div class="grid-item body-card-display">
                <img src="img/${cardbotbody.id}.jpg" id="${cardbotbody.id}" class="card-actual">
            </div>
            <div class="grid-item hand-card-stats-display">
                <h5 class="card-name-text">${cardbothand.name}</h5>
                <p class="card-text">Melee: ${cardbothand.meleeStat}</p>
                <p class="card-text">Range: ${cardbothand.rangeStat}</p>
                <p class="card-text">Guard: ${cardbothand.guardStat}</p>
            </div>
            <div class="grid-item body-card-stats-display">
                <h5 class="card-name-text">${cardbotbody.name}</h5>
                <p class="card-text">Melee: ${cardbotbody.meleeStat}</p>
                <p class="card-text">Range: ${cardbotbody.rangeStat}</p>
                <p class="card-text">Guard: ${cardbotbody.guardStat}</p>
            </div>
        </div>
        <div class="player-two-interaction-box" id="p2-interact">
            <div class="hp-bar">
                <div class="p2-hp-bar" id="p2-hp-bar"></div>
            </div>
            <div class="buttons-display" id="p2-button-display">
            </div>
        </div>
    </div>
    <div class="action-box">
        <div id="action-messages">
            <h1>Action Log</h1>
            
        </div>
    </div>
    `

    return htmlCard;
}

runStart();

async function leaveGame(){
    let obj;
    const result = fetch("https://localhost:433/game/start", {
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
// update hp stats
async function hpCall(string){
    let obj;
    var url = "https://localhost:433/game/playerhp";
    const result = fetch(url, {
        method: "POST",
        body: string,
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        }
    });

    obj = (await result).json();

    return obj;
}

async function hpUpdate(){
    var p1hp = await hpCall("true"); // TRUE IS P1 FALSE IS P2
    var p2hp = await hpCall("false");

    Math.ceil(p1hp);
    Math.ceil(p2hp);

    p1Bar = document.getElementById("p1-hp-bar");
    p2Bar = document.getElementById("p2-hp-bar");

    p1Bar.innerHTML = "HP: " + p1hp + "/300";
    p2Bar.innerHTML = "HP: " + p2hp + "/300";
}

async function playerMove(string){
    let obj;
    let list = [p1turn, string]
    const result = fetch("https://localhost:433/game/turn", {
        method: "POST",
        body: JSON.stringify(list),
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        }
    });

    obj = (await result).json();

    return obj;
}
function formatActionLog(string){
    var htmlCard = `
    <p style="font-size: 16px;">Turn:${turn} - ${string}</p>
    `
    return htmlCard
}

function updateActionLog(string){
    const actionlog = document.getElementById("action-messages")
    actionlog.innerHTML += formatActionLog(string);
}

async function verifyGameState(){
    let obj;
    const result = fetch("https://localhost:433/game/verify", {
        method: "GET",
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        }
    });
    obj = (await result).json();
    console.log("Next one is gamestate")
    console.log(obj);
    return obj;
}

function disableDefend(){
    document.getElementById('p1-defend').style.display = "none";
}
function enableDefend(){
    document.getElementById('p1-defend').style.display = null;
}

async function onPlayerPress(string) {
    var actionlog;
    var temp;
    turn++;
    console.log(turn);
    if (string === "defend" && !p1DefendLastTurn) {
        actionlog = await playerMove("false");
        updateActionLog(actionlog);
        hpUpdate();
        temp = await verifyGameState();
        p1DefendLastTurn = true;
        disableDefend();
    } else {
        actionlog = await playerMove("true");
        updateActionLog(actionlog);
        hpUpdate();
        temp = await verifyGameState();
        p1DefendLastTurn = false;
        enableDefend();
    }
    console.log("THIS IS TEMP: " + temp);
    gameEnd = JSON.parse(temp);
    console.log("THIS IS THE GAME END VARIABLE: " + gameEnd)
    if (gameEnd) {
        console.log("Game Ended on P1 Turn")
        return;

    }
    p1turn = false;
    turn++;
    console.log(turn);
    var botDefend = Math.random() < 0.5;
    if (botDefend && !p2DefendLastTurn) {
        actionlog = await playerMove("false");
        updateActionLog(actionlog);
        hpUpdate();
        temp = await verifyGameState();
        p2DefendLastTurn = true;
    } else {
        actionlog = await playerMove("true");
        updateActionLog(actionlog);
        hpUpdate();
        temp = await verifyGameState();
        p2DefendLastTurn = false;
    }
    console.log("THIS IS TEMP: " + temp);
    gameEnd = JSON.parse(temp);
    console.log("THIS IS THE GAME END VARIABLE: " + gameEnd)
    if (gameEnd) {
        console.log("Game Ended on P2 Turn")
        return;

    }
    p1turn = true;

}

function playerAttack() {
    if(!gameEnd){
        onPlayerPress("attack");
    }
}

function playerDefend(){
    if(!gameEnd){
        onPlayerPress("defend");
    }
    
}

