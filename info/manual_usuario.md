# Manual de instalación y uso — Professional Nuzlocker

---

## Índice

1. [Requisitos del dispositivo](#1-requisitos-del-dispositivo)
2. [Instalación de la aplicación](#2-instalación-de-la-aplicación)
3. [Primer arranque](#3-primer-arranque)
4. [Registro de cuenta](#4-registro-de-cuenta)
5. [Inicio de sesión](#5-inicio-de-sesión)
6. [Pantalla de inicio](#6-pantalla-de-inicio)
7. [Gestión de cuenta](#7-gestión-de-cuenta)
8. [Guía Nuzlocke](#8-guía-nuzlocke)
9. [Configuración de nueva partida](#9-configuración-de-nueva-partida)
10. [Pantalla de registro — flujo principal](#10-pantalla-de-registro--flujo-principal)
11. [Registro de rutas y encuentros](#11-registro-de-rutas-y-encuentros)
12. [Registro de combates importantes](#12-registro-de-combates-importantes)
13. [Registro de muerte de un Pokémon](#13-registro-de-muerte-de-un-pokémon)
14. [Fin del Nuzlocke](#14-fin-del-nuzlocke)
15. [Pantalla Mi Partida — Equipo, PC y Cementerio](#15-pantalla-mi-partida--equipo-pc-y-cementerio)
16. [Pokédex](#16-pokédex)
17. [NuzBot — Asistente IA](#17-nuzbot--asistente-ia)
18. [Estadísticas y exportación PDF](#18-estadísticas-y-exportación-pdf)
19. [Control del audio](#19-control-del-audio)
20. [Empezar una nueva partida sobre una existente](#20-empezar-una-nueva-partida-sobre-una-existente)

---

## 1. Requisitos del dispositivo

Para instalar y ejecutar Professional Nuzlocker es necesario cumplir los siguientes requisitos mínimos:

- Dispositivo Android con versión **8.0 (API 26) o superior**.
- Conexión a internet activa. Todas las funciones de la aplicación dependen de servicios en la nube, por lo que la autenticación y el almacenamiento de datos, que requieren Firebase, funcionan con conexión. Además, el asistente NuzBot requiere el backend desplegado en Render.
- Espacio suficiente en almacenamiento interno para instalar el APK y para guardar el archivo PDF exportado en la carpeta de Descargas.

---

## 2. Instalación de la aplicación

Professional Nuzlocker no se distribuye a través de Google Play Store. El archivo de instalación es un APK firmado que se transfiere e instala directamente en el dispositivo.

**Pasos para instalar el APK:**

1. Transferir el archivo `app-release.apk` al dispositivo mediante cable USB, correo electrónico, o cualquier otro medio de transferencia de archivos.
2. En el dispositivo, abrir el explorador de archivos y localizar el APK en la carpeta donde se haya guardado.
3. Pulsar sobre el archivo. El sistema mostrará una advertencia indicando que la fuente es desconocida.
4. Si es la primera vez que se instala una aplicación de origen desconocido, el sistema pedirá permiso para habilitarlo. Acceder a los ajustes indicados, activar la opción correspondiente y volver al instalador.
5. Pulsar "Instalar" y esperar a que se instale.
6. Una vez completada la instalación busca la aplicación en tus pantallas y ábrela.

---

## 3. Primer arranque

Al abrir la aplicación por primera vez, la música de fondo comienza a reproducirse de forma automática. La aplicación comprueba si existe una sesión activa de Firebase Authentication.

- Si no hay ninguna sesión activa, se muestra `PantallaLogin`.
- Si ya existe una sesión guardada (no es el caso), se navega directamente a la `PantallaInicio` del menú principal.

---

## 4. Registro de cuenta

Si no se tiene cuenta, pulsar el enlace `*¿No tienes cuenta? Regístrate"` en la pantalla de inicio de sesión para acceder al formulario de creación de cuenta.

La pantalla de registro contiene tres campos:

- **Correo electrónico**: Debe tener un formato válido.
- **Contraseña**: Mínimo 8 caracteres. El icono del ojo situado a la derecha del campo permite mostrar u ocultar los caracteres introducidos.
- **Confirmar contraseña**: debe coincidir exactamente con la contraseña introducida en el campo anterior.

Una vez completados los tres campos correctamente, pulsar el botón `"Registrarse"`. Mientras se procesa la solicitud, el botón muestra un indicador de carga circular y queda deshabilitado para evitar envíos duplicados.

Si se produce algún error de validación, se mostrará un mensaje en rojo debajo del campo correspondiente. Los errores más comunes son:

- El correo no tiene un formato válido.
- La contraseña tiene menos de 8 caracteres.
- Las contraseñas no coinciden.

Si el correo ya está registrado, se mostrará un mensaje de error general debajo del formulario.

Pulsar `"¿Ya tienes cuenta? Inicia sesión"` para volver a la pantalla de inicio de sesión.

Tras un registro exitoso, la aplicación navega automáticamente a `PantallaInicio`.

---

## 5. Inicio de sesión

La pantalla de inicio de sesión solicita dos campos:

- **Correo electrónico**: el correo con el que se registró la cuenta.
- **Contraseña**: la contraseña de la cuenta. El icono del ojo permite mostrar u ocultar los caracteres.

Pulsar el botón `"Entrar"` para iniciar sesión. La aplicación valida el formato del correo y que la contraseña tenga al menos 8 caracteres antes de enviar la solicitud a Firebase. Si las credenciales son incorrectas, se mostrará un mensaje de error debajo del formulario.

Tras un inicio de sesión exitoso, la aplicación navega a `Pantalla de inicio`.

---

## 6. Pantalla de inicio

La pantalla de inicio es el menú principal de la aplicación. Muestra el logo de Professional Nuzlocker en la parte superior y un carrusel central con tres opciones navegables mediante los botones circulares **"<"** y **">"** situados a ambos lados.

Las tres opciones del carrusel son:

#### CONTINUAR
Navega a la partida activa guardada en la nube. Al pulsar `"¡Adelante!"`, la aplicación consulta Firestore para verificar si existe una partida en curso.
- Si existe, se accede directamente a la pantalla de registro.
- Si no existe ninguna partida guardada, se muestra un diálogo informando de que es necesario crear una nueva.

#### NUEVA PARTIDA
Inicia el proceso de configuración de una partida nueva. Al pulsar `"¡Adelante!"`, la aplicación consulta Firestore.
- Si no existe ninguna partida, se accede directamente a la pantalla de configuración.
- Si ya existe una partida guardada, se muestra un diálogo de confirmación advirtiendo de que continuar eliminará permanentemente la partida anterior. El diálogo ofrece las opciones "Sí" (eliminar y crear nueva) y "No" (cancelar).

#### GUÍA NUZLOCKE
Abre la pantalla de guía con las reglas y conceptos del modo Nuzlocke.

### Iconos en la parte superior de la pantalla:

- **Icono de cuenta** (esquina superior izquierda): Abre el diálogo de gestión de cuenta.
- **Icono de volumen** (esquina superior derecha): Alterna entre música activada y silenciada.
- **Icono de cierre de sesión** (esquina superior derecha, junto al volumen): Cierra la sesión y navega a `PantallaLogin`.

---

## 7. Gestión de cuenta

Pulsar el icono de cuenta en la esquina superior izquierda de la pantalla de inicio abre un diálogo con las opciones de gestión de la cuenta.

El diálogo muestra:

- El correo electrónico de la cuenta actual en formato solo lectura.
- Un formulario de cambio de contraseña con tres campos:
  - Contraseña actual.
  - Nueva contraseña.
  - Confirmar nueva contraseña.

Para guardar el cambio, pulsar el botón `"Guardar"`. Si la contraseña actual es incorrecta, se mostrará el mensaje `"La contraseña actual es incorrecta"`. Si la operación es correcta, aparecerá el mensaje `"Contraseña actualizada"` en verde y los campos se limpiarán automáticamente.

Para cerrar el diálogo sin realizar cambios, pulsar `"Cancelar"` o tocar fuera del diálogo.

---

## 8. Guía Nuzlocke

La pantalla de guía está accesible desde el carrusel de la pantalla de inicio. Muestra seis tarjetas organizadas en una cuadrícula de dos columnas, cada una con un título y un botón "+" para desplegar su contenido:

- **¿QUÉ ES?**: Explicación general del reto Nuzlocke.
- **REGLAS**: Reglas principales del modo.
- **VICTORIA**: Condición de victoria.
- **DERROTA**: Condición de derrota.
- **PROGRESO**: Cómo se registra y sigue el progreso en la aplicación.
- **IA**: Descripción del asistente NuzBot y cómo utilizarlo.

Al pulsar el botón "+" de cualquier tarjeta, se abre un diálogo con la información correspondiente, presentada mediante el personaje de la Prof. Encina. Pulsar sobre el cuadro de diálogo avanza al siguiente texto.

---

## 9. Configuración de nueva partida

Al iniciar una nueva partida, la aplicación muestra la pantalla de configuración mediante un diálogo introductorio presentado por la **Prof. Encina**. El jugador debe responder a una serie de preguntas avanzando por los diálogos de la profesora.

**Para avanzar:** pulsar sobre el cuadro de diálogo situado en la parte inferior de la pantalla. Cuando el diálogo está bloqueado esperando una selección, el cuadro no responde al toque hasta que se realice la elección.

**Pasos del proceso:**

1. **Bienvenida**: Textos introductorios de la Prof. Encina. Pulsar para avanzar.

2. **Versión del juego**: Aparecen dos imágenes en la parte superior de la pantalla: la carátula de **Pokémon Blanco** y la de **Pokémon Negro**. Pulsar sobre la versión elegida para seleccionarla y continuar.

3. **Sexo del jugador**: Aparecen dos personajes: **Lucho** (chico) y **Liza** (chica). Pulsar sobre el personaje usado.

4. **Nombre del jugador**: Se muestra un campo de texto con el título `"¿Cómo te llamas?"`. Introducir el nombre con un máximo 12 caracteres y pulsar `"Aceptar"` o la tecla de confirmación del teclado. El contador de caracteres `X/12` se actualiza en tiempo real.

5. **Elección del Pokémon inicial**: La Prof. Encina presenta progresivamente los tres Pokémon iniciales:
   - **Snivy**: Tipo Planta.
   - **Tepig**: Tipo Fuego.
   - **Oshawott**: Tipo Agua.
   Una vez presentados los tres, pulsar sobre el sprite del Pokémon elegido para seleccionarlo.

6. **Confirmación y guardado**: La Prof. Encina confirma la elección con el nombre del Pokémon seleccionado y el nombre del jugador. En los últimos diálogos, la partida se guarda automáticamente en Firestore. Al pulsar sobre el último diálogo, la aplicación navega a `PantallaRegistro`.

---

## 10. Pantalla de registro

La pantalla de registro es el núcleo de la aplicación durante el transcurso de la partida. Presenta una lista desplazable verticalmente que combina las rutas del juego y los combates importantes, intercalados en el orden cronológico de Pokémon Negro/Blanco.

#### Barra superior fija:

En la parte superior se muestra de forma permanente el estado actual de la partida:

- **Contador de vidas**: En la esquina izquierda, con el formato `VIDAS X/10`. Cada muerte de un Pokémon del equipo activo descuenta una vida. Cuando las vidas llegan a cero, el contador desaparece y se muestra el texto `"FIN DE LOCKE"`.
- **Seis slots del equipo activo**: Cada slot muestra el sprite del Pokémon y su mote (o nombre si no tiene mote asignado). Los slots vacíos se muestran como círculos semitransparentes.

Pulsar sobre un slot ocupado del equipo abre el diálogo de detalle del Pokémon.

#### Orden de la lista:

Los elementos de la lista siguen el orden cronológico del juego. Solo la ruta actualmente pendiente de resolver está desbloqueada, donde todas las anteriores quedan registradas y todas las posteriores están bloqueadas. Intentar interactuar con una ruta bloqueada hace que la lista se desplace automáticamente hasta la ruta activa y muestra un mensaje informativo en la parte inferior de la pantalla indicando qué ruta debe resolverse primero. Los combates que aún no corresponde registrar también aparecen bloqueados hasta que se llegue a ellos.

---

## 11. Registro de rutas y encuentros

Cada elemento de ruta en la lista muestra la imagen de la ruta, su nombre y su estado actual. Las rutas pueden encontrarse en uno de los siguientes estados:

- **Libre**: Aún no se ha registrado ningún encuentro. Es la única ruta interactiva al ser la activa.
- **Capturado**: Se ha registrado un Pokémon capturado en esta ruta.
- **Perdido**: El Pokémon encontrado fue derrotado sin ser capturado.

#### Proceso de registro de un encuentro en la ruta activa:

1. Pulsar sobre la ruta activa para desplegar el selector de Pokémon.
2. La aplicación muestra la lista completa de Pokémon disponibles en esa ruta según los datos del juego. Pulsar sobre el Pokémon encontrado para seleccionarlo.
3. Una vez seleccionado el Pokémon, aparecen dos opciones:
   - **Capturar**: Se abre un formulario donde introducir el mote del Pokémon (opcional) y su nivel actual (obligatorio). Pulsar "Confirmar" para registrar la captura. El Pokémon se añade al equipo activo si tiene menos de 6 miembros.
   - **Debilitar**: Registra el Pokémon como perdido sin necesidad de más datos. La ruta queda marcada como "Perdido" y no ocupa un slot del equipo.

Tras resolver el encuentro, la ruta queda bloqueada y la siguiente ruta disponible se convierte en la activa.

---

## 12. Registro de combates importantes

Los combates importantes aparecen intercalados en la lista entre las rutas, representados por la imagen y el nombre del rival correspondiente. Cuando llega el momento de enfrentarse a uno, el elemento se desbloquea.

**Proceso de registro de un combate:**

1. Pulsar sobre el combate para desplegarlo.
2. Seleccionar los Pokémon del equipo activo que se usaron en el combate. Pulsar sobre cada uno para marcarlo o desmarcarlo.
3. Elegir el resultado:
   - **Ganar**: Registra la victoria con el equipo seleccionado. El combate queda marcado como superado.
   - **Perder**: Registra la derrota. La partida finaliza inmediatamente (ver sección 14).

---

## 13. Registro de muerte de un Pokémon

Un Pokémon del equipo activo puede morir de dos formas: durante un combate importante (mediante el botón "Perder" del registro de combate) o manualmente desde el slot del equipo en la barra superior.

**Para registrar una muerte manualmente:**

1. Pulsar sobre el sprite del Pokémon en la barra superior de la pantalla de registro. Se abre el diálogo de detalle, que muestra la especie, el mote (si tiene), el nivel, los tipos, la habilidad y la ruta donde fue capturado.
2. Pulsar el botón `"† El Pokémon ha caído"` para abrir el formulario de registro de muerte.
3. Completar el formulario:
   - **Tipo de entrenador** (obligatorio): Seleccionar entre las opciones disponibles presentadas como chips el tipo de entrenador que causó la derrota.
   - **Pokémon que lo mató** (obligatorio): Escribir al menos 2 caracteres en el buscador para ver sugerencias de la Pokédex. Pulsar sobre el resultado deseado para seleccionarlo. Para cambiar la selección, pulsar la "×" junto al Pokémon seleccionado.
   - **Ataque definitivo** (opcional): Nombre del movimiento que derrotó al Pokémon.
4. Pulsar `"Confirmar"` para registrar la muerte. El Pokémon se mueve del equipo activo al Cementerio, se descuenta una vida, y si las vidas llegan a cero la partida termina automáticamente.

También es posible mover un Pokémon del equipo activo al **PC** sin registrar una muerte. Desde el diálogo de detalle, pulsar el botón `"Añadir al PC"`. El Pokémon queda guardado en el PC sin penalización de vidas.

---

## 14. Fin del Nuzlocke

El Nuzlocke puede terminar de tres formas:

- **Sin vidas**: El contador de vidas llega a cero al morir el décimo Pokémon.
- **Combate perdido**: Se registra una derrota en un combate importante.
- **Victoria**: Se superan todos los combates importantes registrados.

Cuando se detecta alguna de estas condiciones, la pantalla muestra durante 3 segundos una animación de pantalla completa con el mensaje correspondiente:

- "FIN DEL LOCKE" con el motivo (sin vidas o combate perdido).
- "¡LOCKE COMPLETADO!" en caso de victoria.

Tras la animación, la lista se desplaza automáticamente hasta el final donde aparece un banner de fin de partida con el motivo y un botón `"Ver estadísticas"`. A partir de este momento, todas las rutas y combates quedan bloqueados y la barra superior muestra "FIN DE LOCKE" en lugar del contador de vidas.

Pulsar `"Ver estadísticas"` navega a la pantalla de estadísticas finales.

---

## 15. Pantalla Mi Partida — Equipo, PC y Cementerio

Accesible desde la barra de navegación inferior (icono de Pokémon), esta pantalla permite gestionar en detalle todos los Pokémon de la partida. Se organiza en tres pestañas seleccionables en la parte superior.

### Pestaña Equipo

Muestra la tarjeta del jugador con su avatar, nombre y logo de la versión del juego, seguida de las tarjetas de cada Pokémon del equipo activo.

Cada tarjeta muestra el sprite del Pokémon, su nombre, el mote (si tiene), el nivel y sus tipos mediante píldoras de color. Pulsar sobre una tarjeta abre el **diálogo de detalle y edición** con las siguientes secciones:

- **Mote**: Campo de texto editable. Dejar en blanco para no usar mote.
- **Nivel**: Ajustable mediante los botones "−" y "+" (rango 1–100).
- **Tipos**: Mostrados como píldoras de color informativas.
- **Ruta de captura**: Imagen y nombre de la ruta. Pulsar sobre la imagen para verla ampliada en un diálogo.
- **Objeto**: Si se registró algún objeto portado (informativo).
- **Habilidad**: Chips seleccionables con las habilidades disponibles para la especie. Pulsar sobre una para marcarla como activa.
- **Cadena evolutiva**: Si la especie tiene evoluciones, se muestra la cadena completa con flechas y el método de evolución entre cada etapa.

Pulsar `"Guardar cambios"` persiste los datos modificados en Firestore.

Si existe una siguiente evolución, aparece el botón `"Evolucionar a X"`. Este botón está deshabilitado si el nivel actual del Pokémon es inferior al nivel requerido para evolucionar (se muestra un aviso indicando el nivel mínimo). Al pulsar el botón, se abre el diálogo de evolución:

- Muestra el sprite de la especie evolucionada con sus tipos.
- Permite modificar el mote y el nivel.
- Requiere seleccionar obligatoriamente una habilidad de las disponibles para la nueva especie.
- Pulsar `"Confirmar"` registra la evolución en Firestore y cierra el diálogo.

### Pestaña PC

Muestra una cuadrícula de dos columnas con todos los Pokémon depositados en el PC. Incluye:

- **Campo de búsqueda** "Buscar en el PC": Filtra en tiempo real por nombre de especie o mote.
- **Chips de tipo**: Fila desplazable horizontalmente con los tipos presentes en el PC. Pulsar sobre un tipo para filtrar. Pulsar `"Todos"` para quitar el filtro.

Pulsar sobre una tarjeta del PC abre un diálogo con el detalle del Pokémon (tipos, nivel, habilidad, ruta de captura) y el botón `"Añadir al equipo"`.

- Si el equipo tiene menos de 6 miembros, el Pokémon se mueve directamente del PC al equipo.
- Si el equipo está completo (6 miembros), se abre un diálogo de sustitución que lista los miembros actuales del equipo. Pulsar sobre el Pokémon que se desea sustituir para intercambiar posiciones. El Pokémon reemplazado pasa al PC.

### Pestaña Cementerio (Muertos)

Muestra todos los Pokémon caídos a lo largo de la partida. El fondo adopta una paleta oscura diferenciada del resto de la aplicación.

Cada tarjeta de Pokémon caído muestra:

- Sprite en escala de grises.
- Nombre de la especie y mote (si tenía).
- Fecha y hora de la muerte.
- Tipo de entrenador responsable.
- Sprite en escala de grises y nombre del Pokémon que lo mató.
- Ataque con el que fue derrotado (si se registró).

Si no hay ningún caído aún, se muestra el mensaje "Ningún caído hasta ahora."

---

## 16. Pokédex

Accesible desde la barra de navegación inferior (segundo icono), la Pokédex muestra todos los Pokémon de la región Teselia disponibles en Pokémon Negro y Blanco.

La pantalla presenta una cuadrícula de dos columnas con las tarjetas de cada Pokémon. Cada tarjeta incluye el sprite, el nombre y sus tipos.

En la parte superior se encuentra la barra de búsqueda con un icono de lupa. Al escribir en ella, la lista se filtra en tiempo real mostrando únicamente los Pokémon cuyo nombre contiene el texto introducido. Para limpiar la búsqueda, pulsar la "×" que aparece en el campo cuando hay texto escrito.

Pulsar sobre cualquier Pokémon de la cuadrícula abre su ficha completa, que incluye información sobre dónde encontrarlo, su línea evolutiva y sus habilidades, entre otros datos.

---

## 17. NuzBot — Asistente IA

Accesible desde la barra de navegación inferior (último icono, "IA"), NuzBot es el asistente de inteligencia artificial de la aplicación.

La pantalla muestra un historial de conversación en forma de burbujas de chat:

- Los mensajes del jugador aparecen a la derecha, sobre fondo rojo.
- Las respuestas de NuzBot aparecen a la izquierda, sobre fondo gris.

El historial se desplaza automáticamente al mensaje más reciente cada vez que se recibe una nueva respuesta o se envía un mensaje.

#### Para enviar una pregunta:

1. Pulsar el campo de texto situado en la parte inferior de la pantalla.
2. Escribir la pregunta.
3. Pulsar el botón de envío (flecha) situado a la derecha del campo.

Mientras NuzBot procesa la respuesta, aparece el indicador `"NuzBot pensando..."` en la zona del historial. El campo de texto y el botón de envío quedan deshabilitados hasta que llega la respuesta.

NuzBot tiene acceso al contexto completo de la partida actual, por lo que puede responder preguntas específicas como qué Pokémon están en el equipo, qué rutas quedan por completar, qué combates se han superado, o proporcionar consejos estratégicos basados en el estado real de la partida.

Si el servicio no está disponible, se mostrará un mensaje de error en el historial.

---

## 18. Estadísticas y exportación PDF

La pantalla de estadísticas se abre automáticamente al pulsar `"Ver estadísticas"` al final del registro, o puede accederse desde el menú de inicio si la partida ya ha concluido.

Las estadísticas se revelan de forma progresiva a través de ocho fases. Cada fase se desbloquea pulsando un botón que avanza al siguiente bloque de información.

**Las fases y su contenido son:**

1. **Resultado**: Muestra una píldora verde con `✓ VICTORIA` o roja con `✕ DERROTA` según el resultado de la partida.
2. **Datos generales**: Vidas restantes al finalizar, número de Pokémon capturados, número de Pokémon perdidos y total de rutas del juego.
3. **Pokémon más usados**: Ranking de los 3 Pokémon que participaron en más combates, con sprite, nombre o mote, y número de combates.
4. **Combates más mortales**: Los 3 combates importantes en los que cayeron más Pokémon del equipo, con imagen del rival y número de bajas.
5. **Rivales más letales**: Los 3 Pokémon enemigos que más Pokémon del equipo eliminaron, con número de víctimas.
6. **Distribución de tipos**: Gráfico de barras horizontales con la distribución de tipos de todos los Pokémon capturados durante la partida.
7. **Consultas a NuzBot**: Número total de preguntas realizadas al asistente IA durante la partida.
8. **Exportación PDF**: Aparece el botón `"Descargar PDF"`.

#### Para exportar el informe:

Pulsar el botón `"Descargar PDF"` en la fase 8. La aplicación genera un documento PDF completo con todos los datos de la partida, incluyendo resultado, capturas, rankings y distribución de tipos, y lo guarda automáticamente en la carpeta Descargas del dispositivo con el nombre `resumen_aventura.pdf`. Al terminar, aparece el mensaje "PDF guardado en Descargas".

El archivo puede abrirse con cualquier visor de PDF del dispositivo.

---

## 19. Control del audio

La aplicación reproduce música de fondo de forma automática al abrirse. El audio se gestiona desde `PantallaInicio`.

#### Para silenciar o reactivar la música:

Pulsar el icono de volumen situado en la esquina superior derecha de la pantalla de inicio. El icono alterna entre el estado activo (altavoz con ondas) y silenciado (altavoz tachado).

El estado de mute se mantiene durante toda la sesión activa. Al cerrar y reabrir la aplicación, la música vuelve a reproducirse independientemente del estado anterior.

#### Comportamiento del audio durante combates:

Al registrar un combate importante, la música de exploración se reduce gradualmente mientras entra la música de batalla con un fundido cruzado de 1 segundo. Al terminar el combate, la música de batalla desaparece y la música de exploración regresa con el mismo fundido cruzado.

Al registrar la captura de un Pokémon, suena el efecto de captura y el volumen de la música de fondo se reduce brevemente durante 2,5 segundos.

Otros efectos de sonido presentes en la aplicación. Clic al pulsar botones y confirmaciones y subida de nivel al guardar cambios en el diálogo de edición de Pokémon.

---

## 20. Empezar una nueva partida sobre una existente

Si ya existe una partida guardada y se desea empezar una nueva, la aplicación lo gestiona desde la pantalla de inicio.

1. Navegar en el carrusel hasta la opción **NUEVA PARTIDA** y pulsar `"¡Adelante!"`.
2. Aparece el diálogo **"Partida existente"** con el mensaje: *"Ya hay una partida guardada. ¿Quieres borrarla y empezar una nueva?"*
3. Pulsar **"Sí"** para eliminar permanentemente la partida actual y acceder al formulario de configuración de la nueva partida. Esta acción no se puede deshacer.
4. Pulsar **"No"** para cancelar y mantener la partida existente.

Se recomienda exportar el informe PDF antes de eliminar una partida, ya que una vez borrada no es posible recuperar ningún dato.
