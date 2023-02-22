import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GenerateCallGraph {

    private static Function processFunction(String name, BufferedReader br) throws IOException  {
        Function f = new Function(name);

        while (true) {
            String line = br.readLine();
            // System.out.println(line);

            if (line.equals("")) {
                // System.out.println(f);
                break;
            }

            if (line.indexOf("external") != -1) {
                continue;
            }

            int start = line.indexOf("\'");
            int end = line.lastIndexOf("\'");
            String callName = line.substring(start + 1, end);

            f.addFunctionCall(callName);
        }
        return f;
    }

    public static CallGraph generate(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));

        CallGraph callGraph = new CallGraph();
        // System.out.println("here");

        while (true) {
            String line = br.readLine();
            if (line == null) {
                break;
            }
            // System.out.println(line);

            int i = line.indexOf("null function");
            String functionName = null;

            if (i == -1) {
                // normal function
                int start = line.indexOf("\'");
                int end = line.lastIndexOf("\'");
                if (start == -1 || end == -1) {
                    continue;
                }
                functionName = line.substring(start + 1, end);
            } else {
                // null function
                functionName = "null function";
            }

            Function f = processFunction(functionName, br);
            callGraph.addFunction(f);
        }

        br.close();

        return callGraph;
    }
}