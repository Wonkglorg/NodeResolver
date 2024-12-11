package com.wonkglorg.newnoderesolver;

import java.util.function.Consumer;

public abstract class BaseHolder<T> {
	protected final T value;
	//todo:jmd on runtime generate the different holders for each case? since alot can be extracted to annotations

	protected BaseHolder(T value) {
		this.value = value;
	}

	public abstract BaseHolder<T> peak(Consumer<T> consumer);
}
