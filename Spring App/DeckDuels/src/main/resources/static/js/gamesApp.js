console.log("loaded");

function initialLoad(){

}

function getPageLayout(){
    var htmlCard = `
    <div class="left-player inline-players">
        <h1 class="player-text">Player One</h1>
        <div class="grid-container">
            <div class="grid-item player-photo-holder">
                <img src="img/small.jpg" id="photo" class="player-photo-actual">
            </div>
            <div class="grid-item hand-card-display">
                <img src="img/sword.jpg" id="sword" class="card-actual">
            </div>
            <div class="grid-item body-card-display">
                <img src="img/sword.jpg" id="sword" class="card-actual">
            </div>
            <div class="grid-item hand-card-stats-display">
                <h5 class="card-name-text">Name</h5>
                <p class="card-text">Melee:</p>
                <p class="card-text">Range:</p>
                <p class="card-text">Defence:</p>
            </div>
            <div class="grid-item body-card-stats-display">
                <h5 class="card-name-text">Name</h5>
                <p class="card-text">Melee:</p>
                <p class="card-text">Range:</p>
                <p class="card-text">Defence:</p>
            </div>
        </div>
        <div class="player-one-interaction-box" id="p1-interact">
            <div class="hp-bar">
                <div class="p1-hp-bar" id="p1-hp-bar">HP: x/x</div>
            </div>
            <div class="buttons-display">
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
                <img src="img/sword.jpg" id="sword" class="card-actual">
            </div>
            <div class="grid-item body-card-display">
                <img src="img/sword.jpg" id="sword" class="card-actual">
            </div>
            <div class="grid-item hand-card-stats-display">
                <h5 class="card-name-text">Name</h5>
                <p class="card-text">Melee:</p>
                <p class="card-text">Range:</p>
                <p class="card-text">Defence:</p>
            </div>
            <div class="grid-item body-card-stats-display">
                <h5 class="card-name-text">Name</h5>
                <p class="card-text">Melee:</p>
                <p class="card-text">Range:</p>
                <p class="card-text">Defence:</p>
            </div>
        </div>
        <div class="player-two-interaction-box" id="p2-interact">
            <div class="hp-bar">
                <div class="p2-hp-bar" id="p2-hp-bar">HP: x/x</div>
            </div>
            <div class="buttons-display">
                <button class="button-attack button-interact" id="p2-attack">ATTACK</button>
                <button class="button-defend button-interact" id="p2-defend">DEFEND</button>
            </div>
        </div>
    </div>
    `

    return htmlCard;
}



/*
<div class="left-player inline-players">
                    <h1 class="player-text">Player One</h1>
                    <div class="grid-container">
                        <div class="grid-item player-photo-holder">
                            <img src="img/small.jpg" id="photo" class="player-photo-actual">
                        </div>
                        <div class="grid-item hand-card-display">
                            <img src="img/sword.jpg" id="sword" class="card-actual">
                        </div>
                        <div class="grid-item body-card-display">
                            <img src="img/sword.jpg" id="sword" class="card-actual">
                        </div>
                        <div class="grid-item hand-card-stats-display">
                            <h5 class="card-name-text">Name</h5>
                            <p class="card-text">Melee:</p>
                            <p class="card-text">Range:</p>
                            <p class="card-text">Defence:</p>
                        </div>
                        <div class="grid-item body-card-stats-display">
                            <h5 class="card-name-text">Name</h5>
                            <p class="card-text">Melee:</p>
                            <p class="card-text">Range:</p>
                            <p class="card-text">Defence:</p>
                        </div>
                    </div>
                    <div class="player-one-interaction-box" id="p1-interact">
                        <div class="hp-bar">
                            <div class="p1-hp-bar" id="p1-hp-bar">HP: x/x</div>
                        </div>
                        <div class="buttons-display">
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
                            <img src="img/sword.jpg" id="sword" class="card-actual">
                        </div>
                        <div class="grid-item body-card-display">
                            <img src="img/sword.jpg" id="sword" class="card-actual">
                        </div>
                        <div class="grid-item hand-card-stats-display">
                            <h5 class="card-name-text">Name</h5>
                            <p class="card-text">Melee:</p>
                            <p class="card-text">Range:</p>
                            <p class="card-text">Defence:</p>
                        </div>
                        <div class="grid-item body-card-stats-display">
                            <h5 class="card-name-text">Name</h5>
                            <p class="card-text">Melee:</p>
                            <p class="card-text">Range:</p>
                            <p class="card-text">Defence:</p>
                        </div>
                    </div>
                    <div class="player-two-interaction-box" id="p2-interact">
                        <div class="hp-bar">
                            <div class="p2-hp-bar" id="p2-hp-bar">HP: x/x</div>
                        </div>
                        <div class="buttons-display">
                            <button class="button-attack button-interact" id="p2-attack">ATTACK</button>
                            <button class="button-defend button-interact" id="p2-defend">DEFEND</button>
                        </div>
                    </div>
                </div>
*/