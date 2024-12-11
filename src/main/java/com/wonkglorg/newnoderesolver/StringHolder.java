package com.wonkglorg.newnoderesolver;

import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHolder extends BaseHolder<String> {
	protected StringHolder(String value) {
		super(value);
	}

	public StringHolder toUpperCase() {
		return new StringHolder(value.toUpperCase());
	}

	public StringHolder toLowerCase() {
		return new StringHolder(value.toLowerCase());
	}

	public StringHolder trim() {
		return new StringHolder(value.trim());
	}

	public boolean matchesExact(String matches) {
		return value.matches(matches);
	}

	public Matcher matcher(Pattern pattern) {
		return pattern.matcher(value);
	}

	public Matcher matcher(String regex) {
		return Pattern.compile(regex).matcher(value);
	}


	@Override
	public BaseHolder<String> peak(Consumer<String> consumer) {
		return null;
	}
}
