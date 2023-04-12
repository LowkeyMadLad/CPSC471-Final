
const username = "dannyp";
var url = "https://localhost:443/cards/getallcards/" +username;
fetch(url).then(function(response){
    return response.json();
}).then(function (obj) {
    console.log(obj);
    for(i= 0; i < obj.length; i++){
        console.log("This is working maybe?")
    }
}).catch(function (err){
    console.error("something went wrong");
    console.error(err);
})


function cardHtmlFormat(cardID){
    var htmlCard = `
    <div class="card-holder hidden" id="${cardID}-holder">
        <img src="img/${cardID}.jpg" id="${cardID}" class="card draggable body-only" draggable="true">
    </div>
    `
}