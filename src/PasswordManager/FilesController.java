package PasswordManager;

import java.io.*;
import java.nio.file.*;
import static java.lang.System.out;

public class FilesController {
    private final String fileName;
    private final Path file;
    private final InputStream in;


    public FilesController(String fileName) {
        this.fileName = fileName;
        this.file = FileSystems.getDefault().getPath(fileName);
        this.in = openFile();
    }

    public void writeInFile(String content) {
        Writer wt = null;

        try {
            wt = new BufferedWriter(new FileWriter(this.fileName));
            wt.write(content);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (wt != null)
                    wt.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String getFileContent() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(this.in));
        String stringBuffer = null;

        try {
            String str;
            while ((str = reader.readLine()) != null) {
                if (stringBuffer == null)
                    stringBuffer = str;
                else
                    stringBuffer = stringBuffer + str;
            }
        } catch (IOException e) {
            out.printf("eol");
        }

        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return stringBuffer;
    }

    @SuppressWarnings("unused")
    public void printFileContent() {
        out.println(this.getFileContent());
    }


    private InputStream openFile() {
        InputStream in;

        try {
            in = Files.newInputStream(this.file);
        } catch (IOException e) {
            System.err.println(e);
            createNewFile(this.fileName);
            return openFile();
        }
        return in;
    }

    private void createNewFile(String name) {
        FileOutputStream out = null;

        try {
            out = new FileOutputStream(name);
            out.write(0);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {

            if(out != null) {
                try {
                    out.close();
                } catch (IOException expt) {
                    throw new RuntimeException(expt);
                }
            }
        }
    }

    public void close(){
        try {
            if (this.in != null)
                this.in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}