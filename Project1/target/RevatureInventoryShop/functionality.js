var table = document.querySelector('#table1_ID');
//var itemContainer = document.querySelector('#table1_ID');
var btn = document.getElementById("btn")

var data = {
    index:
        [
            {id:1,in:1},
            {id:2,in:2},
            {id:3,in:3},
            {id:4,in:4},
        ],
    item:
        [
            {id:1,name:'Candy'},
            {id:2,name:'Potato Chips'},
            {id:3,name: 'Mints'},
            {id:4,name: 'Sodas'},
        ],
    quantity:
        [
            {id:1,q:400},
            {id:2,q:350},
            {id:3,q:300},
            {id:4,q:200},
        ]
};



btn.addEventListener("click",function () {

    let ourRequest=  new XMLHttpRequest();
    ourRequest.onload = function() {
        if(ourRequest.status >=200 && ourRequest.status <400){
            var ourData = JSON.parse(ourRequest.responseText);
            renderHTML(ourData);
        } else{
            console.log("We connected to the server but error");
        }
    };
    ourRequest.send();
    addRows(data);

    function addRows(data) {
        if (!data) return;
        var tbody = table.createTBody();
        tbody.id = data.id;
        for (i=0;i<3;i++)

            data.item.forEach(file => {
                var tr = tbody.insertRow();

                var td1 = tr.insertCell();
                var td2 = tr.insertCell();
                var td3 = tr.insertCell();

                var i = document.createTextNode(data.in);
                td1.appendChild(i);

                var n = document.createTextNode(file.name);
                td2.appendChild(n);

                var ph = document.createTextNode(data.q);
                td3.appendChild(ph);
            });
    }


});





