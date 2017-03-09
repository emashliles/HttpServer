import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class RoutingTests {
    @Test
    public void findHandlerForPath() {
        Router router = new Router();
        router.add(new SimpleHandler("src/test/resources/public"));
        router.add(new CoffeeHandler());

        Handler simpleGetHandler = router.findHandler("/");
        Handler coffeeHandler = router.findHandler("/coffee");

        assertEquals(true, simpleGetHandler.canHandle("/"));
        assertEquals(true, coffeeHandler.canHandle("/coffee"));
    }

    @Test
    public void notFoundHandlerIfNoneFound() {
        Router router = new Router();

        Handler handler = router.findHandler("/hello");

        assertEquals(true, handler.canHandle("/hello"));
        assertTrue(handler instanceof NotFoundHandler);
    }
}
