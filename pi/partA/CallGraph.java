import java.util.List;
import java.util.ArrayList;
import java.lang.StringBuilder;

public class CallGraph {
    private List<Function> functions;

    public CallGraph() {
        functions = new ArrayList<>();
    }

    public List<Function> getFunctions() {
        return functions;
    }

    public void addFunction(Function f) {
        functions.add(f);
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
}