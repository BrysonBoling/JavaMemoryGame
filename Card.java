import java.util.concurrent.ThreadLocalRandom;
//import java.lang.Math;
import java.awt.*;
import java.util.*;

class Card{
    public static int sizeX = SetBoard.COLUMNS, sizeY = SetBoard.ROWS;

    private static cards[][] gameArray = new cards[sizeX][sizeY];
    public static int counter = 0;
    private static int x1 = -1, y1 = -1, x2 = -1, y2 = -1;

    public static class cards{
        public int value;
        public int face;

        //location of card;
        public int x;
        public int y;

    }

    public static void setCards() {
        int maxValue = (SetBoard.COLUMNS * SetBoard.ROWS) / 2;

        int value;
        ArrayList<Integer> valueSet = new ArrayList<Integer>();
        for(int i = 0; i < maxValue; i++) {
            valueSet.add(i);
            valueSet.add(i);
        }

        for(int i = 0; i < sizeX; i++) {
            for(int j = 0; j < sizeY; j++) {
                value = ThreadLocalRandom.current().nextInt(0, valueSet.size());

                //initialize all cards
                cards data = new cards();

                data.value = valueSet.get(value);
                data.face = 0;
                gameArray[i][j] = data;
                iniPos(i, j);
                System.out.println(gameArray[i][j].x + " " + gameArray[i][j].y);

                gameArray[i][j] = data;
                valueSet.remove(value);
            }
        }

    }

    public static void iniPos(int x, int y) {
        Rectangle rect = new Rectangle(0, SetBoard.TILE_SIZE * x, SetBoard.TILE_SIZE * ((2 * y) + 1), SetBoard.TILE_SIZE);

        int xCoord = rect.y + (rect.height) / 2;
        int yCoord = rect.x + ((rect.width) / 2);

		//System.out.println(x + " " + y);

        gameArray[x][y].x = xCoord - 7;
        gameArray[x][y].y = yCoord + 9;
    }

    public static int getValue(int x, int y) {
        //System.out.println(gameArray[x][y].face);
        return gameArray[x][y].value;
    }

    public static int getFace(int x, int y) {
        return gameArray[x][y].face;
    }

    public static int getX(int x, int y) {
        return gameArray[x][y].x;
    }

    public static int getY(int x, int y) {
        return gameArray[x][y].y;
    }

    public static void flipCard(int x, int y){
        gameArray[x][y].face = 1;
        System.out.println("Flipped: " + gameArray[x][y].value);
        if (counter == 0) {
            System.out.println("I am here");
            x1 = x;
            y1 = y;
            counter++;
        }else if (counter == 1) {
            System.out.println("Here i am");
            x2 = x;
            y2 = y;
            counter++;
        } else {
            checkFlips();
        }
    }

    public static void undoFlip(int x, int y) {
        gameArray[x][y].face = 0;
    }

    public static int validFlip() {
        int valid = 0;

        if (x1 == -1) {
            valid = 1;
        }else if (x1 != x2 && y1 != y2) {
            valid = 1;
        }

        return valid;
    }

    public static void checkFlips() {
        //int valid = 1;
        if (counter == 2) {
            System.out.println(gameArray[x1][y1].value + " " + gameArray[x2][y2].value);
            if(gameArray[x1][y1].value != gameArray[x2][y2].value) {
                reset();
                //valid = 0;
            }else {
                x1 = x2 = y1 = y2 = -1;
                counter = 0;
            }
        }

        //return valid;

    }

    public static void reset() {
        Card.undoFlip(x1, y1);
        Card.undoFlip(x2, y2);
        
        x1 = x2 = y1 = y2 = -1;
        counter = 0;
        System.out.println(counter);
    }

    public static void wait(int ms) {
        try {
            Thread.sleep(ms);
        }
        catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}