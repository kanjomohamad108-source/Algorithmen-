import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * This class implements a game of Row of Bowls.
 * For the games rules see Blatt05. The goal is to find an optimal strategy.
 */
public class RowOfBowls {

    public RowOfBowls() {
    }
    private int[] values;
    private int[][] dp;
    
    /**
     * Implements an optimal game using dynamic programming
     * @param values array of the number of marbles in each bowl
     * @return number of game points that the first player gets, provided both parties play optimally
     */
    public int maxGain(int[] values)
    {
        // TODO
        // Speichere das gegebene Array mit den Schüsseln
        this.values = values;
        // Anzahl der Schüsseln (wie lang das Array ist)
        int n = values.length;
        // Erstelle eine Tabelle (Matrix) zum Merken der besten Punktunterschiede
        dp = new int[n][n];

        // Wir gehen alle möglichen Längen von Schüssel-Abschnitten durch (z.B. 1 Schüssel, 2 Schüsseln, ... bis alle)
        for (int len = 0; len < n; len++) {
            // i ist der Startpunkt des Abschnitts
            for (int i = 0; i < n - len; i++) {
                // j ist das Ende des Abschnitts
                int j = i + len;

                // Wenn nur eine Schüssel übrig ist, nimmt der Spieler sie einfach
                if (i == j) {
                    dp[i][j] = values[i];
                } else {
                    // Wenn mehr als eine Schüssel da ist:
                    // Spieler kann links oder rechts nehmen.

                    dp[i][j] = Math.max(values[i] - dp[i + 1][j], values[j] - dp[i][j - 1]);
                }// Danach spielt der Gegner optimal weiter und holt das Beste für sich raus.
                // Wir rechnen beide Möglichkeiten durch und nehmen den besseren Weg für uns.
            }
        }
        return dp[0][n - 1];
// die beste Punktedifferenz für Spieler 1, wenn alle Schüsseln zur Auswahl stehen

    }

    /**
     * Implements an optimal game recursively.
     *
     * @param values array of the number of marbles in each bowl
     * @return number of game points that the first player gets, provided both parties play optimally
     */
    public int maxGainRecursive(int[] values) {
        // TODO

        this.values = values;
        int n = values.length;
        dp = new int[n][n];

        // Starte die Berechnung mit dem ganzen Bereich (von der ersten bis zur letzten Schüssel)
        return maxGainRecursive(0, n - 1);
    }

    // Diese Methode schaut: Welche Punktedifferenz kann ich bekommen,
    // wenn nur noch die Schüsseln von i bis j übrig sind
    public int maxGainRecursive(int i, int j) {
        if (i == j) {
            // Nur eine Schüssel da → nimm sie einfach
            return values[i];
        } else {
            // → links nehmen (i) und Gegner spielt danach [i+1..j]
            // → oder rechts nehmen (j) und Gegner spielt danach [i..j-1]
            return Math.max(values[i] - maxGainRecursive( i + 1,j ), values[j] - maxGainRecursive( i , j - 1)); //
        }
    }
    
    /**
     * Calculates an optimal sequence of bowls using the partial solutions found in maxGain(int values)
     * @return optimal sequence of chosen bowls (represented by the index in the values array)
     */
    public Iterable<Integer> optimalSequence()
    {
        // TODO
        int i = 0;
        int j = values.length - 1;
        Queue<Integer> seq = new LinkedList<Integer>(); // Liste für die Zugfolge (welche Schüssel wurde gewählt)
        while (i < j) {

            if (values[i] - dp[i + 1][j] > values[j] - dp [i][j -1]) {
                seq.add(i);  // Wir nehmen die linke Schüssel (Index i)
                i++;         // und bewegen uns einen Schritt nach rechts
            } else {
                seq.add(j);  // Wir nehmen die rechte Schüssel (Index j)
                j--;         // und bewegen uns einen Schritt nach links
            }

        }
        seq.add(i); // vollständiger aufruf also am Ende nur noch eine Schüssel übrig ist
        return seq;
    }


    public static void main(String[] args) {
        // For Testing
    int [] values = {4,7,3,2};
    int exp = 7;
    RowOfBowls r = new RowOfBowls();
    assertEquals(exp, r.maxGain(values));
    }
}


