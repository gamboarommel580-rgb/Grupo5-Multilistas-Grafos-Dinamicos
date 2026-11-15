package app;

import negocio.Grafo;

/**
 * Puente con el Grupo 4: el mismo grafo de ejemplo de su clase
 * V = {A, B, C, D}, E = {AB, AC, BC, CD}, ahora construido con la lista de adyacencia propia
 * (sin Map ni Set). Cada amistad no dirigida se guarda como dos aristas.
 */
public class RedSocialG4 {

    public static void main(String[] args) {

        Grafo red = new Grafo();

        //PASO 1: registrar usuarios (sin duplicados)
        for (String n : new String[]{"D", "C", "B", "A"}) {
            red.agregarVertice(n);
        }
        System.out.println("Registrar A otra vez: " + red.agregarVertice("A"));

        //PASO 2: crear amistades (en los dos sentidos)
        red.agregarRelacion("C", "D", 1);
        red.agregarRelacion("B", "C", 1);
        red.agregarRelacion("A", "C", 1);
        red.agregarRelacion("A", "B", 1);
        System.out.println("Lazo A - A: " + red.agregarRelacion("A", "A", 1));
        System.out.println("Amistad con usuario inexistente A - Z: " + red.agregarRelacion("A", "Z", 1));

        //PASO 3: consultar amigos y grados
        System.out.println();
        System.out.println(red.mostrar());
        int suma = 0;
        for (String n : new String[]{"A", "B", "C", "D"}) {
            System.out.println("amigos(" + n + ") = " + red.vecinos(n) + " | grado = " + red.grado(n));
            suma += red.grado(n);
        }
        System.out.println("Suma de grados = " + suma + " = 2 x " + (red.totalAristas() / 2) + " amistades");

        //PASO 4: la misma red como matriz (lo que expuso el Grupo 4)
        System.out.println();
        System.out.println("Matriz de adyacencia (simetrica, diagonal en 0):");
        System.out.println(red.matrizAdyacencia());

        //PASO 5: quitar la amistad C - D y verificar los dos lados
        System.out.println();
        System.out.println("Quitar C - D: " + red.eliminarRelacion("C", "D"));
        System.out.println("C -> D existe: " + red.existeArista("C", "D") + " | D -> C existe: " + red.existeArista("D", "C"));
        System.out.println("Conexa desde A: " + red.esConexo("A") + " (D quedo aislado)");

        //PASO 6: eliminar usuario limpiando todas sus referencias
        System.out.println("Eliminar C: " + red.eliminarVertice("C"));
        System.out.println(red.mostrar());
    }
}
