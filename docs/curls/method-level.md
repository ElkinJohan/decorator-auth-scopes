# 📌 Method Level Controller Documentation

## **Base Path:** `/api/v1/authscopes/method-level`

---

## 📝 **Available Operations**

### 🚀 **1. Get Greeting with Scope**

**📌 Endpoint:** `GET /scopes/hello`

#### 🔹 **Request:**

```bash
curl --location 'http://localhost:8085/api/v1/authscopes/method-level/scopes/hello' \
--header 'X-Scopes: WRITE READ'
```

#### 🔹 **Response Example:**

```json
{
  "message": "Hello with scopes.",
  "executorScope": "WRITE or READ",
  "decoratorAt": "method level"
}
```

---

### 🚀 **2. Get Greeting without Decorator**

**📌 Endpoint:** `GET /no-decorator/hi`

#### 🔹 **Request:**

```bash
curl --location 'http://localhost:8085/api/v1/authscopes/method-level/no-decorator/hi'
```

#### 🔹 **Response Example:**

```json
{
  "message": "Hi without decorator!!!",
  "executorScope": "no scopes",
  "decoratorAt": "no decorator"
}
```

---

📌 **Notas:**
- Modifica los valores de `X-Scopes` según los permisos requeridos.
- Este documento se actualizará a medida que se agreguen más endpoints.

📩 *Última actualización: **`26-03-2025`***

