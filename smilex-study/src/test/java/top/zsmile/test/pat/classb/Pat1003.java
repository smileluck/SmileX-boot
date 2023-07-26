package top.zsmile.test.pat.classb;

import java.util.Scanner;

/**
 * “答案正确”是自动判题系统给出的最令人欢喜的回复。本题属于 PAT 的“答案正确”大派送 —— 只要读入的字符串满足下列条件，系统就输出“答案正确”，否则输出“答案错误”。
 * <p>
 * 得到“答案正确”的条件是：
 * <p>
 * 字符串中必须仅有 P、 A、 T这三种字符，不可以包含其它字符；
 * 任意形如 xPATx 的字符串都可以获得“答案正确”，其中 x 或者是空字符串，或者是仅由字母 A 组成的字符串；
 * 如果 aPbTc 是正确的，那么 aPbATca 也是正确的，其中 a、 b、 c 均或者是空字符串，或者是仅由字母 A 组成的字符串。
 * 现在就请你为 PAT 写一个自动裁判程序，判定哪些字符串是可以获得“答案正确”的。
 * <p>
 * 输入格式：
 * 每个测试输入包含 1 个测试用例。第 1 行给出一个正整数 n (≤10)，是需要检测的字符串个数。接下来每个字符串占一行，字符串长度不超过 100，且不包含空格。
 * <p>
 * 输出格式：
 * 每个字符串的检测结果占一行，如果该字符串可以获得“答案正确”，则输出 YES，否则输出 NO。
 * <p>
 * 输入样例：
 * 10
 * PAT
 * PAAT
 * AAPATAA
 * AAPAATAAAA
 * xPATx
 * PT
 * Whatever
 * APAAATAA
 * APT
 * APATTAA
 * <p>
 * 输出样例：
 * YES
 * YES
 * YES
 * YES
 * NO
 * NO
 * NO
 * NO
 * NO
 * NO
 */
public class Pat1003 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextInt()) {
            // 接受一个数字
            int count = scanner.nextInt();
            for (int j = 0; j < count; j++) {
                String s = scanner.next();
                if (check(s))
                    System.out.println("YES");
                else System.out.println("NO");
            }
        }
        scanner.close();
    }

    public static boolean check(String str) {
        int sp = -1, st = -1, sa = 0, ma = 0, ea = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == 'P') {
                if (st > -1 || sp > -1) {
                    return false;
                }
                sp = i;
            } else if (c == 'T') {
                if (st > -1) {
                    return false;
                }
                st = i;
            } else if (c != 'A') {
                return false;
            } else {
                if (sp != -1) {
                    if (st == -1) {
                        ma++;
                    } else if (st != -1) {
                        ea++;
                    }
                } else {
                    sa++;
                }
            }
        }
        // 有且只有一个P、T和至少一个A
        if (sp == -1 || st == -1 || ma == 0) return false;
        // 规则三 P前面A的数量*中间A的数量 = T后A的数量
        if (sa * ma != ea) {
            return false;
        }

        return true;
    }
}
