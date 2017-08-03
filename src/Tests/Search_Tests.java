package Tests;

import Math_Evaluation_Library.Miscellaneous.Scripts;
import Math_Evaluation_Library.Search;

/**
 * Created by Antonio on 2017-07-13.
 */
public class Search_Tests extends _Tests_{

    public Search_Tests(String name){
        super(name);
    }

    @Override
    public void run(){
        String[] strArray = {"{x}", "{y}"};
        assertionTest(Search.contains(strArray, "{x}"));
        assertionTest(Search.binarySearch(strArray, "{y}") != -1);
        assertionTest(Search.binarySearch(strArray, "{z}"), -1);


        char[] charArray = {'a', 'b', 'c'};
        assertionTest(Search.contains(charArray, 'a'));
        assertionTest(Search.binarySearch(charArray, 'b') != -1);
        assertionTest(Search.binarySearch(charArray, 'c'), 2);
        assertionTest(Search.binarySearch(charArray, 'd'), -1);

        charArray = new char[]{')', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'x', 'y', 'γ', 'η', 'π', 'ϕ'};
        assertionTest(Search.contains(charArray, '3'));
        assertionTest(Search.contains(charArray, 'π'));
    }

}
