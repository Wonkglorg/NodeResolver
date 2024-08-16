package com.wonkglorg.noderesolver.nodes.base;

public abstract class BaseNode implements Cloneable {
	protected boolean resolved = false;

	public abstract Object resolve();

	@Override
	public BaseNode clone() {
		try {
			BaseNode clonedNode = (BaseNode) super.clone();
			// You might need to deep clone inputs/outputs if required
			clonedNode.resolved = false; // Reset the resolved flag
			return clonedNode;
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException("Clone not supported", e);
		}
	}

	protected String formatClassError(Class<?> expectedClass, Class<?> providedClass) {
		return formatClassError(expectedClass, providedClass, false);
	}

	protected String formatClassError(Class<?> expectedClass, Class<?> providedClass,
			boolean simpleName) {
		// Case: expectedClass is null
		if (expectedClass == null) {
			if (providedClass == null) {
				return "Both expected and provided types are null.";
			}
			String providedTypeName =
					simpleName ? providedClass.getSimpleName() : providedClass.getName();
			return "Expected type is null but found: \"%s\".".formatted(providedTypeName);
		}

		String expectedTypeName = simpleName ? expectedClass.getSimpleName() : expectedClass.getName();

		// Case: providedClass is null
		if (providedClass == null) {
			return "Input expected type: \"%s\" but found: null.".formatted(expectedTypeName);
		}

		String providedTypeName = simpleName ? providedClass.getSimpleName() : providedClass.getName();

		// Case: expectedClass and providedClass are the same or compatible
		if (expectedClass.isAssignableFrom(providedClass)) {
			return "Input expected type: \"%s\" but found: \"%s\".".formatted(expectedTypeName,
					providedTypeName);
		}

		// Case: Primitive types (e.g., int vs Integer)
		if (expectedClass.isPrimitive() || providedClass.isPrimitive()) {
			if (getWrapperType(expectedClass).equals(getWrapperType(providedClass))) {
				return "Input expected type: \"%s\" but found: \"%s\" (both primitive types).".formatted(
						expectedTypeName, providedTypeName);
			}
		}

		// Case: General mismatch
		return "Input expected type: \"%s\" but found: \"%s\".".formatted(expectedTypeName,
				providedTypeName);
	}

	private Class<?> getWrapperType(Class<?> clazz) {
		if (!clazz.isPrimitive()) {
			return clazz;
		}

		if (clazz == int.class) {
			return Integer.class;
		}
		if (clazz == long.class) {
			return Long.class;
		}
		if (clazz == boolean.class) {
			return Boolean.class;
		}
		if (clazz == byte.class) {
			return Byte.class;
		}
		if (clazz == char.class) {
			return Character.class;
		}
		if (clazz == float.class) {
			return Float.class;
		}
		if (clazz == double.class) {
			return Double.class;
		}
		if (clazz == short.class) {
			return Short.class;
		}
		if (clazz == void.class) {
			return Void.class;
		}

		throw new IllegalArgumentException("Unknown primitive type: " + clazz);
	}

	protected <T> T resolveInput(Object input, Class<T> type) {
		if (!type.isInstance(input) && input != null) {
			throw new IllegalStateException(formatClassError(type, input.getClass()));
		}

		return type.cast(input);
	}

	public boolean isResolved() {
		return resolved;
	}

	public void reset() {
		resolved = false;
	}

}
