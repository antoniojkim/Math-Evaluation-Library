package Math_Evaluation_Library.UnitConversion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antonio on 2017-07-22.
 */
public class VolumeConverter extends UnitConverter {

    public VolumeConverter(){

        units     = new String[]{"ul",         "ml",         "cl",         "l",     "dal",       "hl",         "kl",        "mm³",              "cm³",              "dm³",             "m³",          "in³",        "ft³",        "yd³",        "gal",    "pt",   "qt"};
        longUnits = new String[]{"microliter", "milliliter", "centiliter", "liter", "decaliter", "hectoliter", "kiloliter", "cubic millimeter", "cubic centimeter", "cubic decimeter", "cubic meter", "cubic inch", "cubic feet", "cubic yard", "gallon", "pint", "quart"};

        List<String> tempUnit1 = new ArrayList<>();
        List<String> tempUnit2 = new ArrayList<>();
        List<Double> tempRelation = new ArrayList<>();

        tempUnit1.add("ul");        tempUnit2.add("ml");        tempRelation.add(1000.0);
        tempUnit1.add("ul");        tempUnit2.add("cl");        tempRelation.add(10000.0);
        tempUnit1.add("ul");        tempUnit2.add("l");         tempRelation.add(1000000.0);
        tempUnit1.add("ul");        tempUnit2.add("dal");       tempRelation.add(1.0E7);
        tempUnit1.add("ul");        tempUnit2.add("hl");        tempRelation.add(1.0E8);
        tempUnit1.add("ul");        tempUnit2.add("kl");        tempRelation.add(1.0E9);
        tempUnit1.add("ul");        tempUnit2.add("mm³");       tempRelation.add(1.0);
        tempUnit1.add("ul");        tempUnit2.add("cm³");       tempRelation.add(1000.0);
        tempUnit1.add("ul");        tempUnit2.add("dm³");       tempRelation.add(1000000.0);
        tempUnit1.add("ul");        tempUnit2.add("m³");        tempRelation.add(1.0E9);
        tempUnit1.add("ul");        tempUnit2.add("in³");       tempRelation.add(16387.064);
        tempUnit1.add("ul");        tempUnit2.add("ft³");       tempRelation.add(2.8316846592E7);
        tempUnit1.add("ul");        tempUnit2.add("yd³");       tempRelation.add(7.64554857984E8);
        tempUnit1.add("ul");        tempUnit2.add("gal");       tempRelation.add(3785411.784);
        tempUnit1.add("ul");        tempUnit2.add("pt");        tempRelation.add(473176.473);
        tempUnit1.add("ul");        tempUnit2.add("qt");        tempRelation.add(946352.946);

        tempUnit1.add("ml");        tempUnit2.add("cl");        tempRelation.add(10.0);
        tempUnit1.add("ml");        tempUnit2.add("l");         tempRelation.add(1000.0);
        tempUnit1.add("ml");        tempUnit2.add("dal");       tempRelation.add(10000.0);
        tempUnit1.add("ml");        tempUnit2.add("hl");        tempRelation.add(100000.0);
        tempUnit1.add("ml");        tempUnit2.add("kl");        tempRelation.add(1000000.0);
        tempUnit1.add("ml");        tempUnit2.add("mm³");       tempRelation.add(0.001);
        tempUnit1.add("ml");        tempUnit2.add("cm³");       tempRelation.add(1.0);
        tempUnit1.add("ml");        tempUnit2.add("dm³");       tempRelation.add(1000.0);
        tempUnit1.add("ml");        tempUnit2.add("m³");        tempRelation.add(1000000.0);
        tempUnit1.add("ml");        tempUnit2.add("in³");       tempRelation.add(16.387064);
        tempUnit1.add("ml");        tempUnit2.add("ft³");       tempRelation.add(28316.846592);
        tempUnit1.add("ml");        tempUnit2.add("yd³");       tempRelation.add(764554.857984);
        tempUnit1.add("ml");        tempUnit2.add("gal");       tempRelation.add(3785.411784);
        tempUnit1.add("ml");        tempUnit2.add("pt");        tempRelation.add(473.176473);
        tempUnit1.add("ml");        tempUnit2.add("qt");        tempRelation.add(946.352946);

        tempUnit1.add("cl");        tempUnit2.add("l");         tempRelation.add(100.0);
        tempUnit1.add("cl");        tempUnit2.add("dal");       tempRelation.add(1000.0);
        tempUnit1.add("cl");        tempUnit2.add("hl");        tempRelation.add(10000.0);
        tempUnit1.add("cl");        tempUnit2.add("kl");        tempRelation.add(100000.0);
        tempUnit1.add("cl");        tempUnit2.add("mm³");       tempRelation.add(1.0E-4);
        tempUnit1.add("cl");        tempUnit2.add("cm³");       tempRelation.add(0.1);
        tempUnit1.add("cl");        tempUnit2.add("dm³");       tempRelation.add(100.0);
        tempUnit1.add("cl");        tempUnit2.add("m³");        tempRelation.add(100000.0);
        tempUnit1.add("cl");        tempUnit2.add("in³");       tempRelation.add(1.6387064);
        tempUnit1.add("cl");        tempUnit2.add("ft³");       tempRelation.add(2831.6846592);
        tempUnit1.add("cl");        tempUnit2.add("yd³");       tempRelation.add(76455.4857984);
        tempUnit1.add("cl");        tempUnit2.add("gal");       tempRelation.add(378.5411784);
        tempUnit1.add("cl");        tempUnit2.add("pt");        tempRelation.add(47.3176473);
        tempUnit1.add("cl");        tempUnit2.add("qt");        tempRelation.add(94.6352946);

        tempUnit1.add("l");         tempUnit2.add("dal");       tempRelation.add(10.0);
        tempUnit1.add("l");         tempUnit2.add("hl");        tempRelation.add(100.0);
        tempUnit1.add("l");         tempUnit2.add("kl");        tempRelation.add(1000.0);
        tempUnit1.add("l");         tempUnit2.add("mm³");       tempRelation.add(1.0E-6);
        tempUnit1.add("l");         tempUnit2.add("cm³");       tempRelation.add(0.001);
        tempUnit1.add("l");         tempUnit2.add("dm³");       tempRelation.add(1.0);
        tempUnit1.add("l");         tempUnit2.add("m³");        tempRelation.add(1000.0);
        tempUnit1.add("l");         tempUnit2.add("in³");       tempRelation.add(0.016387064);
        tempUnit1.add("l");         tempUnit2.add("ft³");       tempRelation.add(28.316846592);
        tempUnit1.add("l");         tempUnit2.add("yd³");       tempRelation.add(764.554857984);
        tempUnit1.add("l");         tempUnit2.add("gal");       tempRelation.add(3.785411784);
        tempUnit1.add("l");         tempUnit2.add("pt");        tempRelation.add(0.473176473);
        tempUnit1.add("l");         tempUnit2.add("qt");        tempRelation.add(0.946352946);

        tempUnit1.add("dal");       tempUnit2.add("hl");        tempRelation.add(10.0);
        tempUnit1.add("dal");       tempUnit2.add("kl");        tempRelation.add(100.0);
        tempUnit1.add("dal");       tempUnit2.add("mm³");       tempRelation.add(1.0E-7);
        tempUnit1.add("dal");       tempUnit2.add("cm³");       tempRelation.add(1.0E-4);
        tempUnit1.add("dal");       tempUnit2.add("dm³");       tempRelation.add(0.1);
        tempUnit1.add("dal");       tempUnit2.add("m³");        tempRelation.add(100.0);
        tempUnit1.add("dal");       tempUnit2.add("in³");       tempRelation.add(0.0016387064);
        tempUnit1.add("dal");       tempUnit2.add("ft³");       tempRelation.add(2.8316846592);
        tempUnit1.add("dal");       tempUnit2.add("yd³");       tempRelation.add(76.4554857984);
        tempUnit1.add("dal");       tempUnit2.add("gal");       tempRelation.add(0.3785411784);
        tempUnit1.add("dal");       tempUnit2.add("pt");        tempRelation.add(0.0473176473);
        tempUnit1.add("dal");       tempUnit2.add("qt");        tempRelation.add(0.0946352946);

        tempUnit1.add("hl");        tempUnit2.add("kl");        tempRelation.add(10.0);
        tempUnit1.add("hl");        tempUnit2.add("mm³");       tempRelation.add(1.0E-8);
        tempUnit1.add("hl");        tempUnit2.add("cm³");       tempRelation.add(1.0E-5);
        tempUnit1.add("hl");        tempUnit2.add("dm³");       tempRelation.add(0.01);
        tempUnit1.add("hl");        tempUnit2.add("m³");        tempRelation.add(10.0);
        tempUnit1.add("hl");        tempUnit2.add("in³");       tempRelation.add(1.6387064E-4);
        tempUnit1.add("hl");        tempUnit2.add("ft³");       tempRelation.add(0.28316846592);
        tempUnit1.add("hl");        tempUnit2.add("yd³");       tempRelation.add(7.64554857984);
        tempUnit1.add("hl");        tempUnit2.add("gal");       tempRelation.add(0.03785411784);
        tempUnit1.add("hl");        tempUnit2.add("pt");        tempRelation.add(0.00473176473);
        tempUnit1.add("hl");        tempUnit2.add("qt");        tempRelation.add(0.00946352946);

        tempUnit1.add("kl");        tempUnit2.add("mm³");       tempRelation.add(1E-9);
        tempUnit1.add("kl");        tempUnit2.add("cm³");       tempRelation.add(1E-6);
        tempUnit1.add("kl");        tempUnit2.add("dm³");       tempRelation.add(0.001);
        tempUnit1.add("kl");        tempUnit2.add("m³");        tempRelation.add(1.0);
        tempUnit1.add("kl");        tempUnit2.add("in³");       tempRelation.add(1.6387064E-5);
        tempUnit1.add("kl");        tempUnit2.add("ft³");       tempRelation.add(0.028316846592);
        tempUnit1.add("kl");        tempUnit2.add("yd³");       tempRelation.add(0.764554857984);
        tempUnit1.add("kl");        tempUnit2.add("gal");       tempRelation.add(0.003785411784);
        tempUnit1.add("kl");        tempUnit2.add("pt");        tempRelation.add(4.73176473E-4);
        tempUnit1.add("kl");        tempUnit2.add("qt");        tempRelation.add(9.46352946E-4);

        tempUnit1.add("mm³");       tempUnit2.add("cm³");       tempRelation.add(1000.0);
        tempUnit1.add("mm³");       tempUnit2.add("dm³");       tempRelation.add(1000000.0);
        tempUnit1.add("mm³");       tempUnit2.add("m³");        tempRelation.add(1E9);
        tempUnit1.add("mm³");       tempUnit2.add("in³");       tempRelation.add(16387.064);
        tempUnit1.add("mm³");       tempUnit2.add("ft³");       tempRelation.add(2.8316846592E7);
        tempUnit1.add("mm³");       tempUnit2.add("yd³");       tempRelation.add(7.64554857984E8);
        tempUnit1.add("mm³");       tempUnit2.add("gal");       tempRelation.add(3785411.784);
        tempUnit1.add("mm³");       tempUnit2.add("pt");        tempRelation.add(473176.473);
        tempUnit1.add("mm³");       tempUnit2.add("qt");        tempRelation.add(946352.946);

        tempUnit1.add("cm³");       tempUnit2.add("dm³");       tempRelation.add(1000.0);
        tempUnit1.add("cm³");       tempUnit2.add("m³");        tempRelation.add(1000000.0);
        tempUnit1.add("cm³");       tempUnit2.add("in³");       tempRelation.add(16.387064);
        tempUnit1.add("cm³");       tempUnit2.add("ft³");       tempRelation.add(28316.846592);
        tempUnit1.add("cm³");       tempUnit2.add("yd³");       tempRelation.add(764554.857984);
        tempUnit1.add("cm³");       tempUnit2.add("gal");       tempRelation.add(3785.411784);
        tempUnit1.add("cm³");       tempUnit2.add("pt");        tempRelation.add(473.176473);
        tempUnit1.add("cm³");       tempUnit2.add("qt");        tempRelation.add(946.352946);

        tempUnit1.add("dm³");       tempUnit2.add("m³");        tempRelation.add(1000.0);
        tempUnit1.add("dm³");       tempUnit2.add("in³");       tempRelation.add(0.016387064);
        tempUnit1.add("dm³");       tempUnit2.add("ft³");       tempRelation.add(28.316846592);
        tempUnit1.add("dm³");       tempUnit2.add("yd³");       tempRelation.add(764.554857984);
        tempUnit1.add("dm³");       tempUnit2.add("gal");       tempRelation.add(3.785411784);
        tempUnit1.add("dm³");       tempUnit2.add("pt");        tempRelation.add(0.473176473);
        tempUnit1.add("dm³");       tempUnit2.add("qt");        tempRelation.add(0.946352946);

        tempUnit1.add("m³");        tempUnit2.add("in³");       tempRelation.add(1.6387064E-5);
        tempUnit1.add("m³");        tempUnit2.add("ft³");       tempRelation.add(0.028316846592);
        tempUnit1.add("m³");        tempUnit2.add("yd³");       tempRelation.add(0.764554857984);
        tempUnit1.add("m³");        tempUnit2.add("gal");       tempRelation.add(0.003785411784);
        tempUnit1.add("m³");        tempUnit2.add("pt");        tempRelation.add(4.73176473E-4);
        tempUnit1.add("m³");        tempUnit2.add("qt");        tempRelation.add(9.46352946E-4);

        tempUnit1.add("in³");       tempUnit2.add("ft³");       tempRelation.add(1728.0);
        tempUnit1.add("in³");       tempUnit2.add("yd³");       tempRelation.add(46656.0);
        tempUnit1.add("in³");       tempUnit2.add("gal");       tempRelation.add(231.0);
        tempUnit1.add("in³");       tempUnit2.add("pt");        tempRelation.add(28.875);
        tempUnit1.add("in³");       tempUnit2.add("qt");        tempRelation.add(57.75);

        tempUnit1.add("ft³");       tempUnit2.add("yd³");       tempRelation.add(27.0);
        tempUnit1.add("ft³");       tempUnit2.add("gal");       tempRelation.add(0.13368055555555556);
        tempUnit1.add("ft³");       tempUnit2.add("pt");        tempRelation.add(0.01671006944444444);
        tempUnit1.add("ft³");       tempUnit2.add("qt");        tempRelation.add(0.03342013888888888);

        tempUnit1.add("yd³");       tempUnit2.add("gal");       tempRelation.add(0.0049511316872427975);
        tempUnit1.add("yd³");       tempUnit2.add("pt");        tempRelation.add(6.188914609053497E-4);
        tempUnit1.add("yd³");       tempUnit2.add("qt");        tempRelation.add(0.0012377829218106994);

        tempUnit1.add("gal");       tempUnit2.add("pt");        tempRelation.add(0.125);
        tempUnit1.add("gal");       tempUnit2.add("qt");        tempRelation.add(0.25);

        tempUnit1.add("pt");        tempUnit2.add("qt");        tempRelation.add(2.0);


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
