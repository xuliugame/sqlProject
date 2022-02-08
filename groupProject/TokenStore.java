import java.util.HashMap;

public class TokenStore{
	private HashMap<String,String[]> store;
	
	public TokenStore(){
		this.store=new HashMap<>();
	}
	
	public String generate(){
		String token;
		do{
			token="";
			for(int i=0;i<8;i++){
				token+=Math.floor(Math.random()*Math.pow(2,32));
			}
		}while(this.store.containsKey(token));
		
		return token;
	}
	
	public boolean exists(String token){
		return this.store.containsKey(token);
	}
	
	public String post(String[] data){
		String token=this.generate();
		this.store.put(token,data);
		return token;
	}
	
	public String[] get(String token){
		return this.store.get(token);
	}
	
	public void delete(String token){
		this.store.remove(token);
	}
}