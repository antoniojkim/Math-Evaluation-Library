package Math_Evaluation_Library.UnitConversion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antonio on 2017-07-22.
 */
public class FileSizeConverter extends UnitConverter {

    public FileSizeConverter(){

        units     = new String[]{"bit", "B",    "Kbit",    "KB",       "Mbit",    "MB",       "Gbit",    "GB",       "Tbit",    "TB",       "Pbit",    "PB",       "Ebit",   "EB",      "Zbit",     "ZB",        "Ybit",     "YB"};
        longUnits = new String[]{"bit", "byte", "kilobit", "kilobyte", "megabit", "megabyte", "gigabit", "gigabyte", "terabit", "terabyte", "petabit", "petabyte", "exabit", "exabyte", "zettabit", "zettabyte", "yottabit", "yottabyte"};

        List<String> tempUnit1 = new ArrayList<>();
        List<String> tempUnit2 = new ArrayList<>();
        List<Double> tempRelation = new ArrayList<>();

        tempUnit1.add("bit");       tempUnit2.add("B");         tempRelation.add(8.0);
        tempUnit1.add("bit");       tempUnit2.add("Kbit");      tempRelation.add(1024.0);
        tempUnit1.add("bit");       tempUnit2.add("KB");        tempRelation.add(8192.0);
        tempUnit1.add("bit");       tempUnit2.add("Mbit");      tempRelation.add(1048576.0);
        tempUnit1.add("bit");       tempUnit2.add("MB");        tempRelation.add(8388608.0);
        tempUnit1.add("bit");       tempUnit2.add("Gbit");      tempRelation.add(1.073741824E9);
        tempUnit1.add("bit");       tempUnit2.add("GB");        tempRelation.add(8.589934592E9);
        tempUnit1.add("bit");       tempUnit2.add("Tbit");      tempRelation.add(1.099511627776E12);
        tempUnit1.add("bit");       tempUnit2.add("TB");        tempRelation.add(8.795755265206E12);
        tempUnit1.add("bit");       tempUnit2.add("Pbit");      tempRelation.add(1.125899911454E15);
        tempUnit1.add("bit");       tempUnit2.add("PB");        tempRelation.add(9.006853391570944E15);
        tempUnit1.add("bit");       tempUnit2.add("Ebit");      tempRelation.add(1.152921509328896E18);
        tempUnit1.add("bit");       tempUnit2.add("EB");        tempRelation.add(9.2230178729686467E18);
        tempUnit1.add("bit");       tempUnit2.add("Zbit");      tempRelation.add(1.1805916255527895E21);
        tempUnit1.add("bit");       tempUnit2.add("ZB");        tempRelation.add(9.444370301919895E21);
        tempUnit1.add("bit");       tempUnit2.add("Ybit");      tempRelation.add(1.2089258245660565E24);
        tempUnit1.add("bit");       tempUnit2.add("YB");        tempRelation.add(9.671035189165972E24);

        tempUnit1.add("B");         tempUnit2.add("Kbit");      tempRelation.add(128.0);
        tempUnit1.add("B");         tempUnit2.add("KB");        tempRelation.add(1024.0);
        tempUnit1.add("B");         tempUnit2.add("Mbit");      tempRelation.add(131072.0);
        tempUnit1.add("B");         tempUnit2.add("MB");        tempRelation.add(1048576.0);
        tempUnit1.add("B");         tempUnit2.add("Gbit");      tempRelation.add(1.34217728E8);
        tempUnit1.add("B");         tempUnit2.add("GB");        tempRelation.add(1.073741824E9);
        tempUnit1.add("B");         tempUnit2.add("Tbit");      tempRelation.add(1.37438953472E11);
        tempUnit1.add("B");         tempUnit2.add("TB");        tempRelation.add(1.09946940815075E12);
        tempUnit1.add("B");         tempUnit2.add("Pbit");      tempRelation.add(1.4073748893175E14);
        tempUnit1.add("B");         tempUnit2.add("PB");        tempRelation.add(1.125856673946368E15);
        tempUnit1.add("B");         tempUnit2.add("Ebit");      tempRelation.add(1.44115188666112E17);
        tempUnit1.add("B");         tempUnit2.add("EB");        tempRelation.add(1.15287723412108083E18);
        tempUnit1.add("B");         tempUnit2.add("Zbit");      tempRelation.add(1.475739531940987E20);
        tempUnit1.add("B");         tempUnit2.add("ZB");        tempRelation.add(1.1805462877399869E21);
        tempUnit1.add("B");         tempUnit2.add("Ybit");      tempRelation.add(1.5111572807075706E23);
        tempUnit1.add("B");         tempUnit2.add("YB");        tempRelation.add(1.2088793986457465E24);

        tempUnit1.add("Kbit");      tempUnit2.add("KB");        tempRelation.add(8.0);
        tempUnit1.add("Kbit");      tempUnit2.add("Mbit");      tempRelation.add(1024.0);
        tempUnit1.add("Kbit");      tempUnit2.add("MB");        tempRelation.add(8192.0);
        tempUnit1.add("Kbit");      tempUnit2.add("Gbit");      tempRelation.add(1048576.0);
        tempUnit1.add("Kbit");      tempUnit2.add("GB");        tempRelation.add(8388608.0);
        tempUnit1.add("Kbit");      tempUnit2.add("Tbit");      tempRelation.add(1.073741824E9);
        tempUnit1.add("Kbit");      tempUnit2.add("TB");        tempRelation.add(8.589604751177734E9);
        tempUnit1.add("Kbit");      tempUnit2.add("Pbit");      tempRelation.add(1.0995116322792969E12);
        tempUnit1.add("Kbit");      tempUnit2.add("PB");        tempRelation.add(8.795755265206E12);
        tempUnit1.add("Kbit");      tempUnit2.add("Ebit");      tempRelation.add(1.125899911454E15);
        tempUnit1.add("Kbit");      tempUnit2.add("EB");        tempRelation.add(9.006853391570944E15);
        tempUnit1.add("Kbit");      tempUnit2.add("Zbit");      tempRelation.add(1.152921509328896E18);
        tempUnit1.add("Kbit");      tempUnit2.add("ZB");        tempRelation.add(9.2230178729686477E18);
        tempUnit1.add("Kbit");      tempUnit2.add("Ybit");      tempRelation.add(1.1805916255527895E21);
        tempUnit1.add("Kbit");      tempUnit2.add("YB");        tempRelation.add(9.444370301919894E21);

        tempUnit1.add("KB");        tempUnit2.add("Mbit");      tempRelation.add(128.0);
        tempUnit1.add("KB");        tempUnit2.add("MB");        tempRelation.add(1024.0);
        tempUnit1.add("KB");        tempUnit2.add("Gbit");      tempRelation.add(131072.0);
        tempUnit1.add("KB");        tempUnit2.add("GB");        tempRelation.add(1048576.0);
        tempUnit1.add("KB");        tempUnit2.add("Tbit");      tempRelation.add(1.34217728E8);
        tempUnit1.add("KB");        tempUnit2.add("TB");        tempRelation.add(1.0737005938972168E9);
        tempUnit1.add("KB");        tempUnit2.add("Pbit");      tempRelation.add(1.374389540349121E11);
        tempUnit1.add("KB");        tempUnit2.add("PB");        tempRelation.add(1.09946940815075E12);
        tempUnit1.add("KB");        tempUnit2.add("Ebit");      tempRelation.add(1.4073748893175E14);
        tempUnit1.add("KB");        tempUnit2.add("EB");        tempRelation.add(1.125856673946368E15);
        tempUnit1.add("KB");        tempUnit2.add("Zbit");      tempRelation.add(1.44115188666112E17);
        tempUnit1.add("KB");        tempUnit2.add("ZB");        tempRelation.add(1.15287723412108096E18);
        tempUnit1.add("KB");        tempUnit2.add("Ybit");      tempRelation.add(1.475739531940987E20);
        tempUnit1.add("KB");        tempUnit2.add("YB");        tempRelation.add(1.1805462877399868E21);

        tempUnit1.add("Mbit");      tempUnit2.add("MB");        tempRelation.add(8.0);
        tempUnit1.add("Mbit");      tempUnit2.add("Gbit");      tempRelation.add(1024.0);
        tempUnit1.add("Mbit");      tempUnit2.add("GB");        tempRelation.add(8192.0);
        tempUnit1.add("Mbit");      tempUnit2.add("Tbit");      tempRelation.add(1048576.0);
        tempUnit1.add("Mbit");      tempUnit2.add("TB");        tempRelation.add(8388285.889822006);
        tempUnit1.add("Mbit");      tempUnit2.add("Pbit");      tempRelation.add(1.0737418283977509E9);
        tempUnit1.add("Mbit");      tempUnit2.add("PB");        tempRelation.add(8.589604751177734E9);
        tempUnit1.add("Mbit");      tempUnit2.add("Ebit");      tempRelation.add(1.0995116322792969E12);
        tempUnit1.add("Mbit");      tempUnit2.add("EB");        tempRelation.add(8.795755265206E12);
        tempUnit1.add("Mbit");      tempUnit2.add("Zbit");      tempRelation.add(1.125899911454E15);
        tempUnit1.add("Mbit");      tempUnit2.add("ZB");        tempRelation.add(9.006853391570945E15);
        tempUnit1.add("Mbit");      tempUnit2.add("Ybit");      tempRelation.add(1.152921509328896E18);
        tempUnit1.add("Mbit");      tempUnit2.add("YB");        tempRelation.add(9.2230178729686467E18);

        tempUnit1.add("MB");        tempUnit2.add("Gbit");      tempRelation.add(128.0);
        tempUnit1.add("MB");        tempUnit2.add("GB");        tempRelation.add(1024.0);
        tempUnit1.add("MB");        tempUnit2.add("Tbit");      tempRelation.add(131072.0);
        tempUnit1.add("MB");        tempUnit2.add("TB");        tempRelation.add(1048535.7362277508);
        tempUnit1.add("MB");        tempUnit2.add("Pbit");      tempRelation.add(1.3421772854971886E8);
        tempUnit1.add("MB");        tempUnit2.add("PB");        tempRelation.add(1.0737005938972168E9);
        tempUnit1.add("MB");        tempUnit2.add("Ebit");      tempRelation.add(1.374389540349121E11);
        tempUnit1.add("MB");        tempUnit2.add("EB");        tempRelation.add(1.09946940815075E12);
        tempUnit1.add("MB");        tempUnit2.add("Zbit");      tempRelation.add(1.4073748893175E14);
        tempUnit1.add("MB");        tempUnit2.add("ZB");        tempRelation.add(1.1258566739463681E15);
        tempUnit1.add("MB");        tempUnit2.add("Ybit");      tempRelation.add(1.44115188666112E17);
        tempUnit1.add("MB");        tempUnit2.add("YB");        tempRelation.add(1.15287723412108083E18);

        tempUnit1.add("Gbit");      tempUnit2.add("GB");        tempRelation.add(8.0);
        tempUnit1.add("Gbit");      tempUnit2.add("Tbit");      tempRelation.add(1024.0);
        tempUnit1.add("Gbit");      tempUnit2.add("TB");        tempRelation.add(8191.685439279303);
        tempUnit1.add("Gbit");      tempUnit2.add("Pbit");      tempRelation.add(1048576.0042946786);
        tempUnit1.add("Gbit");      tempUnit2.add("PB");        tempRelation.add(8388285.889822006);
        tempUnit1.add("Gbit");      tempUnit2.add("Ebit");      tempRelation.add(1.0737418283977509E9);
        tempUnit1.add("Gbit");      tempUnit2.add("EB");        tempRelation.add(8.589604751177734E9);
        tempUnit1.add("Gbit");      tempUnit2.add("Zbit");      tempRelation.add(1.0995116322792969E12);
        tempUnit1.add("Gbit");      tempUnit2.add("ZB");        tempRelation.add(8.795755265206001E12);
        tempUnit1.add("Gbit");      tempUnit2.add("Ybit");      tempRelation.add(1.125899911454E15);
        tempUnit1.add("Gbit");      tempUnit2.add("YB");        tempRelation.add(9.006853391570944E15);

        tempUnit1.add("GB");        tempUnit2.add("Tbit");      tempRelation.add(128.0);
        tempUnit1.add("GB");        tempUnit2.add("TB");        tempRelation.add(1023.9606799099129);
        tempUnit1.add("GB");        tempUnit2.add("Pbit");      tempRelation.add(131072.00053683482);
        tempUnit1.add("GB");        tempUnit2.add("PB");        tempRelation.add(1048535.7362277508);
        tempUnit1.add("GB");        tempUnit2.add("Ebit");      tempRelation.add(1.3421772854971886E8);
        tempUnit1.add("GB");        tempUnit2.add("EB");        tempRelation.add(1.0737005938972168E9);
        tempUnit1.add("GB");        tempUnit2.add("Zbit");      tempRelation.add(1.374389540349121E11);
        tempUnit1.add("GB");        tempUnit2.add("ZB");        tempRelation.add(1.0994694081507501E12);
        tempUnit1.add("GB");        tempUnit2.add("Ybit");      tempRelation.add(1.4073748893175E14);
        tempUnit1.add("GB");        tempUnit2.add("YB");        tempRelation.add(1.125856673946368E15);

        tempUnit1.add("Tbit");      tempUnit2.add("TB");        tempRelation.add(7.999692811796194);
        tempUnit1.add("Tbit");      tempUnit2.add("Pbit");      tempRelation.add(1024.000004194022);
        tempUnit1.add("Tbit");      tempUnit2.add("PB");        tempRelation.add(8191.685439279303);
        tempUnit1.add("Tbit");      tempUnit2.add("Ebit");      tempRelation.add(1048576.0042946786);
        tempUnit1.add("Tbit");      tempUnit2.add("EB");        tempRelation.add(8388285.889822006);
        tempUnit1.add("Tbit");      tempUnit2.add("Zbit");      tempRelation.add(1.0737418283977509E9);
        tempUnit1.add("Tbit");      tempUnit2.add("ZB");        tempRelation.add(8.589604751177735E9);
        tempUnit1.add("Tbit");      tempUnit2.add("Ybit");      tempRelation.add(1.0995116322792969E12);
        tempUnit1.add("Tbit");      tempUnit2.add("YB");        tempRelation.add(8.795755265206E12);

        tempUnit1.add("TB");        tempUnit2.add("Pbit");      tempRelation.add(128.0049157242702);
        tempUnit1.add("TB");        tempUnit2.add("PB");        tempRelation.add(1024.0);
        tempUnit1.add("TB");        tempUnit2.add("Ebit");      tempRelation.add(131077.0337016527);
        tempUnit1.add("TB");        tempUnit2.add("EB");        tempRelation.add(1048576.0);
        tempUnit1.add("TB");        tempUnit2.add("Zbit");      tempRelation.add(1.3422288251049235E8);
        tempUnit1.add("TB");        tempUnit2.add("ZB");        tempRelation.add(1.0737418240000002E9);
        tempUnit1.add("TB");        tempUnit2.add("Ybit");      tempRelation.add(1.3744423169074417E11);
        tempUnit1.add("TB");        tempUnit2.add("YB");        tempRelation.add(1.099511627776E12);

        tempUnit1.add("Pbit");      tempUnit2.add("PB");        tempRelation.add(7.999692779031656);
        tempUnit1.add("Pbit");      tempUnit2.add("Ebit");      tempRelation.add(1024.0);
        tempUnit1.add("Pbit");      tempUnit2.add("EB");        tempRelation.add(8191.685405728415);
        tempUnit1.add("Pbit");      tempUnit2.add("Zbit");      tempRelation.add(1048576.0);
        tempUnit1.add("Pbit");      tempUnit2.add("ZB");        tempRelation.add(8388285.855465899);
        tempUnit1.add("Pbit");      tempUnit2.add("Ybit");      tempRelation.add(1.073741824E9);
        tempUnit1.add("Pbit");      tempUnit2.add("YB");        tempRelation.add(8.589604715997079E9);

        tempUnit1.add("PB");        tempUnit2.add("Ebit");      tempRelation.add(128.0049157242702);
        tempUnit1.add("PB");        tempUnit2.add("EB");        tempRelation.add(1024.0);
        tempUnit1.add("PB");        tempUnit2.add("Zbit");      tempRelation.add(131077.0337016527);
        tempUnit1.add("PB");        tempUnit2.add("ZB");        tempRelation.add(1048576.0000000002);
        tempUnit1.add("PB");        tempUnit2.add("Ybit");      tempRelation.add(1.3422288251049235E8);
        tempUnit1.add("PB");        tempUnit2.add("YB");        tempRelation.add(1.073741824E9);

        tempUnit1.add("Ebit");      tempUnit2.add("EB");        tempRelation.add(7.999692779031656);
        tempUnit1.add("Ebit");      tempUnit2.add("Zbit");      tempRelation.add(1024.0);
        tempUnit1.add("Ebit");      tempUnit2.add("ZB");        tempRelation.add(8191.685405728417);
        tempUnit1.add("Ebit");      tempUnit2.add("Ybit");      tempRelation.add(1048576.0);
        tempUnit1.add("Ebit");      tempUnit2.add("YB");        tempRelation.add(8388285.855465897);

        tempUnit1.add("EB");        tempUnit2.add("Zbit");      tempRelation.add(128.0049157242702);
        tempUnit1.add("EB");        tempUnit2.add("ZB");        tempRelation.add(1024.0);
        tempUnit1.add("EB");        tempUnit2.add("Ybit");      tempRelation.add(131077.0337016527);
        tempUnit1.add("EB");        tempUnit2.add("YB");        tempRelation.add(1048576.0);

        tempUnit1.add("Zbit");      tempUnit2.add("ZB");        tempRelation.add(7.999692779031657);
        tempUnit1.add("Zbit");      tempUnit2.add("Ybit");      tempRelation.add(1024.0);
        tempUnit1.add("Zbit");      tempUnit2.add("YB");        tempRelation.add(8191.685405728415);

        tempUnit1.add("ZB");        tempUnit2.add("Ybit");      tempRelation.add(128.00491572427018);
        tempUnit1.add("ZB");        tempUnit2.add("YB");        tempRelation.add(1023.9999999999998);

        tempUnit1.add("Ybit");      tempUnit2.add("YB");        tempRelation.add(7.999692779031656);

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
