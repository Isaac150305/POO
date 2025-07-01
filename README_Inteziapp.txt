Inteziapp - Software diseñado para Intezia, empresa de consultoría en IA.
Creadores: Christian Vilera e Isaac Rodriguez.

Inteziapp es una aplicación de escritorio desarrollada en Java utilizando JavaFX como framework gráfico. El sistema permite gestionar reportes diarios, visualizar historiales, manejar usuarios y organizar reuniones internas, todo desde una interfaz amigable y moderna.

🛠 Requisitos del sistema

- Java Development Kit (JDK) 17 o superior
- JavaFX SDK 17 o superior
- NetBeans IDE (recomendado: 15 o superior)
- Sistema operativo: Windows, macOS o Linux

📦 Instalación paso a paso

1. Descarga JavaFX

- Sitio: https://gluonhq.com/products/javafx/
- Extrae el SDK, por ejemplo en:
  C:/javafx-sdk-<version>/

2. Configurar JavaFX en NetBeans

A. Agregar librerías

- NetBeans > Tools → Libraries → New Library
- Nombra: JavaFX
- Add JAR/Folder → C:/javafx-sdk-<version>/lib

B. Configurar el proyecto

- Clic derecho al proyecto > Properties > Libraries
- Add Library → JavaFX
- Run > VM Options:
  --module-path "C:/javafx-sdk-<version>/lib" --add-modules javafx.controls,javafx.fxml

📁 Estructura del proyecto

src/
├── inteziapp/
│   └── Inteziapp.java
├── ui/
│   ├── LoginUI.java
│   ├── DashboardUI.java
│   ├── ReportesUI.java
│   ├── TablasUI.java
│   ├── ReunionUI.java
│   └── login.css
├── modelo/
│   └── Usuario.java
│   └── Reporte.java
├── dao/
│   ├── ReporteDAO.java
│   └── ReporteJSONDAO.java
├── servicio/
│   └── Autorizacion.java
├── util/
     ├── Navegador.java
     └── login.css

💾 Almacenamiento de datos

Los datos de reportes se guardan en archivos reportes.json dentro del directorio raíz.

🖼 Recursos visuales

Las imágenes se cargan desde internet (URLs). Se necesita conexión para visualizarlas.

▶️ Cómo ejecutar la aplicación

- Ejecuta Inteziapp.java
- Los usuarios pueden ser visualizados en el main (Inteziapp.java)

❓ Preguntas frecuentes

- ¿Dónde se guardan los reportes?
  → En reportes.json en la raíz del proyecto.

- ¿Necesito más librerías?
  → No. Solo JavaFX y JDK.

- ¿Funciona sin internet?
  → Sí, excepto imágenes externas.

📃 Licencia

Proyecto educativo. Uso libre.