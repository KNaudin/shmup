/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Elements;

/**
 *
 * @author Kévin
 */
public class Coordinates {
    
    private int _x;
    private int _y;
    
    public Coordinates(int x,int y){
        this.setX(x);
        this.setY(y);
    }
    
    public void setX(int x){
        this._x = x;
    }
    
    public void setY(int y){
        this._y = y;
    }
    
    public int getX(){
        return this._x;
    }
    
    public int getY(){
        return this._y;
    }
    
    public String toString(){
        return "Coordinates are "+this.getX()+" "+this.getY();
    }
}
