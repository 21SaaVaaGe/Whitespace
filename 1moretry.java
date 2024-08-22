import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

public class FileMover {
    public static void main(String[] args) {
        File mainDir = new File("C:/main directory"); // замените на путь к основной директории
        File tempDir = new File("C:/temp directory"); // замените на путь к временной директории

        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }

        moveFiles(mainDir, tempDir);
    }

    private static void moveFiles(File dir, File tempDir) {
        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                String fileName = file.getName().replaceAll(Pattern.quote("[\u3000\\s]+"), ""); // удаляем пробелы и IDEOGRAPHIC SPACE из имени файла
                File targetFile = new File(tempDir, fileName);
                if (!file.renameTo(targetFile)) {
                    System.err.println("Ошибка перемещения файла: " + file.getPath());
                } else {
                    System.out.println("Перемещен файл: " + file.getPath() + " в " + targetFile.getPath());
                }
            } else if (file.isDirectory()) {
                moveFiles(file, tempDir);
            }
        }
    }
}
