# 🧑‍💼 Proyecto: Gestión de Funcionarios (Java + Swing + DAO + Excepciones)

Este proyecto implementa una aplicación de escritorio en **Java (Swing)** que permite **crear, listar, editar y eliminar funcionarios**, aplicando el **patrón de diseño DAO** y el **manejo de excepciones personalizadas**.

---

## 🚀 Características

- CRUD completo sobre la tabla `funcionario`
- Patrón **DAO (Data Access Object)** para acceso a datos desacoplado
- Manejo centralizado de errores con la clase `DAOException`
- Interfaz gráfica con **Swing**
- Base de datos **MySQL / MariaDB**
- Scripts SQL para crear y poblar las tablas
- Código modular organizado por paquetes

---

## 🧱 Estructura del proyecto
FuncionarioProject/
├── sql/
│ ├── schema.sql # Script de creación de base de datos
│ └── seed.sql # Datos iniciales
│
├── src/com/rh/
│ ├── model/ # Clases de dominio
│ │ └── Funcionario.java
│ │
│ ├── dao/ # Interfaces DAO y excepciones
│ │ ├── DAOException.java
│ │ ├── FuncionarioDAO.java
│ │ └── impl/
│ │ └── FuncionarioDAOImpl.java
│ │
│ ├── util/ # Utilidades de conexión
│ │ └── DBConnection.java
│ │
│ └── app/ # Interfaz de usuario (Swing)
│ ├── FuncionarioApp.java
│ └── FuncionarioFormDialog.java

## 🗄️ Configuración de la base de datos

1. Crea la base de datos y tablas ejecutando:
   ```bash
   mysql -u root -p < sql/schema.sql
   mysql -u root -p < sql/seed.sql