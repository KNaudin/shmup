/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Elements;

import Interface.Image_Manager;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author Kévin
 */
public class Bullet extends Item{

    private boolean _fromPlayer;
    private String _bulletType = "normal";
    private int _bulletSpeed;
    
    public Bullet(Coordinates c, Image i, boolean isFromPlayer){
        super(c, i);
        this.setFromPlayer(isFromPlayer);
        // Player bullets are faster than ennemy bullet
        if(this.isFromPlayer()) this.setBulletSpeed(20);
        else { this.setBulletSpeed(-15); }
    }
    
    @Override
    public void animate() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void move() {
        this.setCoordinates(new Coordinates(this.getCoordinateX()+this.getBulletSpeed(), this.getCoordinateY()));
    }

    @Override
    public void onCollision(Item i) {
        if(i.getClass() == TIE.class && this.isFromPlayer() && this.isIsActive()){
            TIE t = (TIE) i;
            t.getHit(1);
            this.setIsActive(false);
            this.setSprite(Image_Manager.load("blank"));
        }
        if(i.getClass() == Player.class && !this.isFromPlayer() && this.isIsActive()){
            Player p = (Player) i;
            p.getHit(1);
            this.setIsActive(false);
            this.setSprite(Image_Manager.load("blank"));
        }
    }

    public boolean isFromPlayer() {
        return _fromPlayer;
    }

    public void setFromPlayer(boolean _fromPlayer) {
        this._fromPlayer = _fromPlayer;
    }

    public String getBulletType() {
        return _bulletType;
    }

    public void setBulletType(String _bulletType) {
        this._bulletType = _bulletType;
    }

    public int getBulletSpeed() {
        return _bulletSpeed;
    }

    public void setBulletSpeed(int _bulletSpeed) {
        this._bulletSpeed = _bulletSpeed;
    }

    @Override
    public void action() {
        move();
    }
    
    @Override
    public String toString(){
        return "Balle ";
    }
}
