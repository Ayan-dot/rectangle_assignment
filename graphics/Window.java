package graphics; //imports 
import javax.swing.*; 
import graphics.SelectPlane.RectButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;
public class Window extends JFrame{ // class for main drawing window
    private int maxx = 0; // extent parameters, to find the edges of the cartesian plane needed
    private int maxy = 0;
    private int minx = 0;
    private int miny = 0;
    private SelectPlane hiddenPlane;
    private RectButton[] buttonarray;

    public Window(myRectangle[] rect1){ 
        for(int i = 0; i<rect1.length; i++){
            int[] holder = rect1[i].dimensions(); // take each rectangle, and find the max needed edges for the overall cartesian plane to draw the rectangles on
            // System.out.println(Arrays.toString(holder));
            if(holder[0]<this.minx){
                this.minx = holder[0];
                
                
            }
            if(holder[1]<this.miny){
                this.miny = holder[1];
                
            }
            if(holder[2]>this.maxy){
                this.maxy = holder[2];
                
            }
            if(holder[3]>this.maxx){
                this.maxx = holder[3];
                
                
            }
            // System.out.println(this.maxx+" "+this.minx+" "+this.maxy+" "+this.miny);
        }
        // set the characteristics of this window, and initialize subclasses 
        setTitle("Rectangle Assignment");
        setSize(1000,1000);
     
        setLayout(null); 
        Plane mainPlane = new Plane(this.maxx,this.maxy,this.minx,this.miny, rect1);
        add(mainPlane);
        this.hiddenPlane = new SelectPlane(mainPlane.returnRecArr(), mainPlane.returnMinx(), mainPlane.returnMiny(),mainPlane.returnSep());
        add(hiddenPlane);
        this.buttonarray = this.hiddenPlane.returnArray();
        setResizable(true);
        
        
        // pass the array to the plane
       
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    public void visible(boolean n){ // set visible function
        setVisible(n);
    }
    public void rectUpdate(myRectangle[] rect1){ // when new rectangle is input, redraw the whole plain while recalculating extents
        getContentPane().removeAll();
        repaint();
        for(int i = 0; i<rect1.length; i++){
            int[] holder = rect1[i].dimensions();
            // System.out.println(Arrays.toString(holder));
            
            if(holder[0]<this.minx){
                this.minx = holder[0];
                
                
            }
            if(holder[1]<this.miny){
                this.miny = holder[1];
                
            }
            if(holder[2]>this.maxy){
                this.maxy = holder[3];
                
            }
            if(holder[3]>this.maxx){
                this.maxx = holder[3];
               
                
            }
            // System.out.println(this.maxx+" "+this.minx+" "+this.maxy+" "+this.miny);
        }
        Plane mainPlane = new Plane(this.maxx,this.maxy,this.minx,this.miny, rect1);
        add(mainPlane);
        this.hiddenPlane = new SelectPlane(mainPlane.returnRecArr(), mainPlane.returnMinx(), mainPlane.returnMiny(),mainPlane.returnSep());
        add(hiddenPlane);
         


    }
    public void startUpdate(){ // update the array of buttons using the rectangle array passed to this window
        this.buttonarray = this.hiddenPlane.returnArray();
        
        
    }
    public RectButton[] returnArray(){ // return the array of buttons
        return this.buttonarray;
    }

}
class Plane extends JComponent{ // drawing plane
    private int maxx;
    private int maxy;
    private int minx;
    private int miny;
    private myRectangle[] rect2;
    private myRectangle[] rect3;
    private double seperator;
    private Color[] recColors;

    public Plane(int mx, int my, int x, int y, myRectangle[] rect1){
        this.rect2 = new myRectangle[rect1.length];
        this.rect3 = new myRectangle[rect1.length];
        if(mx>0&&my>0&&x>0&&y>0){
            x=-5;
            y=-5;
        }
        else if(mx<0&&my<0&&x<0&&y<0){
            mx=5;
            my=5;
        }
        this.maxx=(int)Math.round(1.5*mx); // set padding for edges, we don't want them to be exactly aligned with rectangle
        this.maxy=(int)Math.round(1.5*my);
        this.minx = (int)Math.round(1.5*x);
        this.miny = (int)Math.round(1.5*y);
        setBounds(25,25,900,900);
        setBackground(Color.black);
        setBorder(BorderFactory.createLineBorder(Color.white, 2));
        setColoring();
        divide();
        convert(rect1);
    }
    public void convert(myRectangle[] rect1){ // convert the coordinate and widths of each rectangle using the seperator constant, which is the scale of the drawing plane in pixels
        // System.out.println("hello");
        for(int i = 0; i<rect1.length; i++){
            int[] holder = rect1[i].dimensions();
            // System.out.println(Arrays.toString(holder));
            this.rect2[i] = new myRectangle((int)Math.round(holder[0]*this.seperator),(int)Math.round(holder[1]*this.seperator), (int)Math.round(holder[4]*this.seperator), (int)Math.round(holder[5]*this.seperator));
            this.rect3[i] = new myRectangle(holder[0], holder[1], holder[4], holder[5]);
            // System.out.println((int)Math.round(holder[4]*this.seperator));
        }

    }
    public int returnMinx(){ // return functions
        return this.minx;
    }
    public int returnMiny(){
        return this.miny;
    }
    public double returnSep(){
        return this.seperator;
    }
    public myRectangle[] returnRecArr(){
        return this.rect2;
    }
    public void divide(){ // find a suitable scale, using the x and y ranges 
         this.seperator = (Math.abs(this.maxx-this.minx));
         
         if((Math.abs(this.maxy-this.miny))>this.seperator){
             this.seperator = Math.abs(this.maxy-this.miny);
             
         }  
         this.seperator = 900/this.seperator;
         

    }
    public void setColoring(){ // set random colors for each rectangle 
        this.recColors = new Color[(this.rect2).length];

        for(int i = 0; i<(this.rect2).length; i++){
        Random rand = new Random();
            float rd = rand.nextFloat();
            float gr = rand.nextFloat();
            float bl = rand.nextFloat();
           double a1 = 0.5;
           float a2 = (float)a1;
            Color randomColor = new Color(rd,gr,bl,a2);
            this.recColors[i] = randomColor;
        }
    }  
    public void paintComponent(Graphics g){
        Font customFont = new Font("Corbel", Font.PLAIN, 12);
        
        try {
            //create the font we're using
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts\\medium.otf")).deriveFont(12f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();//register the font
            ge.registerFont(customFont);
        } catch (IOException e) {
            e.printStackTrace();
        } catch(FontFormatException e) {
            e.printStackTrace();
        }
        // IMPORTANT:: here, we're drawing the x and y axis with respect to the rectangles themselves, the axis isn't in a fixed position and rather is translated relative to the positions of the x and y ranges
        g.setColor(Color.white);
        g.drawLine((int)(Math.round(Math.abs(0-this.minx)*this.seperator)), 0,(int)(Math.round(Math.abs(0-this.minx)*this.seperator)), 900); 
        g.drawLine(0,900-(int)(Math.round(Math.abs(0-this.miny)*this.seperator)), 900,900-(int)(Math.round(Math.abs(0-this.miny)*this.seperator)));
       
        // System.out.println(900-(int)(Math.round(Math.abs(0-this.miny)*this.seperator)));
        // System.out.println(this.minx);
        // System.out.println(this.seperator);
        g.setColor(Color.black);
        
      
        for(int i = 0; i<(this.rect2).length; i++){ // for loop to draw rectangles and write their dimensions 
            int[] holder = this.rect2[i].dimensions();
            int[] holder2 = this.rect3[i].dimensions();
            g.setColor(this.recColors[i]);
            g.fillRect((int)Math.round(Math.abs(0-this.minx)*this.seperator)+holder[0],900-((int)(Math.round(Math.abs(0-this.miny)*this.seperator))+holder[1])-holder[5], holder[4], holder[5]);

            g.setFont(customFont);
            g.setColor(Color.black);
            g.drawString("["+holder2[0]+","+holder2[1]+"]", (int)Math.round(Math.abs(0-this.minx)*this.seperator)+holder[0]-15,900-((int)(Math.round(Math.abs(0-this.miny)*this.seperator))+holder[1]-15));
            g.drawString(""+holder2[4], (int)Math.round(Math.abs(0-this.minx)*this.seperator)+holder[0]-15+holder[4]/2+(int)this.seperator/2,900-((int)(Math.round(Math.abs(0-this.miny)*this.seperator))+holder[1]-15)-holder[5]);
            g.drawString(""+holder2[5], (int)Math.round(Math.abs(   0-this.minx)*this.seperator)+holder[0]-15,900-((int)(Math.round(Math.abs(0-this.miny)*this.seperator))+holder[1]-15)-holder[5]/2);
            // System.out.println((int)Math.round(Math.abs(0-this.minx)*this.seperator)+holder[0]+" "+(900-(int)(Math.round(Math.abs(0-this.miny)*this.seperator))+holder[1])+" "+ holder[4]+" "+holder[5]);
            // System.out.println(this.rect3[0]+" "+this.rect3[1]);
        } 
      
        
       
        

        //draw every rectangle, and add any clicker mathingers 
        
    } 
    
}
class SelectPlane extends JLayeredPane{ // plane for rectangular buttons to select the rectangles 
    private myRectangle[] rect1;
    private int minx; 
    private int miny; 
    private double seperator;
    private RectButton[] buttonarray;
 

    public SelectPlane(myRectangle[] rect2, int x, int y, double sep){
        // set characteristics of the button plane
        setBounds(25,25,900,900);
        this.rect1 = rect2;
        this.minx = x;
        this.miny = y; 
        this.seperator = sep;
        this.buttonarray = new RectButton[rect2.length];
        setLayout(null);
        addAllRect();
    }
    public void addAllRect(){ // add a button for each rectangle that exists, in the same position 
        for(int i = 0; i<(this.rect1).length; i++){
         RectButton placehold = new RectButton(this.rect1[i], this.minx, this.miny, this.seperator);
         this.buttonarray[i] = placehold;
         add(placehold);
        //  System.out.print("button added");
         
    }}
       
        public RectButton[] returnArray(){ // return the array of buttons for reference elsewhere
            return this.buttonarray;
        }
public class RectButton extends JButton{ // custom button class that is invisible over each rectangle 
    private boolean active = false;
    public RectButton(myRectangle r1,int minx, int miny, double sep){
        
        int[] dim = r1.dimensions();
        setBounds((int)Math.round(Math.abs(0-minx)*sep)+dim[0],900-((int)(Math.round(Math.abs(0-miny)*sep))+dim[1])-dim[5], dim[4], dim[5]);
        setBackground(Color.white);
        setBorder(BorderFactory.createLineBorder(Color.white,2));
        setOpaque(false);
        setActive();
        setRolloverEnabled(false);
        setContentAreaFilled(false);
        
        
        
    }
    public boolean activate() { //return the state of the button (active or not)
        return this.active;
    }
    public void setActive(){
        
        addActionListener(new ActionListener() { // set the button to active when clicked 
            public void actionPerformed(ActionEvent e) {
                
                ((RectButton)e.getSource()).darken();
            
            } 
          });
    }
    public void darken(){ // add the border and activate the button when clicked when referenced in action listener
       
        this.active = !this.active;
        if(this.active){
        Color greyHold = new Color(178, 0, 0,255);
        setBorder(BorderFactory.createLineBorder(greyHold, 3));}
        else{
            setBorder(BorderFactory.createLineBorder(Color.white));
        }}
        
    }
    

}
