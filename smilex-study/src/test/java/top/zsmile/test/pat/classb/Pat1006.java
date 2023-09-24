package top.zsmile.test.pat.classb;

import java.util.Scanner;

/**
 * 让我们用字母 B 来表示“百”、字母 S 表示“十”，用 12...n 来表示不为零的个位数字 n（<10），换个格式来输出任一个不超过 3 位的正整数。例如 234 应该被输出为 BBSSS1234，因为它有 2 个“百”、3 个“十”、以及个位的 4。
 * <p>
 * 输入格式：
 * 每个测试输入包含 1 个测试用例，给出正整数 n（<1000）。
 * <p>
 * 输出格式：
 * 每个测试用例的输出占一行，用规定的格式输出 n。
 * <p>
 * 输入样例 1：
 * 234
 * 输出样例 1：
 * BBSSS1234
 * 输入样例 2：
 * 23
 * 输出样例 2：
 * SS123
 */
public class Pat1006 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextLine()) {
            String str = scanner.nextLine();
            int num = Integer.parseInt(str);
            int bai = num / 100;
            int shi = (num - bai * 100) / 10;
            int ge = num - bai * 100 - shi * 10;
            for (int i = 0; i < bai; i++) {
                System.out.print("B");
            }
            for (int i = 0; i < shi; i++) {
                System.out.print("S");
            }
            for (int i = 0; i < ge; i++) {
                System.out.print(i + 1);
            }

        }
    }
}
