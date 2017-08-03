package Math_Evaluation_Library.UnitConversion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antonio on 2017-07-22.
 */
public class AreaConverter extends UnitConverter {

    public AreaConverter(){

        maxLength = 4;

        units     = new String[]{"mm²",               "cm²",               "m²",           "km²",              "in²",         "ft²",         "yd²",         "mi²",         "acre", "ha"};
        longUnits = new String[]{"square millimeter", "square centimeter", "square meter", "square kilometer", "square inch", "square feet", "square yard", "square mile", "acre", "hectare"};

        List<String> tempUnit1 = new ArrayList<>();
        List<String> tempUnit2 = new ArrayList<>();
        List<Double> tempRelation = new ArrayList<>();

        tempUnit1.add("mm²");       tempUnit2.add("cm²");       tempRelation.add(1E2);
        tempUnit1.add("mm²");       tempUnit2.add("m²");        tempRelation.add(1E6);
        tempUnit1.add("mm²");       tempUnit2.add("km²");       tempRelation.add(1E12);
        tempUnit1.add("mm²");       tempUnit2.add("in²");       tempRelation.add(645.16);
        tempUnit1.add("mm²");       tempUnit2.add("ft²");       tempRelation.add(92903.04);
        tempUnit1.add("mm²");       tempUnit2.add("yd²");       tempRelation.add(836127.36);
        tempUnit1.add("mm²");       tempUnit2.add("mi²");       tempRelation.add(2589988110336.0);
        tempUnit1.add("mm²");       tempUnit2.add("acre");      tempRelation.add(4046856422.4);
        tempUnit1.add("mm²");       tempUnit2.add("ha");        tempRelation.add(1E10);

        tempUnit1.add("cm²");       tempUnit2.add("m²");        tempRelation.add(1E4);
        tempUnit1.add("cm²");       tempUnit2.add("km²");       tempRelation.add(1E10);
        tempUnit1.add("cm²");       tempUnit2.add("in²");       tempRelation.add(6.4516);
        tempUnit1.add("cm²");       tempUnit2.add("ft²");       tempRelation.add(929.0304);
        tempUnit1.add("cm²");       tempUnit2.add("yd²");       tempRelation.add(8361.2736);
        tempUnit1.add("cm²");       tempUnit2.add("mi²");       tempRelation.add(25899881103.36);
        tempUnit1.add("cm²");       tempUnit2.add("acre");      tempRelation.add(40468564.224);
        tempUnit1.add("cm²");       tempUnit2.add("ha");        tempRelation.add(1E8);

        tempUnit1.add("m²");        tempUnit2.add("km²");       tempRelation.add(1E6);
        tempUnit1.add("m²");        tempUnit2.add("in²");       tempRelation.add(0.00064516);
        tempUnit1.add("m²");        tempUnit2.add("ft²");       tempRelation.add(0.0001);
        tempUnit1.add("m²");        tempUnit2.add("yd²");       tempRelation.add(0.83612736);
        tempUnit1.add("m²");        tempUnit2.add("mi²");       tempRelation.add(2589988.110336);
        tempUnit1.add("m²");        tempUnit2.add("acre");      tempRelation.add(4046.8564224 );
        tempUnit1.add("m²");        tempUnit2.add("ha");        tempRelation.add(1E4);

        tempUnit1.add("km²");       tempUnit2.add("in²");       tempRelation.add(6.4516E-10);
        tempUnit1.add("km²");       tempUnit2.add("ft²");       tempRelation.add(9.290304E-8);
        tempUnit1.add("km²");       tempUnit2.add("yd²");       tempRelation.add(8.3612736E-7);
        tempUnit1.add("km²");       tempUnit2.add("mi²");       tempRelation.add(2.589988110336);
        tempUnit1.add("km²");       tempUnit2.add("acre");      tempRelation.add(0.0040468564224);
        tempUnit1.add("km²");       tempUnit2.add("ha");        tempRelation.add(0.01);

        tempUnit1.add("in²");       tempUnit2.add("ft²");       tempRelation.add(144.0);
        tempUnit1.add("in²");       tempUnit2.add("yd²");       tempRelation.add(1296.0);
        tempUnit1.add("in²");       tempUnit2.add("mi²");       tempRelation.add(4014489600.0);
        tempUnit1.add("in²");       tempUnit2.add("acre");      tempRelation.add(6272640.0);
        tempUnit1.add("in²");       tempUnit2.add("ha");        tempRelation.add(15500031.00006);

        tempUnit1.add("ft²");       tempUnit2.add("yd²");       tempRelation.add(107639.1041671);
        tempUnit1.add("ft²");       tempUnit2.add("mi²");       tempRelation.add(27878400.0);
        tempUnit1.add("ft²");       tempUnit2.add("acre");      tempRelation.add(43560.0);
        tempUnit1.add("ft²");       tempUnit2.add("ha");        tempRelation.add(107639.1041671 );

        tempUnit1.add("yd²");       tempUnit2.add("mi²");       tempRelation.add(3097600.0);
        tempUnit1.add("yd²");       tempUnit2.add("acre");      tempRelation.add(4840.0);
        tempUnit1.add("yd²");       tempUnit2.add("ha");        tempRelation.add(11959.90046301);

        tempUnit1.add("mi²");       tempUnit2.add("acre");      tempRelation.add(0.0015625);
        tempUnit1.add("mi²");       tempUnit2.add("ha");        tempRelation.add(0.003861021585424);

        tempUnit1.add("acre");      tempUnit2.add("ha");        tempRelation.add(2.471053814672);

        units1 = new String[tempUnit1.size()];
        units2 = new String[tempUnit2.size()];
        unitRelation = new double[tempRelation.size()];

        for (int i = 0; i<units1.length; i++){
            units1[i] = tempUnit1.get(i);
            units2[i] = tempUnit2.get(i);
            unitRelation[i] = tempRelation.get(i);
        }

    }

}
