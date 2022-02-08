public abstract class BusinessLayerObject {
    protected DataAccessObject dao;

    public abstract boolean canCreate();
    public abstract boolean canRead();
    public abstract boolean canUpdate();
    public abstract boolean canDelete();

    public BusinessLayerObject(DataAccessObject dao){
        this.dao=dao;
    }

    public boolean fetch(){
        if(this.canRead()){
            return this.dao.fetch();
        }
        return false;
    }

    public boolean post(){
        if(this.canCreate()){
            return this.dao.post();
        }
        return false;
    }

    public boolean put(){
        if(this.canUpdate()){
            return this.dao.put();
        }
        return false;
    }

    public boolean delete(){
        if(this.canDelete()){
            return this.dao.delete();
        }
        return false;
    }
}
