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

public class fireworks extends PApplet {

ArrayList<Firework> firework = new ArrayList<Firework>();
float gravity;

public void setup(){
  stroke(255);
  strokeWeight(4);
  background(55);
  
  gravity = 0.5f;
}

public void draw(){
  
  background(55);
  
  if(random(0, 1) > .9f)
    firework.add(new Firework());
    
  boolean [] removeFirework = new boolean [firework.size()];
  
  int x = 0;
  for(Firework i: firework){
    i.display();
    i.update();
    removeFirework[x] = i.getRemoveState();
    stroke(255);
    x++;
  }
  
  for(int i = 0; i < removeFirework.length; i++){
    if(removeFirework[i])
      firework.remove(i);
  }
}

class Firework{
  
  private float vel;
  private int x;
  private int y;
  private boolean exploded;
  private Particle particles [] = new Particle[75];
  
  Firework(){
    vel = random(20, 32.5f);
    x = (int)(random(0, width));
    y = height;
    exploded = false;
  }
  
  public void update(){
    if(!exploded){
      y-=vel;
      vel-=gravity;
      if(vel <= 0){
        exploded = true;
        for(int i = 0; i < particles.length; i++){
          particles[i] = new Particle();
          particles[i].setParticle(x, y);
        }
      }
    }
  }
  
  public void display(){
    if(!exploded)
      point(x, y);
    if(exploded){
      stroke(particles[0].getR(), particles[0].getG(), particles[0].getB(), particles[0].getA());
      for(Particle i: particles){
        i.show();
        i.update();
      } 
    }
  }
  
  public boolean getRemoveState(){
    if(particles[0] != null)
      return particles[0].getA() < 0;
    return false;
  }
}

class Particle{
   private int x;
   private int y;
   private float velx;
   private float vely;
   private int r;
   private int g;
   private int b;
   private int a;
   
   public void setParticle(int vx, int vy){
     x = vx;
     y = vy;
     velx = random(-3, 3) * random(.5f, 1);
     vely = random(-3, 5) * random(.5f, 1);
     r = (int)random(255);
     g = (int)random(255);
     b = (int)random(255);
     a = 255;
   }
   
   public void show(){
    point(x, y); 
   }
   
   public void update(){
     x+=velx;
     y-=vely*random(.8f, 1.1f);
     vely-=gravity*.2f;
     a-=7;
   }
   
   public int getR(){
     return r;
   }
   
   public int getG(){
     return g;
   }
   
   public int getB(){
     return b;
   }
   
   public int getA(){
     return a;
   }
   
   Particle(){
     x = 0;
     y = 0;
     velx = 0;
     vely = 0;
   }
}
  public void settings() {  fullScreen(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#666666", "--stop-color=#cccccc", "fireworks" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
