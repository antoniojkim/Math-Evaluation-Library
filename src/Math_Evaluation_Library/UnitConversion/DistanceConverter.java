package Math_Evaluation_Library.UnitConversion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antonio on 2017-07-22.
 */
public class DistanceConverter extends UnitConverter {

    public DistanceConverter(){

        units =      new String[]{"nm",        "um",         "mm",         "cm",         "dm",        "m",     "dam",       "hm",         "km",        "in",   "ft",   "yd",   "mi",   "nmi"};
        longUnits =  new String[]{"nanometer", "micrometer", "millimeter", "centimeter", "decimeter", "meter", "decameter", "hectometer", "kilometer", "inch", "feet", "yard", "mile", "nautical mile"};

        List<String> tempUnit1 = new ArrayList<>();
        List<String> tempUnit2 = new ArrayList<>();
        List<Double> tempRelation = new ArrayList<>();

        tempUnit1.add("nm");    tempUnit2.add("um");    tempRelation.add(1E4);
        tempUnit1.add("nm");    tempUnit2.add("mm");    tempRelation.add(1E6);
        tempUnit1.add("nm");    tempUnit2.add("cm");    tempRelation.add(1E7);
        tempUnit1.add("nm");    tempUnit2.add("dm");    tempRelation.add(1E8);
        tempUnit1.add("nm");    tempUnit2.add("m");     tempRelation.add(1E9);
        tempUnit1.add("nm");    tempUnit2.add("dam");   tempRelation.add(1E10);
        tempUnit1.add("nm");    tempUnit2.add("hm");    tempRelation.add(1E11);
        tempUnit1.add("nm");    tempUnit2.add("km");    tempRelation.add(1E12);
        tempUnit1.add("nm");    tempUnit2.add("in");    tempRelation.add(2.54E7);
        tempUnit1.add("nm");    tempUnit2.add("ft");    tempRelation.add(3.048E8);
        tempUnit1.add("nm");    tempUnit2.add("yd");    tempRelation.add(9.144E8);
        tempUnit1.add("nm");    tempUnit2.add("mi");    tempRelation.add(1.609344E12);
        tempUnit1.add("nm");    tempUnit2.add("nmi");   tempRelation.add(1.852E12);

        tempUnit1.add("um");    tempUnit2.add("mm");    tempRelation.add(1E3);
        tempUnit1.add("um");    tempUnit2.add("cm");    tempRelation.add(1E4);
        tempUnit1.add("um");    tempUnit2.add("dm");    tempRelation.add(1E5);
        tempUnit1.add("um");    tempUnit2.add("m");     tempRelation.add(1E6);
        tempUnit1.add("um");    tempUnit2.add("dam");   tempRelation.add(1E7);
        tempUnit1.add("um");    tempUnit2.add("hm");    tempRelation.add(1E8);
        tempUnit1.add("um");    tempUnit2.add("km");    tempRelation.add(1E9);
        tempUnit1.add("um");    tempUnit2.add("in");    tempRelation.add(2.54E4);
        tempUnit1.add("um");    tempUnit2.add("ft");    tempRelation.add(3.048E5);
        tempUnit1.add("um");    tempUnit2.add("yd");    tempRelation.add(9.144E5);
        tempUnit1.add("um");    tempUnit2.add("mi");    tempRelation.add(1.609344E9);
        tempUnit1.add("um");    tempUnit2.add("nmi");   tempRelation.add(1.852E9);

        tempUnit1.add("mm");    tempUnit2.add("cm");    tempRelation.add(1E1);
        tempUnit1.add("mm");    tempUnit2.add("dm");    tempRelation.add(1E2);
        tempUnit1.add("mm");    tempUnit2.add("m");     tempRelation.add(1E3);
        tempUnit1.add("mm");    tempUnit2.add("dam");   tempRelation.add(1E4);
        tempUnit1.add("mm");    tempUnit2.add("hm");    tempRelation.add(1E5);
        tempUnit1.add("mm");    tempUnit2.add("km");    tempRelation.add(1E6);
        tempUnit1.add("mm");    tempUnit2.add("in");    tempRelation.add(25.4);
        tempUnit1.add("mm");    tempUnit2.add("ft");    tempRelation.add(304.8);
        tempUnit1.add("mm");    tempUnit2.add("yd");    tempRelation.add(914.4);
        tempUnit1.add("mm");    tempUnit2.add("mi");    tempRelation.add(1.609344E6);
        tempUnit1.add("mm");    tempUnit2.add("nmi");   tempRelation.add(1.852E6);

        tempUnit1.add("cm");    tempUnit2.add("dm");    tempRelation.add(1E1);
        tempUnit1.add("cm");    tempUnit2.add("m");     tempRelation.add(1E2);
        tempUnit1.add("cm");    tempUnit2.add("dam");   tempRelation.add(1E3);
        tempUnit1.add("cm");    tempUnit2.add("hm");    tempRelation.add(1E4);
        tempUnit1.add("cm");    tempUnit2.add("km");    tempRelation.add(1E5);
        tempUnit1.add("cm");    tempUnit2.add("in");    tempRelation.add(2.54);
        tempUnit1.add("cm");    tempUnit2.add("ft");    tempRelation.add(30.48);
        tempUnit1.add("cm");    tempUnit2.add("yd");    tempRelation.add(91.44);
        tempUnit1.add("cm");    tempUnit2.add("mi");    tempRelation.add(160934.4);
        tempUnit1.add("cm");    tempUnit2.add("nmi");   tempRelation.add(185200.0);

        tempUnit1.add("dm");    tempUnit2.add("m");     tempRelation.add(1E1);
        tempUnit1.add("dm");    tempUnit2.add("dam");   tempRelation.add(1E2);
        tempUnit1.add("dm");    tempUnit2.add("hm");    tempRelation.add(1E3);
        tempUnit1.add("dm");    tempUnit2.add("km");    tempRelation.add(1E4);
        tempUnit1.add("dm");    tempUnit2.add("in");    tempRelation.add(0.254);
        tempUnit1.add("dm");    tempUnit2.add("ft");    tempRelation.add(3.048);
        tempUnit1.add("dm");    tempUnit2.add("yd");    tempRelation.add(9.144);
        tempUnit1.add("dm");    tempUnit2.add("mi");    tempRelation.add(16093.44);
        tempUnit1.add("dm");    tempUnit2.add("nmi");   tempRelation.add(18520.0);

        tempUnit1.add("m");     tempUnit2.add("dam");   tempRelation.add(1E1);
        tempUnit1.add("m");     tempUnit2.add("hm");    tempRelation.add(1E2);
        tempUnit1.add("m");     tempUnit2.add("km");    tempRelation.add(1E3);
        tempUnit1.add("m");     tempUnit2.add("in");    tempRelation.add(0.0254);
        tempUnit1.add("m");     tempUnit2.add("ft");    tempRelation.add(0.3048);
        tempUnit1.add("m");     tempUnit2.add("yd");    tempRelation.add(0.9144);
        tempUnit1.add("m");     tempUnit2.add("mi");    tempRelation.add(1609.344);
        tempUnit1.add("m");     tempUnit2.add("nmi");   tempRelation.add(1852.0);

        tempUnit1.add("dam");   tempUnit2.add("hm");    tempRelation.add(1E1);
        tempUnit1.add("dam");   tempUnit2.add("km");    tempRelation.add(1E2);
        tempUnit1.add("dam");   tempUnit2.add("in");    tempRelation.add(0.00254);
        tempUnit1.add("dam");   tempUnit2.add("ft");    tempRelation.add(0.03048);
        tempUnit1.add("dam");   tempUnit2.add("yd");    tempRelation.add(0.09144);
        tempUnit1.add("dam");   tempUnit2.add("mi");    tempRelation.add(160.9344);
        tempUnit1.add("dam");   tempUnit2.add("nmi");   tempRelation.add(185.2);

        tempUnit1.add("hm");    tempUnit2.add("km");    tempRelation.add(1E1);
        tempUnit1.add("hm");    tempUnit2.add("in");    tempRelation.add(0.000254);
        tempUnit1.add("hm");    tempUnit2.add("ft");    tempRelation.add(0.003048);
        tempUnit1.add("hm");    tempUnit2.add("yd");    tempRelation.add(0.009144);
        tempUnit1.add("hm");    tempUnit2.add("mi");    tempRelation.add(16.09344);
        tempUnit1.add("hm");    tempUnit2.add("nmi");   tempRelation.add(18.52);

        tempUnit1.add("km");    tempUnit2.add("in");    tempRelation.add(0.0000254);
        tempUnit1.add("km");    tempUnit2.add("ft");    tempRelation.add(0.0003048);
        tempUnit1.add("km");    tempUnit2.add("yd");    tempRelation.add(0.0009144);
        tempUnit1.add("km");    tempUnit2.add("mi");    tempRelation.add(1.609344);
        tempUnit1.add("km");    tempUnit2.add("nmi");   tempRelation.add(1.852);

        tempUnit1.add("in");    tempUnit2.add("ft");    tempRelation.add(12.0);
        tempUnit1.add("in");    tempUnit2.add("yd");    tempRelation.add(36.0);
        tempUnit1.add("in");    tempUnit2.add("mi");    tempRelation.add(63360.0);
        tempUnit1.add("in");    tempUnit2.add("nmi");   tempRelation.add(72913.4);

        tempUnit1.add("ft");    tempUnit2.add("yd");    tempRelation.add(3.0);
        tempUnit1.add("ft");    tempUnit2.add("mi");    tempRelation.add(5280.0);
        tempUnit1.add("ft");    tempUnit2.add("nmi");   tempRelation.add(6076.12);

        tempUnit1.add("yd");    tempUnit2.add("mi");    tempRelation.add(1760.0);
        tempUnit1.add("yd");    tempUnit2.add("nmi");   tempRelation.add(2025.37);

        tempUnit1.add("mi");    tempUnit2.add("nmi");   tempRelation.add(1.15078);

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
