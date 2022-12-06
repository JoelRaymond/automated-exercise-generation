package exercise_generation;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import com.aspose.tex.InputFileSystemDirectory;
import com.aspose.tex.OutputFileSystemDirectory;
import com.aspose.tex.OutputMemoryTerminal;
import com.aspose.tex.TeXConfig;
import com.aspose.tex.TeXJob;
import com.aspose.tex.TeXOptions;
import com.aspose.tex.rendering.PdfDevice;
import com.aspose.tex.rendering.PdfSaveOptions;

import exercise_generation.WeightedGraph.Edge;

public class Visualiser {
	
	public static void generateGraph(Dijkstra d, String filename) {
        try {
        	FileWriter f = new FileWriter(filename);
        	f.write("\\documentclass{article}\n");
        	f.write("\\usepackage{tikz}\n");
        	f.write("\\begin{document}\n");
        	f.write("\\begin{tikzpicture}[auto]\n");
        	f.write("\\tikzstyle{vertex}=[circle,fill=black!25,minimum size=20pt,inner sep=0pt]\n");
        	f.write("\\tikzstyle{edge} = [draw,thick,-]\n");
        	f.write("\\tikzstyle{weight} = [font=\\small]\n");
        	f.write("\\def \\radius {7cm}\n");
        	int n = d.getGraph().vertices;
        	for (int i=1; i<=n; i++) {
        		f.write("\\node[vertex] (" + Integer.toString(i) 
        		+ ") at ({360/" + Integer.toString(n) + " * (" + Integer.toString(i-1) 
        		+ " )}:\\radius) {$V_{" + Integer.toString(i) + "}$};\r\n");
        	}
        	for (int i=0; i<n; i++) {
        		LinkedList<Edge> list = d.getGraph().dirAdjacencylist[i];
        		for (int j=0; j<list.size(); j++) {
        			f.write("\\path[edge] (" + Integer.toString(i+1) 
        			+ ") -- node[weight] {$" + Integer.toString(list.get(j).weight) 
        			+ "$} (" + Integer.toString(list.get(j).end+1) + ");\r\n");
        		}
        	}
        	f.write("\\end{tikzpicture}\n");
        	f.write("\\end{document}\n");
        	f.close();
        }
        catch (IOException e) {
        	System.out.println("Error");
        	e.printStackTrace();
        }
        
        TeXOptions options = TeXOptions.consoleAppOptions(TeXConfig.objectLaTeX());
        options.setInputWorkingDirectory(new InputFileSystemDirectory("exercises/tex"));
        options.setOutputWorkingDirectory(new OutputFileSystemDirectory("exercises/pdf"));
        options.setTerminalOut(new OutputMemoryTerminal());
        options.setSaveOptions(new PdfSaveOptions());
        
        new TeXJob (filename, new PdfDevice(), options).run();
	}

    public static void main(String[] args) {
    	Dijkstra test = new Dijkstra(5, 5);
    	test.getGraph().printGraph();
    	generateGraph(test, "exercises/tex/test.tex");
    }
}