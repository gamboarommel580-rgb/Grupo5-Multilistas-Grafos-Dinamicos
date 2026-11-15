package app;

import negocio.Grafo;

/**
 * RETO (Grupo 5): grafo conexo o desconectado?
 * Completen los TODO usando SOLO los metodos de Grafo.
 * Prohibido: HashMap, ArrayList y librerias de grafos.
 * Ejecutar: java -cp out app.RetoPlantilla
 */
public class RetoPlantilla {

    public static void main(String[] args) {

        //PASO 1: Construir (10 min)
        // Grafo A: Ana -> Mia, Ana -> Luis, Mia -> Leo, Luis -> Leo, Leo -> Eva, Eva -> Noa
        Grafo a = new Grafo();
        // TODO: agregar los 6 vertices con a.agregarVertice(...)
        // TODO: agregar las 6 aristas con a.agregarArista(origen, destino, peso)

        //PASO 2: Consultar (10 min)
        // TODO: imprimir a.mostrar()
        // TODO: imprimir a.recorridoBfs("Ana")
        // TODO: imprimir a.grado(...) de al menos dos vertices

        //PASO 3: Decidir (15 min)
        // Grafo B: Sol -> Tito, Tito -> Rex, Zoe -> Pia
        Grafo b = new Grafo();
        // TODO: construir B, imprimir b.esConexo("Sol")
        // TODO: eliminar un vertice con eliminarVertice(...) y volver a decidir

        //CASOS LIMITE
        // TODO: grafo vacio, vertice repetido, arista hacia un vertice inexistente,
        //       eliminar un vertice que tiene aristas entrantes

        System.out.println("Vertices en A: " + a.totalVertices() + " | Vertices en B: " + b.totalVertices());
    }
}
