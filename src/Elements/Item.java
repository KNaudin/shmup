/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Elements;

import java.awt.Image;

/**
 *
 * @author Kévin
 * This is the base class for every component displayed in-game
 * It cannot be instanciated
 */
public abstract class Item {
    // On screen coordinates for the item
    private Coordinates _coordinates;
    // Item's spritesheet
    private Image[] _sprites = new Image[5];
    // Current sprite
    private Image _sprite;
    private int _nbrOfSprites = 0;
    // Displayed sprite is affected by current frame
    protected int _currentAnimFrame = 0;
    protected int _movespeed;
    // An inactive item means that it is to be deleted
    protected boolean _isActive = true;
    
    // Sometimes we have to create an empty item
    public Item(){
        
    }
    
    public Item(Coordinates c, Image s){
        this.setCoordinates(c);
        this.addSprite(s);
        this.setSprite(s);
    }
    
    
    public void setCoordinates(Coordinates c){
        this._coordinates = c;
    }
    
    public int getCoordinateX(){
        return this._coordinates.getX();
    }
    
    public int getCoordinateY(){
        return this._coordinates.getY();
    }
    
    public void addSprite(Image i){
        this._sprites[this._nbrOfSprites] = i;
        this._nbrOfSprites++;
    }
    
    public void setSprite(Image i){
        this._sprite = i;
    }
    
    public Image getSprite(){
        return this._sprite;
    }

    public Image[] getSprites() {
        return _sprites;
    }

    public Coordinates getCoordinates() {
        return _coordinates;
    }

    public int getNbrOfSprites() {
        return _nbrOfSprites;
    }

    public int getCurrentAnimFrame() {
        return _currentAnimFrame;
    }

    public int getMovespeed() {
        return _movespeed;
    }

    public boolean isIsActive() {
        return _isActive;
    }

    public void setSprites(Image[] _sprites) {
        this._sprites = _sprites;
    }

    public void setNbrOfSprites(int _nbrOfSprites) {
        this._nbrOfSprites = _nbrOfSprites;
    }

    public void setCurrentAnimFrame(int _currentAnimFrame) {
        this._currentAnimFrame = _currentAnimFrame;
    }

    public void setMovespeed(int _movespeed) {
        this._movespeed = _movespeed;
    }

    public void setIsActive(boolean _isActive) {
        this._isActive = _isActive;
    }
    
    
    // This method is called to animate an item
    public abstract void animate();
    
    // This method is called to move the item
    public abstract void move();
    
    // This method is called when a collision is detected to know what action will be taken
    public abstract void onCollision(Item i);
    
    // This method is called every frame to decide which other method to call in the object
    public abstract void action();
}
