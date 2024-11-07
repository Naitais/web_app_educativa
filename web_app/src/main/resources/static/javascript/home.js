// Get the search input and the filter options container
const searchBox = document.getElementById('searchBox');
const filterOptions = document.getElementById('filterOptions');

// Show filter options when the search box is focused
searchBox.addEventListener('focus', function() {
    filterOptions.style.display = 'block';
});

// Hide the filter options when the user clicks anywhere outside the search box or filter options
document.addEventListener('click', function(event) {
    if (!searchBox.contains(event.target) && !filterOptions.contains(event.target)) {
        filterOptions.style.display = 'none';
    }
});

// logica para buscar de forma asincronica y filtrar tutorias
 document.getElementById("searchBox").addEventListener("input", function() {
        const query = this.value;

        // Only perform the search if query is not empty
        if (query) {
            //ruta para el request de la query
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
            document.getElementById("tutoriasFiltradas").innerHTML = ""; // Clear results if input is empty
        }
    });

    // Function to render search results
    function displayResults(tutorias) {
        const container = document.getElementById("tutoriasFiltradas");
        container.innerHTML = ""; // Clear any previous results

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
