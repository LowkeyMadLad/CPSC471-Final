//
//const username = "dannyp";
//var url = "https://localhost:443/cards/getallcards/" +username;
//const addCards = document.getElementById("cards-here");
//fetch(url).then(function(response){
//    return response.json();
//}).then(function (obj) {
//    console.log(obj);
//    for(i= 0; i < obj.length; i++){
//        addCards.innerHTML += cardHtmlFormat(obj[i], testHand(obj[i]));
//    }
//}).catch(function (err){
//    console.error("something went wrong");
//    console.error(err);
//})
//
//
//function cardHtmlFormat(cardID, tag){
//    var htmlCard = `
//    <div class="card-holder show" id="${cardID}-holder">
//        <img src="img/${cardID}.jpg" id="${cardID}" class="card draggable ${tag}" draggable="true">
//    </div>
//    `
//    return htmlCard;
//}
//function testHand(cardID){
//    var temp = cardID;
//    if (temp > 1000){temp = temp - 1000}
//    if (temp > 100){return "hand-only"}
//    else {return "body-only"}
//}
