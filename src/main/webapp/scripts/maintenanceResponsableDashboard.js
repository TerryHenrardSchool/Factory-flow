const validateMaintenanceButtons = document.querySelectorAll(".validate-maintenance-btn");
const sendToMaintenanceButtons = document.querySelectorAll(".send-to-maintenance-btn");

const machineIdHiddenInputValidateMaintenance = document.getElementById("machineIdHiddenInputValidateMaintenance");
const machineIdHiddenInputSendToMaintenance = document.getElementById("machineIdHiddenInputSendToMaintenance");

const reportRext = document.getElementById("reportText");
const reportTitle = document.getElementById("reportTitle");

validateMaintenanceButtons.forEach((validateMaintenanceButton) => {
	validateMaintenanceButton.addEventListener("click", ({target: {id}}) => {
		const tmpReportText = document.getElementById(`reportTextMachine${id}`).textContent;
		const tmpReportTitle = document.getElementById(`reportAuthorMachine${id}`).textContent;
		
		machineIdHiddenInputValidateMaintenance.value = id;
		
		reportRext.textContent = tmpReportText;
		reportTitle.textContent = tmpReportTitle;
	});
});

sendToMaintenanceButtons.forEach((sendToMaintenanceButton) => {
	sendToMaintenanceButton.addEventListener("click", ({target: {id}}) => {
		machineIdHiddenInputSendToMaintenance.value = id;
	});
});