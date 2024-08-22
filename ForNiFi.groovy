import java.io.File
import java.io.FileInputStream
import java.io.ByteArrayOutputStream
import org.apache.nifi.flowfile.FlowFile

def mainDir = new File('C:/main directory')
def tempDir = new File('C:/temp directory') 
if (!tempDir.exists()) {
    tempDir.mkdirs()
}
def flowFiles = []
mainDir.eachFileRecurse { file ->
    if (file.isFile()) {
        def fileName = file.name.replaceAll(/[\u3000\s]+$/, '') 
        def targetFile = new File(tempDir, fileName)
        file.renameTo(targetFile)
        def fileBytes = new byte[(int) targetFile.length()]
        new FileInputStream(targetFile).withStream { stream ->
            stream.read(fileBytes)
        }
     
        def flowFile = new FlowFile(fileName, fileBytes)
        flowFiles << flowFile
        targetFile.delete()
    }
}
flowFiles

import java.io.File;
import java.io.FileInputStream;
import java.io.ByteArrayOutputStream;
import org.apache.nifi.flowfile.FlowFile;

public class FileMover {
    public static void main(String[] args) {
        File mainDir = new File("C:/main directory"); 
        File tempDir = new File("C:/temp directory");

        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }

        List<FlowFile> flowFiles = new ArrayList<>();

        moveFiles(mainDir, tempDir, flowFiles);
    }

    private static void moveFiles(File dir, File tempDir, List<FlowFile> flowFiles) {
        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                String fileName = file.getName().replaceAll(Pattern.quote("[\u3000\\s]+"), "");
                File targetFile = new File(tempDir, fileName);
                file.renameTo(targetFile);

                // Читаем файл в массив байтов
                byte[] fileBytes = new byte[(int) targetFile.length()];
                try (FileInputStream fis = new FileInputStream(targetFile)) {
                    fis.read(fileBytes);
                }             
                FlowFile flowFile = new FlowFile(fileName, fileBytes);

                flowFiles.add(flowFile);

                targetFile.delete();
            } else if (file.isDirectory()) {
                moveFiles(file, tempDir, flowFiles);
            }
        }
    }
}
