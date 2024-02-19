package AdventureGame;

import java.util.*;

public class Adventure {
    public static class Item {
        private String name;
        private String description;

        public Item(String name, String description) {
            this.name = name;
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }
    }

    public static class Puzzle {
        private String description;
        private String solution;

        public Puzzle(String description, String solution) {
            this.description = description;
            this.solution = solution;
        }

        public String getDescription() {
            return description;
        }

        public boolean checkSolution(String input) {
            return input.equalsIgnoreCase(solution);
        }
    }

    public static class NPC {
        private String name;
        private String dialog;

        public NPC(String name, String dialog) {
            this.name = name;
            this.dialog = dialog;
        }

        public String getName() {
            return name;
        }

        public String getDialog() {
            return dialog;
        }
    }

    private Map<String, Item> items;
    private Map<String, Puzzle> puzzles;
    private Map<String, NPC> npcs;
    private Map<String, Boolean> puzzleSolved;

    private static final String GAME_LOCATIONS = """
            road,at the end of the road, W: hill, E:well house,S:valley,N:forest
            hill,on top of hill with a view in all directions,N:forest, E:road
            well house,inside a well house for a small spring,W:road,N:lake,S:stream
            valley,in a forest valley beside a tumbling stream,N:road,W:hill,E:stream
            forest,at the edge of a thick dark forest,S:road,E:lake
            lake,by an alpine lake surrounded by wildflowers,W:forest,S:well house
            stream,near a stream with a rocky bed,W:valley, N:well house
            """;

    private enum Compass {
        E, N, S, W;

        private static final String[] directions = {"East", "North", "South", "West"};

        public String getString() {
            return directions[this.ordinal()];
        }
    }

    private record Location(String description, Map<Compass, String> nextPlaces) {}

    private String lastPlace;
    private Map<String, Location> adventureMap = new HashMap<>();

    public Adventure() {
        this(null);
    }

    public Adventure(String customLocations) {
        loadLocations(GAME_LOCATIONS);
        if (customLocations != null) {
            loadLocations(customLocations);
        }

        items = new HashMap<>();
        puzzles = new HashMap<>();
        npcs = new HashMap<>();
        puzzleSolved = new HashMap<>();
    }

    // Method to add an item to the game
    public void addItem(String name, String description) {
        items.put(name, new Item(name, description));
    }

    // Method to add a puzzle to the game
    public void addPuzzle(String name, String description, String solution) {
        puzzles.put(name, new Puzzle(description, solution));
    }

    // Method to add an NPC to the game
    public void addNPC(String name, String dialog) {
        npcs.put(name, new NPC(name, dialog));
    }

    // Method to interact with an item
    public void interactWithItem(String itemName) {
        Item item = items.get(itemName);
        if (item != null) {
            System.out.println("You interacted with: " + item.getName());
            // Add your item interaction logic here
        } else {
            System.out.println("Item not found.");
        }
    }

    public void solvePuzzle(String puzzleName, String solution) {
        Puzzle puzzle = puzzles.get(puzzleName);
        if (puzzle != null) {
            boolean alreadySolved = puzzleSolved.getOrDefault(puzzleName, false); // Check if puzzle already solved
            if (puzzle.checkSolution(solution) && !alreadySolved) { // Check if solution is correct and puzzle not already solved
                System.out.println("You solved the puzzle!");
                puzzleSolved.put(puzzleName, true); // Mark puzzle as solved
            } else if (puzzle.checkSolution(solution.toLowerCase()) && !alreadySolved) { // Check lowercase solution
                System.out.println("You solved the puzzle!");
                puzzleSolved.put(puzzleName, true); // Mark puzzle as solved
            } else {
                if (alreadySolved) {
                    System.out.println("Puzzle already solved.");
                } else {
                    System.out.println("Incorrect solution.");
                }
            }
        } else {
            System.out.println("Puzzle not found.");
        }
    }

    public void talkToNPC(String npcName) {
        NPC npc = npcs.get(npcName);
        if (npc != null) {
            System.out.println("You talked to " + npc.getName() + ": " + npc.getDialog());
        } else {
            System.out.println("NPC not found.");
        }
    }

    private void loadLocations(String data) {
        for (String s: data.split("\\R")) {
            String[] parts = s.split(",", 3);
            Arrays.asList(parts).replaceAll(String::trim);
            Map<Compass, String> nextPlaces = loadDirections(parts[2]);
            Location location = new Location(parts[1], nextPlaces);
            adventureMap.put(parts[0], location);
        }

//        adventureMap.forEach((k, v) -> System.out.printf("%s:%s%n", k, v));
    }

    private Map<Compass, String> loadDirections(String nexPlaces) {

        Map<Compass, String> directions = new HashMap<>();
        List<String> nextSteps = Arrays.asList(nexPlaces.split(","));

        nextSteps.replaceAll(String::trim);
        for (String nextPlace: nextSteps) {
            String[] splits = nextPlace.split(":");
            Compass compass = Compass.valueOf(splits[0].trim());
            String destination = splits[1].trim();
            directions.put(compass, destination);
        }

        return directions;
    }

    private void visit(Location location) {

        System.out.printf("*** You're standing %s *** %n", location.description);
        System.out.println("\tFrom here, you can see:");

        location.nextPlaces.forEach((k, v) -> {
            System.out.printf("\t* A %s to the %s (%S) %n", v, k.getString(), k);
        });

        System.out.print("Select Your Compass (Q to quit) >> ");
    }

    public void move(String direction) {

        var nextPlaces = adventureMap.get(lastPlace).nextPlaces;
        String nextPlace = null;
        if ("ENSW".contains(direction)) {
            nextPlace = nextPlaces.get(Compass.valueOf(direction));
            if (nextPlace != null) {
                play(nextPlace);
                return;
            }
        } else {
            System.out.println("!! Invalid direction, try again !!");
        }
        visit(adventureMap.get(lastPlace));
    }

    public void play(String location) {

        if (adventureMap.containsKey(location)) {
            Location next = adventureMap.get(location);
            lastPlace = location;
            visit(next);
        } else {
            System.out.println(location + " is an invalid location");
        }
    }

    public static void main(String[] args) {
        Adventure game = new Adventure();
        game.addItem("Sword", "A sharp sword for battles");
        game.addPuzzle("Riddle", "What has keys but can't open locks?", "Piano");
        game.addNPC("Guard", "Halt! Who goes there?");
        game.play("road");
    }
}

