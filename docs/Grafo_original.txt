import java.util.*;

public class Grafo {
    static class Arista { Vertice destino; int peso; Arista sig;
        Arista(Vertice d, int p) { destino = d; peso = p; } }
    static class Vertice { String id; Arista aristas; Vertice sig;
        Vertice(String id) { this.id = id; } }

    private Vertice cabeza;

    Vertice buscar(String id) {
        for (Vertice v = cabeza; v != null; v = v.sig) if (v.id.equals(id)) return v;
        return null;
    }
    private Arista buscarArista(Vertice o, Vertice d) {
        for (Arista a = o.aristas; a != null; a = a.sig) if (a.destino == d) return a;
        return null;
    }
    // CREATE
    boolean agregarVertice(String id) {
        if (buscar(id) != null) return false;
        Vertice v = new Vertice(id); v.sig = cabeza; cabeza = v; return true;
    }
    boolean agregarArista(String o, String d, int peso) {
        Vertice vo = buscar(o), vd = buscar(d);
        if (vo == null || vd == null || buscarArista(vo, vd) != null) return false;
        Arista a = new Arista(vd, peso); a.sig = vo.aristas; vo.aristas = a; return true;
    }
    // UPDATE
    boolean actualizarPeso(String o, String d, int peso) {
        Vertice vo = buscar(o), vd = buscar(d);
        if (vo == null || vd == null) return false;
        Arista a = buscarArista(vo, vd);
        if (a == null) return false;
        a.peso = peso; return true;
    }
    // DELETE
    private boolean quitar(Vertice o, Vertice d) {
        Arista prev = null;
        for (Arista a = o.aristas; a != null; prev = a, a = a.sig)
            if (a.destino == d) {
                if (prev == null) o.aristas = a.sig; else prev.sig = a.sig;
                return true;
            }
        return false;
    }
    boolean eliminarArista(String o, String d) {
        Vertice vo = buscar(o), vd = buscar(d);
        return vo != null && vd != null && quitar(vo, vd);
    }
    boolean eliminarVertice(String id) {
        Vertice v = buscar(id);
        if (v == null) return false;
        for (Vertice u = cabeza; u != null; u = u.sig) quitar(u, v);
        Vertice prev = null;
        for (Vertice u = cabeza; u != null; prev = u, u = u.sig)
            if (u == v) { if (prev == null) cabeza = u.sig; else prev.sig = u.sig; break; }
        return true;
    }
    // READ
    void mostrar() {
        for (Vertice v = cabeza; v != null; v = v.sig) {
            StringBuilder sb = new StringBuilder(v.id + " -> ");
            for (Arista a = v.aristas; a != null; a = a.sig)
                sb.append(a.destino.id).append("(").append(a.peso).append(") ");
            System.out.println(sb);
        }
    }
    // Recorrido BFS: devuelve cuántos vértices alcanza desde 'inicio'
    int bfs(String inicio) {
        Vertice s = buscar(inicio);
        if (s == null) return 0;
        Queue<Vertice> cola = new LinkedList<>();
        Set<Vertice> visitados = new HashSet<>();
        cola.add(s); visitados.add(s);
        while (!cola.isEmpty()) {
            Vertice v = cola.poll();
            System.out.print(v.id + " ");
            for (Arista a = v.aristas; a != null; a = a.sig)
                if (visitados.add(a.destino)) cola.add(a.destino);
        }
        System.out.println();
        return visitados.size();
    }
    // Grado: cuántas aristas salen del vértice (-1 si no existe)
    int grado(String id) {
        Vertice v = buscar(id);
        if (v == null) return -1;
        int n = 0;
        for (Arista a = v.aristas; a != null; a = a.sig) n++;
        return n;
    }
    // Recorrido DFS (recursivo): devuelve cuántos vértices alcanza
    int dfs(String inicio) {
        Vertice s = buscar(inicio);
        if (s == null) return 0;
        Set<Vertice> visitados = new HashSet<>();
        dfs(s, visitados);
        System.out.println();
        return visitados.size();
    }
    private void dfs(Vertice v, Set<Vertice> visitados) {
        visitados.add(v);
        System.out.print(v.id + " ");
        for (Arista a = v.aristas; a != null; a = a.sig)
            if (!visitados.contains(a.destino)) dfs(a.destino, visitados);
    }
    int totalVertices() { int n = 0; for (Vertice v = cabeza; v != null; v = v.sig) n++; return n; }
    boolean esConexo(String inicio) { return bfs(inicio) == totalVertices(); }

    public static void main(String[] args) {
        Grafo g = new Grafo();
        for (String n : new String[]{"Ana", "Luis", "Mia", "Leo"}) g.agregarVertice(n);
        g.agregarArista("Ana", "Luis", 1); g.agregarArista("Luis", "Mia", 2);
        g.agregarArista("Mia", "Leo", 3);
        g.mostrar();
        System.out.println("Conexo desde Ana: " + g.esConexo("Ana"));
        g.actualizarPeso("Luis", "Mia", 5);
        g.eliminarArista("Mia", "Leo");
        System.out.println("Conexo tras eliminar: " + g.esConexo("Ana"));
        g.eliminarVertice("Luis");
        g.mostrar();
    }
}
