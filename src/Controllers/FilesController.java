package Controllers;

import Exceptions.*;
import java.io.*;
import java.nio.file.*;
import static java.lang.System.out;

public class FilesController {
    // classe servant à gérer les fichiers ouverture / manipulation / fermeture etc...

    // fileName sera le fichier cible
    private final String fileName;
    private final Path file;
    private InputStream in = null;


    public FilesController(String fileName) {
        this.fileName = fileName;
        this.file = FileSystems.getDefault().getPath(fileName);
        this.in = openFile();
    }

    public void writeInFile(String content) {
        // on écrit dans le fichier le contenu de content
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

    public String getFileContent() throws EmptyFileException {
        // on retourne le contenu du fichier lu
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
            if (str == null && stringBuffer == null)
                throw new EmptyFileException("The file is empty");
        } catch (IOException e) {
            throw new EmptyFileException("The file is empty");
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
        try {
            out.println(this.getFileContent());
        } catch (EmptyFileException e) {
            throw new RuntimeException(e);
        }
    }


    private InputStream openFile() {
        // on tente d'ovrir le fichier
        InputStream in;

        try {
            in = Files.newInputStream(this.file);
        } catch (IOException e) {
            System.err.println(e);
            //si le fichier n'a pas pue être ouvert on le créer
            createNewFile(this.fileName);
            // et retente d'ouvrir le fichier nouvellement créer
            return openFile();
        }
        return in;
    }

    private void createNewFile(String name) {
        // on créer le fichier demander
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