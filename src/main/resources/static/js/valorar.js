// Obtenemos todos los elementos de las estrellas y el select
const stars = document.querySelectorAll('.bi-star');
const select = document.getElementById('select');

// Agregamos un event listener a cada estrella
stars.forEach(star => {
  star.addEventListener('click', () => {
    const clickedId = parseInt(star.id.replace('i', ''));
    
    // Cambiamos la clase de todas las estrellas anteriores y la estrella clickeada
    stars.forEach((s, index) => {
      if (index < clickedId) {
        s.classList.remove('bi-star');
        s.classList.add('bi-star-fill');
      } else {
        s.classList.remove('bi-star-fill');
        s.classList.add('bi-star');
      }
    });
    
    // Marcamos el option correspondiente como seleccionado
    const option = document.getElementById(`o${clickedId}`);
    option.selected = true;
  });
});
