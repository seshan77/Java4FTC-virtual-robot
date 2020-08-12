package net.aspenk12.java4ftc.ps4;

import java.util.ArrayList;
import java.util.List;

public class TestWriter {
    private List<String> lines = new ArrayList<>();

    public void writeLine(String line) {
        lines.add(line);
    }

    public List<String> getLines() {
        return lines;
    }

}
