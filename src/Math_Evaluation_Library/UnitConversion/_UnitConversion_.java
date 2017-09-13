package Math_Evaluation_Library.UnitConversion;

import Math_Evaluation_Library.Objects._Number_;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.NaN;

/**
 * Created by Antonio on 2017-07-22.
 */
public class _UnitConversion_ {

    public static String[] isUnitConversion(String param, int unitIndex){
        if (param.charAt(unitIndex) != '→'){
            unitIndex = param.indexOf("→");
        }
        if (unitIndex != -1){
            int commaIndex = param.indexOf(",");
                String measure = "";
            if (commaIndex != -1){

            }
            else{
                char[] array = param.toCharArray();
                for (char c : array){
                    if (_Number_.isNumber(c) || c == '.'){
                        measure += String.valueOf(c);
                    }
                    else{
                        break;
                    }
                }
                if (measure.length() > 0){
                    String unit1 = param.substring(measure.length(), unitIndex);
                    String unit2 = param.substring(unitIndex+1);
                    if (unit1.length() > 0 && unit2.length() > 0){
                        return new String[]{measure, unit1, unit2};
                    }
                }
            }
        }
        return null;
    }

    public static double convert(double measure, String unit1, String unit2){
        if (converters.isEmpty()){
            addAllConverters();
        }
        for(UnitConverter converter : converters){
            if (converter.areUnits(unit1, unit2)){
                return converter.convert(measure, unit1, unit2);
            }
        }
        int unit1_divIndex = unit1.indexOf("/");
        int unit2_divIndex = unit2.indexOf("/");
        if (unit1_divIndex != -1 && unit2_divIndex != -1){
            double conversion1 = NaN, conversion2 = NaN;
            String unit1Numerator = unit1.substring(0, unit1_divIndex);
            String unit2Numerator = unit2.substring(0, unit2_divIndex);
            for(UnitConverter converter : converters){
                if (converter.areUnits(unit1Numerator, unit2Numerator)){
                    conversion1 = converter.convert(1, unit1Numerator, unit2Numerator);
                    break;
                }
            }
            String unit1Denominator = unit1.substring(unit1_divIndex+1);
            String unit2Denominator = unit2.substring(unit2_divIndex+1);
            for(UnitConverter converter : converters){
                if (converter.areUnits(unit1Denominator, unit2Denominator)){
                    conversion2 = converter.convert(1, unit1Denominator, unit2Denominator);
                    break;
                }
            }
            if (_Number_.isNumber(conversion1) && _Number_.isNumber(conversion2)){
                return measure*conversion1/conversion2;
            }
        }
        return NaN;
    }

    private static List<UnitConverter> converters = new ArrayList<>();

    private static void addAllConverters(){
        converters.add(new DistanceConverter());
        converters.add(new WeightConverter());
        converters.add(new AreaConverter());
        converters.add(new TemperatureConverter());
        converters.add(new VolumeConverter());
        converters.add(new TimeConverter());
        converters.add(new FileSizeConverter());
    }

}
