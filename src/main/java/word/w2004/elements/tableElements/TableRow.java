package word.w2004.elements.tableElements;

import word.api.interfaces.IElement;
import word.api.interfaces.IFluentElementStylable;

public class TableRow implements IElement, IFluentElementStylable<TableRowStyle> {

    private final String tableRowTop = "\n <w:tr>{styleRowPh}";
    private final String tableRowBottom = "\n  </w:tr>";
    private StringBuilder txt = new StringBuilder();
    private TableRowStyle style = new TableRowStyle();

    public TableRow(Object[] cols) {
        txt.append(tableRowTop);
        for (int i = 0; i < cols.length; i++) {
            txt.append(TableCell.with(cols[i]).create().getContent());
        }

        txt.append(tableRowBottom);
    }

    public static TableRow with(Object... colls) {
        return new TableRow(colls);
    }

    @Override
    public TableRowStyle withStyle() {
        style.setElement(this);
        return style;
    }

    @Override
    public String getContent() {
        String withStyle = style.getNewContentWithStyle(txt.toString());
        return withStyle;
    }

}
