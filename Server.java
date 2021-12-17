import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;


public class Server{
    private ServerSocket serverSocket;
    public Server(ServerSocket serverSocket){
        this.serverSocket=serverSocket;
    }
    public void startServer(){ //to make connection to client via socket
        new Thread(new Runnable() {
            @Override
            public void run(){//this does the running in the other thread thingy
                try {
                    while (!serverSocket.isClosed()) {
                        Socket socket = serverSocket.accept();
                        System.out.println("a client is connected");
                        ClientHandler clientHandler = new ClientHandler(socket); //handling client

                        Thread thread = new Thread(clientHandler); //running client on multiple thread
                        thread.start();//starting the thread
                    }
                }catch(
                        IOException e)

                {
                    e.printStackTrace();
                }
            }
        }).start();//object created and then start called

    }
    public void closeServerSocket(){
        try{
            if(serverSocket!=null){
                serverSocket.close();//to close the connection
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws UnknownHostException, IOException {//using console
        ServerSocket serverSocket=new ServerSocket(8080);
        Server server=new Server(serverSocket);
        server.startServer();//to start the server when compiled
        Socket socket=new Socket("localhost", 8080);//idk how this work, i just choose one random port in my pc
        Client client=new Client(socket, "Admin");//create client
        client.listenForMessage();//needs separate thread because either way it waits for "while" to get done
        client.sendMessage();
    }
}
