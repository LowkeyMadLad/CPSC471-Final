
console.log("loaded");
const usernamePlayer = "dannyp";

onStart();
async function onStart(){
    var handSlot = 1;
    var bodySlot = 1;
    var cards = await getCards();
    console.log(cards);
    for (let i = 0; i < 6; i++) {
        x = cards[i]
        y = cards[i]
        if (x > 1000) {
            x = x -1000;
        }
        console.log(y)
        if (x > 99) {
            // this is a hand card
            docSlot = document.getElementById("slot" + handSlot +"-hand-holder");
            docSlot.innerHTML = formatHTML(y, "hand-only");
            handSlot++;
        } else {
            docSlot = document.getElementById("slot" + bodySlot +"-body-holder");
            docSlot.innerHTML = formatHTML(y, "body-only");
            bodySlot++;
        }
    }
}

async function getCards(){
    let obj;
    const result = fetch("https://localhost:433/cards/getdeck/"+usernamePlayer, {
        method: "GET",
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        }
    });
    obj = (await result).json();
    console.log(obj);
    return obj;
}


function formatHTML(idNumber, slotTag){
    htmlCard = `
    <img src="img/${idNumber}.jpg" id="${idNumber}" class="card draggable slot-1 ${slotTag}" draggable="true">
    `
    return htmlCard;
}

// 

