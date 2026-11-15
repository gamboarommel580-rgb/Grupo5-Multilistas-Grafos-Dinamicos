package app;

import negocio.Grafo;

/**
 * Solucion del reto: "grafo conexo o desconectado?"
 * Solo se usan Vertice, Arista y Grafo (sin HashMap, ArrayList ni librerias de grafos).
 */
public class RetoSolucion {

    public static void main(String[] args) {

        // ---------- 1. Construir: Grafo A (6 vertices, 6 aristas) ----------
        System.out.println("=== Grafo A ===");
        Grafo a = new Grafo();
        for (String n : new String[]{"Noa", "Eva", "Leo", "Mia", "Luis", "Ana"}) {
            a.agregarVertice(n);
        }
        a.agregarArista("Ana", "Mia", 1);
        a.agregarArista("Ana", "Luis", 1);
        a.agregarArista("Luis", "Leo", 1);
        a.agregarArista("Mia", "Leo", 1);
        a.agregarArista("Leo", "Eva", 1);
        a.agregarArista("Eva", "Noa", 1);
        System.out.println("Vertices: " + a.totalVertices() + " | Aristas: " + a.totalAristas());

        // ---------- 2. Consultar ----------
        System.out.println(a.mostrar());
        System.out.println("BFS desde Ana: " + a.recorridoBfs("Ana"));
        System.out.println("grado(Ana) = " + a.grado("Ana") + " | grado(Leo) = " + a.grado("Leo")
                + " | grado(Noa) = " + a.grado("Noa"));
        System.out.println("Conexo desde Ana: " + a.esConexo("Ana")
                + " (alcanza " + a.bfs("Ana") + " de " + a.totalVertices() + ")");

        // ---------- 3. Decidir: Grafo B (5 vertices, 3 aristas) ----------
        System.out.println();
        System.out.println("=== Grafo B ===");
        Grafo b = new Grafo();
        for (String n : new String[]{"Pia", "Zoe", "Rex", "Tito", "Sol"}) {
            b.agregarVertice(n);
        }
        b.agregarArista("Sol", "Tito", 1);
        b.agregarArista("Tito", "Rex", 1);
        b.agregarArista("Zoe", "Pia", 1);
        System.out.println(b.mostrar());
        System.out.println("BFS desde Sol: " + b.recorridoBfs("Sol"));
        System.out.println("Conexo desde Sol: " + b.esConexo("Sol")
                + " (alcanza " + b.bfs("Sol") + " de " + b.totalVertices() + ")");

        System.out.println();
        System.out.println("Eliminar Zoe y decidir otra vez:");
        b.eliminarVertice("Zoe");
        System.out.println("Conexo desde Sol: " + b.esConexo("Sol")
                + " (alcanza " + b.bfs("Sol") + " de " + b.totalVertices() + "; Pia sigue aislada)");

        System.out.println();
        System.out.println("Eliminar Leo en el Grafo A y decidir otra vez:");
        a.eliminarVertice("Leo");
        System.out.println(a.mostrar());
        System.out.println("Conexo desde Ana: " + a.esConexo("Ana")
                + " (alcanza " + a.bfs("Ana") + " de " + a.totalVertices() + ")");

        // ---------- Casos limite ----------
        System.out.println();
        System.out.println("=== Casos limite ===");
        Grafo vacio = new Grafo();
        System.out.println("Grafo vacio: " + vacio.mostrar() + " | esConexo(Ana) = " + vacio.esConexo("Ana"));
        System.out.println("Vertice repetido (Ana): " + a.agregarVertice("Ana"));
        System.out.println("Arista hacia vertice inexistente (Ana -> Zoe): " + a.agregarArista("Ana", "Zoe", 1));
        System.out.println("Eliminar Noa (recibe la arista Eva -> Noa): " + a.eliminarVertice("Noa"));
        System.out.println("Eva ya no apunta a Noa: " + !a.existeArista("Eva", "Noa") + " | grado(Eva) = " + a.grado("Eva"));
        System.out.println(a.mostrar());
    }
}
