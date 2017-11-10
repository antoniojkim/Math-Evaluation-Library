package Math_Evaluation_Library.Objects;

/**
 * Created by Antonio on 2017-11-10.
 */
public class VarNum {

    public String var;
    public String value;

    public VarNum(String var, double value){
        this.var = var;
        this.value = _Number_.format(value);
    }

    public void setValue(double value){
        this.value = _Number_.format(value);
    }

}
