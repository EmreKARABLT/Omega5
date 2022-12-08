package TIME_COMPLEXITY_ANALISIS;

public class FitLine {


    static double[] x = {37, 33, 29, 25, 21, 17, 13, 9, 5};
    static double[] y = {141, 94, 83, 73, 115, 11, 8, 20, 18};

    public static void main(String[] args) {
        x =  ExcelArray.b;
        y =  ExcelArray.a;
        System.out.println(x.length);
        System.out.println(y.length);
        if(!(x.length == y.length)){System.exit(0);}

        Regresion ln = new Regresion(x, y);
        ln.lineal();
        System.out.println("Correlation = " + ln.correlacion());
    }
}
