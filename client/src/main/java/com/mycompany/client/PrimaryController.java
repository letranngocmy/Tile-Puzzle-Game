package com.mycompany.client;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

public class PrimaryController implements Initializable {
    
    private Gateway gateway;
    private TilePane tiles;
    
    @FXML
    private VBox vBox;
    
    @FXML
    private void exit() {
        Platform.exit();
    }
    
    @FXML
    private void scramble() {
        gateway.shuffleTiles();
    }
    
    @FXML
    private void reset() {
        tiles.reset();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tiles = new TilePane();
        vBox.getChildren().add(tiles);
        gateway = new Gateway(tiles);
        // Start the transcript check thread
        new Thread(new TranscriptCheck(gateway, tiles)).start();
    }    
}

class TranscriptCheck extends TileConstants implements Runnable {
    private Gateway gateway; // Gateway to the server
    private int N;
    private TilePane tiles;
    
    /** Construct a thread */
    public TranscriptCheck(Gateway gateway, TilePane tiles) {
      this.gateway = gateway;
      this.tiles = tiles;
      this.N = 0;
    }

    /** Run a thread */
    public void run() {
      while(true) {
          if(gateway.countMoves() > N) {
              Move m = gateway.fetchMove(N);
              Platform.runLater(()->tiles.moveTileShuffle(m));
              N++;
          } else {
              try {
                  Thread.sleep(250);
              } catch(InterruptedException ex) {}
          }
      }
    }
  }