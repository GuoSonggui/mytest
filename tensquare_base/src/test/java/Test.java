public class Test {
    static {
        int x = 5;
    }

    static int x, y;

    public static void main(String[] args) {
        x--;
        System.out.println(x);

        myMethod();
        System.out.println( x + y++ + x);

    }

    private static void myMethod() {
        y = x++ + ++x;
        System.out.println("y:" + y);
        System.out.println("x;" + x);
    }
}
