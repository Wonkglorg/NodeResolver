package com.wonkglorg.noderesolver.nodes;

import com.wonkglorg.noderesolver.nodes.base.BaseNode;
import com.wonkglorg.noderesolver.nodes.base.BaseSingleNode;

import java.util.function.Function;

public class ModifierNode<T, R> extends BaseSingleNode<T, R> implements Cloneable {
    protected Function<T, R> modifyFunction;

    public ModifierNode(Class<T> inputType, Function<T, R> modify) {
        super(inputType);
        this.modifyFunction = modify;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object resolve() {
        if (resolved) {
            return result;
        }

        resolved = true;

        T inputData = null;

        if (this.input != null) inputData = super.resolveInput(input.resolve(), inputType);
        result = modifyFunction.apply(inputData);
        // resolves downstream nodes
        if (outputs != null) {
            for (BaseNode output : outputs) {
                output.resolve();
            }
        }

        return result;
    }

    @Override
    public ModifierNode<T, R> clone() {
        ModifierNode<T, R> clonedNode = (ModifierNode<T, R>) super.clone();
        clonedNode.modifyFunction = modifyFunction;
        return clonedNode;
    }
}
