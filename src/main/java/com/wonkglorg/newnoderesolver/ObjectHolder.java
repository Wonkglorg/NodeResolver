package com.wonkglorg.newnoderesolver;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class ObjectHolder<T> extends BaseHolder<T> {


	protected ObjectHolder(T value) {
		super(value);
	}

	public static <T> ObjectHolder<T> of(T value) {
		return new ObjectHolder<>(value);
	}

	public static <T> ObjectHolder<T> of(Supplier<T> value) {
		return new ObjectHolder<>(value.get());
	}

	public <R> ObjectHolder<R> modify(Function<T, R> func) {
		return new ObjectHolder<>(func.apply(value));
	}

	@Override
	public ObjectHolder<T> peak(Consumer<T> consumer) {
		return null;
	}

	public IntHolder toInt(ToIntFunction<T> func) {
		return new IntHolder(func.applyAsInt(value));
	}

	public boolean isPresent() {
		return value != null;
	}

	public boolean is(Predicate<T> predicate) {
		return predicate.test(value);
	}

	public T getValue() {
		return value;
	}
}
