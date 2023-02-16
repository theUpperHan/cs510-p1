import java.io.File;
import java.io.IOException;

public class GenerateFiles {
    private static void generateBitCode(String c_file, String bc_file) {
        // Argument validation
        if (c_file.isEmpty() || bc_file.isEmpty()) {
            System.out.println("Missing file name(s).\n");
            return;
        }
        if (! new File(c_file).isFile()) {
            System.out.println("Source file not found");
        }

        // Call clang to generate bitcode file
        String clang_cmd = String.format("clang -emit-llvm %s -c -o %s", c_file, bc_file);
        try {
            Process p = Runtime.getRuntime().exec(new String[]{"bash","-c", clang_cmd});
            p.waitFor();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void generateCallGraph(String bc_file, String call_graph) {
        // generateBitCode(c_file, bc_file);

        String opt_cmd = String.format("opt -print-callgraph %s 2> %s", bc_file, call_graph);
        try {
            Process p = Runtime.getRuntime().exec(new String[]{"bash","-c", opt_cmd});
            p.waitFor();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
