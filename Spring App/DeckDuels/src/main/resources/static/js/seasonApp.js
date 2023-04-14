console.log("loaded");
const username = "dannyp";

formatPage()

async function formatPage(){
    var strings = await getSeasonInfo();
    console.log(strings)
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


function formatHTML(seasonNumber, peakMMR, gamesPlayed){
    var htmlCard = `
    <div class="season-box">
        <p style="font-size: 24px;">Season: ${seasonNumber}   |   Peak MMR: ${peakMMR}   |   Total Games Played: ${gamesPlayed}</p>
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

