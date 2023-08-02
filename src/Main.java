import java.awt.*;
import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.ArrayList;
import java.nio.file.Path;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import static java.lang.System.out;
import static java.nio.file.StandardOpenOption.CREATE;

public class Main {
    public static void main(String[] args)
    {
        Scanner pipe = new Scanner(System.in);
        ArrayList<String> arrList = new ArrayList<>();

        String switch1 = "";
        boolean rerun = true;
        boolean needsToBeSaved = false;
        String name = "";

        do {
            switch1 = Menu(pipe, arrList);
            switch(switch1) {
                case "A":
                    addToList(pipe, arrList);
                    needsToBeSaved = true;
                    break;
                case "C":
                    clearList(arrList);
                    needsToBeSaved = true;
                    break;
                case"D":
                    deleteFromList(pipe, arrList);
                    needsToBeSaved = true;
                    break;
                case "O":
                    name = Open(pipe, arrList, needsToBeSaved);
                    break;
                case "S":
                    saveFile(arrList, name);
                    needsToBeSaved = false;
                    break;
                case "V":
                    Display(arrList);
                    break;
                case"Q":
                    if(SafeInput.getYNConfirm(pipe, "Are you sure? List will be lost"))
                    {
                        if(needsToBeSaved)
                        {
                            saveFile(arrList, name);
                        }
                        rerun = false;
                    } else {
                        System.out.println("Returning to menu");
                    }
                    break;
            }
        } while(rerun);
    }

    private static String Menu(Scanner pipe, ArrayList arrList)
    {
        if(arrList.isEmpty())
        {
            System.out.println("Your list is currently empty.");
        } else {
            System.out.println("Your list:");
            for (int x = 0; x < arrList.size(); x++)
            {
                System.out.printf("   %d. %s\n", x + 1, arrList.get(x));
            }
        }
        return SafeInput.getRegExString(pipe, "Select a menu option:\n   A: Add\n   C: Clear\n   D: Delete\n   O: Open\n   S: Save\n   V: View\n   Q: Quit\n", "[AaCcDdOoSsVvQq]").toUpperCase();
    }

    public static void clearList(ArrayList arrList)
    {
        arrList.clear();
    }

    public static void addToList(Scanner pipe, ArrayList arrList)
    {
        String itemToAdd = SafeInput.getNonZeroLenString(pipe, "What do you want to add to the array?");
        arrList.add(itemToAdd);
    }

    public static void Display(ArrayList arrList)
    {
        for(int x = 0; x < arrList.size(); x++);
        {
            int x = 0;
            System.out.println(arrList.get(x));
        }
    }

    public static void deleteFromList(Scanner pipe, ArrayList arrList)
    {
        int itemToDelete = SafeInput.getRangedInt(pipe, "What item do you want to delete", 1, arrList.size());
        arrList.remove(itemToDelete - 1);
        System.out.println(itemToDelete + " list item was deleted");
    }

    public static void saveFile(ArrayList arrList, String fileName)
    {
        PrintWriter outFile;
        Path target = new File(System.getProperty("user.dir")).toPath();

        if(fileName.equals(""))
        {
            target = target.resolve("src\\list.txt");
        } else {
            target = target.resolve(fileName);
        }

        try {
            outFile = new PrintWriter(target.toString());
            for(int x = 0; x < arrList.size(); x++)
            {
                outFile.println(arrList.get(x));
            }

            outFile.close();
            System.out.printf("File \"%s\" saved.\n", target.getFileName());

        } catch (IOException e) {
            System.out.println("Error!");
        }
    }

    private static String Open (Scanner pipe, ArrayList arrList, boolean needsToSaved)
    {
        if(needsToSaved)
        {
            String prompt = "New List loading, current list will be deleted";
            boolean burnListYN = SafeInput.getYNConfirm(pipe, prompt);
            if(!burnListYN)
            {
                return "";
            }
        }

        clearList(arrList);
        Scanner inFile;

        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
        chooser.setFileFilter(filter);

        String row = "";

        Path target = new File(System.getProperty("user.dir")).toPath();
        target = target.resolve("src");
        chooser.setCurrentDirectory(target.toFile());

        File selectedFile;
        String rec = "";

        ArrayList<String> lines = new ArrayList<>();

        try
        {
            File workingDirectory = new File(System.getProperty("user.dir"));

            chooser.setCurrentDirectory(workingDirectory);

            if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                selectedFile = chooser.getSelectedFile();
                Path file = selectedFile.toPath();

                InputStream in =
                        new BufferedInputStream(Files.newInputStream(file, CREATE));
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(in));

                int line = 0;
                while(reader.ready())
                {
                    rec = reader.readLine();
                    lines.add(rec);
                    line++;

                    System.out.printf("\nLine %4 %-60s ", line, rec);
                }
                for(String l:lines)
                {
                    System.out.println(l);
                }

                String fields[] = lines.get(5).split(", ");
                for(String f:fields)
                    System.out.println(f);

                reader.close();
                System.out.println("\n\nDate file read!");
            }
            else
            {
                out.println("Failed to choose a file to process");
                out.println("Run the program again!");
                System.exit(0);
            }
        }
        catch(FileNotFoundException e)
        {
            out.println("File not found!");
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return target.toFile().toString();
    }
}