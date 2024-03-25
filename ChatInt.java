package cmpt436A3;

import java.lang.reflect.Array;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.*;

/**
 * my chatroom interface
 */
public interface ChatInt extends Remote{

    // add client
    void addClient(String clientname) throws RemoteException;

    // create chatroom
    void createChatRoom(String roomname) throws RemoteException;

    // list chatrooms
    String listChatRooms() throws RemoteException;

    // join chatroom
    void joinChatRoom(String roomname, String clientname) throws RemoteException;

    //leave chatroom
    void leaveChatRoom(String clientname) throws RemoteException;

    // adds new message to the chatrooms chat array
    String postMessage(String clientname, String message) throws RemoteException;

    // gets all messages
    String getAllMessages(String roomname) throws RemoteException;

}
