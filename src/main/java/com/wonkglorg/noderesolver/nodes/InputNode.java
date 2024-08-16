package com.wonkglorg.noderesolver.nodes;

import com.wonkglorg.noderesolver.nodes.base.BaseNode;
import com.wonkglorg.noderesolver.nodes.base.BaseSingleNode;

import java.util.function.Supplier;

public class InputNode<T> extends BaseSingleNode<Void, T> implements Cloneable {

    protected Supplier<T> inputFunction;

    /**
     * Input node constructor
     *
     * @param inputFunction The input function
     */
    public InputNode(Supplier<T> inputFunction) {
        super(Void.class);
        this.inputFunction = inputFunction;
    }

    @Override
    public Object resolve() {
        if (resolved) {
            return result;
        }
        resolved = true;
        result = inputFunction.get();

        // resolves downstream nodes
        if (outputs != null) {
            for (BaseNode output : outputs) {
                output.resolve();
            }
        }

        return result;

    }

    @Override
    public InputNode<T> clone() {
        InputNode<T> clonedNode = (InputNode<T>) super.clone();
        clonedNode.inputFunction = inputFunction;
        return clonedNode;
    }
}
