// Obtener el input de búsqueda y el contenedor de opciones de filtro
const searchBox = document.getElementById('searchBox');
const filterOptions = document.getElementById('filterOptions');

// Mostrar opciones de filtro cuando el cuadro de búsqueda está enfocado
searchBox.addEventListener('focus', function() {
    filterOptions.style.display = 'block';
});

// Ocultar las opciones de filtro cuando el usuario hace clic en cualquier lugar fuera del cuadro de búsqueda o las opciones
document.addEventListener('click', function(event) {
    if (!searchBox.contains(event.target) && !filterOptions.contains(event.target)) {
        filterOptions.style.display = 'none';
    }
});

// logica para buscar de forma asincronica y filtrar tutorias
 document.getElementById("searchBox").addEventListener("input", function() {
        const query = this.value;

        // Solo realizar la búsqueda si la query no está vacía
        if (query) {
            //ruta para el request de la query
            fetch(`/api/tutorias/busqueda?palabra=${encodeURIComponent(query)}`)
                .then(response => {
                    if (!response.ok) {
                        throw new Error("La respuesta de la red no fue correcta");
                    }
                    return response.json();
                })
                .then(data => {
                    displayResults(data);
                })
                .catch(error => console.error("Error obteniendo tutorias:", error));
        } else {
            document.getElementById("tutoriasFiltradas").innerHTML = ""; // Limpiar resultados si el input está vacío
        }
    });

    // Función para renderizar los resultados de búsqueda
    function displayResults(tutorias) {
        const container = document.getElementById("tutoriasFiltradas");
        container.innerHTML = ""; // Limpiar cualquier resultado anterior

        tutorias.forEach(tutoria => {
            const tutoriaDiv = document.createElement("div");
            tutoriaDiv.className = "tutoria";
            tutoriaDiv.innerHTML = `
                <h3>${tutoria.disciplina}</h3>
                <p>${tutoria.descripcion}</p>
                <p>Modalidad: ${tutoria.modalidadNombre}</p>
                <p>Ubicación: ${tutoria.tipoUbicacionesNombre}</p>
            `;
            container.appendChild(tutoriaDiv);
        });
    }

function abrirModalTutoria(btn) {
    var tutoriaId = parseInt(btn.getAttribute('data-id'));
    document.getElementById('modalTutoriaId').value = tutoriaId;
    document.getElementById('modalDisciplina').innerText = btn.getAttribute('data-disciplina');
    document.getElementById('modalTutorNombre').innerText = btn.getAttribute('data-tutornombre');
    document.getElementById('modalTutorRating').innerText = btn.getAttribute('data-tutorrating');
    document.getElementById('modalTutorBio').innerText = btn.getAttribute('data-tutorbio');
    document.getElementById('modalDescripcion').innerText = btn.getAttribute('data-descripcion');
    document.getElementById('modalDias').innerText = btn.getAttribute('data-dias') || 'No especificado';
    document.getElementById('modalModalidad').innerText = btn.getAttribute('data-modalidad');
    document.getElementById('modalArancel').innerText = btn.getAttribute('data-arancel');
    document.getElementById('modalEdad').innerText = btn.getAttribute('data-edadminima') !== '0' ? btn.getAttribute('data-edadminima') + ' años' : 'Sin límite';
    
    var hDesde = btn.getAttribute('data-horariodesde');
    var hHasta = btn.getAttribute('data-horariohasta');
    document.getElementById('modalHorario').innerText = (hDesde && hHasta) ? hDesde + ' - ' + hHasta : 'A coordinar';
    document.getElementById('modalMateriales').innerText = btn.getAttribute('data-materiales');

    var tipoUbiMapping = {
        'DOMICILIO_TUTOR': 'Domicilio del tutor',
        'DOMICILIO_ESTUDIANTE': 'Domicilio del estudiante',
        'OTRO': 'Otro'
    };
    var tipoUbiStr = btn.getAttribute('data-tipoubicacion');
    var ubiLabel = tipoUbiMapping[tipoUbiStr] || 'Ubicación';
    
    // Si es virtual, podemos ocultar o establecerlo como 'Virtual' en lugar de la ubicación específica
    if (btn.getAttribute('data-modalidad') === 'VIRTUAL') {
        document.getElementById('modalTipoUbicacionLabel').innerText = 'Plataforma Virtual (A coordinar)';
    } else {
        document.getElementById('modalTipoUbicacionLabel').innerText = ubiLabel;
    }
    
    var tipoPagoMapping = {
        'POR_HORA': 'por hora',
        'POR_CLASE': 'por clase',
        'POR_SEMANA': 'por semana',
        'POR_MES': 'por mes'
    };
    var tipoPagoStr = btn.getAttribute('data-tipopago');
    document.getElementById('modalTipoPago').innerText = tipoPagoMapping[tipoPagoStr] || 'por clase';
    
    var tutorPersonaId = parseInt(btn.getAttribute('data-tutorpersonaid'));
    var tutorPerfilId = parseInt(btn.getAttribute('data-tutorperfilid'));
    
    var verPerfilBtn = document.getElementById('modalVerPerfilBtn');
    if (tutorPerfilId) {
        verPerfilBtn.href = '/perfil/' + tutorPerfilId;
        verPerfilBtn.style.display = 'inline-block';
    } else {
        verPerfilBtn.style.display = 'none';
    }
    
    var btnSolicitar = document.getElementById('btnSolicitarTutoria');
    var msgPropia = document.getElementById('msgTutoriaPropia');
    
    // Estilos por defecto
    btnSolicitar.style.display = 'block';
    btnSolicitar.innerText = 'Solicitar Tutoría';
    btnSolicitar.disabled = false;
    btnSolicitar.style.backgroundColor = '#ffcc00';
    btnSolicitar.style.cursor = 'pointer';
    msgPropia.style.display = 'none';
        
    // Prevenir auto-solicitud (Tutor pidiendo su propia clase)
    if (typeof currentUsuarioPersonaId !== 'undefined' && tutorPersonaId === currentUsuarioPersonaId) {
        btnSolicitar.style.display = 'none';
        msgPropia.innerText = 'Esta es tu propia tutoría';
        msgPropia.style.display = 'block';
    } else if (typeof tutoriasSolicitadasIds !== 'undefined' && tutoriasSolicitadasIds.includes(tutoriaId)) {
        // Prevenir solicitud duplicada
        btnSolicitar.style.display = 'none';
        msgPropia.innerText = 'Ya has solicitado esta tutoría';
        msgPropia.style.display = 'block';
        msgPropia.style.color = '#e67e22'; // Naranja de advertencia
    }
    
    document.getElementById('tutoriaModal').style.display = 'flex';
}

function cerrarModalTutoria() {
    document.getElementById('tutoriaModal').style.display = 'none';
}
