package net.aspenk12.java4ftc;

import net.aspenk12.java4ftc.ps4.GridLogger;
import net.aspenk12.java4ftc.ps4.TestWriter;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GridLoggerTest {

    @Test
    public void writeLn() {
        TestWriter writer = new TestWriter();
        GridLogger gridLogger = new GridLogger(writer);
        gridLogger.setColumnHeaders(new String[]{"RobotX", "RobotY"});
        gridLogger.add("RobotX", 2.4);
        gridLogger.add("RobotY", 3.2);
        gridLogger.writeRow();

        List<String> lines = writer.getLines();
        assertEquals("RobotX,RobotY", lines.get(0));
        assertEquals("2.4,3.2", lines.get(1));
    }


}