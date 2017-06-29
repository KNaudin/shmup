/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Levels.Level_Manager;
import java.awt.Dimension;
import javax.swing.JPanel;


import Elements.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.ListIterator;
/**
 *
 * @author Kévin
 */
public class Display extends JPanel implements ActionListener{
    
    // Array to keep track of held keys
    private boolean[] keyHeld = new boolean[256];
    
    // Window dimensions
    private final int _Window_W = 800;
    private final int _Window_H = 700;
    
    // Stocks the player's score
    private static int _score = 0;
    
    // Stocks the background position
    private int _backgroundPaintPos = 0;
    private int _parallaxPaintPos = 0;
    private int _endorPaintPos = 400;
    
    // Game Managers
    private Level_Manager _L_Manager;
    private Audio_Manager _A_Manager;
    
    // The player is part of the display and controlled by a Class
    private Player _player;
    private Image _background;
    private Image _parallaxBg;
    private Image _endor;
    
    // Items that will be draw on screen
    /*
        This should be left accessible by every class so they're able to
        spawn sub-items
    */
    private static ArrayList<Item> _drawables;
    private static ArrayList<Item> _toAddNextFrame;
    
    public Display()
    {
        // Initialize our JPanel attributes
        init();
        
        // Adds a key listner to our JPanel so we can interact with it
        addKeyListener(new KAdapter());
        
        this.setPreferredSize(new Dimension(_Window_W, _Window_H));
        setFocusable(true);
    }
    
    public void init(){
        _drawables = new ArrayList<Item>();
        _toAddNextFrame = new ArrayList<Item>();
        _L_Manager = new Level_Manager(this);
        
        // Creates the player component and define its starting position
        _player = new Player();
        this.getPlayer().setCoordinates(_L_Manager.getPlayerStartPos());
        
        // Loads the player sprite and the background
        loadImages();
        
        // Plays music
        _A_Manager = new Audio_Manager();
        this.getA_Manager().playMusic(this.getL_Manager().getMusic(), 0.1);
    }
    
    public static void addtoScore(int points){
        _score += points;
    }
    
    public void loadImages(){
        System.out.println("Loaded /Images/"+this.getL_Manager().getPlayerStartShip()+".png");
        // We get the image stocked inside the buffer and set it as a sprite for the player
        this.getPlayer().addSprite(Image_Manager.load(this.getL_Manager().getPlayerStartShip()));
        this.getPlayer().setSprite(this.getPlayer().getSprites()[1]);
        
        System.out.println("Loaded /Images/"+this.getL_Manager().getBackground_img()+".png");
        this._background = Image_Manager.load(this.getL_Manager().getBackground_img());
        
        System.out.println("Loaded /Images/Stars.png");
        this._parallaxBg = Image_Manager.load("Stars");
        
        System.out.println("Loaded /Images/endor.png");
        this._endor = Image_Manager.load("endor");
    }
    
    // This method is called automatically on new events occuring inside the JPanel
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        // Renders the gameover screen if the player died
        // else renders the game normally
        if(this.getPlayer().isIsActive()) draw(g);
        else{ endScreen(g); }
    }
    
    // Method that draws the backgrounds, all items in _drawables and then the player
    public void draw(Graphics g){
        g.drawImage(_parallaxBg, this.getParallaxPaintPos(), 0, this);
        g.drawImage(_endor, this.getEndorPaintPos(), 0, this);
        g.drawImage(_background, this.getBackgroundPaintPos(), 200, this);
        
        _drawables.forEach((i) -> {
            g.drawImage(i.getSprite(), i.getCoordinateX(), i.getCoordinateY(), this);
        });
        
        g.drawImage(this.getPlayer().getSprite(), this.getPlayer().getCoordinateX(), this.getPlayer().getCoordinateY(), this);
        
        // Renders the player's life and score at the bottom of the screen
        g.setColor(Color.BLACK);
        g.fillRect(0, 600, _Window_W, 100);
        g.setColor(Color.RED);
        g.fillRect(100, 670, this.getPlayer().getHP()*6, 20);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Helvetica", Font.BOLD, 14));
        g.drawString("X-Wing", 40, 685);
        g.drawString(String.valueOf(this.getPlayer().getHP())+"/100", this.getPlayer().getHP()*6+110, 685);
        g.drawString("Score :   "+_score, 40, 660);
        g.drawString("Stage "+this.getL_Manager().getLvlNbr(), 40, 635);
    }
    
    public void endScreen(Graphics g){
        this.setBackground(Color.BLACK);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Helvetica", Font.BOLD, 75));
        g.drawString("GAME OVER", 170, 300);
        g.setFont(new Font("Helvetica", Font.BOLD, 20));
        g.drawString("Final Score : "+_score, 325, 350);
    }
    
    // Adds an item to be drawn
    public static void addDrawable(Item i){
       _toAddNextFrame.add(i);
    }
    
    /*
        Method that is used to check which keys are pressed so we can move the player,
        change the audio, replace the player inbound, remove out of bounds items
        and then ask to re-draw the display
    */
    public void action(){
        
        // If we're pressing Q or LEFT the player goes LEFT
        if(keyHeld[37] == true || keyHeld[81] == true){
            this.getPlayer().setCoordinates(new Coordinates(this.getPlayer().getCoordinateX()-10, this.getPlayer().getCoordinateY()));
        }
        
        // If we're pressing Z or UP the player goes UP
        if(keyHeld[38] == true || keyHeld[90] == true){
            this.getPlayer().setCoordinates(new Coordinates(this.getPlayer().getCoordinateX(), this.getPlayer().getCoordinateY()-10));
        }
        
        // If we're pressing D or RIGHT the player goes RIGHT
        if(keyHeld[39] == true || keyHeld[68] == true){
            this.getPlayer().setCoordinates(new Coordinates(this.getPlayer().getCoordinateX()+10, this.getPlayer().getCoordinateY()));
        }
        
        // If we're pressing S or DOWN the player goes DOWN
        if(keyHeld[40] == true || keyHeld[83] == true){
            this.getPlayer().setCoordinates(new Coordinates(this.getPlayer().getCoordinateX(), this.getPlayer().getCoordinateY()+10));
        }
        
        // If we're pressing I we increase the music's volume
        if(keyHeld[73] == true){
            this.getA_Manager().increaseVolume();
        }
        
        // If we're pressing L we lower the music's volume
        if(keyHeld[76] == true){
            this.getA_Manager().lowerVolume();
        }
        
        // If we're pressing SPACEBAR or M we shoot
        if(keyHeld[32] == true || keyHeld[77] == true){
            this.getPlayer().Shoot();
        }
        
        checkScreenPos();
        
        moveItems();
        
        checkCollision();
        
        _drawables.addAll(_toAddNextFrame);
        
        // Send an event to the JPanel, automatically calling for the paintComponent() method
        repaint();
    }
    
    // Check if the player or any Item are out of bound
    public void checkScreenPos(){
        // Replace the player inside the screen if he tries to get out of bounds
        if(this.getPlayer().getCoordinateY() < 0){
            this.getPlayer().setCoordinates(new Coordinates(this.getPlayer().getCoordinateX(), this.getPlayer().getCoordinateY()+10));
        }
        if(this.getPlayer().getCoordinateY()+this.getPlayer().getSprite().getHeight(this) > _Window_H-100){
            this.getPlayer().setCoordinates(new Coordinates(this.getPlayer().getCoordinateX(), this.getPlayer().getCoordinateY()-10));
        }
        if(this.getPlayer().getCoordinateX() < 0){
            this.getPlayer().setCoordinates(new Coordinates(this.getPlayer().getCoordinateX()+10, this.getPlayer().getCoordinateY()));
        }
        if(this.getPlayer().getCoordinateX()+this.getPlayer().getSprite().getWidth(this) > _Window_W){
            this.getPlayer().setCoordinates(new Coordinates(this.getPlayer().getCoordinateX()-10, this.getPlayer().getCoordinateY()));
        }
    }

    /*
        Method that goes over all the _drawables items and moves them isnide our screen
        Also moves our parallaxes backgrounds
    */
    public void moveItems(){
        
        this.setBackgroundPaintPos(this.getBackgroundPaintPos()-30);
        if(this.getBackgroundPaintPos() <= -800) this.setBackgroundPaintPos(0);
        
        this.setParallaxPaintPos(this.getParallaxPaintPos()-5);
        if(this.getParallaxPaintPos() <= -800) this.setParallaxPaintPos(0);
        
        if(this.getParallaxPaintPos() % 35 == 0) this.setEndorPaintPos(this.getEndorPaintPos()-1);
        
        // Remove all Items that are out of bounds
        for(ListIterator<Item> item_it = _drawables.listIterator(); item_it.hasNext();){
            Item i = item_it.next();
            i.action();
            if(i.getCoordinateX() < 0-i.getSprite().getWidth(this) || i.getCoordinateY() > 600)
            {
                item_it.remove();
            }
        }
        this.getPlayer().animate();
    }
    
    // Method called by the timer set in the level manager
    @Override
    public void actionPerformed(ActionEvent e) {
        if(this.getPlayer().isIsActive())
        {
            action();
            _toAddNextFrame.clear();
        }else{
            this.getA_Manager().stopMusic();
        }
    }
    
    /*
        Method that checks if objetcs are colliding
    */
    public void checkCollision(){
        _drawables.stream().map((i1) -> {
            if(areColliding(this.getPlayer(), i1)){
                this.getPlayer().onCollision(i1);
                i1.onCollision(this.getPlayer());
            }
            return i1;
        }).forEachOrdered((i1) -> {
            for(Item i2 : _drawables){
                if(areColliding(i1, i2)){
                    i1.onCollision(i2);
                    i2.onCollision(i1);
                }
            }
        });
    }
    
    /*
        Method that tells if objects are colliding.
        The difference is that this method returns a boolean if the objects are collidning but
        does not take action.
    */
    public boolean areColliding(Item i1, Item i2){
        boolean areColliding = true;
        if( (i2.getCoordinateX() >= i1.getCoordinateX()+i1.getSprite().getWidth(this) )
            || ( i2.getCoordinateX() + i2.getSprite().getWidth(this) <= i1.getCoordinateX() )
            || ( i2.getCoordinateY() >= i1.getCoordinateY() + i1.getSprite().getHeight(this) ) 
            || ( i2.getCoordinateY() + i2.getSprite().getHeight(this) <= i1.getCoordinateY() ) )
            areColliding = false;
        return areColliding;
    }
    
    public boolean[] getKeyHeld() {
        return keyHeld;
    }

    public void setKeyHeld(boolean[] keyHeld) {
        this.keyHeld = keyHeld;
    }

    public static int getScore() {
        return _score;
    }

    public Level_Manager getL_Manager() {
        return _L_Manager;
    }

    public void setL_Manager(Level_Manager _L_Manager) {
        this._L_Manager = _L_Manager;
    }

    public Audio_Manager getA_Manager() {
        return _A_Manager;
    }

    public void setA_Manager(Audio_Manager _A_Manager) {
        this._A_Manager = _A_Manager;
    }

    public Player getPlayer() {
        return _player;
    }

    public void setPlayer(Player _player) {
        this._player = _player;
    }

    public void setBackground(Image _background) {
        this._background = _background;
    }

    public static ArrayList<Item> getDrawables() {
        return _drawables;
    }

    public static void setDrawables(ArrayList<Item> _drawables) {
        Display._drawables = _drawables;
    }

    public static ArrayList<Item> getToAddNextFrame() {
        return _toAddNextFrame;
    }

    public static void setToAddNextFrame(ArrayList<Item> _toAddNextFrame) {
        Display._toAddNextFrame = _toAddNextFrame;
    }

    public int getBackgroundPaintPos() {
        return _backgroundPaintPos;
    }

    public void setBackgroundPaintPos(int _backgroundPaintPos) {
        this._backgroundPaintPos = _backgroundPaintPos;
    }

    public int getParallaxPaintPos() {
        return _parallaxPaintPos;
    }

    public void setParallaxPaintPos(int _parallaxPaintPos) {
        this._parallaxPaintPos = _parallaxPaintPos;
    }

    public Image getParallaxBg() {
        return _parallaxBg;
    }

    public void setParallaxBg(Image _parallaxBg) {
        this._parallaxBg = _parallaxBg;
    }

    public int getEndorPaintPos() {
        return _endorPaintPos;
    }

    public void setEndorPaintPos(int _endorPaintPos) {
        this._endorPaintPos = _endorPaintPos;
    }
    
    /*
        Private class that enables key listening
    */
    private class KAdapter extends KeyAdapter{
        
        @Override
        public void keyPressed(KeyEvent e){
            
            keyHeld[e.getKeyCode()] = true;
        }
        
        @Override
        public void keyReleased(KeyEvent e){
            
            keyHeld[e.getKeyCode()] = false;
        }
    }
}
