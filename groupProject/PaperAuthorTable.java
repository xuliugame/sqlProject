import java.util.ArrayList;
import java.util.List;

public class PaperAuthorTable extends TableObject<PaperAuthor> {
    /**
     *
     * @param conn database connection for access
     * @param PAs list of PaperAuthor objects to be
     */
    public PaperAuthorTable(DatabaseConnection conn, List<PaperAuthor> PAs){
        super(conn, PAs);
    }

    /**
     *
     * @return name of table containing PaperAuthors
     */
    @Override
    public String getTable() {
        return "paperauthors";
    }

    /**
     *
     * @return String[] of all column names in paperauthors table
     */
    @Override
    public String[] getColumns() {
        return new String[]{"paperId", "userId", "displayOrder"};
    }

    /**
     * Creates PaperAuthor object using returned row data, used in load()
     * @param row The result of a query
     * @return PaperAuthor with values from the row
     */
    @Override
    public PaperAuthor createItem(String[] row) {
        PaperAuthor PA = new PaperAuthor(this.conn);
        PA.setPaperId(Integer.parseInt(row[0]));
        PA.setUserId(Integer.parseInt(row[1]));
        PA.setDisplayOrder(Integer.parseInt(row[2]));
        return PA;
    }
}
