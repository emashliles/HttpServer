import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class DirectoryTests {

    private PublicDirectory publicDirectory;

    @Before
    public void setUp() {
        publicDirectory = new PublicDirectory("public");
    }

    @Test
    public void canFindDirectory() {
        URL directoryPath = publicDirectory.getDirectory();
        assertTrue(directoryPath.toString().contains("public"));
    }

    @Test
    public void canListFiles() {
        List<String> files = publicDirectory.getFiles();
        assertTrue(files.contains("file1"));
    }

    @Test
    public void canReturnFileContents() {
        byte[] file1s = publicDirectory.getFileContent("file1");
        StringBuilder body = new StringBuilder();

        for(int i = 0; i < file1s.length; i++) {
            body.append((char) file1s[i]);
        }

        assertEquals("file1 contents", body.toString());
    }

    @Test
    public void findContentType() {
        assertEquals("image/jpeg", publicDirectory.getContentType("image.jpeg"));
    }

    @Test
    public void getPartialContent() {
        byte[] partialFile = publicDirectory.getPartialFileContent("partial_content.txt", 0, 4);
        StringBuilder body = new StringBuilder();

        for(int i = 0; i < partialFile.length; i++) {
            body.append((char) partialFile[i]);
        }

        assertEquals("This ", body.toString());
    }

    @Test
        public void getToEndOfFileIfNoRangeEnd() {
        byte[] partialFile = publicDirectory.getPartialFileContent("partial_content.txt", 4, -1);
        StringBuilder body = new StringBuilder();

        for(int i = 0; i < partialFile.length; i++) {
            body.append((char) partialFile[i]);
        }

        assertEquals(" is a file that contains text to read part of in order to fulfill a 206.\n", body.toString());
    }

    @Test
    public void getFromEndOfFileIfNoRangeStart() {
        byte[] partialFile = publicDirectory.getPartialFileContent("partial_content.txt", -1, 6);
        StringBuilder body = new StringBuilder();

        for(int i = 0; i < partialFile.length; i++) {
            body.append((char) partialFile[i]);
        }
        assertEquals(" 206.\n", body.toString());
    }

    @Test
    public void defaultContentType() {
        assertEquals("text/plain", publicDirectory.getContentType("file1"));
    }

    @Test
    public void getFileSHA1() {
        assertEquals("dc50a0d27dda2eee9f65644cd7e4c9cf11de8bec", publicDirectory.getHash("patch-content.txt"));
    }

    @Test
    public void setFileContents() {
        publicDirectory.setFileContents("patch-content.txt", "patched content");
        assertEquals("5c36acad75b78b82be6d9cbbd6143ab7e0cc04b0", publicDirectory.getHash("patch-content.txt"));

        publicDirectory.setFileContents("patch-content.txt", "default content");
        assertEquals("dc50a0d27dda2eee9f65644cd7e4c9cf11de8bec", publicDirectory.getHash("patch-content.txt"));
    }
}
