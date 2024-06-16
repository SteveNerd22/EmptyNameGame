import Features.Map.Map;

public class Main {
    public static void main(String[] args) {
        long start = System.nanoTime();
        for(int i = 0; i < 1000; i++) {
            Map map = new Map(1);
            map.printMap();
            System.out.println();
            System.out.println();
            System.out.println(((double)(System.nanoTime()-start))/((double)(1000000)));
        }
        long end = System.nanoTime();
        long elapsed = end - start;
        System.out.println("tempo esecuzione: "+ ((double)(elapsed)/(double)(1000000000)));
    }
}