package common;

import org.junit.Test;
import static org.junit.Assert.*;

public class AdderTest {
    @Test public void testSomeLibraryMethod() {
        Adder classUnderTest = new Adder();
        assertTrue("someLibraryMethod should return 'true'", classUnderTest.add(1, 1) == 2);
    }
}