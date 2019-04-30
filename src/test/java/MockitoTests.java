import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Scanner;

import static org.mockito.Mockito.*;

public class MockitoTests {

    @Test
    public void ConnectFourShouldCallAddPlayerMethod(){
        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.nextInt())
                .thenReturn(3)
                .thenReturn(0);
        ConnectFour.reader = mockScanner;
        ConnectFour.menu();
        verify(mockScanner, times(1)).next();
    }
}
