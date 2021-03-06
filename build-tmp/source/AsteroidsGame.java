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
public boolean touch = false;
public int bulletCount = 0;
public boolean left =true;
SpaceShip xWing = new SpaceShip();
Star[] galaxy = new Star[500];
ArrayList <Asteroid> field = new ArrayList <Asteroid>();
ArrayList <Bullet> bang = new ArrayList <Bullet>();
boolean wIsPressed, aIsPressed, dIsPressed, sIsPressed, spaceIsPressed;
public void setup() 
{
  size(700,700);
  for(int i = 0;i<galaxy.length;i++)
  {
    galaxy[i] = new Star();
  }
  for(int i = 0;i<=15;i++)
  {
    field.add(i, new Asteroid());
  }
}
public void draw() 
{
  background(0);
  if(spaceIsPressed == true && bulletCount<25)
  {
    bang.add(new Bullet(xWing));
    bulletCount++;
  }
  for(int i = 0;i<bang.size();i++) 
  {
    bang.get(i).show();
    bang.get(i).move();
    if(((bang.get(i).getX()) > 700) || ((bang.get(i).getX() < 0))) {
      bang.remove(i);
      bulletCount--;
    }
    else if(((bang.get(i).getY()) > 700) || ((bang.get(i).getY() < 0))) {
      bang.remove(i);
      bulletCount--;
    }
  }
  for(int i = 0;i<galaxy.length;i++)
  {
    galaxy[i].show();
  }
  for(int i = 0;i<field.size();i++)
  {
    field.get(i).show();
    field.get(i).move();
    field.get(i).isTouchingShip(); 
    if(touch == true)
    {
      field.remove(i);
      touch = false;
      break;
    }
    for(int z = 0; z<bang.size();z++)
    {
      //checking to see if the bullets are touching the asteroids
      if((dist((float)field.get(i).getX(),(float)field.get(i).getY(), (float)bang.get(z).getX(), (float)bang.get(z).getY())) < 5)
      { 
        field.remove(i);
        bang.remove(z);
        bulletCount--;
        break;
      }
    }
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
  if(key == ' ') {spaceIsPressed = true;}
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
  if(key == ' ') {spaceIsPressed = false;}
  if(key == 'w') {wIsPressed = false;}//go forward
  if(key == 'a') {aIsPressed = false;}//turn left
  if(key == 'd') {dIsPressed = false;}//turn right
  if(key == 's') {sIsPressed = false;}
}
class Bullet extends Floater
{
  public Bullet(SpaceShip nutella)
  {
    myCenterX = nutella.myCenterX;
    myPointDirection = nutella.myPointDirection;
    double dRadians = myPointDirection*(Math.PI/180);
    // if(left == true) {
    //   myCenterX = (nutella.myCenterX-dRadians);
    //   left = !left;
    // }
    // else if(left ==false) {
    //   myCenterX = (nutella.myCenterX+dRadians);
    //   left = !left;
    // }
    myCenterY = nutella.myCenterY;
    myDirectionX = 5*Math.cos(dRadians)+nutella.myDirectionX;
    myDirectionY = 5*Math.sin(dRadians)+nutella.myDirectionY;
  }
  public void move()
  {    
    myCenterX += myDirectionX;    
    myCenterY += myDirectionY;   
  }
  public void show()
  {
    noFill();
    stroke(130);
    ellipse((float)myCenterX, (float)myCenterY,(float)5,(float)5);
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
class SpaceShip extends Floater  
{   
    public SpaceShip()
    {
      int[] xS = {16, 8, 13, 13, 7, 0, -6, -6, -10, -10, -6, -3, -6, -10, -10, -6, -6,  0, 7, 13, 13, 8, 16};
      int[] yS = {0, 5, 5, 6, 6, 10, 15, 10, 9, 6, 5, 0, -5, -6, -9, -10, -15, -10, -6, -6, -5, -5, 0};
      corners = xS.length;
      xCorners = xS;
      yCorners = yS;

      myCenterX=350;//starting position
      myCenterY=350;
      myColor=255;
      myDirectionX=0;
      myDirectionY=0;
      myPointDirection=-90;//direction its pointing
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
  public Star()
  {
    starX = (int)(Math.random()*700);
    starY = (int)(Math.random()*700);
  }
  public void show()
  {
    fill(255);
    noStroke();
    ellipse(starX, starY, 1, 1);
  }
}
class Asteroid extends Floater
{
  private int rotSpeed;
  public Asteroid()
  {
    corners = 6;
    int[] aXs = {((int)(Math.random()*4)-11), ((int)(Math.random()*4)+7), ((int)(Math.random()*4)+13), ((int)(Math.random()*4)+6), ((int)(Math.random()*4)-11), ((int)(Math.random()*4)-15)};
    int[] aYs = {((int)(Math.random()*4)-8), ((int)(Math.random()*4)-8), ((int)(Math.random()*4)), ((int)(Math.random()*4)+10), ((int)(Math.random()*4)+8), ((int)(Math.random()*4))};
    xCorners = aXs;
    yCorners = aYs;
    //add in myDirectionX and myDirectionY in order to move
    myDirectionY = ((int)(Math.random()*5)-2);
    myDirectionX = ((int)(Math.random()*5)-2);
    myCenterX = ((int)(Math.random()*700));
    myCenterY = ((int)(Math.random()*700));
    rotSpeed = ((int)(Math.random()*10));
    myColor = 105;
  }
  public void move()
  {
    rotate(rotSpeed);
    super.move();
  }
  public boolean isTouchingShip()
  {
    if ((dist((float)myCenterX,(float)myCenterY, (float)xWing.getX(), (float)xWing.getY())) < 20) {return touch = true;}
    else {return touch;}
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
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "AsteroidsGame" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
