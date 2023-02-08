import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

public class Analyzer {
    private static final int T_SUPPORT = 3;
    private static final double T_CONFIDENCE = 0.65;
    private static final String DELIMITER = "#";
    
    private Map<String, Double> individuals;
    private Map<String, Double> pairs;
    private Map<String, List<String>> scopes;
    private List<String> potentialBugs;

    public Analyzer() {
        individuals = new HashMap<>();
        pairs = new HashMap<>();
        scopes = new HashMap<>();
        potentialBugs = new ArrayList<String>();
    }

    private String hashPair(String a, String b) {
        if (a.compareTo(b) <= 0) return a+DELIMITER+b;
        else return b+"#"+a;
    }

    private void checkValidBug(String ind_name, double pair_support, String scope, String pair) {
        double confidence = pair_support / individuals.get(ind_name);
        if (confidence >= T_CONFIDENCE) {
            String pair_members[] = pair.split(DELIMITER);
            String reformated_pair = String.format("(%s, %s)", pair_members[0], pair_members[1]);
            potentialBugs.add(String.format("Bug %s in %s, pair %s, support: %.0f, confidence: %.2f%%",
                                            ind_name, scope, reformated_pair, pair_support, confidence*100));
        }
    }

    private void populateMaps(CallGraph callGraph) {
        for (Function func : callGraph.getFunctions()) {
            // Pass null and main
            String current_function = func.getName();
            if(current_function.equals("null function") || (current_function.equals("main")))
                continue;
            
            List<String> func_calls = func.getFunctionCalls();

            //Only keep worth-analyze scopes
            if (func_calls.size() > 1) {
                scopes.put(current_function, func_calls);
            }
            
            for (int i = 0; i < func_calls.size(); i++) {
                String current_func = func_calls.get(i);
                //caculate individual support
                individuals.put(current_func, individuals.getOrDefault(current_func, 0.0)+1);
                for (int j = i+1; j < func_calls.size(); j++) {
                    //calculate pair support
                    String hashKey = hashPair(func_calls.get(i), func_calls.get(j));
                    pairs.put(hashKey, pairs.getOrDefault(hashKey, 0.0)+1);
                }
            }
        }
    }

    

    public void analyzeCallGraph(CallGraph callGraph) {
        populateMaps(callGraph);

        System.out.println(individuals);
        System.out.println(pairs);
        System.out.println(scopes);

        for (Map.Entry<String, Double> pair_entry : pairs.entrySet()) {
            if (pair_entry.getValue() >= T_SUPPORT) {
                //Extract members
                String pair_members[] = pair_entry.getKey().split(DELIMITER);
                
                //Check each scope for bugs
                for (Map.Entry<String, List<String>> scope_entry : scopes.entrySet()) {
                    List<String> scope_members = scope_entry.getValue();

                    //If only contains one of the members in pair
                    if (scope_members.contains(pair_members[0]) ^ scope_members.contains(pair_members[1])) {
                        
                        if (scope_members.contains(pair_members[0])) {
                            checkValidBug(pair_members[0], pair_entry.getValue(), scope_entry.getKey(), pair_entry.getKey());
                        }
                        else {
                            checkValidBug(pair_members[1], pair_entry.getValue(), scope_entry.getKey(), pair_entry.getKey());
                        }
                    }
                }
            }
        }
        
    }
}
