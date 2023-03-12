/**
 * This is an exception for when the Database doesn't connect
 */
public class DBConnectException extends Exception{
    public DBConnectException(){
        super();
    }
    public DBConnectException(String msg){
        super(msg);
    }
}