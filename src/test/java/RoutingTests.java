import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RoutingTests {
    @Test
    public void canFindHandlerForPath() {
        Router router = new Router();
        router.add(new SimpleHandler());
        router.add(new CoffeeHandler());

        Handler simpleGetHandler = router.find("/");
        Handler coffeeHandler = router.find("/coffee");

        assertEquals("/", simpleGetHandler.path());
        assertEquals("/coffee", coffeeHandler.path());
    }
}
