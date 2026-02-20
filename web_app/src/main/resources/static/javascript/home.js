const searchBox = document.getElementById('searchBox');
const filterOptions = document.getElementById('filterOptions');

searchBox.addEventListener('focus', function () {
    filterOptions.style.display = 'block';
});

document.addEventListener('click', function (event) {
    if (!searchBox.contains(event.target) && !filterOptions.contains(event.target)) {
        filterOptions.style.display = 'none';
    }
});

document.getElementById("searchBox").addEventListener("input", function () {
    const query = this.value;

    if (query) {
        fetch(`/api/tutorias/busqueda?palabra=${encodeURIComponent(query)}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                return response.json();
            })
            .then(data => {
                displayResults(data);
            })
            .catch(error => console.error("Error fetching tutorias:", error));
    } else {
        document.getElementById("tutoriasFiltradas").innerHTML = "";
    }
});

function displayResults(tutorias) {
    const container = document.getElementById("tutoriasFiltradas");
    container.innerHTML = "";

    tutorias.forEach(tutoria => {
        const tutoriaDiv = document.createElement("div");
        tutoriaDiv.className = "tutoria";
        tutoriaDiv.innerHTML = `
                <h3>${tutoria.disciplina}</h3>
                <p>${tutoria.descripcion}</p>
                <p>Modalidad: ${tutoria.modalidad}</p>
                <p>Ubicación: ${tutoria.ubicacion}</p>
            `;
        container.appendChild(tutoriaDiv);
    });
}
