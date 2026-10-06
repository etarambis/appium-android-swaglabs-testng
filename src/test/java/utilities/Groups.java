package utilities;

/**
 * Nombres de los grupos de TestNG. Se usan en {@code @Test(groups = ...)}
 * y como filtro de Maven: {@code -Dgroups="smoke"}.
 */
public final class Groups {
    public static final String REGRESSION = "regression";
    public static final String SMOKE = "smoke";

    private Groups() {
    }
}
