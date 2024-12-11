package com.wonkglorg.noderesolver.nodes;

import com.wonkglorg.noderesolver.nodes.base.BaseNode;
import com.wonkglorg.noderesolver.nodes.base.BaseSingleNode;

/**
 * Modifies the input data with data kept by this node itself
 *
 * @param <T>
 */
public abstract class LocalMergeNode<T> extends BaseSingleNode<T, T> {


	protected LocalMergeNode(Class<T> returnType) {
		super(returnType);
	}

	/**
	 * Merge the input data with the data in this node
	 *
	 * @param input The input data
	 * @return The merged data
	 */
	protected abstract T merge(T input);


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

		result = merge(inputData);

		// resolves downstream nodes
		if (outputs != null) {
			for (BaseNode output : outputs) {
				output.resolve();
			}
		}

		return result;
	}
}
