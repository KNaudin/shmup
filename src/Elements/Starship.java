/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Elements;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author Kévin
 */
public class Starship extends Item{
    
    protected int _HP;
    
    public Starship(){
        super();
    }
    
    
    
    public Starship(Coordinates c, Image img){
        super(c, img);
    }

    @Override
    public void move() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void animate() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onCollision(Item i) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getHP() {
        return _HP;
    }

    public void setHP(int _HP) {
        this._HP = _HP;
        if(this.getHP() > 100) this.setHP(100);
        if(this.getHP() <= 0){
            this.setIsActive(false);
            this.onDeath();
        }
    }
    
    public void getHit(int dmg){
    }

    @Override
    public void action() {
    }
    
    public void Shoot(){
        
    }
    
    public void onDeath(){
        
    }
}
