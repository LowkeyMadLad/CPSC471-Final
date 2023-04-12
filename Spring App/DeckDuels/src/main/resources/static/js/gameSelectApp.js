console.log("loaded");
const username = "dannyp";

const gameselect = document.getElementById("game-select");
gameselect.innerHTML
const draggables = document.querySelectorAll('.draggable')
const selectedCardSub = document.querySelectorAll('.selected-card-sub')


// This detects if a card is being dragged and when the dragging has stopped
draggables.forEach(draggable => {
    draggable.addEventListener('dragstart', () => {
        draggable.classList.add('dragging')
    })

    draggable.addEventListener('dragend', () => {
        draggable.classList.remove('dragging')
    })
})

selectedCardSub.forEach(selectCard => {
    // Prevents the crossed out bar saying cannot add
    selectCard.addEventListener("dragover", e => {
        e.preventDefault()
    })

    // This is the logic behind the drop command. It is a doozie so comments will be at every section
    selectCard.addEventListener("drop", e => {
        e.preventDefault() // Same as above
        const draggable = document.querySelector('.dragging') // Grabs the card being dragged
        
        /**
         * This if block is for if there is no card in the selected slot or if there is.
         * if the childnodes have a length of 1, then there is nothing in it. Thus it uses empty logic
         */
        if(selectCard.childNodes.length == 1){
            // This if and else if are for making sure the card goes into the only slot it is allowed too
            if (selectCard.id === "body-card-slot" && draggable.classList.contains("body-only")) {
                draggable.classList.add('body-card-slot') // This is for logic and potential styling
                selectCard.appendChild(draggable)
                draggable.classList.add("selected-card")
                selectCard.classList.remove("outline-unselected-body")
                selectCard.classList.add("outline-selected-body")
            } 
            // This is identical as above but for hand instead of body.
            else if(selectCard.id === "hand-card-slot" && draggable.classList.contains("hand-only")){
                draggable.classList.add('hand-card-slot')
                selectCard.appendChild(draggable)
                draggable.classList.add("selected-card")
                selectCard.classList.remove("outline-unselected-hand")
                selectCard.classList.add("outline-selected-hand")
            }
        }
        else{ // this is when a card slot is already populated. It will remove the already existing one and add the new one
            if (selectCard.id === "body-card-slot") { // Body Card
                if (draggable.classList.contains("body-only")) { // Only does something if it is in the right slot
                    const replaceable = document.querySelector('.body-card-slot') // Gets the item currently in the body slot
                    // Deals with the slot adding and removing
                    // Finds the location to put the card being replaced, and then places it in that slot. Then appends the new card
                    if (replaceable.classList.contains("slot-1")) {
                        var sendToSlot = "slot1-body-holder";
                    }
                    if (replaceable.classList.contains("slot-2")) {
                        var sendToSlot = "slot2-body-holder";
                    }
                    if (replaceable.classList.contains("slot-3")) {
                        var sendToSlot = "slot3-body-holder";
                    }
                    const replacableSlot = document.getElementById(sendToSlot)
                    replaceable.classList.remove('body-card-slot')
                    replaceable.classList.remove('selected-card')
                    replacableSlot.appendChild(replaceable)
                    selectCard.appendChild(draggable)
                    draggable.classList.add('body-card-slot')
                    draggable.classList.add("selected-card")
                }
            } 
            // This else is the same as above just for the hand slot
            else { // Hand card
                if (draggable.classList.contains("hand-only")) {
                    const replaceable = document.querySelector('.hand-card-slot') // Gets the item currently in the body slot
                    console.log(replaceable)
                    if (replaceable.classList.contains("slot-1")) {
                        var sendToSlot = "slot1-hand-holder";
                    }
                    if (replaceable.classList.contains("slot-2")) {
                        var sendToSlot = "slot2-hand-holder";
                    }
                    if (replaceable.classList.contains("slot-3")) {
                        var sendToSlot = "slot3-hand-holder";
                    }
                    const replacableSlot = document.getElementById(sendToSlot)
                    replaceable.classList.remove('hand-card-slot')
                    replaceable.classList.remove('selected-card')
                    replacableSlot.appendChild(replaceable)
                    selectCard.appendChild(draggable)
                    draggable.classList.add('hand-card-slot')
                    draggable.classList.add("selected-card")
                }

            }
        }

    })
})

// $.ajax({
//     url: "https://localhost:8080/infoget/getdeck/" + username,
//     dataType: "json",
//     type: 'get',
//     success: function(data){
//         console.log(data);
//     }
// })

// Doing an on click thing where on the play button it auto loads into a draft pick with the AI
// Figuring out how to do that one now.