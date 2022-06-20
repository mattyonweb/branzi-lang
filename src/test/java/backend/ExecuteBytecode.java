package backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Executes a .class file given a classpath.
 */
public class ExecuteBytecode {
    private String classpath;

    public ExecuteBytecode(String classpath) {
        this.classpath = classpath;
    }

    public List<String> run(String className) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(
                "java", "-cp", this.classpath, className
        );
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();
        process.waitFor();

        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        String line;
        List<String> out = new ArrayList<>();

        while((line =br.readLine())!=null) {
            out.add(line);
            System.out.println(line);
        }

        return out;
    }


}
