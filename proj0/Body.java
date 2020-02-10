
public class Body {
    /** the variable of Body */
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;


    /** common constructor of Body */
    public Body(double xP, double yP, double xV,
                double yV, double m, String img){
        xxPos=xP;
        yyPos=yP;
        xxVel=xV;
        yyVel=yV;
        mass=m;
        imgFileName=img;
    }

    /** second constructor of Body */
    public Body(Body b){
        xxPos=b.xxPos;
        yyPos=b.yyPos;
        xxVel=b.xxVel;
        yyVel=b.yyVel;
        mass=b.mass;
        imgFileName=b.imgFileName;
    }

    /** caculate the distance between two Bodies */
    public double calcDistance(Body b){
        double dx=xxPos-b.xxPos;
        double dy=yyPos-b.yyPos;
        return Math.sqrt(dx * dx + dy * dy);
    }

    /** caculate the force between two Bodies */
    public double calcForceExertedBy(Body b){
        double G = 6.67e-11;
        double r = calcDistance(b);
        double F = (G*mass*b.mass) / (r*r);
        return F;
    }

    /** caculate the Xforce by body b */
    public double calcForceExertedByX(Body b){
        double F = calcForceExertedBy(b);
        double dx = b.xxPos - xxPos;
        double r = calcDistance(b);
        double FX = F * dx / r;
        return FX;
    }

    /** caculate the Yforce by body b */
    public double calcForceExertedByY(Body b){
        double F = calcForceExertedBy(b);
        double dy = b.yyPos - yyPos;
        double r = calcDistance(b);
        double FY = F * dy / r;
        return FY;
    }

    /** caculate the Xnetforce by net given */
    public double calcNetForceExertedByX(Body [] net){
        double FNetX = 0;
        for(int i = 0; i<net.length; i++){
            if(net[i].equals(this)){
                continue;
            }
            else{
                FNetX+=calcForceExertedByX(net[i]);
            }
        }
        return FNetX;
    }

    /** caculate the Ynetforce by net given */
    public double calcNetForceExertedByY(Body [] net){
        double FNetY = 0;
        for(int i = 0; i<net.length; i++){
            if(net[i].equals(this)){
                continue;
            }
            else{
                FNetY+=calcForceExertedByY(net[i]);
            }
        }
        return FNetY;
    }

    /** update the changes of this planet */
    public void update(double dt, double fx, double fy){
        double ax = fx / mass;
        double ay = fy / mass;
        xxVel += ax * dt;
        yyVel += ay * dt;
        xxPos += xxVel * dt;
        yyPos += yyVel * dt;

    }

    /** draw the body */
    public void draw(){
        StdDraw.picture(xxPos, yyPos, "images/"+imgFileName);
    }

}
