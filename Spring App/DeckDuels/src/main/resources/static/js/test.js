
document.getElementById("subButton").addEventListener('click', onboop, false);


function onboop(){
    fetch("http://localhost:8080/start-game", {
        method: "POST",
        body: JSON.stringify({
            "usernamep1": "bob",
            "p1h": "103",
            "p1b": "1",
            "usernamep2": "dannyp",
            "p2h": "105",
            "p2b": "5"
        }),
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        }
    }) .then((response) => response.json()).then((json) => console.log(json));
}

