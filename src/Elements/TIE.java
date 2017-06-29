/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Elements;

import Interface.Audio_Manager;
import Interface.Display;
import Interface.Image_Manager;
import Interface.Image_Manager;
import javax.swing.ImageIcon;

/**
 *
 * @author Kévin
 */
public class TIE extends Starship{
    
    private int _frame = 0;
    private int _deathFrame = 0;
    private boolean _soundPlayed = false;
    private double _rotate;
    private Audio_Manager _soundM;
    
    public TIE(Coordinates c){
        super(c, Image_Manager.load("TIE"));
        this.setMovespeed(10);
        this.setHP(2);
        this.addSprite(Image_Manager.load("TIE_HIT"));
        _rotate = Math.toRadians(Math.random()*360);
        this.addSprite(new Image_Manager().rotateResizeImage(Image_Manager.toBufferedImage(Image_Manager.load("explosion1")), _rotate));
        _rotate = Math.toRadians(Math.random()*360);
        this.addSprite(new Image_Manager().rotateResizeImage(Image_Manager.toBufferedImage(Image_Manager.load("explosion2")), _rotate));
        this.addSprite(Image_Manager.load("blank"));
        _soundM = new Audio_Manager();
    }
    
    @Override
    public void getHit(int dmg){
        if(this.isIsActive()){
            this.setSprite(this.getSprites()[1]);
            this.setHP(this.getHP()-dmg);
            Display.addtoScore(100);
        }
    }
    
    @Override
    public void move(){
        if(!_soundPlayed) {
            _soundPlayed = true;
            _soundM.playMusic("tie_spawn", 0.1);
        }
        this.setCoordinates(new Coordinates(this.getCoordinateX()-_movespeed, this.getCoordinateY()));
    }
    
    @Override
    public void Shoot(){
        _soundM.playMusic("fire", 1);
        Display.addDrawable(new Bullet(new Coordinates(this.getCoordinateX(), this.getCoordinateY()+40), new ImageIcon(this.getClass().getResource("/Images/ennemy_beam.png")).getImage() ,false));
    }
    
    @Override
    public void action(){
        this.move();
        if(this.isIsActive())
        {
            _frame++;
            if(_frame % 10 == 0) this.Shoot();
        }
        this.animate();
    }
    
    @Override
    public void animate(){
        if(this.isIsActive()){
            this.setSprite(this.getSprites()[0]);
        }else{
            _deathFrame++;
            if(_deathFrame <= 3){
                this.setSprite(this.getSprites()[2]);
            }else if(_deathFrame <= 10){
                this.setSprite(this.getSprites()[3]);
            }else{
                this.setSprite(this.getSprites()[4]);
            }
        }
    }
    
    @Override
    public void onDeath(){
        int rdm = (int) (Math.random() * 10);
        if(rdm >= 8) Display.addDrawable(new Modifier(new Coordinates(this.getCoordinateX(), this.getCoordinateY())));
        _soundM.playMusic("destroy", 1);
    }
    
    @Override
    public String toString(){
        return "TIE ";
    }
}
