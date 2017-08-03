package Math_Evaluation_Library.Geometry;

import Math_Evaluation_Library.Objects.Fraction;

/**
 * Created by Antonio on 2017-07-13.
 */
public class Formulas {

    public static String getVolumeFormula (String type){
        if (type.equalsIgnoreCase("sphere")){
            return "V = (4/3)*πr³    where r is the radius of the sphere";
        }
        else if (type.equalsIgnoreCase("cylinder")){
            return "V = πr²h    where r is the radius and h is the height of the cylinder";
        }
        else if (type.equalsIgnoreCase("cone")){
            return "V = (1/3)*πr²h    where r is the radius and h is the height of the cone";
        }
        else if (type.equalsIgnoreCase("rectangular prism")){
            return "V = l*w*h    where l is the length, w is the width, and h is the height of the rectangular prism";
        }
        else if (type.equalsIgnoreCase("cube")){
            return "V = l³    where l is the length of one of the sides of the cube";
        }
        return "Formula not Found";
    }

    public static String calculateVolume (String type, double[] arguments){
        if (arguments.length == 1){
            if (type.equalsIgnoreCase("sphere")){
                return "V = "+Fraction.getFraction(4.0/3.0*Math.PI*Math.pow(arguments[0], 3))+"   is the volume of a sphere with radius "+Fraction.getFraction(arguments[0]);
            }
            if (type.equalsIgnoreCase("cube")){
                return "V = "+Fraction.getFraction(Math.pow(arguments[0], 3))+"   is the volume of a cube with side length "+Fraction.getFraction(arguments[0]);
            }
        }
        if (arguments.length == 2){
            if (type.equalsIgnoreCase("cylinder")){
                return "V = "+Fraction.getFraction(Math.PI*Math.pow(arguments[0], 2)*arguments[1])+"   is the volume of a cylinder with a radius of "+Fraction.getFraction(arguments[0])+" and a height of "+Fraction.getFraction(arguments[1]);
            }
            if (type.equalsIgnoreCase("cone")){
                return "V = "+Fraction.getFraction(1.0/3.0*Math.PI*Math.pow(arguments[0], 2)*arguments[1])+"   is the volume of a cone with a radius of "+Fraction.getFraction(arguments[0])+" and a height of "+Fraction.getFraction(arguments[1]);
            }
        }
        if (arguments.length == 3){
            if (type.equalsIgnoreCase("rectangular prism")){
                return "V = "+Fraction.getFraction(arguments[0]*arguments[1]*arguments[2])+" is the volume of a cone with a length of "+Fraction.getFraction(arguments[0])+", a width of "+Fraction.getFraction(arguments[1])+", and a height of "+Fraction.getFraction(arguments[2]);
            }
        }
        return "Invalid Input Error - Invalid shape";
    }

    public static String getAreaFormula (String type){
        if (type.equalsIgnoreCase("sphere")){
            return "SA = 4*πr²    where r is the radius of the sphere";
        }
        else if (type.equalsIgnoreCase("cylinder")){
            return "SA = 2πrh+2πr²    where r is the radius and h is the height of the cylinder";
        }
        else if (type.equalsIgnoreCase("cone")){
            return "SA = πr(r+√(h²+r²))    where r is the radius and h is the height of the cone";
        }
        else if (type.equalsIgnoreCase("rectangular prism")){
            return "SA = 2*l*w + 2*w*h + 2*l*h    where l is the length, w is the width, and h is the height of the rectangular prism";
        }
        else if (type.equalsIgnoreCase("cube")){
            return "SA = 6*l²    where l is the length of one of the sides of the cube";
        }
        else if (type.equalsIgnoreCase("circle")){
            return "A = πr²     where r is the radius of the circle";
        }
        if (type.equalsIgnoreCase("semicircle") || type.equalsIgnoreCase("semi-circle")){
            return "A = 0.5*πr²     where r is the radius of the "+type;
        }
        else if (type.equalsIgnoreCase("square")){
            return "A = l²     where l is the lengths of one of the sides of the square";
        }
        else if (type.equalsIgnoreCase("rectangle")){
            return "A = l*w     where l is the length and w is the width of the rectangle";
        }
        else if (type.equalsIgnoreCase("triangle")){
            return "A = 0.5*b*h     where b is the base and h is the height of the triangle";
        }
        else if (type.equalsIgnoreCase("parallelogram") || type.equalsIgnoreCase("rhombus") || type.equalsIgnoreCase("diamond")){
            return "A = b*h     where b is the base and hw is the height of the "+type;
        }
        return "Formula Not Found";
    }

    public static String calculateArea (String type, double[] arguments){
        if (arguments.length == 1){
            if (type.equalsIgnoreCase("sphere")){
                return "SA = "+Fraction.getFraction(4.0*Math.PI*Math.pow(arguments[0], 2))+"   is the Surface Area of a sphere with radius "+Fraction.getFraction(arguments[0]);
            }
            if (type.equalsIgnoreCase("cube")){
                return "SA = "+Fraction.getFraction(6.0*Math.pow(arguments[0], 2))+"   is the Surface Area of a cube with side length "+Fraction.getFraction(arguments[0]);
            }
        }
        if (arguments.length == 2){
            if (type.equalsIgnoreCase("cylinder")){
                return "SA = "+Fraction.getFraction(2.0*Math.PI*arguments[0]*arguments[1]+2.0*Math.PI*Math.pow(arguments[0], 2))+"   is the Surface Area of a cylinder with a radius of "+Fraction.getFraction(arguments[0])+" and a height of "+Fraction.getFraction(arguments[1]);
            }
            if (type.equalsIgnoreCase("cone")){
                return "SA = "+Fraction.getFraction(Math.PI*arguments[0]*(arguments[0]+Math.sqrt(Math.pow(arguments[0], 2)+Math.pow(arguments[1], 2))))+"   is the Surface Area of a cone with a radius of "+Fraction.getFraction(arguments[0])+" and a height of "+Fraction.getFraction(arguments[1]);
            }
        }
        if (arguments.length == 3){
            if (type.equalsIgnoreCase("rectangular prism")){
                return "SA = "+Fraction.getFraction(2.0*(arguments[0]*arguments[1]+arguments[1]*arguments[2]+arguments[0]*arguments[2]))+" is the Surface Area of a cone with a length of "+Fraction.getFraction(arguments[0])+", a width of "+Fraction.getFraction(arguments[1])+", and a height of "+Fraction.getFraction(arguments[2]);
            }
        }
        return "Invalid Input Error - Invalid shape";
    }

}
