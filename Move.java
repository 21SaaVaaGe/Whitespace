import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

public class FileMover {
    public static void main(String[] args) {
        File mainDir = new File("C:/main directory"); // replace with your main directory path
        File tempDir = new File("C:/temp directory"); // replace with your temp directory path

        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }

        moveFiles(mainDir, tempDir);
    }

    private static void moveFiles(File dir, File tempDir) {
        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                String fileName = file.getName().replaceAll(Pattern.quote("[\u3000\\s]+"), ""); // remove trailing whitespaces and IDEOGRAPHIC SPACE
                File targetFile = new File(tempDir, fileName);
                if (!file.renameTo(targetFile)) {
                    System.err.println("Error moving file: " + file.getPath());
                } else {
                    System.out.println("Moved file: " + file.getPath() + " to " + targetFile.getPath());
                }
            } else if (file.isDirectory()) {
                moveFiles(file, tempDir);
            }
        }
    }
}
