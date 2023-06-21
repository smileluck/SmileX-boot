package top.zsmile.test.sensitive;

import java.util.HashMap;
import java.util.Map;

public final class TrieNode {
    /**
     * 是否是单词，0否，1是
     */
    private Integer isWord;
    private Character nodeChar;
    private Map<Character, TrieNode> values;

    public TrieNode(Character nodeChar) {
        this.nodeChar = nodeChar;
        this.isWord = 0;
    }

    public TrieNode(Character nodeChar, Integer isWord) {
        this.nodeChar = nodeChar;
        this.isWord = isWord;
    }

    public void put(Character nodeChar, TrieNode node) {
        if (values == null) {
            values = new HashMap<>();
        }
        values.put(nodeChar, node);
    }

    public TrieNode get(Character nodeChar) {
        return values == null ? null : values.get(nodeChar);
    }

    /**
     * 是否结束了
     *
     * @return
     */
    public Integer getIsEnd() {
        return values == null ? 0 : 1;
    }

    public Integer getIsWord() {
        return isWord;
    }
}
