import java.util.Arrays;

public class ConvexPolygon extends Polygon {

    // Das ist der Bauplan für das Vieleck. Man gibt ihm die Punkte, also die Ecken.

    public ConvexPolygon(Vector2D[] vertices) {
        this.vertices = vertices; // Übergibt das Array an die Oberklasse (Polygon)
    }

    // Diese Methode rechnet aus, wie groß die Fläche von dem Vieleck ist.
    // Das geht so: Wir nehmen den ersten Punkt und bauen damit viele kleine Dreiecke.
    // Dann rechnen wir die Fläche von jedem kleinen Dreieck aus und addieren alles.
    @Override
    public double area() {
        double flaeche = 0.0;

        // Wir wählen den ersten Punkt im Array als festen Punkt für die Dreiecke
        Vector2D referenzPunkt = vertices[0];

        // Jedes Dreieck besteht aus dem Referenzpunkt und zwei benachbarten Punkten im Array
        for (int zaehler = 1; zaehler < vertices.length - 1; zaehler++) {
            Triangle dreieck = new Triangle(referenzPunkt, vertices[zaehler], vertices[zaehler + 1]);
            flaeche += dreieck.area(); // Fläche des Dreiecks zur Gesamtfläche hinzufügen
        }

        return flaeche;
    }


    // Diese Methode rechnet aus, wie lang der Rand vom Vieleck ist.
    // Also: Wie lang ist es, wenn man einmal ganz außen rum läuft?
    @Override
    public double perimeter() {
        double umfang = 0.0;

        for (int i = 0; i < vertices.length; i++) {
            Vector2D punktA = vertices[i];
            Vector2D punktB = vertices[(i + 1) % vertices.length]; // letzter Punkt verbindet sich mit erstem
            double dx =  punktA.getX() - punktB.getX();
            double dy =  punktA.getY() - punktB.getY();
            double result = Math.sqrt(dx * dx + dy * dy);
            umfang += result; // die Strecke kommt zur Gesamtlänge dazu

        }

        return umfang;
    }

    // Diese Methode wandelt das Vieleck in einen Text um, damit man es ausdrucken kann
    // Beispiel: ConvexPolygon([(0.0, 0.0), (1.0, 0.0), (1.0, 1.0)])
    @Override
    public String toString() {
        return "ConvexPolygon("  + Arrays.toString(vertices) + ")";
    }
    // Hier bauen wir ein paar Beispiele für Polygone – damit man was zum Testen hat

    public static Polygon[] somePolygons() {
        Polygon[] polygons = new Polygon[4];

        // 1. Dreieck
        Vector2D a = new Vector2D(0, 0);
        Vector2D b = new Vector2D(10, 0);
        Vector2D c = new Vector2D(5, 5);
        polygons[0] = new Triangle(a, b, c);

        // 2. Viereck
        Vector2D d = new Vector2D(0, 0);
        Vector2D e = new Vector2D(10, -5);
        Vector2D f = new Vector2D(12, 2);
        Vector2D g = new Vector2D(3, 17);
        polygons[1] = new Tetragon(d, e, f, g);

        // 3. Regelmäßiges Fünfeck mit Radius 1
        RegularPolygon fuenfecke = new RegularPolygon(5, 1);
        polygons[2] = fuenfecke;

        // 4. Regelmäßiges Sechseck mit Radius 1
        RegularPolygon secksecken = new RegularPolygon(6, 1);
        polygons[3] = secksecken;

        return polygons;
    }

    // Diese Methode rechnet die Flächen aller Polygone zusammen

    public static double totalArea(Polygon[] polygons) {
    double total = 0.0;
    for (Polygon p : polygons) {
        total += p.area(); // total = total + ../ wir addieren die Fläche von jedem Polygon dazu
    }
    return total;
}

}
/*
public static void main(String[] args) {
        Vector2D a = new Vector2D(0, 0);
        Vector2D b = new Vector2D(10, 0);
        Vector2D c = new Vector2D(5, 5);
        Vector2D[] suup = new Vector2D[] { a, b, c };
        ConvexPolygon mussui = new ConvexPolygon(suup);
        System.out.println(mussui);
    }
 */
