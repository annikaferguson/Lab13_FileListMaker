import java.awt.*;
import java.lang.reflect.Array;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class Main {
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        ArrayList<String> arrList = new ArrayList<>();

        String switch1 = "";
        boolean rerun = true;
        boolean needsToBeSaved = false;
        String name = "";

        do {
            switch1 = Menu(in, arrList);
            switch(switch1) {
                case "A":
                    addToList(in, arrList);
                    needsToBeSaved = true;
                    break;
                case "C":
                    clearList(arrList);
                    needsToBeSaved = true;
                    break;
                case"D":
                    deleteFromList(in, arrList);
                    needsToBeSaved = true;
                    break;
                case "O":
                    name = Open(in, arrList, needsToBeSaved);
                    break;
                case "S":
                    saveFile(arrList, name);
                    needsToBeSaved = false;
                    break;
                case "V":
                    Display(arrList);
                    break;
                case"Q":
                    if(SafeInput.getYNConfirm(in, "Are you sure? List will be lost"))
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

    private static String Menu(Scanner in, ArrayList arrList)
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
        return SafeInput.getRegExString(in, "Select a meny option:\n   A: Add\n   C: Clear\n   D: Delete\n   O: Open\n   S: Save\n   V: View\n   Q: Quit\n", "[AaCcDdOoSsVvQq").toUpperCase();
    }

    public static void clearList(ArrayList arrList)
    {
        arrList.clear();
    }

    public static void addToList(Scanner in, ArrayList arrList)
    {
        String itemToAdd = SafeInput.getNonZeroLenString(in, "What do you want to add to the array?");
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

    public static void deleteFromList(Scanner in, ArrayList arrList)
    {
        int itemToDelete = SafeInput.getRangedInt(in, "What item do you want to delete", 1, arrList.size());
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

    private static String Open (Scanner in, ArrayList arrList, boolean needsToSaved)
    {
        if(needsToSaved)
        {
            String prompt = "New List loading, current list will be deleted";
            boolean burnListYN = SafeInput.getYNConfirm(in, prompt);
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

        try {
            if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                target = chooser.getSelectedFile().toPath();
                inFile = new Scanner(target);

                System.out.println("Opening File: " + target.getFileName());

                while(inFile.nextLine())
                {
                    row = inFile.nextLine();
                    arrList.add(row);
                }
                inFile.close();

            } else {
                System.out.println("Please select file");
            }
        } catch (IOException e)
        {
            System.out.println("Error");
        }

        return target.toFile().toString();
    }
}