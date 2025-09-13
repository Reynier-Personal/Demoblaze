DEMOBLAZE E2E TESTING - INSTRUCCIONES DE EJECUCIÓN
========================================================

DESCRIPCIÓN DEL PROYECTO:
Este proyecto implementa pruebas automatizadas End-to-End (E2E) para el sitio web de e-commerce 
Demoblaze (https://www.demoblaze.com/) utilizando Serenity BDD con JUnit 5 y Selenium WebDriver.

REQUISITOS DEL SISTEMA Y COMPATIBILIDAD:
========================================

VERSIONES REQUERIDAS:
--------------------
- Java: JDK 17+ (configurado para Java 17 LTS en el proyecto)
- Maven: 3.8.0 o superior (recomendado 3.9.x)
- Navegadores soportados:
  * Google Chrome 90+ (recomendado)
  * Mozilla Firefox 88+
  * Microsoft Edge 90+

SISTEMAS OPERATIVOS COMPATIBLES:
-------------------------------
- Windows 10/11 (x64)
- macOS 10.15+ (Intel y Apple Silicon)
- Linux Ubuntu 18.04+ / CentOS 7+

VERIFICACIÓN DE REQUISITOS:
==========================
Ejecutar los siguientes comandos para verificar las instalaciones:

java -version
mvn -version
chrome --version

ESTRUCTURA DEL PROYECTO:
=======================
src/main/java/org/example/
├── config/                   # Configuración centralizada del proyecto
│   ├── TestConfiguration.java # Configuración principal (URLs, timeouts, datos)
│   └── WebDriverConfig.java   # Configuración específica del navegador
├── pages/                     # Page Objects (patrón Page Object Model)
│   ├── HomePage.java          # Página principal
│   ├── ProductPage.java       # Página de producto individual
│   ├── CartPage.java         # Página del carrito de compras
│   └── CheckoutPage.java     # Página de checkout y confirmación
├── tasks/                    # Tareas reutilizables (principio SOLID)
│   ├── NavigationTask.java   # Tareas de navegación
│   ├── ProductSelectionTask.java # Tareas de selección de productos
│   ├── CartValidationTask.java   # Validaciones del carrito
│   └── CheckoutTask.java     # Tareas de checkout
└── utils/                    # Utilidades y helpers
    ├── TestDataProvider.java # Proveedor de datos (usa TestConfiguration)
    └── LocatorManager.java   # Gestor de localizadores dinámicos

src/test/java/org/example/
└── tests/
    └── CompraTest.java       # Clase principal de pruebas E2E

INSTRUCCIONES PASO A PASO:
=========================

PASO 1: PREPARACIÓN DEL ENTORNO
-------------------------------
1. Abrir terminal/command prompt
2. Navegar al directorio del proyecto:
   cd c:..\Demoblaze\Demoblaze

3. Verificar que Maven puede resolver las dependencias:
   mvn clean compile

PASO 2: EJECUCIÓN DE TODAS LAS PRUEBAS
--------------------------------------
Para ejecutar todas las pruebas E2E:

mvn clean verify

Este comando:
- Limpia compilaciones anteriores
- Compila el código fuente y de pruebas
- Ejecuta todas las pruebas
- Genera reportes de Serenity

PASO 3: EJECUCIÓN DE PRUEBAS ESPECÍFICAS
----------------------------------------
Para ejecutar solo la prueba principal de compra completa:

mvn clean verify -Dtest=CompraTest#testCompleteE2EPurchaseFlow

Para ejecutar solo las validaciones del homepage:

mvn clean verify -Dtest=CompraTest#testHomepageValidation

PASO 4: OPCIONES DE CONFIGURACIÓN
---------------------------------
Ejecutar en modo headless (sin interfaz gráfica):

mvn clean verify -Dheadless.mode=true

Ejecutar con Firefox en lugar de Chrome:

mvn clean verify -Dwebdriver.driver=firefox

Ejecutar con URL personalizada:

mvn clean verify -Dbase.url=https://www.demoblaze.com/

PASO 5: VISUALIZACIÓN DE REPORTES
---------------------------------
Después de la ejecución, los reportes se generan automáticamente en:

UBICACIÓN DEL REPORTE:
target/site/serenity/index.html

CÓMO ABRIR EL REPORTE:
- Windows: start target\site\serenity\index.html
- Manualmente: Navegar a la carpeta del proyecto y abrir el archivo en cualquier navegador web

CONTENIDO DEL REPORTE:
- Resumen ejecutivo de todas las pruebas
- Screenshots paso a paso de cada acción
- Tiempos de ejecución detallados
- Logs de errores (si los hay)
- Métricas de rendimiento

PASO 6: INTERPRETACIÓN DE RESULTADOS
------------------------------------
El reporte de Serenity incluye:
- Resumen de ejecución de pruebas
- Screenshots paso a paso
- Detalles de cada validación
- Tiempos de ejecución
- Logs detallados de errores (si los hay)

CONFIGURACIÓN DEL PROYECTO:
==========================
El proyecto utiliza una arquitectura de configuración centralizada:

CONFIGURACIÓN CENTRALIZADA (TestConfiguration.java):
- URLs del sistema (base, páginas específicas)
- Timeouts y esperas (default, extendido, corto)
- Datos de cliente realistas (María Elena Rodríguez - Argentina)
- Mensajes esperados del sistema
- Configuración de reportes y localizadores

CONFIGURACIÓN DEL NAVEGADOR:
- Navegador maximizado automáticamente al iniciar
- Timeouts optimizados para elementos dinámicos
- Manejo robusto de sincronización
- Configuración heredada desde BaseTest para mejor mantenibilidad

FLUJO DE PRUEBAS IMPLEMENTADO:
=============================
Las pruebas E2E incluyen los siguientes casos ordenados:

1. NAVEGACIÓN INICIAL:
   - Abrir homepage de Demoblaze
   - Validar que la categoría "Phones" esté visible

2. SELECCIÓN DE PRODUCTOS:
   - Seleccionar primer producto de la categoría Phones
   - Agregar al carrito con validación de alert
   - Seleccionar segundo producto diferente
   - Agregar al carrito con validación de alert

3. VALIDACIÓN DEL CARRITO:
   - Navegar a la página del carrito
   - Validar que hay exactamente 2 productos
   - Verificar nombres correctos de productos
   - Validar que el total sea la suma de precios individuales

4. PROCESO DE COMPRA:
   - Proceder al checkout
   - Validar campos del formulario visibles y editables
   - Completar formulario con datos de prueba:
     * Nombre: Juan Perez
     * País: Colombia
     * Ciudad: Bogota
     * Tarjeta: 4111111111111111
     * Mes: 12
     * Año: 2025

5. CONFIRMACIÓN:
   - Validar mensaje "Thank you for your purchase!"
   - Verificar presencia de ID de transacción
   - Confirmar que se muestra el monto

SOLUCIÓN DE PROBLEMAS COMUNES:
==============================

1. ERROR DE JAVA VERSION:
   - Verificar que Java esté instalado: java -version
   - Proyecto configurado para Java 17 LTS (compatible con Java 17+)
   - Instalar JDK 17+ si es necesario desde: https://adoptium.net/

2. ERROR DE MAVEN:
   - Verificar instalación: mvn -version
   - Descargar desde: https://maven.apache.org/download.cgi

3. CONFIGURACIÓN DEL PROYECTO:
- Java 17 LTS (configurado en el proyecto, compatible con Java 17+)
- Maven 3.6.0 o superior
- Google Chrome (versión 90 o superior)
- Conexión a Internet para descargar dependencias y acceder a https://www.demoblaze.com/
   - Verificar que no haya proxy o firewall bloqueando

4. PROBLEMAS DE DRIVER:
   - WebDriverManager descarga drivers automáticamente
   - Si hay problemas, ejecutar: mvn clean compile

5. PROBLEMAS DE TIMEOUT:
   - Los timeouts están configurados en serenity.properties
   - Para conexiones lentas, aumentar timeout en el archivo

6. PROBLEMAS DE COMPILACIÓN:
   - Limpiar y recompilar: mvn clean compile
   - Verificar que no haya errores de sintaxis

7. PROBLEMAS DE PERMISOS (Windows):
   - Ejecutar terminal como Administrador
   - Verificar permisos de escritura en la carpeta del proyecto

ARCHIVOS DE CONFIGURACIÓN:
==========================
- pom.xml: Dependencias y configuración de Maven
- serenity.properties: Configuración de Serenity BDD
- README.md: Documentación técnica detallada

INSTALACIÓN PASO A PASO:
=======================

PARA WINDOWS:
1. Descargar e instalar JDK 17 desde: https://adoptium.net/
2. Descargar Maven desde: https://maven.apache.org/download.cgi
3. Extraer Maven y agregar bin/ al PATH del sistema
4. Verificar instalaciones con: java -version y mvn -version

PARA LINUX (Ubuntu/Debian):
1. sudo apt update
2. sudo apt install openjdk-17-jdk maven
3. Verificar instalaciones con: java -version y mvn -version

EJECUCIÓN RÁPIDA:
================
1. Abrir terminal en la carpeta del proyecto
2. Ejecutar: mvn clean verify
3. Esperar a que termine la ejecución
4. Abrir reporte en: target/site/serenity/index.html

NOTAS IMPORTANTES:
=================
- Las pruebas interactúan con el sitio web real de Demoblaze
- Los datos de compra son ficticios y no generan transacciones reales
- El navegador se maximiza automáticamente
- Todas las validaciones incluyen manejo de errores robusto
