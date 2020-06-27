package graphics; // imports
import javax.swing.*;
import graphics.SelectPlane.RectButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
public class GuiWin extends JFrame{ // config window class
    private Header header1;
    private ConfigPanel configpanel1;
    private RectButton[] buttonarray;
    private myRectangle[] rectarray;
    

    public GuiWin(myRectangle[] rectarray1, RectButton[] buttonarray1){ 
        // set characteristics and initialize sub classes 
        this.buttonarray = buttonarray1;
        setSize(500, 500);
        setTitle("Config Window");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.header1 = new Header("Rectangle Config");
        add(this.header1);
        this.configpanel1 = new ConfigPanel(rectarray1, buttonarray1, (this.header1).returnFont());
        this.configpanel1.ensureActive();
        this.configpanel1.revalidate();
        add(this.configpanel1);
        setLayout(null);
        setResizable(false);

    }
    public void visible(boolean b){ // set visible
        setVisible(b);
    }
    public void updateArray(RectButton[] buttonarray1, myRectangle[] rectarray1){ // update the class with the arrays from main, and the drawing plane
        this.buttonarray = buttonarray1;
        this.rectarray = rectarray1;
    }
    public void updateAll(){ //update the panel with the rectangle attributes with the button array and the rectangle array continuously 
        this.configpanel1.updateArray(this.buttonarray,this.rectarray);
        repaint();
        this.configpanel1.ensureActive();
        this.configpanel1.revalidate();
        
    }
    public myRectangle returnConf(){ // return the user entered rectangle attributes 
        return this.configpanel1.returnRect();
    }
    
}
class Header extends JPanel{ // header class for the title on the config window
    private Font customFont;
    public Header(String s){
        setBounds(0,0,500,50);
        setOpaque(true);
    }
    
    public void paintComponent(Graphics g5){ // paint the title 
         this.customFont = new Font("Corbel", Font.PLAIN, 28);
         
        
        try { 
            this.customFont = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts\\medium.otf")).deriveFont(28f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(this.customFont);
        } catch (IOException e) {
            e.printStackTrace();
        } catch(FontFormatException e) {
            e.printStackTrace();
        }
        
        g5.setColor(Color.DARK_GRAY);
        g5.fillRect(0, 0, getWidth(), getHeight());
        g5.setColor(Color.WHITE);
        g5.setFont(this.customFont);
        g5.drawString("Rect", 180, 30);
        g5.setColor(Color.red);
        g5.drawString("Utility", 250, 30);
    }
    public Font returnFont(){
        return this.customFont;
    }

}
class ConfigPanel extends JPanel{ // panel of attributes from the rectangles selected
    private myRectangle[] rectarray;
    private RectButton[] buttonarray;
    private boolean oneActive = false;
    private boolean twoActive = false;
    private boolean moreActive = false;
    private myRectangle rect1;
    private myRectangle rect2;
    private  JTextField x; 
    private  JTextField y;
    private  JTextField w;
    private  JTextField h;
    private customButton cButton;


    Font customFont;
    Font customFont2;
    Graphics g1;
    public ConfigPanel(myRectangle[] rectarray1, RectButton[] buttonarray1, Font customFont1){
        // set attributes and initialize subclasses 
        this.rectarray = rectarray1;
        this.buttonarray = buttonarray1;
        this.customFont = customFont1;
        setBounds(0, 50, 500, 450);
        setOpaque(true);
        setLayout(null);
        ensureActive();
        editables(true);
        // create an "update rectangle" button
        customButton cButton = new customButton("Update");
            cButton.addActionListener(new ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                cButton.input();
              }
            });
            this.cButton = cButton;
            add(this.cButton);
        

    }
    public myRectangle returnRect(){ // return the custom rectangle entered by user 
        if(this.cButton.rBool()){
        this.cButton.reverse();
        return (this.cButton).rRect();}
        else {
            return null;
        }


    }
    public void ensureActive(){ // ensure how many rectangles are active 
        int activecount = 0;
        for(int i = 0; i<this.buttonarray.length;i++){
          
            if(this.buttonarray[i]!=null){
                if(this.buttonarray[i].activate()){
                    activecount++;

                }
            }
        }
  
        if(activecount==2){
            this.oneActive = false;
            this.twoActive = true;
             this.moreActive = false;
             
        }
        else if(activecount==1){
            oneActive = true;
            twoActive = false;
            moreActive = false;
            

        }
        else{
            oneActive = false;
            twoActive = false;
            moreActive = true; 
        }
    }
    public void updateArray(RectButton[] rectb1, myRectangle[] rect1){ // update the arrays in the class, and also update the custom rectangle button with the text in the text fields for user entry 
        this.buttonarray = rectb1;
        this.rectarray = rect1;
        (this.cButton).set(this.x.getText(), this.y.getText(), this.w.getText(), this.h.getText());
    }
    public void paintComponent(Graphics g6){ // paint the rectangle characteristics based on how many rectangles are selected 
        try { 
            this.customFont2 = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts\\medium.otf")).deriveFont(20f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(this.customFont2);
        } catch (IOException e) {
            e.printStackTrace();
        } catch(FontFormatException e) {
            e.printStackTrace();
        }   
        
        if(this.moreActive)  {  //if not 1 and not 2 are selected 
            
            
            g6.setColor(Color.DARK_GRAY);
            g6.setFont(this.customFont2);
            g6.drawString("Please select either 1 or 2 rectangles only", 20, 25);
            this.rect1 = null;
            this.rect2 = null;
            g6.setColor(Color.black);
            g6.drawRect(200, 255, 100, 100);
            g6.drawRect(205, 260, 90, 90);
            

        }
        else if(this.oneActive){ // if only one is selected
            
          
            g6.setColor(Color.DARK_GRAY);
            g6.setFont(this.customFont2);
            for(int i = 0; i<this.buttonarray.length;i++){
                
                if(this.buttonarray[i]!=null){
                    if(this.buttonarray[i].activate()){
                        this.rect1 = this.rectarray[i];
    
                    }
                }
            }
            this.rect2 = null;
            g6.setColor(Color.black);
            g6.fillRect(0, 0, 500, 30);
            g6.setColor(Color.LIGHT_GRAY);
            g6.drawString("Rectangle Attributes", 10, 20);
            g6.drawLine(7, 22, 230, 22);
            g6.drawLine(7, 25, 230, 25);
            g6.setColor(Color.DARK_GRAY);
            g6.fillRect(0, 30, 500, 30);
            g6.setColor(Color.WHITE);
            g6.drawString("Perimeter: "+(this.rect1).perimeter(), 10, 50);
            g6.setColor(Color.gray);
            g6.fillRect(0, 60, 500, 30);
            g6.setColor(Color.WHITE);
            g6.drawString("Area: "+(this.rect1).area(), 10, 80);
            g6.setColor(Color.DARK_GRAY);
            g6.fillRect(0, 90, 500, 30);
            g6.setColor(Color.WHITE);
            g6.drawString("Quadrants: "+(this.rect1).quadrant() , 10, 110);
            g6.setColor(Color.gray);
            g6.fillRect(0, 120, 500, 30);
            g6.setColor(Color.WHITE);
            g6.drawString("Bottom Left: ("+((this.rect1).dimensions())[0]+","+((this.rect1).dimensions())[1]+")", 10, 140);
            g6.setColor(Color.DARK_GRAY);
            g6.fillRect(0, 150, 500, 30);
            g6.setColor(Color.WHITE);
            g6.drawString("Top Right: ("+((this.rect1).dimensions())[3]+","+((this.rect1).dimensions())[2]+")", 10, 170);
            g6.setColor(Color.black);
            g6.drawRect(200, 255, 100, 100);
            g6.drawRect(205, 260, 90, 90);
              

        }
        else{ // if 2 are selected
           
            int holder = 0;
            g6.setColor(Color.DARK_GRAY);
            g6.setFont(this.customFont2);
            for(int i = 0; i<this.buttonarray.length;i++){
          
                if(this.buttonarray[i]!=null){
                    if(this.buttonarray[i].activate()){
                        this.rect1 = this.rectarray[i];
                        holder = i;

    
                    }
                }
            }
            for(int i = 0; i<this.buttonarray.length;i++){
          
                if(this.buttonarray[i]!=null&&i!=holder){
                    if(this.buttonarray[i].activate()){
                        this.rect2 = this.rectarray[i];
                        

    
                    }
                }
            }
            
            g6.setColor(Color.black);
            g6.fillRect(0, 0, 500, 30);
            g6.setColor(Color.LIGHT_GRAY);
            g6.drawString("Shared Attributes", 10, 20);
            g6.drawLine(7, 22, 230, 22);
            g6.drawLine(7, 25, 230, 25);
            g6.setColor(Color.DARK_GRAY);
            g6.fillRect(0, 30, 500, 30);
            g6.setColor(Color.WHITE);
            g6.drawString("Intersection: "+myRectangle.intersection(this.rect1, this.rect2).toString(), 10, 50);
            g6.setColor(Color.gray);
            g6.fillRect(0, 60, 500, 30);
            g6.setColor(Color.WHITE);
            g6.drawString("Total Perimeter: "+myRectangle.totalPerimeter(this.rect1, this.rect2), 10, 80);
            g6.setColor(Color.DARK_GRAY);
            g6.fillRect(0, 90, 500, 30);
            g6.setColor(Color.WHITE);
            g6.drawString("Quadrants: "+(this.rect1).quadrant2(this.rect2) , 10, 110);
            g6.setColor(Color.gray);
            g6.fillRect(0, 120, 500, 30);
            g6.setColor(Color.WHITE);
            g6.drawString("Total Area: "+ myRectangle.totalArea(this.rect1, this.rect2), 10, 140);
            g6.setColor(Color.DARK_GRAY);
            g6.fillRect(0, 150, 500, 30);
            g6.setColor(Color.WHITE);
            g6.drawString("Contained?  "+ this.rect1.contains(this.rect2), 10, 170);
            g6.setColor(Color.black);
            g6.drawRect(200, 255, 100, 100);
            g6.drawRect(205, 260, 90, 90);
            

        }
       
        this.g1 = g6;
    }
    public void editables(boolean n){ // initialize editable JTextFields 
        if(n){
        this.x = new JTextField("x-pos", 6);
        (this.x).setBounds(120, 230, 60, 20);
        this.y = new JTextField("y-pos", 6);
        (this.y).setBounds(190, 230, 60, 20);
        this.w = new JTextField("width", 6);
        (this.w).setBounds(260, 230, 60, 20);
        this.h = new JTextField("height", 6);
        (this.h).setBounds(330, 230, 60, 20);
        add(this.x);
            add(this.y);
            add(this.w);
            add(this.h);
        }
        else{
            remove(this.h);
            remove(this.w);
            remove(this.x);
            remove(this.y);
            revalidate();
            
        }
        

    }


}
class customButton extends JButton{ // custom update button - this is an inefficient way of doing this but simpler to implement in a project of this scale 
    private String x2;
    private String y2;
    private String w2;
    private String h2;
    private myRectangle r2;
    private boolean activated;
    private Font customFont;
    public customButton(String s){
        // set attributes
        setBackground(Color.DARK_GRAY);
        setForeground(Color.red);
        
        this.customFont = new Font("Corbel", Font.PLAIN, 28);
         
        
        try { 
            this.customFont = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts\\medium.otf")).deriveFont(14f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(this.customFont);
        } catch (IOException e) {
            e.printStackTrace();
        } catch(FontFormatException e) {
            e.printStackTrace();
        }
        setFont(this.customFont);
        setFocusPainted(false);
        setText(s);
        setBounds(200,355,100,40);
        
        
        
    }
    public void input (){ // create a rectangle based off of user input 
        int newx = Integer.parseInt((this.x2));
        int newy = Integer.parseInt((this.y2));
        int neww = Integer.parseInt((this.w2));
        int newh = Integer.parseInt((this.h2));
        this.r2 = new myRectangle(newx, newy, neww, newh);
        activated = true;

    }
    public void set(String x1,String y1,String w1,String h1){ // continuously set the text fields in parent frame to the private ones in this class, to retain user input
        this.x2 = x1;
        this.y2 = y1;
        this.w2 = w1;
        this.h2 = h1;
    }
    public myRectangle rRect(){ // various utility functins to handle button activation and returns
        return this.r2;
    }
    public boolean rBool(){
        return this.activated;
    }
    public void reverse(){
        this.activated = !this.activated;
    }
    
}