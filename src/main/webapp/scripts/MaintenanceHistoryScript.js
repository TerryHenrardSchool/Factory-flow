const reports = document.querySelectorAll(".p-report")
const modal_buttons = document.querySelectorAll(".button-report");
const display_report = document.getElementById("displayReport");


modal_buttons.forEach((button) => {
	button.addEventListener("click", ({target : {id}}) => {
		reports.forEach((report) => {
			if(id == report.getAttribute("id")){
				display_report.textContent = report.textContent;
			}
		});
	});
});