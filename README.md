Configuración Inicial

Prueba Arkon

El proyecto está configurado con docker, utilizar el comando "docker-compose build" dentro de la carpeta "pruebaArkon" para construir la imagen de docker, la cual tiene una imagen de mysql para el funcionamiendo del servicio. Finalmente usar "docker-compose up" para levantar el contenedor.

Utilicé swagger para documentar los controladores, una vez levantado el contenedor, puede acceder en "http://localhost:8080/swagger-ui/index.html" para ver la descripción de los endpoint y probar el servicio.