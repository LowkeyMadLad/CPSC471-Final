


const username = "dannyp";

function onStart(){
    $.ajax({
        url: "https://localhost:8080/cards/getallcards/" + username,
        dataType: "json",
        type: "get",
        success: function(data){
            console.log(data);
        }
    })
}