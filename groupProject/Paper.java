public class Paper extends DataAccessObject {

    /**
     *
     * @param conn database connection object to access data in database
     */
    public Paper(DatabaseConnection conn){
        super(conn);
    }

    /**
     *
     * @return name of table containing papers
     */
    @Override
    public String getTable() {
        return "Papers";
    }

    /**
     *
     * @return name of primary key column for the relative table
     */
    @Override
    public String[] getPrimaryKey() {
        return new String[]{"paperId"};
    }

    /**
     *
     * @return String[] of column names for the relative table
     */
    @Override
    public String[] getColumns() {
        return new String[]{"paperId", "title", "abstract", "track", "status", "submissionType", "submitterId", "fileId", "tentativeStatus"};
    }

    //accessors
    public int getPaperId(){
        return Integer.parseInt(this.values.get("paperId"));
    }

    public String getTitle(){
        return this.values.get("title");
    }

    public String getAbstract(){
        return this.values.get("abstract");
    }

    public String getTrack(){
        return this.values.get("track");
    }

    public String getStatus(){
        return this.values.get("status");
    }

    public int getSubmissionType(){
        return Integer.parseInt(this.values.get("submissionType"));
    }

    public int getSubmitterId(){
        return Integer.parseInt(this.values.get("submitterId"));
    }

    public String getFileId(){
        return this.values.get("fileId");
    }

    public String getTentativeStatus(){
        return this.values.get("tentativeStatus");
    }

    // Mutators
    public void setPaperId(int id){
        this.values.put("paperId", String.valueOf(id));
    }

    public void setTitle(String title){
        this.values.put("title", title);
    }

    public void setAbstract(String abstrct){
        this.values.put("abstract", abstrct);
    }

    public void setTrack(String track){
        this.values.put("track", track);
    }

    public void setStatus(String status){
        this.values.put("status", status);
    }

    public void setSubmissionType(int type){
        this.values.put("submissionType", String.valueOf(type));
    }

    public void setSubmitterId(int id){
        this.values.put("submitterId", String.valueOf(id));
    }

    public void setFileId(String fileId){
        this.values.put("fileId", fileId);
    }

    public void setTentativeStatus(String tentativeStatus){
        this.values.put("tentativeStatus", tentativeStatus);
    }
}
