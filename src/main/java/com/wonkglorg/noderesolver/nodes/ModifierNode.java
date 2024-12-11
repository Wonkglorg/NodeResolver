package com.wonkglorg.noderesolver.nodes;

import com.wonkglorg.noderesolver.nodes.base.BaseNode;
import com.wonkglorg.noderesolver.nodes.base.BaseSingleNode;

import java.util.function.Function;

/**
 * Represents a {@link Function}Node taking in 1 input and providing 1 output
 *
 * @param <T>
 * @param <R>
 */
public class ModifierNode<T, R> extends BaseSingleNode<T, R> implements Cloneable {
	protected Function<T, R> modifyFunction;

	public ModifierNode(Class<T> inputType, Function<T, R> modify) {
		super(inputType);
		this.modifyFunction = modify;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object resolve() {
		T inputData = null;
		R value = null;

		if (this.input != null) {
			inputData = super.resolveInput(input.resolve(), inputType);
			if (cacheValues) {
				value = cache.getOrDefault(inputData, null);
				if (value != null) {
					return value;
				}
			}
		}

		value = modifyFunction.apply(inputData);
		cache.put(inputData, value);
		// resolves downstream nodes
		if (outputs != null) {
			for (BaseNode output : outputs) {
				output.resolve();
			}
		}

		return value;
	}

	@Override
	public void clearCache() {
		cache.clear();
	}

	/**
	 * @param cacheValues if true set to true caches inputs and their resolved outputs (should be
	 * used
	 * when an input predictably always leads to the same output)
	 */
	public void setCacheValues(boolean cacheValues) {
		this.cacheValues = cacheValues;
	}

	@Override
	public ModifierNode<T, R> clone() {
		ModifierNode<T, R> clonedNode = (ModifierNode<T, R>) super.clone();
		clonedNode.modifyFunction = modifyFunction;
		return clonedNode;
	}
}
