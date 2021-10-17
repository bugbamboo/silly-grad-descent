import java.util.Arrays;
import java.util.PriorityQueue;

public class gradescent {
    public static void main(String[] args) {
        double courtsize = 5;
        double x = 1.5;
        double y= 1.5;
        double size = 0.23;
        robot robo = new robot(courtsize,x,y, size);
        robo.rotate(0.7);
        double heading = robo.heading;
        System.out.println(Arrays.toString(robo.query()));
        System.out.println(robo.x+" "+robo.y+" "+robo.heading);
        double minx = x-0.3;

        double miny = y-0.3;
        double minang = robo.heading-1.36;
        double[] readings = robo.query();
        double[] best = new double[4];
        best[3]=Double.MAX_VALUE;
        int runs = 20;
        for(int i=0; i<runs; i++){
            for(int j=0; j<runs; j++){
                for(int k=0; k<runs; k++){
                    double xguess = minx+0.6*(i/(runs+0.0));
                    double yguess = miny+0.6*(j/(runs+0.0));
                    double angguess = minang+2.792*(k/(runs+0.0));
                    if(angguess>Math.PI*2){
                        angguess-=Math.PI*2;
                    }if(angguess<0){
                        angguess+=Math.PI*2;
                    }
                    double[] value =gradient(100,0.4,0.998,xguess,yguess,angguess,x,y,heading,readings,robo);
                    if(value.length==4){
                    if(value[3]<best[3]){
                        best=value;
                    }}
                }
            }
        }
        System.out.println(Arrays.toString(best));
        robo = new robot(courtsize,best[0],best[1],size);
        robo.rotate(Math.PI*1.5+best[2]);
        System.out.println(Arrays.toString(robo.query()));
        System.out.println(robo.x+" "+robo.y+" "+robo.heading);
        robo = new robot(courtsize,0,0,size);
        System.out.println(loss(true,best[0],best[1],readings,best[2],robo));



        //1.396 rad
        //0.3 m


    }
    public static double[] gradient(int steps, double lr, double decay,double startx, double starty, double startang, double pastx, double pasty, double pastang, double[] measurement, robot robo ){
        double x = startx;
        double y = starty;
        double ang = startang;
        for(int i=0; i<steps; i++){
            double currentLoss = loss(false,x,y,measurement,ang, robo);
            double delta =0.0001;
            //Step 1: approximate the 3 partial derivativesloss(x,y,measurement,ang, robo);
            double xpartial = (loss(false,x+delta,y,measurement,ang, robo)-currentLoss)/delta;
            double ypartial = (loss(false,x,y+delta,measurement,ang, robo)-currentLoss)/delta;
            double angpartial = (loss(false,x,y,measurement,ang+delta, robo)-currentLoss)/delta;

            //Step 2: update rule
            x=x+lr*xpartial*currentLoss;
            y=y+lr*ypartial*currentLoss;
            ang=ang+lr*angpartial*currentLoss;
            if(ang<0){
                ang+=2*Math.PI;
            }
            if(ang >Math.PI*2){
                ang -=Math.PI*2;
            }

            //Step 3: LR decay
            lr*=decay;
        }
        if(Math.abs(x-pastx)>0.4){
            return new double[]{-10,-10,-10,-10,-10};
        }
        if(Math.abs(y-pasty)>0.4){
            return new double[]{-10,-10,-10,-10,-10};
        }
        if(Math.abs(ang-pastang)>1.4){
            return new double[]{-10,-10,-10,-10,-10};

        }
        return new double[]{x,y,ang,loss(false, x,y,measurement,ang, robo)};





    }
    public static double loss( boolean print,double x, double y, double[] expected, double angle,robot robo){
        robo.translate(x-robo.x,y-robo.y);
        robo.rotate(angle-robo.heading);
        double[] measured = robo.query();
        if(print) {
            System.out.println(robo.x+" "+robo.y+" "+robo.heading);
            System.out.println(Arrays.toString(measured));
        }
        double mean = 0.0;
        for(int i=0; i<measured.length; i++){
            mean+=(measured[i]-expected[i])*(measured[i]-expected[i]);
        }
        mean/= (measured.length+0.0);

        return mean;
    }
}
