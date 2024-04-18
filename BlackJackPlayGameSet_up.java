/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BlackJack;

/**
 *
 * @author jojojojo
 */
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class BlackJackPlayGameSet_up extends JFrame{
    private Login login;

    protected static final Color WHITE = null;
    String title = "BLACK JACK";
    Font pont = new Font("Arial", Font.PLAIN, 25);
    String dealer = "Dealer's Hand";
    String player;
    String message = "";
    int bet;
    
    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        login.setVisible(!b);
    }

    public void run() {
        try {
            Card card = deck.remove(deck.size() - 1);
            dealerSum += card.getValue();
            dealerAceCount += card.isAce() ? 1 : 0; // is.Ace() return boolean
            dealerHand.add(card);
            Thread.sleep(400); // Pause the thread for 1000 milliseconds
        } catch (InterruptedException e) {
            System.err.println("Thread was interrupted!");
        }
    }

    private class Card {
        String value;
        String type;

        Card(String value, String type) {
            this.value = value; // type A-K
            this.type = type; // D(diamond), S(spade), H(heart), C(cloves)
        }

        public String toString() {
            return value + "-" + type; // for locating the img. // (example: value = 2, and type is H) output: 2-H
        } // method toString() == 2-H

        public int getValue() {
            if ("AJQK".contains(value)) { // A J Q K
                if (value == "A") {
                    return 11;
                }
                return 10;
            }
            return Integer.parseInt(value); // 2-10
        }

        public boolean isAce() {
            return value == "A";
        }// C:\Users\jojojojo\Documents\NetBeansProjects\GUIBlackJack\src\main\java\cards\2-C.png

        public String getImagePath() {
            return "./cards/" + toString() + ".png";
        }
    }

    ArrayList<Card> deck;
    Random random = new Random(); // shuffle deck

    int cardValue;
    // dealer
    Card hiddenCard;
    ArrayList<Card> dealerHand;
    int dealerSum;
    int dealerAceCount;

    // player
    ArrayList<Card> playerHand;
    int playerSum;
    int playerAceCount;

    // window
    int boardWidth = 700;
    int boardHeight = boardWidth;

    int cardWidth = 95; // ratio should 1/1.4
    int cardHeight = 139;

    JFrame frame = new JFrame("Black Jack");
    JPanel gamePanel = new JPanel() {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.setFont(new Font("Arial", Font.BOLD, 40));
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(title);
            int textHeight = fm.getHeight();

            // Calculate the position to center the text horizontally and vertically
            int x = (getWidth() - textWidth) / 2; // Adjust for width to center vertically
            int y = (getHeight() - textHeight) / 2 + fm.getAscent(); // Adjust for height to center vertically

            // Draw the text
            g.setColor(Color.white);
            g.drawString(title, x, 55);

            g.setFont(new Font("Arial", Font.PLAIN, 20));
            textWidth = fm.stringWidth(dealer);
            x = (getWidth() - (textWidth / 2)) / 2;
            g.setColor(Color.white);
            g.drawString(dealer, x, 270); // DEALER'S HAND TEXT

            g.setFont(new Font("Arial", Font.PLAIN, 20));
            textWidth = fm.stringWidth(player);
            x = (getWidth() - (textWidth / 2)) / 2;
            int x1 = (getWidth() - (fm.stringWidth(" Card Value: ") / 2) / 2) + x;
            g.setColor(Color.white);
            g.drawString(player, x, 430); // PLAYER'S HAND TEXT

            Font fnt = new Font("Arial", Font.PLAIN, 15);
            g.setFont(fnt);
            String cardValueString1 = " Card Value: " + Integer.toString(playerSum);
            g.drawString(cardValueString1, 50, 430);

            String cardValueString2 = " Card Value: " + Integer.toString(dealerSum);

            try {
                // draw hidden card
                Image hiddenCardImg = new ImageIcon(getClass().getResource("cards/BACK.png")).getImage();
                // hiddenCardImg = new
                // ImageIcon(this.getClass().getResource(hiddenCard.getImagePath())).getImage();
                if (!stayButton.isEnabled()) {
                    hiddenCardImg = new ImageIcon(getClass().getResource(hiddenCard.getImagePath())).getImage();
                    g.drawImage(hiddenCardImg, 50, 90, cardWidth, cardHeight, null);
                    g.drawString(cardValueString2, 50, 265);
                } else {
                    g.drawImage(hiddenCardImg, 50, 90, cardWidth, cardHeight, null);
                    hiddenCardImg = new ImageIcon(this.getClass().getResource(hiddenCard.getImagePath())).getImage();
                    g.drawString("Card Value: Hidden", 50, 265);

                    g.drawString("Bet: ".concat(Integer.toString(bet)), 315, 350);
                }

                // draw dealer's hand
                for (int i = 0; i < dealerHand.size(); i++) {
                    Card card = dealerHand.get(i);
                    Image cardImg = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
                    g.drawImage(cardImg, cardWidth + 55 + (cardWidth + 5) * i, 90, cardWidth, cardHeight, null);
                }

                // draw player's hand
                for (int i = 0; i < playerHand.size(); i++) {
                    Card card = playerHand.get(i);
                    Image cardImg = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
                    g.drawImage(cardImg, 50 + (cardWidth + 5) * i, 450, cardWidth, cardHeight, null);
                }

                if (!stayButton.isEnabled()) {
                    dealerSum = reduceDealerAce();
                    playerSum = reducePlayerAce();
                    System.out.println("STAY: ");
                    System.out.println(dealerSum);
                    System.out.println(playerSum);

                    if (playerSum > 21) {
                        message = "You Lose!";
                        playAgain.setEnabled(false);
                        hitButton.setEnabled(false);
                        playAgain.setEnabled(true);
                    } else if (dealerSum > 21) {
                        message = "You Win!";
                    }
                    // both you and dealer <= 21
                    else if (playerSum == dealerSum) {
                        message = "Tie!";
                    } else if (playerSum > dealerSum) {
                        message = "You Win!";
                    } else if (playerSum < dealerSum) {
                        message = "You Lose!";
                    } else if (playerSum == 0 && dealerSum == 0)
                        message = "";

                    g.setFont(new Font("Arial", Font.PLAIN, 30));
                    textWidth = fm.stringWidth(message);
                    x = (getWidth() - (textWidth / 2)) / 2;
                    y = (getHeight() - textHeight / 2) / 2 + fm.getAscent(); // Adjust for height to center vertically
                    g.setColor(Color.white);
                    g.drawString(message, x, y + 15); // PLAYER'S HAND TEXT

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    JPanel buttonPanel = new JPanel();
    JTextField betText = new JTextField(10);
    JButton hitButton = new JButton("Hit");
    JButton betButton = new JButton("Bet");
    JButton stayButton = new JButton("Stay");
    JButton playAgain = new JButton("PlayAgain");

    BlackJackPlayGameSet_up(Login login) {
        this.login = login;
        
        player = login.getUsername();
        
        startGame();

        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gamePanel.setLayout(new BorderLayout());
        gamePanel.setBackground(new Color(1, 50, 32));
        frame.add(gamePanel);

        hitButton.setFocusable(false);
        buttonPanel.add(hitButton);

        stayButton.setFocusable(false);
        buttonPanel.add(stayButton);

        betButton.setFocusable(false);
        buttonPanel.add(betButton);

        playAgain.setFocusable(false);
        playAgain.setEnabled(false);
        buttonPanel.add(playAgain);

        betText.setVisible(false);
        // betText.getHorizontalAlignment(JTextField.CENTER);
        gamePanel.add(betText);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        hitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                Card card = deck.remove(deck.size() - 1);
                if (card.value == "A" && playerSum > 10) {
                    cardValue = card.getValue() - 10;

                } else
                    cardValue = card.getValue();
                playerSum += cardValue;
                cardValue = 0;
                // playerAceCount += card.isAce() ? 1 : 0; // is.Ace() return boolean

                if (card.isAce()) {
                    playerAceCount++;
                }

                playerHand.add(card);

                // FontMetrics fm = g.getFontMetrics();

                // scoreText = ("Card Value = " + Integer.toString(cardValue));

                if (reducePlayerAce() > 21) { // A + 2 + J --> 1 + 2 + J
                    hitButton.setEnabled(false);
                }

                if (playerSum > 21) {
                    gamePanel.repaint();
                    hitButton.setEnabled(false);
                    stayButton.setEnabled(false);
                }

                gamePanel.repaint();
            }

        });

        stayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hitButton.setEnabled(false);
                stayButton.setEnabled(false);
                playAgain.setEnabled(true);
                betButton.setEnabled(true);

                while (dealerSum < 17) {
                    run();
                }
                gamePanel.repaint();
            }
        });

        betButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                betText.setVisible(true);
                hitButton.setEnabled(true);
                stayButton.setEnabled(true);
                playAgain.setEnabled(false);
                betButton.setEnabled(false);

                gamePanel.repaint();

            }
        });

        playAgain.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hitButton.setEnabled(true);
                stayButton.setEnabled(true);
                playAgain.setEnabled(false);

                startGame();
                gamePanel.repaint();

            }
        });

        gamePanel.repaint();
    }

    public void startGame() {
        // deck
        buildDeck();
        shuffleDeck();

        // dealer
        dealerHand = new ArrayList<Card>();
        dealerSum = 0;
        dealerAceCount = 0;
        cardValue = 0;

        hiddenCard = deck.remove(deck.size() - 1); // remove card at last index
        dealerSum += hiddenCard.getValue();
        dealerAceCount += hiddenCard.isAce() ? 1 : 0;

        Card card = deck.remove(deck.size() - 1);
        dealerSum += card.getValue();
        dealerAceCount += card.isAce() ? 1 : 0;
        dealerHand.add(card);

        System.out.println("DEALER:");
        System.out.println(hiddenCard);
        System.out.println(dealerHand);
        System.out.println(dealerSum);
        System.out.println(dealerAceCount);

        // player
        playerHand = new ArrayList<Card>();
        playerSum = 0;
        playerAceCount = 0;

        for (int i = 0; i < 2; i++) {
            card = deck.remove(deck.size() - 1);

            playerSum += card.getValue();
            playerAceCount += card.isAce() ? 1 : 0;
            playerHand.add(card);
        }

        if (playerAceCount == 2) {
            playerSum -= 20;
        }

        System.out.println("PLAYER: ");
        System.out.println(playerHand);
        System.out.println(playerSum);
        System.out.println(playerAceCount);
    }

    public void buildDeck() {
        deck = new ArrayList<Card>();
        String[] values = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" };
        String[] types = { "C", "D", "H", "S" };

        for (int i = 0; i < types.length; i++) {
            for (int j = 0; j < values.length; j++) {
                Card card = new Card(values[j], types[i]);
                deck.add(card);
            }
        }

        System.out.println("BUILD DECK:");
        System.out.println(deck);
    }

    public void shuffleDeck() {
        for (int i = 0; i < deck.size(); i++) {
            int j = random.nextInt(deck.size());
            Card currCard = deck.get(i);
            Card randomCard = deck.get(j);
            deck.set(i, randomCard);
            deck.set(j, currCard);
        }

        System.out.println("AFTER SHUFFLE");
        System.out.println(deck);
    }

    public int reducePlayerAce() {
        while (playerSum > 21 && playerAceCount > 0) {
            playerSum -= 10;
            playerAceCount -= 1;
        }
        return playerSum;
    }

    public int reduceDealerAce() {
        while (dealerSum > 21 && dealerAceCount > 0) {
            dealerSum -= 10;
            dealerAceCount -= 1;
        }
        return dealerSum;
    }
}
