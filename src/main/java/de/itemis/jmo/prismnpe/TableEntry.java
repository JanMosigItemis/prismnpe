package de.itemis.jmo.prismnpe;



import static java.util.Objects.requireNonNull;

public class TableEntry {

    private final String col1;
    private final String col2;

    public TableEntry(String col1, String col2) {
        this.col1 = requireNonNull(col1, "col1");
        this.col2 = requireNonNull(col2, "col2");
    }

    public String getCol1() {
        return col1;
    }

    public String getCol2() {
        return col2;
    }
}
