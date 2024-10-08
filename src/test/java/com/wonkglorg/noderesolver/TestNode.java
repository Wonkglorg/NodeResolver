package com.wonkglorg.noderesolver;

import com.wonkglorg.noderesolver.nodes.BiModifierNode;
import com.wonkglorg.noderesolver.nodes.InputNode;
import com.wonkglorg.noderesolver.nodes.ModifierNode;
import com.wonkglorg.noderesolver.nodes.OutputNode;
import com.wonkglorg.noderesolver.testimplementation.VideoData;
import com.wonkglorg.noderesolver.testimplementation.VideoDataMerge;
import org.junit.Assert;
import org.junit.Test;

public class TestNode {

	@Test
	public void doesNodeInputReturnIntermediateValue() {
		var inputNode = new InputNode<>(() -> 5);
		var result = inputNode.resolve();
		Assert.assertEquals(5, result);
	}

	@Test
	public void doesNodeModifierReturnIntermediateValue() {
		var valueNode = new InputNode<>(() -> 5);
		var modifierNode = new ModifierNode<>(Integer.class, a -> a + 5);
		modifierNode.setInput(valueNode);

		var result = modifierNode.resolve();
		Assert.assertEquals(10, result);
	}

	@Test
	public void doesNodeBiModifierReturnIntermediateValue() {
		var valueNode1 = new InputNode<>(() -> 5);
		var valueNode2 = new InputNode<>(() -> 5);
		var modifierNode = new BiModifierNode<>(Integer.class, Integer.class, Integer::sum);
		modifierNode.setInput1(valueNode1);
		modifierNode.setInput2(valueNode2);
		var result = modifierNode.resolve();
		Assert.assertEquals(10, result);
	}

	@Test
	public void doesNodeLocalMergeReturnIntermediateValue() {
		var inputNode = new InputNode<>(() -> new VideoData("videoName", "videoType", "videoUrl"));
		var modifierNode = new VideoDataMerge<>(VideoData.class);
		modifierNode.setInput(inputNode);

		VideoData result = (VideoData) modifierNode.resolve();
		Assert.assertEquals("videoNameModified", result.getVideoName());
		Assert.assertEquals("videoTypeModified", result.getVideoType());
		Assert.assertEquals("videoUrl", result.getVideoUrl());
	}

	@Test(expected = IllegalStateException.class)
	public void doesUnexpectedInputTypeThrowError() {
		var inputNode = new InputNode<>(() -> "Hello");
		var modifyNode = new ModifierNode<>(Integer.class, value -> value + value * 2);
		modifyNode.setInput(inputNode);
		var ignored = modifyNode.resolve();
	}

	@Test
	public void doesAutoboxingBehaveCorrectlyWork() {
		var inputNode = new InputNode<>(() -> 1);
		var modifyNode = new ModifierNode<>(Integer.class, value -> value + value * 2);
		modifyNode.setInput(inputNode);
		Assert.assertEquals(3, modifyNode.resolve());
	}

	@Test(expected = IllegalArgumentException.class)
	public void canNodeDetectSelfAssigningInput() {
		var modifyNode = new ModifierNode<>(Integer.class, value -> value + value * 2);
		modifyNode.setInput(modifyNode);
	}

	@Test(expected = IllegalArgumentException.class)
	public void canNodeDetectSelfAssigningOutput() {
		var modifyNode = new ModifierNode<>(Integer.class, value -> value + value * 2);
		modifyNode.setOutput(modifyNode);
	}

	@Test
	public void canModifierNodeHandleNullPassing() {
		var inputNode = new InputNode<>(() -> null);
		var modifyNode =
				new ModifierNode<>(Integer.class, value -> value != null ? value + value * 2 : null);
		modifyNode.setInput(inputNode);
		modifyNode.resolve();
	}

	@Test
	public void canOutputNodeHandleNullPassing() {
		var inputNode = new InputNode<>(() -> null);
		var modifyNode = new OutputNode<>(Integer.class, System.out::println);
		modifyNode.setInput(inputNode);
		modifyNode.resolve();
	}

	@Test
	public void canBiModifierNodeHandleNullPassing() {
		var inputNode = new InputNode<>(() -> null);
		var modifyNode =
				new BiModifierNode<>(String.class, String.class, (value1, value2) -> value1 + value2);
		modifyNode.setInput1(inputNode);
		modifyNode.setInput2(inputNode);
		modifyNode.resolve();
	}

	@Test
	public void canMergeNodeHandleNullPassing() {
		var inputNode = new InputNode<>(() -> null);
		var mergeNode = new VideoDataMerge<>(VideoData.class);
		mergeNode.setInput(inputNode);
		mergeNode.resolve();
	}
}
