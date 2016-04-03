/*4
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.novakvo5;

import java.awt.*;
import java.awt.Graphics;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;

/**
 *
 * @author vonovak
 */
public class PlayBoardGUI extends Canvas {

    /**
     * pocet policek na stranu hraci plochy
     */
    public static final int NUMOFSQUARES = 20;
    /**
     * velikost hraciho policka v pixelech
     */
    public static final int PXSIZEOFSQUARE = 31;
    private PlayBoard playBoard;
    private boolean playerOneOnTurn;
    private Player player1;
    private Player player2;
    private JLabel plrOnTurnJLabel;

    /**
     * konstruktor hraci plochy
     * @param playerOnTurn JLabel, ktery ukazuje, ktery z hracu je na rade
     * @param playBoard instance reprezentujici hraci pole jako datovy typ
     * @param player1 instance prvniho hrace
     * @param player2 instance druheho hrace
     */
    public PlayBoardGUI(JLabel playerOnTurn, PlayBoard playBoard, Player player1, Player player2) {
        this.playBoard = playBoard;
        playerOneOnTurn = true;
        this.addMouseListener(new BoardClickListener());
        this.setEnabled(false);
        this.plrOnTurnJLabel = playerOnTurn;
        this.player1 = player1;
        this.player2 = player2;
    }

    @Override
    public String toString() {
        return "PlayBoardGUI{" + "playBoard=" + playBoard.toString() + "\nplayer1=" + player1.toString() + "\nplayer2=" + player2.toString() + '}';
    }

    /**
     * povoluje/zakazuje hraci plochu
     * @param value true = zapne se hraci plocha (reaguje na kliknuti)
     * false=hraci plocha nereaguje
     */
    public void enableBoard(boolean value) {
        this.setEnabled(value);
    }

    /**
     *zjisti, ktery hrac je na rade a podle toho nastavi vykreslovanou znacku a Label ukazujici jmeno hrace
     */
    public void determinePlayerOnTurn() {
        if (playBoard.playerOnTurn() == 2) {
            plrOnTurnJLabel.setText(player2.getName());
            playerOneOnTurn = false;
        } else {
            plrOnTurnJLabel.setText(player1.getName());
            playerOneOnTurn = true;
        }
    }

    class NewGame extends JDialog implements ActionListener {

        private JTextField plr1TextField;
        private JTextField plr2TextField;
        private JColorChooser plr1cc;
        private JColorChooser plr2cc;

        //konstruktor dialogu new game
        public NewGame() {
            super(Piskvorky.getFrames()[0], "new game", true);
            setLocation(Piskvorky.getFrames()[0].getLocation().x,
                    Piskvorky.getFrames()[0].getLocation().y + 200);
            plr1TextField = new JTextField(10);
            plr1TextField.setText("player1");
            JLabel plr1Label = new JLabel("Player's name");
            plr1cc = new JColorChooser();
            plr1cc.addMouseListener(null);
            plr1cc.setPreviewPanel(new JPanel());
            AbstractColorChooserPanel[] cc1Panels = plr1cc.getChooserPanels();
            plr1cc.removeChooserPanel(cc1Panels[2]);
            plr1cc.removeChooserPanel(cc1Panels[1]);
            //end of plr1 settings
            plr2TextField = new JTextField(10);
            plr2TextField.setText("player2");
            JLabel plr2Label = new JLabel("Player's name");
            plr2cc = new JColorChooser();
            plr2cc.addMouseListener(null);
            plr2cc.setPreviewPanel(new JPanel());
            AbstractColorChooserPanel[] cc2Panels = plr2cc.getChooserPanels();
            plr2cc.removeChooserPanel(cc2Panels[2]);
            plr2cc.removeChooserPanel(cc2Panels[1]);
            //end of plr2 settings


            //panel pro prvniho hrace
            JPanel plr1Panel = new JPanel(new FlowLayout());
            plr1Panel.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createEtchedBorder(), "Player one settings"));
            plr1Panel.add(plr1Label);
            plr1Panel.add(plr1TextField);
            plr1Panel.add(plr1cc);
            this.add(plr1Panel, BorderLayout.NORTH);

            //panel pro druheho hrace
            JPanel plr2Panel = new JPanel(new FlowLayout());
            plr2Panel.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createEtchedBorder(), "Player two settings"));
            plr2Panel.add(plr2Label);
            plr2Panel.add(plr2TextField);
            plr2Panel.add(plr2cc);
            this.add(plr2Panel, BorderLayout.CENTER);

            JButton confirm = new JButton("Play!");
            JPanel confButtonPanel = new JPanel();
            confButtonPanel.add(confirm);
            confirm.addActionListener(this);


            this.add(confButtonPanel, BorderLayout.SOUTH);
            pack();
            this.setResizable(false);
            setVisible(true);
        }

        public void actionPerformed(ActionEvent e) {
            Color c1 = null;
            Color c2 = null;
            String plr1Name, plr2Name;
            if (!plr1TextField.getText().isEmpty()
                    && !plr2TextField.getText().isEmpty()
                    && !plr1TextField.getText().equals(plr2TextField.getText())
                    && plr1TextField.getText().length() < 20
                    && plr2TextField.getText().length() < 20) {
                //pokud jsou jmena platna
                plr1Name = plr1TextField.getText();
                plr2Name = plr2TextField.getText();

                c1 = plr1cc.getColor();
                c2 = plr2cc.getColor();
                //kontrola barev
                if (c1 != c2 && c1 != null && c2 != null) {
                    if (Math.abs(c1.getRed() - c2.getRed()) < 70
                            && Math.abs(c1.getBlue() - c2.getBlue()) < 70
                            && Math.abs(c1.getGreen() - c2.getGreen()) < 70) {
                        //pokud jsou si barvy prilis podobne
                        JOptionPane.showMessageDialog(this, "Please enter colors that differ more than the selected ones",
                                "Wrong colors", JOptionPane.NO_OPTION);
                    } else if (Math.abs(c1.getRed() - Color.lightGray.getRed()) < 50
                            && Math.abs(c1.getGreen() - Color.lightGray.getGreen()) < 50
                            && c1.getBlue() - Color.lightGray.getBlue() < 50
                            || Math.abs(c2.getRed() - Color.lightGray.getRed()) < 50
                            && Math.abs(c2.getGreen() - Color.lightGray.getGreen()) < 50
                            && Math.abs(c2.getBlue() - Color.lightGray.getBlue()) < 50) {
                        //pokud jsou barvy podobne pozadi
                        JOptionPane.showMessageDialog(this, "Please enter colors that differ from the background color",
                                "Wrong selection", JOptionPane.NO_OPTION);
                    } else {
                        //muzu nastavit hrace a vynulovat hraci plochu
                        player1.setName(plr1Name);
                        player1.setColor(c1);
                        player2.setName(plr2Name);
                        player2.setColor(c2);

                        playBoard.cleanPlayBoard();

                        //zobrazit, povolit klikani na plochu a zobrazit jmeno hrace ktery je na rade
                        setVisible(false);
                        enableBoard(true);
                        plrOnTurnJLabel.setText(plr1Name);
                        playerOneOnTurn = true;
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Don't forget about the colors!",
                            "Pick a color!", JOptionPane.NO_OPTION);
                }
            } else if (plr1TextField.getText().length() > 19 || plr2TextField.getText().length() > 19) {
                JOptionPane.showMessageDialog(this, "Name has to be up to 19 chars long.",
                        "Wrong input", JOptionPane.NO_OPTION);
            } else {
                JOptionPane.showMessageDialog(this, "Please enter valid names.",
                        "Wrong input", JOptionPane.NO_OPTION);
            }
        }
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    @Override
    public void paint(Graphics g) {
        //vykresluje svetle sede pozadi hraciho platna
        g.setColor(Color.lightGray);
        g.fillRect(0, 0, NUMOFSQUARES * PXSIZEOFSQUARE, NUMOFSQUARES * PXSIZEOFSQUARE);
        g.setColor(Color.BLACK);
        for (int i = 0; i < NUMOFSQUARES * PXSIZEOFSQUARE; i += PXSIZEOFSQUARE) {
            for (int j = 0; j < NUMOFSQUARES * PXSIZEOFSQUARE; j += PXSIZEOFSQUARE) {
                //vykresluje cernou mrizku
                g.drawLine(i, 0, i, NUMOFSQUARES * PXSIZEOFSQUARE);
                g.drawLine(0, j, NUMOFSQUARES * PXSIZEOFSQUARE, j);
                //kdyz v poli reprezentujicim hraci plochu najde hodnotu 1 nebo 2,
                //vykresli znacku v odpovidajici barve na odpovidajicich souradnicich
                if (playBoard.getFieldValue(i / PXSIZEOFSQUARE, j / PXSIZEOFSQUARE) == 1) {
                    g.setColor(player1.getColor());
                    g.fillOval(i + 3, j + 3, 26, 26);
                    g.setColor(Color.lightGray);
                    g.fillOval(i + 10, j + 10, 12, 12);
                    g.setColor(Color.BLACK);
                } else if (playBoard.getFieldValue(i / PXSIZEOFSQUARE, j / PXSIZEOFSQUARE) == 2) {
                    g.setColor(player2.getColor());

                    g.drawLine(i + 5, j + 5, i + 26, j + 26);//zleva doprava
                    g.drawLine(i + 6, j + 5, i + 26, j + 25);
                    g.drawLine(i + 7, j + 5, i + 26, j + 24);

                    g.drawLine(i + 5, j + 6, i + 25, j + 26);
                    g.drawLine(i + 5, j + 7, i + 24, j + 26);

                    g.drawLine(i + 5, j + 26, i + 26, j + 5);//zleva doprava
                    g.drawLine(i + 6, j + 26, i + 26, j + 6);
                    g.drawLine(i + 7, j + 26, i + 26, j + 7);

                    g.drawLine(i + 5, j + 25, i + 25, j + 5);
                    g.drawLine(i + 5, j + 24, i + 24, j + 5);
                    g.setColor(Color.BLACK);
                }
            }
        }

        //vykresli uplne pravou a uplne dolni caru mrizky
        g.drawLine(0, NUMOFSQUARES * PXSIZEOFSQUARE, NUMOFSQUARES * PXSIZEOFSQUARE, NUMOFSQUARES * PXSIZEOFSQUARE);
        g.drawLine(NUMOFSQUARES * PXSIZEOFSQUARE, 0, NUMOFSQUARES * PXSIZEOFSQUARE, NUMOFSQUARES * PXSIZEOFSQUARE);
    }

    class BoardClickListener extends MouseAdapter {

        private int mousePressedX, mousePressedY;
        private int mouseReleasedX, mouseReleasedY;
        private boolean clickedOnBound = false;

        @Override
        public void mousePressed(MouseEvent e) {
            Graphics gr = PlayBoardGUI.this.getGraphics();
            //Graphics gr = getGraphics();
            gr.setColor(Color.blue);
            mousePressedX = e.getX();
            mousePressedY = e.getY();
            //nastavi se na true, kdyz uzivatel kliknul na mrizku a tim znemozni vykresleni znacky
            clickedOnBound = false;
            for (int i = 0; i < NUMOFSQUARES + 1; i++) {
                if (mousePressedX == PXSIZEOFSQUARE * i) {
                    clickedOnBound = true;
                    break;
                } else if (mousePressedX < PXSIZEOFSQUARE * i) {
                    mousePressedX = PXSIZEOFSQUARE * (i - 1);

                    break;
                }
            }
            for (int i = 0; i < NUMOFSQUARES + 1; i++) {
                if (mousePressedY == PXSIZEOFSQUARE * i) {
                    clickedOnBound = true;
                    break;
                } else if (mousePressedY < PXSIZEOFSQUARE * i) {
                    mousePressedY = PXSIZEOFSQUARE * (i - 1);
                    break;
                }
            }
            //System.out.println("detekovan press na " + mousePressedX + " " + mousePressedY);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            Graphics gr = PlayBoardGUI.this.getGraphics();
            mouseReleasedX = e.getX();
            mouseReleasedY = e.getY();


            //povoluje vykresleni znacky
            //tato promenna je false, kdyz se klikne na mrizku
            //nebo kdyz policko na kterem byl detekovan mousePressed je jine nez to
            //na kterem byl detekovan mouseReleased

            if (!clickedOnBound) {
                for (int i = 0; i < NUMOFSQUARES + 1; i++) {
                    if (mouseReleasedX == PXSIZEOFSQUARE * i) {
                        clickedOnBound = true;
                        break;
                    } else if (mouseReleasedX < PXSIZEOFSQUARE * i) {
                        mouseReleasedX = PXSIZEOFSQUARE * (i - 1);
                        break;
                    }
                }
                for (int i = 0; i < NUMOFSQUARES + 1; i++) {
                    if (mouseReleasedY == PXSIZEOFSQUARE * i) {
                        clickedOnBound = true;
                        break;
                    } else if (mouseReleasedY < PXSIZEOFSQUARE * i) {
                        mouseReleasedY = PXSIZEOFSQUARE * (i - 1);
                        break;
                    }
                }
                //System.out.println("detekovan release na " + mouseReleasedX + " " + mouseReleasedY);
            }

            //pokud se nerovnaji souradnice policek u mouseRelease a mousePressed nebo kdyz se kliklo na
            //hranici, znemozni se kresleni
            if (!clickedOnBound && mouseReleasedX == mousePressedX && mouseReleasedY == mousePressedY) {
                //pokud je press a release stejny a nekliklo se na mrizku
                if (playBoard.fieldIsFree(mousePressedX / PXSIZEOFSQUARE, mousePressedY / PXSIZEOFSQUARE)) {
                    //pokud je pozice volna, tah vykreslim a zanesu do playBoard
                    paintMove(mousePressedX, mousePressedY, gr);
                    performMoveInPlayboard(mousePressedY, mousePressedX);
                }
            }
        }
    }

    private void paintMove(int mousePressedX, int mousePressedY, Graphics gr) {
        //System.out.println(mousePressedY / PXSIZEOFSQUARE +", "+ mousePressedX / PXSIZEOFSQUARE);
        if (playerOneOnTurn) {
            //kdyz je na rade prvni hrac
            gr.setColor(player1.getColor());
            gr.fillOval(mousePressedX + 3, mousePressedY + 3, 26, 26);
            gr.setColor(Color.lightGray);
            gr.fillOval(mousePressedX + 10, mousePressedY + 10, 12, 12);
            plrOnTurnJLabel.setText(player2.getName());
        } else {
            //kdyz je na rade druhy hrac
            gr.setColor(player2.getColor());

            gr.drawLine(mousePressedX + 5, mousePressedY + 5, mousePressedX + 26, mousePressedY + 26);
            gr.drawLine(mousePressedX + 6, mousePressedY + 5, mousePressedX + 26, mousePressedY + 25);
            gr.drawLine(mousePressedX + 7, mousePressedY + 5, mousePressedX + 26, mousePressedY + 24);

            gr.drawLine(mousePressedX + 5, mousePressedY + 6, mousePressedX + 25, mousePressedY + 26);
            gr.drawLine(mousePressedX + 5, mousePressedY + 7, mousePressedX + 24, mousePressedY + 26);

            gr.drawLine(mousePressedX + 5, mousePressedY + 26, mousePressedX + 26, mousePressedY + 5);
            gr.drawLine(mousePressedX + 6, mousePressedY + 26, mousePressedX + 26, mousePressedY + 6);
            gr.drawLine(mousePressedX + 7, mousePressedY + 26, mousePressedX + 26, mousePressedY + 7);

            gr.drawLine(mousePressedX + 5, mousePressedY + 25, mousePressedX + 25, mousePressedY + 5);
            gr.drawLine(mousePressedX + 5, mousePressedY + 24, mousePressedX + 24, mousePressedY + 5);
            plrOnTurnJLabel.setText(player1.getName());
        }
    }

    private void performMoveInPlayboard(int mousePressedY, int mousePressedX) {
        if (playerOneOnTurn) {
            playBoard.setFieldValue(mousePressedX / PXSIZEOFSQUARE, mousePressedY / PXSIZEOFSQUARE, 1);
        } else {
            playBoard.setFieldValue(mousePressedX / PXSIZEOFSQUARE, mousePressedY / PXSIZEOFSQUARE, 2);
        }
        int numOfWinner = playBoard.checkForWinner(mousePressedY / PXSIZEOFSQUARE, mousePressedX / PXSIZEOFSQUARE);

        if (playBoard.isFull() && numOfWinner == 0) {
            //kdyz je hraci plocha plna a nikdo nevyhral
            JOptionPane.showMessageDialog(Piskvorky.getFrames()[0], "This must have been a tough game!\n"
                    + "nobody has won!",
                    "End of game!", JOptionPane.NO_OPTION);
            saveDialog(true);
            enableBoard(false);
        }
        if (numOfWinner != 0) {
            //kdyz nekdo vyhral
            plrOnTurnJLabel.setText("");
            String nameOfWinner;
            if (numOfWinner == 1) {
                nameOfWinner = player1.getName();
            } else {
                nameOfWinner = player2.getName();
            }

            JOptionPane.showMessageDialog(Piskvorky.getFrames()[0], nameOfWinner + " has won!",
                    "End of game!", JOptionPane.NO_OPTION);
            saveDialog(true);
            enableBoard(false);
        }

        //prepinam hrace
        playerOneOnTurn = !playerOneOnTurn;
    }

    /**
     * zobrazuje dialog pro ukladani hry
     * @param displayDialog true=zobrazi dialog, kde se zepta uzivatele, zda chce hru ulozit,
     * false=rovnou se zobrazi okno s vyberem souboru pro ulozeni
     */
    public void saveDialog(boolean displayDialog) {
        if (displayDialog) {
            int userChoice = JOptionPane.showConfirmDialog(Piskvorky.getFrames()[0],
                    "Do you want to save your game?", "Save game?", JOptionPane.YES_NO_OPTION);
            if (userChoice != JOptionPane.YES_OPTION) {
                return;
            }
        }

        JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
        fc.addChoosableFileFilter(new GameFileFilter());
        boolean displayDialogAgain = false;
        boolean gameSavingSuccessful = false;

        do {
            displayDialogAgain = false;
            toEndOfFileChoosing:
            {
                int usrChoice = fc.showSaveDialog(Piskvorky.getFrames()[0]);
                if (usrChoice == JFileChooser.APPROVE_OPTION) {
                    try {
                        //zpracovani koncovky
                        String originalName = fc.getSelectedFile().getName();
                        if (originalName.length() > 40) {
                            JOptionPane.showMessageDialog(Piskvorky.getFrames()[0], "Name of file has to be up to 40 characters long!",
                                    "Wrong input", JOptionPane.NO_OPTION);
                            displayDialogAgain = true;
                            break toEndOfFileChoosing;
                        }
                        String absolutePath = fc.getSelectedFile().getAbsolutePath();
                        String saveDirectoryPath =
                                absolutePath.substring(0, absolutePath.lastIndexOf(File.separator) + 1);
                        String newFileName = originalName;
                        if (GameFileFilter.getExtension(fc.getSelectedFile()) == null
                                || !GameFileFilter.getExtension(fc.getSelectedFile()).equals("txt")) {
                            int i = 0;
                            i = originalName.lastIndexOf('.');
                            if (i > 0) {
                                newFileName = originalName.substring(0, i) + ".txt";
                            } else {
                                newFileName = originalName + ".txt";
                            }
                        }

                        //kontrola, zda file uz neexsituje
                        File file = new File(saveDirectoryPath + newFileName);
                        if (file != null && file.exists()) {
                            int response = JOptionPane.showConfirmDialog(Piskvorky.getFrames()[0],
                                    "The file " + newFileName + " already exists. Do you "
                                    + "want to replace the existing file?", "Overwrite?",
                                    JOptionPane.YES_NO_OPTION);
                            if (response == JOptionPane.CLOSED_OPTION) {
                                break toEndOfFileChoosing;
                            } else if (response == JOptionPane.NO_OPTION) {
                                displayDialogAgain = true;
                                break toEndOfFileChoosing;
                            }
                        }

                        //zapsani hraci plochy
                        if (playBoard.saveGame(file)) {
                            gameSavingSuccessful = true;
                        }

                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(Piskvorky.getFrames()[0], ex.getLocalizedMessage(),
                                "unable to write the file", JOptionPane.ERROR_MESSAGE);
                    }
                }
                //end of if statement
            }//endoffilechoosing
        } while (displayDialogAgain);
        if (gameSavingSuccessful) {
            JOptionPane.showMessageDialog(Piskvorky.getFrames()[0], "Game has been saved!",
                    "Saved successfully!", JOptionPane.NO_OPTION);
        }

    }

    /**
     *zobrazuje dialog pro nacteni hry
     * @return true=hra byla uspesne nahrana, false=hra nahrana nebyla (storno uzivatele, chyba)
     */
    public boolean loadDialog() {
        JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
        fc.addChoosableFileFilter(new GameFileFilter());
        boolean repeatInput = false;

        do {
            repeatInput = false;
            int usrChoice = fc.showOpenDialog(Piskvorky.getFrames()[0]);
            newInputDialog:
            {
                if (usrChoice == JFileChooser.APPROVE_OPTION) {
                    //vlastni load
                    String loadAbsolutePath = fc.getSelectedFile().getAbsolutePath();
                    File loadFile = new File(loadAbsolutePath);

                    if (loadFile.exists() && GameFileFilter.getExtension(loadFile).equals("txt")
                            && loadFile.length() < 71 + PlayBoardGUI.NUMOFSQUARES * 2 + PlayBoardGUI.NUMOFSQUARES * PlayBoardGUI.NUMOFSQUARES
                            && loadFile.length() > 20) {
                        //kdyz souhlasi delka a pripona

                        try {
                            //load hraci plochy
                            if (!playBoard.loadPlayBoardArray(loadFile)) {
                                repeatInput = true;
                            }
                        } catch (Exception nfe) {
                            JOptionPane.showMessageDialog(Piskvorky.getFrames()[0],
                                    "Unable to load game\n" + nfe.getMessage(),
                                    "Wrong input", JOptionPane.ERROR_MESSAGE);
                            repeatInput = true;
                            break newInputDialog;
                        }
                        
                        try {
                            //load jmen a barev hracu
                            if (!playBoard.loadPlayers(loadFile)) {
                                repeatInput = true;
                                break newInputDialog;
                            }
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(Piskvorky.getFrames()[0],
                                    "Unable to load game\nUnable to load players",
                                    "Wrong input", JOptionPane.ERROR_MESSAGE);
                            repeatInput = true;
                            break newInputDialog;
                        }

                    } else {
                        JOptionPane.showMessageDialog(Piskvorky.getFrames()[0],
                                "This is not the game file",
                                "Wrong input", JOptionPane.ERROR_MESSAGE);
                        repeatInput = true;
                    }
                } else {
                    return false;
                }
            }
            //end of APPROVE_OPTION
        } while (repeatInput);
        return true;
    }
}
