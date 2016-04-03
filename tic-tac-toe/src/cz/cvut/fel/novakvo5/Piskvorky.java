package cz.cvut.fel.novakvo5;

import cz.cvut.fel.novakvo5.PlayBoardGUI.NewGame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author vonovak
 */
class FramePanel extends JPanel {

    private final static int BORDER = 10;
    Dimension d;

    //vytvari okraje hraci plochy
    @Override
    public Insets getInsets() {
        d = getSize();
        int tempWidth = BORDER;
        int tempHeight = BORDER;

        if (d.width > PlayBoardGUI.NUMOFSQUARES * PlayBoardGUI.PXSIZEOFSQUARE + 2 + 2 * BORDER) {
            tempWidth = d.width - (PlayBoardGUI.NUMOFSQUARES * PlayBoardGUI.PXSIZEOFSQUARE + 1 + BORDER);
        }
        if (d.height > PlayBoardGUI.NUMOFSQUARES * PlayBoardGUI.PXSIZEOFSQUARE + 2 + 2 * BORDER) {
            tempHeight = d.height - (PlayBoardGUI.NUMOFSQUARES * PlayBoardGUI.PXSIZEOFSQUARE + 1 + BORDER);
        }
        return new Insets(BORDER, BORDER, tempHeight, tempWidth);
    }
}

/**
 *
 * @author vonovak
 */
public class Piskvorky extends JFrame {

    PlayBoardGUI playBoardGUI;
    PlayBoard playBoard;
    Player player1;
    Player player2;
    JButton saveButton;
    JButton loadButton;
    JButton newGame;
    JButton aboutGame;
    JLabel plrOnTurnName;

    /**
     * konstruktor - vytvari okno
     */
    public Piskvorky() {
        super.setTitle("Piškvorky");
        plrOnTurnName = new JLabel();
        player1 = new Player("DEFAULT", Color.lightGray);
        player2 = new Player("DEFAULT", Color.lightGray);
        playBoard = new PlayBoard(PlayBoardGUI.NUMOFSQUARES, player1, player2);
        playBoardGUI = new PlayBoardGUI(plrOnTurnName, playBoard, player1, player2);


        FramePanel background = new FramePanel();
        background.setLayout(new BorderLayout());
        background.add(playBoardGUI);
        this.add(background, BorderLayout.CENTER);

        saveButton = new JButton("Save Game");
        loadButton = new JButton("Load Game");
        newGame = new JButton("New Game");
        aboutGame = new JButton("About");
        saveButton.addActionListener(new GameButtonsListener());
        loadButton.addActionListener(new GameButtonsListener());
        aboutGame.addActionListener(new CreditsButtonListener());
        newGame.addActionListener(new NewGameButtonListener());

        //novy panel pro tlacitka
        JPanel forGameButtons = new JPanel();
        forGameButtons.add(newGame);
        forGameButtons.add(loadButton);
        forGameButtons.add(saveButton);
        forGameButtons.add(aboutGame);
        this.add(forGameButtons, BorderLayout.SOUTH);

        //panel pro info o hraci, ktery je na rade
        JPanel forPlayerInfo = new JPanel();
        final JLabel onTurn = new JLabel("Player: ");

        forPlayerInfo.add(onTurn);
        forPlayerInfo.add(plrOnTurnName);

        this.add(forPlayerInfo, BorderLayout.NORTH);
        this.add(forGameButtons, BorderLayout.SOUTH);
        this.setSize(648, 745);
        this.setIconImage(null);
        this.setResizable(false);

        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    class GameButtonsListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == saveButton) {
                //volani ukladani funkce
                if (!player1.getName().equals(player2.getName())) {
                    playBoardGUI.saveDialog(false);
                } else {
                    JOptionPane.showMessageDialog(Piskvorky.getFrames()[0], "Please play a game first",
                            "nothing to save!", JOptionPane.NO_OPTION);
                }
            } else if (e.getSource() == loadButton) {
                //volani loadu hry
                boolean loaded = playBoardGUI.loadDialog();
                if (loaded) {
                    playBoardGUI.update(playBoardGUI.getGraphics());
                    playBoardGUI.enableBoard(true);
                    playBoardGUI.determinePlayerOnTurn();
                }
            }
        }
    }

    class NewGameButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            NewGame newGame1 = playBoardGUI.new NewGame();
            playBoardGUI.update(playBoardGUI.getGraphics());

        }
    }

    class CreditsButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(Piskvorky.getFrames()[0], "Piškvorky: semestrální práce PR1\nVojtěch Novák, OI FEL",
                    "about Piškvorky", JOptionPane.NO_OPTION);
        }
    }

    /**
     * metoda main - vytvari instanci piskvorek a zobrazuje ji
     * @param args
     */
    public static void main(String[] args) {
        new Piskvorky().setVisible(true);
    }
}
