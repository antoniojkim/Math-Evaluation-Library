/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Math_Evaluation_Library.GraphingTechnology;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;

import static java.lang.Double.NaN;

/**

@author Antonio
*/
public class Graph extends JPanel{

    protected static JFrame w;
    private String header;
    private final double widthR = (Screen.getWidth()/3840), heightR = (Screen.getHeight()/2160);
    private final double diagonalR = (Screen.getDiagonal()/4405.814340164597);
    private final double default_width = 76.75;
    private double default_height = 130;
    
    protected int x_min = -30, x_max = 30, y_min = -30, y_max = 30;
    protected int graph_width = (int)((x_max-x_min)*default_width*widthR),
            graph_height = (int)((y_max-y_min)*default_height*heightR);
    protected boolean stretchY = false;
    
    protected BufferedImage image;
    private int imageX = 0, imageY = 0;
    private double imagescale = 1.25;
    
    
    public Graph(String header){
        this.header = header;
    }
    public Graph(String header, int x_min, int x_max, int y_min, int y_max){
        this.header = header;
        setBoundaries(x_min, x_max, y_min, y_max);
    }
    public Graph(String header, int x_min, int x_max, int y_min, int y_max, boolean stretchY){
        this.header = header;
        setBoundaries(x_min, x_max, y_min, y_max);
        if (!stretchY){
            this.stretchY = stretchY;
            default_height = 76.75;
            graph_height = (int)((y_max-y_min)*default_height*heightR);
        }
    }

    public void initializeWindow(){
        w = new JFrame(header);
        w.getContentPane().add(this, BorderLayout.CENTER);
        w.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        w.setSize((int)(Screen.getWidth()/2), (int)(Screen.getHeight()/2));
        w.setLocationRelativeTo(null);
//        w.addWindowStateListener((WindowEvent we) -> {
//            centerImage();
//        });
//        w.addComponentListener(new ComponentAdapter() {
//            @Override
//            public void componentResized(ComponentEvent ce) {
//                centerImage();
//            }
//        });
        w.add(this);
        w.setVisible(true);
    }
    public void Open(){
        setLayout(null);
        initializeWindow();
        Mouse mouse = new Mouse();
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
        addMouseWheelListener(mouse);
    }
    
    public void setBoundaries(int x_min, int x_max, int y_min, int y_max){
        this.x_min = Math.min(x_min, this.x_min);
        this.x_max = Math.max(x_max, this.x_max);
        this.y_min = Math.min(y_min, this.y_min);
        this.y_max = Math.max(y_max, this.y_max);
        this.image = null;
        graph_width = (int)((this.x_max-this.x_min)*default_width*widthR);
        graph_height = (int)((this.y_max-this.y_min)*default_height*heightR);
    }
    public int get_x_min(){
        return x_min;
    }
    public int get_x_max(){
        return x_max;
    }
    public int get_y_min(){
        return y_min;
    }
    public int get_y_max(){
        return y_max;
    }
    
    protected void centerImage(){
        if (image != null){
            imageX = (int)(w.getWidth()/2-getCoordinateX(0)*imagescale);
            imageY = (int)(w.getHeight()/2-getCoordinateY(0)*imagescale);
        }
    }
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if (image == null){
            image = new BufferedImage(graph_width, graph_height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = image.createGraphics();
            drawCartesianGrid(g2);
            draw(g2);
            centerImage();
        }
        g.drawImage(image, imageX, imageY, (int)(image.getWidth()*imagescale), (int)(image.getHeight()*imagescale), null);
    }
    
    protected double getCoordinateX (double x){
        if (x > x_min && x < x_max){
            return (x-x_min)*default_width*widthR;
        }
        return NaN;
    }
    protected double getCoordinateY (double y){
        if (y > y_min && y < y_max){
            return (y_max-y)*default_height*heightR;
        }
        return NaN;
    }
    private void drawCartesianGrid(Graphics2D g){
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, graph_width, graph_height);
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke((int)Math.ceil(2*diagonalR)));
        g.drawLine(0, (int)getCoordinateY(0), graph_width, (int)getCoordinateY(0));
        g.drawLine((int)getCoordinateX(0), 0, (int)getCoordinateX(0), graph_height);
        g.setFont(_Font_.getFont((int)Math.ceil(25*diagonalR)));
        double tickwidth = 5*diagonalR;
        for (int x = x_min+1; x<x_max; x++){
            g.drawLine((int)getCoordinateX(x), (int)(getCoordinateY(0)-tickwidth*heightR), (int)getCoordinateX(x), (int)(getCoordinateY(0)+tickwidth*heightR));
            if (x != 0){
                g.drawString(""+x, (int)((int)getCoordinateX(x)-5*widthR), (int)(getCoordinateY(0)+25*heightR));
            }
        }
        for (int y = y_min+1; y<y_max; y++){
            g.drawLine((int)(getCoordinateX(0)-tickwidth*widthR), (int)getCoordinateY(y), (int)(getCoordinateX(0)+tickwidth*widthR), (int)getCoordinateY(y));
            if (y != 0){
                g.drawString(""+y, (int)((int)getCoordinateX(0)+10*widthR), (int)(getCoordinateY(y)+8*heightR));
            }
        }
    }
    protected void draw(Graphics2D g2){}
    
    private class Mouse extends MouseAdapter{
        Point origin = new Point();
        Point imageorigin = new Point();
        @Override
        public void mousePressed(MouseEvent me){
            if (image != null){
                origin = me.getPoint();
                imageorigin = new Point(imageX, imageY);
            }
            
        }
        @Override
        public void mouseReleased(MouseEvent me) {
            if (image != null && me.getButton() == 3){
                imagescale = 1.25;
                centerImage();
                repaint();
            }
        }
        @Override
        public void mouseDragged(MouseEvent me){
            if (image != null){
                int changeX = imageorigin.x - (origin.x - me.getX());
                if (changeX <= 0 && changeX+image.getWidth()*imagescale >= w.getWidth()){
                    imageX = changeX;
                    //System.out.println(imageorigin.x+", "+imageorigin.y+", "+imageX+", "+imageY);
                    repaint();
                }
                int changeY = imageorigin.y - (origin.y - me.getY());
                if (changeY <= 0 && changeY+image.getHeight()*imagescale >= w.getHeight()){
                    imageY = changeY;
                    //System.out.println(imageorigin.x+", "+imageorigin.y+", "+imageX+", "+imageY);
                    repaint();
                }
            }
        }
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            if (image != null){
                if (e.getWheelRotation() < 0){
//                    int mx = e.getX(), my = e.getY();
                    imagescale += 0.1;
                }
                else if (image.getWidth()*(imagescale) > w.getWidth()
                        && image.getHeight()*(imagescale) > w.getHeight()){
                    imagescale = Math.max(0, imagescale-0.1);
                }
                centerImage();
                repaint();
            }
        }
        
    }
    
    
    public boolean isOpen(){
        if (w != null){
            return w.isVisible();
        }
        return false;
    }
    public void setDisposable(){
        w.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    public void dispose(){
        if (w != null){
            w.dispose();
        }
    }
    
}
