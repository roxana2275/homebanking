EMAIL 1

From: Manuel Figueira
To: You
Subject: Bienvenida al proyecto Home Banking
¡Bienvenido equipo al proyecto de Home Banking!
Nuestro banco, Mindhub Brothers, tiene una historia de más de 50 años en el mercado,
brindando estabilidad y respaldo a millones de clientes sobre sus bienes así como también
múltiples servicios y préstamos a tasas muy competitivas.
En la actualidad los clientes buscan realizar sus operaciones bancarias lo más rápido posible
y sin tener que pasar por muchos inconvenientes, es por ello que en pro de mantener la
calidad y el buen servicio que provee el Mindhub Brothers deseamos crear un sistema que
en una primera etapa provea algunas de las funcionalidades que actualmente se pueden
realizar de manera presencial en el banco, si todo va bien podremos seguir agregando más.
Por lo tanto nos gustaría que construyan una aplicación que permita realizar operaciones
bancarias desde cualquier dispositivo, pc, teléfono, tablet a través de una aplicación web. Si
la aplicación web tiene éxito podríamos pensar en crear una aplicación móvil que utiliza los
mismo servicios.
Estamos muy emocionados de ver qué nos pueden presentar.
¡Gracias!
Manuel
Mindhub Brothers Bank

EMAIL 2

From: Laureano Andreotti
To: You
Subject: Plan del proyecto Home Banking
Equipo,
Asumo que recibieron el correo de Manuel. Estamos muy contentos de recibir este proyecto
de un nuevo cliente así que debemos dejar una buena impresión para que nos asignen más
proyectos en el futuro.
Luego de analizar el requerimiento y tener en cuenta las necesidades futuras he llegado a la
conclusión de que necesitamos seguir una arquitectura de cliente/servidor, por un lado
tendremos el código de front-end - cliente (como aplicaciones web, aplicaciones nativas
móviles) que tendrá toda la parte visual y las interacciones con el usuario y por otro el
código de back-end - servidor que administrará las cuentas de los clientes del banco, por
ejemplo el estado de cuenta, transacciones realizadas, préstamos adquiridos y demás
operaciones, la comunicación entre ámbas partes se llevará a cabo a través de peticiones
HTTP enviando y recibiendo la información en formato JSON. De esta manera podemos en el
futuro crear otras aplicaciones que utilicen el mismo back-end - servidor sin tener que
cambiar nada y eligiendo la tecnología de front-end que se desee.
Como nos piden en primera instancia una aplicación web para interactuar con el servidor,
usaremos HTML 5, CSS y javascript, utilizando Vue.js, Bootstrap y Axios para facilitar el
desarrollo. Esto permitirá a los clientes acceder desde cualquier dispositivo desde un
navegador web, así los clientes con dispositivos móviles podrán operar hasta que se decida
crear una aplicación nativa.
Para el back-end utilizaremos Java con el framework Spring, utilizando la capa llamada
Spring Boot que nos permite hacer las configuraciones a la hora de crear aplicaciones
basadas en Java de manera rápida y sencilla, también utilizaremos la capa Spring Data JPA
para eliminar la necesidad de implementar consultas SQL directamente en el código, solo
necesitaremos los repositorios de cada entidad. Además la capa Spring Web, el cual nos
permite iniciar un servidor, trabajar con la arquitectura MVC, también nos provee una
manera de crear fácilmente servicios REST que permiten enviar y recibir información a través
de peticiones HTTP, cualquier programa que pueda enviar peticiones HTTP (como los
navegadores) podrá acceder al back-end - servidor.
Como primer paso debemos configurar el ambiente de desarrollo y crear el esqueleto de la
aplicación, utilizaremos la herramienta spring initializr para realizar ese proceso de manera
sencilla, además se debe crear la primera entidad llamada Client y crear un cliente de prueba
en la base de datos. Una vez que se termine de realizar el esqueleto de la aplicación se debe
enviar un reporte de las herramientas instaladas.
¡Gracias!
Laureano
