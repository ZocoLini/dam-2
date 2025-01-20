# Creacion de Componentes

## Componentes
Los componentes personalizados se encuentran 
dentro del paquete org.lebastudios.fxcomponents.components. Para usarlos 
debe ejecutar mvn package para compilar el proyecto y luego agregar
el jar generado en la carpeta target al proyecto en el que se desee
utilizar los componentes.

Nota: Al compilar el proyecto puede dar un error de símbolos no encontrados, 
ese error se debe a que Lombok no generó los métodos getter y setter o que
no está habilitada la opción de preprocesar anotaciones en el IDE. La mayor 
de las veces, ejecutando el Launcher.class o intentando compilar un par de veces
se arregla el problema.

## Utilizando el componente
Nuestro componente de interes es el MultipleItemsListView, el cual es un ListView
con paginación, optimización de carga de nodos reciclando los que ya están
creados y un evento personalizable al hacer click en un item.

Para utilizarlo, se debe crear un objeto de la clase MultipleItemsListView si 
se desea usar de forma programática o utilizar un constructor que no sea el 
por defecto, o añadiendo el siguiente código en el archivo FXML:

```xml
<?import org.lebastudios.fxcomponents.components.MultipleItemsListView?>

<MultipleItemsListView fx:id="multipleItemsListView" groupSize="250"/>
```

Si hemos construido el objeto sin asignarle un ContentGenerator debemos asignarselo 
para que pueda a empezar a cargar los items. Tambíen es posible asignarle un 
ListCellNodeRecicler para que pueda reciclar los nodos que ya no se estén utilizado 
pero ya hayan sido cargados o simplemente cargar uno nuevo en caso de que no haya
sido creado anteriormente.