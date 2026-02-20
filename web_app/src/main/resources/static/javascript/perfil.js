const eliminarTutoriaBtn = document.getElementById('eliminarTutoriaBtn');
const agregarTutoriaBtn = document.getElementById('agregarTutoriaBtn');

if (agregarTutoriaBtn) {
    agregarTutoriaBtn.addEventListener('click', function () {
        document.getElementById('ventanaEmergente').style.display = 'flex';
    });
}

window.onclick = function (event) {
    const modal = document.getElementById('ventanaEmergente');
    if (event.target == modal) {
        modal.style.display = "none";
    }
}
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
            alert("¡Tutoría creada exitosamente!");
            document.getElementById("tutoriaForm").reset();
            location.reload();
        } else {
            const errorText = await response.text();
            console.error("Server responded with error:", errorText);
            alert("Error al crear la Tutoría: " + errorText);
        }
    } catch (error) {
        console.error("Error in submitTutoriaForm:", error);
        alert("Ocurrió un error al crear la Tutoría. Revisa la consola para más detalles.");
    }
}

document.getElementById("tutoriaForm").addEventListener("submit", async function (event) {
    event.preventDefault();  // Prevent form submission
    await submitTutoriaForm();
});

document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('.eliminarTutoriaBtn').forEach(button => {
        button.addEventListener('click', function (event) {
            const tutoriaId = event.target.closest('div').querySelector('[id^="tutoriaID"]').textContent;
            console.log('Tutoria ID:', tutoriaId);

            if (tutoriaId) {
                fetch(`/api/tutorias/${tutoriaId}`, {
                    method: 'DELETE',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                })
                    .then(response => {
                        if (response.ok) {
                            alert('Tutoria deleted successfully');
                            location.reload();
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
