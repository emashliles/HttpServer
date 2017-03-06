import org.junit.Test;

import java.net.URL;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

public class DirectoryTests {
    @Test
    public void canFindDirectory() {
        URL directoryPath = new PublicDirectory("public").getDirectory();

        assertTrue(directoryPath.toString().contains("public"));
    }

    @Test
    public void canListFiles() {
        List<String> files = new PublicDirectory("public").getFiles();

        assertTrue(files.contains("file1"));
    }
}
