/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Elements;

import Interface.Image_Manager;

/**
 *
 * @author Kévin
 * Displays decor like debris
 */
public class Decor extends Item{

    public Decor(Coordinates c, int speed){
        super(c, Image_Manager.load("something"));
        _movespeed = speed;
    }
    
    @Override
    public void move() {
        this.setCoordinates(new Coordinates(this.getCoordinateX()-_movespeed, this.getCoordinateY()));
    }

    @Override
    public void animate() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onCollision(Item i) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void action() {
        move();
    }
    
}
