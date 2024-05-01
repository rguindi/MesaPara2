const urlProvincias = 'https://raw.githubusercontent.com/IagoLast/pselect/master/data/provincias.json';
const urlMunicipios = 'https://raw.githubusercontent.com/IagoLast/pselect/master/data/municipios.json';

const selectProvincia = document.getElementById("selectProvincia");
const selectMunicipio = document.getElementById("selectLocalidad");
var usuario = document.currentScript.getAttribute('data-usuario');
var localidad = document.currentScript.getAttribute('data-localidad');
console.log("la provincia es " + usuario);
console.log("la localidad es " + localidad);

async function obtenerProvincias() {
    try {
        const response = await fetch(urlProvincias);
        const data = await response.json(); 

        // Procesar los datos y construir el array de objetos
        const provincias = data.map(provincia => ({
            id: provincia.id,
            nombre: provincia.nm
        }));

        provincias.sort((a, b) => a.nombre.localeCompare(b.nombre));

        return provincias; // Devolver el array de provincias
    } catch (error) {
        console.error('Error al obtener provincias:', error);
        return []; // Devolver un array vacío en caso de error
    }
}

async function obtenerMunicipios(idProvincia) {
    try {
        const response = await fetch(urlMunicipios);
        const data = await response.json();

        // Filtrar los municipios por el ID de la provincia seleccionada
        const municipios = data.filter(municipio => municipio.id.startsWith(idProvincia));

        return municipios; // Devolver el array de municipios filtrados
    } catch (error) {
        console.error('Error al obtener municipios:', error);
        return []; // Devolver un array vacío en caso de error
    }
}

// Función para cargar las provincias en el select
async function cargarProvinciasEnSelect() {
    try {
        const provincias = await obtenerProvincias(); // Esperar a que se resuelva la promesa

        // Iterar sobre el array de provincias y agregar opciones al select
        provincias.forEach(provincia => {
            let option = document.createElement("option");
            option.innerText = provincia.nombre;
            option.value = provincia.nombre;
            if(usuario == provincia.nombre) option.selected = true;
            selectProvincia.appendChild(option);
        });

        // Escuchar el evento change en el select de provincias
        selectProvincia.addEventListener('change', async () => {

            let idProvinciaSeleccionada;
            provincias.forEach(provincia => {
                if(provincia.nombre==selectProvincia.value)idProvinciaSeleccionada= provincia.id;
            });

            selectMunicipio.innerText='';

            if (idProvinciaSeleccionada) {
                const municipios = await obtenerMunicipios(idProvinciaSeleccionada);

                // Iterar sobre los municipios y agregar opciones al select
                municipios.forEach(municipio => {
                    let option = document.createElement("option");
                    option.innerText = municipio.nm;
                    option.value = municipio.nm;
                    if(localidad == municipio.nombre) option.selected = true;
                    selectMunicipio.appendChild(option);
                });
            }
        });

    } catch (error) {
        console.error('Error al cargar provincias en el select:', error);
    }
    pintarMunicipio();
}

async function pintarMunicipio(){
    let idProvinciaSeleccionada;
    const provincias = await obtenerProvincias();
    provincias.forEach(provincia => {
        if(provincia.nombre==selectProvincia.value)idProvinciaSeleccionada= provincia.id;
    });
    console.log(idProvinciaSeleccionada);
    
    if (idProvinciaSeleccionada) {


        const municipios = await obtenerMunicipios(idProvinciaSeleccionada);
        
        // Iterar sobre los municipios y agregar opciones al select
        municipios.forEach(municipio => {
            let option = document.createElement("option");
            option.innerText = municipio.nm;
            option.value = municipio.nm;
            if(localidad == municipio.nm) option.selected = true;
            selectMunicipio.appendChild(option);
        });
    }
} 

const formulario = document.getElementById("formulario");

// formulario.addEventListener('submit', async function(event) {
//     // Evita que el formulario se envíe automáticamente
//     event.preventDefault();

//     const provincias = await obtenerProvincias();
//     provincias.forEach(provincia => {
//         if(provincia.id == selectProvincia.value) selectProvincia.value = provincia.nombre;
//     });

//    formulario.submit();

// });



// Llamar a la función para cargar las provincias en el select cuando el documento esté cargado
document.addEventListener('DOMContentLoaded', cargarProvinciasEnSelect);

