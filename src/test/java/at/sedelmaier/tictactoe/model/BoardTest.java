package at.sedelmaier.tictactoe.model;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;

public class BoardTest {

    @Test
    public void testGetData() throws Exception {
        //arrange
        Board board = new Board();
        board.setPlayer(1,1, Player.RED);
        board.setPlayer(1,1, Player.BLUE);

        //act
        Board result = JacksonUtils.serialize(board);

        //assert
        assertThat(result.getData(), is(board.getData()));
    }

}
