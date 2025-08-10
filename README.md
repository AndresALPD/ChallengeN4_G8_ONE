#📋 ForoHub API - Challenge Alura ONE
Una API REST completa para gestión de foros desarrollada en Spring Boot con autenticación JWT y base de datos MySQL.

🚀 Características
✅ CRUD completo de tópicos - Crear, leer, actualizar y eliminar tópicos
✅ Autenticación JWT - Sistema de login seguro con tokens
✅ Base de datos MySQL - Persistencia con JPA/Hibernate
✅ Validaciones - Reglas de negocio y validación de datos
✅ Paginación - Listados paginados y ordenados
✅ Spring Security - Endpoints protegidos
✅ Arquitectura limpia - Separación de responsabilidades

🛠️ Tecnologías
Java 17
Spring Boot 3.x
Spring Security
Spring Data JPA
MySQL
Flyway (migraciones)
JWT (autenticación)
Maven

📁 Estructura del Proyecto
src/main/java/com/andres/forohub/
├── config/           # Configuraciones (Security, JWT)
├── controllers/      # Endpoints REST
├── dto/             # Data Transfer Objects
├── entities/        # Entidades JPA (Usuario, Curso, Tópico)
├── enums/           # Enumeraciones (StatusTopico)
├── exceptions/      # Manejo de excepciones
├── repositories/    # Interfaces Repository
├── services/        # Lógica de negocio
└── ForohubApplication.java

🗄️ Configuración de Base de Datos
Crear usuario MySQL:
sqlCREATE USER IF NOT EXISTS 'foro_user'@'localhost' IDENTIFIED BY 'Foro1234!';
GRANT ALL PRIVILEGES ON foro_hub.* TO 'foro_user'@'localhost';
FLUSH PRIVILEGES;
Variables de entorno:
bash# Windows
$env:DB_USERNAME="foro_user"
$env:DB_PASSWORD="Foro1234!"

Linux/Mac
export DB_USERNAME="foro_user"
export DB_PASSWORD="Foro1234!"

Instalar MySQL
Crear el usuario foro_user (ver script SQL arriba)
Configurar variables de entorno

3. Ejecutar la aplicación:
bashmvn spring-boot:run
La aplicación estará disponible en: http://localhost:8080

📝 Endpoints Principales
🔐 Autenticación
httpPOST /login

📋 Gestión de Tópicos
httpGET    /topicos           # Listar todos
GET    /topicos/paginado  # Listar paginado
GET    /topicos/{id}      # Obtener por ID
POST   /topicos           # Crear nuevo
PUT    /topicos/{id}      # Actualizar
DELETE /topicos/{id}      # Eliminar

🧪 Testing
httpGET /test/health         # Health check (público)

🔑 Usuarios de Prueba
      Email                Contraseña           Rol
admin@forohub.com            secret            Admin
juan.perez@email.com         secret           Usuario
maria.gonzalez@email.com     secret           Usuario

📊 Datos de Ejemplo
La aplicación incluye datos iniciales:
3 usuarios de prueba
5 cursos (Spring Boot, Java, React, MySQL, JavaScript)
3 tópicos de ejemplo

🧪 Cómo Probar la API
1. Health Check:
bashcurl http://localhost:8080/test/health
2. Login:
bashcurl -X POST http://localhost:8080/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@forohub.com","password":"secret"}'
3. Usar el token JWT en endpoints protegidos:
bashcurl http://localhost:8080/topicos \
  -H "Authorization: Bearer {tu-jwt-token}"

📱 Testing con Postman/Insomnia
Importar la colección desde /docs/api-collection.json
Configurar variable {{base_url}} = http://localhost:8080
Hacer login para obtener JWT token
Usar token en header Authorization: Bearer {token}

🏗️ Reglas de Negocio
✅ No duplicados: No se permiten tópicos con mismo título y mensaje
✅ Validaciones: Todos los campos obligatorios validados
✅ Referencias: Usuario y curso deben existir y estar activos
✅ Autenticación: Endpoints protegidos requieren JWT válido
✅ Estados: Tópicos pueden estar ACTIVO, CERRADO o RESUELTO

📈 Características Técnicas
Arquitectura: MVC con capas bien definidas
Seguridad: BCrypt para passwords, JWT para sesiones
Base de datos: Migrations con Flyway
Validaciones: Bean Validation con anotaciones
Manejo de errores: ResponseEntityExceptionHandler personalizado
Paginación: Spring Data Pageable
