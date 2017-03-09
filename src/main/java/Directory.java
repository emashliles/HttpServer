import java.io.*;
import java.net.FileNameMap;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

public class Directory {

    private String directoryPath;

    public Directory(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public List<String> getFiles() {
        File directory = new File(directoryPath);
        List<String> files = new ArrayList<>();

        for(File file : directory.listFiles()) {
            files.add(file.getName());
        }
        return files;
    }

    public byte[] getFileContent(String fileName) {
        File file = getFile(fileName);
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
        File file = getFile(fileName);

        URL resource = getUrl(file);
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
        File file = getFile(fileName);
        byte[] fileBytes = new byte[0];

        try {
            rangeEnd = checkRangeEndValue(rangeEnd, file);
            rangeStart = checkRangeStartValue(rangeStart, rangeEnd, file);

            int rangeSize = getRangeSize(rangeStart, rangeEnd);

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

    public void createNewEmptyFile(String fileName) {
        File file = getFile(fileName);
        file.mkdir();
    }

    public void setFileContents(String fileName, String newContent, boolean append) {
        File file = getFile(fileName);

        try {
            FileOutputStream outputStream = new FileOutputStream(file, append);
            byte[] myBytes = newContent.getBytes();
            outputStream.write(myBytes);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getFileHash(String fileName) {
        File file = getFile(fileName);
        MessageDigest rawHash = getRawHash(file);
        return  formatHash(rawHash);
    }

    private int checkRangeStartValue(int rangeStart, int rangeEnd, File file) {
        if(rangeStart == -1) {
            rangeStart = ((int) file.length()) - rangeEnd;
        }
        return rangeStart;
    }

    private URL getUrl(File file) {
        URL resource = null;
        try {
            resource = file.toURI().toURL();
        } catch (MalformedURLException e) {
        }
        return resource;
    }

    private int checkRangeEndValue(int rangeEnd, File file) {
        if(rangeEnd == -1) {
            rangeEnd = ((int) file.length() - 1);
        }
        return rangeEnd;
    }

    private int getRangeSize(int rangeStart, int rangeEnd) {
        int rangeSize;
        if(rangeEnd > rangeStart) {
            rangeSize = (rangeEnd + 1) - rangeStart;
        }
        else {
            rangeEnd += rangeStart;
            rangeSize = rangeEnd - rangeStart;
        }
        return rangeSize;
    }

    private MessageDigest getRawHash(File file) {
        MessageDigest rawHash = null;

        try {
            rawHash = MessageDigest.getInstance("SHA1");
            try (InputStream is = new BufferedInputStream(new FileInputStream(file))) {
                final byte[] buffer = new byte[1024];
                for (int read = 0; (read = is.read(buffer)) != -1; ) {
                    rawHash.update(buffer, 0, read);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return rawHash;
    }

    private File getFile(String fileName) {
        return new File(directoryPath + "/" + fileName);
    }

    private String formatHash(MessageDigest rawHash) {
        String hash;
        try (Formatter formatter = new Formatter()) {
            for (final byte b : rawHash.digest()) {
                formatter.format("%02x", b);
            }
            hash = formatter.toString();
        }
        return hash;
    }
}
