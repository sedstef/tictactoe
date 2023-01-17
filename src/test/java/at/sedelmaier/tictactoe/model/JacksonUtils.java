package at.sedelmaier.tictactoe.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.bind.JAXBException;

public class JacksonUtils {

    public static Board serialize(final Board board) throws IOException, JAXBException {
        byte[] buffer = marshal(board);
        return unmarshal(buffer);
    }

    private static byte[] marshal(final Board board) throws JAXBException, IOException {
        try (final ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            new ObjectMapper().writeValue(os, board);
            return os.toByteArray();
        }
    }

    private static Board unmarshal(byte[] buffer) throws JAXBException, IOException {
        try (final InputStream is = new ByteArrayInputStream(buffer)) {
            return new ObjectMapper().readValue(is, Board.class);
        }
    }

}
