package com.wonkglorg.newnoderesolver;

import java.util.function.Consumer;

public abstract class BaseHolder<T> {
	protected final T value;

	protected BaseHolder(T value) {
		this.value = value;
	}

	public abstract BaseHolder<T> peak(Consumer<T> consumer);
}
