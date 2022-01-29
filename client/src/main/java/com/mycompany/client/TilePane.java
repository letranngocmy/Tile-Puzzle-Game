package com.mycompany.client;

import java.util.ArrayList;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class TilePane extends Pane {

    private ArrayList<Tile> tiles;
    private double dragStartX;
    private double dragStartY;
    private Gateway gateway;

    public TilePane() {
        tiles = new ArrayList<Tile>();
        Image i = new Image(getClass().getResourceAsStream("/images/picture.jpg"));
        int j = 0;
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 8; col++) {
                int leftX = col * 100;
                int topY = row * 100;
                Tile t = new Tile(leftX, topY,i, j);
                t.setOnMousePressed((evt)->startDrag(evt));
                t.setOnMouseDragged((evt)->continueDrag(evt, t));
                t.setOnMouseReleased((evt)->finishDrag(evt, t));
                tiles.add(t);
                t.setLayoutX(leftX);
                t.setLayoutY(topY);
                this.getChildren().add(t);
                j++;
            }
        }
    }
    
    public void setGateway(Gateway gateway){
        this.gateway=gateway;
    }
    
    private void startDrag(MouseEvent event) {
        dragStartX = event.getSceneX();
        dragStartY = event.getSceneY();
    }
    
    private void continueDrag(MouseEvent event, Tile t) {
        double dragNowX = event.getSceneX();
        double dragNowY = event.getSceneY();
        t.setLayoutX(t.getX() + dragNowX - dragStartX);
        t.setLayoutY(t.getY() + dragNowY - dragStartY);
    }
    
    private void finishDrag(MouseEvent event, Tile t) {
        Point2D dragEnd = this.sceneToLocal(event.getSceneX(),event.getSceneY());
        int X = (int) (dragEnd.getX()/100);
        int Y = (int) (dragEnd.getY()/100);
        t.setX(X*100);
        t.setY(Y*100);
        t.setLayoutX(X*100);
        t.setLayoutY(Y*100);
        Move m = new Move(t.getNumber(), t.getX(), t.getY());
        gateway.moveTile(m);
    }
    
    public void moveTileShuffle(Move m) {
        for (Tile t : tiles) {
            if (t.getNumber() == m.getNumber()) {
                Timeline timeline = new Timeline();
                timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1000),
                    new KeyValue(t.layoutXProperty(), m.getX()),
                    new KeyValue(t.layoutYProperty(), m.getY())));
                timeline.play();
                t.setX((int) m.getX());
                t.setY((int) m.getY());
                t.setLayoutX((int) m.getX());
                t.setLayoutY((int) m.getX());
                break;
            }
        }
    }
    
    public void moveTileDrag(Move m) {
        for (Tile t : tiles) {
            if (t.getNumber() == m.getNumber()) {
                t.setX((int) m.getX());
                t.setY((int) m.getY());
                t.setLayoutX(m.getX());
                t.setLayoutY(m.getY());
                break;
            }
        }
    }
    
    public void scramble() {
        Random rnd = new Random();
        for (Tile t : tiles) {
            int row = rnd.nextInt(6);
            int col = rnd.nextInt(8);
            t.setX(col*100);       
            t.setY(row*100);       
            Timeline timeline = new Timeline();
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1000),
                    new KeyValue(t.layoutXProperty(), 100 * col),
                    new KeyValue(t.layoutYProperty(), 100 * row)));
            timeline.play();
        }
    }
    
    public void reset() {
        for (Tile t : tiles) {
            t.reset();
            Move m = new Move(t.getNumber(), t.getX(), t.getY());
            gateway.moveTile(m);
        }
    }

}
