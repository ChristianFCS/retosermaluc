# Proyecto Reto Sermaluc

Este proyecto es una aplicación desarrollada como parte de la entrevista de trabajo de Sermaluc.

## Requisitos Previos

Antes de comenzar, asegúrate de tener instalado lo siguiente:

- [Java Development Kit (JDK) 21](https://www.oracle.com/java/technologies/javase-jdk21-downloads.html)
- [Maven](https://maven.apache.org/) (opcional, si usas otro gestor de dependencias, ajusta los pasos en consecuencia)
- [Git](https://git-scm.com/)

Puedes verificar las versiones instaladas usando los siguientes comandos:

```bash
java -version
mvn -version
git --version
```

## Instalación

Sigue estos pasos para configurar y levantar el proyecto:

### 1. Clonar el Repositorio

Clona el repositorio en tu máquina local:

```bash
git clone https://github.com/ChristianFCS/retosermaluc.git
cd retosermaluc
```

### 2. Configurar el Entorno

Configura las variables de configuración si es necesario. Puedes crear un archivo `application.properties` o `application.yml` en el directorio `src/main/resources` y agregar tus variables de configuración allí. 

```
server.port: Puerto sobre el cual se desplegará el software.
spring.datasource.url: cadena de conexión para la comunicación con la base de datos
spring.datasource.user: usuario de conexión con la base de datos.
spring.datasource.password: contraseña para la conexión con la base de datos
mybatis.config-location: indica la ubicación del archivo de configuración de mybatis.
```

### 3. Construir el Proyecto

Construye el proyecto usando Maven:

```bash
mvn clean install
```

### 4. Levantar el Servidor

Levanta el servidor utilizando Maven:

```bash
mvn spring-boot:run
```

Alternativamente, puedes ejecutar el archivo `jar` generado en el paso anterior:

```bash
java -jar target/retosermaluc-0.0.1-SNAPSHOT.jar
```

Por defecto, la aplicación estará disponible en `http://localhost:8080/`.

## Scripts Disponibles

En el archivo `pom.xml`, puedes encontrar varias configuraciones útiles:

- `mvn spring-boot:run`: Levanta el servidor de desarrollo.
- `mvn clean install`: Construye la aplicación.
- `mvn test`: Ejecuta los tests.

## Contacto

Si tienes alguna pregunta o sugerencia, no dudes en abrir un issue o contactarme a través de mi correo electrónico [ccondoris1@gmail.com](mailto:ccondoris1@gmail.com).
