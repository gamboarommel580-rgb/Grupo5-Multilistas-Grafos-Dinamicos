# Observaciones a las diapositivas (antes del lunes 16/11)

Revisamos las 20 láminas contra el código. Esto es lo que conviene corregir o, por lo menos, saber explicar si el ingeniero pregunta.

## Para corregir

1. **Láminas del BFS y del DFS.** El BFS usa `Queue`, `LinkedList` y `HashSet`, y el DFS usa `HashSet` para los visitados, pero la lámina del reto prohíbe colecciones de Java. Hay que cambiarlo por el BFS de `Grafo.java` (cola enlazada con `sigCola` dentro de `Vertice` y marca `visitado`), o decir en voz alta que la versión final ya no las usa.
2. **Lámina del DFS.** Dice "No está en Grafo.java", pero `dfs` sí está. Quitar esa frase.
3. **Ejemplo del BFS.** Dice que los vecinos de Ana salen en el orden Luis, Mia. Como insertamos al inicio, eso solo pasa si primero se agrega Ana -> Mia y después Ana -> Luis. Conviene escribirlo en la lámina o en la demo (el `Main` ya lo hace así).
4. **Fórmula de memoria de la matriz.** Aparece "V2". Debe ser V² (V al cuadrado).
5. **Definición de multilista.** La lámina dice que "un nodo pertenece a más de una lista". Eso es la multilista de aristas que mencionó el Grupo 4: un solo nodo arista [v1 | v2 | sigEnV1 | sigEnV2] enlazado en las listas de sus dos vértices. Nuestro código es una lista de adyacencia: cada nodo `Arista` está en una sola lista, y una relación no dirigida se guarda como dos nodos. Hay dos salidas: ajustar la frase ("una lista de listas: la lista de vértices y, colgada de cada uno, su lista de aristas") o explicar la diferencia en la lámina 1. La segunda opción además enlaza con lo que dejó el Grupo 4.
6. **Componentes.** "Repetir el recorrido desde cada vértice no visitado" cuenta componentes en un grafo no dirigido. En uno dirigido eso no da componentes fuertemente conexas. Mejor aclarar "en un grafo no dirigido".

## Para tener listo en la defensa

- Por qué `eliminarVertice` cuesta O(V + A): hay que revisar la lista de todos los vértices para quitar las aristas que llegan al eliminado.
- Por qué `esConexo` depende del inicio en un grafo dirigido: en el Grafo A, desde Ana se llega a 6, desde Noa a 1.
- Cuándo usar matriz y cuándo lista. Con los números del Padlet del Grupo 4 (100 usuarios, 150 amistades): la matriz tiene 10 000 celdas con 300 unos, la lista unos 100 vértices y 300 nodos arista. Para una red dispersa gana la lista; para una red pequeña y densa con muchas preguntas de "¿existe la arista?" gana la matriz, porque responde en O(1).
- Qué es la pila en el DFS: es la pila de llamadas de Java. Cada llamada a `profundidad` se apila y se desapila al volver. Si preguntan por grafos enormes: con una cadena muy larga se llena (StackOverflowError, el límite depende de la JVM) y ahí convendría un DFS iterativo con una pila propia.
- Si preguntan si `esConexo` es "conexidad" en un grafo dirigido: lo que calcula es si desde ese inicio se alcanzan todos. La conexidad fuerte pide eso desde todos los vértices.

## Conexión con el APE 4 · Fase 2 (jueves 19/11)

La fase 2 pide alta y baja de persona, crear y eliminar relación y listar vecinos con representación dinámica. Eso ya está en `Grafo`: `agregarVertice`, `eliminarVertice`, `agregarRelacion`, `eliminarRelacion` y `vecinos`. `RedSocialG4.java` sirve de ejemplo para esa clase.
