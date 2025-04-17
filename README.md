# 🛒 service-product

Microserviço responsável pela **gestão de produtos** e seus respectivos estoques em uma arquitetura orientada a microsserviços.

---

## 📌 Objetivo

CRUD completo de produtos, batches (lotes) e lojas, utilizando arquitetura MVC simples com comunicação entre micros por Kafka e REST.

---

## ⚙️ Stack Tecnológica

- **Linguagem:** Java
- **Framework:** Spring Boot
- **Banco de dados:** PostgreSQL
- **Mensageria:** Kafka
- **Descoberta de serviços:** Eureka
- **Gateway de entrada:** Spring Gateway
- **Mapper:** MapStruct
- **Client HTTP:** Spring Feign
- **Leitura de CSV:** OpenCSV

---

## 📁 Estrutura

Arquitetura MVC (Controller → Service → Repository) com divisão de domínio em `products`, `stores` e `batches`.

---

## 🔗 Principais Endpoints

### 🏬 Stores

- `GET    /v1/stores`
- `POST   /v1/stores`
- `GET    /v1/stores/{storeId}`
- `PUT    /v1/stores/{storeId}`
- `DELETE /v1/stores/{storeId}`

### 📦 Products

- `GET    /v1/stores/{storeId}/products`
- `POST   /v1/stores/{storeId}/products`
- `GET    /v1/stores/{storeId}/products/{productId}`
- `PUT    /v1/stores/{storeId}/products/{productId}`
- `DELETE /v1/stores/{storeId}/products/{productId}`

### 🔁 Batches

- `GET    /v1/stores/{storeId}/batches/{batchId}`
- `PUT    /v1/stores/{storeId}/batches/{batchId}`
- `POST   /v1/stores/{storeId}/batches/{productId}`
- `GET    /v1/stores/{storeId}/product/{productId}`

---

## 🧪 Testes

- ✅ **Unitários:**
    - Services
    - Listeners Kafka

- ✅ **Integração:**
    - Controllers (com MockMvc ou WebTestClient)

Para executar os testes:
```bash
./mvnw test
