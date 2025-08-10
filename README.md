# 📋 ForoHub API - Challenge Alura ONE

API REST completa para gestión de foros, desarrollada en **Spring Boot**, con autenticación **JWT** y base de datos **MySQL**.

---

## 🚀 Características

- ✅ **CRUD completo de tópicos** → Crear, leer, actualizar y eliminar.
- ✅ **Autenticación JWT** → Sistema de login seguro con tokens.
- ✅ **Base de datos MySQL** → Persistencia con JPA/Hibernate.
- ✅ **Validaciones** → Reglas de negocio y validación de datos.
- ✅ **Paginación** → Listados paginados y ordenados.
- ✅ **Spring Security** → Endpoints protegidos.
- ✅ **Arquitectura limpia** → Separación de responsabilidades.

---

## 🛠️ Tecnologías

- **Java 17**
- **Spring Boot 3.x**
- **Spring Security**
- **Spring Data JPA**
- **MySQL**
- **Flyway** (migraciones)
- **JWT** (autenticación)
- **Maven**

---

## 📁 Estructura del Proyecto

```
src/main/java/com/andres/forohub/
├── config/         # Configuraciones (Security, JWT)
├── controllers/    # Endpoints REST
├── dto/            # Data Transfer Objects
├── entities/       # Entidades JPA (Usuario, Curso, Tópico)
├── enums/          # Enumeraciones (StatusTopico)
├── exceptions/     # Manejo de excepciones
├── repositories/   # Interfaces Repository
├── services/       # Lógica de negocio
└── ForohubApplication.java
```

---

## 🗄️ Configuración de Base de Datos

### 1️⃣ Crear usuario MySQL
```sql
CREATE USER IF NOT EXISTS 'foro_user'@'localhost' IDENTIFIED BY 'Foro1234!';
GRANT ALL PRIVILEGES ON foro_hub.* TO 'foro_user'@'localhost';
FLUSH PRIVILEGES;
```

### 2️⃣ Variables de entorno
**Windows (PowerShell)**:
```powershell
$env:DB_USERNAME="foro_user"
$env:DB_PASSWORD="Foro1234!"
```
**Linux / Mac**:
```bash
export DB_USERNAME="foro_user"
export DB_PASSWORD="Foro1234!"
```

### 3️⃣ Ejecutar la aplicación
```bash
mvn spring-boot:run
```
📍 Disponible en: **http://localhost:8080**

---

## 📝 Endpoints Principales

### 🔐 Autenticación
```
POST /login
```

### 📋 Gestión de Tópicos
```
GET    /topicos           # Listar todos
GET    /topicos/paginado  # Listar paginado
GET    /topicos/{id}      # Obtener por ID
POST   /topicos           # Crear nuevo
PUT    /topicos/{id}      # Actualizar
DELETE /topicos/{id}      # Eliminar
```

### 🧪 Testing
```
GET /test/health  # Health check (público)
```

---

## 🔑 Usuarios de Prueba

| Email                     | Contraseña | Rol     |
|---------------------------|------------|---------|
| admin@forohub.com         | secret     | Admin   |
| juan.perez@email.com      | secret     | Usuario |
| maria.gonzalez@email.com  | secret     | Usuario |

---

## 📊 Datos de Ejemplo

- 3 usuarios de prueba
- 5 cursos: Spring Boot, Java, React, MySQL, JavaScript
- 3 tópicos de ejemplo

---

## 🧪 Cómo Probar la API

### 1️⃣ Health Check
```bash
curl http://localhost:8080/test/health
```

### 2️⃣ Login
```bash
curl -X POST http://localhost:8080/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@forohub.com","password":"secret"}'
```

### 3️⃣ Usar el token JWT
```bash
curl http://localhost:8080/topicos \
  -H "Authorization: Bearer {tu-jwt-token}"
```

---

## 📱 Testing con Postman / Insomnia

1. Importar la colección desde `/docs/api-collection.json`
2. Configurar la variable `{{base_url}}` = `http://localhost:8080`
3. Hacer login para obtener el token JWT
4. Usar el token en **Authorization: Bearer {token}**

---

## 🏗️ Reglas de Negocio

- ✅ No se permiten tópicos con el mismo título y mensaje.
- ✅ Todos los campos obligatorios validados.
- ✅ Usuario y curso deben existir y estar activos.
- ✅ Endpoints protegidos requieren JWT válido.
- ✅ Estados posibles: `ACTIVO`, `CERRADO`, `RESUELTO`.

---

## 📈 Características Técnicas

- **Arquitectura**: MVC con capas bien definidas.
- **Seguridad**: BCrypt para passwords, JWT para sesiones.
- **Base de datos**: Migraciones con Flyway.
- **Validaciones**: Bean Validation con anotaciones.
- **Errores**: `ResponseEntityExceptionHandler` personalizado.
- **Paginación**: Spring Data Pageable.
