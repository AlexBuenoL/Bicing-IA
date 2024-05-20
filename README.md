Autores: Alex Bueno, Oriol Roca i Álvaro Terrón.

Para la ejecución de la practica primero es necesario la generación del ejecutable. 

Hay 2 opciones:

Pasar proyecto a NetBeans o IntelliJ y desde alli ejecutar.

Utilizar comando "javac *.java" para generar archivos.class

Utilzar comando "java *.class" para generar el ejecutable

Hay dos maneras para intoducir los datos: la primera es dejar el código como está
y al ejecutar te pedirá unos datos. Contamos con la bondad del usuario que ejecute la
práctica para que introduzca los valores y proporciones correctos, ya que no está
programado ningun tipo de Usage.

La segunda opción es entrar en el archivo "main.java" y actualizar manualmente los valores
(descomentar línea 15 del archivo y comentar de la 16 a 31).

Hay algunos valores como la heurísitca y la solución inicial que si habrá que poner
unos valores específicos:

Heurística: 0 (simple) o 1 (combinada)
Solución inicial: 0 (aleatoria) o 1 (greedy)

Los valores del Simulated annealing se tendrán que modificar de manera manual en el 
archivo "main.java" en las líneas 90 y 110. Si no se aplicarán los valores por defecto
"stepts = 2500, stiter = 100, k = 50 i lamb = 1.0).
