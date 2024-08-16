package com.wonkglorg.noderesolver.nodes.base;

import java.util.function.BiConsumer;

public enum BaseNodeIndex {
    ONE(BaseNodeIndex::setInput1, BaseNodeIndex::removeInput1), TWO(BaseNodeIndex::setInput2, BaseNodeIndex::removeInput2);

    private final BiConsumer<BaseMultiNode<?, ?, ?>, BaseNode> adder;
    private final BiConsumer<BaseMultiNode<?, ?, ?>, BaseNode> remover;

    BaseNodeIndex(BiConsumer<BaseMultiNode<?, ?, ?>, BaseNode> adder, BiConsumer<BaseMultiNode<?, ?, ?>, BaseNode> remover) {
        this.adder = adder;
        this.remover = remover;
    }

    public BiConsumer<BaseMultiNode<?, ?, ?>, BaseNode> getAdder() {
        return adder;
    }

    public BiConsumer<BaseMultiNode<?, ?, ?>, BaseNode> getRemover() {
        return remover;
    }

    private static void setInput1(BaseMultiNode<?, ?, ?> baseMultiNode, BaseNode baseNode) {
        if (baseNode instanceof BaseMultiNode<?, ?, ?> baseNodeMul) {
            baseMultiNode.setInput1(baseNodeMul);
        } else if (baseNode instanceof BaseSingleNode<?, ?> baseNodeSingle) {
            baseMultiNode.setInput1(baseNodeSingle);
        }
    }

    private static void removeInput1(BaseMultiNode<?, ?, ?> baseMultiNode, BaseNode baseNode) {
        if (baseNode instanceof BaseMultiNode<?, ?, ?> baseNodeMul) {
            baseMultiNode.removeInput1(baseNodeMul);
        } else if (baseNode instanceof BaseSingleNode<?, ?> baseNodeSingle) {
            baseMultiNode.removeInput1(baseNodeSingle);
        }
    }

    private static void setInput2(BaseMultiNode<?, ?, ?> baseMultiNode, BaseNode baseNode) {
        if (baseNode instanceof BaseMultiNode<?, ?, ?> baseNodeMul) {
            baseMultiNode.setInput2(baseNodeMul);
        } else if (baseNode instanceof BaseSingleNode<?, ?> baseNodeSingle) {
            baseMultiNode.setInput2(baseNodeSingle);
        }
    }

    private static void removeInput2(BaseMultiNode<?, ?, ?> baseMultiNode, BaseNode baseNode) {
        if (baseNode instanceof BaseMultiNode<?, ?, ?> baseNodeMul) {
            baseMultiNode.removeInput2(baseNodeMul);
        } else if (baseNode instanceof BaseSingleNode<?, ?> baseNodeSingle) {
            baseMultiNode.removeInput2(baseNodeSingle);
        }
    }
}