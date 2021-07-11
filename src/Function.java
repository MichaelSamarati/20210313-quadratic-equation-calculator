/*
Die Taktik ist es, aus den gegebenen Werten a, b und c zu ermitteln und mit diesen Werten alle andereren ausrechnen.
*/

class Functions{
    
    private final int A_Y1_X3_Y3_DEFAULTVALUE = 1;
    private final int B_C_M_N_Z1_Z2_Y1_X2_Y2_DEFAULTVALUE = 0;
    private final int X1_Y2DEFAULTVALUE = -1;
    private final int A_MIN_RANDOM_VALUE = -5;
    private final int B_C_MIN_RANDOM_VALUE = -10;
    private final int A_MAX_RANDOM_VALUE = 5;
    private final int B_C_MAX_RANDOM_VALUE = 10;
    private final int MAX_DECIMAL_PLACES = 3;
    public double a, b, c, m, n, z1, z2, x1, y1, x2, y2, x3, y3;
    public double b_normalized, c_normalized;
    public double firstresult, secondresult;
    
    Functions(){
        clrValues();
    }
    
    public void clrValues(){
        a = 0.0;
        b = 0.0;
        c = 0.0;
        m = 0.0;
        n = 0.0;
        z1 = 0.0;
        z2 = 0.0;
        x1 = 0.0;
        y1 = 0.0;
        x2 = 0.0;
        y2 = 0.0;
        x3 = 0.0;
        y3 = 0.0;
        b_normalized = 0.0;
        c_normalized = 0.0;
        firstresult = 0.0; 
        secondresult = 0.0;
    }

    public void calculateFunction_Normal(String newa, String newb, String newc){
        if(newa.isEmpty()){a = A_Y1_X3_Y3_DEFAULTVALUE;}else{a = Double.valueOf(newa);}
        if(newb.isEmpty()){b = B_C_M_N_Z1_Z2_Y1_X2_Y2_DEFAULTVALUE;}else{b = Double.valueOf(newb);}
        if(newc.isEmpty()){c = B_C_M_N_Z1_Z2_Y1_X2_Y2_DEFAULTVALUE;}else{c = Double.valueOf(newc);}
        calculateAllResults();
    }
    
    public void calculateFunction_Vertex(String newa, String newm, String newn){
        double m_value, n_value;
        if(newa.isEmpty()){a = A_Y1_X3_Y3_DEFAULTVALUE;}else{a = Double.valueOf(newa);}
        if(newm.isEmpty()){m_value = B_C_M_N_Z1_Z2_Y1_X2_Y2_DEFAULTVALUE;}else{m_value = Double.valueOf(newm);}
        if(newn.isEmpty()){n_value = B_C_M_N_Z1_Z2_Y1_X2_Y2_DEFAULTVALUE;}else{n_value = Double.valueOf(newn);}
        b = a*2*(-m_value);
        c = a*(m_value*m_value)+n_value;
        calculateAllResults();
    }
    
    public void calculateFunction_LinearFactors(String newa, String newz1, String newz2){
        double z1_value, z2_value;
        if(newa.isEmpty()){a = A_Y1_X3_Y3_DEFAULTVALUE;}else{a = Double.valueOf(newa);}
        if(newz1.isEmpty()){z1_value = B_C_M_N_Z1_Z2_Y1_X2_Y2_DEFAULTVALUE;}else{z1_value = Double.valueOf(newz1);}
        if(newz2.isEmpty()){z2_value = B_C_M_N_Z1_Z2_Y1_X2_Y2_DEFAULTVALUE;}else{z2_value = Double.valueOf(newz2);}
        b = a*(z1_value+z2_value);
        c = a*(z1_value*z2_value);
        calculateAllResults();
    }
    
    public void calculateFunction_ThreePoints(String newx1, String newy1, String newx2, String newy2, String newx3, String newy3){
        if(newx1.isEmpty()){x1 = X1_Y2DEFAULTVALUE;}else{x1 = Double.valueOf(newx1);}
        if(newy1.isEmpty()){y1 = A_Y1_X3_Y3_DEFAULTVALUE;}else{y1 = Double.valueOf(newy1);}
        if(newx2.isEmpty()){x2 = B_C_M_N_Z1_Z2_Y1_X2_Y2_DEFAULTVALUE;}else{x2 = Double.valueOf(newx2);}
        if(newy2.isEmpty()){y2 = B_C_M_N_Z1_Z2_Y1_X2_Y2_DEFAULTVALUE;}else{y2 = Double.valueOf(newy2);}
        if(newx3.isEmpty()){x3 = A_Y1_X3_Y3_DEFAULTVALUE;}else{x3 = Double.valueOf(newx3);}
        if(newy3.isEmpty()){y3 = A_Y1_X3_Y3_DEFAULTVALUE;}else{y3 = Double.valueOf(newy3);}
        /*
        Es wird hier das Einsetzungsverfahren benutzt, um a, b und c zu ermitteln.
        Zuerst wird die dritte Formel in die erste eingesetzt.
        Die umgeformte Gleichung wird dann in die zweite eingesetzt.
        Diese umgeformte Gleichung dann wieder in die dritte Anfangsgleichung eingesetzt.
        
        Bsp.:
        P1(-2|15) => a*x1^2+b*x1+c = y => a*(-2)^2+b*(-2)+c = 15
        P2(1|-6) => a*x2^2+b*x2+c = y => a+b+c = -6
        P3(2|-1) => a*x3^2+b*x3+c = y => a*2^2+b*2+c = -1
        
        4a-2b-4a-2b-1 = 15
        -4b-1 = 15
        -4b = 16
        b = -4
        
        a-4+c = -6
        a+c = -2
        c = -a-2
        
        4a-8-a-2 = -1
        3a-10 = -1
        3a = 9
        a = 3
        
        c = -3-2 = -5
        
        3x^2-4x-5 = y
        
        Weil man bei dem Algorithmus sehr viel bedenken musste, habe ich dafür mehrere Stunden gebraucht.
        */
        double a1 = x1*x1;
        double b1 = x1;
        double r1 = y1;
        double a2 = x2*x2;
        double b2 = x2;
        double r2 = y2;
        double a3 = x3*x3;
        double b3 = x3;
        double r3 = y3;
        double b_1_3 = b1-b3;
        double a_1_3 = -(a1-a3); if(b_1_3!=0){a_1_3 = a_1_3/b_1_3;}
        double r_1_3 = r1-r3; if(b_1_3!=0){r_1_3 = r_1_3/b_1_3;}
        double a_2_13 = -(a2+a_1_3*b2);
        double r_2_13 = (r2-(r_1_3*b2));
        double a_3_213 = a3+a_2_13;
        double r_3_213 = (r3-(r_1_3*b3+r_2_13)); if(a_3_213!=0){r_3_213 = r_3_213/a_3_213;}
        a = r_3_213;
        a = round(a, MAX_DECIMAL_PLACES);
        b = r_1_3+a_1_3*r_3_213;
        b = round(b, MAX_DECIMAL_PLACES);
        c = r_2_13+a_2_13*r_3_213;
        c = round(c, MAX_DECIMAL_PLACES);
        calculateAllResults();
    }
    
    public void calculateFunction_RandomValues(){
        double rangeA = A_MAX_RANDOM_VALUE-A_MIN_RANDOM_VALUE+1;
        double rangeB = B_C_MAX_RANDOM_VALUE-B_C_MIN_RANDOM_VALUE+1;            
            a = (Math.random() * rangeA)+A_MIN_RANDOM_VALUE;
            a = round(a, MAX_DECIMAL_PLACES);
            b = (Math.random() * rangeB)+B_C_MIN_RANDOM_VALUE;
            b = round(b, MAX_DECIMAL_PLACES);
            c = (Math.random() * rangeB)+B_C_MIN_RANDOM_VALUE;
            c = round(c, MAX_DECIMAL_PLACES);
            calculate_firstResult();
        calculateAllResults();
    }

    private void calculateAllResults(){
        calculate_firstResult();
        calculate_secondResult();
        calculate_b_normalized();
        calculate_c_normalized();
        calculate_m_n();
        calculate_z1_z2();
    }
    
    private void calculate_firstResult(){
        firstresult = (-b+Math.sqrt(b*b-4*a*c))/(2*a);
        if(!Double.isNaN(firstresult)){firstresult = round(firstresult, MAX_DECIMAL_PLACES);}
    }
    
    private void calculate_secondResult(){
        secondresult = (-b-Math.sqrt(b*b-4*a*c))/(2*a); 
        if(!Double.isNaN(secondresult)){secondresult = round(secondresult, MAX_DECIMAL_PLACES);}
    }
    
    public void calculate_b_normalized(){
        b_normalized = b/a;
        b_normalized = round(b_normalized, MAX_DECIMAL_PLACES);
    }
    
    public void calculate_c_normalized(){
        c_normalized = c/a;
        c_normalized = round(c_normalized, MAX_DECIMAL_PLACES);
    }
    
    public void calculate_m_n(){
        m = (b/a)/2; 
        m = round(m, MAX_DECIMAL_PLACES);
        n = c-m*m*a;
        n = round(n, MAX_DECIMAL_PLACES);
    }
    
    public void calculate_z1_z2(){
        z1 = firstresult*(-1);
        z1 = round(z1, MAX_DECIMAL_PLACES);
        z2 = secondresult*(-1);
        z2 = round(z2, MAX_DECIMAL_PLACES);
    }
    
    public double round(double value, int places) {
        /*
        Das ist die einzige Methode, die ich nicht selbst programmiert habe, sondern aus dem Internet kopiert habe.
        Ich verstehe es darum auch nicht komplett.
        Ich hätte es eventuell auch alleine geschafft, aber es hätte mich bestimmt ein paar Stunden gebraucht, dass es auch ohne Fehler funktioniert hätte.
        */
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }  
    
    public int[] getXPoints(int n){
        int[] x = new int[n];
        for(int i = 0; i<x.length; i++){
            x[i] = i;
        }
        return x;
    }
    
    public int[] getYPoints(int[] x, int x_unit_length, int y_unit_length, int x_origin, int y_origin, int step){
        int[] y = new int[x.length];
        double[] z = new double[x.length];
        for(int i = 0; i<y.length; i++){
            z[i] = (double)(x[i]-x_origin)*x_unit_length/step;
            y[i] = (int) (y_origin-(a*z[i]*z[i]+b*z[i]+c)*step/y_unit_length);
        }
        return y;
    }
    
}
