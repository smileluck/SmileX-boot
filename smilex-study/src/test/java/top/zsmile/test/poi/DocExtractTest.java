package top.zsmile.test.poi;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.*;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Base64Utils;
import org.zwobble.mammoth.DocumentConverter;
import org.zwobble.mammoth.Result;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

/**
 * @Version: 1.0.0
 * @Author: Administrator
 * @Date: 2023/03/13/11:26
 * @ClassName: DocExtractTest
 * @Description: DocExtractTest
 */

@Slf4j
@SpringBootTest
public class DocExtractTest {

    /**
     * https://github.com/mwilliamson/java-mammoth
     *
     * @throws IOException
     */
    @Test
    public void test() throws IOException {
        File file = new File("D://test/doc/import.docx");
        if (file.exists()) {
            DocumentConverter converter = new DocumentConverter();
            Result<String> result = converter.convertToHtml(file);
            String html = result.getValue(); // The generated HTML
            Set<String> warnings = result.getWarnings(); // Any warnings during conversion
            log.info(html);
        }
    }


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
            // 获取段落所有的表格
//            List<XWPFTable> tables = para.getBody().getTables();
//            for (XWPFTable table : tables) {
//                String tableStr = "<table>";
//                List<XWPFTableRow> rows = table.getRows();
//                for (XWPFTableRow row : rows) {
//                    tableStr += "<tr>";
//                    List<XWPFTableCell> tableCells = row.getTableCells();
//                    for (XWPFTableCell tableCell : tableCells) {
//                        // 获取文本
//                        tableStr += "<td>";
//                        String text = tableCell.getText();
//                        tableStr += text;
//                        tableStr += "<td>";
//                    }
//                    tableStr += "</tr>";
//                }
//                tableStr += "</table>";
//                htmlStrBuild.append(tableStr);
//            }
            htmlStrBuild.append("</p>");
        }
        log.info(htmlStrBuild.toString());
    }
}
