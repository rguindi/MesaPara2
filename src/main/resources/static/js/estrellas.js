document.addEventListener("DOMContentLoaded", function() {
    // Obtener el valor de valoracion y redondearlo a un decimal con saltos de 0.5
    var valoracion = parseFloat(document.getElementById("valoracion").innerText);
    var valoracionRedondeada = Math.round(valoracion * 2) / 2;

    // Obtener los iconos de estrellas
    var iconosEstrellas = document.querySelectorAll("#estrellas i");

    // Modificar las clases de los iconos según el valor de la valoracionRedondeada
    for (var i = 0; i < iconosEstrellas.length; i++) {
        if (i < Math.floor(valoracionRedondeada)) {
            // Si el índice es menor que la parte entera de valoracionRedondeada, agregar la clase bi-star-fill
            iconosEstrellas[i].classList.add("bi-star-fill");
            iconosEstrellas[i].classList.remove("bi-star");
        } else if (i === Math.floor(valoracionRedondeada) && valoracionRedondeada % 1 !== 0) {
            // Si el índice es igual a la parte entera de valoracionRedondeada y valoracionRedondeada no es un número entero,
            // agregar la clase bi-star-half
            iconosEstrellas[i].classList.add("bi-star-half");
            iconosEstrellas[i].classList.remove("bi-star");
        }
    }
});
