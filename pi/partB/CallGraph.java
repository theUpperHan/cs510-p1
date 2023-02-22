import java.util.List;
import java.util.ArrayList;
import java.lang.StringBuilder;
import java.util.Map;
import java.util.HashMap;

public class CallGraph {
    private List<Function> functions;
    private Map<String, List<String>> functionsMap;

    public CallGraph() {
        functions = new ArrayList<>();
        functionsMap = new HashMap<>();
    }

    public List<Function> getFunctions() {
        return functions;
    }

    public void addFunction(Function f) {
        functions.add(f);
        functionsMap.put(f.getName(), f.getFunctionCalls());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        for (Function f : functions) {
            sb.append("  ");
            sb.append(f.toString());
            sb.append("\n");
        }
        sb.append("}");
        return sb.toString();
    }

    public void expand() {
        for (Function func_entry : functions) {
            List<String> expanded = new ArrayList<>(); //expanded function calls
            
            for (String func : func_entry.getFunctionCalls()) {
                if (functionsMap.get(func).size() > 0) { //if expandable
                    expanded.addAll(functionsMap.get(func));
                }
                else {
                    expanded.add(func);
                }
            }

            func_entry.setFunctionCalls(expanded);
        }
    }
}