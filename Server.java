package cmpt436A3;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.*;

public class Server implements ChatInt{

    ArrayList<String> clients = new ArrayList<>(); // clients list in server
    ArrayList<String> chatrooms = new ArrayList<>(); // chatroom list
    ArrayList<String> clientmessage;    // client message
    ArrayList<ArrayList<String>> roommessages;  // chatroom messages list according to client
    Hashtable<String, String> clientsinroom;    // hashtable of clients and the rooms they are currently in
    ArrayList<String> clients2gthr; // list of clients in the same chatroom
    
    public Server() {}


    /* 
     * adds client to clients list
     * param: String clietname
     */
    public void addClient(String clientname){
        clients.add(clientname);

    }

    /* 
     * creates chatroom with name given by client, and starts new hashtavle and arraylist
     * for clients in room and room's messages repectively
     * param: String roomname
     */
    public void createChatRoom(String roomname){

        try{
            chatrooms.add(roomname);
            clientsinroom = new Hashtable<String, String>();
            roommessages = new ArrayList<ArrayList<String>>();
            if(chatrooms.contains(roomname)){
                System.out.println(roomname+" is created\n");
            }
        } catch (Exception e){
            System.out.println("unable to create "+ roomname+"\n");
        }
    }


    /*  
     * lists chatrooms created
     */
    public String listChatRooms(){
        String chatroomsList = "";

        for(int r = 0; r < chatrooms.size(); r++)
        {
            chatroomsList = chatrooms.get(r) + ", " + chatroomsList;
        }

        return chatroomsList;
    }

    /* 
     * joins client to specific chatroom
     * param: String roomname, String clietname
     */
    public void joinChatRoom(String roomname, String clientname){

        try{
            if(chatrooms.contains(roomname)){
                clientsinroom.put(clientname, roomname);
                System.out.print(clientname+" joined the chatroom "+roomname+"\n");  
            }
        }
        catch (Exception e){
            System.out.print("unable to join chatroom\n");
        }
    }

    /* 
     * removes client from clientsinroom list when they select this
     * param: String clientname
     */
    public void leaveChatRoom(String clientname){
        try{
            if(clientsinroom.containsKey(clientname)){
                clientsinroom.remove(clientname);
                System.out.println(clientname+" leaving the chatroom\n");
            }
        }
        catch (Exception e){
            System.out.println("unable to leave chatroom\n");
        }
    }
    
    /* 
     * when client enters message, the message is then added to roommessages
     * param: String clientname, String message
     */
    public String postMessage(String clientname, String message){
        try{
            if(clientsinroom.containsKey(clientname)){
                clientmessage = new ArrayList<String>();
                clientmessage.add(clientname);
                clientmessage.add(message);
                roommessages.add(clientmessage);
            }
        }
        catch (Exception e){
            System.out.println("unable to post new message\n");
        }
        
        return message;
    }

    /*
     * prints all messages in that specific chatroom
     * param: String roomname
     */    
    public String getAllMessages(String roomname){
        String allM = "";
        try{
            if(clientsinroom.containsValue(roomname)){
                for(int i = roommessages.size()-1; i >= 0; i--){
                    allM = roommessages.get(i).get(0) + ": "+roommessages.get(i).get(1)+"\n"+allM;       
                }
                  
            }
        }
        catch (Exception e)
        {
            System.out.println("unable to get all messages\n");
            allM = "can't get all messages";
        }
        System.out.println(allM);
        return allM;
    }


    public static void main(String args[]) {
        // creaitng my server and its side of the connection
        try{

            Server obj = new Server();
            ChatInt stub = (ChatInt) UnicastRemoteObject.exportObject(obj, 0);

            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("ChatRooms Service", stub);

            System.err.println("Server ready");

            
        }
        catch (Exception e){
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }



    }
}
