package top.zsmile.test.pat.classb;

import java.util.Scanner;

/**
 * 读入 n（>0）名学生的姓名、学号、成绩，分别输出成绩最高和成绩最低学生的姓名和学号。
 * <p>
 * 输入格式：
 * 每个测试输入包含 1 个测试用例，格式为
 * <p>
 * 第 1 行：正整数 n
 * 第 2 行：第 1 个学生的姓名 学号 成绩
 * 第 3 行：第 2 个学生的姓名 学号 成绩
 * ... ... ...
 * 第 n+1 行：第 n 个学生的姓名 学号 成绩
 * 其中姓名和学号均为不超过 10 个字符的字符串，成绩为 0 到 100 之间的一个整数，这里保证在一组测试用例中没有两个学生的成绩是相同的。
 * <p>
 * 输出格式：
 * 对每个测试用例输出 2 行，第 1 行是成绩最高学生的姓名和学号，第 2 行是成绩最低学生的姓名和学号，字符串间有 1 空格。
 * <p>
 * 输入样例：
 * 3
 * Joe Math990112 89
 * Mike CS991301 100
 * Mary EE990830 95
 * 输出样例：
 * Mike CS991301
 * Joe Math990112
 */
public class Pat1004 {
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextLine()) {
            int count = Integer.parseInt(scanner.nextLine());
            String maxLine = null, minLine = null;
            int maxNum = -1, minNum = -1;
            for (int i = 0; i < count; i++) {
                String line = scanner.nextLine();
                String[] strs = line.split(" ");
                Integer num = Integer.valueOf(strs[2]);
                if (minNum == -1 || num < minNum) {
                    minNum = num;
                    minLine = strs[0] + " " + strs[1];
                }
                if (maxNum == -1 || num > maxNum) {
                    maxNum = num;
                    maxLine = strs[0] + " " + strs[1];
                }
            }
            System.out.println(maxLine);
            System.out.println(minLine);
        }
    }
}
