# moviles - Android Respository


#### Generalidades

Este proyecto se llama Pentagono App y fue realizado para la clase de construcción de aplicaciones móviles dictada en la Universidad de los Andes y tomada en el periodo 2020-10. 

El pentagono es un espacio del departamento de matemáticas de la Universidad en el que monitores/estudiantes se encuentran para resolver dudas sobre alguna materia que estén cursando. Es un espacio construido por estudiantes para estudiantes. 

En la carpeta llamada "Release" encontrarán el APK que corresponde a la última versión del producto. 

#### Credenciales

- Para usar el app, la persona puede registrarse sin problema siguiendo el link de registro que aparece en el formulario de login del app, que es la primera pantalla que el usuario ve. 

- También pueden usar las siguientes credenciales:

    Email -> jmtrilli@gmail.com  Password -> 12345678

#### Funcionalidades 


1. Reservar un espacio en el pentagono online: 

    - El usuario identifica la tarjeta con nombre "booking" en el home de la aplicación
    - El flujo de reservar un espacio, ha empezado
    - El usuario selecciona la materia que se le dificulta o sobre la cual tenga dudas
    - El usuario selecciona el monitor/estudiante de su preferencia para que sea quien le explique
    - El usuario selecciona un horario en la franja horaria disponible del monitor 
    - El usuario confirma que la reserva está bien realizada
    - La reserva es guardada como un evento en el calendario del usuario (google calendar)
    - La reserva se visualiza en el Home de la aplicación a modo de "reminder" para que tenga presente la cita
    
2. Editar/Borrar una monitoria existente en el sistema:
    - El usuario solo puede eliminar/editar una monitoria siempre y cuando dicha monitoria no haya sucedido
    - El usuario en el Home de la aplicación puede ver su próxima monitoria y el puede editarla o eliminarla
    - En caso de que seleccione eliminar, la monitoria es borrada del sistema y el slot de tiempo del monitor es liberado
    - En caso de que seleccione editar, la monitoria es también borrada pero empieza el flujo de reservar una nueva
    
3. Sistema colaborativo de recursos del pentagono: 
    - El usuario identifica la tarjeta con nombre "Resources" en el home de la aplicación
    - El usuario es dirigido a una nueva pantalla con diferentes opciones: "Upload Resource", "View Resources", "Home"
    - El usuario también observa un edit text que pone: "Enter PDF New Name" 
    - SI el usuario quiere colaborar con el sistema y agregar un recurso, el usuario debe colocar un nombre al recurso usando el editText "Enter PDF new name" y luego haciendo clic en "Upload Resource" , dicho botón abrirá google Drive y le permitirá seleccionar un elemento desde dicha aplicación. 
    - SI el usuario quiere solo ver los recursos, hace clic en "View Resources" y se despliega una lista con todos los recursos que los usuarios han subido. Puede tambien buscar por nombre de recurso usando el search bar que aparece en la parte superior de la pantalla. 
    - SI el usuario se quiere devolver al home, solo tiene que pulsar el boton "Home" o pulsar el botón de "atrás" en su dispositivo móvil
   
4. Compendio historico de monitorias reservadas por el usuario:
    - El usuario identifica la tarjeta con nombre "Historic" en el home de la aplicación
    - El usuario puede observar la lista de monitorias que ha reservado desde su inicio en el App ordenadas por fecha de la mas reciente a la mas vieja. 
    
5. Chatbot o Asistente virtual "Stacy Lawrence":
    - El usuario identifica la tarjeta con nombre "Assistant" en el home de la aplicacion
    - El usuario empieza un chat con un bot programado por medio de IBM Watson. 
    - El asistente es usado para fines informativos en caso de que la persona no sepa que hacer a continuacion o como usar el app. 
    
6. Home con imagenes para banners publicitarios/ads/etc:
    - Como forma de revenue stream, en el home se colocan ciertos banners e imagenes. Dichos espacios pueden ser alquilados por diferentes personas que les pueda interesar y de esta manera convertimos o generamos monetizacion en nuestra aplicacion
    
    
    
