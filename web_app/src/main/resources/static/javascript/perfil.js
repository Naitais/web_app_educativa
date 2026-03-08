const eliminarTutoriaBtn = document.querySelectorAll('.eliminarTutoriaBtn');
const agregarTutoriaBtn = document.getElementById('agregarTutoriaBtn');
const cerrarVentanaBtn = document.getElementById('cerrarVentanaBtn');
const ventanaEmergente = document.getElementById('ventanaEmergente');


//CREAR/EDITAR TUTORIA
 async function submitTutoriaForm() {
            // Get checked days
            const fileCheckboxes = document.querySelectorAll('input[name="dias"]:checked');
            const checkedDias = Array.from(fileCheckboxes).map(cb => cb.value);
            
            // Get robust profile ID from hidden input
            const profileId = document.getElementById('perfilId').value;
            const editId = document.getElementById('tutoriaIdEdit').value;

            const formData = {
                edadMinima: document.getElementById('edadMinima').value,
                horarioDesde: document.getElementById('horarioDesde').value,
                horarioHasta: document.getElementById('horarioHasta').value,
                fechaDesde: document.getElementById('fechaDesde') ? document.getElementById('fechaDesde').value : null,
                fechaHasta: document.getElementById('fechaHasta') ? document.getElementById('fechaHasta').value : null,
                dias: checkedDias,
                tipoUbicaciones: document.getElementById('tipoUbicaciones').value,
                disciplina: document.getElementById('disciplina').value,
                materiales: document.getElementById('materiales') ? document.getElementById('materiales').value : null,
                ubicacion: document.getElementById('ubicacion') ? document.getElementById('ubicacion').value : null,
                estado: true,
                descripcion: document.getElementById('descripcion').value,
                tipoPago: document.getElementById('tipoPago').value,
                modalidad: document.getElementById('modalidad').value,
                arancel: document.getElementById('arancel').value
            };

            try {
                let response;
                if (editId) {
                    response = await fetch('/api/tutorias/' + editId, {
                        method: 'PUT',
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify(formData),
                    });
                } else {
                    response = await fetch('/api/tutorias/' + profileId, {
                        method: 'POST',
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify(formData),
                    });
                }

                if (response.ok) {
                    alert(editId ? "Tutoria actualizada!" : "Tutoria created successfully!");
                    document.getElementById("tutoriaForm").reset();
                    document.getElementById("tutoriaIdEdit").value = "";
                    document.getElementById("modalTitle").innerText = "Nueva Tutoría";
                    ventanaEmergente.style.display = 'none'; // Hide modal
                    location.reload(); // Reload to show new item
                } else {
                    const errorText = await response.text();
                    alert("Failed to save Tutoria: " + errorText);
                }
            } catch (error) {
                console.error("Error:", error);
                alert("An error occurred while saving the Tutoria.");
            }
        }

document.getElementById("tutoriaForm").addEventListener("submit", async function(event) {
    event.preventDefault();  // Prevent form submission
    await submitTutoriaForm();
});

// MODAL TOGGLES
if(agregarTutoriaBtn) {
    agregarTutoriaBtn.addEventListener('click', () => {
        document.getElementById("tutoriaForm").reset();
        document.getElementById("tutoriaIdEdit").value = "";
        document.getElementById("modalTitle").innerText = "Nueva Tutoría";
        ventanaEmergente.style.display = 'flex';
    });
}
if(cerrarVentanaBtn) {
    cerrarVentanaBtn.addEventListener('click', () => {
        ventanaEmergente.style.display = 'none';
        document.getElementById("tutoriaForm").reset();
    });
}

//ELIMINAR / TOGGLE / EDITAR TUTORIAS

document.addEventListener('DOMContentLoaded', function () {
    // TOGGLE ESTADO
    document.querySelectorAll('.toggleTutoriaBtn').forEach(button => {
        button.addEventListener('click', function(event) {
            const tutoriaId = event.target.getAttribute('data-tutoria-id');

            if (tutoriaId) {
                fetch(`/api/tutorias/${tutoriaId}/toggle`, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                })
                .then(response => {
                    if (response.ok) {
                        location.reload();  // Reload the page to reflect changes
                    } else {
                        alert('Failed to toggle tutoria estado');
                    }
                })
                .catch(error => {
                    console.error('Error toggling tutoria:', error);
                });
            }
        });
    });

    // EDITAR TUTORIA
    document.querySelectorAll('.editarTutoriaBtn').forEach(button => {
        button.addEventListener('click', function(event) {
            const btn = event.target;
            
            document.getElementById('tutoriaIdEdit').value = btn.getAttribute('data-id');
            document.getElementById('modalTitle').innerText = "Editar Tutoría";
            
            document.getElementById('edadMinima').value = btn.getAttribute('data-edadminima');
            document.getElementById('horarioDesde').value = btn.getAttribute('data-horariodesde');
            document.getElementById('horarioHasta').value = btn.getAttribute('data-horariohasta');
            if(document.getElementById('fechaDesde')) document.getElementById('fechaDesde').value = btn.getAttribute('data-fechadesde') || '';
            if(document.getElementById('fechaHasta')) document.getElementById('fechaHasta').value = btn.getAttribute('data-fechahasta') || '';
            document.getElementById('disciplina').value = btn.getAttribute('data-disciplina');
            document.getElementById('tipoUbicaciones').value = btn.getAttribute('data-tipoubicaciones');
            document.getElementById('descripcion').value = btn.getAttribute('data-descripcion');
            if (document.getElementById('materiales')) document.getElementById('materiales').value = btn.getAttribute('data-materiales');
            document.getElementById('tipoPago').value = btn.getAttribute('data-tipopago');
            document.getElementById('modalidad').value = btn.getAttribute('data-modalidad');
            document.getElementById('arancel').value = btn.getAttribute('data-arancel');
            
            // Checkboxes
            const diasStr = btn.getAttribute('data-dias'); // e.g., "[Lunes, Martes]"
            document.querySelectorAll('input[name="dias"]').forEach(cb => {
                cb.checked = diasStr ? diasStr.includes(cb.value) : false;
            });
            
            ventanaEmergente.style.display = 'flex';
        });
    });
});
