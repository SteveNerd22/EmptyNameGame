package Features.Map;

import Features.Components.Drawable;
import Structures.DrawableList;
import Window.GamePanel;

import java.util.Random;
import java.util.Stack;

public class Map {
    int level;
    Room[][] rooms = new Room[7][7];
    String startingRoomPosition;
    int startX, startY;

    public Map(int level) {
        this.level = level;
        genLevel();
    }

    private void genLevel() {
        Random rand = new Random();

        // Genera la posizione iniziale
        switch (rand.nextInt(4)) {
            case 0 -> {
                startingRoomPosition = "N";
                startX = 0;
                startY = 3;
            }
            case 1 -> {
                startingRoomPosition = "S";
                startX = 6;
                startY = 3;
            }
            case 2 -> {
                startingRoomPosition = "W";
                startX = 3;
                startY = 0;
            }
            case 3 -> {
                startingRoomPosition = "E";
                startX = 3;
                startY = 6;
            }
        }

        // Inizializza tutte le stanze come vuote
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                rooms[i][j] = new Room();
            }
        }

        // Imposta le stanze speciali
        rooms[0][0] = new Room("corner", 1, 0, 0);
        rooms[6][6] = new Room("corner", 4, 6, 6);
        rooms[0][6] = new Room("corner", 2, 0, 6);
        rooms[6][0] = new Room("corner", 3, 6, 0);
        rooms[3][3] = new Room("boss", 3, 3);

        // Posiziona la stanza iniziale
        rooms[startX][startY] = new Room("start", startX, startY);

        // Genera le stanze
        generateRooms(startX, startY);
    }

    private void generateRooms(int startX, int startY) {
        Random rand = new Random();
        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{startX, startY});

        while (!stack.isEmpty()) {
            int[] current = stack.pop();
            int x = current[0];
            int y = current[1];

            // Definisce le direzioni (nord, sud, est, ovest)
            int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
            shuffleArray(directions, rand);

            for (int[] direction : directions) {
                int newX = x + direction[0];
                int newY = y + direction[1];

                if (isValidRoom(newX, newY) && rooms[newX][newY].getType().equals("empty")) {
                    rooms[newX][newY] = new Room("done", newX, newY);
                    stack.push(new int[]{newX, newY});

                    // Creiamo le porte casuali tra le stanze
                    createRandomDoors(x, y, newX, newY, rand);
                }
            }
        }
        rooms[0][1].setDoorWest(rand.nextBoolean());
        rooms[1][0].setDoorNorth(rand.nextBoolean());
        rooms[0][5].setDoorEast(rand.nextBoolean());
        rooms[1][6].setDoorNorth(rand.nextBoolean());
        rooms[5][0].setDoorSouth(rand.nextBoolean());
        rooms[6][1].setDoorWest(rand.nextBoolean());
        rooms[5][6].setDoorSouth(rand.nextBoolean());
        rooms[6][5].setDoorEast(rand.nextBoolean());
        //North-West room doors
        rooms[0][0].setDoorEast(rooms[0][1].hasDoorWest());
        rooms[0][0].setDoorSouth(rooms[1][0].hasDoorNorth());
        //North-East room doors
        rooms[0][6].setDoorWest(rooms[0][5].hasDoorEast());
        rooms[0][6].setDoorSouth(rooms[1][6].hasDoorNorth());
        //South-West room doors
        rooms[6][0].setDoorNorth(rooms[5][0].hasDoorSouth());
        rooms[6][0].setDoorEast(rooms[6][1].hasDoorWest());
        //South-East room doors
        rooms[6][6].setDoorNorth(rooms[5][6].hasDoorSouth());
        rooms[6][6].setDoorWest(rooms[6][5].hasDoorEast());
        //Center
        switch(rand.nextInt(4)) {
            case 0 -> {
                rooms[3][3].setDoorWest(true);
                rooms[3][2].setDoorNorth(true);
            }
            case 1 -> {
                rooms[3][3].setDoorNorth(true);
                rooms[2][3].setDoorSouth(true);
            }
            case 2 -> {
                rooms[3][3].setDoorEast(true);
                rooms[3][4].setDoorWest(true);
            }
            case 3 -> {
                rooms[3][3].setDoorSouth(true);
                rooms[4][3].setDoorNorth(true);
            }
        }
    }

    private void createRandomDoors(int x1, int y1, int x2, int y2, Random rand) {
        if (x1 < x2) {
            rooms[x1][y1].setDoorSouth(true);
            rooms[x2][y2].setDoorNorth(true);
        } else if (x1 > x2) {
            rooms[x1][y1].setDoorNorth(true);
            rooms[x2][y2].setDoorSouth(true);
        } else if (y1 < y2) {
            rooms[x1][y1].setDoorEast(true);
            rooms[x2][y2].setDoorWest(true);
        } else if (y1 > y2) {
            rooms[x1][y1].setDoorWest(true);
            rooms[x2][y2].setDoorEast(true);
        }
    }

    // Metodo per mescolare l'array di direzioni per aggiungere casualità
    private void shuffleArray(int[][] array, Random rand) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            int[] temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    private boolean isValidRoom(int x, int y) {
        return x >= 0 && x < 7 && y >= 0 && y < 7;
    }

    public void printMap() {
        for (int i = 0; i < 7; i++) {
            // Stampiamo la riga superiore della stanza
            for (int j = 0; j < 7; j++) {
                System.out.print("+");
                if (rooms[i][j].hasDoorNorth()) {
                    System.out.print("   ");
                } else {
                    System.out.print("---");
                }
            }
            System.out.println("+");

            // Stampiamo il contenuto della stanza e le pareti laterali
            for (int j = 0; j < 7; j++) {
                if (rooms[i][j].hasDoorWest()) {
                    System.out.print("  ");
                } else {
                    System.out.print("| ");
                }
                if(rooms[i][j].getType().charAt(0) != 'd')
                    System.out.print(rooms[i][j].getType().charAt(0) + " ");
                else
                    System.out.print("  ");
            }
            if (rooms[i][6].hasDoorEast()) {
                System.out.println(" ");
            } else {
                System.out.println("|");
            }
        }

        // Stampiamo la riga inferiore della mappa
        for (int j = 0; j < 7; j++) {
            System.out.print("+");
            if (rooms[6][j].hasDoorSouth()) {
                System.out.print("   ");
            } else {
                System.out.print("---");
            }
        }
        System.out.println("+");
    }

    public void printCenter() {
        for (int i = 2; i < 5; i++) {
            for (int j = 2; j < 5; j++) {
                System.out.println();
                System.out.println();
                rooms[i][j].printRoom();
                System.out.println();
                System.out.println();
            }
        }
    }

    public void printRooms() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.println();
                System.out.println();
                rooms[i][j].printRoom();
                System.out.println();
                System.out.println();
            }
        }
    }

    public Room getStartingRoom(DrawableList<Drawable> drawList) {
        drawList.add(rooms[startX][startY]);
        return rooms[startX][startY];
    }

    public Room changeRoom(int x, int y, DrawableList<Drawable> list) {
        list.remove(GamePanel.currentRoom);
        list.add(rooms[x][y]);
        return rooms[x][y];
    }
}