

<h1>Sistema de Gestión de Cursos con Microservicios usando Spring Boot</h1>

![Platform](https://img.shields.io/badge/Java-17%2B-red)
![Framework](https://img.shields.io/badge/Spring%20Boot-3.2.0-green)

## Descripción

Esta aplicación de Spring Boot basada en microservicios permite gestionar cursos y estudiantes, utilizando Hibernate, Swagger y Docker. La aplicación consta de tres microservicios:
1. <b>Microservicio de cursos:</b> Permite crear, leer, actualizar, eliminar y listar cursos.
2. <b>Microservicio de estudiantes:</b> Permite crear, leer, actualizar, eliminar y listar cursos.
3. <b>Microservicio de inscripciones:</b> Permite obtener información de los cursos y alumnos inscritos en ellos, así como inscribir y desinscribir estudiantes de cursos.

Los microservicios realizan peticiones HTTP entre ellas en caso de haber cambios relevantes como que se elimine un estudiante del registro o un curso. Inmediatamente dicho microservicio enviará esta información al microservicio de inscripciones para eliminar los registros de inscripciones relacionados con ese curso y/o estudiante.

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
microservicio-cursos: 8080
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

## Microservicios

Para el testeo de los microservicios se puede utilizar herramientas como Postman o Swagger, donde se pueden realizar peticiones HTTP a los diferentes Endpoints.

<h3>Microservicio de Cursos</h3>

El microservicio de cursos se encarga de gestionar la información de los cursos. Permite crear curso, leer un curso, listar los cursos, actualizar un curso y eliminar cursos.
<b>
- Puerto: 8080
- Base de datos H2
</b>

<h4><b>Swagger</b></h4>

La documentación de los endpoints de este microservicio se encuentra en la siguiente URL:
```
http://localhost:8080/swagger-ui/index.html
```
Una vez arrancado el microservicio, se realizarán las peticiones en 
```
http://localhost:8080/{ruta}
```
con las siguientes rutas
<table>
    <thead>
        <tr>
            <th>Método</th>
            <th>Ruta</th>
            <th>Descripción</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>GET</td>
            <td>/api/cursos</td>
            <td>Obtiene la lista completa de cursos</td>
        </tr>
        <tr>
            <td>GET</td>
            <td>/api/cursos/{id}</td>
            <td>Obtiene un curso dado un Id</td>
        </tr>
        <tr>
            <td>POST</td>
            <td>/api/cursos</td>
            <td>Crea un curso</td>
        </tr>
        <tr>
            <td>PUT</td>
            <td>/api/cursos</td>
            <td>Actualiza la información de un curso</td>
        </tr>
        <tr>
            <td>DELETE</td>
            <td>/api/cursos/{id}</td>
            <td>Elimina un curso dado su Id</td>
        </tr>
        <tr>
            <td>DELETE</td>
            <td>/api/cursos</td>
            <td>Elimina todos los cursos</td>
        </tr>
    </tbody>
</table>

Cuando se realiza la eliminación de algún curso, se realiza una petición HTTP al microservicio inscripción para eliminar todas las inscripciones relacioonadas con este curso eliminado.

<h3>Microservicio de Estudiantes</h3>

El microservicio de estudiantes se encarga de gestionar la información de los estudiantes. Permite crear un estudiante, leer un estudiante, listar los estudiantes, actualizar la información de un estudiante y eliminar estudiantes.
<b>
- Puerto: 8081
- Base de datos H2
</b>

<h4><b>Swagger</b></h4>

La documentación de los endpoints de este microservicio se encuentra en la siguiente URL:
```
http://localhost:8081/swagger-ui/index.html
```
Una vez arrancado el microservicio, se realizarán las peticiones en
```
http://localhost:8081/{ruta}
```
con las siguientes rutas
<table>
    <thead>
        <tr>
            <th>Método</th>
            <th>Ruta</th>
            <th>Descripción</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>GET</td>
            <td>/api/estudiantes</td>
            <td>Obtiene la lista completa de estudiantes</td>
        </tr>
        <tr>
            <td>GET</td>
            <td>/api/estudiantes/{id}</td>
            <td>Obtiene un estudiante dado un Id</td>
        </tr>
        <tr>
            <td>POST</td>
            <td>/api/estudiantes</td>
            <td>Crea un estudiante</td>
        </tr>
        <tr>
            <td>PUT</td>
            <td>/api/estudiantes</td>
            <td>Actualiza la información de un estudiante</td>
        </tr>
        <tr>
            <td>DELETE</td>
            <td>/api/estudiantes/{id}</td>
            <td>Elimina un estudiante dado su Id</td>
        </tr>
        <tr>
            <td>DELETE</td>
            <td>/api/estudiantes</td>
            <td>Elimina todos los estudiantes</td>
        </tr>
    </tbody>
</table>


Cuando se realiza la eliminación de algún estudiante, se realiza una petición HTTP al microservicio inscripción para eliminar todas las inscripciones relacioonadas con este estudiante eliminado.

<h3>Microservicio de Inscripciones</h3>

El microservicio de inscripciones se encarga de gestionar las inscripciones de estudiantes a cursos. La relación entre estudiantes y cursos es de muchos a muchos.
Este microservicio permite crear registros de inscripciones dados por los Ids de cursos y estudiantes. Al tener una relación de muchos a muchos, la entidad principal (Inscripción) tendrá una clave primaria compuesta por idCurso e idEstudiante.
Por lo tanto, este microservicio principalmente permite inscribir y desinscribir estudiantes a cursos.
<b>
- Puerto: 8082
- Base de datos H2
</b>

<h4><b>Swagger</b></h4>

La documentación de los endpoints de este microservicio se encuentra en la siguiente URL:
```
http://localhost:8082/swagger-ui/index.html
```
Una vez arrancado el microservicio, se realizarán las peticiones en
```
http://localhost:8082/{ruta}
```
con las siguientes rutas
<table>
    <thead>
        <tr>
            <th>Método</th>
            <th>Ruta</th>
            <th>Descripción</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>GET</td>
            <td>/api/inscripciones/curso/{idCurso}</td>
            <td>Obtiene la información de un curso y los estudiantes inscritos</td>
        </tr>
        <tr>
            <td>POST</td>
            <td>/api/inscripciones/curso</td>
            <td>Permite inscribir uno o más estudiantes a un curso</td>
        </tr>
        <tr>
            <td>DELETE</td>
            <td>/api/inscripciones/curso</td>
            <td>Desinscribe a uno o más estudiantes de un curso</td>
        </tr>
        <tr>
            <td>DELETE</td>
            <td>/api/inscripciones/curso/{idCurso}</td>
            <td>Elimina todas las inscripciones de un curso dado su idCurso</td>
        </tr>
        <tr>
            <td>DELETE</td>
            <td>/api/inscripciones</td>
            <td>Elimina todas las inscripciones</td>
        </tr>
        <tr>
            <td>DELETE</td>
            <td>/api/estudiantes/estudiante/{idEstudiante}</td>
            <td>Elimina todas las inscripciones relacionadas con un estudiante dado su idEstudiante</td>
        </tr>
    </tbody>
</table>

Cuando se realiza una petición GET y POST, se hace una conexión HTTP a los microservicios de cursos y estudiantes para recuperar la información completa.


