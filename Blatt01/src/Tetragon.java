//TODO
import java.util.Arrays;
public class Tetragon extends ConvexPolygon {

    // Konstruktor, der vier Punkte erhält und an ConvexPolygon weitergibt
    public Tetragon(Vector2D a, Vector2D b, Vector2D c, Vector2D d) {
        super(new Vector2D[]{a, b, c, d});
    }

}

