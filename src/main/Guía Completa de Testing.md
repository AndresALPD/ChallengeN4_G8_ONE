# 📋 Guía Completa de Testing - ForoHub API

## 🚀 URL Base
```
http://localhost:8080
```

---

## 🔐 1. AUTENTICACIÓN

### 📍 Login
**Endpoint:** `POST /login`  
**Descripción:** Autentica un usuario y retorna token JWT válido por 24 horas  
**Requiere autenticación:** ❌ No

**Request:**
```json
POST http://localhost:8080/login
Content-Type: application/json

{
    "email": "admin@forohub.com",
    "password": "secret"
}
```

**Response exitoso (200):**
```json
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJmb3JvaHViIiwic3ViIjoiYWRtaW5AZm9yb2h1Yi5jb20iLCJpZCI6MSwibm9tYnJlIjoiQWRtaW4gVXN1YXJpbyIsImV4cCI6MTY5MjkxNzUyM30...",
    "tipo": "Bearer",
    "usuarioId": 1,
    "nombreUsuario": "Admin Usuario",
    "email": "admin@forohub.com"
}
```

**Response error (401):**
```json
HTTP 401 Unauthorized
```

**Usuarios disponibles para pruebas:**
```json
// Usuario 1
{
    "email": "admin@forohub.com",
    "password": "secret"
}

// Usuario 2  
{
    "email": "juan.perez@email.com",
    "password": "juan123"
}

// Usuario 3
{
    "email": "maria.gonzalez@email.com", 
    "password": "maria123"
}
```

---

## 📝 2. GESTIÓN DE TÓPICOS

> **⚠️ IMPORTANTE:** Todos los endpoints de tópicos requieren autenticación JWT.  
> Incluir en headers: `Authorization: Bearer {tu-jwt-token}`

### 📍 Crear Tópico
**Endpoint:** `POST /topicos`  
**Descripción:** Crea un nuevo tópico en el foro  
**Requiere autenticación:** ✅ Sí

**Request:**
```json
POST http://localhost:8080/topicos
Content-Type: application/json
Authorization: Bearer {tu-jwt-token}

{
    "titulo": "¿Cómo configurar Spring Security con JWT?",
    "mensaje": "Necesito ayuda para implementar autenticación JWT en mi proyecto Spring Boot. ¿Alguien me puede orientar con las mejores prácticas?",
    "autorId": 1,
    "cursoId": 1
}
```

**Response exitoso (201):**
```json
{
    "id": 4,
    "titulo": "¿Cómo configurar Spring Security con JWT?",
    "mensaje": "Necesito ayuda para implementar autenticación JWT en mi proyecto Spring Boot. ¿Alguien me puede orientar con las mejores prácticas?",
    "fechaCreacion": "2025-08-09T18:30:45.123",
    "status": "ACTIVO",
    "autorId": 1,
    "nombreAutor": "Admin Usuario",
    "emailAutor": "admin@forohub.com",
    "cursoId": 1,
    "nombreCurso": "Spring Boot",
    "categoriaCurso": "Backend"
}
```

### 📍 Listar Todos los Tópicos
**Endpoint:** `GET /topicos`  
**Descripción:** Obtiene lista completa de todos los tópicos  
**Requiere autenticación:** ✅ Sí

**Request:**
```json
GET http://localhost:8080/topicos
Authorization: Bearer {tu-jwt-token}
```

**Response exitoso (200):**
```json
[
    {
        "id": 1,
        "titulo": "¿Cómo configurar Spring Security?",
        "mensaje": "Tengo dudas sobre la configuración básica de Spring Security en mi proyecto.",
        "fechaCreacion": "2025-08-09T10:00:00",
        "status": "ACTIVO",
        "nombreAutor": "Juan Pérez",
        "nombreCurso": "Spring Boot"
    },
    {
        "id": 2,
        "titulo": "Error con JPA Repository",
        "mensaje": "Mi repositorio no está funcionando correctamente, ¿alguna idea?",
        "fechaCreacion": "2025-08-09T11:30:00",
        "status": "ACTIVO",
        "nombreAutor": "María González",
        "nombreCurso": "Spring Boot"
    }
]
```

### 📍 Listar Tópicos Paginados
**Endpoint:** `GET /topicos/paginado`  
**Descripción:** Obtiene lista paginada ordenada por fecha descendente  
**Requiere autenticación:** ✅ Sí

**Request:**
```json
GET http://localhost:8080/topicos/paginado?page=0&size=5&sort=fechaCreacion,desc
Authorization: Bearer {tu-jwt-token}
```

**Parámetros de query opcionales:**
- `page`: Número de página (default: 0)
- `size`: Tamaño de página (default: 10)
- `sort`: Campo de ordenamiento (default: fechaCreacion,desc)

**Response exitoso (200):**
```json
{
    "content": [
        {
            "id": 3,
            "titulo": "Mejores prácticas en React",
            "mensaje": "Me gustaría conocer las mejores prácticas para componentes en React.",
            "fechaCreacion": "2025-08-09T15:45:00",
            "status": "ACTIVO",
            "nombreAutor": "Juan Pérez",
            "nombreCurso": "React"
        }
    ],
    "pageable": {
        "sort": {
            "sorted": true,
            "unsorted": false
        },
        "pageNumber": 0,
        "pageSize": 5
    },
    "totalElements": 3,
    "totalPages": 1,
    "last": true,
    "first": true,
    "numberOfElements": 3
}
```

### 📍 Obtener Tópico por ID
**Endpoint:** `GET /topicos/{id}`  
**Descripción:** Obtiene detalles completos de un tópico específico  
**Requiere autenticación:** ✅ Sí

**Request:**
```json
GET http://localhost:8080/topicos/1
Authorization: Bearer {tu-jwt-token}
```

**Response exitoso (200):**
```json
{
    "id": 1,
    "titulo": "¿Cómo configurar Spring Security?",
    "mensaje": "Tengo dudas sobre la configuración básica de Spring Security en mi proyecto.",
    "fechaCreacion": "2025-08-09T10:00:00",
    "status": "ACTIVO",
    "autorId": 2,
    "nombreAutor": "Juan Pérez",
    "emailAutor": "juan.perez@email.com",
    "cursoId": 1,
    "nombreCurso": "Spring Boot",
    "categoriaCurso": "Backend"
}
```

**Response error (404):**
```json
HTTP 404 Not Found
```

### 📍 Actualizar Tópico
**Endpoint:** `PUT /topicos/{id}`  
**Descripción:** Actualiza los datos de un tópico existente  
**Requiere autenticación:** ✅ Sí

**Request:**
```json
PUT http://localhost:8080/topicos/1
Content-Type: application/json
Authorization: Bearer {tu-jwt-token}

{
    "titulo": "¿Cómo configurar Spring Security correctamente? [ACTUALIZADO]",
    "mensaje": "Tengo dudas sobre la configuración básica de Spring Security en mi proyecto. Ya revisé la documentación oficial pero necesito ejemplos prácticos.",
    "autorId": 2,
    "cursoId": 1
}
```

**Response exitoso (200):**
```json
{
    "id": 1,
    "titulo": "¿Cómo configurar Spring Security correctamente? [ACTUALIZADO]",
    "mensaje": "Tengo dudas sobre la configuración básica de Spring Security en mi proyecto. Ya revisé la documentación oficial pero necesito ejemplos prácticos.",
    "fechaCreacion": "2025-08-09T10:00:00",
    "status": "ACTIVO",
    "autorId": 2,
    "nombreAutor": "Juan Pérez",
    "emailAutor": "juan.perez@email.com",
    "cursoId": 1,
    "nombreCurso": "Spring Boot",
    "categoriaCurso": "Backend"
}
```

### 📍 Eliminar Tópico
**Endpoint:** `DELETE /topicos/{id}`  
**Descripción:** Elimina un tópico permanentemente  
**Requiere autenticación:** ✅ Sí

**Request:**
```json
DELETE http://localhost:8080/topicos/1
Authorization: Bearer {tu-jwt-token}
```

**Response exitoso (204):**
```json
HTTP 204 No Content
```

**Response error (404):**
```json
HTTP 404 Not Found
```

---

## 🧪 3. ENDPOINT DE PRUEBA

### 📍 Health Check
**Endpoint:** `GET /test/health`  
**Descripción:** Verifica que la API está funcionando  
**Requiere autenticación:** ❌ No

**Request:**
```json
GET http://localhost:8080/test/health
```

**Response (200):**
```json
"ForoHub API está funcionando correctamente!"
```

---

## 📊 4. DATOS DE PRUEBA DISPONIBLES

### 👥 Usuarios:
```json
// ID: 1
{
    "id": 1,
    "nombre": "Admin Usuario",
    "email": "admin@forohub.com",
    "activo": true
}

// ID: 2  
{
    "id": 2,
    "nombre": "Juan Pérez",
    "email": "juan.perez@email.com",
    "activo": true
}

// ID: 3
{
    "id": 3,
    "nombre": "María González", 
    "email": "maria.gonzalez@email.com",
    "activo": true
}
```

### 📚 Cursos:
```json
// ID: 1
{
    "id": 1,
    "nombre": "Spring Boot",
    "categoria": "Backend",
    "activo": true
}

// ID: 2
{
    "id": 2,
    "nombre": "Java Orientado a Objetos",
    "categoria": "Backend", 
    "activo": true
}

// ID: 3
{
    "id": 3,
    "nombre": "React",
    "categoria": "Frontend",
    "activo": true
}

// ID: 4
{
    "id": 4,
    "nombre": "MySQL",
    "categoria": "Base de Datos",
    "activo": true
}

// ID: 5
{
    "id": 5,
    "nombre": "JavaScript",
    "categoria": "Frontend",
    "activo": true
}
```

---

## ⚠️ 5. CÓDIGOS DE ERROR COMUNES

### 🔐 Autenticación:
- **401 Unauthorized:** Token JWT inválido, expirado o no proporcionado
- **403 Forbidden:** Token válido pero sin permisos

### 📝 Validación:
- **400 Bad Request:** Datos inválidos o tópico duplicado
- **404 Not Found:** Recurso no encontrado (tópico, usuario, curso)

### 🛠️ Ejemplos de errores 400:
```json
// Campos obligatorios faltantes
{
    "titulo": "El título es obligatorio",
    "mensaje": "El mensaje es obligatorio"
}

// Tópico duplicado
"Ya existe un tópico con el mismo título y mensaje"

// Usuario o curso no encontrado
"Usuario con ID 999 no encontrado"
"Curso con ID 999 no encontrado"
```

---

## 🚀 6. FLUJO DE TESTING RECOMENDADO

### Paso 1: Verificar API
```json
GET http://localhost:8080/test/health
```

### Paso 2: Autenticarse
```json
POST http://localhost:8080/login
{
    "email": "admin@forohub.com",
    "password": "secret"
}
```

### Paso 3: Copiar token JWT de la respuesta

### Paso 4: Probar CRUD completo
1. Crear tópico → `POST /topicos`
2. Listar tópicos → `GET /topicos`
3. Obtener por ID → `GET /topicos/{id}`
4. Actualizar → `PUT /topicos/{id}`
5. Eliminar → `DELETE /topicos/{id}`

---

## 📱 7. CONFIGURACIÓN PARA INSOMNIA/POSTMAN

### Headers para endpoints protegidos:
```
Authorization: Bearer {tu-jwt-token}
Content-Type: application/json
```

### Variables de entorno sugeridas:
```
**Windows:**
```powershell
$env:DB_USERNAME="foro_user"  
$env:DB_PASSWORD="Foro1234!"

{{jwt_token}} = {token-obtenido-del-login}
```

---

**🔄 Recuerda:** Los tokens JWT expiran en 24 horas, así que necesitarás hacer login nuevamente si expira.