import org.junit.jupiter.api.Test;

import java.util.List;

import static org.easymock.EasyMock.*;

public class EasyMockTests {
    @Test
    public void TestEasyMock(){
        List mockedList = mock(List.class);
        expect(mockedList.add("one")).andReturn(true);
        replay(mockedList);
        mockedList.add("one");
        verify(mockedList);
    }
}
