package cmpt436A3;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Client {

    public Client() {}

    
    public static void main(String[] args) {

        String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            ChatInt stub = (ChatInt) registry.lookup("ChatRooms Service"); // client side connection

            // prompting user to enter username
            Scanner s = new Scanner(System.in);
            System.out.println("Enter username: ");
            String clientname = s.nextLine();

            stub.addClient(clientname);

            Boolean loop = true;
            
            // while loop for the options given to client when they enter the server
            while(loop){

                System.out.println("\n------------------------------------------");
                System.out.println("OPTIONS");
                System.out.println("1 Create Chat Room");
                System.out.println("2 List All Existing Chat Rooms");
                System.out.println("3 Join a Chat Room");
                System.out.println("4 Leave Server");

                System.out.println("\ninput: "); // prompting user to enter input for their choices
                String command = s.nextLine();

                // creating chatroom
                if(command.equals("1")){

                    System.out.println("Enter chatroom name: ");
                    String roomname = s.nextLine();

                   stub.createChatRoom(roomname);
    
                }

                // listing chatroooms that have been created
                if(command.equals("2")){
                    System.out.println("list of chatrooms: ");
                    System.out.println(stub.listChatRooms());
                }

                // entering desired chatroom
                if(command.equals("3")){

                    System.out.println("Enter chatroom name u wish to join: ");
                    String roomname = s.nextLine();

                    stub.joinChatRoom(roomname, clientname);

                    System.out.println(roomname+" is selected");


                    Boolean cloop = true;
                    /**
                     * once entered the chatroom, new loop
                     * loop ends when X is entered
                     * press enter to see update of new emssges or enter a nw message
                     */
                    while(cloop){

                        System.out.println("\n------------------------------------------");
                        System.out.println("Enter X Leave Chat Room");
                        
                        // prints all the messages in the chatroom if there are any
                        if(stub.getAllMessages(roomname) != ""){
                            System.out.println(stub.getAllMessages(roomname));
                        }

                        System.out.println(clientname+": "); // prompt user to send message
                        String message= s.nextLine();

                        // if X then end loop back to server, leave chatroom
                        if(message.equals("X")){ 
                            command = message;
                            cloop = false;
                        }
                        else{
                            stub.postMessage(clientname, message); // add new message to chat message list
                        }
                    }
                
                }

                // leaving chatroom
                if(command.equals("X")){
                    stub.leaveChatRoom(clientname);
                    System.out.println(clientname+" leaving chatroom");
                }

                // leaving server
                if(command.equals("4")){
                    System.out.println("leaving server..");
                    loop = false;

                }
               
            }   
           
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}