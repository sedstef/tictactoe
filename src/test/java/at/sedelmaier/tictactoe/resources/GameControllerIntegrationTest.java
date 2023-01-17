package at.sedelmaier.tictactoe.resources;

import at.sedelmaier.tictactoe.service.GameService;
import at.sedelmaier.tictactoe.model.Board;
import at.sedelmaier.tictactoe.model.Player;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(GameController.class)
public class GameControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GameService service;

    @Test
    public void getBoard() throws Exception {
        //arrange
        Board board = new Board();
        board.setPlayer(1, 1, Player.BLUE);
        board.setPlayer(2, 2, Player.RED);

        when(service.getBoard()).thenReturn(board);

        //act + assert
        mvc.perform(get("/game"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("data[1][1]", is("blue")))
                .andExpect(jsonPath("data[2][2]", is("red")))
                .andDo(print());
    }

}
