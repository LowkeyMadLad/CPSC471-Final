console.log("loaded");
const username = "dannyp";

formatPage()

async function formatPage(){
    mainBox = document.getElementById("main-box");
    var strings = await getSeasonInfo();
    for (let i = 0; i < strings.length; i++) {
        mainBox.innerHTML += formatHTML(strings[i]);
    }
}


async function getSeasonInfo(){
    let obj;
    const result = fetch("https://localhost:433/season/getallseasonpeaks/" + username, {
        method: "GET",
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        }
    });
    obj = (await result).json();
    console.log(obj);
    return obj;
}


function formatHTML(string){
    var htmlCard = `
    <div class="season-box">
        <p style="font-size: 24px;">${string}</p>
    </div>
    `
    return htmlCard;
}


{/* <div id="test" class="season-box">
    <p style="font-size: 24px;">This is just a test to show how it looks</p>
</div>
<div id="test2" class="season-box">
    <p style="font-size: 24px;">This is just a test to show how it looks</p>
</div>
<div id="test3" class="season-box">
    <p style="font-size: 24px;">This is just a test to show how it looks</p>
</div>
<div id="test4" class="season-box">
    <p style="font-size: 24px;">This is just a test to show how it looks</p>
</div> */}

