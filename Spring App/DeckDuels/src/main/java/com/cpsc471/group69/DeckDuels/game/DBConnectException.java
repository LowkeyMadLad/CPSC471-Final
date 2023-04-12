/**
 * This is an exception for when the Database doesn't connect
 */
package com.cpsc471.group69.DeckDuels.game;
public class DBConnectException extends Exception{
    public DBConnectException(){
        super();
    }
    public DBConnectException(String msg){
        super(msg);
    }
}