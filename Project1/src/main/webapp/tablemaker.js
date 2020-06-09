let pageCounter = 1;
var itemContainer = document.querySelector('#table1_ID');
var btn = document.getElementById("btn")
 btn.addEventListener("click",function () {
     var ourRequest=  new XMLHttpRequest();

     ourRequest.open("GET",'https://learnwebcode.github.io/json-example/animals-' + pageCounter + '.json');
     ourRequest.onload = function() {
         if(ourRequest.status >=200 && ourRequest.status <400){
             var ourData = JSON.parse(ourRequest.responseText);
             renderHTML(ourData);
         } else{
             console.log("We connected to the server but error");
         }
     };

     ourRequest.onerror = function(){
         console.log("Connection error");
     };
     ourRequest.send();

     pageCounter++;
     if (pageCounter > 3){
         btn.classList.add("hide-me");
     }
 });

function renderHTML(data) {
    let htmlString = " ";
    for( i=0; i <data.length; i++){
        htmlString += "<p>" + data[i].name + " is a " + data[i].species + ".</p>";

        for( j=0; j <data.foods.likes.length; j++){
            htmlString += data[i].foods.likes[j];
        }
        htmlString += ".<p>";
        itemContainer.insertAdjacentElement('beforeend', htmlString);
    }




}