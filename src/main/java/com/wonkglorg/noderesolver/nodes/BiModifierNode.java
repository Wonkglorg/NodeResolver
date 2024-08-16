package com.wonkglorg.noderesolver.nodes;

import com.wonkglorg.noderesolver.nodes.base.BaseMultiNode;
import com.wonkglorg.noderesolver.nodes.base.BaseNode;

import java.util.function.BiFunction;

public class BiModifierNode<T, U, R> extends BaseMultiNode<T, U, R> implements Cloneable {
    protected BiFunction<T, U, R> modifyFunction;

    public BiModifierNode(Class<T> inputType1, Class<U> inputType2, BiFunction<T, U, R> modify) {
        super(inputType1, inputType2);
        this.modifyFunction = modify;
    }


    @Override
    public Object resolve() {
        if (resolved) {
            return result;
        }

        resolved = true;

        T input1Data = null;
        U input2Data = null;

        if (this.input1 != null) input1Data = super.resolveInput(input1.resolve(), inputType1);
        if (this.input2 != null) input2Data = super.resolveInput(input2.resolve(), inputType2);

        result = modifyFunction.apply(input1Data, input2Data);

        // resolves downstream nodes
        if (outputs != null) {
            for (BaseNode output : outputs) {
                output.resolve();
            }
        }

        return result;
    }

    @Override
    public BiModifierNode<T, U, R> clone() {
        BiModifierNode<T, U, R> clonedNode = (BiModifierNode<T, U, R>) super.clone();
        clonedNode.modifyFunction = modifyFunction;
        return clonedNode;
    }


}
