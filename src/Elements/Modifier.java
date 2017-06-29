/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Elements;

import Interface.*;
import javax.swing.ImageIcon;

/**
 *
 * @author Kévin
 */
public class Modifier extends Item{

    public Modifier(Coordinates c){
        super(c, Image_Manager.load("health"));
        this.addSprite(Image_Manager.load("health2"));
        _movespeed = 5;
    }
    
    @Override
    public void move() {
        this.setCoordinates(new Coordinates(this.getCoordinateX()-_movespeed, this.getCoordinateY()));
    }

    @Override
    public void animate() {
        if(this.isIsActive()){
            if(this._currentAnimFrame++ >= 25) this._currentAnimFrame=0;
            if(this._currentAnimFrame <= 15){
                this.setSprite(this.getSprites()[0]);
            }
            else{
                this.setSprite(this.getSprites()[1]);
            }
        }
    }

    @Override
    public void onCollision(Item i) {
        if(this.isIsActive() && i.getClass() == Player.class){
            this.setIsActive(false);
            this.setSprite(Image_Manager.load("blank"));
            Audio_Manager boop = new Audio_Manager();
            Display.addtoScore(10);
            boop.playMusic("health", 0.1);
        }
    }

    @Override
    public void action() {
        move();
        animate();
    }
    
    @Override
    public String toString(){
        return "Health pack ";
    }
}
