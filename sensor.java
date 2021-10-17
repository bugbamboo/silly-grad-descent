public class sensor {
    public double x;
    public double y;
    public double direction;
    public sensor(double newX, double newY, double newDirection){
        x=newX;
        y=newY;
        direction=newDirection;
    }
    public double sense(double distance){
        if(direction<=Math.PI*0.5){

            double intersecthor = x+ (distance-y)*Math.tan(Math.PI*0.5-direction);
            double intersectvert = y+ (distance-x)*Math.tan(direction);
            if(-1.0*distance-0.001<=intersecthor &&intersecthor<=distance+0.001){
                return finddistance(intersecthor,distance);

            }
            return finddistance(distance,intersectvert);

        }else if(direction<=Math.PI){
            double intersecthor = x+ (distance-y)*Math.tan(Math.PI*0.5-direction);
            double intersectvert = y+ (distance+x)*Math.tan(-1.0*direction);
            if(-1.0*distance-0.001<=intersecthor &&intersecthor<=distance+0.001){
                return finddistance(intersecthor,distance);

            }
            return finddistance(-distance,intersectvert);
        }else if(direction<=Math.PI*1.5){
            double intersecthor = x+ (distance+y)*Math.tan(direction-Math.PI*0.5);
            double intersectvert = y+ (distance+x)*Math.tan(-1.0*direction);
            if(-1.0*distance-0.001<=intersecthor &&intersecthor<=distance+0.001){
                return finddistance(intersecthor,-1.0*distance);

            }
            return finddistance(-1.0*distance,intersectvert);

        }else if(direction<=Math.PI*2){
            double intersecthor = x+ (distance+y)*Math.tan(direction-Math.PI*0.5);
            double intersectvert = y+ (distance-x)*Math.tan(direction);
            if(-1.0*distance-0.001<=intersecthor &&intersecthor<=distance+0.001){
                return finddistance(intersecthor,-1.0*distance);

            }
            return finddistance(distance,intersectvert);
        }
        return -1;
    }
    public double finddistance(double x2, double y2){
        return Math.sqrt((x2-x)*(x2-x)+(y2-y)*(y2-y));
    }

}
