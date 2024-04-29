package DiceJack;

import java.util.Scanner;

public class DiceJack {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int roll1 = rollDice();
        int roll2 = rollDice();
        int roll3 = rollDice();

        System.out.println("Enter three numbers between 1 and 6: ");

        int num1 = scanner.nextInt();
        int num2 = scanner.nextInt();
        int num3 = scanner.nextInt();

        if (isLessThan1(num1, num2, num3) || isHigherThan6(num1, num2, num3)) {
            System.out.println("You entered numbers outside the valid range!");
            System.exit(0);
        }

        int sumOfNumbers = num1 + num2 + num3;
        int sumOfDiceRoll = roll1 + roll2 + roll3;

        System.out.println("Your sum: " + sumOfNumbers + "\nDice sum: " + sumOfDiceRoll);

        if (userWon(sumOfNumbers, sumOfDiceRoll)) {
            System.out.println("\nYou have won!");
        } else {
            System.out.println("\nBetter luck next time.");
        }

        scanner.close();
    }

    public static boolean isLessThan1(int num1, int num2, int num3) {
        return (num1 < 1 || num2 < 1 || num3 < 1);
    }

    public static boolean isHigherThan6(int num1, int num2, int num3) {
        return (num1 > 6 || num2 > 6 || num3 > 6);
    }

    public static boolean userWon(int sumNumbers, int sumDiceRolls) {
        return (sumNumbers > sumDiceRolls && (sumNumbers - sumDiceRolls) < 3);
    }

    public static int rollDice() {

        double randomNumber = Math.random() * 6;
        randomNumber += 1;
        return (int)randomNumber;
    }
}
