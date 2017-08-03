package Math_Evaluation_Library.UnitConversion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antonio on 2017-07-22.
 */
public class TimeConverter extends UnitConverter {

    public TimeConverter(){

        units     = new String[]{"ns",         "us",          "ms",          "s",      "min",    "h",    "d",   "wk",   "fn",        "mth",   "yr",   "dc",     "c",       "ml"};
        longUnits = new String[]{"nanosecond", "microsecond", "millisecond", "second", "minute", "hour", "day", "week", "fortnight", "month", "year", "decade", "century", "millenium"};

        List<String> tempUnit1 = new ArrayList<>();
        List<String> tempUnit2 = new ArrayList<>();
        List<Double> tempRelation = new ArrayList<>();

        tempUnit1.add("ns");        tempUnit2.add("us");        tempRelation.add(1000.0);
        tempUnit1.add("ns");        tempUnit2.add("ms");        tempRelation.add(1000000.0);
        tempUnit1.add("ns");        tempUnit2.add("s");         tempRelation.add(1.0E9);
        tempUnit1.add("ns");        tempUnit2.add("min");       tempRelation.add(6.0E10);
        tempUnit1.add("ns");        tempUnit2.add("h");         tempRelation.add(3.6E12);
        tempUnit1.add("ns");        tempUnit2.add("d");         tempRelation.add(8.64E13);
        tempUnit1.add("ns");        tempUnit2.add("wk");        tempRelation.add(6.048E14);
        tempUnit1.add("ns");        tempUnit2.add("fn");        tempRelation.add(1.2096E15);
        tempUnit1.add("ns");        tempUnit2.add("mth");       tempRelation.add(2.62982296584E15);
        tempUnit1.add("ns");        tempUnit2.add("yr");        tempRelation.add(3.1536E16);
        tempUnit1.add("ns");        tempUnit2.add("dc");        tempRelation.add(3.1536E17);
        tempUnit1.add("ns");        tempUnit2.add("c");         tempRelation.add(3.1536E18);
        tempUnit1.add("ns");        tempUnit2.add("ml");        tempRelation.add(3.1536E19);

        tempUnit1.add("us");        tempUnit2.add("ms");        tempRelation.add(1000.0);
        tempUnit1.add("us");        tempUnit2.add("s");         tempRelation.add(1000000.0);
        tempUnit1.add("us");        tempUnit2.add("min");       tempRelation.add(6.0E7);
        tempUnit1.add("us");        tempUnit2.add("h");         tempRelation.add(3.6E9);
        tempUnit1.add("us");        tempUnit2.add("d");         tempRelation.add(8.64E10);
        tempUnit1.add("us");        tempUnit2.add("wk");        tempRelation.add(6.048E11);
        tempUnit1.add("us");        tempUnit2.add("fn");        tempRelation.add(1.2096E12);
        tempUnit1.add("us");        tempUnit2.add("mth");       tempRelation.add(2.62982296584E12);
        tempUnit1.add("us");        tempUnit2.add("yr");        tempRelation.add(3.1536E13);
        tempUnit1.add("us");        tempUnit2.add("dc");        tempRelation.add(3.1536E14);
        tempUnit1.add("us");        tempUnit2.add("c");         tempRelation.add(3.1536E15);
        tempUnit1.add("us");        tempUnit2.add("ml");        tempRelation.add(3.1536E16);

        tempUnit1.add("ms");        tempUnit2.add("s");         tempRelation.add(1000.0);
        tempUnit1.add("ms");        tempUnit2.add("min");       tempRelation.add(60000.0);
        tempUnit1.add("ms");        tempUnit2.add("h");         tempRelation.add(3600000.0);
        tempUnit1.add("ms");        tempUnit2.add("d");         tempRelation.add(8.64E7);
        tempUnit1.add("ms");        tempUnit2.add("wk");        tempRelation.add(6.048E8);
        tempUnit1.add("ms");        tempUnit2.add("fn");        tempRelation.add(1.2096E9);
        tempUnit1.add("ms");        tempUnit2.add("mth");       tempRelation.add(2.62982296584E9);
        tempUnit1.add("ms");        tempUnit2.add("yr");        tempRelation.add(3.1536E10);
        tempUnit1.add("ms");        tempUnit2.add("dc");        tempRelation.add(3.1536E11);
        tempUnit1.add("ms");        tempUnit2.add("c");         tempRelation.add(3.1536E12);
        tempUnit1.add("ms");        tempUnit2.add("ml");        tempRelation.add(3.1536E13);

        tempUnit1.add("s");         tempUnit2.add("min");       tempRelation.add(60.0);
        tempUnit1.add("s");         tempUnit2.add("h");         tempRelation.add(3600.0);
        tempUnit1.add("s");         tempUnit2.add("d");         tempRelation.add(86400.0);
        tempUnit1.add("s");         tempUnit2.add("wk");        tempRelation.add(604800.0);
        tempUnit1.add("s");         tempUnit2.add("fn");        tempRelation.add(1209600.0);
        tempUnit1.add("s");         tempUnit2.add("mth");       tempRelation.add(2629822.96584);
        tempUnit1.add("s");         tempUnit2.add("yr");        tempRelation.add(3.1536E7);
        tempUnit1.add("s");         tempUnit2.add("dc");        tempRelation.add(3.1536E8);
        tempUnit1.add("s");         tempUnit2.add("c");         tempRelation.add(3.1536E9);
        tempUnit1.add("s");         tempUnit2.add("ml");        tempRelation.add(3.1536E10);

        tempUnit1.add("min");       tempUnit2.add("h");         tempRelation.add(60.0);
        tempUnit1.add("min");       tempUnit2.add("d");         tempRelation.add(1440.0);
        tempUnit1.add("min");       tempUnit2.add("wk");        tempRelation.add(10080.0);
        tempUnit1.add("min");       tempUnit2.add("fn");        tempRelation.add(20160.0);
        tempUnit1.add("min");       tempUnit2.add("mth");       tempRelation.add(43830.382764);
        tempUnit1.add("min");       tempUnit2.add("yr");        tempRelation.add(525600.0);
        tempUnit1.add("min");       tempUnit2.add("dc");        tempRelation.add(5256000.0);
        tempUnit1.add("min");       tempUnit2.add("c");         tempRelation.add(5.256E7);
        tempUnit1.add("min");       tempUnit2.add("ml");        tempRelation.add(5.256E8);

        tempUnit1.add("h");         tempUnit2.add("d");         tempRelation.add(24.0);
        tempUnit1.add("h");         tempUnit2.add("wk");        tempRelation.add(168.0);
        tempUnit1.add("h");         tempUnit2.add("fn");        tempRelation.add(336.0);
        tempUnit1.add("h");         tempUnit2.add("mth");       tempRelation.add(730.5063794);
        tempUnit1.add("h");         tempUnit2.add("yr");        tempRelation.add(8760.0);
        tempUnit1.add("h");         tempUnit2.add("dc");        tempRelation.add(87600.0);
        tempUnit1.add("h");         tempUnit2.add("c");         tempRelation.add(876000.0);
        tempUnit1.add("h");         tempUnit2.add("ml");        tempRelation.add(8760000.0);

        tempUnit1.add("d");         tempUnit2.add("wk");        tempRelation.add(7.0);
        tempUnit1.add("d");         tempUnit2.add("fn");        tempRelation.add(14.0);
        tempUnit1.add("d");         tempUnit2.add("mth");       tempRelation.add(30.437765808333335);
        tempUnit1.add("d");         tempUnit2.add("yr");        tempRelation.add(365.0);
        tempUnit1.add("d");         tempUnit2.add("dc");        tempRelation.add(3650.0);
        tempUnit1.add("d");         tempUnit2.add("c");         tempRelation.add(36500.0);
        tempUnit1.add("d");         tempUnit2.add("ml");        tempRelation.add(365000.0);

        tempUnit1.add("wk");        tempUnit2.add("fn");        tempRelation.add(2.0);
        tempUnit1.add("wk");        tempUnit2.add("mth");       tempRelation.add(4.348252258333334);
        tempUnit1.add("wk");        tempUnit2.add("yr");        tempRelation.add(52.142857142857146);
        tempUnit1.add("wk");        tempUnit2.add("dc");        tempRelation.add(521.4285714285714);
        tempUnit1.add("wk");        tempUnit2.add("c");         tempRelation.add(5214.285714285715);
        tempUnit1.add("wk");        tempUnit2.add("ml");        tempRelation.add(52142.857142857145);

        tempUnit1.add("fn");        tempUnit2.add("mth");       tempRelation.add(2.174126129166667);
        tempUnit1.add("fn");        tempUnit2.add("yr");        tempRelation.add(26.071428571428573);
        tempUnit1.add("fn");        tempUnit2.add("dc");        tempRelation.add(260.7142857142857);
        tempUnit1.add("fn");        tempUnit2.add("c");         tempRelation.add(2607.1428571428573);
        tempUnit1.add("fn");        tempUnit2.add("ml");        tempRelation.add(26071.428571428572);

        tempUnit1.add("mth");       tempUnit2.add("yr");        tempRelation.add(11.991681725209585);
        tempUnit1.add("mth");       tempUnit2.add("dc");        tempRelation.add(119.91681725209584);
        tempUnit1.add("mth");       tempUnit2.add("c");         tempRelation.add(1199.1681725209585);
        tempUnit1.add("mth");       tempUnit2.add("ml");        tempRelation.add(11991.681725209584);

        tempUnit1.add("yr");        tempUnit2.add("dc");        tempRelation.add(10.0);
        tempUnit1.add("yr");        tempUnit2.add("c");         tempRelation.add(100.0);
        tempUnit1.add("yr");        tempUnit2.add("ml");        tempRelation.add(1000.0);

        tempUnit1.add("dc");        tempUnit2.add("c");         tempRelation.add(10.0);
        tempUnit1.add("dc");        tempUnit2.add("ml");        tempRelation.add(100.0);

        tempUnit1.add("c");         tempUnit2.add("ml");        tempRelation.add(10.0);

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
        if (unit.equals("millenia")){
            return "ml";
        }
        return unit;
    }

}
