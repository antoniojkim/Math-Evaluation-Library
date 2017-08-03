package Math_Evaluation_Library.UnitConversion;

import Math_Evaluation_Library.Sort;
import Math_Evaluation_Library.Search;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.NaN;

/**
 * Created by Antonio on 2017-07-22.
 */
public class UnitConverter {

//    public static void main (String[] args){
//        String[] array =  {"bit", "B",    "Kbit",    "KB",       "Mbit",    "MB",       "Gbit",    "GB",       "Tbit",    "TB",       "Pbit",    "PB",       "Ebit",   "EB",      "Zbit",     "ZB",        "Ybit",     "YB"};
//        double[] values = null;
//
//        String valuesString = "tempUnit1.add(\"bit\");       tempUnit2.add(\"B\");         tempRelation.add(8.0);\n" +
//                "        tempUnit1.add(\"bit\");       tempUnit2.add(\"Kbit\");      tempRelation.add(1024.0);\n" +
//                "        tempUnit1.add(\"bit\");       tempUnit2.add(\"KB\");        tempRelation.add(8192.0);\n" +
//                "        tempUnit1.add(\"bit\");       tempUnit2.add(\"Mbit\");      tempRelation.add(1048576.0);\n" +
//                "        tempUnit1.add(\"bit\");       tempUnit2.add(\"MB\");        tempRelation.add(8388608.0);\n" +
//                "        tempUnit1.add(\"bit\");       tempUnit2.add(\"Gbit\");      tempRelation.add(1073741824.0);\n" +
//                "        tempUnit1.add(\"bit\");       tempUnit2.add(\"GB\");        tempRelation.add(8589934592.0);\n" +
//                "        tempUnit1.add(\"bit\");       tempUnit2.add(\"Tbit\");      tempRelation.add(1099511627776.0);\n" +
//                "        tempUnit1.add(\"bit\");       tempUnit2.add(\"TB\");        tempRelation.add(8795755265206.0);\n" +
//                "        tempUnit1.add(\"bit\");       tempUnit2.add(\"Pbit\");      tempRelation.add(1125899911454000.0);\n" +
//                "        tempUnit1.add(\"bit\");       tempUnit2.add(\"PB\");        tempRelation.add(9006853391570944.0);\n" +
//                "        tempUnit1.add(\"bit\");       tempUnit2.add(\"Ebit\");      tempRelation.add(1.152921509328896E18);\n" +
//                "        tempUnit1.add(\"bit\");       tempUnit2.add(\"EB\");        tempRelation.add(9.2230178729686467E18);\n" +
//                "        tempUnit1.add(\"bit\");       tempUnit2.add(\"Zbit\");      tempRelation.add(1.1805916255527895E21);\n" +
//                "        tempUnit1.add(\"bit\");       tempUnit2.add(\"ZB\");        tempRelation.add(9.444370301919895E21);\n" +
//                "        tempUnit1.add(\"bit\");       tempUnit2.add(\"Ybit\");      tempRelation.add(1.2089258245660565E24);\n" +
//                "        tempUnit1.add(\"bit\");       tempUnit2.add(\"YB\");        tempRelation.add(9.671035189165972E24);";
//        if (valuesString.length() > 0){
//            String[] split = valuesString.split("\n");
//            values = new double[split.length+1];
//            values[0] = 1;
//            for (int i = 1; i<values.length; i++){
//                int prefix = split[i-1].indexOf("tempRelation.add(");
//                int post = split[i-1].indexOf(")", prefix);
//                values[i] = Double.parseDouble(split[i-1].substring(prefix+("tempRelation.add(").length(), post));
//                System.out.print(values[i]+"  ");
//            }
//            System.out.println("\n");
//        }
//
//        print(array, values);
//    }
//    public static void print(String[] array, double[] values){
//        if (values != null) {
//            for (int i = 0; i<array.length; i++){
//                for (int j = i+1; j < array.length; j++){
//                    System.out.println("tempUnit1.add(\""+array[i]+"\");"+getSpace(array[i].length())+"tempUnit2.add(\""+array[j]+"\");"+getSpace(array[j].length())+"tempRelation.add("+values[j]+");");
//                }
//                for (int j = i + 2; j < values.length; j++) {
//                    values[j] = values[j] / values[i + 1];
//                }
//                System.out.println();
//            }
//        }
//        else{
//            for (int i = 0; i<array.length; i++){
//                for (int j = i+1; j < array.length; j++){
//                    System.out.println("tempUnit1.add(\""+array[i]+"\");"+getSpace(array[i].length())+"tempUnit2.add(\""+array[j]+"\");"+getSpace(array[j].length())+"tempRelation.add();");
//                }
//                System.out.println();
//            }
//        }
//    }
//    public static String getSpace(int length){
//        length = 10-length;
//        String space = "";
//        for (int i = 0; i<length; i++){
//            space += " ";
//        }
//        return space;
//    }

    private boolean sorted = false;

    protected String[] units;
    protected String[] longUnits;

    protected String[] units1;
    protected String[] units2;
    protected double[] unitRelation;

    public boolean isUnit(String unit){
        if (units2 != null && units1 != null && units2 != null && unitRelation != null) {
            if (!sorted) {
                Sort.quicksort(units, longUnits);
                sorted = true;
            }
            return Search.contains(units, unit) || Search.contains(units, toShort(unit));
        }
        return false;
    }
    public boolean areUnits(String unit1, String unit2){
        return isUnit(unit1) && isUnit(unit2);
    }

    public double convert(double measure, String unit1, String unit2){
        if (unit1.equals(unit2)) {
            return measure;
        }
        unit1 = toShort(unit1);
        unit2 = toShort(unit2);
        for (int i = 0; i < units1.length; i++) {
            if (unit1.equals(units1[i]) && unit2.equals(units2[i])) {
                return measure / unitRelation[i];
            } else if (unit2.equals(units1[i]) && unit1.equals(units2[i])) {
                return measure * unitRelation[i];
            }
        }
        return NaN;
    }

    protected int maxLength = 3;
    public String toShort(String unit){
        if (unit.length() > maxLength && longUnits != null){
            if (unit.endsWith("s")){
                if (unit.endsWith("hes")){
                    unit = unit.substring(0, unit.length()-2);
                }
                else{
                    unit = unit.substring(0, unit.length()-1);
                }
            }
            for (int i = 0; i<longUnits.length; i++){
                if (longUnits[i].equals(unit)){
                    return units[i];
                }
            }
            return additionalShort(unit);
        }
        return unit;
    }
    public String additionalShort(String unit){
        return unit;
    }

}
