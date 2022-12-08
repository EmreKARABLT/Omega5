package TIME_COMPLEXITY_ANALISIS;

public class MemoryCrash {
    /**
     * Realiza reservas de memoria, de 10MB en 10MB, en un bucle;
     * y va mostrando el estado de la memoria de la JVM.
     *
     * @param args parametros de linea de comandos (no usado)
     */
    public static void main(String[] args) {
        int max = 100;
        Runtime rt = Runtime.getRuntime();
        Object[] objects = new Object[max];
        for (int i=0; i<max; i++) {
            System.out.println("Tenemos " + i*10 + "MB y añadimos 10MB");
            objects[i] = new long[10*1024*128];
            System.out.println(" Memoria libre: " + rt.freeMemory());
            System.out.println(" Memoria total: " + rt.totalMemory());
            try {
                Thread.sleep(2*1000);
            } catch (InterruptedException ie) {
                System.out.println("Interrumpido.");
            }
        }
    }
}
