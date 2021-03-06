/* Controls the playing field and the characters moving on it */

import java.util.ArrayList;

public class Board {

    private ArrayList<Character> players;
    //2D arraylist of positions used as the board
    private Position[][] positions = new Position[11][11];
    private Character[][] ranking = new Character[4][0];
    
    
    //Constructor
    public Board(Character one, Character two, Character three, Character four) throws InterruptedException {
        shuffle(1);
        addC(one);
        addC(two);
        addC(three);
        addC(four);
        
        ranking[0] = new Character[] {one, two, three, four};
        ranking[1] = new Character[] {null,null,null};
        ranking[2] = new Character[] {null,null};
        ranking[3] = new Character[] {null};
    }
    
    //Methods
    public void addC(Character Boy_X) {
        positions[Boy_X.getX()+5][Boy_X.getY()+5].addCharacter(Boy_X);
    }
    public void addCB(int phase, Character Boy_X) throws InterruptedException, Exception {
        showMap(phase, Boy_X);
        positions[Boy_X.getX()+5][Boy_X.getY()+5].addCharacterB(Boy_X);
    }
    
    public void rmC(Character Boy_X) {
        positions[Boy_X.getX()+5][Boy_X.getY()+5].clearCharacter(Boy_X);
    }
    
    public void shuffle(int phase) {
        int val = (int)(Math.random()* 4);
        if (val == 0) {
            positions[5][10] = new StarChest(phase);
            positions[0][5] = new EqChest(phase);
            positions[10][5] = new EqChest(phase);
            positions[5][0] = new EqChest(phase);
        }
        else if (val == 1) {
            positions[0][5] = new StarChest(phase);
            positions[5][10] = new EqChest(phase);
            positions[10][5] = new EqChest(phase);
            positions[5][0] = new EqChest(phase);
        }
        else if (val == 2) {
            positions[10][5] = new StarChest(phase);
            positions[5][10] = new EqChest(phase);
            positions[0][5] = new EqChest(phase);
            positions[5][0] = new EqChest(phase);
        }
        else {
            positions[5][0] = new StarChest(phase);
            positions[5][10] = new EqChest(phase);
            positions[0][5] = new EqChest(phase);
            positions[10][5] = new EqChest(phase);
        }
        
        for (int x = 1, y = 5; x<10; x++) {
            setEvent(x,y);
            setEvent(y,x);
        }
        
        for(int x = 1, y = 6; x<5; x++, y++) {
            setEvent(x,y);
            setEvent(y,x);
            setEvent(x,y-2*x);
            setEvent(y,y-2*x+5);
        }
    }
    
    public void setEvent(int x, int y) {
        int val = (int)(Math.random() * 100);
        if (val < 35) positions[x][y] = new BlueSpace();
        else if (val < 50) positions[x][y] = new RedSpace();
        else if (val < 75) positions[x][y]= new StatChange();
        else if (val < 80) positions[x][y] = new Bandit();
        else positions[x][y] = new Dimbling();
    }
    
    public void setRanking(Character one, Character two, Character three, Character four) {
        resetRanking(); 
        int[] numRank = new int[4];
        numRank[0] = (int)Math.pow(one.getCoins(), one.getStars() + 1);
        numRank[1] = (int)Math.pow(two.getCoins(), two.getStars() + 1);
        numRank[2] = (int)Math.pow(three.getCoins(), three.getStars() + 1);
        numRank[3] = (int)Math.pow(four.getCoins(), four.getStars() + 1);
        int sorted = 0;
        int threshold = 0;
        int max;
        int rank = 1;
        while (sorted < 4) {
            max = 0;
            for (int x = 0; x<4; x++) {
                if (sorted == 0) {
                    if(numRank[x] >= max) max = numRank[x];
                }
                else if (numRank[x] < threshold && numRank[x] > max) max = numRank[x];
            }
            threshold = max;
            for (int x = 0; x<4; x++) {
                if (numRank[x] == max) {
                    setRank(rank - 1, x, one, two, three, four);
                    sorted++;
                }
            }
            rank++;
        }
    }
    
    public void resetRanking() {
        ranking = new Character[4][0];
        ranking[0] = new Character[] {null,null,null,null};
        ranking[1] = new Character[] {null,null,null};
        ranking[2] = new Character[] {null,null};
        ranking[3] = new Character[] {null};
    }
    
    public void setRank(int rank, int indicator, Character one, Character two, Character three, Character four) {
        int x = 0;
        while (ranking[rank][x] != null) {
            x++;
        }
        if (indicator == 0) ranking[rank][x] = one;
        else if (indicator == 1) ranking[rank][x] = two;
        else if (indicator == 2) ranking[rank][x] = three;
        else if (indicator == 3) ranking[rank][x] = four;
    }
    
    public Position getPos(int x, int y) {
        return positions[x][y];
    }
    
    public Character[][] getRanking() {
        return ranking;
    }
    
    public void setTime() {
        for(int x = 0; x<11; x++) {
            for(int y = 0; y<11; y++) {
                if (positions[x][y]!=null) positions[x][y].setTime();
            }
        }
    }

    public void showMap(int Phase, Character Boy_X) {
        String temp;
        System.out.println("Phase: " + Phase + "  Character: " + Boy_X.getName() + "  Roll: " + Boy_X.getRoll() + "  Coins: " + Boy_X.getCoins() + "  Stars: " + Boy_X.getStars() + "\n");
        temp = "";
        for (int x = 0; x<4 && ranking[0][x] != null; x++) temp+= ranking[0][x].getName() + " ";
        System.out.println("                    " + positions[5][10].look(Boy_X) + "                     1st: " + temp + "\n");
        temp = "";
        for (int x = 0; x<3 && ranking[1][x] != null; x++) temp+= ranking[1][x].getName() + " ";
        System.out.println("                " + positions[4][9].look(Boy_X) + " " + positions[5][9].look(Boy_X) + " " + positions[6][9].look(Boy_X) + "                 2nd: " + temp + "\n");
        temp = "";
        for (int x = 0; x<2 && ranking[2][x] != null; x++) temp+= ranking[2][x].getName() + " ";
        System.out.println("            " + positions[3][8].look(Boy_X) + "     " + positions[5][8].look(Boy_X) + "     " + positions[7][8].look(Boy_X) + "             3rd: " + temp + "\n");
        temp = "";
        for (int x = 0; x<1 && ranking[3][x] != null; x++) temp+= ranking[3][x].getName() + " ";
        System.out.println("        " + positions[2][7].look(Boy_X) + "         " + positions[5][7].look(Boy_X) + "         " + positions[8][7].look(Boy_X) + "         4th: " + temp + "\n");
        System.out.println("    " + positions[1][6].look(Boy_X) + "             " + positions[5][6].look(Boy_X) + "             " + positions[9][6].look(Boy_X) + "\n");
        System.out.println(positions[0][5].look(Boy_X) + " " + positions[1][5].look(Boy_X) + " " + positions[2][5].look(Boy_X) + " " + positions[3][5].look(Boy_X) + " " + positions[4][5].look(Boy_X) + " " + positions[5][5].look(Boy_X) + " " + positions[6][5].look(Boy_X) + " " + positions[7][5].look(Boy_X) + " " + positions[8][5].look(Boy_X) + " " + positions[9][5].look(Boy_X) + " " + positions[10][5].look(Boy_X) + "\n");
        System.out.println("    " + positions[1][4].look(Boy_X) + "             " + positions[5][4].look(Boy_X) + "             " + positions[9][4].look(Boy_X) + "\n");
        System.out.println("        " + positions[2][3].look(Boy_X) + "         " + positions[5][3].look(Boy_X) + "         " + positions[8][3].look(Boy_X) + "\n");
        System.out.println("            " + positions[3][2].look(Boy_X) + "     " + positions[5][2].look(Boy_X) + "     " + positions[7][2].look(Boy_X) + "\n");
        System.out.println("                " + positions[4][1].look(Boy_X) + " " + positions[5][1].look(Boy_X) + " " + positions[6][1].look(Boy_X) + "\n");
        System.out.println("                    " + positions[5][0].look(Boy_X) + "\n\n");
    }
    
    
}



