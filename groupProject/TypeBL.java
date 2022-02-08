public class TypeBL extends BusinessLayerObject{
	private boolean admin;
	
	public TypeBL(Type type){
		this(type,false);
	}
	
	public TypeBL(Type type,boolean admin){
		super(type);
		this.admin=admin;
	}
	
	@Override
	public boolean canRead(){
		return true;
	}
	
	@Override
	public boolean canCreate(){
		return this.admin;
	}
	
	@Override
	public boolean canUpdate(){
		return this.admin;
	}
	
	@Override
	public boolean canDelete(){
		return this.admin;
	}
	
	public int getTypeId(){
		return ((Type)this.dao).getTypeId();
	}
	
	public void setTypeId(int id){
		((Type)this.dao).setTypeId(id);
	}
	
	public String getTypeName(){
		return ((Type)this.dao).getTypeName();
	}
	
	public void setTypeName(String name){
		((Type)this.dao).setTypeName(name);
	}
}