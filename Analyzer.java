import java.util.HashMap;
import java.util.List;

public class Analyzer {
    private HashMap<String, Integer> statistics;
    private HashMap<String, Integer> pairs;
    private static final int T_SUPPORT = 3;
    private static final double T_CONFIDENCE = 0.65;

    public Analyzer() {
        statistics = new HashMap<>();
        pairs = new HashMap<>();
    }

    private String hashPair(String a, String b) {
        if (a.compareTo(b) <= 0) return a+"#"+b;
        else return b+"#"+a;
    }

    public void analyzeCallGraph(CallGraph callGraph) {
        for (Function func : callGraph.getFunctions()) {
            if(func.getName().equals("null function")) continue;

            for (String funcCall : func.getFunctionCalls()) {
                statistics.put(funcCall, statistics.getOrDefault(funcCall, 0)+1);
            }
        }
        System.out.println(statistics);

        for (Function func: callGraph.getFunctions()) {
            if(func.getName().equals("null function")) continue;

            List<String> funcCalls = func.getFunctionCalls();

            for (int i = 0; i < funcCalls.size(); i++) {
                for (int j = i+1; j < funcCalls.size(); j++) {
                    if (i == j) continue;
                    
                    String hashKey = hashPair(funcCalls.get(i), funcCalls.get(j));

                    pairs.put(hashKey, pairs.getOrDefault(hashKey, 0)+1);
                }
            }
        }
        System.out.println(pairs);
    }
}
