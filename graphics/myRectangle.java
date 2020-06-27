package graphics;
 public class myRectangle{
	 private int left; 
	 private int bottom;
	 private int width; 
	 private int height;
	 private int top;
	 private int right;
	
	public myRectangle(){ // constructor empty
		this.left = 0;
		this.bottom = 0;
		this.width = 0;
		this.height = 0;
	}
	public myRectangle(int l, int b, int w, int h){ //constructor w paramaters
		
			this.left = l;
		
		
	
			this.bottom = b;
		
		
		if(w>0){
			this.width = w;
		}
		else{
			this.width = 0;
		}
		if(h>0){
			this.height = h;
		}
		else{
			this.height = 0;
		}
	}
	public void set(int l, int b, int w, int h){ // set variables
		this.left = l;
		this.bottom = b;
		if(w>0){
			this.width = w;
		}
		else{
			this.width = 0;
		}
		if(h>0){
			this.height = h;
		}
		else{
			this.height = 0;
		}
	}
	public String toString(){ // make rectangle's parameters a string
		 String s = "base: ("+this.left+","+this.bottom+") w:"+this.width+" h:"+this.height;
		return s;
	}
	public int area(){ // output area
		 int area1 = this.width*this.height; 
		return area1; 
	}
	public int[] dimensions(){ //return an array with the dimensions
		this.top = this.bottom+this.height;
		this.right = this.left+this.width;
		int[] dimen = {this.left, this.bottom, this.top, this.right,this.width,this.height};
		return dimen;
	}
	
	public int perimeter(){ // return perimeter
		int perim1 = 0;
		 if(this.width!=0&&this.height!=0){
		 	perim1+= 2*this.width + 2*this.height;
		 }
		 else{
		 	perim1+= this.width + this.height;
		 }
		return perim1;
	}
	public static int totalArea(myRectangle r1, myRectangle r2){ // return total area
		myRectangle holder = intersection(r1,r2);
		int totArea = r1.area()+r2.area()-holder.area(); // both areas combined, removing the intersection area
		return totArea;
	}
	
	public static myRectangle intersection(myRectangle r1, myRectangle r2){ // intersection rectangle
		 myRectangle finalrec = new myRectangle();
	
		r1.top = r1.bottom+r1.height; // define the top right corner coordinates to be used in calc 
		r2.top = r2.bottom+r2.height;
		r1.right = r1.left+r1.width;
		r2.right = r2.left+r2.width;
		int leftx = Math.max(r1.left, r2.left); // find the largest left coordinate
		int bottomy = Math.max(r1.bottom, r2.bottom); // ^^ but for bottom
		int rightx = Math.min(r1.right, r2.right);// smallest right coordinate
		int topy = Math.min(r1.top,r2.top); // ^^ but for top
		if(leftx>rightx){ // if the largest left is greater than the smallest right, no intersection
			finalrec.set(0,0,0,0);
		}
		else if(bottomy>topy){ // if the largest bottom is greater than smallest top, no intersection
			finalrec.set(0,0,0,0);
		}
		else{
			finalrec.set(leftx, bottomy, rightx-leftx, topy-bottomy); // set the dimensions of new rect
		}
		return finalrec;	
		
	}
	public static int totalPerimeter(myRectangle r1, myRectangle r2){ // find total perimeter
		myRectangle r3 = intersection(r1, r2);
		int totPerim =0;
		 if ( r1.width * r1.height > 0 && r2.width * r2.height > 0 && r3.width * r3.height == 0 || r1.width * r1.height == 0 && r2.width * r2.height == 0) { 
		 totPerim = (r1.perimeter() + r2.perimeter()) - r3.perimeter()*2;} 
		else{ 
			totPerim = (r1.perimeter() + r2.perimeter()) - r3.perimeter();
		}
		return totPerim;
	}
	public String quadrant(){ // returns quadrant the rectangle is in 
		String quadrants = "";
		this.top = this.bottom + this.height;
		this.right = this.left + this.width;
		
		if(this.top>0&&this.right>0){
			quadrants+="-Q1-";
		}
		if(this.top>0&&this.left<0){
			quadrants+="-Q2-";
		}
		if(this.bottom<0&&this.left<0){
			quadrants+="-Q3-";
		}
		if(this.bottom<0&&this.right>0){
			quadrants+="-Q4-";
		}
		return quadrants;
	}
	public String quadrant2(myRectangle rect2){ // returns quadrant of two rectangles 
		String quadrants = "";
		this.top = this.bottom + this.height;
		this.right = this.left + this.width;
		rect2.top = rect2.bottom + rect2.height;
		rect2.right = rect2.left + rect2.width;
		
		if((this.top>0&&this.right>0)||(rect2.top>0&&rect2.right>0)){
			quadrants+="-Q1-";
		}
		if((this.top>0&&this.left<0)||(rect2.top>0&&rect2.left<0)){
			quadrants+="-Q2-";
		}
		if((this.bottom<0&&this.left<0)||(rect2.bottom<0&&rect2.left<0)){
			quadrants+="-Q3-";
		}
		if((this.bottom<0&&this.right>0)||(rect2.bottom<0&&rect2.right>0)){
			quadrants+="-Q4-";
		}
		return quadrants;
	}
	public boolean contains(myRectangle r1){ // returns if the rectangle is contained by the current rectangle 
		boolean container = false;
		r1.top = r1.bottom+r1.height;
		this.top = this.bottom+this.height;
		r1.right = r1.left+r1.width;
		this.right = this.left+this.width;
		
		if(r1.bottom>=this.bottom&&r1.left>=this.left&&r1.top<=this.top&&r1.right<=this.right){
			container = true;
		}
		return container;
		
	}
	
}