/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Levels;

import Elements.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import Interface.Display;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
/**
 *
 * @author Kévin
 */
public final class Level_Manager implements ActionListener {
    
    // In case we'd like to add levels
    private int _lvlNbr = 1;
    
    // Informations about the game's data
    private Coordinates _playerStartPos;
    private String _playerStartShip;
    private String _background_img;
    private String _music;
    
    // One level has an exact number of frames (time)
    private int _nbFrames = 0;
    
    // Will stock all the items of the level
    private ArrayList<Item>[] _objects;
    
    // Dictionnary used to generate items
    private HashMap<String, Class> _dictionnary;
    
    // The level manager needs to know in which panel it will load items
    private Display _display;
    
    // Timer is used to refresh the game, 60 times a second
    private Timer _timerRefresh;
    private Timer _timerLevel;
    
    public Level_Manager(Display d){
        setDisplay(d);
        init();
    }
    
    // Setting up the manager
    public void init(){
        fillDictionnary();
        loadLevel();
        
        System.out.println(this.getPlayerStartPos());
        System.out.println("Starting player ship is "+this.getPlayerStartShip());
        
        _timerRefresh = new Timer(33, _display);
        _timerRefresh.start();
        
        _timerLevel = new Timer(33, this);
        _timerLevel.start();
    }
    
    public void fillDictionnary(){
        // Our dictionnary associates Strings with Class files
        _dictionnary = new HashMap<String, Class>();
        _dictionnary.put("TIE", TIE.class);
        _dictionnary.put("health", Modifier.class);
    }
    
    public void loadLevel(){
        // The level is contained within a .dat file
        try(BufferedReader _buffer = new BufferedReader(new FileReader("./src/Levels/lvl"+this.getLvlNbr()+".dat"))) {
            //Replace by line underneath for release version
        //try(BufferedReader _buffer = new BufferedReader(new FileReader("./Levels/lvl"+this.getLvlNbr()+".dat"))) {
            String _currentLine;
            
            while ((_currentLine = _buffer.readLine()) != null) {
                // We parse our line to decide what to do with it
		parseLine(_currentLine);
            }
            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void parseLine(String line){
        String[] data = line.split(" ");
        switch(data[0]){
            default:
                System.out.println("Couldn't retrieve corresponding data for '"+line+"'");
                break;
            case "playerStartPos":
                this.setPlayerStartPos(Integer.parseInt(data[1]), Integer.parseInt(data[2]));
                break;
            case "playerStartShip":
                this.setPlayerStartShip(data[1]);
                break;
            case "background":
                this.setBackground_img(data[1]);
                break;
            case "music":
                this.setMusic(data[1]);
                break;
            case "modifier":
                this.addItem(data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]), Integer.parseInt(data[4]));
                break;
            case "ennemy":
                this.addItem(data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]), Integer.parseInt(data[4]));
                break;
            case "duration":
                _objects = new ArrayList[Integer.parseInt(data[1])];
                for(int i=0; i<_objects.length-1; i++){
                    _objects[i] = new ArrayList<>();
                }
                break;
            case "//":
                System.out.println(line.split("//")[1]);
                break;
        }
    }
    
    public void addItem(String name, int frameNbr, int x, int y){
        try {
            /*
                In the game, everything is bascially an Item with different abilities.
                Hence we can use polymorphism to generate any Class of the game.
            */
            Item i = (Item) this._dictionnary.get(name).getConstructor(Coordinates.class).newInstance(new Coordinates(x, y));
            _objects[frameNbr].add(i);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(Level_Manager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(Level_Manager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Level_Manager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Level_Manager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(Level_Manager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(Level_Manager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // On each frame we check if the game needs to dispaly a new Displayable
        if(_nbFrames < _objects.length-1){
            _nbFrames++;
            ArrayList<Item> itemsToAdd = _objects[_nbFrames];
            itemsToAdd.forEach((i) -> {
                Display.addDrawable(i);
            });
        }
    }
    
    public Coordinates getPlayerStartPos(){
        return this._playerStartPos;
    }
    
    public void setPlayerStartPos(int x, int y){
        this._playerStartPos = new Coordinates(x, y);
    }
    
    public String getPlayerStartShip(){
        return this._playerStartShip;
    }
    
    public void setPlayerStartShip(String shipname){
        this._playerStartShip = shipname;
    }

    public String getBackground_img() {
        return _background_img;
    }

    public void setBackground_img(String _background_img) {
        this._background_img = _background_img;
    }

    public String getMusic() {
        return _music;
    }

    public void setMusic(String _music) {
        this._music = _music;
    }

    public void setDisplay(Display d) {
        this._display = d;
    }

    public int getLvlNbr() {
        return _lvlNbr;
    }

    public void setLvlNbr(int _lvlNbr) {
        this._lvlNbr = _lvlNbr;
    }

    public int getNbFrames() {
        return _nbFrames;
    }

    public void setNbFrames(int _nbFrames) {
        this._nbFrames = _nbFrames;
    }

    public ArrayList<Item>[] getObjects() {
        return _objects;
    }

    public void setObjects(ArrayList<Item>[] _objects) {
        this._objects = _objects;
    }

    public Timer getTimerRefresh() {
        return _timerRefresh;
    }

    public void setTimerRefresh(Timer _timerRefresh) {
        this._timerRefresh = _timerRefresh;
    }

    public Timer getTimerLevel() {
        return _timerLevel;
    }

    public void setTimerLevel(Timer _timerLevel) {
        this._timerLevel = _timerLevel;
    }
}
