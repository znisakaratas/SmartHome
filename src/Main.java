import java.util.ArrayList;
import java.util.List;
public class Main {
    public static List<Superclass> devFeauture =new ArrayList<>();
    public static String output = "";
    public static void main(String[] args) {
        String file = args[0];
        String outFile = args[1];
        String[] inputs = FileInput.readFile(file,true,true);
        Superclass.getInput(inputs);
        FileOutput.writeToFile(outFile,output,false,false);
    }
}
