/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author Kévin
 * This class is used to play audio while the game is playing
 * It can be music or SFX
 */
public class Audio_Manager {
    
    private MediaPlayer _mediaPlayer;
    
    public Audio_Manager() {
        JFXPanel fxPanel = new JFXPanel();
    }
    
    public void playMusic(String file, double volume){
        try {
            Media music = new Media(new File("./Music/"+file+".mp3").toURI().toString());
            _mediaPlayer = new MediaPlayer(music);
            _mediaPlayer.play();
            _mediaPlayer.setVolume(volume);
        } catch (Exception ex) {
            Logger.getLogger(Audio_Manager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void stopMusic(){
        _mediaPlayer.stop();
    }
    
    public void lowerVolume(){
        _mediaPlayer.setVolume(_mediaPlayer.getVolume()-0.1);
        checkVolume();
    }
    
    public void increaseVolume(){
        _mediaPlayer.setVolume(_mediaPlayer.getVolume()+0.1);
        checkVolume();
    }
    
    public void checkVolume(){
        if(_mediaPlayer.getVolume() > 1)
            _mediaPlayer.setVolume(1);
        if(_mediaPlayer.getVolume() < 0){
            _mediaPlayer.setVolume(0);
        }
    }
}
