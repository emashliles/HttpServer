import org.junit.Test;

import java.net.URL;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

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

    @Test
    public void canReturnFileContents() {
        PublicDirectory publicDirectory = new PublicDirectory("public");

        byte[] file1s = publicDirectory.getFileContent("file1");

        StringBuilder body = new StringBuilder();

        for(int i = 0; i < file1s.length; i++) {
            body.append((char) file1s[i]);
        }

        assertEquals("file1 contents", body.toString());
    }

    @Test
    public void findContentType() {
        PublicDirectory publicDirectory = new PublicDirectory("public");

        assertEquals("image/jpeg", publicDirectory.getContentType("image.jpeg"));
    }

    @Test
    public void getPartialContent() {
        PublicDirectory publicDirectory = new PublicDirectory("public");

        byte[] partialFile = publicDirectory.getPartialFileContent("partial_content.txt", 0, 4);

        StringBuilder body = new StringBuilder();

        for(int i = 0; i < partialFile.length; i++) {
            body.append((char) partialFile[i]);
        }

        assertEquals("This", body.toString());

    }

    @Test
    public void defaultContentType() {
        PublicDirectory publicDirectory = new PublicDirectory("public");

        assertEquals("text/plain", publicDirectory.getContentType("file1"));
    }
}
