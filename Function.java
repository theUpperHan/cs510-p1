import java.util.List;
import java.util.ArrayList;

public class Function {
    private String name;
    private List<String> functionCalls;

    public Function() {
        functionCalls = new ArrayList<>();
    }

    public Function(String name) {
        this.name = name;
        functionCalls = new ArrayList<>();
    }

    public void addFunctionCall(String name) {
        functionCalls.add(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFunctionCalls() {
        return name;
    }

    @Override
    public String toString() {
        return "{ name: " + name + ", functionCalls: " + functionCalls + " }";
    }
}