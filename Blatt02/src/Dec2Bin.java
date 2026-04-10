import java.util.Stack;
/** * A class for constructing a Decimal-to-Binary Number- Converter; * contains a main method for demonstration. */
public class Dec2Bin {

    public Stack<Integer> binStack;  // We make it public to modify it in our tests.
    private int N;

    /**
     * Constructor of an empty object. Use method {@code convert()} to convert a number.
     */
    public Dec2Bin() {
        binStack = new Stack<>();
    }

    /**
     * Returns the number that is converted as {@code int}.
     *
     * @return the converted number
     */
    public int getN() {
        return N;
    }

    /**
     * Converts the given number into binary format, with each digit being represented in a
     * stack of {@code int}.
     *
     * @param N the number that is to be converted.
     */
    public void convert(int N) {
        // TODO implement this method
        this.N = N; //Speichert diee Eingabezahl in der Objektvariablen N, damit man sie später mit getN() wieder abrufen kannst.
        binStack.clear(); //Löscht den Stack, falls vorher schon etwas drin war –> vermeidung von falsche Werte.
        if (N == 0) {
            binStack.push(0);
            return;
            /*Wenn N = 0, ist die Binärdarstellung "0" → also nur eine Ziffer.
            Deshalb pushst man  direkt 0 auf den Stack und brichst danach ab (return)*/
        }

        while (N > 0) {
            int digit = N % 2;
            binStack.push(digit);
            N = N / 2;
            /*
    1.	N % 2 berechnet den Rest → also entweder 0 oder 1 → das ist eine Binärziffer.
	2.	binStack.push(digit) speichert die Ziffer oben im Stack.
	3.	N = N / 2 halbiert die Zahl (ganzzahlig) → nächster Schritt der Umrechnung.
	4.	Das wiederholt sich, bis N == 0, also alle Ziffern berechnet wurden.*/
        }
    }

    /**
     * Returns the digits that are stored in {@code binStack} as a string. To is the binary format of the
     * converted number.
     * For testing purpose, we require that the function works also, if the variable {@code binStack} is
     * modified externally.
     *
     * @return a string representation of the number in binary format.
     */
    @Override
    public String toString() {
        // Caution: Stack.toString() does NOT respect stack order. Do not use it.
        // TODO implement this method

        // Wir bauen einen leeren String auf, in dem wir die Binärzahl zusammensetzen
        String result = "";

        // Jetzt gehen wir den Stack von oben nach unten durch (also vom letzten bis zum ersten Element)
        for (int i = binStack.size() - 1; i >= 0; i--) {
            // Wir hängen jede Ziffer (0 oder 1) hinten an den String dran
            result += binStack.get(i);
        }

        // zusammengesetzten Binär-String zurück
        return result;
    }
    public static void main(String[] args) {
        Dec2Bin dec2bin = new Dec2Bin();
        dec2bin.convert(50);
        System.out.println("Die Zahl " + dec2bin.getN() + " in Binärdarstellung: " + dec2bin);
        // Do it another time to demonstrate that toString does not erase the binStack.
        System.out.println("Die Zahl " + dec2bin.getN() + " in Binärdarstellung: " + dec2bin);
    }
}

