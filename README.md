# Práctica Spring Boot: Comparación de Acceso a Datos (BD vs Caché) y CORS

Proyecto para la materia **Diseño y Arquitectura de Software (ISWZ2202)**.

## Descripción
Aplicación en Java 17 con Spring Boot que inserta 10,000 registros de prueba en una base de datos H2 al iniciar y expone endpoints REST para comparar el tiempo de respuesta leyendo directamente desde la base de datos frente al uso de caché en memoria (`@Cacheable`). Incluye un frontend en HTML/JS para visualizar los tiempos de respuesta y probar la configuración de CORS.

## Estructura
- `backend/`: API REST en Spring Boot (JPA, H2 Database, Spring Cache).
- `frontend/`: Interfaz web (`index.html`) para consultar los endpoints con y sin caché y medir los tiempos.

## Cómo ejecutar el proyecto
1. Entrar a la carpeta `backend` y ejecutar:
   ```bash
   mvn spring-boot:run
   ```
2. Abrir `frontend/index.html` en el navegador (por ejemplo con Live Server en VS Code).

## Resultados de tiempos obtenidos (10,000 registros)
- **Sin caché (`/api/productos/sin-cache`):** ~130 ms - 150 ms por petición (consulta SQL completa a la BD).
- **Con caché - 1ra llamada (`/api/productos/con-cache`):** ~135 ms (carga los datos por primera vez y los guarda en caché).
- **Con caché - 2da llamada en adelante (`/api/productos/con-cache`):** ~1 ms - 2 ms (lee directo desde memoria RAM).

## Consulta: CORS en ambientes de desarrollo
El error de CORS ocurre porque en desarrollo el backend corre en `http://localhost:8080` y el frontend se abre desde otro puerto (como `http://127.0.0.1:5500` con Live Server). Como los puertos son distintos, el navegador bloquea la petición por la política *Same-Origin Policy*.

Para solucionarlo en desarrollo se implementó la clase `CorsConfig.java` (usando `WebMvcConfigurer`) y la anotación `@CrossOrigin(origins = "*")` en el controlador para permitir peticiones desde el origen local del frontend.
