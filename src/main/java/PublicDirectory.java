import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PublicDirectory {

    private String directoryName;

    public PublicDirectory(String directoryName) {
        this.directoryName = directoryName;
    }

    public URL getDirectory() {
        return getClass().getResource(directoryName);
    }

    public List<String> getFiles() {
        URL resource = getClass().getResource(directoryName);

        File directory = new File(resource.getPath());
        List<String> files = new ArrayList<>();

        for(File file : directory.listFiles()) {
            files.add(file.getName());
        }
        return files;
    }

    public byte[] getFileContent(String fileName) {
        URL resource = getClass().getResource(directoryName + "/" + fileName);

        File file = new File(resource.getPath());

        byte[] fileBytes = new byte[(int) file.length()];

        try {
            FileInputStream inputStream = new FileInputStream(file);
            inputStream.read(fileBytes);
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileBytes;
    }
}
