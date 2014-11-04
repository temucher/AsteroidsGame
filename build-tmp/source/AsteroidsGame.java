import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class AsteroidsGame extends PApplet {

//your variable declarations here
SpaceShip xWing = new SpaceShip();
Star[] galaxy = new Star[200];
boolean wIsPressed, aIsPressed, dIsPressed, sIsPressed;
public void setup() 
{
  size(700,700);
  for(int i = 0;i<galaxy.length;i++)
  {
    galaxy[i] = new Star();
  }
}
public void draw() 
{
  background(0);
  for(int i = 1;i<galaxy.length;i++)
  {
    galaxy[i].show();
  }
  xWing.show();
  if(wIsPressed == true) {xWing.accelerate(.2f);}
  if(aIsPressed == true) {xWing.rotate(-6);}
  if(dIsPressed == true) {xWing.rotate(6);}
  if(sIsPressed == true) {xWing.accelerate(-.2f);}
  xWing.move();
}
public void keyPressed()
{
  if(key == 'w') {wIsPressed = true;}//go forward
  if(key == 'a') {aIsPressed = true;}//turn left
  if(key == 'd') {dIsPressed = true;}//turn right
  if(key == 's') {sIsPressed = true;}//go backwards
  if(key == 'q')//"hyperspace"
  {
    xWing.setDirectionX(0);
    xWing.setDirectionY(0);
    xWing.setX((int)(Math.random()*700));
    xWing.setY((int)(Math.random()*700));   
  }
}
public void keyReleased()
{
  if(key == 'w') {wIsPressed = false;}//go forward
  if(key == 'a') {aIsPressed = false;}//turn left
  if(key == 'd') {dIsPressed = false;}//turn right
  if(key == 's') {sIsPressed = false;}
}
class SpaceShip extends Floater  
{   
    SpaceShip()
    {
      corners = 3;//draw the ship
      xCorners = new int [corners];
      yCorners = new int [corners];
      xCorners[0]=16;
      yCorners[0]=0;
      xCorners[1]=-8;
      yCorners[1]=-8;
      xCorners[2]=-8;
      yCorners[2]=8;
      myCenterX=350;//starting position
      myCenterY=350;
      myColor=255;
      myDirectionX=0;//direction its pointing
      myDirectionY=0;
      myPointDirection=-90;
    }
    public void setX(int x) {myCenterX = x;}
    public int getX() {return (int)myCenterX;}
    public void setY(int y) {myCenterY = y;}  
    public int getY() {return (int)myCenterY;}  
    public void setDirectionX(double x) {myDirectionX = x;}  
    public double getDirectionX() {return myDirectionX;}  
    public void setDirectionY(double y) {myDirectionY = y;}  
    public double getDirectionY() {return myDirectionY;}   
    public void setPointDirection(int degrees) {myPointDirection = degrees;}   
    public double getPointDirection() {return myPointDirection;} 

}
abstract class Floater //Do NOT modify the Floater class! Make changes in the SpaceShip class 
{   
  protected int corners;  //the number of corners, a triangular floater has 3   
  protected int[] xCorners;   
  protected int[] yCorners;   
  protected int myColor;   
  protected double myCenterX, myCenterY; //holds center coordinates   
  protected double myDirectionX, myDirectionY; //holds x and y coordinates of the vector for direction of travel   
  protected double myPointDirection; //holds current direction the ship is pointing in degrees    
  abstract public void setX(int x);  
  abstract public int getX();   
  abstract public void setY(int y);   
  abstract public int getY();   
  abstract public void setDirectionX(double x);   
  abstract public double getDirectionX();   
  abstract public void setDirectionY(double y);   
  abstract public double getDirectionY();   
  abstract public void setPointDirection(int degrees);   
  abstract public double getPointDirection(); 

  //Accelerates the floater in the direction it is pointing (myPointDirection)   
  public void accelerate (double dAmount)   
  {          
    //convert the current direction the floater is pointing to radians    
    double dRadians =myPointDirection*(Math.PI/180);     
    //change coordinates of direction of travel    
    myDirectionX += ((dAmount) * Math.cos(dRadians));    
    myDirectionY += ((dAmount) * Math.sin(dRadians));       
  }   
  public void rotate (int nDegreesOfRotation)   
  {     
    //rotates the floater by a given number of degrees    
    myPointDirection+=nDegreesOfRotation;   
  }   
  public void move ()   //move the floater in the current direction of travel
  {      
    //change the x and y coordinates by myDirectionX and myDirectionY       
    myCenterX += myDirectionX;    
    myCenterY += myDirectionY;     

    //wrap around screen    
    if(myCenterX >width)
    {     
      myCenterX = 0;    
    }    
    else if (myCenterX<0)
    {     
      myCenterX = width;    
    }    
    if(myCenterY >height)
    {    
      myCenterY = 0;    
    }   
    else if (myCenterY < 0)
    {     
      myCenterY = height;    
    }   
  }   
  public void show ()  //Draws the floater at the current position  
  {             
    fill(myColor);   
    stroke(myColor);    
    //convert degrees to radians for sin and cos         
    double dRadians = myPointDirection*(Math.PI/180);                 
    int xRotatedTranslated, yRotatedTranslated;    
    beginShape();         
    for(int nI = 0; nI < corners; nI++)    
    {     
      //rotate and translate the coordinates of the floater using current direction 
      xRotatedTranslated = (int)((xCorners[nI]* Math.cos(dRadians)) - (yCorners[nI] * Math.sin(dRadians))+myCenterX);     
      yRotatedTranslated = (int)((xCorners[nI]* Math.sin(dRadians)) + (yCorners[nI] * Math.cos(dRadians))+myCenterY);      
      vertex(xRotatedTranslated,yRotatedTranslated);    
    }   
    endShape(CLOSE);  
  }   
} 
class Star
{
  private int starX, starY;
  Star()
  {
    starX = (int)(Math.random()*700);
    starY = (int)(Math.random()*700);
  }
  public void show()
  {
    fill(255);
    noStroke();
    ellipse(starX, starY, 2, 2);
  }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "AsteroidsGame" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
