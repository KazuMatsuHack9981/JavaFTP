import java.io.*;

public class WriteFileTest {
    public static void main(String[] args) {
        String inputfile = "./test.html";
        String outfile = "./out.html";
        try {
            String line;
            BufferedReader reader = new BufferedReader(new FileReader(inputfile));
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(outfile)));
            while((line = reader.readLine()) != null) {
                writer.println(line);
            }
            reader.close();
            writer.close();
        } catch(FileNotFoundException e) {
            System.out.println(inputfile + "not found.");
        } catch(IOException e){
            System.out.println(e);
        }
    }
}