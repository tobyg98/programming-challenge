package au.com.workingmouse.challenge.services;

import au.com.workingmouse.challenge.models.VelocityAndDirectionData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.sql.Timestamp;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class VelocityAndDirectionServiceTest {
    private static final String VALID_LINE = "2019-02-15 10:40:00,94780,420,720,15.36746,208.9371,-13.4357,-7.070466,"
        + "223.5234,13.6896,-6.570364,5.081869,-39.14706,100,15.29624,16.54015,0.9127843";

    private static final String INVALID_LINE = "12/01/1995,94780,dog,720,15.36746,208.9371,-13.4357,-7.070466,"
            + "223.5234,13.6896,-6.570364,5.081869,-39.14706,100,15.29624,16.54015,0.9127843";

    private static final String INVALID_LINE_TWO = "2019-02-15 10:40:00,dog,720,15.36746,208.9371,-13.4357,-7.070466,"
            + "223.5234,13.6896,-6.570364,5.081869,-39.14706,100,15.29624,16.54015,0.9127843";

    private VelocityAndDirectionData velocityAndDirectionData;

    @BeforeEach
    void setUp() {
        velocityAndDirectionData = new VelocityAndDirectionData(
                Timestamp.valueOf("2019-02-15 10:40:00"),
                94780,
                420,
                720,
                15.36746,
                208.9371,
                -13.4357,
                -7.070466,
                223.5234,
                13.6896,
                -6.570364,
                5.081869,
                -39.14706,
                100.0,
                15.29624,
                16.54015,
                0.9127843
        );
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void parseValidLineTest() {
        VelocityAndDirectionData actualVelocityAndDirectionData = VelocityAndDirectionService.parseLine(VALID_LINE);
        assertEquals(velocityAndDirectionData, actualVelocityAndDirectionData);
    }

    @Test
    void parseInvalidLineTestTimeStamp() {
       Executable parseLineTest = () -> VelocityAndDirectionService.parseLine(INVALID_LINE);
        assertThrows(IllegalArgumentException.class, parseLineTest, "Invalid Timestamp");
    }


    @Test
    void parseInvalidLineTest() {
        Executable parseLineTest = () -> VelocityAndDirectionService.parseLine(INVALID_LINE_TWO);
        assertThrows(NumberFormatException.class, parseLineTest, "Dog is not a double");
    }

    @Test
    void parseMultipleLinesTest() {
       var lines = VelocityAndDirectionService.parseLines(Arrays.asList(
                VALID_LINE,
                VALID_LINE
        ));

       assertEquals(2, lines.size());
       assertTrue(lines.get(0).equals(lines.get(1)));
    }

    @Test
    void summeriseTest() {
        fail("Test not implemented");
    }
}