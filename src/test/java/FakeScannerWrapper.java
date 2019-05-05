import java.util.Queue;

public class FakeScannerWrapper implements IScanner {
    public Queue<Integer> nextInts;
    public Queue<String> nexts;
    public Queue<Boolean> hasNexts;
    @Override
    public String next() {
        return nexts.poll();
    }

    @Override
    public boolean hasNextInt() {
        return hasNexts.poll();
    }

    @Override
    public int nextInt() {
        return nextInts.poll();
    }
}
