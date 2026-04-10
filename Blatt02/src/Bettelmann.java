import java.util.*;

/**
 * The class {@code Bettelmann} simulated the card game 'Bettelmann'. You can construct objects
 * either by providing the piles of cards of the two players, or by requesting a shuffled
 * distribution of cards.
 */
public class Bettelmann {
    private Deque<Card> closedPile1;
    private Deque<Card> closedPile2;
    private int winner = -1;

    /**
     * Constructor which initializes both players with empty piles.
     */
    public Bettelmann() {
        closedPile1 = new LinkedList<>();
        closedPile2 = new LinkedList<>();
    }

    /**
     * Constructor which initializes both players with the provided piles of cards.
     *
     * @param pile1 pile of cards of player 1.
     * @param pile2 pile of cards of player 2.
     */
    public Bettelmann(Deque<Card> pile1, Deque<Card> pile2) {
        closedPile1 = pile1;
        closedPile2 = pile2;
    }

    /**
     * Returns the closed pile of player 1 (required for the tests).
     *
     * @return The closed pile of player 1.
     */
    public Deque<Card> getClosedPile1() {
        return closedPile1;
    }

    /**
     * Returns the closed pile of player 2 (required for the tests).
     *
     * @return The closed pile of player 2.
     */
    public Deque<Card> getClosedPile2() {
        return closedPile2;
    }

    /**
     * Play one round of the game. This includes drawing more cards, when both players
     * have drawn cards of the same rank. At the end of the round, the player with the
     * higher ranked card wins the trick, so all drawn cards from that round are added
     * to the bottom of her/his closed pile of cards.
     */
    public void playRound() {
        // TODO implement this method
        // Wenn schon jemand gewonnen hat, dann mache ich nichts mehr
        if (winner != -1) return;

        // Ich schaue, ob einer oder beide Stapel leer sind, bevor das Spiel richtig losgeht
        boolean empty1 = closedPile1.isEmpty();
        boolean empty2 = closedPile2.isEmpty();

        if (empty1 && empty2) {
            // Wenn beide Spieler keine Karten mehr haben, ist es ein Unentschieden
            winner = 0;
            return;
        }
        if (empty1) {
            // Wenn Spieler 1 keine Karten mehr hat, gewinnt Spieler 2
            winner = 2;
            return;
        }
        if (empty2) {
            // Wenn Spieler 2 keine Karten mehr hat, gewinnt Spieler 1
            winner = 1;
            return;
        }

        // Ich lege zwei Stapel an, auf denen ich die gespielten Karten sammle
        Deque<Card> open1 = new LinkedList<>();
        Deque<Card> open2 = new LinkedList<>();

        // Jetzt geht das eigentliche Spiel los
        while (true) {
            // Ich prüfe nochmal, ob jemand keine Karten mehr hat – das Spiel muss dann enden
            if (closedPile1.isEmpty() && closedPile2.isEmpty()) {
                winner = 0; // keiner hat mehr Karten → Unentschieden
                return;
            } else if (closedPile1.isEmpty()) {
                winner = 2; // Spieler 1 hat keine Karten mehr → Spieler 2 gewinnt
                return;
            } else if (closedPile2.isEmpty()) {
                winner = 1; // Spieler 2 hat keine Karten mehr → Spieler 1 gewinnt
                return;
            }

            // Beide Spieler ziehen eine Karte vom verdeckten Stapel
            Card card1 = closedPile1.pollFirst();
            Card card2 = closedPile2.pollFirst();

            // Ich lege die gezogenen Karten auf den offenen Stapel
            open1.addLast(card1);
            open2.addLast(card2);

            // Jetzt vergleiche ich die beiden Karten
            if (card1.compareTo(card2) > 0) {
                // Karte von Spieler 1 ist stärker → Spieler 1 gewinnt diese Runde
                closedPile1.addAll(open1); // Spieler 1 bekommt alle seine offenen Karten zurück
                closedPile1.addAll(open2); // und auch die offenen Karten von Spieler 2
                break; // Runde ist vorbei
            } else if (card1.compareTo(card2) < 0) {
                // Karte von Spieler 2 ist stärker → Spieler 2 gewinnt diese Runde
                closedPile2.addAll(open2);
                closedPile2.addAll(open1);
                break; // Runde ist vorbei
            }

            // Wenn beide Karten gleich stark sind, geht der Kampf weiter
            // Dann wird nochmal gezogen – das passiert oben im while-Loop
        }

        // Nach der Runde schaue ich, ob das Spiel vorbei ist
        if (closedPile1.isEmpty() && closedPile2.isEmpty()) {
            winner = 0; // niemand hat mehr Karten → Unentschieden
        } else if (closedPile1.isEmpty()) {
            winner = 2; // Spieler 1 ist leer → Spieler 2 gewinnt
        } else if (closedPile2.isEmpty()) {
            winner = 1; // Spieler 2 ist leer → Spieler 1 gewinnt
        } else {
            winner = -1; // es hat noch niemand gewonnen → das Spiel geht weiter
        }
    }







    /**
     * Returns the winner of the game after the end, or -1 during the game.
     *
     * @return the winner of game (1 or 2), or -1 while the game is ongoing.
     */
    public int getWinner() {
        return winner;
    }

    /**
     * Deal the given deck of cards alternately to the two players.
     * Side effect: The deck is empty after calling this method.
     *
     * @param deck The deck of cards that is distributed to the players.
     */
    public void distributeCards(Stack<Card> deck) {
        closedPile1.clear();
        closedPile2.clear();
        // use addFirst() because the last distributed card should be drawn first
        while (!deck.isEmpty()) {
            Card card = deck.pop();
            closedPile1.addFirst(card);
            if (!deck.isEmpty()) {
                card = deck.pop();
                closedPile2.addFirst(card);
            }
        }
    }

    /**
     * Shuffle a deck of cards and distribute it evenly to the two players.
     */
    public void distributeCards() {
        Stack<Card> deck = new Stack<>();
        for (int i = 0; i < Card.nCards; i++){
            deck.add(new Card(i));
        }
        Collections.shuffle(deck);
        distributeCards(deck);
    }

    /**
     * Returns a String representation of closed piles of cards of the two players.
     *
     * @return String representation of the state of the game.
     */
    @Override
    public String toString() {
        return "Player 1: " + closedPile1 + "\nPlayer 2: " + closedPile2;
    }

    public static void main(String[] args) {
/*
        // Game with a complete, shuffled deck
        Bettelmann game = new Bettelmann();
        game.distributeCards();
*/

        // For testing, you may also use specific distribtions and a small number of cards like this:
        int[] deckArray = {28, 30, 6, 23, 17, 14};
        Stack<Card> deck = new Stack<>();
        for (int id : deckArray) {
            deck.push(new Card(id));
        }
        Bettelmann game = new Bettelmann();
        game.distributeCards(deck);

        // This part is the same for both of the above variants
        System.out.println("Initial situation (top card first):\n" + game);
        int round = 0;
        while (round < 1000000 && game.getWinner()<0) {
            round++;
            game.playRound();
            System.out.println("State after round " + round + ":\n" + game);
        }
    }
}

