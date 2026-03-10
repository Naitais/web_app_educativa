const eliminarTutoriaBtn = document.querySelectorAll('.eliminarTutoriaBtn');
const agregarTutoriaBtn = document.getElementById('agregarTutoriaBtn');
const cerrarVentanaBtn = document.getElementById('cerrarVentanaBtn');
const ventanaEmergente = document.getElementById('ventanaEmergente');


// CREAR/EDITAR TUTORIA
 async function submitTutoriaForm() {
            // Obtener dias chequeados
            const fileCheckboxes = document.querySelectorAll('input[name="dias"]:checked');
            const checkedDias = Array.from(fileCheckboxes).map(cb => cb.value);
            
            // Obtener el ID del perfil robusto desde el input oculto
            const profileId = document.getElementById('perfilId').value;
            const editId = document.getElementById('tutoriaIdEdit').value;

            const formData = {
                edadMinima: document.getElementById('edadMinima').value,
                horarioDesde: document.getElementById('horarioDesde').value,
                horarioHasta: document.getElementById('horarioHasta').value,
                fechaDesde: document.getElementById('fechaDesde') ? document.getElementById('fechaDesde').value : null,
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
                    alert(editId ? "¡Tutoría actualizada!" : "¡Tutoría creada correctamente!");
                    document.getElementById("tutoriaForm").reset();
                    document.getElementById("tutoriaIdEdit").value = "";
                    document.getElementById("modalTitle").innerText = "Nueva Tutoría";
                    ventanaEmergente.style.display = 'none'; // Ocultar modal
                    location.reload(); // Recargar para mostrar el nuevo item
                } else {
                    const errorText = await response.text();
                    alert("Fallo al guardar la Tutoría: " + errorText);
                }
            } catch (error) {
                console.error("Error:", error);
                alert("Ocurrió un error al guardar la Tutoría.");
            }
        }

document.getElementById("tutoriaForm").addEventListener("submit", async function(event) {
    event.preventDefault();  // Prevenir envio del formulario
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
                        location.reload();  // Recargar la pagina para ver los cambios
                    } else {
                        alert('Fallo al cambiar el estado de la tutoría');
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

    // VER SOLICITUDES
    const modalSolicitudes = document.getElementById('modalSolicitudes');
    const cerrarModalSolicitudesBtn = document.getElementById('cerrarModalSolicitudesBtn');
    const listaSolicitudesContainer = document.getElementById('listaSolicitudesContainer');

    if (cerrarModalSolicitudesBtn) {
        cerrarModalSolicitudesBtn.addEventListener('click', () => {
            modalSolicitudes.style.display = 'none';
        });
    }

    function attachModalLogic(buttonsSelector, targetEstado, modalTitle) {
        document.querySelectorAll(buttonsSelector).forEach(button => {
            button.addEventListener('click', function(event) {
                const tutoriaId = event.target.getAttribute('data-tutoria-id');
                const dataContainer = document.getElementById('solicitudes-data-' + tutoriaId);
                const titleNode = document.getElementById('modalSolicitudesTitle');
                if (titleNode) titleNode.innerText = modalTitle;
                
                listaSolicitudesContainer.innerHTML = ''; // Limpiar anterior

                if (dataContainer) {
                    const solicitudes = dataContainer.querySelectorAll('.solicitud-item');
                    let hasItems = false;
                    if (solicitudes.length > 0) {
                        solicitudes.forEach(sol => {
                            const estado = sol.getAttribute('data-estado');
                            if (estado === targetEstado) {
                                hasItems = true;
                                const solId = sol.getAttribute('data-id');
                                const infoHtml = sol.querySelector('.solicitud-info').innerHTML;
                                
                                let buttonsHtml = '';
                                if (estado === 'PENDIENTE') {
                                    buttonsHtml = `
                                        <div style="margin-top: 10px; display: flex; gap: 10px;">
                                            <form action="/solicitudes/aceptar/${solId}" method="POST" style="margin: 0;">
                                                <button type="submit" class="btn" style="background-color: #2ecc71; color: white; padding: 5px 10px; font-size: 0.9em;">Aceptar</button>
                                            </form>
                                            <form action="/solicitudes/rechazar/${solId}" method="POST" style="margin: 0;">
                                                <button type="submit" class="btn" style="background-color: #e74c3c; color: white; padding: 5px 10px; font-size: 0.9em;">Rechazar</button>
                                            </form>
                                        </div>
                                    `;
                                }

                                const div = document.createElement('div');
                                div.style = "border: 1px solid #ddd; border-radius: 8px; padding: 15px; margin-bottom: 10px; background: #fafafa;";
                                div.innerHTML = `
                                    ${infoHtml}
                                    ${buttonsHtml}
                                `;
                                listaSolicitudesContainer.appendChild(div);
                            }
                        });
                    } 
                    if (!hasItems) {
                        listaSolicitudesContainer.innerHTML = '<p>No hay items para mostrar.</p>';
                    }
                }
                
                modalSolicitudes.style.display = 'flex';
            });
        });
    }

    attachModalLogic('.verSolicitudesBtn', 'PENDIENTE', 'Solicitudes Pendientes');
    attachModalLogic('.verAceptadosBtn', 'ACEPTADA', 'Alumnos Aceptados');

});

    function abrirModalEditarPerfil() {
        var modal = document.getElementById('editarPerfilModal');
        // Pre-fill fields con los valores actuales recuperados del DOM
        var currentBio = document.getElementById('biografia-text').innerText;
        var currentFoto = document.getElementById('foto-url').innerText;
        
        // No pre-llenar con el texto de contingencia "Biografía no disponible..."
        if (currentBio && !currentBio.includes('Biografía no disponible')) {
            document.getElementById('biografia').value = currentBio;
        }
        
        if (currentFoto && currentFoto.trim() !== '') {
            document.getElementById('foto').value = currentFoto.trim();
        }
        
        modal.style.display = 'flex';
    }

    function cerrarModalEditarPerfil() {
        document.getElementById('editarPerfilModal').style.display = 'none';
    }

    function abrirModalEditarExp(id) {
        document.getElementById('editarExpModal_' + id).style.display = 'flex';
    }
    function cerrarModalEditarExp(id) {
        document.getElementById('editarExpModal_' + id).style.display = 'none';
    }

    function abrirModalEditarCert(id) {
        document.getElementById('editarCertModal_' + id).style.display = 'flex';
    }
    function cerrarModalEditarCert(id) {
        document.getElementById('editarCertModal_' + id).style.display = 'none';
    }
