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

selectedCardSub.forEach(selectCard => {
    selectCard.addEventListener('dragover', e => {
        e.preventDefault()
        const draggable = document.querySelector('.dragging')
        console.log(selectCard.childNodes[1])
        var dragID = draggable.id
        if(selectCard.childNodes.length == 1){
            selectCard.appendChild(draggable)
        }
        else if(selectCard.childNodes.length == 2 && selectCard.childNodes[1].id != dragID){

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



