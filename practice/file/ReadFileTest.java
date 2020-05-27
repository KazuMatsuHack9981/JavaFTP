import java.io.*;

public class ReadFileTest {
    public static void main(String[] args) {
        String filename = args[0];
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch(FileNotFoundException e) {
            System.out.println(filename + "not found.");
        } catch(IOException e){
            System.out.println(e);
        }
    }
}