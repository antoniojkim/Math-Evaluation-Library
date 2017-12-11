package Math_Evaluation_Library.ExpressionObjects;

import Math_Evaluation_Library.Expressions.Expression;

/**
 * Created by Antonio on 2017-12-07.
 */
public abstract class RandomVariable extends MultiParamFunction {

    public RandomVariable(String function, int numParameters) {
        super(function, numParameters);
    }
    public RandomVariable(String function, int numParameters, String description) {
        super(function, numParameters, description);
    }

    public abstract boolean isContinuous();

    public abstract Expression getExpectedValue(Expression[] parameters);
    public abstract Expression getVariance(Expression[] parameters);
    public abstract Expression getPDF(Expression[] parameters, Expression xe);
    public abstract Expression getCDF(Expression[] parameters, Expression xe);
}
