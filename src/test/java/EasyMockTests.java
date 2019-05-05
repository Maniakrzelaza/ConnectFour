import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Scanner;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;

import static org.easymock.EasyMock.*;

@PrepareForTest(Scanner.class)
public class EasyMockTests{
   /* @Test
    public void GameShouldLoadGame(){
        Scanner mockAppScanner = PowerMock.createMock(Scanner.class);
        expect(mockAppScanner.nextInt())
                .andReturn(4)
                .andReturn(0);
        Scanner mockGameScanner = PowerMock.createMock(Scanner.class);
        expect(mockGameScanner.nextInt())
                .andReturn(0)
                .andReturn(1);
        expect(mockGameScanner.hasNextInt())
                .andReturn(false);
        expect(mockGameScanner.next())
                .andReturn("e");
        GameSaver mockGameSaver = mock(GameSaver.class);
        expect(mockGameSaver.loadGame())
                .andReturn(new GameState());
        RankingList mockRankingList = mock(RankingList.class);
        HashMap<String, Player> fakeList = new HashMap<>();
        fakeList.put("Player1", new Player( "Player1", 1));
        fakeList.put("Player2", new Player( "Player2", 2));
        expect(mockRankingList.getList())
                .andReturn(fakeList);
        PowerMock.replay(mockAppScanner);
        PowerMock.replay(mockGameSaver);
        replay(mockRankingList);
        replay(mockGameScanner);
        ConnectFour.reader = mockAppScanner;
        ConnectFour.game.reader = mockGameScanner;
        PowerMock.verify(mockAppScanner);
        PowerMock.verify(mockGameSaver);
        verify(mockRankingList);
        verify(mockGameScanner);
    }*/
}
