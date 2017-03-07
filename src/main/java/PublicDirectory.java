import java.io.*;
import java.net.FileNameMap;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    public String getContentType(String fileName) {
        URL resource = getClass().getResource(directoryName + "/" + fileName);
        String type = "text/plain";

        try {
            FileNameMap fileNameMap = resource.openConnection().getFileNameMap();
            String contentType = fileNameMap.getContentTypeFor(fileName);

            if(contentType != null) {
                type = contentType;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return type;
    }

    public byte[] getPartialFileContent(String fileName, int rangeStart, int rangeEnd) {
        URL resource = getClass().getResource(directoryName + "/" + fileName);
        File file = new File(resource.getPath());
        byte[] fileBytes = new byte[rangeEnd - rangeStart];


        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
            randomAccessFile.seek(rangeStart);
            randomAccessFile.read(fileBytes, 0, rangeEnd - rangeStart);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileBytes;


    }
}
