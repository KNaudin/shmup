/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Elements;

import Interface.Audio_Manager;
import Interface.Display;
import Interface.Image_Manager;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.Timer;

/**
 *
 * @author Kévin
 */
public class Player extends Starship implements ActionListener{
    
    private boolean _canShoot = true;
    private boolean _canBeHit = true;
    private Timer _Timer;
    private int _shootCounter;
    private int _hitCounter;
    private int _currentSprite = 0;
    private Audio_Manager _soundM;
    
    public Player(){
        super();
        this.setHP(50);
        this.addSprite(Image_Manager.load("xwing_hit"));
        _Timer = new Timer(33, this);
        _Timer.start();
        _soundM = new Audio_Manager();
    }
    
    @Override
    public void onCollision(Item i){
        if(i.isIsActive())
        {
            if(i.getClass() == Modifier.class){
                this.setHP(this.getHP()+10);
            }
        }
    }
    
    @Override
    public void Shoot(){
        if(_canShoot){
            _soundM.playMusic("fire", 1);
            _canShoot = false;
            Display.addDrawable(new Bullet(new Coordinates(this.getCoordinateX()+52, this.getCoordinateY()+1), new ImageIcon(this.getClass().getResource("/Images/player_beam.png")).getImage() ,true));
            Display.addDrawable(new Bullet(new Coordinates(this.getCoordinateX()+52, this.getCoordinateY()+100), new ImageIcon(this.getClass().getResource("/Images/player_beam.png")).getImage() ,true));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Our player needs to wait between every shot else he will shoot at every possible frame
        if(!_canShoot){
            _shootCounter++;
            if(_shootCounter >= 5){
                _shootCounter = 0;
                _canShoot = true;
            }
        }
        // Player can't be hit at each frame else we'd die too fast
        if(!_canBeHit){
            _hitCounter++;
            if(_hitCounter >= 15){
                _hitCounter = 0;
                _canBeHit = true;
            }
        }
    }
    
    @Override
    public void getHit(int dmg){
        if(_canBeHit){
            _canBeHit = false;
            this.setHP(this.getHP()-dmg);
        }
    }
    
    @Override
    public void animate(){
        if(!this._canBeHit){
            if(_currentSprite++ > 15) _currentSprite = 0;
            if(_currentSprite %2 == 0){
                this.setSprite(this.getSprites()[0]);
            }
            else{
                this.setSprite(this.getSprites()[1]);
            }
        }else{
            this.setSprite(this.getSprites()[1]);
        }
    }
    
    @Override
    public String toString(){
        return "Joueur ";
    }
}
