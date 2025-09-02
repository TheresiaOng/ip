package katsu.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ParserTest {
    private final String[] words = {"Deadline", "eat", "apple", "/by", "2025-12-01", "14:35"};

    @Test
    public void findWord_byZero_three() {
        assertEquals(3, Parser.findWord(words, "/by", 0));
    }

    @Test
    public void findWord_byNegOne_three() {
        assertEquals(3, Parser.findWord(words, "/by", -1));
    }

    @Test
    public void findWord_byFour_negativeOne() {
        assertEquals(-1, Parser.findWord(words, "/by", 4));
    }

    @Test
    public void findWord_byLength_negativeOne() {
        assertEquals(-1, Parser.findWord(words, "/by", 6));
    }
}
