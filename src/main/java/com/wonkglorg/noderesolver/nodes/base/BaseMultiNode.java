package com.wonkglorg.noderesolver.nodes.base;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseMultiNode<T, U, R> extends BaseNode implements Cloneable {
    protected BaseNode input1;
    protected BaseNode input2;
    protected R result;
    protected Class<T> inputType1;
    protected Class<U> inputType2;
    protected List<BaseNode> outputs = new ArrayList<>();

    public BaseMultiNode(Class<T> inputType1, Class<U> inputType2) {
        this.inputType1 = inputType1;
        this.inputType2 = inputType2;
    }

    /**
     * Set the first input node for this node
     *
     * @param input The input node
     */
    public void setInput1(BaseMultiNode<?, ?, ?> input) {
        if (input == null) {
            return;
        }
        if (input == this) {
            throw new IllegalArgumentException("Cannot set input to self");
        }

        if (input1 == input || input2 == input) {
            return;
        }

        input1 = input;
        input.addOutput(this, BaseNodeIndex.ONE);
    }

    /**
     * Set the first input node for this node
     *
     * @param input The input node
     */
    public void setInput1(BaseSingleNode<?, ?> input) {
        if (input == null) {
            return;
        }

        if (input1 == input || input2 == input) {
            return;
        }

        input1 = input;
        input.setOutput(this, BaseNodeIndex.ONE);
    }

    /**
     * Set the second input node for this node
     *
     * @param input The input node
     */
    public void setInput2(BaseMultiNode<?, ?, ?> input) {
        if (input == null) {
            return;
        }
        if (input == this) {
            throw new IllegalArgumentException("Cannot set input to self");
        }

        if (input1 == input || input2 == input) {
            return;
        }

        input2 = input;
        input.addOutput(this, BaseNodeIndex.TWO);
    }

    /**
     * Set the second input node for this node
     *
     * @param input The input node
     */
    public void setInput2(BaseSingleNode<?, ?> input) {
        if (input == null) {
            return;
        }

        if (input1 == input || input2 == input) {
            return;
        }

        input2 = input;
        input.setOutput(this, BaseNodeIndex.TWO);
    }

    /**
     * Remove the first input node for this node
     *
     * @param input The input node
     */
    public void removeInput1(BaseMultiNode<?, ?, ?> input) {

        if (input == null) {
            return;
        }

        if (this.input1 != input) {
            return;
        }

        this.input1 = null;
        input.removeOutput(this, BaseNodeIndex.ONE);
    }

    /**
     * Remove the first input node for this node
     *
     * @param input The input node
     */
    public void removeInput1(BaseSingleNode<?, ?> input) {

        if (input == null) {
            return;
        }

        if (this.input1 != input) {
            return;
        }

        this.input1 = null;
        input.removeOutput(this, BaseNodeIndex.ONE);
    }

    /**
     * Remove the second input node for this node
     *
     * @param input The input node
     */
    public void removeInput2(BaseMultiNode<?, ?, ?> input) {

        if (input == null) {
            return;
        }

        if (this.input2 != input) {
            return;
        }

        this.input2 = null;
        input.removeOutput(this, BaseNodeIndex.TWO);
    }

    /**
     * Remove the second input node for this node
     *
     * @param input The input node
     */
    public void removeInput2(BaseSingleNode<?, ?> input) {

        if (input == null) {
            return;
        }

        if (this.input2 != input) {
            return;
        }

        this.input2 = null;
        input.removeOutput(this, BaseNodeIndex.TWO);
    }

    /**
     * Resolve the output of this node
     */
    public void addOutput(BaseMultiNode<?, ?, ?> output, BaseNodeIndex index) {
        if (output == null) {
            return;
        }
        if (output == this) {
            throw new IllegalArgumentException("Cannot set output to self");
        }

        this.outputs.add(output);
        index.getAdder().accept(output, this);
    }

    /**
     * Add an output node to this node
     *
     * @param output The output node
     */
    public void addOutput(BaseSingleNode<?, ?> output) {
        if (output == null) {
            return;
        }

        this.outputs.add(output);
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
        if (output == this) {
            throw new IllegalArgumentException("Cannot set output to self");
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

        if (!this.outputs.contains(output)) {
            return;
        }

        this.outputs.add(output);
        output.removeInput(this);
    }

    //todo:jmd clone the list otherwise the list is shared between the original and the clone
    @SuppressWarnings("unchecked")
    @Override
    public BaseMultiNode<T, U, R> clone() {
        BaseMultiNode<T, U, R> clonedNode = (BaseMultiNode<T, U, R>) super.clone();
        clonedNode.input1 = input1;
        clonedNode.input2 = input2;
        clonedNode.outputs = outputs;
        return clonedNode;
    }

    public BaseNode getInput1() {
        return input1;
    }

    public BaseNode getInput2() {
        return input2;
    }

    public R getResult() {
        return result;
    }

    public List<BaseNode> getOutputs() {
        return outputs;
    }
}