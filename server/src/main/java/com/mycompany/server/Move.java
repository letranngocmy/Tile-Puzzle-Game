/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.server;

/**
 *
 * @author letranngocmy
 */
public class Move {
    
    private int number;
    private double x;
    private double y;
    //private boolean shuffle;
    
    public Move(int number, double x, double y) {
        this.number = number;
        this.x = x;
        this.y = y;
//        shuffle = false;
    }
    
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
    
    public int getNumber() {
        return number;
    }
    
//    public boolean getShuffle() {
//        return shuffle;
//    }
//    
//    public void setShuffle(boolean t) {
//        shuffle = t;
//    }
    
}
