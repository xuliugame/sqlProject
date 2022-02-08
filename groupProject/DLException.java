public class DLException extends Throwable{
	
	private String message;
	private Exception exception;
	
	public DLException(Exception e){
		this(e,new String[]{});
	}
	
	public DLException(Exception e,String... items){
		super();
		this.exception=e;
		this.message=String.join(", ",items);
	}
	
	public String getMessage(){
		return this.message;
	}
}