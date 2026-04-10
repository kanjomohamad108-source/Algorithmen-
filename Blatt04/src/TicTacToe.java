/**
 * This class implements and evaluates game situations of a TicTacToe game.
 */
public class TicTacToe {

    /**
     * Returns an evaluation for player at the current board state.
     * Arbeitet nach dem Prinzip der Alphabeta-Suche. Works with the principle of Alpha-Beta-Pruning.
     *
     * @param board  current Board object for game situation
     * @param player player who has a turn
     * @return rating of game situation from player's point of view
     **/
    public static final int INF = 1_000_000;

    public static int alphaBeta(Board board, int player) {
        // Diese Methode startet die Alpha-Beta-Suche
        // Der Spieler ist dran und wir schauen, wie gut das Feld für ihn ist
        // Wir rufen die andere Methode auf mit Anfangswerten für alpha und beta
        return alphaBeta(board, player, -INF, INF, board.nFreeFields());
    }
    public static int alphaBeta(Board board, int player, int alpha, int beta, int depth) {
        // Wenn jemand gewonnen hat, geben wir eine Zahl zurück, die zeigt, wie gut das ist
        if (board.isGameWon()) {
            return -(board.nFreeFields() + 1);  // Bewertung: ±(p + 1)
        }

        // Wenn es keine freien Felder mehr gibt, ist es ein Unentschieden
        if (board.nFreeFields() == 0) {
            return 0; // Unentschieden
        }

        // Jetzt probieren wir jeden möglichen Zug aus
        for (Position move : board.validMoves()) {
            // Wir machen den Zug und schauen, wie gut er ist
            board.doMove(move, player);

            int score = -alphaBeta(board, -player, -beta, -alpha, depth - 1);  // Rollenwechsel + Negation

            // Wir nehmen den Zug wieder zurück
            board.undoMove(move);

            // Wenn der neue Zug besser ist, merken wir uns das
            // Wenn der Zug schon gut genug ist, hören wir auf weiter zu suchen
            if (score > alpha) {
                alpha = score;
                if (alpha >= beta) {
                    break; // Alpha-Beta-Cut
                }
            }
        }

        return alpha;
    }



    /**
     * Vividly prints a rating for each currently possible move out at System.out.
     * (from player's point of view)
     * Uses Alpha-Beta-Pruning to rate the possible moves.
     * formatting: See "Beispiel 1: Bewertung aller Zugmöglichkeiten" (Aufgabenblatt 4).
     *
     * @param board  current Board object for game situation
     * @param player player who has a turn
     **/

    public static void evaluatePossibleMoves(Board board, int player) {
        // TODO
        // Wir sagen, welcher Spieler gerade an der Reihe ist (x oder o)
        System.out.println("Evaluation for player '" + (player == 1 ? 'x' : 'o') + "':");

        // Wir gehen jede Zeile durch (y ist die Zeile)
        for (int y = 0; y < board.getN(); y++) {

            // In jeder Zeile schauen wir jedes Feld an (x ist die Spalte)
            for (int x = 0; x < board.getN(); x++) {

                // Wir merken uns die aktuelle Position
                Position move = new Position(x, y);

                // Wir holen uns, was im Feld steht: leer, x oder o
                int current = board.getField(move);

                String output; // Was wir gleich anzeigen wollen (Zahl oder Buchstabe)

                // Wenn dort ein 'x' liegt (Spieler 1)
                if (current == 1) {
                    output = "x";
                }
                // Wenn dort ein 'o' liegt (Spieler -1)
                else if (current == -1) {
                    output = "o";
                }
                // Wenn das Feld leer ist
                else {
                    // Wir tun so, als würden wir den Zug hier machen
                    board.doMove(move, player);

                    // Wir fragen mit der Alpha-Beta-Suche: Wie gut wäre das für den Gegner?
                    int rating = -alphaBeta(board, -player);

                    // Wir machen den Zug wieder rückgängig
                    board.undoMove(move);

                    // Die Zahl schreiben wir auf (wie gut der Zug ist)
                    output = String.valueOf(rating);
                    System.out.print(rating + " ");
                }
            }
            System.out.println();
        }
    }
}
