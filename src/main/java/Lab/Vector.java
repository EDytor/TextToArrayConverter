package Lab;

public class Vector {
        private final double first;
        private final double second;
        private final double third;
//      a)
//      Vector () {}
//      b)
        Vector (Double x, Double y, Double z){
                first = x;
                second = y;
                third = z;
        }
        void module() {
                double u = Math.sqrt(first * first + second + second + third * third);
                System.out.println("module u = " + u);
        }
        void print() {
                System.out.println("x = " + first);
                System.out.println("y = " + second);
                System.out.println("z = " + third);
        }
}



