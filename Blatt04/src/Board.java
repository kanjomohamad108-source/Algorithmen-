import java.util.InputMismatchException;
import java.util.Stack;

import static java.lang.Math.abs;
/**
 * This class represents a generic TicTacToe game board.
 */
public class Board {
    private int n;
    //TODO
    private int[][] board;
    private int freeFields;


    /**
     * Creates Board object, am game board of size n * n with 1<=n<=10.
     */
    public Board(int n) {
        // TODO
        // Wenn die Zahl für das Spielfeld kleiner als 1 oder größer als 10 ist ...
        if (n < 1 || n > 10) {
            // ... dann sagen wir: Das geht nicht! Und werfen eine Fehlermeldung.
            throw new InputMismatchException("n must be between 1 and 10");
        }
// Wir merken uns, wie groß das Spielfeld sein soll (3 für 3x3)
        this.n = n;
// Wir machen ein Spielfeld mit n Zeilen und n Spalten (also Felder)
        this.board = new int[n][n];
// Am Anfang sind alle Felder frei, das sind Felder
        this.freeFields = n * n;
    }

    /**
     * @return length/width of the Board object
     */
    public int getN() {
        // TODO
        return n;
    }

    /**
     * @return number of currently free fields
     */
    public int nFreeFields() {
        // TODO
        return freeFields;
    }

    /**
     * @return token at position pos
     */
    public int getField(Position pos) throws InputMismatchException {
        // TODO
        int x = pos.x;
        int y = pos.y;
// Wir prüfen, ob die x- oder y-Position außerhalb des Spielfelds liegt
        if (x < 0 || x >= n || y < 0 || y >= n) {
            // Wenn ja, dann sagen wir: "Feld gibt es nicht!" und geben einen Fehler zurück
            throw new InputMismatchException("Position out of bounds");
        }

        return board[y][x];
    }

    /**
     * Sets the specified token at Position pos.
     */
    public void setField(Position pos, int token) throws InputMismatchException {
        // TODO
        int x = pos.x;
        int y = pos.y;
        if (x < 0 || x >= n || y < 0 || y >= n) {
            throw new InputMismatchException("Position out of bounds");
        }
        if (token != 0 && token != 1 && token != -1) {
            throw new InputMismatchException("Invalid token");
        }

        // Wir setzen das Zeichen (x, o oder leer) auf das Spielfeld
        board[y][x] = token;

        // Jetzt zählen wir neu, wie viele Felder noch frei sind
        freeFields = 0;
        for (int i = 0; i < n; i++) { // für jede Zeile
            for (int j = 0; j < n; j++) { // für jede Spalte
                if (board[i][j] == 0) { // wenn das Feld leer ist
                    freeFields++; // eins mehr frei
                }
            }
        }
    }

    /**
     * Places the token of a player at Position pos.
     */
    public void doMove(Position pos, int player) {
        // TODO
        // Wenn das Feld nicht leer ist (also da ist schon ein x oder o)
        if (getField(pos) != 0) {
            // Dann sagen wir: "Da darfst du nicht setzen!"
            throw new IllegalArgumentException("Position already occupied");
        }
// Wenn der Spieler nicht 1 oder -1 ist (also kein x oder o)
        if (player != 1 && player != -1) {
            // Dann sagen wir: "Ungültiger Spieler!"
            throw new IllegalArgumentException("Player must be 1 or -1");
        }
        setField(pos, player); // freeFields wird dann angepasst

    }

    /**
     * Clears board at Position pos.
     */
    public void undoMove(Position pos) {
        // TODO
        setField(pos, 0); // setzt das Feld wieder leer (und aktualisiert freeFields)

    }

    /**
     * @return true if game is won, false if not
     */
    public boolean isGameWon() {
        // TODO
        for (int i = 0; i < n; i++) {
            if (checkLine(0, i, 1, 0)) return true; // Spalte i
            if (checkLine(i, 0, 0, 1)) return true; // Zeile i

        }
        if (checkLine(0, 0, 1, 1)) return true; // Prüfe Hauptdiagonale (oben links nach unten rechts)
        if (checkLine(n - 1, 0, -1, 1)) return true; // Prüfe Gegendiagonale (oben rechts nach unten links)

        return false; // Wenn keine Linie gefunden → niemand hat gewonnen
    }
    private boolean checkLine(int startX, int startY, int dx, int dy) {
        int x = startX;
        int y = startY;
        int first = board[y][x]; // Erstes Feld in der Linie
        if (first == 0) return false; // Wenn das erste Feld leer ist → kein Gewinn

        for (int i = 1; i < n; i++) {
            x = startX + i * dx;
            y = startY + i * dy;
            if (x < 0 || x >= n || y < 0 || y >= n) return false; // außerhalb des Feldes?
            if (board[y][x] != first) return false; // anderes Zeichen? → kein Gewinn
        }
        return true;
    }

    /**
     * @return set of all free fields as some Iterable object
     */
    public Iterable<Position> validMoves() {
        // TODO
        Stack<Position> moves = new Stack<>(); // Wir machen einen leeren Stapel für die freien Felder
        for (int i = 0; i < n; i++) {           // Wir gehen jede Zeile durch
            for (int j = 0; j < n; j++) {       // Wir gehen jede Spalte durch
                if (getField(new Position(i, j)) == 0) { // Wenn das Feld leer ist (also 0)
                    moves.push(new Position(i, j));      // Dann merken wir uns diese Stelle
                }
            }
        }
        return moves;
    }

    /**
     * Outputs current state representation of the Board object.
     * Practical for debugging.
     */
    public void print() {
        // TODO
        // Wir gehen durch jede Zeile des Spielfelds
        for (int i = 0; i < n; i++) {
            // Wir gehen durch jede Spalte in der aktuellen Zeile
            for (int j = 0; j < n; j++) {
                // Wir schauen, was im Feld steht:
                // 1 = x, -1 = o, 0 = leer (dann ein Punkt)
                char c = switch (board[i][j]) {
                    case 1 -> 'x';   // Wenn 1 drinsteht, dann ist es ein "x"
                    case -1 -> 'o';  // Wenn -1 drinsteht, dann ist es ein "o"
                    default -> '.';  // Wenn 0 drinsteht, dann ist es leer → wir zeigen einen Punkt
                };
                // Wir zeigen das Zeichen und ein Leerzeichen
                System.out.print(c + " ");
            }
            // Am Ende jeder Zeile machen wir einen Zeilenumbruch
            System.out.println();
        }
    }
}
