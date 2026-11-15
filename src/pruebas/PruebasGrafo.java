package pruebas;

import negocio.Grafo;

/**
 * Pruebas sin JUnit: cada caso imprime OK o FALLA y al final el total.
 * Ejecutar: java -cp out pruebas.PruebasGrafo
 */
public class PruebasGrafo {

    private static int ok = 0;
    private static int total = 0;

    private static void verificar(String nombre, boolean condicion) {
        total++;
        if (condicion) {
            ok++;
            System.out.println("OK    " + nombre);
        } else {
            System.out.println("FALLA " + nombre);
        }
    }

    private static Grafo grafoA() {
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
        return a;
    }

    private static Grafo grafoB() {
        Grafo b = new Grafo();
        for (String n : new String[]{"Pia", "Zoe", "Rex", "Tito", "Sol"}) {
            b.agregarVertice(n);
        }
        b.agregarArista("Sol", "Tito", 1);
        b.agregarArista("Tito", "Rex", 1);
        b.agregarArista("Zoe", "Pia", 1);
        return b;
    }

    public static void main(String[] args) {

        //PASO 1: grafo vacio
        Grafo vacio = new Grafo();
        verificar("vacio: estaVacio", vacio.estaVacio());
        verificar("vacio: totalVertices = 0", vacio.totalVertices() == 0);
        verificar("vacio: mostrar", vacio.mostrar().equals("(grafo vacio)"));
        verificar("vacio: esConexo es false", !vacio.esConexo("Ana"));
        verificar("vacio: bfs = 0", vacio.bfs("Ana") == 0);
        verificar("vacio: recorridoDfs vacio", vacio.recorridoDfs("Ana").isEmpty());
        verificar("vacio: eliminarVertice false", !vacio.eliminarVertice("Ana"));

        //PASO 2: insercion de vertices
        Grafo g = new Grafo();
        verificar("agregarVertice Ana", g.agregarVertice("Ana"));
        verificar("vertice repetido rechazado", !g.agregarVertice("Ana"));
        verificar("vertice repetido con espacios rechazado", !g.agregarVertice("  Ana "));
        verificar("id null rechazado", !g.agregarVertice(null));
        verificar("id en blanco rechazado", !g.agregarVertice("   "));
        verificar("id con espacio interno rechazado", !g.agregarVertice("Ana Maria"));
        verificar("totalVertices = 1", g.totalVertices() == 1);

        //PASO 3: insercion de aristas
        g.agregarVertice("Luis");
        verificar("agregarArista Ana -> Luis", g.agregarArista("Ana", "Luis", 1));
        verificar("arista repetida rechazada", !g.agregarArista("Ana", "Luis", 7));
        verificar("arista hacia vertice inexistente", !g.agregarArista("Ana", "Zoe", 1));
        verificar("arista desde vertice inexistente", !g.agregarArista("Zoe", "Ana", 1));
        verificar("lazo rechazado", !g.agregarArista("Ana", "Ana", 1));
        verificar("peso negativo rechazado", !g.agregarArista("Luis", "Ana", -3));
        verificar("es dirigido: no existe Luis -> Ana", !g.existeArista("Luis", "Ana"));
        verificar("peso Ana -> Luis = 1", g.peso("Ana", "Luis") == 1);

        //PASO 4: actualizar peso
        verificar("actualizarPeso a 5", g.actualizarPeso("Ana", "Luis", 5) && g.peso("Ana", "Luis") == 5);
        verificar("actualizarPeso arista inexistente", !g.actualizarPeso("Luis", "Ana", 2));
        verificar("actualizarPeso negativo rechazado", !g.actualizarPeso("Ana", "Luis", -1));
        verificar("peso de arista inexistente = -1", g.peso("Luis", "Ana") == -1);

        //PASO 5: insercion al inicio
        Grafo m = new Grafo();
        for (String n : new String[]{"Leo", "Mia", "Luis", "Ana"}) {
            m.agregarVertice(n);
        }
        m.agregarArista("Ana", "Mia", 4);
        m.agregarArista("Ana", "Luis", 1);
        m.agregarArista("Luis", "Leo", 2);
        m.agregarArista("Mia", "Leo", 3);
        verificar("mostrar primera linea", m.mostrar().startsWith("Ana -> Luis(1) Mia(4)"));
        verificar("BFS Ana Luis Mia Leo", m.recorridoBfs("Ana").equals("Ana Luis Mia Leo"));
        verificar("DFS Ana Luis Leo Mia", m.recorridoDfs("Ana").equals("Ana Luis Leo Mia"));
        verificar("grado(Ana) = 2", m.grado("Ana") == 2);
        verificar("gradoEntrada(Leo) = 2", m.gradoEntrada("Leo") == 2);
        verificar("grado de inexistente = -1", m.grado("Zoe") == -1);
        verificar("totalAristas = 4", m.totalAristas() == 4);
        verificar("trazaBfs tiene 4 pasos", m.trazaBfs("Ana").split("\\R").length == 4);
        verificar("trazaBfs: al visitar Luis la cola era Luis, Mia", m.trazaBfs("Ana").contains("visita: Luis     | cola: Luis, Mia"));

        //PASO 6: el reto
        Grafo a = grafoA();
        verificar("A: 6 vertices y 6 aristas", a.totalVertices() == 6 && a.totalAristas() == 6);
        verificar("A: bfs desde Ana alcanza 6", a.bfs("Ana") == 6);
        verificar("A: dfs desde Ana alcanza 6", a.dfs("Ana") == 6);
        verificar("A: conexo desde Ana", a.esConexo("Ana"));
        verificar("A: no conexo desde Noa", !a.esConexo("Noa"));
        Grafo b = grafoB();
        verificar("B: bfs desde Sol alcanza 3", b.bfs("Sol") == 3);
        verificar("B: desconectado desde Sol", !b.esConexo("Sol"));
        verificar("esConexo con inicio inexistente es false", !b.esConexo("Zeus"));

        //PASO 7: eliminar arista y vertice
        verificar("eliminarArista Eva -> Noa", a.eliminarArista("Eva", "Noa"));
        verificar("eliminarArista ya eliminada", !a.eliminarArista("Eva", "Noa"));
        verificar("A sin Eva -> Noa: no conexo", !a.esConexo("Ana"));
        a.agregarArista("Eva", "Noa", 1);
        verificar("eliminar Leo (2 aristas entrantes)", a.eliminarVertice("Leo"));
        verificar("Leo ya no existe", !a.existeVertice("Leo"));
        verificar("Luis ya no apunta a Leo", a.grado("Luis") == 0 && a.grado("Mia") == 0);
        verificar("A sin Leo: 5 vertices, 3 aristas", a.totalVertices() == 5 && a.totalAristas() == 3);
        verificar("A sin Leo: no conexo", !a.esConexo("Ana"));
        verificar("eliminar la cabeza (Ana)", a.eliminarVertice("Ana") && !a.existeVertice("Ana"));
        verificar("eliminar el ultimo (Noa)", a.eliminarVertice("Noa") && a.grado("Eva") == 0);
        verificar("eliminar inexistente", !a.eliminarVertice("Zeus"));

        //PASO 8: red no dirigida del Grupo 4 (A-B, A-C, B-C, C-D)
        Grafo r = new Grafo();
        for (String n : new String[]{"D", "C", "B", "A"}) {
            r.agregarVertice(n);
        }
        r.agregarRelacion("C", "D", 1);
        r.agregarRelacion("B", "C", 1);
        r.agregarRelacion("A", "C", 1);
        r.agregarRelacion("A", "B", 1);
        verificar("G4: grados 2, 2, 3, 1", r.grado("A") == 2 && r.grado("B") == 2
                && r.grado("C") == 3 && r.grado("D") == 1);
        verificar("G4: 4 amistades = 8 aristas", r.totalAristas() == 8);
        verificar("G4: vecinos(A) = B C", "B C".equals(r.vecinos("A")));
        verificar("G4: vecinos de inexistente = null", r.vecinos("Z") == null);
        verificar("G4: lazo rechazado", !r.agregarRelacion("A", "A", 1));
        verificar("G4: relacion repetida rechazada", !r.agregarRelacion("B", "A", 1));
        verificar("G4: matriz fila A", r.matrizAdyacencia().contains("A     0     1     1     0"));
        verificar("G4: quitar C - D de los dos lados", r.eliminarRelacion("C", "D")
                && !r.existeArista("C", "D") && !r.existeArista("D", "C"));
        verificar("G4: quitar C - D otra vez", !r.eliminarRelacion("C", "D"));
        verificar("G4: D aislado, no conexa", !r.esConexo("A"));
        Grafo largo = new Grafo();
        largo.agregarVertice("Sebastian");
        largo.agregarVertice("Kerly");
        largo.agregarRelacion("Sebastian", "Kerly", 1);
        verificar("matriz con nombres largos alineada", largo.matrizAdyacencia().contains("Kerly     0         1"));
        r.agregarArista("D", "A", 1);
        verificar("G4: relacion a medias se deshace", !r.agregarRelacion("A", "D", 1)
                && !r.existeArista("A", "D"));
        verificar("un solo vertice es conexo", grafoUnico().esConexo("X"));

        System.out.println();
        System.out.println(ok + "/" + total + " pruebas OK");
        if (ok != total) {
            System.exit(1);
        }
    }

    private static Grafo grafoUnico() {
        Grafo u = new Grafo();
        u.agregarVertice("X");
        return u;
    }
}
