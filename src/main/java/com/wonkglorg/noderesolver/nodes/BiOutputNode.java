package com.wonkglorg.noderesolver.nodes;

import com.wonkglorg.noderesolver.nodes.base.BaseMultiNode;

import java.util.function.BiConsumer;

public class BiOutputNode<T, U> extends BaseMultiNode<T, U, Void> implements Cloneable {
    protected BiConsumer<T, U> outputFunction;

    public BiOutputNode(Class<T> inputType1, Class<U> inputType2, BiConsumer<T, U> output) {
        super(inputType1, inputType2);
        this.outputFunction = output;
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

        outputFunction.accept(input1Data, input2Data);
        return null;
    }

    @Override
    public BiOutputNode<T, U> clone() {
        BiOutputNode<T, U> clonedNode = (BiOutputNode<T, U>) super.clone();
        clonedNode.outputFunction = outputFunction;
        return clonedNode;
    }
}
