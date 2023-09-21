package top.zsmile.test.pat.classb;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 卡拉兹(Callatz)猜想已经在1001中给出了描述。在这个题目里，情况稍微有些复杂。
 * <p>
 * 当我们验证卡拉兹猜想的时候，为了避免重复计算，可以记录下递推过程中遇到的每一个数。例如对 n=3 进行验证的时候，我们需要计算 3、5、8、4、2、1，则当我们对 n=5、8、4、2 进行验证的时候，就可以直接判定卡拉兹猜想的真伪，而不需要重复计算，因为这 4 个数已经在验证3的时候遇到过了，我们称 5、8、4、2 是被 3“覆盖”的数。我们称一个数列中的某个数 n 为“关键数”，如果 n 不能被数列中的其他数字所覆盖。
 * <p>
 * 现在给定一系列待验证的数字，我们只需要验证其中的几个关键数，就可以不必再重复验证余下的数字。你的任务就是找出这些关键数字，并按从大到小的顺序输出它们。
 * <p>
 * 输入格式：
 * 每个测试输入包含 1 个测试用例，第 1 行给出一个正整数 K (<100)，第 2 行给出 K 个互不相同的待验证的正整数 n (1<n≤100)的值，数字间用空格隔开。
 * <p>
 * 输出格式：
 * 每个测试用例的输出占一行，按从大到小的顺序输出关键数字。数字间用 1 个空格隔开，但一行中最后一个数字后没有空格。
 * <p>
 * 输入样例：
 * 6
 * 3 5 6 7 8 11
 * 输出样例：
 * 7 6
 */
public class Pat1005 {

    private static Set<Integer> originSet = new HashSet<>();
    private static Set<Integer> coverSet = new HashSet<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextLine()) {
            int count = Integer.parseInt(scanner.nextLine());
            String s = scanner.nextLine();
            String[] split = s.split(" ");
            for (int i = 0; i < split.length; i++) {
                Integer num = Integer.parseInt(split[i]);
                originSet.add(num);
                coverSet.add(num);
                while (num != 1) {
                    if (num % 2 == 0) {
                        num = num / 2;
                    } else {
                        num = (3 * num + 1) / 2;
                        coverSet.add(num);
                    }
                    coverSet.add(num);
                }
            }
            Iterator<Integer> iterator = originSet.iterator();
            while (iterator.hasNext()) {
                Integer num = iterator.next();
                while (num != 1) {
                    if (num % 2 == 0) {
                        num = num / 2;
                    } else {
                        num = (3 * num + 1) / 2;
                        coverSet.remove(num);
                    }
                    coverSet.remove(num);
                }
            }
            String collect = coverSet.stream().sorted(Comparator.reverseOrder()).map(Object::toString).collect(Collectors.joining(" "));
            System.out.println(collect);
        }
    }
}
