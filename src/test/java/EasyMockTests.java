import org.junit.jupiter.api.Test;

import java.util.List;

import static org.easymock.EasyMock.mock;
import static org.easymock.EasyMock.verify;

public class EasyMockTests {
    @Test
    public void TestEasyMock(){
        List mockedList = mock(List.class);

        mockedList.add("one");
        mockedList.clear();
        verify(mockedList.add("one"));
    }
}
