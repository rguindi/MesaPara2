document.addEventListener('DOMContentLoaded', (event) => {
    const cantidadInputs = document.querySelectorAll('input[name="cantidad"]');
    
    cantidadInputs.forEach((input) => {
        input.addEventListener('change', (event) => {
            const formulario = input.closest('form');
            if (validar(formulario)) {
                formulario.submit();
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
