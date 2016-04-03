/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.novakvo5;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author vonovak
 */
public class PlayBoard {

    private int[][] boardArray;
    private ArrayList<Integer> checkFields;
    private Player player1;
    private Player player2;

    /**
     * konstruktor - vytvari novou matici reprezentujici hraci plochu
     * @param size pocet policek na stranu hraci plochy
     * @param player1 instance prvniho hrace
     * @param player2 instance druheho hrace
     */
    public PlayBoard(int size, Player player1, Player player2) {
        boardArray = new int[size][size];
        checkFields = new ArrayList<Integer>();
        this.player1 = player1;
        this.player2 = player2;
    }

    /**
     *nastavi vsechny prvky v matici hraciho pole na 0
     */
    public void cleanPlayBoard() {
        for (int i = 0; i < boardArray.length; i++) {
            for (int j = 0; j < boardArray[0].length; j++) {
                boardArray[i][j] = 0;
            }
        }
    }

    /**
     *Priradi matici v parametru matici hraci plochy.
     * Pouziva se pri loadu hry
     * @param board priradi tuto matici matici hraci plochy
     */
    public void setBoard(int[][] board) {
        this.boardArray = board;
    }

    /**
     *zjistuje, zda je hraci plocha plna (zda je kam hrat)
     * @return true=hraci plocha je plna, false=hraci plocha neni plna
     */
    public boolean isFull() {
        for (int i = 0; i < boardArray.length; i++) {
            for (int j = 0; j < boardArray[0].length; j++) {
                if (boardArray[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * vraci cislo hrace, ktery je na rade( 1 nebo 2 )
     * @return cislo hrace, ktery je na rade( 1 nebo 2 )
     */
    public int playerOnTurn() {
        int numOfPlr1Fields = 0;
        int numOfPlr2Fields = 0;
        for (int i = 0; i < boardArray.length; i++) {
            for (int j = 0; j < boardArray[0].length; j++) {
                if (boardArray[i][j] == 1) {
                    numOfPlr1Fields++;
                } else if (boardArray[i][j] == 2) {
                    numOfPlr2Fields++;
                }
            }
        }

        if (numOfPlr1Fields > numOfPlr2Fields) {
            return 2;
        } else {
            return 1;
        }
    }

    /**
     * nastavuje prvek matice hraciho pole na hodnotu hrace
     * @param x sloupec matice
     * @param y radek matice
     * @param playerNum cislo hrace
     */
    public void setFieldValue(int x, int y, int playerNum) {
        boardArray[y][x] = playerNum;
    }

    /**
     *vraci hodnotu prvku matice hraciho pole
     * @param x sloupec matice
     * @param y radek matice
     * @return hodnota prvku matice
     */
    public int getFieldValue(int x, int y) {
        return boardArray[y][x];
    }

    @Override
    public String toString() {
        return "PlayBoard{" + "board=" + boardArray + "checkFields=" + checkFields + '}';
    }

    /**
     *zjistuje, zda je policko (prvek matice hraci plochy) volne
     * @param x sloupec matice
     * @param y radek matice
     * @return true= policko je volne. False = policko je zaplnene
     */
    public boolean fieldIsFree(int x, int y) {
        if (boardArray[y][x] == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Tato funkce jen zjisti, zda v okoli zadaneho tahu jsou pole stejneho hrace.
     * Pokud takova pole existuji, zavola se checkForFiveInRow, ktera provadi samotnou detekci vyhry.
     * @param x sloupec matice
     * @param y radek matice
     * @return cislo hrace, ktery vyhral. Jestlize jeste nikdo nevyhral, vraci 0
     */
    public int checkForWinner(int x, int y) {
        //cistim arraylist
        checkFields.clear();
        int playerNum = boardArray[x][y];
        if (y != 0) {
            if (boardArray[x][y - 1] == playerNum) {
                checkFields.add(y - 1);
                checkFields.add(x);
                checkFields.add(-1);
                checkFields.add(0);
            }
        }
        if (x != boardArray.length - 1 && y != 0) {
            //muzu na x+1 a y-1 tj case 2
            if (boardArray[x + 1][y - 1] == playerNum) {
                checkFields.add(y - 1);
                checkFields.add(x + 1);
                checkFields.add(-1); //y -1
                checkFields.add(1); //x +1

            }
        }
        if (x != boardArray.length - 1) {
            //muzu na x+1 a y tj case 3
            if (boardArray[x + 1][y] == playerNum) {
                checkFields.add(y);
                checkFields.add(x + 1);
                checkFields.add(0);
                checkFields.add(1);
            }
        }
        if (x != boardArray.length - 1 && y != boardArray[0].length - 1) {
            //muzu x+1 a y+1 tj case 4
            if (boardArray[x + 1][y + 1] == playerNum) {
                checkFields.add(y + 1);
                checkFields.add(x + 1);
                checkFields.add(1);
                checkFields.add(1);
            }
        }
        if (y != boardArray[0].length - 1) {
            //muzu na x a y+1 tj case 5
            if (boardArray[x][y + 1] == playerNum) {
                checkFields.add(y + 1);
                checkFields.add(x);
                checkFields.add(1);
                checkFields.add(0);
            }
        }
        if (x != 0 && y != boardArray[0].length - 1) {
            //muzu na x-1 a y+1 tj case 6
            if (boardArray[x - 1][y + 1] == playerNum) {
                checkFields.add(y + 1);
                checkFields.add(x - 1);
                checkFields.add(1);
                checkFields.add(-1);
            }
        }
        if (x != 0) {
            //muzu na x-1 a y tj case 7
            if (boardArray[x - 1][y] == playerNum) {
                checkFields.add(y);
                checkFields.add(x - 1);
                checkFields.add(0);
                checkFields.add(-1);
            }
        }
        if (x != 0 && y != 0) {
            //muzu x-1 a y-1 tj case 8
            if (boardArray[x - 1][y - 1] == playerNum) {
                checkFields.add(y - 1);
                checkFields.add(x - 1);
                checkFields.add(-1);
                checkFields.add(-1);
            }
        }

        for (int i = 0; i < checkFields.size(); i += 4) {
            if (chceckForFiveInRow(checkFields.get(i), checkFields.get(i + 1),
                    checkFields.get(i + 2), checkFields.get(i + 3))) {
                return playerNum;
            }
        }
        return 0;
    }

    /**
     *vzdy vezme souradnice posledniho tahu a zjisti, jestli tento tah nekde umoznil
     * dat dohromady 5 nebo vice policek stjneho hrace v rade - a vyhrat
     * @param x radek matice
     * @param y sloupec matice
     * @param diffX
     * @param diffY
     * @return true = hrac vyhral, false = nebyl detekovan vyherce
     */
    private boolean chceckForFiveInRow(int x, int y, int diffX, int diffY) {
        int numOfFieldsInRow = 1;
        int playerNum = boardArray[y][x];

        //nalezeni zacatku hracovy "cary"
        while (true) {
            if (x + diffX == -1 || x + diffX == boardArray.length || y + diffY == boardArray[0].length || y + diffY == -1) {
                //tady to znamena, ze uz se neda dal, jinak bych sel za hranice pole
                break;
            } else {
                if (boardArray[y + diffY][x + diffX] == playerNum) {
                    x = x + diffX;
                    y = y + diffY;
                } else {
                    break;
                }
            }
        }

        //nyni musim projet celou caru, takze se obracim
        diffX *= -1;
        diffY *= -1;

        while (true) {
            if (x + diffX == -1 || x + diffX == boardArray.length || y + diffY == boardArray[0].length || y + diffY == -1) {
                //tady to znamena, ze uz se neda dal, jinak bych sel za hranice pole
                break;
            }
            if (boardArray[y + diffY][x + diffX] == playerNum) {
                numOfFieldsInRow++;
                x = x + diffX;
                y = y + diffY;
            } else {
                break;
            }
        }

        //zde se urcuje, na kolik se hraje policek - defaultne 5 nebo vic
        if (numOfFieldsInRow >= 5) {
            return true;
        }
        return false;
    }

    /**
     *vola se pri nahravani hry - nahrava pouze hraci pole
     * @param loadFile soubor s ulozenou hrou
     * @return true=uspesne nahrano, false se nevraci
     * @throws Exception haze vyjimky s informacemi o chybach v souboru
     */
    public boolean loadPlayBoardArray(File loadFile) throws Exception {
        int[][] loadedPlayBoard = new int[PlayBoardGUI.NUMOFSQUARES][PlayBoardGUI.NUMOFSQUARES];
        Scanner bfr = new Scanner((loadFile));
        String line;
        int temp = 0;

        for (int i = 0; i < loadedPlayBoard.length; i++) {
            line = bfr.nextLine();
            if (line.length() == PlayBoardGUI.NUMOFSQUARES) {
                for (int j = 0; j < loadedPlayBoard[0].length; j++) {
                    temp = Integer.valueOf(line.substring(j, j + 1)).intValue();
                    if (temp > -1 && temp < 3) {
                        loadedPlayBoard[i][j] = temp;
                    } else {
                        //pokud cislo nepatri do rozmezi
                        throw new Exception("not the game file (1)");
                    }
                }
            } else {
                //pokud delka radku neodpovida
                throw new Exception("not the game file (2)");
            }
        }

        this.boardArray = loadedPlayBoard;
        if (this.isFull()) {
            throw new Exception("Board is full");
        }

        int winNum = 0;
        for (int i = 0; i < loadedPlayBoard.length; i++) {
            for (int j = 0; j < loadedPlayBoard[0].length; j++) {
                winNum = checkForWinner(i, j);

                if (winNum != 0) {
                    cleanPlayBoard();
                    throw new Exception("there is already a winner");
                }
            }
        }

        return true;
    }

    /**
     *vola se pri nahravani hry - nahrava pouze informace o hracich
     * @param loadFile - soubor s ulozenou hrou
     * @return true = vse uspesne nahrano
     * @throws Exception haze vyjimky s informacemi o chybach pri loadu
     */
    public boolean loadPlayers(File loadFile) throws Exception {
        Scanner bfr = new Scanner((loadFile));
        for (int i = 0; i < PlayBoardGUI.NUMOFSQUARES; i++) {
            bfr.nextLine();
        }
        String plr1Name = bfr.nextLine();
        String[] colors = bfr.nextLine().split("\\.");
        Color plr1Col = new Color(Integer.valueOf(colors[0]).intValue(),
                Integer.valueOf(colors[1]).intValue(),
                Integer.valueOf(colors[2]).intValue());

        String plr2Name = bfr.nextLine();
        colors = bfr.nextLine().split("\\.");
        Color plr2Col = new Color(Integer.valueOf(colors[0]).intValue(),
                Integer.valueOf(colors[1]).intValue(),
                Integer.valueOf(colors[2]).intValue());

        if (!plr1Name.equals(plr2Name)
                && !plr1Col.toString().equals(plr2Col.toString())
                && plr1Name.length() > 0
                && plr2Name.length() > 0) {
            //kdyz zatim neni zjistena chyba

            player1.setName(plr1Name);
            player2.setName(plr2Name);
            player1.setColor(plr1Col);
            player2.setColor(plr2Col);
        } else {
            throw new Exception();
        }
        return true;
    }

    /**
     *ulozi hru
     * @param file soubor, do ktereho se bude ukladat
     * @return true = uspesne ulozeno
     * @throws IOException chyba zapisu
     */
    public boolean saveGame(File file) throws IOException {

        //zapsani hraci plochy
        BufferedWriter bfw = new BufferedWriter(new FileWriter(file));
        for (int i = 0; i < boardArray.length; i++) {
            for (int j = 0; j < boardArray[0].length; j++) {
                bfw.write(String.valueOf(boardArray[i][j]));
            }
            bfw.newLine();
        }
        //zapsani jmen a barev hracu
        bfw.write(player1.getName());
        bfw.newLine();
        bfw.write(player1.getColor().getRed() + ".");
        bfw.write(player1.getColor().getGreen() + ".");
        bfw.write(player1.getColor().getBlue() + ".");
        bfw.newLine();
        bfw.write(player2.getName());
        bfw.newLine();
        bfw.write(player2.getColor().getRed() + ".");
        bfw.write(player2.getColor().getGreen() + ".");
        bfw.write(player2.getColor().getBlue() + ".");
        bfw.newLine();
        bfw.close();
        return true;
    }
}
