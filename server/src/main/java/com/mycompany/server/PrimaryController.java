package com.mycompany.server;

import static com.mycompany.server.TileConstants.SHUFFLE_TILES;
import static com.mycompany.server.TileConstants.MOVE_TILE;
import static com.mycompany.server.TileConstants.MOVE_COUNT;
import static com.mycompany.server.TileConstants.FETCH_MOVE;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

public class PrimaryController implements Initializable {

    @FXML
    private TextArea textArea;

    private int clientNo = 0;
    private Transcript transcript;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        transcript = new Transcript();

        new Thread(() -> {
            try {
                // Create a server socket
                ServerSocket serverSocket = new ServerSocket(8000);

                while (true) {
                    // Listen for a new connection request
                    Socket socket = serverSocket.accept();

                    // Increment clientNo
                    clientNo++;

                    Platform.runLater(() -> {
                        // Display the client number
                        textArea.appendText("Starting thread for client " + clientNo
                                + " at " + new Date() + '\n');
                    });

                    // Create and start a new thread for the connection
                    new Thread(new HandleAClient(socket, transcript, textArea)).start();
                }
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }).start();
    }

}

class HandleAClient implements Runnable {

    private Socket socket; // A connected socket
    private Transcript transcript; // Reference to shared transcript
    private TextArea textArea;

    public HandleAClient(Socket socket, Transcript transcript, TextArea textArea) {
        this.socket = socket;
        this.transcript = transcript;
        this.textArea = textArea;
    }

    public void run() {
        try {
            // Create reading and writing streams
            BufferedReader inputFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter outputToClient = new PrintWriter(socket.getOutputStream());

            // Continuously serve the client
            while (true) {
                // Receive request code from the client
                int request = Integer.parseInt(inputFromClient.readLine());
                // Process request
                switch (request) {
                    case SHUFFLE_TILES: {
                        int j = transcript.shuffle();
                        for (int i = j; i < 48 + j; i++) {
                            Move m = transcript.getMove(i);
                            outputToClient.println(m.getNumber());
                            outputToClient.println(m.getX());
                            outputToClient.println(m.getY());
                        }
                        outputToClient.flush();
                        break;
                    }
                    case MOVE_TILE: {
                        int number = Integer.parseInt(inputFromClient.readLine());
                        double x = Double.parseDouble(inputFromClient.readLine());
                        double y = Double.parseDouble(inputFromClient.readLine());
                        Move m = new Move(number, x, y);
                        transcript.addMove(m);
                        break;
                    }
                    case MOVE_COUNT: {
                        outputToClient.println(transcript.getSize());
                        outputToClient.flush();
                        break;
                    }
                    case FETCH_MOVE: {
                        int n = Integer.parseInt(inputFromClient.readLine());
                        Move m = transcript.getMove(n);
                        outputToClient.println(m.getNumber());
                        outputToClient.println(m.getX());
                        outputToClient.println(m.getY());
                        outputToClient.flush();
                        break;
                    }
                }
            }
        } catch (IOException ex) {
            Platform.runLater(() -> textArea.appendText("Exception in client thread: " + ex.toString() + "\n"));
        }
    }
}
