/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;

/**
 *
 * @author Kévin
 * This manager is there only to load images and manipulate them
 */
public class Image_Manager{
    public Image_Manager(){}
    
    // Loads an image using it's name
    public static Image load(String image_name){
        return new ImageIcon(Image.class.getResource("/Images/"+image_name+".png")).getImage();
    }
    // Sprites are in fact ImageIcon, so the image needs to be converted first
    public static BufferedImage toBufferedImage(Image image){
        BufferedImage newImage = new BufferedImage(
        image.getWidth(null), image.getHeight(null),
        BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
    }
    
    // This method is used to make the explosions more realistic
    public Image rotateResizeImage(BufferedImage img, double angle){
        AffineTransform tr = new AffineTransform();
        tr.rotate(angle, img.getWidth(null), img.getHeight(null));
        AffineTransformOp op = new AffineTransformOp(tr, AffineTransformOp.TYPE_BILINEAR);
        BufferedImage image = op.filter(img, null);
        Graphics2D g = image.createGraphics();
        g.drawImage(img, 0, 0, (int) angle*img.getWidth()/2, (int) angle*img.getHeight()/2, null);
        g.dispose();
        return image;
    }
}
