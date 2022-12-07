if (asIsLCount.length != 0) {
	new Chart(document.getElementById("asis-l-pie-chart"), {
	    type: 'doughnut',
	    data: {
	      labels: asIsLList,
	      datasets: [{
	        backgroundColor: ["#3e95cd", "#8e5ea2","#3cba9f","#e8c3b9","#c45850"],
	        data: asIsLCount
	      }]
	    },
	    options: {
	      title: {
	        display: true,
	        text: 'AS-IS Instance Type (Linux)'
	      },
	      responsive :false
	    }
	});
}

if (toBeLCount.length != 0) {
	new Chart(document.getElementById("tobe-l-pie-chart"), {
	    type: 'doughnut',
	    data: {
	      labels: toBeLList,
	      datasets: [{
	        backgroundColor: ["#3e95cd", "#8e5ea2","#3cba9f","#e8c3b9","#c45850"],
	        data: toBeLCount
	      }]
	    },
	    options: {
	      title: {
	        display: true,
	        text: 'TO-BE Instance Type (Linux)'
	      },
	      responsive :false
	    }
	});
}

if (asIsWCount.length != 0) {
	new Chart(document.getElementById("asis-w-pie-chart"), {
	    type: 'doughnut',
	    data: {
	      labels: asIsWList,
	      datasets: [{
	        backgroundColor: ["#3e95cd", "#8e5ea2","#3cba9f","#e8c3b9","#c45850"],
	        data: asIsWCount
	      }]
	    },
	    options: {
	      title: {
	        display: true,
	        text: 'AS-IS Instance Type (Windows)'
	      },
	      responsive :false
	    }
	});
}

if (toBeWCount.length != 0) {
	new Chart(document.getElementById("tobe-w-pie-chart"), {
	    type: 'doughnut',
	    data: {
	      labels: toBeWList,
	      datasets: [{
	        backgroundColor: ["#3e95cd", "#8e5ea2","#3cba9f","#e8c3b9","#c45850"],
	        data: toBeWCount
	      }]
	    },
	    options: {
	      title: {
	        display: true,
	        text: 'TO-BE Instance Type (Windows)'
	      },
	      responsive :false
	    }
	});
}