package com.wonkglorg.noderesolver.nodes.base;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseSingleNode<T, R> extends BaseNode implements Cloneable {
    protected BaseNode input;
    protected List<BaseNode> outputs = new ArrayList<>();
    protected Class<T> inputType;
    protected R result;

    public BaseSingleNode(Class<T> inputType) {
        this.inputType = inputType;
    }


    /**
     * Set the input node for this node
     *
     * @param input The input node
     */
    public void setInput(BaseMultiNode<?, ?, ?> input) {
        if (input == null) {
            return;
        }

        if (this.input == input) {
            return;
        }

        this.input = input;
        input.addOutput(this);
    }

    /**
     * Set the input node for this node
     *
     * @param input The input node
     */
    public void setInput(BaseSingleNode<?, ?> input) {
        if (input == null) {
            return;
        }

        if (input == this) {
            throw new IllegalArgumentException("Cannot set input to self");
        }


        if (this.input == input) {
            return;
        }


        this.input = input;
        input.setOutput(this);
    }

    /**
     * Remove the input node for this node
     *
     * @param input The input node
     */
    public void removeInput(BaseMultiNode<?, ?, ?> input) {
        if (input == null) {
            return;
        }

        if (this.input != input) {
            return;
        }

        input.removeOutput(this);
    }

    /**
     * Remove the input node for this node
     *
     * @param input The input node
     */
    public void removeInput(BaseSingleNode<?, ?> input) {
        if (input == null) {
            return;
        }

        if (this.input == this) {
            throw new IllegalArgumentException("Cannot set input to self");
        }

        if (this.input != input) {
            return;
        }

        this.input = null;
        input.removeOutput(this);
    }

    /**
     * Add an output node to this node
     *
     * @param output The output node
     * @param index  The index of the output nodes input
     */
    public void setOutput(BaseMultiNode<?, ?, ?> output, BaseNodeIndex index) {
        if (output == null) {
            return;
        }

        outputs.add(output);
        index.getAdder().accept(output, this);
    }

    /**
     * Add an output node to this node
     *
     * @param output The output node
     */
    public void setOutput(BaseSingleNode<?, ?> output) {
        if (output == null) {
            return;
        }

        if (output == this) {
            throw new IllegalArgumentException("Cannot set output to self");
        }


        outputs.add(output);
        output.setInput(this);
    }

    /**
     * Remove an output node from this node
     *
     * @param output The output node
     * @param index  The index of the output nodes input
     */
    public void removeOutput(BaseMultiNode<?, ?, ?> output, BaseNodeIndex index) {
        if (output == null) {
            return;
        }

        if (!this.outputs.contains(output)) {
            return;
        }

        this.outputs.remove(output);
        index.getRemover().accept(output, this);
    }

    /**
     * Remove an output node from this node
     *
     * @param output The output node
     */
    public void removeOutput(BaseSingleNode<?, ?> output) {
        if (output == null) {
            return;
        }

        if (this.outputs.contains(output)) {
            return;
        }

        this.outputs.remove(output);
        output.removeInput(this);
    }

    //todo:jmd clone the list otherwise the list is shared between the original and the clone
    @SuppressWarnings("unchecked")
    @Override
    public BaseSingleNode<T, R> clone() {
        BaseSingleNode<T, R> singleNode = (BaseSingleNode<T, R>) super.clone();
        singleNode.input = input;
        singleNode.outputs = outputs;
        return singleNode;
    }

    public BaseNode getInput() {
        return input;
    }

    public List<BaseNode> getOutputs() {
        return outputs;
    }

    public R getResult() {
        return result;
    }
}
