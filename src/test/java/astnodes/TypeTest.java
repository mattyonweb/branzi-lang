package astnodes;

import ASTnodes.Type;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ASTnodes.Type.*;

public class TypeTest {

    @Test
    public void testTypeEquality() {
        assertTrue(equality(INT, INT));
        assertTrue(equality(ANY, ANY));
        assertTrue(equality(List(INT), List(INT)));
        assertTrue(equality(List(List(INT)), List(List(INT))));
    }

    @Test
    public void testTypeSubtype() {
        assertTrue(subtype(INT, INT));
        assertTrue(subtype(ANY, ANY));
        assertTrue(subtype(List(INT), List(INT)));
        assertTrue(subtype(List(List(INT)), List(List(INT))));

        assertTrue(subtype(INT, ANY));
        assertTrue(subtype(List(INT), ANY));
        assertFalse(subtype(ANY, INT));
        assertFalse(subtype(ANY, List(ANY)));

        for (Type x: List.of(INT, BOOL, List(INT), List(List(INT)))) {
            assertTrue(subtype(TBD, x));
            assertTrue(subtype(x, ANY));

            assertFalse(subtype(ANY, x));
            assertFalse(subtype(x, TBD));
        }


    }
}