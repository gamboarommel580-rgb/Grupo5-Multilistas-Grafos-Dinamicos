package modelo;

// Nodo de la lista de aristas de un vertice: a donde llega, cuanto cuesta y la siguiente arista.
public class Arista {

    private final Vertice destino;
    private int peso;
    private Arista sig;

    public Arista(Vertice destino, int peso) {
        this.destino = destino;
        this.peso = peso;
    }

    public Vertice getDestino() {
        return destino;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public Arista getSig() {
        return sig;
    }

    public void setSig(Arista sig) {
        this.sig = sig;
    }
}
