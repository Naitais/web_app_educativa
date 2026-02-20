const eliminarTutoriaBtn = document.getElementById('eliminarTutoriaBtn');
const agregarTutoriaBtn = document.getElementById('agregarTutoriaBtn');


//CREAR TUTORIA
async function submitTutoriaForm() {
    try {
        console.log("Compiling form data...");
        const formData = {
            edadMinima: document.getElementById('edadMinima').value,
            horarioDesde: document.getElementById('horarioDesde').value,
            horarioHasta: document.getElementById('horarioHasta').value,
            fechaDesde: document.getElementById('fechaDesde').value,
            fechaHasta: document.getElementById('fechaHasta').value,
            dias: document.getElementById('dias').value,
            tipoUbicaciones: document.getElementById('tipoUbicaciones').value,
            disciplina: document.getElementById('disciplina').value,
            materiales: document.getElementById('materiales').value,
            ubicacion: document.getElementById('ubicacion').value,
            estado: true,
            descripcion: document.getElementById('descripcion').value,
            tipoPago: document.getElementById('tipoPago').value,
            modalidad: document.getElementById('modalidad').value,
            arancel: document.getElementById('arancel').value
        };
        console.log("Form data compiled:", formData);

        // Get perfilId from hidden input
        const perfilIdInput = document.getElementById('perfilId');
        if (!perfilIdInput) {
            throw new Error("Perfil ID not found. Are you logged in as a Tutor with a valid profile?");
        }
        const perfilId = perfilIdInput.value;

        const response = await fetch(`/api/tutorias/${perfilId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData),
        });

        if (response.ok) {
            alert("Tutoria created successfully!");
            // Optionally, redirect or clear the form
            document.getElementById("tutoriaForm").reset();
            location.reload();
        } else {
            const errorText = await response.text();
            console.error("Server responded with error:", errorText);
            alert("Failed to create Tutoria: " + errorText);
        }
    } catch (error) {
        console.error("Error in submitTutoriaForm:", error);
        alert("An error occurred while creating the Tutoria. Check console for details.");
    }
}

document.getElementById("tutoriaForm").addEventListener("submit", async function (event) {
    event.preventDefault();  // Prevent form submission
    await submitTutoriaForm();
});

//ELIMINAR TUTORIAS

document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('.eliminarTutoriaBtn').forEach(button => {
        button.addEventListener('click', function (event) {
            // Get the tutoria id from the hidden span element
            const tutoriaId = event.target.closest('div').querySelector('[id^="tutoriaID"]').textContent;


            console.log('Tutoria ID:', tutoriaId); // Check if the ID is being retrieved

            if (tutoriaId) {
                // Proceed with the fetch request to delete the tutoria
                fetch(`/api/tutorias/${tutoriaId}`, {
                    method: 'DELETE',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                })
                    .then(response => {
                        if (response.ok) {
                            alert('Tutoria deleted successfully');
                            location.reload();  // Reload the page to reflect changes
                        } else {
                            alert('Failed to delete tutoria');
                        }
                    })
                    .catch(error => {
                        console.error('Error deleting tutoria:', error);
                        alert('An error occurred while deleting the tutoria');
                    });
            } else {
                console.log('Tutoria ID is missing');
            }
        });
    });
});
