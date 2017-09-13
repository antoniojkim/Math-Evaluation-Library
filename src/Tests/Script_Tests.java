package Tests;

import Math_Evaluation_Library.Constants.Scripts;

/**
 * Created by Antonio on 2017-07-15.
 */
public class Script_Tests extends _Tests_{

    public Script_Tests(String name) {
        super(name);
    }

    @Override
    public void run(){

        assertionTest(Scripts.isSuperScript('¹'));
        assertionTest(Scripts.isSubScript('₂'));
        assertionTest(!Scripts.isSubScript('³'));
        assertionTest(Scripts.getSuperScript('4'), '⁴');
        assertionTest(Scripts.getSubScript('5'), '₅');
        assertionTest(Scripts.toSuperScript("67"), "⁶⁷");
        assertionTest(Scripts.toSubScript("89"), "₈₉");
        assertionTest(Scripts.toRegularScript("⁺₋=₍⁾"), "+-=()");

    }

}
