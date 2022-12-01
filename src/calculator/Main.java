package calculator;
import calculator.ExceptionForCalculator;
import calculator.RomanNumeral;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ExceptionForCalculator {
        Scanner scanner = new Scanner(System.in);
        String string = scanner.nextLine();
        char [] array = string.toCharArray();
        boolean bool = CheckForInvalidCharacters(array);        // Первая проверка на недопустимые символы и на разность систем счисления
        // Если true(римская сс), если false(арабская сс)
        CheckStartAndEnd(array);                                // Вторая проверка на начало и конец строки
        CheckOperator(array);                                   // Третья проверка на количество операторов
        CheckSpace(array);                                      // Четвёртая проверка на пробелы
        char operator = FindOperator(string);                   // Нахожу оператор
        String [] arrayString = ArrayString(string);            // Создаю массив из 2 строк
        if(bool == true){
            ResultRoman(arrayString, operator);
        } else {ResultArabian(arrayString, operator);}
    }


    static int RomanToArabic(String input) {
        String romanNumeral = input.toUpperCase();
        int result = 0;

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;

        while ((romanNumeral.length() > 0) && (i < romanNumerals.size())) {
            RomanNumeral symbol = romanNumerals.get(i);
            if (romanNumeral.startsWith(symbol.name())) {
                result += symbol.getValue();
                romanNumeral = romanNumeral.substring(symbol.name().length());
            } else {
                i++;
            }
        }

        if (romanNumeral.length() > 0) {
            throw new IllegalArgumentException(input + " cannot be converted to a Roman Numeral");
        }

        return result;
    }

    public static String ArabicToRoman(int number) {
        if ((number <= 0) || (number > 4000)) {
            throw new IllegalArgumentException(number + " is not in range (0,4000]");
        }

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;
        StringBuilder sb = new StringBuilder();

        while ((number > 0) && (i < romanNumerals.size())) {
            RomanNumeral currentSymbol = romanNumerals.get(i);
            if (currentSymbol.getValue() <= number) {
                sb.append(currentSymbol.name());
                number -= currentSymbol.getValue();
            } else {
                i++;
            }
        }

        return sb.toString();
    }

    static void ResultRoman(String[] arrayString, char operator) throws ExceptionForCalculator{
        int number1 = RomanToArabic(arrayString[0]);
        int number2 = RomanToArabic(arrayString[1]);
        if(number1 > 10 | number2 > 10){
            throw new ExceptionForCalculator("Числа не могут быть больше 10");
        }

        int result = 0;
        switch (operator){
            case '+': result = number1 + number2;
                break;
            case '-': result = number1 - number2;
                break;
            case '*': result = number1 * number2;
                break;
            case '/': result = number1 / number2;
                break;
        }
        if(result < 1){
            throw new ExceptionForCalculator("Ответ при операциях с римскими числами не может быть < 1");
        }

        System.out.println(ArabicToRoman(result));
    }
    static void ResultArabian(String[] arrayString, char operator)throws ExceptionForCalculator{
        int number1 = Integer.parseInt(arrayString[0]);
        int number2 = Integer.parseInt(arrayString[1]);
        if(number1 > 10 | number2 > 10){
            throw new ExceptionForCalculator("Числа не могут быть больше 10");
        }

        int result = 0;
        switch (operator){
            case '+': result = number1 + number2;
                break;
            case '-': result = number1 - number2;
                break;
            case '*': result = number1 * number2;
                break;
            case '/': result = number1 / number2;
                break;
        }
        System.out.println(result);
    }
    static String[] ArrayString(String string) throws ExceptionForCalculator{
        String s = string.replaceAll("\\s+","");
        String [] array = s.split("[+\\-*/]");
        return array;
    }
    static char FindOperator(String string) throws ExceptionForCalculator {
        char space = ' ';
        if (string.contains("+")){
            space = '+';
        }
        if (string.contains("-")){
            space = '-';
        }
        if (string.contains("/")){
            space = '/';
        }
        if (string.contains("*")){
            space = '*';
        }
        return space;
    }

    static boolean CheckForInvalidCharacters(char [] array) throws ExceptionForCalculator{
        List Roman = new ArrayList();
        Roman.add('I');
        Roman.add('V');
        Roman.add('X');
        List Number = new ArrayList();
        Number.add('0');
        Number.add('1');
        Number.add('2');
        Number.add('3');
        Number.add('4');
        Number.add('5');
        Number.add('6');
        Number.add('7');
        Number.add('8');
        Number.add('9');
        List Operator = new ArrayList();
        Operator.add('+');
        Operator.add('-');
        Operator.add('*');
        Operator.add('/');
        Operator.add(' ');
        int count1 = 0;
        int count2 = 0;
        boolean bool;
        for(int i = 0; i < array.length; i++){
            if(!Roman.contains(array[i]) && !Operator.contains(array[i]) && !Number.contains(array[i])){
                throw new ExceptionForCalculator("Строка содержит недопустимые символы");
            }
            if(Roman.contains(array[i])){
                ++count1;
            }
            if (Number.contains(array[i])){
                ++count2;
            }
        }
        if (count1 != 0 && count2 != 0){
            throw new ExceptionForCalculator("Одновременно используются разные системы счисления");
        }
        if (count1 != 0){
            bool = true;
        } else { bool = false;}
        return bool;
    }

    static void CheckStartAndEnd (char [] array) throws ExceptionForCalculator{
        List Check = new ArrayList();
        Check.add(' ');
        Check.add('+');
        Check.add('*');
        Check.add('/');
        Check.add('-');
        if (Check.contains(array[0])){
            throw new ExceptionForCalculator("Строка должна начинаться с римских или арабских чисел");
        }
        if (array[0] == '0'){
            throw new ExceptionForCalculator("Строка не может начинаться с 0");
        }
        int i = array.length;
        if (Check.contains(array[i-1])){
            throw new ExceptionForCalculator("Строка должы заканчиваться римским или арабским числом");
        }
    }

    static void CheckOperator(char[] array) throws ExceptionForCalculator{
        int count2 = 0;
        for (int i = 0; i < array.length; i++){
            if (array[i] == '+' | array[i] == '-' | array[i] == '*' | array[i] == '/') {
                ++count2;
            }
        }
        if(count2 != 1) {
            throw new ExceptionForCalculator("Нужно использовать 1 оператор");
        }
    }

    static void CheckSpace (char [] array) throws ExceptionForCalculator{
        int count = 0;
        for (int i = 0; i < array.length; i++){
            if(array[i] == ' '){
                ++count;
            }
        }
        if(count != 2 && count != 0){
            throw new ExceptionForCalculator("Строка должна быть записана одним из способов: Число1 'оператор' Число2 или Число1'оператор'Число2");
        }
        int j = 0;
        List Check = new ArrayList();
        Check.add('+');
        Check.add('-');
        Check.add('/');
        Check.add('*');
        if(count == 2){
            while (array[j] != ' '){
                ++j;
            }
            if(!Check.contains(array[j+1])){
                throw new ExceptionForCalculator("Строка должна быть записана одним из способов: Число1 'оператор' Число2 или Число1'оператор'Число2");
            }
            if (array[j+2] != ' '){
                throw new ExceptionForCalculator("Строка должна быть записана одним из способов: Число1 'оператор' Число2 или Число1'оператор'Число2");
            }
        }
    }

}