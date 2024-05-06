document.addEventListener('DOMContentLoaded', async function() {
    console.log("Dentro de webSocket.js");

    // Recuperar el JSON almacenado en el div
    var subscribedProductsJsonFromDiv = document.getElementById('subscribedProductsJson').innerText;
    console.log("JSON de subscribedProductsJson:", subscribedProductsJsonFromDiv);

    // Verificar si el JSON no está vacío antes de intentar parsearlo
    if (subscribedProductsJsonFromDiv === 'null') {
        console.log("El JSON de productos suscritos está vacío.");
        return; // Salir de la función si el JSON está vacío
    }

    // Parsear la cadena JSON a un array de productos suscritos
    var subscribedProducts;
    try {
        subscribedProducts = JSON.parse(subscribedProductsJsonFromDiv);
        console.log("Productos suscritos:", subscribedProducts);
    } catch (error) {
        console.error("Error al parsear JSON:", error);
        return; // Salir de la función si hay un error al parsear el JSON
    }

    // Iterar sobre la lista de productos suscritos y suscribirse a las alertas
    for (let productId of subscribedProducts) {
        try {
            await subscribeToProductAlert(productId);
            console.log("Suscripción exitosa para el producto:", productId);
        } catch (error) {
            console.error("Error al suscribirse al producto:", productId, error);
        }
    }

    console.log("Todas las suscripciones se han completado.");
});

// Función para suscribirse a alertas específicas para un producto
function subscribeToProductAlert(productId) {
    return new Promise((resolve, reject) => {
        // Crear un nuevo cliente Stomp para cada suscripción
        var socket = new SockJS('/ws');
        var stompClient = Stomp.over(socket);

        stompClient.connect({}, function(frame) {
            console.log('Conectado al WebSocket para el producto:', productId);

            // Suscribirse a alertas específicas para el producto con el ID dado
            stompClient.subscribe('/topic/lastStockAlert/' + productId, function(message) {
                var messageBody = message.body;
                var messageContainer = document.createElement('div');
                messageContainer.innerText = messageBody;
                messageContainer.className = 'alertaStock'; // Aplicar clase de estilo
                messageContainer.classList.add( 'text-center');
                document.body.appendChild(messageContainer);
               
                   // Eliminar el mensaje después de 3 segundos (3000 milisegundos)
                   setTimeout(function() {
                    // Aplicar una transición de opacidad al remover el mensaje
                    messageContainer.style.transition = 'opacity 0.4s ease';
                    messageContainer.style.opacity = '0';

                    // Eliminar el mensaje del DOM después de la transición
                    setTimeout(function() {
                        messageContainer.remove();
                    }, 400); // Esperar a que termine la transición (400ms) antes de eliminar
                }, 10000);

            });

            resolve(); // Resolver la promesa una vez que la suscripción se establece correctamente
        }, function(error) {
            reject(error); // Rechazar la promesa si hay un error en la conexión
        });
    });
}
