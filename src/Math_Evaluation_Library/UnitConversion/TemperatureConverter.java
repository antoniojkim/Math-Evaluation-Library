package Math_Evaluation_Library.UnitConversion;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.NaN;

/**
 * Created by Antonio on 2017-07-22.
 */
public class TemperatureConverter extends UnitConverter {

    public TemperatureConverter(){

        units     = new String[]{"°C",      "°F",         "K"};
        longUnits = new String[]{"celsius", "fahrenheit", "kelvin"};

    }

    @Override
    public double convert(double measure, String unit1, String unit2){
        if (unit1.equals(unit2)) {
            return measure;
        }
        unit1 = toShort(unit1);
        unit2 = toShort(unit2);
        if (unit1.equals("°C")){
            if (unit2.equals("°F")){
                return measure*1.8+32;
            }
            if (unit2.equals("K")){
                return measure+273.15;
            }
        }
        if (unit1.equals("°F")){
            if (unit2.equals("°C")){
                return (measure-32)/1.8;
            }
            if (unit2.equals("K")){
                return (measure+459.67)*1.8;
            }
        }
        if (unit1.equals("K")){
            if (unit2.equals("°C")){
                return measure-273.15;
            }
            if (unit2.equals("°F")){
                return measure/1.8-459.67;
            }
        }
        return NaN;
    }

}
