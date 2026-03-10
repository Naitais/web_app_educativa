function abrirModalTutoria(btn) {
    var tutoriaId = parseInt(btn.getAttribute('data-id'));
    document.getElementById('modalTutoriaId').value = tutoriaId;
    document.getElementById('modalDisciplina').innerText = btn.getAttribute('data-disciplina');
    document.getElementById('modalTutorNombre').innerText = btn.getAttribute('data-tutornombre');
    document.getElementById('modalTutorRating').innerText = btn.getAttribute('data-tutorrating');
    document.getElementById('modalTutorBio').innerText = btn.getAttribute('data-tutorbio');
    document.getElementById('modalDescripcion').innerText = btn.getAttribute('data-descripcion');
    document.getElementById('modalDias').innerText = btn.getAttribute('data-dias') || "No especificado";
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
    var btnSolicitar = document.getElementById('btnSolicitarTutoria');
    var msgPropia = document.getElementById('msgTutoriaPropia');
    
    // Estilos por defecto
    btnSolicitar.style.display = 'block';
    msgPropia.style.display = 'none';
        
    // Prevenir auto-solicitud visual (Tutor pidiendo su propia clase)
    if (typeof currentUsuarioPersonaId !== 'undefined' && tutorPersonaId === currentUsuarioPersonaId) {
        btnSolicitar.style.display = 'none';
        msgPropia.innerText = 'No puedes solicitar tus propias tutorías';
        msgPropia.style.display = 'block';
        msgPropia.style.color = '#888';
    } else if (typeof tutoriasSolicitadasIds !== 'undefined' && tutoriasSolicitadasIds.includes(tutoriaId)) {
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

function abrirModalComentario() {
    document.getElementById('comentarioModal').style.display = 'flex';
}

function cerrarModalComentario() {
    document.getElementById('comentarioModal').style.display = 'none';
}
