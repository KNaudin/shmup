/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.EventQueue;
import javax.swing.JFrame;

import Interface.Display;
import javax.swing.JOptionPane;
/**
 *
 * @author Kévin
 */
public class SHMUP extends JFrame{

    /**
     * Defines SHMUP()
     */
    public SHMUP()
    {
        // We add a new JPanel display. The display will handle all of the game events
        add(new Display());
        
        // This method is used so the screen picks the exact same size as our JPanel
        setResizable(false);
        pack();
        
        setTitle("SHMUP - Mini-Projet Java");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /**
     * Main function, only used to call SHMUP
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {                
                JFrame g_window = new SHMUP();
                g_window.setVisible(true);                
            }
        });
    }
    
}
