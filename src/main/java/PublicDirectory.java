import java.io.*;
import java.net.FileNameMap;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Formatter;
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
        byte[] fileBytes = new byte[0];

        try {
            if(rangeEnd == -1) {
                rangeEnd = ((int) file.length() - 1);
            }
            if(rangeStart == -1) {
                rangeStart = ((int) file.length()) - rangeEnd;
            }

            int rangeSize;

            if(rangeEnd > rangeStart) {
                rangeSize = (rangeEnd + 1) - rangeStart;
            }
            else {
                rangeEnd += rangeStart;
                rangeSize = rangeEnd - rangeStart;
            }

            fileBytes = new byte[rangeSize];
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
            randomAccessFile.seek(rangeStart);
            randomAccessFile.read(fileBytes, 0, rangeSize);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileBytes;


    }

    public String getHash(String fileName) {
        URL resource = getClass().getResource(directoryName + "/" + fileName);
        File file = new File(resource.getPath());
        String hash = "";

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");

            try (InputStream is = new BufferedInputStream(new FileInputStream(file))) {
                final byte[] buffer = new byte[1024];
                for (int read = 0; (read = is.read(buffer)) != -1;) {
                    messageDigest.update(buffer, 0, read);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try (Formatter formatter = new Formatter()) {
                for (final byte b : messageDigest.digest()) {
                    formatter.format("%02x", b);
                }
                hash = formatter.toString();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return hash;

    }

    public void setFileContents(String fileName, String newContent) {
        URL resource = getClass().getResource(directoryName + "/" + fileName);
        File file = new File(resource.getPath());

        try {
            FileOutputStream outputStream = new FileOutputStream(file, false);
            byte[] myBytes = newContent.getBytes();
            outputStream.write(myBytes);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
