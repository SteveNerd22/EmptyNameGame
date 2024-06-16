package Features.Map;

import Features.Components.Drawable;
import Window.GamePanel;

import java.awt.*;

public class Room implements Drawable {
    private String type;
    private boolean doorNorth;
    private boolean doorSouth;
    private boolean doorEast;
    private boolean doorWest;
    public int x;
    public int y;

    public Room() {
        this.type = "empty";
    }
    private final int layer = 0;

    public Room(String type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }
    public Room(String type, int pos, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
        if(pos == 1) {
            this.doorNorth = false;
            this.doorSouth = true;
            this.doorEast = true;
            this.doorWest = false;
        }
        if(pos == 2) {
            this.doorNorth = false;
            this.doorSouth = true;
            this.doorEast = false;
            this.doorWest = true;
        }
        if(pos == 3) {
            this.doorNorth = true;
            this.doorSouth = false;
            this.doorEast = true;
            this.doorWest = false;
        }
        if(pos == 4) {
            this.doorNorth = true;
            this.doorSouth = false;
            this.doorEast = false;
            this.doorWest = true;
        }
    }

    public String getType() {
        return type;
    }

    public boolean hasDoorNorth() {
        return doorNorth;
    }

    public void setDoorNorth(boolean doorNorth) {
        this.doorNorth = doorNorth;
    }

    public boolean hasDoorSouth() {
        return doorSouth;
    }

    public void setDoorSouth(boolean doorSouth) {
        this.doorSouth = doorSouth;
    }

    public boolean hasDoorEast() {
        return doorEast;
    }

    public void setDoorEast(boolean doorEast) {
        this.doorEast = doorEast;
    }

    public boolean hasDoorWest() {
        return doorWest;
    }

    public void setDoorWest(boolean doorWest) {
        this.doorWest = doorWest;
    }

    public void printRoom() {
        // Stampiamo la parte superiore della stanza con le porte nord
        System.out.print("+");
        if (hasDoorNorth()) {
            System.out.print("   ");
        } else {
            System.out.print("---");
        }
        System.out.println("+");

        // Stampiamo il lato sinistro e destro della stanza con il contenuto e le porte
        if (hasDoorWest()) {
            System.out.print("  ");
        } else {
            System.out.print("| ");
        }
        System.out.print(getType().charAt(0));
        if (hasDoorEast()) {
            System.out.println("  ");
        } else {
            System.out.println(" |");
        }

        // Stampiamo la parte inferiore della stanza con le porte sud
        System.out.print("+");
        if (hasDoorSouth()) {
            System.out.print("   ");
        } else {
            System.out.print("---");
        }
        System.out.println("+");
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.RED);
        if(doorEast)
            fillRectCenter(g, 10, 20, 1000, 500);
        if(doorWest)
            fillRectCenter(g, 10, 20, 0, 500);
        if(doorNorth)
            fillRectCenter(g, 20, 10, 500, 0);
        if(doorSouth)
            fillRectCenter(g, 20, 10, 500, 1000);
    }

    @Override
    public int getLayer() {
        return layer;
    }

    @Override
    public void setLayer(int layer) {

    }

    public boolean isOpen() {
        return true;
    }

    @Override
    public String toString() {
        int coordx = 10;
        int coordy = 10;
        for(int i = 0; i < 7; i++) {
            for(int j = 0; j < 7; j++) {
                if(this.equals(GamePanel.map.rooms[i][j])) {
                    coordx = j;
                    coordy = i;
                }
            }
        }
        return "Rooms["+ coordy+"]["+coordx+"]";
    }
}


