public class UserBL extends BusinessLayerObject {
   private boolean admin;
   private TokenStore tStore;
   private String token = null;
	
	public UserBL(User user){
		this(user, false);
	}

   public UserBL(User user, boolean admin) {
      super(user);
      this.admin = admin;
      this.tStore = new TokenStore();
   }
   
   // if good credentials return token
   public String login(String email, String password) {
      if(this.getEmail().equals(email) && this.getPswd().equals(password)) {
         token = tStore.post(new String[]{email, password});
      }
      
      return token;
   }
   
   // TODO - email user new password set expiration to 5 minutes ahead of current timestamp
   public void resetPassword(String email) {
      System.out.println("Email sent to " + this.getEmail());
   }
   
   // TODO - set user's password
   public void setPassword(String password) {
      tStore.delete(token);
      tStore.post(new String[]{this.getEmail(), password});
   }
   
   @Override
   public boolean canCreate() {
      return this.admin;
   }
   
   @Override
   public boolean canRead() {
      return true;
   }
   
   @Override
   public boolean canUpdate() {
      return this.admin;
   }
   
   @Override
   public boolean canDelete() {
      return this.admin;
   }
   
   // Accessors
   public int getUserId() {
      return ((User)this.dao).getUserId();
   }
   
   public String getLastName() {
      return ((User)this.dao).getLastName();
   }
   
   public String getFirstName() {
      return ((User)this.dao).getFirstName();
   }
   
   public String getEmail() {
      return ((User)this.dao).getEmail();
   }

   public String getPswd() {
      return ((User)this.dao).getPswd();
   }
   
   public int getCanReview() {
      return ((User)this.dao).getCanReview();
   }

   public String getExpiration() {
      return ((User)this.dao).getExpiration();
   }

   public int getIsAdmin() {
      return ((User)this.dao).getIsAdmin();
   }
   
   public int getAffiliationId() {
      return ((User)this.dao).getAffiliationId();
   }

   // Mutators
   public void setUserId(int id){
      ((User)this.dao).setUserId(id);
   }

   public void setLastName(String name){
	   ((User)this.dao).setLastName(name);
   }
   
   public void setFirstName(String name){
      ((User)this.dao).setFirstName(name);
   }
   
   public void setEmail(String email){
      ((User)this.dao).setEmail(email);
   }
   
   public void setPswd(String password){
      ((User)this.dao).setPswd(password);
   }
   
   public void setCanReview(int val){
      ((User)this.dao).setCanReview(val);
   }
   
   public void setExpiration(String date){
      ((User)this.dao).setExpiration(date);
   }
   
   public void setIsAdmin(int val){
      ((User)this.dao).setIsAdmin(val);
   }
   
   public void setAffiliationId(int val){
      ((User)this.dao).setAffiliationId(val);
   }
}