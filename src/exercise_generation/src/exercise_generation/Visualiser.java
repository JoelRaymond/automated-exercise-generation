package exercise_generation;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;

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
        	f.write("\\begin{flushleft}");
        	f.write("Find the shortest paths, and their lengths, "
        			+ "from vertex v1 to each of the other vertices in the graph shown below.\n");
        	f.write("\\end{flushleft}");
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
        			+ ") -- node[weight] {$\\textcolor{red}{" + Integer.toString(list.get(j).weight) 
        			+ "}$} (" + Integer.toString(list.get(j).end+1) + ");\r\n");
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
	
	public static void generateString(KMP k, String filename) {
		try {
        	FileWriter f = new FileWriter(filename);
        	f.write("\\documentclass{article}\n");
        	f.write("\\begin{document}\n");
        	f.write("Construct the border table of the KMP algorithm when "
        			+ "the string being searched for equals: \n\n");
        	f.write("\\begin{center}\n");
        	f.write("\\emph{" + k.getString() + "}\n\n");
        	f.write("\\end{center}\n");
        	f.write("where the text is over the alphabet \\{A,C,G,U,T\\}.\n");
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

    public static void main(String[] args) throws IOException {
    	File tex = new File("exercises/tex");
    	FileUtils.cleanDirectory(tex);
    	File pdf = new File("exercises/pdf");
    	FileUtils.cleanDirectory(pdf);
    	
    	Scanner sc = new Scanner(System.in);
    	
    	System.out.println("How many Dijkstra graphs would you like to generate? ");
    	int dijkstraLimit = sc.nextInt();
    	
    	int v = 0;
    	int e = 0;
    	if(dijkstraLimit != 0) {
    		System.out.println("How many vertices? ");
        	v = sc.nextInt();
        	
        	System.out.println("How many edge-relaxations? ");
        	e = sc.nextInt();
    	}
    	
    	System.out.println("How many KMP strings would you like to generate? ");
    	int kmpLimit = sc.nextInt();
    	
    	int s = 0;
    	int lb = 0;
    	boolean o = false;
    	if(kmpLimit != 0) {
    		System.out.println("What size string? ");
        	s = sc.nextInt();
        	
        	System.out.println("What size largest border? ");
        	lb = sc.nextInt();
        	
        	System.out.println("Overlapping? (T/F) ");
        	while (true) {
	        	String oString = sc.nextLine();
	        	if (oString.equals("T")) {
	        		o = true;
	        		break;
	        	}
	        	else if (oString.equals("F")) {
	        		o = false;
	        		break;
	        	}
        	}
    	}
    	sc.close();
    	
    	System.out.println("Generating...");
    	
    	for(int i = 0; i <dijkstraLimit; i++) {
			Dijkstra test = new Dijkstra(v, e);
			generateGraph(test, "exercises/tex/dijkstra"+Integer.toString(i+1)+".tex");
		}
    	
    	for(int i = 0; i <kmpLimit; i++) {
			KMP test = new KMP(s, lb, o);
			generateString(test, "exercises/tex/kmp"+Integer.toString(i+1)+".tex");
		}
    	
    	System.out.println("Generation complete.");
    	
    	Desktop.getDesktop().open(pdf);
    }
}