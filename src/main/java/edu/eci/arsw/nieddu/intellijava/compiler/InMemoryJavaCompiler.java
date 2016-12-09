package edu.eci.arsw.nieddu.intellijava.compiler;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.util.Arrays;

/**
 * Created by trung on 5/3/15.
 */
public class InMemoryJavaCompiler {
    private JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
    
    private StringWriter resultado;
    private PrintWriter writer;
    private String className, source;
    
    public InMemoryJavaCompiler(String className, String source) {
        resultado = new StringWriter();
        writer = new PrintWriter(resultado);
        this.className = className;
        this.source = source;
    }

    public String compile() throws ClassNotFoundException, URISyntaxException {
        SourceCode sourceCode = new SourceCode(className, source);
        CompiledCode compiledCode = new CompiledCode(className);
        Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(sourceCode);
        DynamicClassLoader cl = new DynamicClassLoader(ClassLoader.getSystemClassLoader());
        ExtendedStandardJavaFileManager fileManager = new ExtendedStandardJavaFileManager(javac.getStandardFileManager(null, null, null), compiledCode, cl);
        JavaCompiler.CompilationTask task = javac.getTask(writer, fileManager, null, null, null, compilationUnits);
        boolean result = task.call();
        //return cl.loadClass(className);
        return resultado.toString();
    }
    
    public String getResult(){
        return resultado.toString();
    }
}
