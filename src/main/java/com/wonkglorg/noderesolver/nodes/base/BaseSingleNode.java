package com.wonkglorg.noderesolver.nodes.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class BaseSingleNode<T, R> extends BaseNode implements Cloneable {
	protected BaseNode input;
	protected List<BaseNode> outputs = new ArrayList<>();
	protected List<Consumer<R>> consumerOutputs = new ArrayList<>();
	protected Class<T> inputType;
	/**
	 * Keeps track of what input lead to what output and skips the calculation if the input has been
	 * computed already
	 */
	protected Map<T, R> cache = new HashMap<>();
	protected R result;

	protected BaseSingleNode(Class<T> inputType) {
		this.inputType = inputType;
	}


	/**
	 * Direct consumer Value
	 *
	 * @param provider
	 */
	public void setInput(Supplier<T> provider) {

	}

	/**
	 * Directly sets the value that should be provided to this node
	 *
	 * @param provider
	 */
	public void setInput(T provider) {

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
	public void setInput(BaseSingleNode<?, T> input) {
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
		input.addOutput(this);
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

	public void addOutput(Consumer<R> output) {
		if (output == null) {
			throw new IllegalArgumentException("Output Consumer is null");
		}
		consumerOutputs.add(output);
	}

	/**
	 * Add an output node to this node
	 *
	 * @param output The output node
	 * @param index The index of the output nodes input
	 */
	public void addOutput(BaseMultiNode<?, ?, ?> output, BaseNodeIndex index) {
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
	public void addOutput(BaseSingleNode<R, ?> output) {
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
	 * @param index The index of the output nodes input
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
