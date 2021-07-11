import java.awt.*;
import javax.swing.*;

public class Graphic {

    JPanel jPanel;
    Graphics2D g2D;
    private final Color COLOR_BACKGROUND_COORDINATESYSTEM = Color.WHITE;
    private final Color COLOR_LINES_COORDINATESYSTEM = Color.LIGHT_GRAY;
    private final Color COLOR_AXIS_LINES_COORDINATESYSTEM = Color.DARK_GRAY;
    private final Color COLOR_FUNCTION_COORDINATESYSTEM = Color.BLUE;
    private final Color COLOR_ZEROPOINT_COORDINATESYSTEM = Color.MAGENTA;
    private final Color COLOR_VERTEXPOINT_COORDINATESYSTEM = Color.ORANGE;
    private final Color COLOR_UNIT_NUMBERS_COORDINATESYSTEM = Color.RED;
    private final BasicStroke STROKE_SIZE_LINES_COORDINATESYSTEM = new BasicStroke(1);
    private final BasicStroke STROKE_SIZE_AXIS_LINES_COORDINATESYSTEM = new BasicStroke(2);
    private final BasicStroke STROKE_SIZE_FUNCTION_COORDINATESYSTEM = new BasicStroke(3);
    private final int X_MINLENGTH = 0;
    private final int Y_MINLENGTH = 0;
    private final int X_MAXLENGTH = 494;
    private final int Y_MAXLENGTH = 494;
    private final int STEP = 40;
    private final int X_ORIGIN = X_MAXLENGTH/2;
    private final int Y_ORIGIN = Y_MAXLENGTH/2;
    private final int X_UNIT_DISPLAY_ALIGNMENT = 10;
    private final int Y_UNIT_DISPLAY_ALIGNMENT = -20;
    private final int X_VERTEXPOINT_DISPLAY_ALIGNMENT = -15;
    private final int Y_VERTEXPOINT_DISPLAY_ALIGNMENT = -20;
    private final int X_ZEROPOINT_DISPLAY_ALIGNMENT = 5;
    private final int Y_ZEROPOINT_DISPLAY_ALIGNMENT = 25;
    private final int POINT_SIZE = 8;
    private int x_unit_length;
    private int y_unit_length;
    
    Graphic(JPanel jPanel){
        this.jPanel = jPanel;
        g2D = (Graphics2D) this.jPanel.getGraphics();
        x_unit_length = -1;
        y_unit_length = -1;
    }
    
    public void drawGraph(Functions function){
        reset_function_graphic();
        if(function.a==0){return;}
        getXUnit(function);
        getYUnit(function);
        drawXUnitNumbers();
        drawYUnitNumbers();      
        draw_function(function);
        draw_zeropoints(function);
        draw_vertexpoint(function);
        draw_legend();
    }
    
    public void reset_function_graphic(){
        g2D.setColor(COLOR_BACKGROUND_COORDINATESYSTEM);        
        g2D.fillRect(X_MINLENGTH, Y_MINLENGTH, X_MAXLENGTH, Y_MAXLENGTH);
        draw_horizontal_lines();
        draw_vertical_lines();
        draw_axis_lines();
    }
    
    public void draw_horizontal_lines(){
        g2D.setColor(COLOR_LINES_COORDINATESYSTEM);
        g2D.setStroke(STROKE_SIZE_LINES_COORDINATESYSTEM);
        for(int i = STEP; i<Y_MAXLENGTH; i = i+STEP){
            g2D.drawLine(X_MINLENGTH, Y_ORIGIN-i, X_MAXLENGTH, Y_ORIGIN-i);     // 1. & 2. Quadrant
            g2D.drawLine(X_MINLENGTH, Y_ORIGIN+i, X_MAXLENGTH, Y_ORIGIN+i);     // 3. & 4. Quadrant
        }
    }
    
    public void draw_vertical_lines(){
        g2D.setColor(COLOR_LINES_COORDINATESYSTEM);
        g2D.setStroke(STROKE_SIZE_LINES_COORDINATESYSTEM);
        for(int i = STEP; i<X_MAXLENGTH; i = i+STEP){
            g2D.drawLine(X_ORIGIN-i, Y_MINLENGTH, X_ORIGIN-i, Y_MAXLENGTH);     // 1. & 2. Quadrant
            g2D.drawLine(X_ORIGIN+i, Y_MINLENGTH, X_ORIGIN+i, Y_MAXLENGTH);     // 3. & 4. Quadrant
        }
    }
    
    public void draw_axis_lines(){
        g2D.setColor(COLOR_AXIS_LINES_COORDINATESYSTEM);
        g2D.setStroke(STROKE_SIZE_AXIS_LINES_COORDINATESYSTEM);
        g2D.drawLine(X_MINLENGTH, Y_ORIGIN, X_MAXLENGTH, Y_ORIGIN);     // 1. & 2. Quadrant
        g2D.drawLine(X_ORIGIN, Y_MINLENGTH, X_ORIGIN, Y_MAXLENGTH);     
    }

    public void getXUnit(Functions function){
        x_unit_length = (int) Math.abs(function.m);
        if(x_unit_length<=0){x_unit_length = 1;}
    }
    
    public void getYUnit(Functions function){
        y_unit_length = (int) Math.abs(function.n);
        if(y_unit_length<=0){y_unit_length = 1;}
    }

    public void drawXUnitNumbers(){
        g2D.setColor(COLOR_UNIT_NUMBERS_COORDINATESYSTEM);  
        String s = "";
        for(int i = 0; i<Y_MAXLENGTH; i = i+STEP){
            s = Integer.toString(x_unit_length*i/STEP);
            g2D.drawString(s, X_ORIGIN+i, Y_ORIGIN+X_UNIT_DISPLAY_ALIGNMENT);
            s = Integer.toString(-(x_unit_length*i/STEP));
            g2D.drawString(s, X_ORIGIN-i, Y_ORIGIN+X_UNIT_DISPLAY_ALIGNMENT);
        }
    }
    
    public void drawYUnitNumbers(){
        g2D.setColor(COLOR_UNIT_NUMBERS_COORDINATESYSTEM);  
        String s = "";
        for(int i = 0; i<Y_MAXLENGTH; i = i+STEP){
            if(i==0){continue;}
            s = Integer.toString(y_unit_length*i/STEP);
            g2D.drawString(s, X_ORIGIN+Y_UNIT_DISPLAY_ALIGNMENT, Y_ORIGIN-i);
            s = Integer.toString(-(y_unit_length*i/STEP));
            g2D.drawString(s, X_ORIGIN+Y_UNIT_DISPLAY_ALIGNMENT, Y_ORIGIN+i);
        }
    }
    
    public void draw_function(Functions function){
        g2D.setColor(COLOR_FUNCTION_COORDINATESYSTEM);
        g2D.setStroke(STROKE_SIZE_FUNCTION_COORDINATESYSTEM);
        int[] x = function.getXPoints(X_MAXLENGTH);
        int[] y = function.getYPoints(x, x_unit_length, y_unit_length, X_ORIGIN, Y_ORIGIN, STEP);
        g2D.drawPolyline(x, y, x.length);
    }
    
    public void draw_zeropoints(Functions function){
        g2D.setColor(COLOR_ZEROPOINT_COORDINATESYSTEM);
        draw_zeropoint(function.firstresult);
        draw_zeropoint(function.secondresult);
    }
    
    public void draw_zeropoint(double value){
        if(!Double.isNaN(value)){
            int x = (int)(value*STEP/x_unit_length+X_ORIGIN-(POINT_SIZE/2));
            int y = (int)Y_ORIGIN-(POINT_SIZE/2);
            g2D.fillOval(x, y, POINT_SIZE, POINT_SIZE);
            g2D.drawString(Double.toString(value), x+X_ZEROPOINT_DISPLAY_ALIGNMENT, y+Y_ZEROPOINT_DISPLAY_ALIGNMENT);
        }   
    }
    
    public void draw_vertexpoint(Functions function){
        g2D.setColor(COLOR_VERTEXPOINT_COORDINATESYSTEM);
        int x = (int)(-(function.m*STEP/x_unit_length)+X_ORIGIN-(POINT_SIZE/2));
        int y = (int)(-(function.n*STEP/y_unit_length)+Y_ORIGIN-(POINT_SIZE/2));
        g2D.fillOval(x, y, POINT_SIZE, POINT_SIZE);
        double m = function.m*(-1);if(m==0){m = m*(-1);}
        double n = function.n;
        g2D.drawString("("+m+"|"+n+")", x+X_VERTEXPOINT_DISPLAY_ALIGNMENT, y+Y_VERTEXPOINT_DISPLAY_ALIGNMENT); 
    }
    
    public void draw_legend(){
        g2D.setColor(COLOR_ZEROPOINT_COORDINATESYSTEM);  
        g2D.drawString("Zeropoint", 10, 20);
        g2D.setColor(COLOR_VERTEXPOINT_COORDINATESYSTEM);  
        g2D.drawString("Vertexpoint", 10, 35);
    }
    
}
