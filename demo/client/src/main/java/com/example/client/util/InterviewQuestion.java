package com.example.client.util;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Predicate;

/**
 * @author long
 * @date 2020/8/8
 */
@Slf4j
public class InterviewQuestion {
    public static int solution1(int n) {
        StringBuilder builder = new StringBuilder(String.valueOf(n));
        int len = builder.length();
        int i = 0;
        if (n >= 0) {
            for (; i < len; i++) {
                int v = builder.charAt(i) - '0';
                if (v < 5) {
                    builder.insert(i, '5');
                    break;
                }
            }
        } else {
            for (i = 1; i < len; i++) {
                int v = builder.charAt(i) - '0';
                if (v > 5) {
                    builder.insert(i, '5');
                    break;
                }
            }
        }
        if (i == len) {
            builder.append('5');
        }
        return Integer.parseInt(builder.toString());
    }

    public static int solution2(int n, int m) {
        if (n >= 0) {
            return getSolution(n, 0, v -> v < m, m);
        } else {
            return getSolution(n, 1, v -> v > m, m);
        }
    }

    private static int getSolution(int n, int start, Predicate<Integer> condition, int insertValue) {
        assert insertValue > 0 && insertValue < 10;
        StringBuilder sb = new StringBuilder(String.valueOf(n));
        int i = start;
        for (; i < sb.length(); i++) {
            int num = sb.charAt(i) - '0';
            // 找到第一个满足条件的地方，插入指定的字符
            if (condition.test(num)) {
                sb.insert(i, insertValue);
                break;
            }
        }
        // 没有满足条件的，则在最后面添加
        if (i == sb.length()) {
            sb.append(insertValue);
        }
        return Integer.parseInt(sb.toString());
    }
}
