package kivetelek;

/**
 * Pálya elhagyás esetén ezt a kivételt dobja.
 */
public class ErreNemMehetsz extends Throwable {
    @Override
    public String getMessage() {
        return "Erre nem mehetsz";
    }
}
