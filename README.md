

<h1>Sistema de Gestión de Cursos con Microservicios usando Spring Boot</h1>

![Platform](https://img.shields.io/badge/Java-17%2B-red)
![Framework](https://img.shields.io/badge/Spring%20Boot-3.2.0-green)

## Descripción

Esta aplicación de Spring Boot basada en microservicios permite gestionar cursos y estudiantes, utilizando Hibernate, Swagger y Docker. La aplicación consta de tres microservicios:
1. <b>Microservicio de cursos:</b> Permite crear, leer, actualizar, eliminar y listar cursos.
2. <b>Microservicio de estudiantes:</b> Permite crear, leer, actualizar, eliminar y listar cursos.
3. <b>Microservicio de inscripciones:</b> Permite obtener información de los cursos y alumnos inscritos en ellos, así como inscribir y desinscribir estudiantes de cursos.

## Características

- Spring Boot 3.2.0
- Java 17+
- Maven
- Swagger
- Docker

## Dependencias

- Spring Web
- Spring Data JPA
- H2 Database
- Lombok
- Spring Boot Dev Tools

## Como usar la aplicación usando Docker

Para utilizar esta aplicación partiendo del repositorio se deberán llevar a cabo las siguientes acciones:

- Descargar el código, en formato ZIP o llevando a cabo el siguiente comando desde un terminal:
```
git clone https://github.com/mckiwen/EstudiantesCursos.git
```
- Acceder al directorio raiz de cada microservicio por separado y ejecutar lo siguiente:
```
mvn clean install
```
Llegados a este punto se pueden levantar los tres microservicios realizando su imagen de forma individual con Docker o de manera conjunta con Docker Compose.

<h3>1. Método individual con Docker</h3>

Cada microservicio consta de un fichero Dockerfile para construir la imagen de cada microservicio con Docker.
Para ello nos situamos en el directorio raíz del microservicio en cuestión y ejecutamos lo siguiente en el terminal:
```
docker build -t <imagename> .
```
Por ejemplo, para el caso del microservicio de estudiantes:
```
docker build -t microservicio-estudiantes:v1.0 .
```
Una vez hecho esto, se puede comprobar las imágenes creadas con:
```
docker image ls
```
Para ejecutar el microservicio se debe ejecutar lo siguiente:
```
docker run -p<port>:<port> <imagename>
```
o alternativamente
```
docker run -p<port>:<port> <image_id>
```
Para cada microservicio se ha configurado un puerto diferente:
```
microservicio-ursos: 8080
microservicio-estudiantes: 8081
microservcio-inscripciones: 8082
```

<h3>2. Método conjunto con Docker Compose</h3>

Utilizando Docker Compose se puede hacer lo anterior de manera más rápidas y conjunta. Dado que la aplicación contiene en su directorio raíz un fichero docker-compose.yml, nos debemos situar en el directorio raíz del proyecto completo y ejecutar lo siguiente:
```
docker-compose up --build
```
Con --build se realiza la construcción/actualización de la imagen, alternativamente se puede usar:
```
docker-compose up
```

## Descripcion de los microservicios (puertos, endpoints, url de swagger, base de datos) - hablar de postman


