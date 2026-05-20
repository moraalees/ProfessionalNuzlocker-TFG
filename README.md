<div align="center">

# PROFESSIONAL NUZLOCKER

![Logo de la app](app/src/main/res/drawable/logo.png)

### *Cristian Morales Canosa — 19/05/2026*

</div>

---

## Índice

| # | Sección |
|---|---------|
| 1 | [Introducción](#1-introducción) |
| 2 | [Descripción del proyecto](#2-descripción-del-proyecto) |
| 3 | [Objetivos del proyecto](#3-objetivos-del-proyecto) |
| 4 | [Alcance del proyecto](#4-alcance-del-proyecto) |
| 5 | [Requisitos del proyecto](#5-requisitos-del-proyecto) |
| 6 | [Planificación del proyecto](#6-planificación-del-proyecto) |
| 7 | [Plan de gestión de riesgos](#7-plan-de-gestión-de-riesgos) |
| 8 | [Diseño](#8-diseño) |
| 9 | [Instalación y preparación](#9-instalación-y-preparación) |
| 10 | [Documentación de ejecución y plan de calidad](#10-documentación-de-ejecución-y-plan-de-calidad) |
| 11 | [Distribución](#11-distribución) |
| 12 | [Manuales](#12-manuales) |
| 13 | [Conclusiones](#13-conclusiones) |
| 14 | [Anexos](#14-anexos) |
| 15 | [Índice de tablas e imágenes](#15-índice-de-tablas-e-imágenes) |
| 16 | [Bibliografía y referencias](#16-bibliografía-y-referencias) |

---

## 1. Introducción

### Justificación del proyecto

Pokémon es una de las sagas de videojuegos más populares y reconocidas del mundo, centrada en la exploración, la captura y el combate entre criaturas llamadas Pokémon. Dentro de esta franquicia, títulos como Pokémon Negro y Pokémon Blanco marcaron especialmente mi interés por la saga, tanto por su historia como por la variedad de personajes y criaturas.

En el ámbito de esta franquicia, un Nuzlocke es un reto autoimpuesto en estos juegos que lleva más de dos décadas de historia en la comunidad Pokémon. Sus reglas básicas, como capturar únicamente el primer Pokémon de cada ruta, perder de forma permanente a los que se debilitan, etc., convierten cualquier partida en una experiencia narrativa única, cargada de tensión y apego emocional hacia el equipo, agregándole dificultad y emoción a los que buscan algo más desafiante y diferente.

Sin embargo, gestionar un Nuzlocke manualmente supone un esfuerzo considerable, porque el jugador debe llevar la cuenta de qué rutas ya ha visitado, qué Pokémon ha capturado o perdido, qué combates importantes ha superado, cuántas vidas le quedan... Hoy en día, la mayoría de jugadores, bajo mi experiencia, recurren a hojas de cálculo, blocs de notas en teléfonos, o aplicaciones genéricas de listas, ninguna de las cuales están diseñadas específicamente para este reto.

Professional Nuzlocker es un proyecto que cubre específicamente esto, ya que su fin es el de ofrecer una herramienta móvil nativa, elegante y centrada en la experiencia de un Nuzlocke, que además integra inteligencia artificial (IA) para asistir al jugador durante la partida. 

Además del interés técnico y de diseño que supone desarrollar esta aplicación, el proyecto surge también de una motivación personal, ligada a mi afición por los juegos de Pokémon, especialmente Pokémon Blanco/Negro, y por otros referentes en plataformas de streaming como YT que impulsan mi afán por jugar de esta forma tan desafiante y divertida.

---

### Análisis comparativo de aplicaciones similares

Como aplicación de registro de Nuzlockes, no hay demasiada variedad. No obstante, sí existen herramientas que pueden llegar a ayudar de alguna forma a guardar el progreso de una partida.

A continuación se resumen las principales alternativas existentes y sus limitaciones:

| Aplicación / Herramienta | Plataforma | Proósito de Nuzlocke | Seguimiento de Pokémon | IA integrada |
|--------------------------|-----------|:----------------------:|:----------------------:|:------------:|
| Hojas de cálculo (Google Sheets) | Web / Móvil | ✗ | Manual | ✗ |
| Nuzlocke Tracker (web) | Web | ✓ | Orientado pero molesto | ✗ |
| Blocs de notas | Multiplataforma | ✗ | Manual | ✗ |
| **Professional Nuzlocker** | Android | ✓ | Orientado y lógico | ✓ |

Ninguna solución existente combina un seguimiento completo del Nuzlocke cómodo y suficientemente eficaz con una integración de IA funcional.

---

### Tendencias

El proyecto se enmarca en dos tendencias tecnológicas actuales:

- **Integración de IA en aplicaciones móviles:** La incorporación de modelos de lenguaje en apps de consumo es cada vez más habitual, como podemos observar en plataformas y servicios de Google (Gmail, Google Docs, Google Sheets, etc.). Esto se debe a que la IA sirve como herramienta eficaz a la hora de entender el contexto de la aplicación y cómo moverse por esta. Professional Nuzlocker utiliza esta tecnología para ofrecer respuestas personalizadas al estado real de la partida del usuario, ya que la UI es sencilla y cómoda de usar.

- **Desarrollo Android con Jetpack Compose:** La interfaz declarativa de Jetpack Compose es de los estándares más modernos para el desarrollo nativo en Android, facilitando la creación de interfaces reactivas, dinámicas y mantenibles con un código más compacto y legible. Además, su integración con las arquitecturas modernas de Android permite acelerar el desarrollo y mejorar la escalabilidad y mantenibilidad de la aplicación a largo plazo, lo cuál es indispensable para grandes proyectos.

---

### Beneficios o expectativas del proyecto

- El jugador **nunca pierde información** de su partida. Todas las capturas, muertes, combates y estadísticas quedan persistidas en la nube mediante Firebase Firestore, vinculadas a su cuenta personal. Incluso si el jugador decide empezar una nueva partida, su antiguo intento podrá ser guardado a través de la generación de un documento PDF con suficiente información para no olvidar nada.
- La **integración con NuzBot** reduce el tiempo que el jugador dedica a buscar información externa durante la partida, ya que la IA responde realmente rápido a cualquier pregunta.
- La aplicación sirve como **referencia técnica** de una arquitectura Android moderna (MVVM, Compose, Firebase, API REST) aplicada a un dominio concreto y bien acotado.
- Bajo temas personales, el principal beneficio es **la mera existencia** de una herramienta concreta para este tipo de retos que sea suficientemente eficaz y cómoda para poder ser usada de forma continua y diaria por aquellos que son apasionados por la saga.

---

## 2. Descripción del proyecto

### Tipo de proyecto

Como se ha comentado anteriormente, **Professional Nuzlocker** es una **aplicación Android nativa**, la cuál está desarrollada únicamente en Kotlin con Jetpack Compose. Sigue el patrón de arquitectura **MVVM** (Model-View-ViewModel) y utiliza Firebase como backend para autenticación y almacenamiento de datos en tiempo real.

---

### Características principales

| Característica | Descripción | Ventaja |
|----------------|-------------|---------|
| **Autenticación** | Registro e inicio de sesión con correo y contraseña mediante Firebase Auth. | Privacidad de partidas para cada usuario. |
| **Formulario de inicio** | Configuración de la partida (versión del juego, sexo, nombre e inicial). | Asistencia a la hora de empezar el registro. |
| **Registro de rutas** | Lista completa de rutas del juego. En cada ruta el jugador registra el encuentro, donde captura, debilita o espera al encuentro de Pokémon. | Libertad y ayuda para planear encuentros. |
| **Seguimiento de combates** | Registro de combates importantes con el equipo usado en cada uno. | Guía de ayuda para momentos críticos del juego. |
| **Gestión del equipo** | Vista detallada del equipo activo, PC y cementerio, con edición de mote, nivel, habilidad y evolución. | Fluidez a la hora del registro de Pokémon. |
| **Sistema de vidas** | Cada muerte descuenta una vida, donde al llegar a cero el Nuzlocke termina automáticamente. | Añadido de dificultad y ayuda al registro. |
| **NuzBot (IA)** | Asistente conversacional que responde preguntas sobre la partida en curso usando el contexto real de Firestore. | Asistencia clara para resolver dudas ofreciendo estrategias o datos. |
| **Estadísticas** | Resumen final con resultado, ranking de Pokémon más usados, combates más mortales, rivales más letales y distribución de tipos. | Datos importantes para recordar o revisar. |
| **Exportación PDF** | Generación y descarga de un informe completo en la carpeta Descargas del dispositivo. | Guardado del resultado para poder empezar una nueva. |
| **Pokédex integrada** | Navegador de los Pokémon disponibles con buscador por nombre, ofreciendo dónde capturar o cómo evolucionar cada uno. | Información relevante para cada Pokémon. |
| **Música y sonido** | Banda sonora con fundido cruzado al iniciar combates y efectos de sonido contextuales. | Ambiente al navegar por la UI. |

---

### Usuarios destinatarios

La aplicación, como es de esperar, está dirigida a un perfil único y bien definido:

> **Jugador de Pokémon aficionado al reto Nuzlocke**, con conocimientos básicos de las reglas del desafío, que quiere sustituir sus hojas de cálculo o notas manuales por una herramienta móvil dedicada, con la que sentirse a gusto registrando cualquier dato.

No se requiere ningún conocimiento técnico para usar la aplicación. El flujo introductorio guía al jugador desde el registro hasta el inicio de la partida de forma intuitiva. De ahí en adelante, todo es muy sencillo e intuible de usar.

Si el jugador novato e inexperto en el ámbito de los Nuzlockes quiere usar la aplicación, no estará perdido acerca de normas y comportamientos de este modo de juego, debido a que la [**Pantalla de Guía**](app/src/main/java/com/example/professionalnuzlocker/ui/screens/guide/PantallaGuia.kt) le informará de cualquier concepto que necesite saber.

---

## 3. Objetivos del proyecto

### Objetivo general

Desarrollar una aplicación Android completa que permita al usuario **registrar, seguir y analizar una partida Nuzlocke** de Pokémon Negro/Blanco, con persistencia en la nube e integración de un asistente de inteligencia artificial contextual.

---

### Objetivos específicos

1. **Implementar un sistema de autenticación seguro:** Logrado mediante Firebase Authentication, con validación de campos y mensajes de error localizados en español (ES).

2. **Diseñar y desarrollar la estructura de datos de la partida:** Todas las rutas, capturas, combates, y equipo, PC y cementerio y persistirla en tiempo real en Firebase Firestore.

3. **Construir el flujo completo de registro de la aventura:** Selección de ruta activa, captura o pérdida del Pokémon encontrado, registro de combates importantes con el equipo utilizado y gestión de muertes con causa detallada.

4. **Desarrollar la pantalla de gestión del equipo:** Funcionalidades de edición de datos del Pokémon y registro de evoluciones, diferenciando equipo activo, PC y cementerio.

5. **Integrar NuzBot:** Asistente de IA conversacional que recibe el contexto de la partida actual y responde preguntas del jugador a través de un endpoint REST protegido con Firebase ID Token.

6. **Generar un informe de estadísticas:** Al finalizar la partida muestra victoria/derrota, capturas, ranking de Pokémon más usados, combates más mortales, rivales más letales y distribución de tipos en una pantalla.

7. **Implementar la exportación a PDF:** El informe de estadísticas es exportado, guardándolo en la carpeta Descargas del dispositivo como archivo PDF.

8. **Garantizar una experiencia de usuario coherente y pulida:** Paleta de colores propia, música de fondo con fundido cruzado y efectos de sonido acordes al contexto de cada acción.

---

## 4. Alcance del proyecto

> *Sección pendiente de desarrollo.*

---

## 5. Requisitos del proyecto

> *Sección pendiente de desarrollo.*

---

## 6. Planificación del proyecto

> *Sección pendiente de desarrollo.*

---

## 7. Plan de gestión de riesgos

> *Sección pendiente de desarrollo.*

---

## 8. Diseño

> *Sección pendiente de desarrollo.*

---

## 9. Instalación y preparación

> *Sección pendiente de desarrollo.*

---

## 10. Documentación de ejecución y plan de calidad

> *Sección pendiente de desarrollo.*

---

## 11. Distribución

> *Sección pendiente de desarrollo.*

---

## 12. Manuales

> *Sección pendiente de desarrollo.*

---

## 13. Conclusiones

> *Sección pendiente de desarrollo.*

---

## 14. Anexos

> *Sección pendiente de desarrollo.*

---

## 15. Índice de tablas e imágenes

> *Sección pendiente de desarrollo.*

---

## 16. Bibliografía y referencias

> *Sección pendiente de desarrollo.*
