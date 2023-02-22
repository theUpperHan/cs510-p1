import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length != 1 && args.length != 3) {
            System.out.println("Usage: ./pipair [bitcode file]\n       or\n       ./pipair [bitcode file] [T_SUPPORT] [T_CONFIDENCE]");
            return;
        }

        Analyzer analyzer = new Analyzer();

        if (args.length == 3) {
            try {
                analyzer.setSupport(Integer.parseInt(args[1]));
                analyzer.setConfidence(Double.parseDouble(args[2]) / 100);
                // System.out.println(Double.parseDouble(args[2]) / 100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        GenerateFiles.generateCallGraph(args[0], "callgraph.txt");
        CallGraph callGraph = GenerateCallGraph.generate("callgraph.txt");

        // System.out.println(callGraph);

        analyzer.analyzeCallGraph(callGraph);
        for (String s : analyzer.getPotentialBugs()) {
            System.out.println(s);
        }
    }

}
