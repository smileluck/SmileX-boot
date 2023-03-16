package top.zsmile.test.poi;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.*;
import org.junit.Test;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTShd;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Base64Utils;
import org.zwobble.mammoth.DocumentConverter;
import org.zwobble.mammoth.Result;
import top.zsmile.common.utils.CmdUtils;

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
        List<IBodyElement> bodyElements = doc.getBodyElements();

        StringBuilder htmlStrBuild = new StringBuilder();
        for (IBodyElement bodyElement : bodyElements) {
            if (bodyElement.getElementType().equals(BodyElementType.PARAGRAPH)) {
                XWPFParagraph paragraph = (XWPFParagraph) bodyElement;
                htmlStrBuild.append(paragraphHandle(paragraph));
            } else if (bodyElement.getElementType().equals(BodyElementType.TABLE)) {
                XWPFTable table = (XWPFTable) bodyElement;
                String tableStr = "<table cellpadding=\"0\" border=\"1\" cellspacing=\"0\">";
                List<XWPFTableRow> rows = table.getRows();
                for (XWPFTableRow row : rows) {
                    String rowStyle = "";
                    // 行高
                    if (row.getHeight() > 0) {
                        rowStyle += "height:" + (row.getHeight() / 10) + "px;";
                    }
                    tableStr += "<tr style=\"" + rowStyle + "\">";
                    List<XWPFTableCell> tableCells = row.getTableCells();
                    for (XWPFTableCell tableCell : tableCells) {
                        String cellStyle = "";
                        if (tableCell.getWidth() > 0) {
                            cellStyle += "width:" + tableCell.getWidth() + "px;";
                        }
                        // 垂直剧中
                        XWPFTableCell.XWPFVertAlign verticalAlignment = tableCell.getVerticalAlignment();
                        if (verticalAlignment != null) {
                            if (verticalAlignment.equals(XWPFTableCell.XWPFVertAlign.TOP)) {
                                cellStyle += "vertical-align:top;";
                            } else if (verticalAlignment.equals(XWPFTableCell.XWPFVertAlign.CENTER)) {
                                cellStyle += "vertical-align:center;";
                            } else if (verticalAlignment.equals(XWPFTableCell.XWPFVertAlign.BOTTOM)) {
                                cellStyle += "vertical-align:bottom;";
                            }
                        }

                        // pct-50
                        CTShd cellShd = getCellShd(tableCell);
                        if (cellShd != null) {
                            // 设置单元格背景色
                            String cellFillHex = getCellFillHex(tableCell);
                            if (cellFillHex != null) {
                                cellStyle += "background-color:#" + cellFillHex + ';';
                            }

                            // 设置单元格底纹
                            String cellColorHex = getCellColorHex(cellShd);
                            if (StringUtils.isBlank(cellColorHex)) {
                                cellColorHex = "000";
                            }
                            String shdValue = cellShd.getVal().toString();
                            if (shdValue.equals("solid")) {
                                cellStyle += String.format("background-color:#%s;", cellColorHex);
                            } else if (shdValue.startsWith("pct")) {
                                cellStyle += String.format("background:radial-gradient(circle, #%s 0.5px, transparent 0.5px);", cellColorHex);
                                Double num = Math.ceil(Double.valueOf(cellShd.getVal().toString().substring(3)) / 10);
                                cellStyle += String.format("background-size: %spx %spx;", num, num);
                            } else if (shdValue.equals("horzStripe")) {
                                cellStyle += String.format("background-image: -webkit-linear-gradient(#%s 50%%, transparent 50%%, transparent);", cellColorHex);
                                cellStyle += String.format("background-image: linear-gradient(#%s 50%%, transparent 50%%, transparent);", cellColorHex);
                                cellStyle += String.format("background-size: 3px 3px;");
                            } else if (shdValue.equals("vertStripe")) {
                                cellStyle += String.format("background-image: -webkit-linear-gradient(to right,#%s 50%%, transparent 50%%, transparent);", cellColorHex);
                                cellStyle += String.format("background-image: linear-gradient(to right,#%s 50%%, transparent 50%%, transparent);", cellColorHex);
                                cellStyle += String.format("background-size: 3px 3px;");
                            } else if (shdValue.equals("diagStripe")) {
                                cellStyle += String.format("background-image: -webkit-linear-gradient(-45deg,#%s 25%%, transparent 25%%,transparent 50%%,#%s 50%%,#%s 75%%,transparent 75%%, transparent);", cellColorHex, cellColorHex,cellColorHex);
                                cellStyle += String.format("background-image: linear-gradient(-45deg,#%s 25%%, transparent 25%%,transparent 50%%,#%s 50%%,#%s 75%%,transparent 75%%, transparent);", cellColorHex, cellColorHex,cellColorHex);
                                cellStyle += String.format("background-size: 5px 5px;");
                            } else if (shdValue.equals("reverseDiagStripe")) {
                                cellStyle += String.format("background-image: -webkit-linear-gradient(45deg,#%s 25%%, transparent 25%%,transparent 50%%,#%s 50%%,#%s 75%%,transparent 75%%, transparent);", cellColorHex, cellColorHex,cellColorHex);
                                cellStyle += String.format("background-image: linear-gradient(45deg,#%s 25%%, transparent 25%%,transparent 50%%,#%s 50%%,#%s 75%%,transparent 75%%, transparent);", cellColorHex, cellColorHex,cellColorHex);
                                cellStyle += String.format("background-size: 5px 5px;");
                            }
                        }
                        // 获取文本
                        tableStr += "<td style=\"" + cellStyle + "\">";

                        List<XWPFParagraph> paragraphs = tableCell.getParagraphs();
                        for (XWPFParagraph paragraph : paragraphs) {
                            tableStr += paragraphHandle(paragraph);
                        }
                        tableStr += "</td>";
                    }
                    tableStr += "</tr>";
                }
                tableStr += "</table>";
                htmlStrBuild.append(tableStr);
            }
        }

        log.info(htmlStrBuild.toString());
    }


    private CTShd getCellShd(XWPFTableCell tableCell) {
        if (tableCell.getCTTc() != null) {
            if (tableCell.getCTTc().getTcPr() != null) {
                return tableCell.getCTTc().getTcPr().getShd();
            }
        }
        return null;
    }

    private String getCellFillHex(XWPFTableCell tableCell) {
        if (tableCell.getCTTc() != null) {
            if (tableCell.getCTTc().getTcPr() != null && getCellShd(tableCell) != null) {
                return getCellFillHex(getCellShd(tableCell));
            }
        }
        return null;
    }


    private String getCellFillHex(CTShd chd) {
        if (chd != null) {
            Object fill = chd.getFill();
            if ("auto".equals(fill.toString())) {
                return null;
            } else if (fill instanceof byte[]) {
                return CmdUtils.bytesToHexString((byte[]) fill);
            }
        }
        return null;
    }


    private String getCellColorHex(XWPFTableCell tableCell) {
        if (tableCell.getCTTc() != null) {
            if (tableCell.getCTTc().getTcPr() != null && getCellShd(tableCell) != null) {
                return getCellColorHex(getCellShd(tableCell));
            }
        }
        return null;
    }

    private String getCellColorHex(CTShd chd) {
        if (chd != null) {
            Object color = chd.getColor();
            if ("auto".equals(color.toString())) {
                return null;
            } else if (color instanceof byte[]) {
                return CmdUtils.bytesToHexString((byte[]) color);
            }
        }
        return null;
    }


    private String paragraphHandle(XWPFParagraph paragraph) {
        StringBuilder htmlStrBuild = new StringBuilder();
        // 段落属性
        String paragraphStyle = "";
        //对齐方式 alignment 枚举值
        if (paragraph.getAlignment().equals(ParagraphAlignment.CENTER)) {
            paragraphStyle += "text-align:center;";
        } else if (paragraph.getAlignment().equals(ParagraphAlignment.LEFT)) {
            paragraphStyle += "text-align:left;";
        } else if (paragraph.getAlignment().equals(ParagraphAlignment.RIGHT)) {
            paragraphStyle += "text-align:right;";
        }

        //获取段落所有的文本对象
        List<XWPFRun> runs = paragraph.getRuns();
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
                if (run.getFontSizeAsDouble() != null && run.getFontSizeAsDouble() > 0) {
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
        return htmlStrBuild.toString();
    }
}
