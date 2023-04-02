import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        GameProgress progress1 = new GameProgress(100, 1, 1, 45.897);
        GameProgress progress2 = new GameProgress(90, 3, 2, 154.52);
        GameProgress progress3 = new GameProgress(150, 10, 8, 1159.845);

        saveGame("C://Prog/Games/savegames/save_0.dat", progress1);
        saveGame("C://Prog/Games/savegames/save_1.dat", progress2);
        saveGame("C://Prog/Games/savegames/save_2.dat", progress3);

        List<String> gameSaves =  new ArrayList<>();
        gameSaves.add("C://Prog/Games/savegames/save_0.dat");
        gameSaves.add("C://Prog/Games/savegames/save_1.dat");
        gameSaves.add("C://Prog/Games/savegames/save_2.dat");

        zipFiles("C://Prog/Games/savegames/gameSaves.zip", gameSaves);

        openZip("C://Prog/Games/savegames/gameSaves.zip", "C://Prog/Games/savegames");

        System.out.println(openProgress("C://Prog/Games/savegames/packagedSave_1.dat"));
    }

    //добавление записи в temp.txt
    public static void writeLog(String path, String option) {
        try(FileWriter writer = new FileWriter("C://Prog/Games/temp/temp.txt", true)) {
            writer.write(option + "\n" + path + "\n\n");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void saveGame(String path, GameProgress progress) {
        try(FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(progress);
            writeLog(path, "Created file");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String path, List<String> files) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path))) {
            writeLog(path, "Created archive");
            for (String file : files) {
                try(FileInputStream fis = new FileInputStream(file)) {
                    ZipEntry entry = new ZipEntry("packagedSave_" + files.indexOf(file) + ".dat");
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                    writeLog(file, "File archived");
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        for (String file : files) {
            File toDelete = new File(file);
            if (toDelete.delete()) {
                writeLog(file, "File deleted");
            } else {
                System.out.println("File not deleted\n" + file);
            }
        }
    }

    public static void openZip(String filePath, String dirPath) {
        try(ZipInputStream zin = new ZipInputStream(new FileInputStream(filePath))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName();
                FileOutputStream fos = new FileOutputStream(dirPath + "/" + name);
                writeLog(name, "File unzipped");
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fos.write(c);
                }
                fos.flush();
                zin.closeEntry();
                fos.close();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static GameProgress openProgress(String path) {
        GameProgress gameProgress = null;
        try(FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            gameProgress = (GameProgress) ois.readObject();
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
        return gameProgress;
    }
}