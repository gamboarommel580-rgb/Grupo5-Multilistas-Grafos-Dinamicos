package negocio;

import modelo.Arista;
import modelo.Vertice;

/**
 * Grafo dirigido y ponderado con listas de adyacencia (una lista de listas):
 *
 *   cabeza -> [Ana] -> [Luis] -> [Mia] -> null      lista de vertices
 *               |
 *               +-> (Luis,1) -> (Mia,4) -> null      lista de aristas de Ana
 *
 * Cada nodo Arista vive en UNA sola lista (la de su origen). En la multilista de
 * aristas que explico el Grupo 4 un mismo nodo arista se enlaza en las listas de
 * sus dos vertices; aqui una relacion no dirigida se guarda como dos aristas.
 *
 * Solo usa Vertice, Arista y Grafo. Sin HashMap, HashSet, ArrayList, LinkedList
 * ni librerias de grafos: la cola del BFS se enlaza con el campo sigCola de cada
 * Vertice y los visitados son una marca dentro de cada Vertice.
 *
 * Los vertices y las aristas se insertan AL INICIO de su lista, igual que en las
 * diapositivas. Por eso el ultimo vertice agregado es el primero que imprime
 * mostrar(), y la ultima arista agregada de un vertice es el primer vecino que
 * exploran BFS y DFS. Los recorridos siempre empiezan en el vertice inicio.
 */
public class Grafo {

    private Vertice cabeza;

    // ===================== BUSQUEDA =====================

    /** true si el vertice existe. O(V) */
    public boolean existeVertice(String id) {
        return buscar(id) != null;
    }

    // Privado: si app recibiera el Vertice podria romper la lista con setSig(null).
    private Vertice buscar(String id) {
        if (id == null) {
            return null;
        }
        String clave = id.trim();
        for (Vertice v = cabeza; v != null; v = v.getSig()) {
            if (v.getId().equals(clave)) {
                return v;
            }
        }
        return null;
    }

    /** Devuelve la arista o -> d, o null. O(grado de o) */
    private Arista buscarArista(Vertice o, Vertice d) {
        for (Arista a = o.getAristas(); a != null; a = a.getSig()) {
            if (a.getDestino() == d) {
                return a;
            }
        }
        return null;
    }

    public boolean existeArista(String o, String d) {
        Vertice vo = buscar(o);
        Vertice vd = buscar(d);
        return vo != null && vd != null && buscarArista(vo, vd) != null;
    }

    // ===================== INSERCION =====================

    /**
     * Inserta un vertice nuevo al inicio de la lista. O(V) por la busqueda.
     * false si el id es null, vacio, tiene espacios internos o ya existe.
     * (los recorridos separan los nombres con espacios: "Ana Maria" seria ambiguo)
     */
    public boolean agregarVertice(String id) {
        if (id == null || id.isBlank() || id.trim().contains(" ") || buscar(id) != null) {
            return false;
        }
        Vertice v = new Vertice(id.trim());
        v.setSig(cabeza);   //PASO 1: el nuevo apunta a la cabeza actual
        cabeza = v;         //PASO 2: el nuevo pasa a ser la cabeza
        return true;
    }

    /**
     * Inserta la arista o -> d con su peso al inicio de la lista de o. O(V + grado)
     * false si falta algun vertice, si el peso es negativo, si es un lazo (o -> o)
     * o si la arista ya existe.
     */
    public boolean agregarArista(String o, String d, int peso) {
        Vertice vo = buscar(o);
        Vertice vd = buscar(d);
        if (vo == null || vd == null || vo == vd || peso < 0 || buscarArista(vo, vd) != null) {
            return false;
        }
        Arista a = new Arista(vd, peso);
        a.setSig(vo.getAristas());  //PASO 1: la nueva apunta a la primera arista de o
        vo.setAristas(a);           //PASO 2: la nueva pasa a ser la primera
        return true;
    }

    // ===================== ACTUALIZACION =====================

    /** Cambia el peso de una arista que ya existe. O(V + grado) */
    public boolean actualizarPeso(String o, String d, int peso) {
        Vertice vo = buscar(o);
        Vertice vd = buscar(d);
        if (vo == null || vd == null || peso < 0) {
            return false;
        }
        Arista a = buscarArista(vo, vd);
        if (a == null) {
            return false;
        }
        a.setPeso(peso);
        return true;
    }

    /** Peso de la arista o -> d, o -1 si no existe. */
    public int peso(String o, String d) {
        Vertice vo = buscar(o);
        Vertice vd = buscar(d);
        if (vo == null || vd == null) {
            return -1;
        }
        Arista a = buscarArista(vo, vd);
        return a == null ? -1 : a.getPeso();
    }

    // ===================== ELIMINACION =====================

    /** Quita la arista o -> d de la lista de o usando un puntero previo. */
    private boolean quitar(Vertice o, Vertice d) {
        Arista prev = null;
        for (Arista a = o.getAristas(); a != null; prev = a, a = a.getSig()) {
            if (a.getDestino() == d) {
                if (prev == null) {
                    o.setAristas(a.getSig());   // era la primera arista
                } else {
                    prev.setSig(a.getSig());    // se la salta
                }
                return true;
            }
        }
        return false;
    }

    /** Quita una relacion sin tocar los vertices. O(V + grado) */
    public boolean eliminarArista(String o, String d) {
        Vertice vo = buscar(o);
        Vertice vd = buscar(d);
        return vo != null && vd != null && quitar(vo, vd);
    }

    /**
     * Quita un vertice y todas las aristas que LLEGAN a el. O(V + A)
     * Las aristas que SALEN de el desaparecen junto con su nodo.
     */
    public boolean eliminarVertice(String id) {
        Vertice v = buscar(id);
        if (v == null) {
            return false;
        }
        //PASO 1: cualquier vertice puede tener una arista hacia v; se quitan todas
        for (Vertice u = cabeza; u != null; u = u.getSig()) {
            quitar(u, v);
        }
        //PASO 2: desenlazar v de la lista de vertices con un puntero previo
        Vertice prev = null;
        for (Vertice u = cabeza; u != null; prev = u, u = u.getSig()) {
            if (u == v) {
                if (prev == null) {
                    cabeza = u.getSig();
                } else {
                    prev.setSig(u.getSig());
                }
                break;
            }
        }
        v.setSig(null);
        v.setAristas(null);
        return true;
    }

    // ===================== CONSULTAS =====================

    /** Lista de adyacencia, una linea por vertice: "Ana -> Mia(4) Luis(1)". */
    public String mostrar() {
        if (cabeza == null) {
            return "(grafo vacio)";
        }
        StringBuilder sb = new StringBuilder();
        for (Vertice v = cabeza; v != null; v = v.getSig()) {
            sb.append(v.getId()).append(" ->");
            for (Arista a = v.getAristas(); a != null; a = a.getSig()) {
                sb.append(' ').append(a.getDestino().getId()).append('(').append(a.getPeso()).append(')');
            }
            if (v.getSig() != null) {
                sb.append(System.lineSeparator());
            }
        }
        return sb.toString();
    }

    /** Grado de salida: cuantas aristas salen del vertice. -1 si no existe. */
    public int grado(String id) {
        Vertice v = buscar(id);
        if (v == null) {
            return -1;
        }
        int n = 0;
        for (Arista a = v.getAristas(); a != null; a = a.getSig()) {
            n++;
        }
        return n;
    }

    /** Grado de entrada: cuantas aristas llegan al vertice. -1 si no existe. O(V + A) */
    public int gradoEntrada(String id) {
        Vertice v = buscar(id);
        if (v == null) {
            return -1;
        }
        int n = 0;
        for (Vertice u = cabeza; u != null; u = u.getSig()) {
            if (buscarArista(u, v) != null) {
                n++;
            }
        }
        return n;
    }

    public int totalVertices() {
        int n = 0;
        for (Vertice v = cabeza; v != null; v = v.getSig()) {
            n++;
        }
        return n;
    }

    public int totalAristas() {
        int n = 0;
        for (Vertice v = cabeza; v != null; v = v.getSig()) {
            for (Arista a = v.getAristas(); a != null; a = a.getSig()) {
                n++;
            }
        }
        return n;
    }

    public boolean estaVacio() {
        return cabeza == null;
    }

    /** Vecinos de un vertice en el orden de su lista: "Luis Mia". null si no existe. */
    public String vecinos(String id) {
        Vertice v = buscar(id);
        if (v == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (Arista a = v.getAristas(); a != null; a = a.getSig()) {
            sb.append(sb.length() == 0 ? "" : " ").append(a.getDestino().getId());
        }
        return sb.toString();
    }

    // ===================== NO DIRIGIDO (continuacion del Grupo 4) =====================

    /**
     * Relacion no dirigida a - b (como la amistad del Grupo 4): se guarda como
     * dos aristas, a -> b y b -> a. Si la segunda falla se deshace la primera.
     */
    public boolean agregarRelacion(String a, String b, int peso) {
        if (!agregarArista(a, b, peso)) {
            return false;
        }
        if (!agregarArista(b, a, peso)) {
            eliminarArista(a, b);   //PASO 1: deshacer para no dejar media relacion
            return false;
        }
        return true;
    }

    /** Quita la relacion no dirigida a - b de los dos lados. */
    public boolean eliminarRelacion(String a, String b) {
        if (!existeArista(a, b) || !existeArista(b, a)) {
            return false;
        }
        eliminarArista(a, b);
        eliminarArista(b, a);
        return true;
    }

    /**
     * Matriz de adyacencia calculada desde la lista de adyacencia (1 = hay arista fila -> columna).
     * Sirve para comparar con la representacion que expuso el Grupo 4. O(V * (V + A))
     */
    public String matrizAdyacencia() {
        if (cabeza == null) {
            return "(grafo vacio)";
        }
        int ancho = 5;   //PASO 1: el ancho de columna sale del nombre mas largo
        for (Vertice c = cabeza; c != null; c = c.getSig()) {
            ancho = Math.max(ancho, c.getId().length());
        }
        String col = "%-" + (ancho + 1) + "s";
        StringBuilder sb = new StringBuilder(String.format(col, ""));
        for (Vertice c = cabeza; c != null; c = c.getSig()) {
            sb.append(String.format(col, c.getId()));
        }
        for (Vertice f = cabeza; f != null; f = f.getSig()) {
            sb.append(System.lineSeparator()).append(String.format(col, f.getId()));
            for (Vertice c = cabeza; c != null; c = c.getSig()) {
                sb.append(String.format(col, buscarArista(f, c) != null ? "1" : "0"));
            }
        }
        return sb.toString();
    }

    // ===================== RECORRIDOS =====================

    private void limpiarVisitados() {
        for (Vertice v = cabeza; v != null; v = v.getSig()) {
            v.setVisitado(false);
        }
    }

    /** BFS: cuantos vertices se alcanzan desde inicio (0 si no existe). O(V + A) */
    public int bfs(String inicio) {
        if (buscar(inicio) == null) {
            return 0;
        }
        recorridoBfs(inicio);
        return contarVisitados();
    }

    /** BFS: orden de visita separado por espacios, por ejemplo "Ana Luis Mia Leo". */
    public String recorridoBfs(String inicio) {
        return anchura(inicio, null);
    }

    /**
     * BFS con el contenido de la cola en cada paso (la tabla de la diapositiva).
     * O(V^2 + A): copia la cola en cada paso. Es para explicar, no para grafos grandes.
     */
    public String trazaBfs(String inicio) {
        StringBuilder traza = new StringBuilder();
        anchura(inicio, traza);
        return traza.toString().stripTrailing();
    }

    private String anchura(String inicio, StringBuilder traza) {
        Vertice s = buscar(inicio);
        if (s == null) {
            return "";
        }
        limpiarVisitados();
        StringBuilder orden = new StringBuilder();
        //PASO 1: encolar el inicio y marcarlo como visitado
        // La cola se enlaza con sigCola: no hace falta otra clase nodo porque
        // cada vertice se marca al encolarlo y por eso entra una sola vez.
        Vertice frente = s;
        Vertice fin = s;
        s.setSigCola(null);
        s.setVisitado(true);
        int paso = 1;
        while (frente != null) {
            String colaAntes = traza != null ? contenido(frente) : null;  // solo si se pide la traza
            //PASO 2: sacar el primero de la cola y procesarlo
            Vertice v = frente;
            frente = frente.getSigCola();
            v.setSigCola(null);
            if (frente == null) {
                fin = null;
            }
            orden.append(orden.length() == 0 ? "" : " ").append(v.getId());
            //PASO 3: encolar los vecinos que no estan visitados
            for (Arista a = v.getAristas(); a != null; a = a.getSig()) {
                Vertice w = a.getDestino();
                if (!w.isVisitado()) {
                    w.setVisitado(true);
                    w.setSigCola(null);
                    if (fin == null) {
                        frente = w;
                    } else {
                        fin.setSigCola(w);
                    }
                    fin = w;
                }
            }
            if (traza != null) {
                traza.append(String.format("Paso %d | visita: %-8s | cola: %s%n", paso++, v.getId(), colaAntes));
            }
        }
        return orden.toString();
    }

    private String contenido(Vertice n) {
        StringBuilder sb = new StringBuilder();
        for (; n != null; n = n.getSigCola()) {
            sb.append(sb.length() == 0 ? "" : ", ").append(n.getId());
        }
        return sb.toString();
    }

    /** DFS recursivo: cuantos vertices se alcanzan desde inicio. O(V + A) */
    public int dfs(String inicio) {
        if (buscar(inicio) == null) {
            return 0;
        }
        recorridoDfs(inicio);
        return contarVisitados();
    }

    /**
     * DFS recursivo: orden de visita, baja por una rama hasta el fondo antes de volver.
     * La pila es la pila de llamadas de Java: con una cadena muy larga de vertices
     * se llena (StackOverflowError); el limite depende de la JVM. Para la clase alcanza.
     */
    public String recorridoDfs(String inicio) {
        Vertice s = buscar(inicio);
        if (s == null) {
            return "";
        }
        limpiarVisitados();
        StringBuilder orden = new StringBuilder();
        profundidad(s, orden);
        return orden.toString();
    }

    private void profundidad(Vertice v, StringBuilder orden) {
        v.setVisitado(true);
        orden.append(orden.length() == 0 ? "" : " ").append(v.getId());
        for (Arista a = v.getAristas(); a != null; a = a.getSig()) {
            if (!a.getDestino().isVisitado()) {
                profundidad(a.getDestino(), orden);   // caso recursivo
            }
        }
    }                                                  // caso base: no quedan vecinos sin visitar

    private int contarVisitados() {
        int n = 0;
        for (Vertice v = cabeza; v != null; v = v.getSig()) {
            if (v.isVisitado()) {
                n++;
            }
        }
        return n;
    }

    /**
     * true si desde inicio se alcanzan TODOS los vertices siguiendo el sentido de las aristas.
     * false si el grafo esta vacio o el inicio no existe.
     */
    public boolean esConexo(String inicio) {
        return buscar(inicio) != null && bfs(inicio) == totalVertices();
    }
}
