import java.util.Scanner;
import java.util.HashMap;

class Main {
    public static String calc(String input) {
// Создаем словарь для хранения соответствия римских и арабских цифр
        HashMap<Character, Integer> romanNumerals = new HashMap<Character, Integer>();
        romanNumerals.put('I', 1);
        romanNumerals.put('V', 5);
        romanNumerals.put('X', 10);
        romanNumerals.put('L', 50);
        romanNumerals.put('C', 100);

// Разбиваем строку на числа и операцию
        String[] parts = input.split(" ");
        if (parts.length != 3) {
            return "Неправильный формат выражения";
        }

// Проверяем, что числа в диапазоне от 1 до 10 включительно
        int a, b;
        try {
            a = Integer.parseInt(parts[0]);
            b = Integer.parseInt(parts[2]);
            if ((a < 1 || a > 10) || (b < 1 || b > 10)) {
                return "Числа должны быть от 1 до 10 включительно";
            }
        } catch (NumberFormatException e) {
// Если число не является арабским, пробуем его распознать как римское
            String roman1 = parts[0], roman2 = parts[2];
            int arabic1 = 0, arabic2 = 0;
            for (int i = 0; i < roman1.length(); i++) {
                char c = roman1.charAt(i);
                if (!romanNumerals.containsKey(c)) {
                    return "Неправильный формат чисел";
                }
                arabic1 += romanNumerals.get(c);
                if (i > 0 && romanNumerals.get(c) > romanNumerals.get(roman1.charAt(i - 1))) {
                    arabic1 -= 2 * romanNumerals.get(roman1.charAt(i - 1));
                }
            }
            for (int i = 0; i < roman2.length(); i++) {
                char c = roman2.charAt(i);
                if (!romanNumerals.containsKey(c)) {
                    return "Неправильный формат чисел";
                }
                arabic2 += romanNumerals.get(c);
                if (i > 0 && romanNumerals.get(c) > romanNumerals.get(roman2.charAt(i - 1))) {
                    arabic2 -= 2 * romanNumerals.get(roman2.charAt(i - 1));
                }
            }
            a = arabic1;
            b = arabic2;
        }

// Выполняем операцию
        int result;
        switch (parts[1]) {
            case "+":
                result = a + b;
                break;
            case "-":
                result = a - b;
                break;
            case "*":
                result = a * b;
                break;
            case "/":
                if (b == 0) {
                    return "Ошибка: Деление на ноль";
                }
                result = a / b;
                break;
            default:
                return "Неправильная операция";
        }

// Если входные числа были римскими, то результат нужно перевести обратно в римские
        if (input.contains("I") || input.contains("V") || input.contains("X") || input.contains("L") || input.contains("C")) {
            if (result < 1) {
                return "Результат не может быть меньше единицы при работе с римскими числами";
            }

// Переводим результат в римские цифры
            StringBuilder sb = new StringBuilder();
            int[] values = {100, 90, 50, 40, 10, 9, 5, 4, 1};
            String[] symbols = {"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
            for (int i = 0; i < values.length; i++) {
                while (result >= values[i]) {
                    sb.append(symbols[i]);
                    result -= values[i];
                }
            }
            return sb.toString();
        } else {
            return Integer.toString(result);
        }
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Введите выражение: ");
            String input = scanner.nextLine();
            if (input.equals("exit")) {
                break;
            }
            String result = calc(input);
            System.out.println("Результат: " + result);
        }
    }
}