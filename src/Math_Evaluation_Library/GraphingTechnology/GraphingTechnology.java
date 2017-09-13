/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Math_Evaluation_Library.GraphingTechnology;

import Math_Evaluation_Library.Miscellaneous.Mod;
import Math_Evaluation_Library.Objects.Function;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.NaN;

/**

@author Antonio
*/
public class GraphingTechnology extends Graph{
    
    private static List<Set> sets = new ArrayList<>();
    private static List<Set> drawSets = new ArrayList<>();
    
    public GraphingTechnology(String header) {
        super(header);
    }
    public GraphingTechnology(String header, String[] functions) {
        super(header);
        drawSets.clear();
        for (String function : functions){
            Set newset = new Set(function);
            for (Set set : sets){
                if(set.equals(newset)){
                    newset.coordinates.addAll(set.coordinates.subList((int)((newset.xmin-set.xmin)/0.01), set.coordinates.size()-(int)((set.xmax-newset.xmax)/0.01)));
                    break;
                }
            }
            drawSets.add(newset);
        }
        List<Thread> threads = new ArrayList<>();
        for (Set set : drawSets){
            if (set.coordinates.isEmpty()){
                Thread thread = new Thread(set);
                thread.run();
                threads.add(thread);
            }
        }
        for (Thread thread : threads){
            try{
                thread.join();
            }catch(InterruptedException e){}
        }
    }
    public GraphingTechnology(String header, String function, int xmin, int xmax, int ymin, int ymax) {
        super(header, xmin, xmax, ymin, ymax);
        Set newset = new Set(function, xmin, xmax, ymin, ymax);
        for (Set set : sets){
            if(set.equals(newset)){
                newset.coordinates.addAll(set.coordinates.subList((int)((xmin-set.xmin)/0.01), set.coordinates.size()-(int)((set.xmax-xmax)/0.01)));
                break;
            }
        }
        if(newset.coordinates.isEmpty()){
            newset.run();
        }
        drawSets.clear();
        drawSets.add(newset);
    }
    
    @Override
    protected void draw(Graphics2D g){
        Color[] colors = {Color.BLUE, Color.RED, Color.GREEN, Color.ORANGE, Color.PINK, Color.YELLOW, Color.CYAN, Color.GRAY, Color.MAGENTA};
        for (int a = 0; a<Math.min(drawSets.size(), colors.length); a++){
            drawFunction(g, drawSets.get(a), colors[a]);
        }
    }
    
    public void drawFunction(Graphics2D g, Set f, Color color){
        int numDivisions = (int) Mod.gcd(f.coordinates.size(), 2520);
        List<Thread> threads = new ArrayList<>();
        for (int b = 0; b<numDivisions; b++){
            final int A = b;
            Thread thread = new Thread(()->{
                for (int a = 1+A*(f.coordinates.size()/numDivisions); a<(A+1)*(f.coordinates.size()/numDivisions); a++){
                    if (Math.abs(f.coordinates.get(a-1).y-f.coordinates.get(a).y) < 1){
                        double x1 = getCoordinateX(f.coordinates.get(a-1).x);
                        double y1 = getCoordinateY(f.coordinates.get(a-1).y);
                        double x2 = getCoordinateX(f.coordinates.get(a).x);
                        double y2 = getCoordinateY(f.coordinates.get(a).y);
                        if (x1 != NaN && y1 != NaN && x2 != NaN && y2 != NaN && Math.abs(y2-y1) < graph_height){
                            g.setColor(color);
                            g.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
                        }
                    }
                }
            });
            thread.start();
            threads.add(thread);
        }
        for (int a = 0; a<threads.size(); a++){
            try{
                threads.get(a).join();
            }catch(InterruptedException e){}
        }
    }
    
    private class Set implements Runnable{
        private Function f;
        private int xmax = 25, xmin = -xmax, ymax = xmax, ymin = xmin;
        static final double step = 0.01;
        
        private List<Coordinate> coordinates = new ArrayList<>();
        
        private Set(String function){
            f = new Function(function);
            sets.add(this);
        }
        private Set(String function, int xmin, int xmax, int ymin, int ymax){
            f = new Function(function);
            this.xmin = xmin;
            this.xmax = xmax;
            this.ymin = ymin;
            this.ymax = ymax;
            sets.add(this);
        }
        
        private boolean equals(Set plot){
            return f.equals(plot.f) && xmin >= plot.xmin && xmax <= plot.xmax && ymin >= plot.ymin && ymax <= plot.ymax;
        }
        
        @Override
        public void run(){
            if(f.isFunction()){
                List<Thread> threads = new ArrayList<>();
                Boolean isNumber = f.isNumber();
                double value = (isNumber ? f.value() : NaN);
                List<List<Coordinate>> groups = new ArrayList<>();
                final int limit = 1, threadCount = (int)((xmax-xmin)/limit);
                for (int a = 0; a<threadCount; a++){
                    final int A = a;
                    groups.add(new ArrayList<>());
                    Thread thread = new Thread(() -> {
                        if (!isNumber){
                            for (double b = xmin+A*limit; b<xmin+(A+1)*limit; b+=step){
                                Coordinate coordinate = new Coordinate(b, f.of(b));
                                if (coordinate.y != NaN){
                                    groups.get(A).add(coordinate);
                                }
                            }
                        }
                        else{
                            for (double b = xmin+A*limit; b<xmin+(A+1)*limit; b+=step){
                                Coordinate coordinate = new Coordinate(b, value);
                                groups.get(A).add(coordinate);
                            }
                        }
                    });
                    thread.start();
                    threads.add(thread);
                }
                for (int b = 0; b<threads.size(); b++){
                    try{
                        threads.get(b).join();
                        coordinates.addAll(groups.get(b));
                    }catch(InterruptedException e){}
                }
            }
        }
    }
    
    private class Coordinate{
        
        private double x, y;
        
        public Coordinate(double x, double y) {
            this.x = x;
            this.y = y;
        }
        
    }
    
}
