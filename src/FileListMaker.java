import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import static java.lang.System.out;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardOpenOption.CREATE;
import java.util.ArrayList;
import javax.swing.JFileChooser;
public class FileListMaker
{
    public static void main(String[] args)
    {
        JFileChooser chooser = new JFileChooser();
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
    }
}
