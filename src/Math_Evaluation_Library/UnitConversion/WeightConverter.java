package Math_Evaluation_Library.UnitConversion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antonio on 2017-07-22.
 */
public class WeightConverter extends UnitConverter {

    public WeightConverter(){

        units     = new String[]{"ug",        "mg",        "g",    "kg",       "t",     "lb",    "oz",    "ozt",        "ct",    "stone", "lt",       "st"};
        longUnits = new String[]{"microgram", "milligram", "gram", "kilogram", "tonne", "pound", "ounce", "troy ounce", "carat", "stone", "long ton", "short ton"};

        List<String> tempUnit1 = new ArrayList<>();
        List<String> tempUnit2 = new ArrayList<>();
        List<Double> tempRelation = new ArrayList<>();

        // tempRelation amount of tempUnit1 is equal to 1 tempUnit2

        tempUnit1.add("ug");    tempUnit2.add("mg");    tempRelation.add(1E3);
        tempUnit1.add("ug");    tempUnit2.add("g");     tempRelation.add(1E6);
        tempUnit1.add("ug");    tempUnit2.add("kg");    tempRelation.add(1E9);
        tempUnit1.add("ug");    tempUnit2.add("t");     tempRelation.add(1E12);
        tempUnit1.add("ug");    tempUnit2.add("lb");    tempRelation.add(453592370.0);
        tempUnit1.add("ug");    tempUnit2.add("oz");    tempRelation.add(28349523.125);
        tempUnit1.add("ug");    tempUnit2.add("ozt");   tempRelation.add(31103476.8);
        tempUnit1.add("ug");    tempUnit2.add("ct");    tempRelation.add(200000.0);
        tempUnit1.add("ug");    tempUnit2.add("stone"); tempRelation.add(6350293180.0);
        tempUnit1.add("ug");    tempUnit2.add("lt");    tempRelation.add(1016046908800.0);
        tempUnit1.add("ug");    tempUnit2.add("st");    tempRelation.add(907184740000.0);

        tempUnit1.add("mg");    tempUnit2.add("g");     tempRelation.add(1E3);
        tempUnit1.add("mg");    tempUnit2.add("kg");    tempRelation.add(1E6);
        tempUnit1.add("mg");    tempUnit2.add("t");     tempRelation.add(1E9);
        tempUnit1.add("mg");    tempUnit2.add("lb");    tempRelation.add(453592.37);
        tempUnit1.add("mg");    tempUnit2.add("oz");    tempRelation.add(28349.523125);
        tempUnit1.add("mg");    tempUnit2.add("ozt");   tempRelation.add(31103.4768);
        tempUnit1.add("mg");    tempUnit2.add("ct");    tempRelation.add(2E2);
        tempUnit1.add("mg");    tempUnit2.add("stone"); tempRelation.add(6350293.18);
        tempUnit1.add("mg");    tempUnit2.add("lt");    tempRelation.add(1016046908.8);
        tempUnit1.add("mg");    tempUnit2.add("st");    tempRelation.add(907184740.0);

        tempUnit1.add("g");     tempUnit2.add("kg");    tempRelation.add(1E3);
        tempUnit1.add("g");     tempUnit2.add("t");     tempRelation.add(1E6);
        tempUnit1.add("g");     tempUnit2.add("lb");    tempRelation.add(453.59237);
        tempUnit1.add("g");     tempUnit2.add("oz");    tempRelation.add(28.349523125);
        tempUnit1.add("g");     tempUnit2.add("ozt");   tempRelation.add(31.1034768 );
        tempUnit1.add("g");     tempUnit2.add("ct");    tempRelation.add(0.2);
        tempUnit1.add("g");     tempUnit2.add("stone"); tempRelation.add(6350.29318);
        tempUnit1.add("g");     tempUnit2.add("lt");    tempRelation.add(1016046.9088);
        tempUnit1.add("g");     tempUnit2.add("st");    tempRelation.add(907184.74);

        tempUnit1.add("kg");    tempUnit2.add("t");     tempRelation.add(1E3);
        tempUnit1.add("kg");    tempUnit2.add("lb");    tempRelation.add(0.45359237);
        tempUnit1.add("kg");    tempUnit2.add("oz");    tempRelation.add(0.028349523125);
        tempUnit1.add("kg");    tempUnit2.add("ozt");   tempRelation.add(0.0311034768);
        tempUnit1.add("kg");    tempUnit2.add("ct");    tempRelation.add(0.0002);
        tempUnit1.add("kg");    tempUnit2.add("stone"); tempRelation.add(6.35029318);
        tempUnit1.add("kg");    tempUnit2.add("lt");    tempRelation.add(1016.0469088);
        tempUnit1.add("kg");    tempUnit2.add("st");    tempRelation.add(907.18474);

        tempUnit1.add("lb");    tempUnit2.add("oz");    tempRelation.add(0.0625);
        tempUnit1.add("lb");    tempUnit2.add("ozt");   tempRelation.add(0.06857142857143);
        tempUnit1.add("lb");    tempUnit2.add("ct");    tempRelation.add(0.0004409245243698);
        tempUnit1.add("lb");    tempUnit2.add("stone"); tempRelation.add(14.0);
        tempUnit1.add("lb");    tempUnit2.add("t");     tempRelation.add(2204.622621849);
        tempUnit1.add("lb");    tempUnit2.add("lt");    tempRelation.add(2240.0);
        tempUnit1.add("lb");    tempUnit2.add("st");    tempRelation.add(2000.0);

        tempUnit1.add("oz");    tempUnit2.add("ozt");   tempRelation.add(1.097142857143 );
        tempUnit1.add("oz");    tempUnit2.add("ct");    tempRelation.add(0.007054792389916);
        tempUnit1.add("oz");    tempUnit2.add("stone"); tempRelation.add(224.0);
        tempUnit1.add("oz");    tempUnit2.add("t");     tempRelation.add(35273.96194958);
        tempUnit1.add("oz");    tempUnit2.add("lt");    tempRelation.add(35840.0);
        tempUnit1.add("oz");    tempUnit2.add("st");    tempRelation.add(32000.0);

        tempUnit1.add("ozt");   tempUnit2.add("ct");    tempRelation.add(0.006430149313726);
        tempUnit1.add("ozt");   tempUnit2.add("stone"); tempRelation.add(204.1666666667);
        tempUnit1.add("ozt");   tempUnit2.add("t");     tempRelation.add(32150.74656863 );
        tempUnit1.add("ozt");   tempUnit2.add("lt");    tempRelation.add(32666.66666667);
        tempUnit1.add("ozt");   tempUnit2.add("st");    tempRelation.add(29166.66666667);

        tempUnit1.add("ct");    tempUnit2.add("stone"); tempRelation.add(31751.4659);
        tempUnit1.add("ct");    tempUnit2.add("t");     tempRelation.add(5000000.0);
        tempUnit1.add("ct");    tempUnit2.add("lt");    tempRelation.add(5080234.544);
        tempUnit1.add("ct");    tempUnit2.add("st");    tempRelation.add(4535923.7);

        tempUnit1.add("stone"); tempUnit2.add("t");     tempRelation.add(157.4730444178);
        tempUnit1.add("stone"); tempUnit2.add("lt");    tempRelation.add(160.0);
        tempUnit1.add("stone"); tempUnit2.add("st");    tempRelation.add(142.8571428571);

        tempUnit1.add("t");     tempUnit2.add("lt");    tempRelation.add(1.0160469088 );
        tempUnit1.add("t");     tempUnit2.add("st");    tempRelation.add(0.90718474);

        tempUnit1.add("lt");    tempUnit2.add("st");    tempRelation.add(0.8928571428571);

        units1 = new String[tempUnit1.size()];
        units2 = new String[tempUnit2.size()];
        unitRelation = new double[tempRelation.size()];

        for (int i = 0; i<units1.length; i++){
            units1[i] = tempUnit1.get(i);
            units2[i] = tempUnit2.get(i);
            unitRelation[i] = tempRelation.get(i);
        }

    }

    @Override
    public String additionalShort(String unit){
        if (unit.equals("foot")){
            return "ft";
        }
        if (unit.equals("ton US")){
            return "st";
        }
        if (unit.equals("ton UK")){
            return "lt";
        }
        return unit;
    }

}
