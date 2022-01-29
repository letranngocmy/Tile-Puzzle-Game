/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javafx.application.Platform;

/**
 *
 * @author letranngocmy
 */
public class Gateway extends TileConstants {

    private PrintWriter outputToServer;
    private BufferedReader inputFromServer;
    private TilePane pane;
    
    // Establish the connection to the server.
    public Gateway(TilePane pane) {
        this.pane = pane;
        pane.setGateway(this);
        try {
            // Create a socket to connect to the server
            Socket socket = new Socket("localhost", 8000);

            // Create an output stream to send data to the server
            outputToServer = new PrintWriter(socket.getOutputStream());

            // Create an input stream to read data from the server
            inputFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (IOException ex) {
            Platform.runLater(() -> System.out.println("Exception in gateway constructor: " + ex.toString() + "\n"));
        }
    }

    public synchronized void shuffleTiles() {
        outputToServer.println(SHUFFLE_TILES);
        outputToServer.flush();
        try {
            for (int i=0; i<48; i++) {
                int number = Integer.parseInt(inputFromServer.readLine());
                double x = Double.parseDouble(inputFromServer.readLine());
                double y = Double.parseDouble(inputFromServer.readLine());
                Move m = new Move(number, x, y);
                pane.moveTileShuffle(m);
            }
        } catch (IOException ex) {
            Platform.runLater(() -> System.out.println("Error in getCommentCount: " + ex.toString() + "\n"));
        }
    }

    public synchronized void moveTile(Move m) {
        outputToServer.println(MOVE_TILE);
        outputToServer.println(m.getNumber());
        outputToServer.println(m.getX());
        outputToServer.println(m.getY());
        outputToServer.flush();
    }

    public synchronized int countMoves() {
        outputToServer.println(MOVE_COUNT);
        outputToServer.flush();
        int count = 0;
        try {
            count = Integer.parseInt(inputFromServer.readLine());
        } catch (IOException ex) {
            Platform.runLater(() -> System.out.println("Error in getCommentCount: " + ex.toString() + "\n"));
        }
        return count;
    }

    public synchronized Move fetchMove(int n) {
        outputToServer.println(FETCH_MOVE);
        outputToServer.println(n);
        outputToServer.flush();
        try {
            int number = Integer.parseInt(inputFromServer.readLine());
            double x = Double.parseDouble(inputFromServer.readLine());
            double y = Double.parseDouble(inputFromServer.readLine());
            Move m = new Move(number, x, y);
            return m;
        } catch (IOException ex) {
            Platform.runLater(() -> System.out.println("Error in getComment: " + ex.toString() + "\n"));
        }
        return null;
    }
    
}

