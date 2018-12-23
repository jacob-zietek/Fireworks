ArrayList<Firework> firework = new ArrayList<Firework>();
float gravity;

void setup(){
  stroke(255);
  strokeWeight(4);
  background(55);
  fullScreen();
  gravity = 0.5;
}

void draw(){
  
  background(55);
  
  if(random(0, 1) > .9)
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
    vel = random(20, 32.5);
    x = (int)(random(0, width));
    y = height;
    exploded = false;
  }
  
  void update(){
    if(!exploded){
      y-=vel;
      vel-=gravity;
      if(vel <= 0){
        exploded = true;
        for(int i = 0; i < particles.length; i++){
          particles[i] = new Particle(x, y);
        }
      }
    }
  }
  
  void display(){
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
  
  boolean getRemoveState(){
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
   
   void show(){
    point(x, y); 
   }
   
   void update(){
     x+=velx;
     y-=vely*random(.8, 1.1);
     vely-=gravity*.2;
     a-=7;
   }
   
   int getR(){
     return r;
   }
   
   int getG(){
     return g;
   }
   
   int getB(){
     return b;
   }
   
   int getA(){
     return a;
   }
   
   Particle(int vx, int vy){
     x = vx;
     y = vy;
     velx = random(-3, 3) * random(.5, 1);
     vely = random(-3, 5) * random(.5, 1);
     r = (int)random(255);
     g = (int)random(255);
     b = (int)random(255);
     a = 255;
   }
}
