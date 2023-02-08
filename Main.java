import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        GenerateFiles.generateCallGraph("test.c", "test.bc", "callgraph.txt");
        CallGraph callGraph = GenerateCallGraph.generate("callgraph.txt");
        //System.out.println(callGraph);
        Analyzer analyzer = new Analyzer();
        analyzer.analyzeCallGraph(callGraph);
    }

}
