package Math_Evaluation_Library.Geometry;

import Math_Evaluation_Library.Objects.Fraction;
import Math_Evaluation_Library.Objects.MathObject;
import Math_Evaluation_Library.Search;

/**
 * Created by Antonio on 2017-07-13.
 */
public class ShapeFormulas {


    public static final String INVALID = "Invalid Input Error - Invalid shape";

    private static final String l = "𝑙", w = "\uD835\uDC64", h = "\uD835\uDCBD", b = "\uD835\uDC4F", r = "\uD835\uDC5F";

    private static class Shape3D extends MathObject{

        String shape = "";
        String formula = "";
        int numArguments;

        public Shape3D(String shape, int numArguments, String formula){
            this.shape = shape;
            this.formula = formula;
            this.numArguments = numArguments;
        }

        public String calculateVolume(double[] arguments){
            return "";
        }
        protected String volumeCalculation(double[] arguments){  return ""; }

        @Override
        public String getName(){
            return shape;
        }
        @Override
        public int length(){
            return shape.length();
        }
    }

    public static final Shape3D[] shapes3D = {
            new Shape3D("cone", 2, "V = (1/3)*π"+r+"²"+h+"    where "+r+" is the radius and "+h+" is the height of the cone")
            {
                @Override
                public String calculateVolume(double[] arguments){
                    if (arguments.length == numArguments){
                        return "V = "+calculateVolume(arguments)+"   is the volume of a cone with a radius of "+Fraction.getFraction(arguments[0])+" and a height of "+Fraction.getFraction(arguments[1]);
                    }
                    return INVALID;
                }
                @Override
                protected String volumeCalculation(double[] arguments){
                    return new Fraction(1.0/3.0*Math.pow(arguments[0], 2)*arguments[1]).piMultiple();
                }
            },
            new Shape3D("cube", 1, "V = "+l+"³    where "+l+" is the length of one of the sides of the cube")
            {
                @Override
                public String calculateVolume(double[] arguments){
                    if (arguments.length == numArguments){
                        return "V = "+calculateVolume(arguments)+"   is the volume of a cube with side length "+Fraction.getFraction(arguments[0]);
                    }
                    return INVALID;
                }
                @Override
                protected String volumeCalculation(double[] arguments){
                    return Fraction.getFraction(Math.pow(arguments[0], 3));
                }
            },
            new Shape3D("cylinder", 2, "V = π"+r+"²"+h+"    where "+r+" is the radius and "+h+" is the height of the cylinder")
            {
                @Override
                public String calculateVolume(double[] arguments){
                    if (arguments.length == numArguments){
                        return INVALID;
                    }
                    return INVALID;
                }
                @Override
                protected String volumeCalculation(double[] arguments){
                    return "V = "+calculateVolume(arguments)+"   is the volume of a cylinder with a radius of "+Fraction.getFraction(arguments[0])+" and a height of "+Fraction.getFraction(arguments[1]);
                }
            },
            new Shape3D("rectangular prism", 3, "V = "+l+"*"+w+"*"+h+"    where "+l+" is the length, "+w+" is the width, and "+h+" is the height of the rectangular prism")
            {
                @Override
                public String calculateVolume(double[] arguments){
                    if (arguments.length == numArguments){
                        return "V = "+calculateVolume(arguments)+"   is the volume of a cone with a length of "+Fraction.getFraction(arguments[0])+", a width of "+Fraction.getFraction(arguments[1])+", and a height of "+Fraction.getFraction(arguments[2]);
                    }
                    return INVALID;
                }
                @Override
                protected String volumeCalculation(double[] arguments){
                    return Fraction.getFraction(arguments[0]*arguments[1]*arguments[2]);
                }
            },
            new Shape3D("sphere", 1, "V = (4/3)*π"+r+"³    where "+r+" is the radius of the sphere")
            {
                @Override
                public String calculateVolume(double[] arguments){
                    if (arguments.length == numArguments){
                        return "V = "+volumeCalculation(arguments)+"   is the volume of a sphere with radius "+Fraction.getFraction(arguments[0]);
                    }
                    return INVALID;
                }
                @Override
                protected String volumeCalculation(double[] arguments){
                    return new Fraction(4.0/3.0*Math.pow(arguments[0], 3)).piMultiple();
                }
            }
    };

    public static String getVolumeFormula (String type){
        int index = Search.binarySearch(shapes3D, type.toLowerCase());
        if (index != -1)    return shapes3D[index].formula;
        return "Formula not Found";
    }

    public static String getVolume(String type, double[] arguments){
        int index = Search.binarySearch(shapes3D, type.toLowerCase());
        if (index != -1)    return shapes3D[index].calculateVolume(arguments);
        return "Invalid Input Error - Invalid shape";
    }

    private static class Shape2D extends MathObject{

        String shape = "";
        String formula = "";
        int numArguments;

        public Shape2D(String shape, int numArguments, String formula){
            this.shape = shape;
            this.formula = formula;
            this.numArguments = numArguments;
        }

        public String calculateArea(double... arguments){
            return "";
        }
        protected String areaCalculation(double[] arguments){  return ""; }

        public int compareTo(String function){
            return shape.compareTo(function);
        }
        public int compareTo(Shape2D m){
            return compareTo(m.shape);
        }

        public String getName(){
            return shape;
        }
        public int length(){
            return shape.length();
        }
    }

    public static final Shape2D[] shapes2D = {
            new Shape2D("circle", 1, "A = π"+r+"²     where "+r+" is the radius of the circle")
            {
                @Override
                public String calculateArea(double... arguments){
                    if (arguments.length == numArguments){

                    }
                    return INVALID;
                }
                @Override
                protected String areaCalculation(double[] arguments){  return INVALID; }
            },
            new Shape2D("cone", 2, "SA = π"+r+"("+r+"+√("+h+"²+"+r+"²))    where "+r+" is the radius and "+h+" is the height of the cone")
            {
                @Override
                public String calculateArea(double... arguments){
                    if (arguments.length == numArguments){
                        return "SA = "+calculateArea(arguments)+"   is the Surface Area of a cone with a radius of "+Fraction.getFraction(arguments[0])+" and a height of "+Fraction.getFraction(arguments[1]);
                    }
                    return INVALID;
                }
                @Override
                protected String areaCalculation(double[] arguments){
                    return new Fraction(Fraction.getFraction(arguments[0]*(arguments[0]+Geometric.ED(arguments[0], arguments[1])))).piMultiple();
                }
            },
            new Shape2D("cube", 1, "SA = 6*"+l+"²    where "+l+" is the length of one of the sides of the cube")
            {
                @Override
                public String calculateArea(double... arguments){
                    if (arguments.length == numArguments){
                        return "SA = "+calculateArea(arguments)+"   is the Surface Area of a cube with side length "+Fraction.getFraction(arguments[0]);
                    }
                    return INVALID;
                }
                @Override
                protected String areaCalculation(double[] arguments){
                    return Fraction.getFraction(6.0*Math.pow(arguments[0], 2));
                }
            },
            new Shape2D("cylinder", 2, "SA = 2π"+r+h+"+2π"+r+"²    where "+r+" is the radius and "+h+" is the height of the cylinder")
            {
                @Override
                public String calculateArea(double... arguments){
                    if (arguments.length == numArguments){
                        return "SA = "+calculateArea(arguments)+"   is the Surface Area of a cylinder with a radius of "+Fraction.getFraction(arguments[0])+" and a height of "+Fraction.getFraction(arguments[1]);
                    }
                    return INVALID;
                }
                @Override
                protected String areaCalculation(double[] arguments){
                    return new Fraction(Fraction.getFraction(2.0*arguments[0]*arguments[1]+2.0*Math.pow(arguments[0], 2))).piMultiple();
                }
            },
            new Shape2D("parallelogram", 2, "A = "+b+"*"+h+"     where "+b+" is the base and "+h+" is the height of the parallelogram")
            {
                @Override
                public String calculateArea(double... arguments){
                    if (arguments.length == numArguments){

                    }
                    return INVALID;
                }
                @Override
                protected String areaCalculation(double[] arguments){  return INVALID; }
            },
            new Shape2D("rectangle", 2, "A = "+l+"*"+w+"     where "+l+" is the length and "+w+" is the width of the rectangle")
            {
                @Override
                public String calculateArea(double... arguments){
                    if (arguments.length == numArguments){

                    }
                    return INVALID;
                }
                @Override
                protected String areaCalculation(double[] arguments){  return INVALID; }
            },
            new Shape2D("rectangular prism", 3, "SA = 2*"+l+"*"+w+" + 2*"+w+"*h + 2*l*h    where "+l+" is the length, "+w+" is the width, and "+h+" is the height of the rectangular prism")
            {
                @Override
                public String calculateArea(double... arguments){
                    if (arguments.length == numArguments){
                        return "SA = "+calculateArea(arguments)+"   is the Surface Area of a cone with a length of "+Fraction.getFraction(arguments[0])+", a width of "+Fraction.getFraction(arguments[1])+", and a height of "+Fraction.getFraction(arguments[2]);
                    }
                    return INVALID;
                }
                @Override
                protected String areaCalculation(double[] arguments){
                    return Fraction.getFraction(2.0*(arguments[0]*arguments[1]+arguments[1]*arguments[2]+arguments[0]*arguments[2]));
                }
            },
            new Shape2D("semicircle", 1, "A = 0.5*π"+r+"²     where "+r+" is the radius of the semicircle")
            {
                @Override
                public String calculateArea(double... arguments){
                    if (arguments.length == numArguments){

                    }
                    return INVALID;
                }
                @Override
                protected String areaCalculation(double[] arguments){  return INVALID; }
            },
            new Shape2D("sphere", 1, "SA = 4*π"+r+"²    where "+r+" is the radius of the sphere")
            {
                @Override
                public String calculateArea(double... arguments){
                    if (arguments.length == numArguments){
                        return "SA = "+calculateArea(arguments)+"   is the Surface Area of a sphere with radius "+Fraction.getFraction(arguments[0]);
                    }
                    return INVALID;
                }
                @Override
                protected String areaCalculation(double[] arguments){
                    return new Fraction(Fraction.getFraction(4.0*Math.pow(arguments[0], 2))).piMultiple();
                }
            },
            new Shape2D("square", 1, "A = "+l+"²     where "+l+" is the lengths of one of the sides of the square")
            {
                @Override
                public String calculateArea(double... arguments){
                    if (arguments.length == numArguments){

                    }
                    return INVALID;
                }
                @Override
                protected String areaCalculation(double[] arguments){  return INVALID; }
            },
            new Shape2D("triangle", 2, "A = 0.5*"+b+"*"+h+"     where "+b+" is the base and "+h+" is the height of the triangle")
            {
                @Override
                public String calculateArea(double... arguments){
                    if (arguments.length == numArguments){

                    }
                    return INVALID;
                }
                @Override
                protected String areaCalculation(double[] arguments){  return INVALID; }
            }
    };

    public static final String[][] areaFormulaReplacements = {
            {"semi-circle", "semicircle"}, {"rhombus", "parallelogram"}, {"diamond", "parallelogram"}
    };

    public static String getAreaFormula (String type){
        int index = Search.binarySearch(shapes3D, Search.replace(type.toLowerCase(), areaFormulaReplacements));
        if (index != -1)    return shapes2D[index].formula;
        return "Formula Not Found";
    }

    public static String getArea(String type, double[] arguments){
        int index = Search.binarySearch(shapes2D, Search.replace(type.toLowerCase(), areaFormulaReplacements));
        if (index != -1)    return shapes2D[index].calculateArea(arguments);
        return "Invalid Input Error - Invalid shape";
    }

}
