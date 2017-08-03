/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Math_Evaluation_Library;

import Math_Evaluation_Library.Objects.Fraction;

import java.util.Arrays;
import java.util.List;

/**

@author Antonio
*/
public class Sort {
    
    public static void quicksort(List<Fraction> list){
        Fraction[] array = new Fraction[list.size()];
        for (int a = 0; a<array.length; a++){
            array[a] = list.get(a);
        }
        quicksort(array, true);
        list.clear();
        for (int a = 0; a<array.length; a++){
            list.add(array[a]);
        }
    }
    public static void quicksort(Fraction[] array){
        quicksort(array, true);
    }
    public static void quicksort(Fraction[] array, boolean ascending){
        quicksort(array, 0, array.length-1, ascending);
    }
    private static void quicksort(Fraction[] array, int first, int last, boolean ascending){
        if (first >= last){
            return;
        }
        Fraction pivot = array[first];
        int pos = last;
        if (ascending){
            for (int a = last; a>first; a--){
                if (array[a].getValue() > pivot.getValue()){
                    swap(array, pos, a);
                    pos--;
                }
            }
        }
        else{
            for (int a = last; a>first; a--){
                if (array[a].getValue() < pivot.getValue()){
                    swap(array, pos, a);
                    pos--;
                }
            }
        }
        swap(array, first, pos);
        quicksort(array, first, pos-1, ascending);
        quicksort(array, pos+1, last, ascending);
    }

    public static void quicksort(int[] array){
        quicksort(array, true);
    }
    public static void quicksort(int[] array, boolean ascending){
        quicksort(array, 0, array.length-1, ascending);
    }
    private static void quicksort(int[] array, int first, int last, boolean ascending){
        if (first >= last){
            return;
        }
        int pivot = array[first];
        int pos = last;
        if (ascending){
            for (int a = last; a>first; a--){
                if (array[a] > pivot){
                    swap(array, pos, a);
                    pos--;
                }
            }
        }
        else{
            for (int a = last; a>first; a--){
                if (array[a] < pivot){
                    swap(array, pos, a);
                    pos--;
                }
            }
        }
        swap(array, first, pos);
        quicksort(array, first, pos-1, ascending);
        quicksort(array, pos+1, last, ascending);
    }

    public static void quicksort(char[] array){
        Arrays.sort(array);
    }

    public static void quicksort(double[] array){
        quicksort(array, true);
    }
    public static void quicksort(double[] array, boolean ascending){
        quicksort(array, 0, array.length-1, ascending);
    }
    private static void quicksort(double[] array, int first, int last, boolean ascending){
        if (first >= last){
            return;
        }
        double pivot = array[first];
        int pos = last;
        if (ascending){
            for (int a = last; a>first; a--){
                if (array[a] > pivot){
                    swap(array, pos, a);
                    pos--;
                }
            }
        }
        else{
            for (int a = last; a>first; a--){
                if (array[a] < pivot){
                    swap(array, pos, a);
                    pos--;
                }
            }
        }
        swap(array, first, pos);
        quicksort(array, first, pos-1, ascending);
        quicksort(array, pos+1, last, ascending);
    }
    
    public static void quicksort(String[] array){
        quicksort(array, true);
    }
    public static void quicksort(String[] array, boolean ascending){
        quicksort(array, 0, array.length-1, ascending);
    }
    private static void quicksort(String[] array, int first, int last, boolean ascending){
        if (first >= last) return;
        String pivot = array[first];
        int pos = last;
        if (ascending){
            for (int a = last; a>first; a--){
                if (array[a].compareTo(pivot) > 0){
                    swap(array, pos, a);
                    pos--;
                }
            }
        }
        else{
            for (int a = last; a>first; a--){
                if (array[a].compareTo(pivot) < 0){
                    swap(array, pos, a);
                    pos--;
                }
            }
        }
        swap(array, first, pos);
        quicksort(array, first, pos-1, ascending);
        quicksort(array, pos+1, last, ascending);
    }

    public static void quicksort(String[] array, int[] supplementary){
        quicksort(array, supplementary, true);
    }
    public static void quicksort(String[] array, int[] supplementary, boolean ascending){
        quicksort(array, supplementary, 0, array.length-1, ascending);
    }
    private static void quicksort(String[] array, int[] supplementary, int first, int last, boolean ascending){
        if (first >= last){
            return;
        }
        String pivot = array[first];
        int pos = last;
        if (ascending){
            for (int a = last; a>first; a--){
                if (array[a].compareTo(pivot) > 0){
                    swap(array, supplementary, pos, a);
                    pos--;
                }
            }
        }
        else{
            for (int a = last; a>first; a--){
                if (array[a].compareTo(pivot) < 0){
                    swap(array, supplementary, pos, a);
                    pos--;
                }
            }
        }
        swap(array, supplementary, first, pos);
        quicksort(array, supplementary, first, pos-1, ascending);
        quicksort(array, supplementary, pos+1, last, ascending);
    }

    public static void quicksort(String[] array, String[] supplementary){
        quicksort(array, supplementary, true);
    }
    public static void quicksort(String[] array, String[] supplementary, boolean ascending){
        quicksort(array, supplementary, 0, array.length-1, ascending);
    }
    private static void quicksort(String[] array, String[] supplementary, int first, int last, boolean ascending){
        if (first >= last){
            return;
        }
        String pivot = array[first];
        int pos = last;
        if (ascending){
            for (int a = last; a>first; a--){
                if (array[a].compareTo(pivot) > 0){
                    swap(array, supplementary, pos, a);
                    pos--;
                }
            }
        }
        else{
            for (int a = last; a>first; a--){
                if (array[a].compareTo(pivot) < 0){
                    swap(array, supplementary, pos, a);
                    pos--;
                }
            }
        }
        swap(array, supplementary, first, pos);
        quicksort(array, supplementary, first, pos-1, ascending);
        quicksort(array, supplementary, pos+1, last, ascending);
    }

    public static void quicksort(String[] array, String[] supplementary1, String[] supplementary2){
        quicksort(array, supplementary1, supplementary2, true);
    }
    public static void quicksort(String[] array, String[] supplementary1, String[] supplementary2, boolean ascending){
        quicksort(array, supplementary1, supplementary2, 0, array.length-1, ascending);
    }
    private static void quicksort(String[] array, String[] supplementary1, String[] supplementary2, int first, int last, boolean ascending){
        if (first >= last){
            return;
        }
        String pivot = array[first];
        int pos = last;
        if (ascending){
            for (int a = last; a>first; a--){
                if (array[a].compareTo(pivot) > 0){
                    swap(array, supplementary1, supplementary2, pos, a);
                    pos--;
                }
            }
        }
        else{
            for (int a = last; a>first; a--){
                if (array[a].compareTo(pivot) < 0){
                    swap(array, supplementary1, supplementary2, pos, a);
                    pos--;
                }
            }
        }
        swap(array, supplementary1, supplementary2, first, pos);
        quicksort(array, supplementary1, supplementary2, first, pos-1, ascending);
        quicksort(array, supplementary1, supplementary2, pos+1, last, ascending);
    }

    public static void quicksort(char[] array, int[] supplementary1, boolean[] supplementary2, boolean[] supplementary3){
        quicksort(array, supplementary1, supplementary2, supplementary3, true);
    }
    public static void quicksort(char[] array, int[] supplementary1, boolean[] supplementary2, boolean[] supplementary3, boolean ascending){
        quicksort(array, supplementary1, supplementary2, supplementary3, 0, array.length-1, ascending);
    }
    private static void quicksort(char[] array, int[] supplementary1, boolean[] supplementary2, boolean[] supplementary3, int first, int last, boolean ascending){
        if (first >= last){
            return;
        }
        char pivot = array[first];
        int pos = last;
        if (ascending){
            for (int a = last; a>first; a--){
                if (array[a] > pivot){
                    swap(array, supplementary1, supplementary2, supplementary3, pos, a);
                    pos--;
                }
            }
        }
        else{
            for (int a = last; a>first; a--){
                if (array[a] < pivot){
                    swap(array, supplementary1, supplementary2, supplementary3, pos, a);
                    pos--;
                }
            }
        }
        swap(array, supplementary1, supplementary2, supplementary3, first, pos);
        quicksort(array, supplementary1, supplementary2, supplementary3, first, pos-1, ascending);
        quicksort(array, supplementary1, supplementary2, supplementary3, pos+1, last, ascending);
    }

    public static void quicksort(char[] array, char[] supplementary1, char[] supplementary2){
        quicksort(array, supplementary1, supplementary2, true);
    }
    public static void quicksort(char[] array, char[] supplementary1, char[] supplementary2, boolean ascending){
        quicksort(array, supplementary1, supplementary2, 0, array.length-1, ascending);
    }
    private static void quicksort(char[] array, char[] supplementary1, char[] supplementary2, int first, int last, boolean ascending){
        if (first >= last){
            return;
        }
        char pivot = array[first];
        int pos = last;
        if (ascending){
            for (int a = last; a>first; a--){
                if (array[a] > pivot){
                    swap(array, supplementary1, supplementary2, pos, a);
                    pos--;
                }
            }
        }
        else{
            for (int a = last; a>first; a--){
                if (array[a] < pivot){
                    swap(array, supplementary1, supplementary2, pos, a);
                    pos--;
                }
            }
        }
        swap(array, supplementary1, supplementary2, first, pos);
        quicksort(array, supplementary1, supplementary2, first, pos-1, ascending);
        quicksort(array, supplementary1, supplementary2, pos+1, last, ascending);
    }

    private static void swap(int[] array, int pos1, int pos2){
        int temp = array[pos1];
        array[pos1] = array[pos2];
        array[pos2] = temp;
    }
    private static void swap(char[] array, int pos1, int pos2){
        char temp = array[pos1];
        array[pos1] = array[pos2];
        array[pos2] = temp;
    }
    private static void swap(Fraction[] array, int pos1, int pos2){
        Fraction temp = array[pos1];
        array[pos1] = array[pos2];
        array[pos2] = temp;
    }
    private static void swap(boolean[] array, int pos1, int pos2){
        boolean temp = array[pos1];
        array[pos1] = array[pos2];
        array[pos2] = temp;
    }
    private static void swap(double[] array, int pos1, int pos2){
        double temp = array[pos1];
        array[pos1] = array[pos2];
        array[pos2] = temp;
    }
    private static void swap(String[] array, int pos1, int pos2){
        String temp = array[pos1];
        array[pos1] = array[pos2];
        array[pos2] = temp;
    }
    private static void swap(String[] array, int[] supplementary, int pos1, int pos2){
        swap(array, pos1, pos2);
        swap(supplementary, pos1, pos2);
    }
    private static void swap(String[] array, String[] supplementary, int pos1, int pos2){
        swap(array, pos1, pos2);
        swap(supplementary, pos1, pos2);
    }
    private static void swap(String[] array, String[] supplementary1, String[] supplementary2, int pos1, int pos2){
        swap(array, pos1, pos2);
        swap(supplementary1, pos1, pos2);
        swap(supplementary2, pos1, pos2);
    }
    private static void swap(char[] array, int[] supplementary1, boolean[] supplementary2, boolean[] supplementary3, int pos1, int pos2){
        swap(array, pos1, pos2);
        swap(supplementary1, pos1, pos2);
        swap(supplementary2, pos1, pos2);
        swap(supplementary3, pos1, pos2);
    }
    private static void swap(char[] array, char[] supplementary1, char[] supplementary2, int pos1, int pos2){
        swap(array, pos1, pos2);
        swap(supplementary1, pos1, pos2);
        swap(supplementary2, pos1, pos2);
    }
    
}
