console.log("loaded");
const draggables = document.querySelectorAll('.draggable')
const selectedCardSub = document.querySelectorAll('.selected-card-sub')

draggables.forEach(draggable => {
    draggable.addEventListener('dragstart', () => {
        draggable.classList.add('dragging')
    })

    draggable.addEventListener('dragend', () => {
        draggable.classList.remove('dragging')
    })
})

// This section is dealing specifically with the cards on the right hand side
selectedCardSub.forEach(selectCard => {
    selectCard.addEventListener("dragover", e => {
        e.preventDefault()
    })

    selectCard.addEventListener("drop", e => {
        e.preventDefault()
        const draggable = document.querySelector('.dragging')
        if(selectCard.childNodes.length == 1){ // When no card is in there
            selectCard.appendChild(draggable)
            if (selectCard.id === "body-card-slot") {
                draggable.classList.add('body-card-slot')
            } else {
                draggable.classList.add('hand-card-slot')
            }
        }
        else{ // When a card is in there
            if (selectCard.id === "body-card-slot") {
                const replaceable = document.querySelector('.body-card-slot') // Gets the item currently in the body slot
                var sendToSlot = replaceable.id + "-holder";
                const replacableSlot = document.getElementById(sendToSlot)
                replaceable.classList.remove('body-card-slot')
                replacableSlot.appendChild(replaceable)
                selectCard.appendChild(draggable)
                draggable.classList.add('body-card-slot')
            } else {
                const replaceable = document.querySelector('.hand-card-slot') // Gets the item currently in the body slot
                var sendToSlot = replaceable.id + "-holder";
                const replacableSlot = document.getElementById(sendToSlot)
                replaceable.classList.remove('hand-card-slot')
                replacableSlot.appendChild(replaceable)
                selectCard.appendChild(draggable)
                draggable.classList.add('hand-card-slot')
            }
        }

        //var elementID = document.getElementById(selectCard.childNodes[0].id)
        // This doesnt quite have all the functionality. Basically I am trying to find a way to grab the
        // ID of the item currently in the node and exchange it with the one that is being dragged
        // Find a way to do this and then read the info :)
    })
})

const observer = new IntersectionObserver((entries) => {
    entries.forEach((entry) => {
        if (entry.isIntersecting) {
            entry.target.classList.add('show');
        } else {
            entry.target.classList.remove('show');
        }
    });
});

const hiddenElements = document.querySelectorAll('.hidden');
hiddenElements.forEach((el) => observer.observe(el));



