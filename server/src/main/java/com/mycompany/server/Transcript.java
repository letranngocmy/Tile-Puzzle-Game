/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author letranngocmy
 */
public class Transcript {
    
    private List<Move> moves = Collections.synchronizedList(new ArrayList<Move>());
    
    public Transcript() {
        
    }
    
    public void addMove(Move m) { moves.add(m); }
    
    public int getSize() { 
//        int count = 0;
//        for (Move m:moves) {
//            if (!m.getShuffle()) {
//                count++;
//            }
//        }
//        return count; 
        return moves.size();
    }
    
    public Move getMove(int n) { return moves.get(n); }
    
    public int shuffle() {
        int j = moves.size();
        Random rnd = new Random();
        for (int i=0; i<48; i++) {
            int row = rnd.nextInt(6);
            int col = rnd.nextInt(8);
            Move m = new Move(i, 100*col, 100*row);
            moves.add(m);
            //m.setShuffle(true); 
        }
        return j;
    }
    
}
