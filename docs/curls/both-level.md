# 📌 Both Level Controller Documentation

## **Base Path:** `/api/v1/authscopes/both`

---

## 📝 **Available Operations**

### 🚀 **1. Get Greeting at Class Level**

**📌 Endpoint:** `GET /class-level`

#### 🔹 **Request:**

```bash
curl --location 'http://localhost:8085/api/v1/authscopes/both/class-level' \
--header 'X-Scopes: greeting'
```

#### 🔹 **Response Example:**

```json
{
  "message": "Both level: hello.",
  "executorScope": "greeting",
  "decoratorAt": "class level"
}
```

---

### 🚀 **2. Post Admin Data at Method Level**

**📌 Endpoint:** `POST /method-level-in-class-level`

#### 🔹 **Request:**

```bash
curl --location --request POST 'http://localhost:8085/api/v1/authscopes/both/method-level-in-class-level' \
--header 'X-Scopes: goodbye'
```

#### 🔹 **Response Example:**

```json
{
  "message": "Both level: bye!!",
  "executorScope": "goodbye",
  "decoratorAt": "method level"
}
```

---

📌 **Notas:**
- Modifica los valores de `X-Scopes` según los permisos requeridos.
- Este documento se actualizará a medida que se agreguen más endpoints.

📩 *Última actualización: **`26-03-2025`***

