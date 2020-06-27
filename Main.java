//RECTANGLE ASSIGNMENT GRAPHICS PORTION
// JUNE 5 2020 - AYAN HAFEEZ
// MS KUTCHSKE'S ICS3U CLASS
import graphics.*; //importing package from file system to reference other classes
class Main {
  public static void main(String[] args) {
    initialize(); // initialize / setup
    }
    public static void initialize(){
    // initializing sample rectangles, and array variables 
    myRectangle r1 = new myRectangle(0,0,5,5);
    myRectangle r2 = new myRectangle(5,0,5,5); 
    myRectangle r3 = new myRectangle(-16,-18,2,2);
    myRectangle r4 = new myRectangle(-24,-20,20,20);
    myRectangle r5 = new myRectangle(-10,2,4,4);

    int number = 5;
    myRectangle[] recter = new myRectangle[number];
    myRectangle[] recter2 = {};
    recter[0] = r1;
    recter[1] = r2;
    recter[2] = r3; 
    recter[3] = r4;
    recter[4] = r5;
    Window mainframe = new Window(recter); //initialize main window
    mainframe.visible(true); 
    System.out.println(myRectangle.totalPerimeter(r1,r2));
    GuiWin maingui = new GuiWin(recter, mainframe.returnArray()); // initialize config gui
    
    maingui.visible(true);
    loop(recter, recter2, mainframe, maingui, number); //begin feedback loop
    }

    public static void loop(myRectangle[] recter, myRectangle[] recter2, Window mainframe, GuiWin maingui, int number){
      while(true) { 
        mainframe.startUpdate(); // update main window
        maingui.updateAll(); // update config gui
        maingui.updateArray(mainframe.returnArray(),recter); //feed rectangle array to config gui
        
        myRectangle rnew = (maingui.returnConf());
        if(rnew!=null){ // if the custom rectangle entered by user isn't null, feed it to the array
          recter2 = new myRectangle[number];
          for(int i=0; i<recter.length; i++){
            recter2[i] = recter[i];
          }
          number++;
          recter = new myRectangle[number];
          recter[number-1] = rnew;
          for(int i=0; i<recter2.length; i++){
            recter[i] = recter2[i];
          }
          
          mainframe.rectUpdate(recter); //update the rectangle array passed to main window
    
        }
        
    
        }

    }
    
   
   
    
        
  }
