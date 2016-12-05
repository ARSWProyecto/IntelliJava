package edu.eci.arsw.nieddu.intellijava.compiler;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.util.Arrays;

/**
 * Created by trung on 5/3/15.
 */
public class InMemoryJavaCompiler {
    static JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
    public static StringWriter RESULTADO = new StringWriter();
    public static PrintWriter PRESULTADO = new PrintWriter(RESULTADO);

    public static Class<?> compile(String className, String sourceCodeInText) throws ClassNotFoundException, URISyntaxException {
        SourceCode sourceCode = new SourceCode(className, sourceCodeInText);
        CompiledCode compiledCode = new CompiledCode(className);
        Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(sourceCode);
        DynamicClassLoader cl = new DynamicClassLoader(ClassLoader.getSystemClassLoader());
        ExtendedStandardJavaFileManager fileManager = new ExtendedStandardJavaFileManager(javac.getStandardFileManager(null, null, null), compiledCode, cl);
        JavaCompiler.CompilationTask task = javac.getTask(RESULTADO, fileManager, null, null, null, compilationUnits);
        
        boolean result = task.call();
        return cl.loadClass(className);
    }
}
