const finalizeButtons = document.querySelectorAll(".finalize-button");
const modalMaintenanceId = document.getElementById("modalMaintenanceId")

finalizeButtons.forEach((button) => {
	button.addEventListener("click", ({target: {id}}) => {
		console.log("Modal : " + modalMaintenanceId)
		modalMaintenanceId.value = id;
	});
});