import java.io.File;
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
}
