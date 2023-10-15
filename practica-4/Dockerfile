# Probando el concepto de Multi-stage.
# Instalando Gradle para compilar al aplicación y luego lo necesario a una imagen completa.
FROM gradle:8.2.1-jdk17-jammy AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle bootjar --no-daemon

# Utilizando uan imagen con lo necesario para arrancar la aplicación.
# en Java las imagenes slim son las mas pequeñas en ese tipo.
FROM eclipse-temurin:17.0.8.1_1-jre-alpine
# Variable de Sistema Puerto
# Indicando variable de ambiente para pasar el nombre
# de la base de datos.
# Indicando variable de ambiente para pasar el nombre
# de la base de datos.
ENV NOMBRE_APP = 'micro-estudiante'
# forma estandar para cambiar la configuracion
ENV DB_NAME=micro_estudiante
# Puedo pasar las variables directas que serán tomadas por Spring Boot.
ENV spring.datasource.username='root'
ENV spring.datasource.password='admin'
# ENV spring.datasource.url='jdbc:mysql://192.168.77.10:3306/micro_estudiante'


# Añadiendo el punto de montaje en el host
# Por defecto Tomcat crea los archivo temporales en esa ruta,
# lo habilitamos para ver los log, no es necesario para nuestro ejemplo.
VOLUME /tmp

# Puertos que estarán disponibles de nuestra aplicación.
EXPOSE 8080
# Copiando el archivo jar generado luego de la ejecución del comando
# gradle task bootjar, se crean el jar y se copia a la imagen.
COPY --from=build /home/gradle/src/build/libs/*.jar mi_app.jar

#Comando que se ejecuta una vez es iniciada la aplicación.
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","mi_app.jar"]