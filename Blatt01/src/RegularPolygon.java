

public class RegularPolygon extends ConvexPolygon {

    // TODO
    // Hier speichern wir, wie groß der Radius ist (Abstand vom Mittelpunkt zu einer Ecke)
    // Und wie viele Ecken das Vieleck hat
    private double radius;
    private int N;

    // Das ist der normale Konstruktor – hier geben wir an, wie viele Ecken und wie groß der Radius ist
    public RegularPolygon(int N, double radius) {
        // TODO
        super(punktederecken(N, radius)); // berechnet alle Eckpunkte und übergibt sie an die Oberklasse
            this.N = N;
            this.radius = radius;
    }
    // Das ist der Copy-Konstruktor – er erstellt eine neue Kopie von einem anderen RegularPolygon
    public RegularPolygon(RegularPolygon polygon) {
        // TODO
        this(polygon.N, polygon.radius);

    }
    // Mit dieser Methode kann man das Vieleck größer oder kleiner machen
    // Wir setzen einfach einen neuen Radius und berechnen die neuen Ecken
    public void resize(double newradius) {
        // TODO
        this.radius = newradius;
        this.vertices = punktederecken(this.N, this.radius);
    }
    // Gibt das Vieleck als Text aus, z. b. RegularPolygon{N=5, radius=1.0}
    @Override
    public String toString() {
        // TODO
        return "RegularPolygon{N=" + N + ", radius=" + radius + "}";
    }
    // Diese Methode berechnet die Koordinaten aller Ecken
    // Wir drehen das Vieleck gleichmäßig im Kreis
    private static Vector2D[] punktederecken(int N, double radius) {
        Vector2D[] result = new Vector2D[N];

        for (int i = 0; i < N; i++) {
            double angle = 2 * Math.PI * i / N; // Winkel berechnen (damit es gleichmäßig ist)
            double x = radius * Math.cos(angle);
            double y = radius * Math.sin(angle);
            result[i] = new Vector2D(x, y);
        }

        return result;
    }

    public static void main(String[] args) {
        RegularPolygon pentagon = new RegularPolygon(5, 1);
        System.out.println("Der Flächeninhalt des " + pentagon + " beträgt " + pentagon.area() + " LE^2.");
//        RegularPolygon otherpentagon = pentagon;      // Dies funktioniert nicht!
        RegularPolygon otherpentagon = new RegularPolygon(pentagon);
        pentagon.resize(10);
        System.out.println("Nach Vergrößerung: " + pentagon + " mit Fläche " + pentagon.area() + " LE^2.");
        System.out.println("Die Kopie: " + otherpentagon + " mit Fläche " + otherpentagon.area() + " LE^2.");
        /*
        Die erwartete Ausgabe ist:
Der Flächeninhalt des RegularPolygon{N=5, radius=1.0} beträgt 2.377641290737883 LE^2.
Nach Vergrößerung: RegularPolygon{N=5, radius=10.0} mit Fläche 237.7641290737884 LE^2.
Die Kopie: RegularPolygon{N=5, radius=1.0} mit Fläche 2.377641290737883 LE^2.
         */
    }
}

