package utilities;

public class Deeplinks {
    private static final String COMPLETE_CHECKOUT = "swaglabs://complete";
    private static final String ITEM_DETAIL = "swaglabs://swag-item/%d";
    private static final String WEB_VIEW = "swaglabs://webview";
    private static final String YOUR_CART = "swaglabs://cart/%s";
    private static final String SHOPPING = "swaglabs://swag-overview/%s";

    private static void goTo(String deeplink) {
        new DriverProvider().get().get(deeplink);
    }

    public static void goToCompleteCheckout() {
        goTo(COMPLETE_CHECKOUT);
    }

    public static void goToItemDetail(int index) {
        final var deeplink = String.format(ITEM_DETAIL, index);
        goTo(deeplink);
    }
    public static void goToYourCart(String list) {
        final var deeplink = String.format(YOUR_CART, list);
        goTo(deeplink);
    }

    public static void goToWebView() {
        goTo(WEB_VIEW);
    }

    public static void goToShopping(String list) {
        final var deeplink = String.format(SHOPPING, list);
        goTo(deeplink);
    }

}
