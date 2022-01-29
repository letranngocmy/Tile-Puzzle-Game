package com.mycompany.client;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/** Image downloaded from https://www.flickr.com/photos/84220074@N07/50409613346
 *  "Lawrence University" by hongwuphoto is licensed with CC BY-NC-SA 2.0. 
 *  To view a copy of this license, visit 
 *  https://creativecommons.org/licenses/by-nc-sa/2.0/ **/

public class Tile extends Pane {
    private int originalX;
    private int originalY;
    private int currentX;
    private int currentY;
    private int number;
    
    public Tile(int leftX,int topY, Image i, int number) {
        this.originalX = leftX;
        this.originalY = topY;
        currentX = originalX;
        currentY = originalY;
        this.number = number;
        Rectangle r = new Rectangle(0,0,100,100);
        ImagePattern ip = new ImagePattern(i,-originalX,-originalY,800,600,false);
        r.setFill(ip);
        this.getChildren().add(r);
    }
    
    public void setX(int leftX) {
        currentX = leftX;
        this.setLayoutX(leftX);
    }
    
    public void setY(int topY) {
        currentY = topY;
        this.setLayoutY(topY);
    }
    
    public int getX() {
        return currentX;
    }
    
    public int getY() {
        return currentY;
    }
    
    public int getOriginalX() {
        return originalX;
    }
    
    public int getOriginalY() {
        return originalY;
    }
    
    public int getNumber() {
        return number;
    }
    
    public void reset() {
        this.setLayoutX(originalX);
        this.setLayoutY(originalY);
        currentX = originalX;
        currentY = originalY;
    }
    
}
