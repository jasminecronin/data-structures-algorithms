public class MyString {
  
  String str;
  
  public MyString() {
    str = "";
  }
  
  public MyString( String in ) {
    str = new String( in );
  }
  
  @Override 
  public int hashCode() {
    int hashVal = 0;
    for( int i = 0; i < str.length(); i++ )
      hashVal += str.charAt( i );
    
    return hashVal;
  }
  
}
