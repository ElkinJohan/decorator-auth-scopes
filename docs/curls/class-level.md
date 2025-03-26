# 📌 Class Level Controller Documentation

## **Base Path:** `/api/v1/authscopes/class-level`

---

## 📝 **Available Operations**

### 🚀 **1. Get Greeting for Boss**

**📌 Endpoint:** `GET /all-with-scopes/greeting`

#### 🔹 **Request:**

```bash
curl --location 'http://localhost:8085/api/v1/authscopes/class-level/all-with-scopes/greeting' \
--header 'X-Scopes: BOSS'
```

#### 🔹 **Response Example:**

```json
{
  "message": "Greetings!!!",
  "executorScope": "BOSS",
  "decoratorAt": "class level"
}
```

---

### 🚀 **2. Say Goodbye as Boss**

**📌 Endpoint:** `GET /all-with-scopes/bye`

#### 🔹 **Request:**

```bash
curl --location 'http://localhost:8085/api/v1/authscopes/class-level/all-with-scopes/bye' \
--header 'X-Scopes: BOSS'
```

#### 🔹 **Response Example:**

```json
{
  "message": "Goodbye!!!",
  "executorScope": "BOSS",
  "decoratorAt": "class level"
}
```

---

📌 **Notas:**
- Modifica los valores de `X-Scopes` según los permisos requeridos.
- Este documento se actualizará a medida que se agreguen más endpoints.

📩 *Última actualización: **`26-03-2025`***

