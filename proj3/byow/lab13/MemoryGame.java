package byow.lab13;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        //TODO: Initialize random number generator
        rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        String str = "";
        int len = CHARACTERS.length;
        for (int i = 0; i < n; i++) {
            int index = rand.nextInt(len);
            str+=CHARACTERS[index];
        }
        return str;
    }

    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen
        StdDraw.clear(Color.BLACK);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(width / 2.0, height / 2.0, s);

        // Display relevant game information.
        font = new Font("Monaco", Font.BOLD, 15);
        StdDraw.setFont(font);
        StdDraw.line(0, height - 2, width, height - 2);
        StdDraw.textLeft(1, height - 1, "Round: " + round);
        if (!playerTurn) {
            StdDraw.text(width / 2.0, height - 1, "Watch!");
        } else {
            StdDraw.text(width / 2.0, height - 1, "Type!");
        }
        StdDraw.textRight(width - 1, height - 1, ENCOURAGEMENT[rand.nextInt(ENCOURAGEMENT.length)]);
        StdDraw.show();
        StdDraw.show();
    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        for (int i = 0; i < letters.length(); i++) {
            String s = Character.toString(letters.charAt(i));
            drawFrame(s);
            StdDraw.pause(1000);
            drawFrame("");
            StdDraw.pause(500);
        }
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        StringBuilder strBuilder = new StringBuilder();
        int count = 0;
        while (count < n) {
            if (StdDraw.hasNextKeyTyped()) {
                strBuilder.append(StdDraw.nextKeyTyped());
                count += 1;
                drawFrame(strBuilder.toString());
            }
        }
        StdDraw.pause(1000); // Keep player's answer staying on screen for 1 second.
        return strBuilder.toString();
    }

    public void startGame() {
        //DONE: Set any relevant variables before the game starts
        round = 1;
        gameOver = false;
        String question;
        String answer;

        //DONE: Establish Engine loop
        while (!gameOver) {
            playerTurn = false;
            drawFrame("Round: " + round);
            StdDraw.pause(2000);
            question = generateRandomString(round);
            flashSequence(question);

            playerTurn = true;
            drawFrame(""); // Change "Watch!" to "Type!".
            answer = solicitNCharsInput(round);
            if (answer.equals(question)) {
                round += 1;
            } else {
                drawFrame("Game Over! You made it to round: " + round);
                gameOver = true;
            }
        }
    }

}
