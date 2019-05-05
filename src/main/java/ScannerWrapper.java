import java.util.Scanner;

public class ScannerWrapper implements IScanner{
    Scanner scanner = new Scanner(System.in);

    @Override
    public String next() {
        return scanner.next();
    }

    @Override
    public boolean hasNextInt() {
        return scanner.hasNextInt();
    }

    @Override
    public int nextInt() {
        return scanner.nextInt();
    }
}
