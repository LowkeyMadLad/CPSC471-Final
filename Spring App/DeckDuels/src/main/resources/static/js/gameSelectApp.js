console.log("loaded");
const username = "dannyp"


$.ajax({
    url: "https://localhost:8080/infoget/getdeck/" + username,
    dataType: "json",
    type: 'get',
    success: function(data){
        console.log(data);
    }
})

// Doing an on click thing where on the play button it auto loads into a draft pick with the AI
// Figuring out how to do that one now.