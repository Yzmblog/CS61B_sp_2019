

public class NBody {
    /** read the Radius given in a file */
    public static double readRadius(String file){
        In in = new In(file);
        double Radius = in.readInt();
        Radius = in.readDouble();
        return Radius;
    }

    /** read Bodies given in a file */
    public static Body[] readBodies(String file){
        In in = new In(file);
        int numbers = in.readInt();
        Body[] Bodies = new Body[numbers];
        in.readDouble();
        for(int i = 0; i<numbers; i++){
            Bodies[i] = new Body(in.readDouble(), in.readDouble(),
                    in.readDouble(), in.readDouble(), in.readDouble(), in.readString());

        }
        return Bodies;
    }

    /** main function */
    public static void main(String[] args){
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double Radius = readRadius(filename);
        Body[] Bodies = readBodies(filename);


        StdDraw.enableDoubleBuffering();

        String img_path = "images/starfield.jpg";
        StdDraw.setScale(-Radius, Radius);
        StdDraw.picture(0, 0, img_path);

        for(int i=0 ; i<Bodies.length; i++){
            Bodies[i].draw();
        }

        for(double time = 0; time<T; time+=dt){
            double[] xForces = new double[Bodies.length];
            double[] yForces = new double[Bodies.length];

            for(int i=0; i<Bodies.length; i++) {
                xForces[i] = Bodies[i].calcNetForceExertedByX(Bodies);
                yForces[i] = Bodies[i].calcNetForceExertedByY(Bodies);
            }

            for(int i=0; i<Bodies.length; i++){
                Bodies[i].update(dt, xForces[i], yForces[i]);

            }
            StdDraw.picture(0, 0, img_path);

            for(int i=0 ; i<Bodies.length; i++){
                Bodies[i].draw();
            }

            StdDraw.show();

            StdDraw.pause(10);


    }
    /** print Bodies information */
    StdOut.printf("%d\n", Bodies.length);
    StdOut.printf("%.2e\n", Radius);
    for (int i = 0; i < Bodies.length; i++) {
        StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                Bodies[i].xxPos, Bodies[i].yyPos, Bodies[i].xxVel,
                Bodies[i].yyVel, Bodies[i].mass, Bodies[i].imgFileName);
    }

}
}
