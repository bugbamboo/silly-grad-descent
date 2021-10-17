import java.util.ArrayList;

public class robot {
    public ArrayList<sensor> sensors;
    public double distance;
    public double size;
    public double x;
    public double y;
    public double heading;
    public robot(double distance, double x, double y, double size){
        sensors = new ArrayList<sensor>();
        this.size=size;
        this.distance =distance;
        this.x=x;
        this.y = y;
        this.heading = Math.PI*0.5;
        sensors.add(new sensor(x+size,y,0));
        sensors.add(new sensor(x+size,y+size,Math.PI*0.25));
        sensors.add(new sensor(x,y+size,Math.PI*0.5));
        sensors.add(new sensor(x-size,y+size,Math.PI*0.75));
        sensors.add(new sensor(x-size,y,Math.PI*1));
        sensors.add(new sensor(x-size,y-size,Math.PI*1.25));
        sensors.add(new sensor(x,y-size,Math.PI*1.5));
        sensors.add(new sensor(x+size,y-size,Math.PI*1.75));

    }
    public boolean rotate(double angle){
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        ArrayList<Double> newx = new ArrayList<>();
        ArrayList<Double> newy = new ArrayList<>();
        ArrayList<Double> newangle = new ArrayList<>();
        for(sensor sens :sensors){
            double xx = sens.x-x;
            double yy = sens.y-y;
            double xf = xx*cos-yy*sin;
            double yf = xx*sin+yy*cos;
            xf +=x;
            yf+=y;
            double anglef = sens.direction+angle;
            if(anglef>2.0*Math.PI){
                anglef-=2.0*Math.PI;
            }else if(anglef<0){
                anglef+=2.0*Math.PI;
            }
            newx.add(xf);
            newy.add(yf);
            newangle.add(anglef);
        }
        int index =0;
        for(sensor sens : sensors){
            sens.x=newx.get(index);
            sens.y = newy.get(index);
            sens.direction=newangle.get(index);
            index++;
        }
        heading+=angle;
        if(heading>2.0*Math.PI){
            heading-=2.0*Math.PI;
        }else if(heading<0){
            heading+=2.0*Math.PI;
        }
        return true;
    }
    public boolean translate(double dx, double dy){
            ArrayList<Double> newx = new ArrayList<>();
            ArrayList<Double> newy = new ArrayList<>();
            for(sensor sens :sensors){
                newx.add(sens.x+dx);
                newy.add(sens.y+dy);
            }
            int index =0;
            for(sensor sens : sensors){
                sens.x=newx.get(index);
                sens.y = newy.get(index);
                index++;
            }
            x+=dx;
            y+=dy;
            return true;

    }
    public boolean move(double length){
        ArrayList<Double> newx = new ArrayList<>();
        ArrayList<Double> newy = new ArrayList<>();
        for(sensor sens :sensors){
            newx.add(sens.x+length*Math.cos(heading));
            newy.add(sens.y+length*Math.sin(heading));
        }
        for(double f : newx){
            if(f<-1.0*distance-0.0001){
                return false;
            }
            if(f>distance+0.0001){
                return false;
            }
        }
        for(double f : newy){
            if(f<-1.0*distance-0.0001){
                return false;
            }
            if(f>distance+0.0001){
                return false;
            }
        }
        int index =0;
        for(sensor sens : sensors){
            sens.x=newx.get(index);
            sens.y = newy.get(index);
            index++;
        }
        x+=length*Math.cos(heading);
        y+=length*Math.sin(heading);
        return true;
    }

    public double[] query(){
        double[] answers = new double[8];
        int counter=0;
        for(sensor x: sensors){
            answers[counter]=x.sense(distance);
            counter++;

        }
        return answers;

    }
}
