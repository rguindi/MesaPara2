//
//
//
// let meses = [];
//// Obtener la fecha actual
//let fechaActual = new Date();
//// Array con los nombres de los meses en español
//let nombresMeses = [
//  'Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio',
//  'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'
//];
//// Calcular los nombres de los últimos 6 meses
//for (let i = 0; i < 6; i++) {
//  // Calcular el índice del mes retrocediendo i meses desde el mes actual
//  let indiceMes = fechaActual.getMonth() - i;
//  // Manejar el caso de retroceder al año anterior
//  if (indiceMes < 0) {
//    indiceMes = 12 + indiceMes; // Sumar 12 para ajustar al año anterior
//  }
//  // Obtener el nombre del mes correspondiente al índice calculado
//  let nombreMes = nombresMeses[indiceMes];
//  // Agregar el nombre del mes al principio del array `meses`
//  meses.unshift(nombreMes);
//}
//
//
//
//
//


  //OBTENER LOS DATOS DE FACTURACION
  console.log("DATOS DE FACTURACION");
var facturacionData = document.currentScript.getAttribute('data-facturacion');
console.log(facturacionData);
var content = facturacionData.substring(1, facturacionData.length - 1);
// Dividir la cadena por ", Facturacion" para obtener los elementos individuales
var items = content.split(", Facturacion");
// Arrays para almacenar meses y totales
var meses2 = [];
var totales = [];

items.forEach(function(item) {
    // Agregar ", Facturacion" al final de cada item (excepto el primero)
    item = item.trim() + ", Facturacion";

    // Utilizar expresiones regulares para extraer mes y total
    var mesMatch = /mes=(.*?),/.exec(item);
    var totalMatch = /total=(.*?)\)/.exec(item);

    // Extraer mes y total utilizando las coincidencias de la expresión regular
    if (mesMatch && mesMatch.length > 1 && totalMatch && totalMatch.length > 1) {
        var mes = mesMatch[1].trim(); // Extraer el valor del mes
        var total = parseFloat(totalMatch[1].trim()); // Extraer el valor del total y convertirlo a número

        // Agregar mes y total a los arrays correspondientes
        meses2.push(mes);
        totales.push(total);
    }
});

meses2.reverse();
totales.reverse();

// Imprimir los arrays de meses y totales
console.log("Meses:", meses2);
console.log("Totales:", totales);




const ctx = document.getElementById('facturacion');
  new Chart(ctx, {
    type: 'line',
    data: {
      labels: meses2,
      datasets: [{
        label: 'Facturación',
        data: totales,
        borderWidth: 1
      }]
    },
    options: {
      scales: {
        y: {
          beginAtZero: true
        }
      }
    }
  });



  //----------------



  //OBTENER LOS DATOS DE PRODUCTOS MAS VENDIDOS
  console.log("PRODUCTOS MAS VENDIDOS");

  var ventasData = document.currentScript.getAttribute('data-ventas');
console.log(ventasData);
// Arrays para almacenar nombres y ventas
var nombres = [];
var ventas = [];
// Parsear el contenido JSON
var contenido = ventasData.replace(/\), /g, '|'); // Reemplazar la coma entre elementos
contenido = contenido.replace('[', ''); // Eliminar el primer corchete
contenido = contenido.replace(']', ''); // Eliminar el último corchete

// Dividir la cadena por el separador "|" para obtener elementos individuales
var elementos = contenido.split('|');

// Iterar sobre los elementos para extraer nombres y ventas
elementos.forEach(function(item) {
    // Extraer el nombre y las ventas del elemento
    var match = /nombre=(.*), ventas=(\d+)/.exec(item);
    if (match) {
        nombres.push(match[1]); // Agregar el nombre al array de nombres
        ventas.push(parseInt(match[2])); // Agregar las ventas (convertidas a entero) al array de ventas
    }
});

// Mostrar los arrays resultantes
console.log("Nombres:", nombres);
console.log("Ventas:", ventas);


const masVendidos = document.getElementById('masVendidos');
  new Chart(masVendidos, {
    type: 'polarArea',
    data: {
      labels: nombres,
      datasets: [{
        label: 'Productos mças vendidos',
        data: ventas,
        backgroundColor: [
          'rgb(255, 99, 132)',
          'rgb(75, 192, 192)',
          'rgb(255, 205, 86)',
          'rgb(201, 203, 207)',
          'rgb(54, 162, 235)'
        ]
      }]
    },
    options: {
      scales: {
        y: {
          beginAtZero: true
        }
      }
    }
  });

    //----------------



      //OBTENER LOS DATOS DE PRODUCTOS MAS VALORADOS

      console.log("PRODUCTOS CON MAS VALORACIONES");
  var valoradosData = document.currentScript.getAttribute('data-valorados');
  console.log(valoradosData);
  // Arrays para almacenar nombres y valoraciones
  var nombres2 = [];
  var valoraciones = [];
  // Parsear el contenido JSON
  var contenido2 = valoradosData.replace(/\), /g, '|'); // Reemplazar la coma entre elementos
  contenido2 = contenido2.replace('[', ''); // Eliminar el primer corchete
  contenido2 = contenido2.replace(']', ''); // Eliminar el último corchete
  
  // Dividir la cadena por el separador "|" para obtener elementos individuales
  var elementos2 = contenido2.split('|');
  
  // Iterar sobre los elementos2 para extraer nombres y valoraciones
  elementos2.forEach(function(item) {
      // Extraer el nombre y las valoraciones del elemento
      var match = /nombre=(.*), ventas=(\d+)/.exec(item);
      if (match) {
          nombres2.push(match[1]); // Agregar el nombre al array de nombres
          valoraciones.push(parseInt(match[2])); // Agregar las valoraciones (convertidas a entero) al array de valoraciones
      }
  });
  
  // Mostrar los arrays resultantes
  console.log("Nombres:", nombres2);
  console.log("Valoraciones:", valoraciones);
const masValorados = document.getElementById('masValorados');
  new Chart(masValorados, {
    type: 'bar',
    data: {
      labels: nombres2,
  datasets: [{
    label: 'Productos con mas valoraciones',
    data: valoraciones,
    backgroundColor: [
      'rgba(255, 99, 132, 0.2)',
      'rgba(255, 159, 64, 0.2)',
      'rgba(255, 205, 86, 0.2)',
      'rgba(75, 192, 192, 0.2)',
      'rgba(54, 162, 235, 0.2)',
      'rgba(153, 102, 255, 0.2)'
    ],
    borderColor: [
      'rgb(255, 99, 132)',
      'rgb(255, 159, 64)',
      'rgb(255, 205, 86)',
      'rgb(75, 192, 192)',
      'rgb(54, 162, 235)',
      'rgb(153, 102, 255)'
    ],
    borderWidth: 1
      }]
    },
    options: {
      scales: {
        y: {
          beginAtZero: true
        }
      }
    }
  });


