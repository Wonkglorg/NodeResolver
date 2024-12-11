package com.wonkglorg.newnoderesolver;

import java.util.function.Consumer;
import java.util.function.IntFunction;

public class IntHolder extends BaseHolder<Integer> {

	protected IntHolder(Integer value) {
		super(value);
	}

	@Override
	public IntHolder peak(Consumer<Integer> consumer) {
		consumer.accept(value);
		return this;
	}

	public IntHolder add(int addValue) {
		return new IntHolder(value + addValue);
	}

	public IntHolder subtract(int subtractValue) {
		return new IntHolder(value - subtractValue);
	}

	public IntHolder multiply(int multiplyValue) {
		return new IntHolder(value * multiplyValue);
	}

	public IntHolder divide(int divideValue) {
		return new IntHolder(value / divideValue);
	}

	public <T> ObjectHolder<T> modify(IntFunction<T> modify) {
		return new ObjectHolder<>(modify.apply(value));
	}


}
