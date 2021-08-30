package me.jvegaf.tornabox.models;

public class CellItem {

    private String itemTitle;
    private String iconLiteral;

    public CellItem(String title, String iconLiteral) {
        this.itemTitle = title;
        this.iconLiteral = iconLiteral;
    }

    public String getItemTitle() {
        return this.itemTitle;
    }

    public String getIconLiteral() {
        return this.iconLiteral;
    }
}
