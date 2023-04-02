import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        StringBuilder log = new StringBuilder();

        final int FOLDERS = 8;

        File[] files = {
                new File("C://Prog/Games/src"),
                new File("C://Prog/Games/res"),
                new File("C://Prog/Games/savegames"),
                new File("C://Prog/Games/temp"),
                new File("C://Prog/Games/src/main"),
                new File("C://Prog/Games/src/test"),
                new File("C://Prog/Games/res/drawables"),
                new File("C://Prog/Games/res/vectors"),
                new File("C://Prog/Games/res/icons"),
                new File("C://Prog/Games/src/main", "Main.java"),
                new File("C://Prog/Games/src/main", "Utils.java"),
                new File("C://Prog/Games/temp", "temp.txt")};

        for (int i = 0; i < FOLDERS; i++) {
            if (files[i].mkdir()) {
                log.append("Created directory \n").append(files[i].getName()).append("\n\n");
            } else {
                System.out.println(files[i].getName() + " не создан");
            }
        }
        for (int i = FOLDERS + 1; i < files.length; i++) {
            try {
                if (files[i].createNewFile()) {
                    log.append("Created file \n").append(files[i].getName()).append("\n\n");
                }
                else {
                    System.out.println(files[i].getName() + " не создан");
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

        try (FileWriter writer = new FileWriter(files[files.length-1])) {
            writer.write(log.toString());
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}