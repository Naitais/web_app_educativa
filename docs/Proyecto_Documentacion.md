# Documentación del Proyecto: Sistema de Tutorías Tatutor

## 1. Descripción del Problema a Solucionar
El aprendizaje de una nueva disciplina o habilidad específica a menudo se ve obstaculizado por la dificultad de encontrar profesionales o instructores calificados que enseñen exactamente lo que el usuario desea aprender. Por otro lado, los expertos en dichos campos carecen de una plataforma centralizada y eficiente para ofrecer sus tutorías, publicar sus horarios, modalidades y costos, así como para gestionar las solicitudes de los alumnos. 

Este proyecto resuelve este problema de fragmentación conectando a estudiantes (Alumnos) con profesionales (Tutores) a través de una aplicación web centralizada. La plataforma permite a los usuarios registrarse, buscar tutorías de diversas categorías (arte, música, ciencia, programación, etc.), visualizar detalles importantes como el arancel (valor por clase), ubicación y modalidad (presencial u online). Asimismo, los tutores obtienen una herramienta "Mis Tutorías" para crear y administrar sus ofertas, definir sus perfiles profesionales con certificados y experiencia, y organizar eficientemente sus clases.

## 2. Requerimientos del Sistema

### 2.1 Requerimientos Funcionales
- **Registro y Autenticación:** El sistema debe permitir el registro de nuevos usuarios bajo tres roles principales (Estudiante, Profesor, Institución) y proveer un mecanismo seguro de inicio y cierre de sesión.
- **Gestión de Perfil:** Todo tutor debe poder editar su biografía profesional, cargar una foto de perfil, y agregar el historial de su experiencia laboral/educativa junto con sus certificados.
- **Gestión de Tutorías (CRUD):** Un tutor debe poder establecer nuevas tutorías indicando características dinámicas: horarios, rango de fechas de disponibilidad, modalidad, arancel con indicador monetario ($), descripción, disciplina (categorizada), y tipo de pago.
- **Inscripción a Tutorías:** Un estudiante debe poder navegar por las tutorías disponibles, analizar la experiencia del tutor y enviar una solicitud constructiva (inscripción) a múltiples tutorías elegidas, no viéndose limitado a cursar únicamente una sola clase en todo el entorno.
- **Gestión de Sesiones:** El sistema debe restringir el panel administrativo/personal, requiriendo estar autenticado.

### 2.2 Requerimientos No Funcionales
- **Diseño Responsivo (UI/UX):** La interfaz debe maximizar el espacio de la resolución usando "Tutorias Cards" dinámicas y bien estructuradas en sistema de cuadrículas (CSS Grid) para presentar la información claramente.
- **Estructura Dinámica de Modelos:** Evitar "hardcoding" para campos acumulables; por ejemplo, la experiencia y certificaciones deben calcular el tiempo en base a fechas en vez de cadenas de texto estáticas.
- **Seguridad y Persistencia:** Todas las contraseñas deben estar encriptadas al persistir. Relaciones ORM como Estudiante-Tutoría deben utilizar cardinalidad Many-To-Many (N:M) de manera indirecta para escalar correctamente cuando la base crezca.

## 3. Validaciones Implementadas
Para garantizar la integridad y seguridad de los datos de la plataforma, se incluyeron varias capas de validación en el nivel de servicios:

1. **Formato de Email Válido:** Al registrarse o iniciar sesión, el sistema se asegura de que la cadena ingresada represente un correo real verificando el patrón que contenga "@" y un dominio válido. Esto evita registros fantasma o spam.
2. **Complejidad de Contraseña (Fuerte):** Se validó que las contraseñas exijan como mínimo:
   - Longitud mínima de 8 caracteres.
   - Presencia de al menos 1 letra mayúscula y 1 minúscula.
   - Presencia de al menos 1 número.
   - Presencia de al menos 1 formato/caracter de símbolo especial.
3. **Confirmación de Contraseña:** En el proceso de registro, se requiere un campo de `confirmContraseña` que debe ser idéntico al campo clave, previniendo así errores de tipeo al darse de alta.
4. **Validación de Mayoría de Edad (Tutores):** Se incluyó un mecanismo que calcula la edad real basado en la `fechaNacimiento` de la Persona, validando que el usuario que desea asignarse el rol de `Profesor (Tutor)` detente un mínimo de 18 años computados dinámicamente según la fecha actual.

## 4. Roles y Flujos de Comunicación

- **Rol de Alumno (ROL_ESTUDIANTE):** Tiene principal interés en la lectura del Feed o Inicio para buscar clases. La comunicación principal hacia la base de datos se basa en la creación de "Solicitudes a Tutorías" las cuales emparéjan al alumno con la tutoría de un Profesor bajo el estado 'Pendiente'.
- **Rol de Tutor (ROL_PROFESOR):** Es un usuario productor de contenido. Su interfaz difiere de la del alumno porque contiene la plataforma exclusiva de "Mis Tutorías" para la inserción y gestión de clases (estado Disponible/No Disponible). Además, tiene una visibilidad pública (su Perfil) la cual los estudiantes investigan para determinar idoneidad (rating/evaluaciones, experiencia validada por fecha).
- **Rol de Institución (ROL_INSTITUCION):** Extensión pensada para el escalamiento orgánico de la aplicación; entidades colectivas en vez de educadores independientes que pueden aglomerar varias instancias de tutorías a la vez.

El flujo de comunicación alumno a profesor se efectúa en la asociación del Alumno a la `SolicitudTutoria`, que a su vez se liga al objeto `Tutoria`. Luego, el profesor puede visualizar las listas de su tutoría respectiva y coordinar el contacto físico o digital si evalúa aceptarla. Adicionalmente, se permite registrar los datos de `adultoResponsable` para los casos donde un estudiante registre una solicitud y sea menor de edad.
