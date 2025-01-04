const validateMaintenanceButtons = document.querySelectorAll(".validate-maintenance-btn");
const machineIdHiddenInput = document.getElementById("machineIdHiddenInput");
const reportRext = document.getElementById("reportText");
const reportTitle = document.getElementById("reportTitle");

validateMaintenanceButtons.forEach((validateMaintenanceButton) => {
	validateMaintenanceButton.addEventListener("click", ({target: {id}}) => {
		const tmpReportText = document.getElementById(`reportTextMachine${id}`).textContent;
		const tmpReportTitle = document.getElementById(`reportAuthorMachine${id}`).textContent;
		
		machineIdHiddenInput.value = id;
		
		reportRext.textContent = tmpReportText;
		reportTitle.textContent = tmpReportTitle;
	});
});