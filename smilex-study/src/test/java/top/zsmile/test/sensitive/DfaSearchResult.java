package top.zsmile.test.sensitive;

/**
 * dfa查询结果
 */
public class DfaSearchResult {
    /**
     * 查询到的起始下标
     */
    private Integer startIndex;

    /**
     * 匹配的字符串
     */
    private String text;

    public DfaSearchResult(Integer startIndex, String text) {
        this.startIndex = startIndex;
        this.text = text;
    }

    public Integer getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
