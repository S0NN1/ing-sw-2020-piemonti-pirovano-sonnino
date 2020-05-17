package it.polimi.ingsw.client.cli;

public class DisplayCell {
    private final String[] cellRows;

    public DisplayCell(){
        cellRows = new String[11];
    }

    public String getCellRows(int i) {
        return cellRows[i];
    }

    public void setCellRows(int i, String string ) {
        cellRows[i] = string;
    }


}


