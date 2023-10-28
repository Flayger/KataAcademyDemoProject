package org.kata.flayger;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println(calc(scanner.nextLine()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static boolean isArabic = false;
    static boolean isRoman = false;

    public static String calc(String input) throws Exception {
        int first;
        int second;
        String result = "";
        boolean isOneOperand = false;

        for (int i = 0; i < input.length(); i++) {
            if ('0' <= input.charAt(i) && input.charAt(i) <= '9') {
                if (isRoman)
                    throw new Exception("arabic and roman detected");
                isArabic = true;
            } else if (input.charAt(i) == 'I' || input.charAt(i) == 'V' || input.charAt(i) == 'X') {
                if (isArabic)
                    throw new Exception("arabic and roman detected");
                isRoman = true;
            } else if (input.charAt(i) == ',' || input.charAt(i) == '.') {
                throw new Exception("only integers supported");
            } else if (input.charAt(i) == '+' || input.charAt(i) == '-' || input.charAt(i) == '*' || input.charAt(i) == '/') {
                if (isOneOperand)
                    throw new Exception("only one operand supported");
                isOneOperand = true;
            } else if (input.charAt(i) != ' ') {
                throw new Exception("detected unsupported symbol");
            }
        }

        if(!isRoman && !isArabic)
            throw new Exception("input is empty string");
        if(!isOneOperand)
            throw new Exception("no operand detected");

        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);

            if (ch == '+' || ch == '-' || ch == '*' || ch == '/') {
                first = isArabic ? Integer.parseInt(input.substring(0, i).trim()) : romanToInt(input.substring(0, i).trim());
                second = isArabic ? Integer.parseInt(input.substring(i + 1).trim()) : romanToInt(input.substring(i + 1).trim());
                if (first > 10 || first == 0 || second > 10 || second == 0) {
                    throw new Exception("numbers out of bounds");
                }
                if (ch == '+') {
                    result = isArabic ? String.valueOf(first + second) : intToRoman(first + second);
                }
                if (ch == '-') {
                    if (first - second < 0 && isRoman) {
                        throw new Exception("negative romanian");
                    }
                    result = isArabic ? String.valueOf(first - second) : intToRoman(first - second);
                }
                if (ch == '*') {
                    result = isArabic ? String.valueOf(first * second) : intToRoman(first * second);
                }
                if (ch == '/') {
                    result = isArabic ? String.valueOf(first / second) : intToRoman(first / second);
                }
            }
        }
        return result;
    }

    public static String intToRoman(int num) {
        if (num == 0)
            return "0";
        int[] intValues = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romanNumerals = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        StringBuilder result = new StringBuilder();
        int i = 0;
        while (num > 0) {
            if (num >= intValues[i]) {
                result.append(romanNumerals[i]);
                num -= intValues[i];
            } else {
                i++;
            }
        }
        return result.toString();
    }

    public static int romanToInt(String s) throws Exception {
        int result = 0;

        Map<Character, Integer> m = new HashMap<>();
        m.put('I', 1);
        m.put('V', 5);
        m.put('X', 10);
        m.put(' ', -1000);

        for (int i = 0; i < s.length(); i++) {
            if (m.get(s.charAt(i)) == -1000)
                throw new Exception("roman number with spaces");
            if (i < s.length() - 1 && m.get(s.charAt(i)) < m.get(s.charAt(i + 1))) {
                result -= m.get(s.charAt(i));
            } else {
                result += m.get(s.charAt(i));
            }
        }

        return result;
    }
}