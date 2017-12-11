/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Math_Evaluation_Library.GraphingTechnology;

import java.awt.*;

/**

@author Antonio
*/
public class DirectionField extends Graph{
    
    /*
    public static void main(String[] args){
        //slopeF(y)
        // Main.w = new Window();
        Function f = new Function("x-y+1");
        new DirectionField("Direction Fields by Antonio Kim", f).Open();
    }
    /*
    */
    
//    private Function f;
//
    public DirectionField(String header) {//, Function f
        super(header, -31, 31, -31, 31, false);
        //this.f = f;
    }
    
//    public DirectionField(Function f){
//        this.f = f;
//        f.printFormat();
//    }
    
    @Override
    protected void draw (Graphics2D g){
//        g.setColor(Color.BLUE);
//        double line_length = 1999/10000.0;
//        for (double x = -25; x<=25; x+=0.5){
//            for (double y = -25; y<=25; y+=0.5){
//                double theta = Math.atan(f.of(x, y));
//                double x_width = Math.cos(theta)*line_length,
//                        y_height = Math.sin(theta)*line_length;
//                double x1 = getCoordinateX(x-x_width);
//                double y1 = getCoordinateY(y-y_height);
//                double x2 = getCoordinateX(x+x_width);
//                double y2 = getCoordinateY(y+y_height);
//                g.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
//            }
//        }
    }
    
    
}
