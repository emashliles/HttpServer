import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RoutingTests {
    @Test
    public void canFindHandlerForPath() {
        Router router = new Router();
        router.add(new SimpleHandler("src/test/public"));
        router.add(new CoffeeHandler());

        Handler simpleGetHandler = router.find("/");
        Handler coffeeHandler = router.find("/coffee");

        assertEquals(true, simpleGetHandler.canHandle("/"));
        assertEquals(true, coffeeHandler.canHandle("/coffee"));
    }
}
