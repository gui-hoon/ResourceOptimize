const cpu_config = {
		type: 'line',
		data: {
			labels: cpuTimeList,
			datasets: [{ 
				data: cpuDataList,
				label: "CPUUtilization",
				borderColor: "#3e95cd",
				fill: false
			}]	
		},
		options: {
			title: {
			display: true,
			responsive: false,
			text: instanceID
			},
			responsive :false
		}
};

const diskR_config = {
		type: 'line',
		data: {
			labels: diskRTimeList,
			datasets: [{ 
				data: diskRDataList,
				label: "DiskReadBytes",
				borderColor: "#3e95cd",
				fill: false
			}]	
		},
		options: {
			title: {
			display: true,
			responsive: false,
			text: diskID
			},
			responsive :false
		}
};

const diskW_config = {
		type: 'line',
		data: {
			labels: diskWTimeList,
			datasets: [{ 
				data: diskWDataList,
				label: "DiskWriteBytes",
				borderColor: "#3e95cd",
				fill: false
			}]	
		},
		options: {
			title: {
			display: true,
			responsive: false,
			text: diskID
			},
			responsive :false
		}
};

const diskF_config = {
		type: 'line',
		data: {
			labels: diskFTimeList,
			datasets: [{ 
				data: diskFDataList,
				label: "DiskFree",
				borderColor: "#3e95cd",
				fill: false
			}]	
		},
		options: {
			title: {
			display: true,
			responsive: false,
			text: diskID
			},
			responsive :false
		}
};

const netI_config = {
		type: 'line',
		data: {
			labels: netITimeList,
			datasets: [{ 
				data: netIDataList,
				label: "NetworkIn",
				borderColor: "#4a3ecd",
				fill: false
			}]	
		},
		options: {
			title: {
			display: true,
			responsive: false,
			text: instanceID
			},
			responsive :false
		}
};	

const netO_config = {
		type: 'line',
		data: {
			labels: netOTimeList,
			datasets: [{ 
				data: netODataList,
				label: "NetworkOut",
				borderColor: "#3e95cd",
				fill: false
			}]	
		},
		options: {
			title: {
			display: true,
			responsive: false,
			text: instanceID
			},
			responsive :false
		}
};		

const mem_config = {
		type: 'line',
		data: {
			labels: memTimeList,
			datasets: [{ 
				data: memDataList,
				label: "MemoryUsed",
				borderColor: "#3e95cd",
				fill: false
			}]	
		},
		options: {
			title: {
			display: true,
			responsive: false,
			text: instanceID
			},
			responsive :false
		}
};

if (cpuDataList.length != 0) {
	var cpuChart = new Chart(document.getElementById("cpuChart"), cpu_config);
}

if (diskRDataList.length != 0) {
	var diskRChart = new Chart(document.getElementById("diskRChart"), diskR_config);
}

if (diskWDataList.length != 0) {
	var diskWChart = new Chart(document.getElementById("diskWChart"), diskW_config);
}

if (diskFDataList.length != 0) {
	var diskFChart = new Chart(document.getElementById("diskFChart"), diskF_config);
}

if (netIDataList.length != 0) {
	var netIChart = new Chart(document.getElementById("netIChart"), netI_config);
}

if (netODataList.length != 0) {
	var netOChart = new Chart(document.getElementById("netOChart"), netO_config);
}

if (memDataList.length != 0) {
	var memChart = new Chart(document.getElementById("memChart"), mem_config);
}






