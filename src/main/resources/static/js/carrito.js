document.addEventListener('DOMContentLoaded', (event) => {
    const cantidadInputs = document.querySelectorAll('input[name="cantidad"]');
    
    cantidadInputs.forEach((input) => {
        input.addEventListener('change', (event) => {
            const formulario = input.closest('form');
            if (validar(formulario)) {
               // formulario.submit();
            }
        });
    });
});

function validar(formulario) {
    const cantidadInput = formulario.querySelector('input[name="cantidad"]');
    const cantidad = parseInt(cantidadInput.value);
    const stockMaximo = parseInt(cantidadInput.getAttribute('max'));

    if (cantidad > stockMaximo) {
        alert('El número ingresado en el carrito excede el valor máximo permitido.');
        return false; // Evita que se envíe el formulario
    }
    return true; // Permite que se envíe el formulario si la validación pasa
}





// Obtener todos los elementos <input> de cantidad y los botones de actualizar
const stockInputs = document.querySelectorAll('input[name="cantidad"]');
const actualizarBtns = document.querySelectorAll('button[name="actualizar"]');

// Iterar sobre cada input de cantidad
stockInputs.forEach(function(stockInput) {
    // Obtener el ID del input de cantidad
    const id = stockInput.getAttribute('id').split('-')[1]; // Obtener el ID de entrada

    // Obtener el botón de actualizar correspondiente al input de cantidad actual
    const actualizarBtn = document.getElementById('Actualiza-' + id);

    // Escuchar cambios en el input de cantidad
    stockInput.addEventListener('change', function() {
        // Quitar la clase d-none del botón de actualizar correspondiente
        if (actualizarBtn) {
            actualizarBtn.classList.remove('d-none');
        }
    });
});
