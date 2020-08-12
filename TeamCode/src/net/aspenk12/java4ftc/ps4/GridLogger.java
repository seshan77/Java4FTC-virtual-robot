package net.aspenk12.java4ftc.ps4;

public class GridLogger {
    private TestWriter writer;

    public GridLogger(TestWriter writer) {
        this.writer = writer;
    }

    /**
     * Define grid column header names
     * @param columns
     */
    public void setColumnHeaders(String[] columns) {
    }

    /**
     * Add a value to the logger under the specified column
     *
     * @param column
     * @param value
     */
    public void add(String column, double value) {
    }

    /**
     * Write a line of data to the log.  If this is the first call to writeRow, a row of comma-separated
     * column names are written first.  A row of comma-separated data values that were added with the add()
     * method is written next.  Once the data row is written, the logger is reset
     * and calls to add() will add values to the next line of data.
     */
    public void writeRow() {
        writer.writeLine("something");
    }

    public void stop() {
    }

}
