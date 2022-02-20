package me.yoursole.main.game;

import me.yoursole.main.game.gameValues.GameData;
import me.yoursole.main.resources.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class GameFrame extends JFrame {


    GameFrameMainPanel p;
    public GameFrame(){
        setupMainPanel();
    }

    private void setupMainPanel() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        p = new GameFrameMainPanel();
        this.add(p);
        this.pack();
        p.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));

        p.setBackground(Color.BLACK);
    }
}

class GameFrameMainPanel extends GamePanel{
    double compression = 1;
    public GameFrameMainPanel(){
        super(50, 0,0);


        if(!((double)(this.dimx)/(double)(this.dimy)==(double)(this.l.getDimxI())/(double)(this.l.getDimyI())) &&
                (this.l.getDimxI() > this.dimx || this.l.getDimyI() > this.dimy)){

            //if aspect ratio isn't correct for whatever reason

            if(this.l.getDimyI()>this.dimy){
                this.compression = (double) (this.dimy) / (double) (this.l.getDimyI());
            }else{ //why are you having an issue on the x direction??? Whatever ill fix it but cmon how does this happen
                this.compression = (double) (this.dimx) / (double) (this.l.getDimxI());
            }

            this.dimx *= this.compression;
            this.dimy *= this.compression;


            for(GameObject o : this.l.getObjects()){ //scale rectangles to match screensize
                if(o.getClass() == RectObject.class){
                    RectObject r = (RectObject) o;

                    r.setXys(
                            (int)(r.getXyLower()[0]*((double)this.dimx/(double)this.l.getDimxI())),
                            (int)(r.getXyLower()[1]*((double)this.dimy/(double)this.l.getDimyI())),
                            (int)(r.getXyUpper()[0]*((double)this.dimx/(double)this.l.getDimxI())),
                            (int)(r.getXyUpper()[1]*((double)this.dimy/(double)this.l.getDimyI()))
                    );
                }

            }
        }else{
            this.dimx = this.l.getDimxI();
            this.dimy = this.l.getDimyI();
        }
        this.setPreferredSize(new Dimension(this.dimx, this.dimy));


        super.startTick();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        super.dimx = this.dimx;
        super.dimy = this.dimy;

        Graphics2D g2 = (Graphics2D) g;


        for(GameObject o : this.l.getObjects()){
            if(o.getClass() == RectObject.class){
                RectObject r = (RectObject) o;

                if(r.isVisible()){
                    Rectangle2D.Double rect = new Rectangle2D.Double(r.getXyLower()[0],r.getXyLower()[1],r.getXyUpper()[0]-r.getXyLower()[0],r.getXyUpper()[1]-r.getXyLower()[1]);
                    BufferedImage b = null;

                    try {
                        b = ImageIO.read(r.getTexture());
                    } catch (IOException | IllegalArgumentException ignored) {}

                    Paint paint = b!=null?new TexturePaint(b, rect):new GradientPaint(0,0,Color.BLACK,1,1,r.isKills()?Color.RED:Color.BLUE);
                    g2.setPaint(paint);

                    g2.fill(rect);
                    g2.setPaint(null);
                    g2.setColor(Color.BLACK);

                }
            }

        }
        if(!this.isPaused){
            float dist = distance(this.rel.x, this.rel.y, GameData.p.getLocx(), GameData.p.getLocy());
            float red = (float) (-1/(1+Math.pow(Math.E,dist/50-10)))+1;
            float green = (float) (1/(1+Math.pow(Math.E,dist/25-10)));

            g2.setColor(new Color(red, green, .5f));
        }else{
            g2.setColor(Color.GREEN);
        }




        Ellipse2D.Double circle = new Ellipse2D.Double((int)(GameData.p.getLocx()-(GameData.p.getSize()/2)), (int)(GameData.p.getLocy()-(GameData.p.getSize()/2)), GameData.p.getSize(), GameData.p.getSize());
        g2.fill(circle);





        g2.setColor(Color.BLACK);

        g2.drawString(String.valueOf(this.levelTime/100f),5,15);


    }


    //LEVEL INFO --------------------------------------------------------
    private Level l = Levels.LEVEL1.getLevel(); //set to lvl 1


    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private int dimx = screenSize.width;
    private int dimy = screenSize.height;

    //Player size set to 50
    //playerSize is up in the constructor under the super call
    //LEVEL INFO --------------------------------------------------------

    private boolean isPaused = false;
    private boolean isPlaying = false;

    private long time = 0;
    private long levelTime = 0;

    Point rel = new Point();
    @Override
    public void onTick() {

        if(!this.isPaused)this.levelTime++;

        this.time++;

        this.repaint();


        GameData.p.setLocx((int) (GameData.p.getLocx()+GameData.p.getMovement().getX()));
        GameData.p.setLocy((int) (GameData.p.getLocy()+GameData.p.getMovement().getY()));

        Point mouse = MouseInfo.getPointerInfo().getLocation();

        Point window = new Point();
        try{
            window = this.getLocationOnScreen();
        }catch(IllegalComponentStateException ignored){}



        this.rel = new Point(mouse.x-window.x, mouse.y-window.y);
        double centerX = GameData.p.getLocx();
        double centerY = GameData.p.getLocy();

        //is cursor in circle
        float dist = distance(centerX, centerY, rel.getX(),rel.getY());
        if(dist < GameData.p.getSize()/2f){
            if(this.isPaused){
                this.isPaused = false;
            }

            if (isPlaying && this.l.isPlayerKills()){
                killPlayer();
                return;
            }


        }else if(isPaused){
            return;
        }else{
            isPlaying = true;
        }

        if(inWindow(rel)){
            applyMovement(rel);
        }else{
            killPlayer();
            return;
        }

        collideWithWall();

        for(GameObject r : this.l.getObjects()){
            if (collideWithObject(r)) return;
        }


    }

    private void applyMovement(Point rel) {
        float dx = GameData.p.getMovement().getX();
        float dy = GameData.p.getMovement().getY();

        dx += (distancex(GameData.p.getLocx(), GameData.p.getLocy(), rel.x, rel.y)/100);
        dy += (distancey(GameData.p.getLocx(), GameData.p.getLocy(), rel.x, rel.y)/100);

        dx/=1.1;
        dy/=1.1;

        GameData.p.getMovement().setX(dx);
        GameData.p.getMovement().setY(dy);
    }

    private boolean collideWithObject(GameObject r) {
        if(!r.compute()) //for backgrounds and sprites
            return false;

        ArrayList<Point> pointsInsidePlayer = GameData.p.getPointsInside();

        Vector playerVel = GameData.p.getMovement(),
                b = new Vector(0,0),
                bouncy = new Vector(r.getBouncyness(),r.getBouncyness()),
                centerOfPlayer = new Vector((float)GameData.p.getLocx(),(float)GameData.p.getLocy());

        int amountOfPointsInPlayerAndObject = 0;
        ArrayList<Point> pointsInPlayerAndObject = new ArrayList<>();
        for(Point p : pointsInsidePlayer){
            if(r.insideObject(p)){ //point is inside both the player and the object in question
                Vector pointCenter = Vector.subtract(centerOfPlayer, new Vector(p.x,p.y));
                b.add(pointCenter);
                amountOfPointsInPlayerAndObject++;
                pointsInPlayerAndObject.add(p);
            }
        }

        if(amountOfPointsInPlayerAndObject!=0){
            if(r.isCollidable()){

                //player location

                float minDist = Integer.MAX_VALUE;

                for(Point p : pointsInPlayerAndObject){
                    float d = distanceSquared(p.x,p.y,GameData.p.getLocx(),GameData.p.getLocy());
                    if(d<minDist){
                        minDist = d;
                    }
                }
                minDist = (float) Math.sqrt(minDist);
                minDist = (float) ((GameData.p.getSize()/2f - minDist)*2);

                b.normalize();

                b.multElements(new Vector(minDist,minDist));


                Vector playerLoc = new Vector((float)GameData.p.getLocx(), (float)GameData.p.getLocy());
                playerLoc.add(b);

                GameData.p.setLocx((int)(playerLoc.getX()));
                GameData.p.setLocy((int)(playerLoc.getY()));
                b.normalize();
                //player velocity
                b.makeNeg();

                playerVel.multElements(b);
                playerVel.multElements(bouncy);


                GameData.p.setMovement(playerVel);


            }
            boxFunctions(r);
        }




        return false;
    }

    private boolean boxFunctions(GameObject r) {
        if (r.isWinner()) {
            this.isPaused = true;
            GameData.p.getMovement().setX(0);
            GameData.p.getMovement().setY(0);

            if(enumContains("LEVEL"+(this.l.getStage()+1))){
                this.l = Levels.valueOf("LEVEL"+(this.l.getStage()+1)).getLevel();
                killPlayer();
                return true;
            }
        }
        if (r.getSizeSet() != -1) {
            GameData.p.setSize((int) (r.getSizeSet() * this.compression));
            GameData.p.resetSavedPoints(); //recalc basis vectors for all points inside circle
        }
        if (r.isKills()) {
            killPlayer();
            return true;
        }
        r.runFunc();
        return false;
    }

    private void collideWithWall() {
        float halfSize = GameData.p.getSize()/2f;
        if(GameData.p.getLocx()+halfSize>=this.dimx || GameData.p.getLocx() - halfSize < 0){ //side wall
            GameData.p.getMovement().setX(GameData.p.getMovement().getX()*-1);
            GameData.p.setLocx(GameData.p.getLocx()<this.dimx/2f? (int) (GameData.p.getSize() / 2f+(this.compression)) : (int) (this.dimx - GameData.p.getSize() / 2f-(this.compression)));
        }

        if(GameData.p.getLocy()+halfSize>=this.dimy || GameData.p.getLocy() - halfSize < 0){ //top/bottom wall
            GameData.p.getMovement().setY(GameData.p.getMovement().getY()*-1);
            GameData.p.setLocy((int) (GameData.p.getLocy()<this.dimy/2f?GameData.p.getSize()/2f+(this.compression):this.dimy-GameData.p.getSize()/2f-(this.compression)));
        }
    }


    private void killPlayer(){
        GameData.p.setLocx(this.l.getRespawn().x);
        GameData.p.setLocy(this.l.getRespawn().y);
        GameData.p.getMovement().setX(0);
        GameData.p.getMovement().setY(0);
        this.isPaused = true;
        this.isPlaying = false;
        this.levelTime = 0;

        GameData.p.setSize((int) (GameData.p.getDefaultSize() * this.compression));
    }

    private static boolean enumContains(String input) {
        for (Levels r : Levels.values()) {
            if (r.name().equalsIgnoreCase(input)) {
                return true;
            }
        }
        return false;
    }

    private float distance(double x, double y, double x2, double y2){
        return (float) Math.sqrt(Math.pow(y2-y,2)+Math.pow(x2-x,2));
    }

    private float distanceSquared(double x, double y, double x2, double y2){
        return (float) ((float) Math.pow(y2-y,2)+Math.pow(x2-x,2));
    }

    private float distancex(double x, double y, double x2, double y2){
        return (float) (x2-x);
    }
    private float distancey(double x, double y, double x2, double y2){
        return (float) (y2-y);
    }

    private boolean inWindow(Point p){
        return (p.getX()>=0 && p.getX()<this.dimx)&&(p.getY()>=0 && p.getY()<this.dimy);
    }
}
