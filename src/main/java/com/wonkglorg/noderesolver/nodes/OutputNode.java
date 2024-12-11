package com.wonkglorg.noderesolver.nodes;

import com.wonkglorg.noderesolver.nodes.base.BaseSingleNode;

import java.util.function.Consumer;

/**
 * Represents a {@link Consumer} Node taking in 1 value and returning nothing
 * @param <T>
 */
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
	public void clearCache() {

	}

	@Override
	public OutputNode<T> clone() {
		OutputNode<T> clonedNode = (OutputNode<T>) super.clone();
		clonedNode.outputFunction = outputFunction;
		return clonedNode;
	}
}
