function showModal(button) {
    // Retrieve the JSON data from the button's data-machine attribute
    const machineJson = button.getAttribute("data-machine");
    const machine = JSON.parse(machineJson);

    // Populate the modal with machine details
    document.getElementById("modalMachineName").textContent = machine.machineName;
    document.getElementById("modalMachineType").textContent = machine.machineTypeName;
    document.getElementById("modalMachineStatus").textContent = machine.machineStatus;
    document.getElementById("modalZoneColor").textContent = machine.zoneColor;
    document.getElementById("modalSiteCity").textContent = machine.siteCity;
    document.getElementById("modalBuy").textContent = machine.buy ? "Yes" : "No";

    // Show the modal
    const machineModal = new bootstrap.Modal(document.getElementById("machineModal"));
    machineModal.show();
}


function submitForm() {
    // Retrieve the machine data from the hidden input
    const machineData = document.getElementById('machineData').value;

    // Send POST request with the machine data
    fetch('BuyMachineServlet', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: machineData
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert('Machine purchased successfully!');
                location.reload(); // Reload the page to update the table
            } else {
                alert('Error purchasing machine.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred while processing the request.');
        });
}
