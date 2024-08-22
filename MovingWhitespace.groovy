import java.io.File

def mainDir = new File('C:/main directory')
def tempDir = new File('C:/temp directory') 

if (!tempDir.exists()) {
    tempDir.mkdirs()
}

mainDir.eachFileRecurse { file ->
    if (file.isFile()) {
        def fileName = file.name.replaceAll(/[\u3000\s]+$/, '')
        def targetFile = new File(tempDir, fileName)
        file.renameTo(targetFile)

    }
}
