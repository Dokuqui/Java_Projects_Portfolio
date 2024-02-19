package AdventureGame;

import java.util.Scanner;

public class StartGame {
    public static void main(String[] args) {
        String myLocations = """        
            lake,at the edge of Lake Tim,E:ocean,W:forest,S:well house,N:cave
            ocean,on Tim's beach before an angry sea,W:lake
            cave,at the mouth of Tim's bat cave,E:ocean,W:forest,S:lake
            """;

        Adventure game = new Adventure(myLocations);
        game.addItem("Key", "An old rusty key");
        game.addPuzzle("Chest", "What needs a key but has no lock?", "Chest");
        game.addNPC("Guard", "You shall not pass without the key!");

        game.play("lake");

        Scanner scanner = new Scanner(System.in);
        boolean puzzleSolved = false; // Flag to track if the puzzle is solved

        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("Q")) {
                System.out.println("Exiting the game...");
                break;
            } else if (!puzzleSolved) { // Check if not in puzzle-solving mode
                if (input.equalsIgnoreCase("I")) {
                    game.interactWithItem("Key");
                } else if (input.equalsIgnoreCase("P")) { // Changed from "S" to "P" for solving puzzle
                    System.out.println("Enter the solution to the puzzle:");
                    String solution = scanner.nextLine().trim();
                    game.solvePuzzle("Chest", solution);
                    puzzleSolved = true; // Set puzzleSolved flag to true after attempting to solve the puzzle
                } else if (input.equalsIgnoreCase("T")) {
                    game.talkToNPC("Guard");
                } else if (input.length() == 1 && "ENSW".contains(input.toUpperCase())) {
                    game.move(input.toUpperCase());
                } else {
                    System.out.println("Invalid input. Please try again.");
                }
            } else { // If puzzle is solved, return to main game loop
                if (input.equalsIgnoreCase("M")) {
                    puzzleSolved = false; // Reset puzzleSolved flag
                    System.out.println("Back to the game.");
                } else {
                    System.out.println("You're still in puzzle-solving mode. Enter 'M' to return to the game.");
                }
            }
        }
    }
}
