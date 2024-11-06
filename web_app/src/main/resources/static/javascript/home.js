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
