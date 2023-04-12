/*
This page was created by Ryan Mailhiot for CPSC 471 final project.

This is the JS page for dealing with all card components that have to deal with drag and drop, including their logic.
It is loaded in and run as soon as the page loads up. This page will end up being for the cards page only.
*/

const mysql = require("mysql");

var con = mysql.createConnection({
    host: "localhost",
    user: "cardUser",
    password: "1234",
    database: "CARD_DATABASE"
});

con.connect(function(err) {
    if (err) throw err;
    console.log("Connected!");
  });

console.log("loaded");
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

// This is a relatively large mostly redundant function but hey it works so I am keeping it.
// The goal of this is to have the dragover and drop functionality for each of the available cards
// Into a given deck slot. 
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
                // Gives it a slot location based on which one it was placed in. 
                if (selectCard.classList.contains("slot-1")) {
                    draggable.classList.add("slot-1")
                }
                if (selectCard.classList.contains("slot-2")) {
                    draggable.classList.add("slot-2")
                }
                if (selectCard.classList.contains("slot-3")) {
                    draggable.classList.add("slot-3")
                }
                // Adds the card to the right location and applyies the correct outline styling
                selectCard.appendChild(draggable)
                draggable.classList.add("selected-card")
                selectCard.classList.remove("outline-unselected-body")
                selectCard.classList.add("outline-selected-body")
            } 
            // This is identical as above but for hand instead of body.
            else if(selectCard.id === "hand-card-slot" && draggable.classList.contains("hand-only")){
                draggable.classList.add('hand-card-slot')
                if (selectCard.classList.contains("slot-1")) {
                    draggable.classList.add("slot-1")
                }
                if (selectCard.classList.contains("slot-2")) {
                    draggable.classList.add("slot-2")
                }
                if (selectCard.classList.contains("slot-3")) {
                    draggable.classList.add("slot-3")
                }
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
                    if (selectCard.classList.contains("slot-1")) {
                        draggable.classList.add("slot-1")
                        replaceable.classList.remove('slot-1')
                    }
                    if (selectCard.classList.contains("slot-2")) {
                        draggable.classList.add("slot-2")
                        replaceable.classList.remove('slot-2')
                    }
                    if (selectCard.classList.contains("slot-3")) {
                        draggable.classList.add("slot-3")
                        replaceable.classList.remove('slot-3')
                    }
                    // Finds the location to put the card being replaced, and then places it in that slot. Then appends the new card
                    var sendToSlot = replaceable.id + "-holder";
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
                    if (selectCard.classList.contains("slot-1")) {
                        draggable.classList.add("slot-1")
                        replaceable.classList.remove('slot-1')
                    }
                    if (selectCard.classList.contains("slot-2")) {
                        draggable.classList.add("slot-2")
                        replaceable.classList.remove('slot-2')
                    }
                    if (selectCard.classList.contains("slot-3")) {
                        draggable.classList.add("slot-3")
                        replaceable.classList.remove('slot-3')
                    }
                    var sendToSlot = replaceable.id + "-holder";
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

// This is an intersection ovserver for when we add more cards. Adds a fade in and out animation
const observer = new IntersectionObserver((entries) => {
    entries.forEach((entry) => {
        if (entry.isIntersecting) {
            entry.target.classList.add('show');
        } else {
            entry.target.classList.remove('show');
        }
    });
});
 
// For fade in and out animation
const hiddenElements = document.querySelectorAll('.hidden');
hiddenElements.forEach((el) => observer.observe(el));

function onClickSave(){
    const selectedCards = document.querySelectorAll('.selected-card')
    
}



 