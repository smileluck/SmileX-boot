[toc]

---

# docx转html

这是自己写了的一个读取docx并处理成html的例子。导入 POI Maven

```xml
<!-- Apache Poi -->
<dependency>
    <groupId>commons-io</groupId>
    <artifactId>commons-io</artifactId>
    <version>2.11.0</version>
    <scope>compile</scope>
</dependency>
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi</artifactId>
    <version>5.2.3</version>
</dependency>
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-ooxml</artifactId>
    <version>5.2.3</version>
</dependency>
```

已经实现如下功能：

- 段落水平对齐方式
- 图片读取并转为base64
- 字体类型，大小，高亮，颜色，加粗，下划线，斜体

注意事项：

1. 低版本的高亮获取会提示null，升级高版本`4.1.2+`即可解决。

```java

@Test
public void poiTest() throws IOException {
    //        InputStream is = new FileInputStream("D:/test/doc/import.docx");
    //        String filename = "D:/test/doc/1111.docx";
    String filename = "D:/test/doc/import.docx";
    InputStream is = new FileInputStream(filename);
    XWPFDocument doc = new XWPFDocument(is);
    List<XWPFParagraph> paragraphs = doc.getParagraphs();
    StringBuilder htmlStrBuild = new StringBuilder();

    for (XWPFParagraph para : paragraphs) {
        // 段落属性
        String paragraphStyle = "";
        //对齐方式 alignment 枚举值
        if (para.getAlignment().equals(ParagraphAlignment.CENTER)) {
            paragraphStyle += "text-align:center;";
        } else if (para.getAlignment().equals(ParagraphAlignment.LEFT)) {
            paragraphStyle += "text-align:left;";
        } else if (para.getAlignment().equals(ParagraphAlignment.RIGHT)) {
            paragraphStyle += "text-align:right;";
        }

        //获取段落所有的文本对象
        List<XWPFRun> runs = para.getRuns();
        htmlStrBuild.append("<p style=\"" + paragraphStyle + "\">");
        for (XWPFRun run : runs) {
            // 图片处理
            List<XWPFPicture> embeddedPictures = run.getEmbeddedPictures();
            if (CollectionUtils.isNotEmpty(embeddedPictures)) {
                for (XWPFPicture embeddedPicture : embeddedPictures) {
                    htmlStrBuild.append("<p>");
                    XWPFPictureData pictureData = embeddedPicture.getPictureData();
                    String encode = "data:" + pictureData.getPackagePart().getContentType() + ";base64," + new String(Base64Utils.encode(pictureData.getData()));
                    htmlStrBuild.append("<img src=\"" + encode + "\"/>");
                    htmlStrBuild.append("</p>");
                }
            }

            if (StringUtils.isNotBlank(run.text())) {
                String style = "";

                //文本的颜色 color
                if (StringUtils.isNotBlank(run.getColor())) {
                    style += "color:#" + run.getColor() + ";";
                }
                //文本 大小 fontSize
                if (run.getFontSizeAsDouble() > 0) {
                    style += "font-size:" + run.getFontSizeAsDouble().intValue() + "px;";
                }
                //文本 类型 fontFamily
                if (StringUtils.isNotBlank(run.getFontFamily())) {
                    style += "font-family:" + run.getFontFamily() + ";";
                }
                //文本 加粗
                if (run.isBold()) {
                    style += "font-weight: bolder;";
                }
                //文本 斜体
                if (run.isItalic()) {
                    style += "font-style:italic;";
                }
                //文本 下划线
                if (run.getUnderline().equals(UnderlinePatterns.SINGLE)) {
                    style += "text-decoration: underline;";
                }
                //文本 高亮
                if (run.isHighlighted()) {
                    style += "background-color:" + run.getTextHighlightColor() + ";";
                }
                htmlStrBuild.append(String.format("<span style=\"%s\">%s</span>", style, run.text()));
            }
        }
        htmlStrBuild.append("</p>");
    }
    log.info(htmlStrBuild.toString());
}
```

