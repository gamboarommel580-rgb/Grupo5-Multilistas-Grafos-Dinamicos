package modelo;

// Nodo de la lista de vertices: su nombre, la primera arista de su lista y el siguiente vertice.
public class Vertice {

    private final String id;
    private Arista aristas;   // null = el vertice no tiene aristas
    private Vertice sig;      // null = es el ultimo de la lista de vertices
    private boolean visitado; // marca que usan BFS y DFS (se limpia antes de cada recorrido)
    private Vertice sigCola;  // enlace de la cola del BFS (cada vertice entra una sola vez)

    public Vertice(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Arista getAristas() {
        return aristas;
    }

    public void setAristas(Arista aristas) {
        this.aristas = aristas;
    }

    public Vertice getSig() {
        return sig;
    }

    public void setSig(Vertice sig) {
        this.sig = sig;
    }

    public boolean isVisitado() {
        return visitado;
    }

    public void setVisitado(boolean visitado) {
        this.visitado = visitado;
    }

    public Vertice getSigCola() {
        return sigCola;
    }

    public void setSigCola(Vertice sigCola) {
        this.sigCola = sigCola;
    }
}
