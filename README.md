# Grupo 5 · Multilistas y grafos dinámicos

Estructura de Datos, 3.er semestre de Software, FISEI-UTA. Docente: Ing. José Rubén Caiza.
Exposición del lunes 16/11/2026, Unidad 4 (APE 4).

## Integrantes

- Espinoza Reyes Kerly Margoth
- Gamboa Araujo Rommel Fabricio
- Jijón Viscaino Gabriel Sebastián
- Manobanda Puaquiza Italo Esteban
- Tenorio Ronquillo Jonathan Sebastián

## De dónde viene este trabajo

Los grupos anteriores fueron armando la unidad por partes. El Grupo 1 abrió con árboles, el Grupo 2 trabajó la recursividad, el Grupo 3 la transformación de un árbol general a binario y el Grupo 4 introdujo los grafos: G = (V, E), dirigido y no dirigido, grado, densidad, lista de adyacencia con `Map`/`Set` y matriz de adyacencia.

El Grupo 4 dejó la teoría y el modelo de la red social. Nosotros hacemos la implementación dinámica: el mismo grafo, pero construido con nodos enlazados propios, sin `HashMap`, `HashSet`, `ArrayList` ni librerías de grafos. Por eso el programa `RedSocialG4` vuelve a armar el grafo de ejemplo de su clase (A-B, A-C, B-C, C-D) y muestra que da los mismos grados (2, 2, 3, 1) y la misma matriz.

## Cómo está hecho

```
cabeza -> [Ana] -> [Luis] -> [Mia] -> [Leo] -> null     lista de vertices
            |
            +-> (Luis,1) -> (Mia,4) -> null              lista de aristas de Ana
```

Cada `Vertice` guarda su nombre, el puntero a su primera `Arista` y el puntero al siguiente vértice. Cada `Arista` guarda el vértice destino, el peso y la siguiente arista del mismo origen. El grafo es dirigido y ponderado. Una relación no dirigida (la amistad del Grupo 4) se guarda como dos aristas, una en cada sentido.

Los vértices y las aristas se insertan al inicio de su lista, igual que en las diapositivas. Eso tiene un efecto que conviene decir en clase: el último vértice agregado es el primero que imprime `mostrar()`, y la última arista agregada de un vértice es el primer vecino que exploran BFS y DFS. Los recorridos siempre empiezan en el vértice que se les pasa.

### Lista de adyacencia o multilista

En nuestro código cada nodo `Arista` vive en una sola lista, la de su vértice origen. Eso es una lista de adyacencia hecha con nodos: una lista de vértices y, colgada de cada uno, su lista de aristas.

La multilista de aristas que mencionó el Grupo 4 es otra cosa: un mismo nodo arista `[v1 | v2 | sigEnV1 | sigEnV2]` se enlaza a la vez en las listas de sus dos vértices. Ahorra memoria en un grafo no dirigido (un nodo por relación en vez de dos), pero insertar y borrar es más delicado porque hay que arreglar dos enlaces. Elegimos la lista de adyacencia porque el grafo es dirigido y así cada arista tiene un solo dueño.

```
src/
  modelo/   Vertice.java, Arista.java           nodos
  negocio/  Grafo.java                          operaciones
  app/      Main.java                           demo de la exposicion
            RetoSolucion.java                   solucion del reto
            RetoPlantilla.java                  plantilla para los compañeros
            RedSocialG4.java                    el grafo del Grupo 4 con nodos propios
  pruebas/  PruebasGrafo.java                   67 pruebas sin JUnit
docs/
  Observaciones_presentacion.md                 correcciones a las diapositivas
  Grafo_original.java                           el codigo antes de los cambios
```

## Operaciones y costo

| Método | Qué hace | Costo |
|---|---|---|
| `agregarVertice(id)` | Inserta al inicio. Rechaza null, vacío y repetido | O(V) |
| `agregarArista(o, d, peso)` | Inserta al inicio de la lista de `o`. Rechaza vértice inexistente, lazo, peso negativo y arista repetida | O(V + grado) |
| `agregarRelacion(a, b, peso)` | Arista en los dos sentidos. Si la segunda falla, deshace la primera | O(V + grado) |
| `actualizarPeso(o, d, peso)` / `peso(o, d)` | Cambia o consulta el peso | O(V + grado) |
| `eliminarArista(o, d)` | La salta con un puntero previo | O(V + grado) |
| `eliminarRelacion(a, b)` | Quita los dos sentidos | O(V + grado) |
| `eliminarVertice(id)` | Quita primero todas las aristas que llegan a él y luego lo desenlaza | O(V + A) |
| `mostrar()` / `vecinos(id)` | Lista de adyacencia / vecinos de un vértice | O(V + A) / O(V + grado) |
| `grado(id)` / `gradoEntrada(id)` | Aristas que salen / que llegan | O(V + grado) / O(V + A) |
| `matrizAdyacencia()` | La misma red como matriz, para comparar con el Grupo 4 | O(V · (V + A)) |
| `bfs`, `recorridoBfs` | Anchura; la cola se enlaza con el campo `sigCola` de cada vértice | O(V + A) |
| `trazaBfs` | El mismo BFS mostrando la cola en cada paso | O(V² + A) |
| `dfs`, `recorridoDfs` | Profundidad recursiva (la pila es la de llamadas) | O(V + A) |
| `existeVertice(id)` | Consulta si el vértice existe sin entregar el nodo | O(V) |
| `esConexo(inicio)` | true si desde `inicio` se llega a todos los vértices | O(V + A) |

`esConexo` sigue el sentido de las aristas. En un grafo dirigido el resultado depende del vértice de inicio: en el Grafo A del reto, desde Ana se llega a todos, pero desde Noa no. En rigor eso es "desde este vértice se alcanzan todos", no conexidad fuerte ni débil. En la red no dirigida del Grupo 4 sí coincide con ser conexo.

El DFS es recursivo, como en las diapositivas. La pila es la pila de llamadas de Java, que se puede llenar con una cadena muy larga de vértices (`StackOverflowError`; el límite depende de la JVM). Para los grafos de la clase no hay problema.

Los nombres distinguen mayúsculas (`ana` y `Ana` son vértices distintos) y no pueden tener espacios internos (`agregarVertice("Ana Maria")` devuelve `false`), porque los recorridos separan los vértices con espacios.

## Ejecución (PowerShell, desde la carpeta del proyecto)

```powershell
javac -encoding UTF-8 -d out (Get-ChildItem -Recurse -Filter *.java src).FullName
java -cp out pruebas.PruebasGrafo
java -cp out app.Main
java -cp out app.RetoSolucion
java -cp out app.RedSocialG4
```

La primera línea compila todo en la carpeta `out`. `PruebasGrafo` debe terminar con `67/67 pruebas OK`.

## Reto para la clase: ¿grafo conexo o desconectado?

Abran `src/app/RetoPlantilla.java` y completen los `TODO` usando solo los métodos de `Grafo`.

1. Construir (10 min). Grafo A con 6 vértices: Ana -> Mia, Ana -> Luis, Mia -> Leo, Luis -> Leo, Leo -> Eva, Eva -> Noa.
2. Consultar (10 min). Imprimir la lista, el BFS desde Ana y el grado de dos vértices.
3. Decidir (15 min). Grafo B: Sol -> Tito, Tito -> Rex, Zoe -> Pia. Decidir si es conexo desde Sol, eliminar un vértice y volver a decidir.

Casos límite que deben probar: grafo vacío, vértice repetido, arista hacia un vértice que no existe y eliminar un vértice que tiene aristas entrantes.

Resultado esperado: el Grafo A es conexo desde Ana (alcanza 6 de 6) y el Grafo B no lo es desde Sol (alcanza 3 de 5). Si se elimina Leo en el Grafo A, desde Ana solo se llega a Ana, Luis y Mia.

Para comparar con la solución:

```powershell
javac -encoding UTF-8 -d out (Get-ChildItem -Recurse -Filter *.java src).FullName
java -cp out app.RetoPlantilla
java -cp out app.RetoSolucion
```

## Cambios respecto al código que presentamos

El código original está en `docs/Grafo_original.java`. Lo revisamos y estos son los cambios:

1. Se separó en capas `app -> negocio -> modelo`, como dice la diapositiva, en vez de tener todo en un archivo.
2. El BFS usaba `Queue`, `LinkedList` y `HashSet` de Java, y el DFS usaba `HashSet` para los visitados. Eso choca con la regla del reto (prohibido usar colecciones de Java) y con el proyecto final, que pide estructuras propias. Ahora la cola se enlaza con el campo `sigCola` de cada vértice (cada uno entra a la cola una sola vez, así que no hace falta otra clase nodo) y el visitado es una marca dentro de cada vértice.
3. `esConexo` devolvía `true` con el grafo vacío, porque comparaba 0 con 0. Ahora devuelve `false`.
4. Se validan id null o vacío, lazos (A -> A), pesos negativos y aristas repetidas.
5. `bfs` y `dfs` devuelven cuántos vértices alcanzan en lugar de imprimir dentro del método. Así se pueden probar.
6. Se agregaron `gradoEntrada`, `totalAristas`, `vecinos`, `peso`, `trazaBfs`, `agregarRelacion`, `eliminarRelacion` y `matrizAdyacencia`.
7. Se agregaron 67 pruebas con los casos límite del reto.
8. `buscar` ahora es privado. Antes devolvía el nodo y desde `app` se podía romper la lista con `setSig(null)`; para preguntar si un vértice existe está `existeVertice`.
9. El BFS armaba el texto de la cola en cada paso aunque nadie pidiera la traza, y eso lo volvía O(V²) en un grafo estrella. Ahora solo lo arma `trazaBfs`.

## Uso de IA

Usamos un asistente de IA para revisar el código, encontrar el error de `esConexo` y escribir las pruebas. La estructura del grafo, los nombres de los métodos y el orden de la exposición son los del grupo. Cualquier integrante puede explicar cualquier método.
