package app;

import negocio.Grafo;

/**
 * Demo de la exposicion (Grupo 5): usa todas las operaciones del Grafo.
 * Capas: app -> negocio (Grafo) -> modelo (Vertice, Arista).
 */
public class Main {

    public static void main(String[] args) {

        // ---------- Parte 1: el mismo Main de la diapositiva ----------
        System.out.println("=== Parte 1: demo de la diapositiva ===");
        Grafo g = new Grafo();
        for (String n : new String[]{"Ana", "Luis", "Mia", "Leo"}) {
            g.agregarVertice(n);
        }
        g.agregarArista("Ana", "Luis", 1);
        g.agregarArista("Luis", "Mia", 2);
        g.agregarArista("Mia", "Leo", 3);
        System.out.println(g.mostrar());
        System.out.println("BFS desde Ana: " + g.recorridoBfs("Ana"));
        System.out.println("Conexo desde Ana: " + g.esConexo("Ana"));
        g.actualizarPeso("Luis", "Mia", 5);
        System.out.println("Peso Luis -> Mia actualizado a: " + g.peso("Luis", "Mia"));
        g.eliminarArista("Mia", "Leo");
        System.out.println("BFS desde Ana: " + g.recorridoBfs("Ana"));
        System.out.println("Conexo tras eliminar Mia -> Leo: " + g.esConexo("Ana"));
        g.eliminarVertice("Luis");
        System.out.println(g.mostrar());

        // ---------- Parte 2: la lista de adyacencia de la diapositiva 6 ----------
        // Se inserta al inicio, por eso se agregan en orden inverso:
        // asi la lista queda Ana, Luis, Mia, Leo y los vecinos de Ana quedan Luis, Mia.
        System.out.println();
        System.out.println("=== Parte 2: lista de adyacencia Ana -> [Luis,1] -> [Mia,4] ===");
        Grafo m = new Grafo();
        for (String n : new String[]{"Leo", "Mia", "Luis", "Ana"}) {
            m.agregarVertice(n);
        }
        m.agregarArista("Ana", "Mia", 4);
        m.agregarArista("Ana", "Luis", 1);
        m.agregarArista("Luis", "Leo", 2);
        m.agregarArista("Mia", "Leo", 3);
        System.out.println(m.mostrar());
        System.out.println("Vertices: " + m.totalVertices() + " | Aristas: " + m.totalAristas());
        System.out.println("Grado de salida de Ana: " + m.grado("Ana")
                + " | Grado de entrada de Leo: " + m.gradoEntrada("Leo"));
        System.out.println();
        System.out.println("BFS desde Ana (con la cola en cada paso):");
        System.out.println(m.trazaBfs("Ana"));
        System.out.println("BFS: " + m.recorridoBfs("Ana") + "  -> alcanza " + m.bfs("Ana"));
        System.out.println("DFS: " + m.recorridoDfs("Ana") + "  -> alcanza " + m.dfs("Ana"));
        System.out.println("Conexo desde Ana: " + m.esConexo("Ana"));
        System.out.println("Conexo desde Leo: " + m.esConexo("Leo") + " (Leo no tiene aristas de salida)");
        System.out.println();
        System.out.println("Eliminar Luis (tiene una arista entrante desde Ana):");
        m.eliminarVertice("Luis");
        System.out.println(m.mostrar());
        System.out.println("Ana ya no apunta a Luis: " + !m.existeArista("Ana", "Luis"));
    }
}
