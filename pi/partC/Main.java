import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length != 1 && args.length != 4) {
            System.out.println("Usage: ./pipair [bitcode file]\n       or\n       ./pipair [bitcode file] [T_SUPPORT] [T_CONFIDENCE] [EXPAND_LEVEL]");
            return;
        }

        Analyzer analyzer = new Analyzer();

        int expandLevel = 1;
        if (args.length == 4) {
            try {
                analyzer.setSupport(Integer.parseInt(args[1]));
                analyzer.setConfidence(Double.parseDouble(args[2]) / 100);
                expandLevel = Integer.parseInt(args[3]);
                // System.out.println(Double.parseDouble(args[2]) / 100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        GenerateFiles.generateCallGraph(args[0], "callgraph.txt");
        CallGraph callGraph = GenerateCallGraph.generate("callgraph.txt");

        callGraph.expand(expandLevel);

        // System.out.println(callGraph);

        analyzer.analyzeCallGraph(callGraph);
        for (String s : analyzer.getPotentialBugs()) {
            System.out.println(s);
        }
    }

}
