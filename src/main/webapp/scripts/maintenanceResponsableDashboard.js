const validateMaintenanceButtons = document.querySelectorAll(".validate-maintenance-btn");
const machineIdHiddenInput = document.getElementById("machineIdHiddenInput");
const reportRext = document.getElementById("reportText");

validateMaintenanceButtons.forEach((validateMaintenanceButton) => {
	validateMaintenanceButton.addEventListener("click", ({target: {id}}) => {
		const textReport = document.getElementById(`reportTextMachine${id}`).textContent
		machineIdHiddenInput.value = id;
		reportRext.textContent = textReport;
	});
});