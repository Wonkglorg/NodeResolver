package com.wonkglorg.noderesolver.nodes;

import com.wonkglorg.noderesolver.nodes.base.BaseSingleNode;

import java.util.function.Consumer;

public class OutputNode<T> extends BaseSingleNode<T, Void> implements Cloneable {
	protected Consumer<T> outputFunction;

	/**
	 * Input node constructor
	 *
	 * @param inputFunction The input function
	 */
	public OutputNode(Class<T> inputType, Consumer<T> inputFunction) {
		super(inputType);
		this.outputFunction = inputFunction;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object resolve() {
		if (resolved) {
			return result;
		}

		resolved = true;

		T inputData = null;

      if (this.input != null) {
          inputData = super.resolveInput(input.resolve(), inputType);
      }

		outputFunction.accept(inputData);
		return null;
	}

	@Override
	public OutputNode<T> clone() {
		OutputNode<T> clonedNode = (OutputNode<T>) super.clone();
		clonedNode.outputFunction = outputFunction;
		return clonedNode;
	}
}
