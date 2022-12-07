let twoMAccontList = [];
let twoMCostList = [];

let lastAccountList = [];
let lastCostList = [];


for (var i = 0; i < twoMonthList.length; i++) {
	twoMAccontList.push(twoMonthList[i].accountID);
	twoMCostList.push(twoMonthList[i].cost);
}

for (var i = 0; i < lastMonthList.length; i++) {
	lastAccountList.push(lastMonthList[i].accountID);
	lastCostList.push(lastMonthList[i].cost);
}


new Chart(document.getElementById("twoM-pie-chart"), {
    type: 'bar',
    data: {
      labels: twoMAccontList,
      datasets: [{
        backgroundColor: ["#3e95cd", "#8e5ea2","#3cba9f","#e8c3b9","#c45850"],
        data: twoMCostList,
        label: twoMonthAgo
      }]
    },
    options: {
      title: {
        display: true,
        text: 'Two Month Ago Instance Usage Cost'
      },
      responsive :false
    }
});

new Chart(document.getElementById("last-pie-chart"), {
    type: 'bar',
    data: {
      labels: lastAccountList,
      datasets: [{
        backgroundColor: ["#3e95cd", "#8e5ea2","#3cba9f","#e8c3b9","#c45850"],
        data: lastCostList,
        label: lastMonth
      }]
    },
    options: {
      title: {
        display: true,
        text: 'Last Month Instance Usage Cost'
      },
      responsive :false
    }
});








