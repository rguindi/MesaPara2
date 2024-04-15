// Función para ordenar las tarjetas por nombre y reordenarlas en el contenedor
function ordenarPorNombre() {
    // Obtener todos los elementos con el id "tarjetaProducto"
    var tarjetas = document.querySelectorAll('#tarjetaProducto');

    // Convertir la NodeList en un array para poder ordenarla
    tarjetas = Array.from(tarjetas);

    // Ordenar las tarjetas por el contenido del elemento "nombreProducto"
    tarjetas.sort(function(a, b) {
        var nombreProductoA = a.querySelector('#nombreProducto').innerText;
        var nombreProductoB = b.querySelector('#nombreProducto').innerText;
        return nombreProductoA.localeCompare(nombreProductoB);
    });

    // Obtener el contenedor de las tarjetas para reordenarlas
    var contenedor = document.querySelector('#contenedorTarjetas');

    // Limpiar el contenido del contenedor
    contenedor.innerHTML = '';

    // Reordenar las tarjetas en el DOM
    tarjetas.forEach(function(tarjeta) {
        contenedor.appendChild(tarjeta);
    });
}

// Función para ordenar las tarjetas por el precio y reordenarlas en el contenedor
function ordenarPorPrecio() {
    // Obtener todos los elementos con el id "tarjetaProducto"
    var tarjetas = document.querySelectorAll('#tarjetaProducto');

    // Convertir la NodeList en un array para poder ordenarla
    tarjetas = Array.from(tarjetas);

    // Ordenar las tarjetas por el precio
    tarjetas.sort(function(a, b) {
        // Obtener el precio de cada tarjeta
        var precioA = parseFloat(a.querySelector('#precio').innerText); // Selecciona el precio dentro de la tarjeta
        var precioB = parseFloat(b.querySelector('#precio').innerText); // Selecciona el precio dentro de la tarjeta

        // Comparar los precios
        return precioA - precioB;
    });

    // Obtener el contenedor de las tarjetas para reordenarlas
    var contenedor = document.querySelector('#contenedorTarjetas');

    // Limpiar el contenido del contenedor
    contenedor.innerHTML = '';

    // Reordenar las tarjetas en el DOM
    tarjetas.forEach(function(tarjeta) {
        contenedor.appendChild(tarjeta);
    });
}

function ordenarPorPrecioInverso() {
    // Obtener todos los elementos con el id "tarjetaProducto"
    var tarjetas = document.querySelectorAll('#tarjetaProducto');

    // Convertir la NodeList en un array para poder ordenarla
    tarjetas = Array.from(tarjetas);

    // Ordenar las tarjetas por el precio
    tarjetas.sort(function(a, b) {
        // Obtener el precio de cada tarjeta
        var precioA = parseFloat(a.querySelector('#precio').innerText); // Selecciona el precio dentro de la tarjeta
        var precioB = parseFloat(b.querySelector('#precio').innerText); // Selecciona el precio dentro de la tarjeta

        // Comparar los precios
        return precioB - precioA;
    });

    // Obtener el contenedor de las tarjetas para reordenarlas
    var contenedor = document.querySelector('#contenedorTarjetas');

    // Limpiar el contenido del contenedor
    contenedor.innerHTML = '';

    // Reordenar las tarjetas en el DOM
    tarjetas.forEach(function(tarjeta) {
        contenedor.appendChild(tarjeta);
    });
}






// Obtener el elemento select
var selectOrdenar = document.querySelector('select[name="ordenar"]');

// Agregar un listener para detectar el cambio en el select
selectOrdenar.addEventListener('change', function() {
    // Verificar si se ha seleccionado "Nombre"
    if (this.value === 'nombre') {
        // Llamar a la función para ordenar por nombre
        ordenarPorNombre();
    }
    if (this.value === 'precioAs') {
        // Llamar a la función para ordenar por nombre
        ordenarPorPrecio();
    }
     if (this.value === 'precioDes') {
        // Llamar a la función para ordenar por nombre
        ordenarPorPrecioInverso();
    }
});



//Actualizar el valor de la casilla filtrar por precio:
  // Obtener el input de rango y el elemento donde se mostrará el valor seleccionado
    var inputRango = document.getElementById('customRange3');
    var spanValorSeleccionado = document.getElementById('valorSeleccionado');

    // Mostrar el valor inicialmente
    spanValorSeleccionado.textContent = inputRango.value;

    // Agregar un listener para detectar cambios en el input de rango
    inputRango.addEventListener('input', function() {
        // Actualizar el contenido del elemento con el valor seleccionado
        spanValorSeleccionado.textContent = this.value;
    });
    
    
    
    // Agregar un listener para detectar cambios en el input de rango
inputRango.addEventListener('input', function() {
    // Llamar a la función para filtrar y ordenar por precio en orden inverso
    var precioMaximo = parseFloat(this.value);
      // Llamar a la función para ocultar o mostrar las tarjetas según su precio
    ocultarMostrarPorPrecio(precioMaximo);
});



// Función para ocultar o mostrar las tarjetas según su precio
function ocultarMostrarPorPrecio(precioMaximo) {
    // Obtener todos los elementos con el id "tarjetaProducto"
    var tarjetas = document.querySelectorAll('#tarjetaProducto');

    // Iterar sobre todas las tarjetas
    tarjetas.forEach(function(tarjeta) {
        var precio = parseFloat(tarjeta.querySelector('.text-dark').innerText);

        // Ocultar o mostrar la tarjeta según el precio
        if (precio > precioMaximo) {
            tarjeta.style.display = 'none'; // Ocultar la tarjeta
        } else {
            tarjeta.style.display = 'block'; // Mostrar la tarjeta
        }
    });
}