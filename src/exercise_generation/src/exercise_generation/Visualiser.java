package exercise_generation;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
	
	public static void generateGraph(Dijkstra d, String filename, boolean pdf) {
        try {
        	FileWriter f = new FileWriter(filename);
        	f.write("\\documentclass{article}\n");
        	f.write("\\usepackage{tikz}\n");
        	f.write("\\begin{document}\n");
        	f.write("\\begin{flushleft}\n");
        	f.write("Find the shortest paths, and their lengths, "
        			+ "from vertex \\textit{$v_1$} to each of the other vertices in the graph shown below.\n");
        	f.write("\\end{flushleft}\n");
        	drawGraph(f, d);
        	f.write("\\end{document}\n");
        	f.close();
        }
        catch (IOException e) {
        	System.out.println("Error");
        	e.printStackTrace();
        }
        
        if (pdf) {
	        TeXOptions options = TeXOptions.consoleAppOptions(TeXConfig.objectLaTeX());
	        options.setInputWorkingDirectory(new InputFileSystemDirectory("exercises/tex"));
	        options.setOutputWorkingDirectory(new OutputFileSystemDirectory("exercises/pdf"));
	        options.setTerminalOut(new OutputMemoryTerminal());
	        options.setSaveOptions(new PdfSaveOptions());
	        
	        new TeXJob (filename, new PdfDevice(), options).run();
        }
	}
	
	public static FileWriter drawGraph(FileWriter f, Dijkstra d) {
		try {
			f.write("\\begin{tikzpicture}[auto]\n");
	    	f.write("\\tikzstyle{vertex}=[circle,fill=black!25,minimum size=20pt,inner sep=0pt]\n");
	    	f.write("\\tikzstyle{edge} = [draw,thick,-]\n");
	    	f.write("\\tikzstyle{weight} = [font=\\small]\n");
	    	f.write("\\def \\radius {7cm}\n");
	    	int n = d.getGraph().vertices;
	    	for (int i=1; i<=n; i++) {
	    		f.write("\\node[vertex] (" + Integer.toString(i) 
	    		+ ") at ({360/" + Integer.toString(n) + " * (" + Integer.toString(i-1) 
	    		+ " )}:\\radius) {$v_{" + Integer.toString(i) + "}$};\r\n");
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
		}
		catch (IOException e) {
        	System.out.println("Error");
        	e.printStackTrace();
        }
    	return f;
	}
	
	public static void generateGraphSolution(Dijkstra d, String filename, boolean pdf) {
		try {
        	FileWriter f = new FileWriter(filename);
        	f.write("\\documentclass{article}\n");
        	f.write("\\usepackage{tikz}\n");
        	f.write("\\begin{document}\n");
        	drawGraph(f, d);
        	f.write("\\begin{flushleft}\n");
        	f.write("\\textbf{Solution}: These following distances are found, by Dijkstra's algorithm.\n");
        	f.write("\\end{flushleft}\n");
        	f.write("\\begin{center}\n");
        	f.write("\\begin{tabular}{| c c c |}\n");
        	f.write("\\hline\n");
        	f.write("vertex & shortest path & length \\\\\n");
        	f.write("\\hline\\hline\n");
        	for (int i=2; i<=d.getVertices(); i++) {
        		f.write("{$v_{" + Integer.toString(i) + "}$} & $" + 
        				d.getShortestPaths().get(i-1) + "$ & " + 
        				Integer.toString(d.getShortestDistance().get(i-1)) + "\\\\ \n");
        	}
        	f.write("\\hline\n");
        	f.write("\\end{tabular}\n");
        	f.write("\\end{center}\n");
        	f.write("\\end{document}\n");
        	f.close();
        }
        catch (IOException e) {
        	System.out.println("Error");
        	e.printStackTrace();
        }
        
		if (pdf) {
	        TeXOptions options = TeXOptions.consoleAppOptions(TeXConfig.objectLaTeX());
	        options.setInputWorkingDirectory(new InputFileSystemDirectory("solutions/tex"));
	        options.setOutputWorkingDirectory(new OutputFileSystemDirectory("solutions/pdf"));
	        options.setTerminalOut(new OutputMemoryTerminal());
	        options.setSaveOptions(new PdfSaveOptions());
	        
	        new TeXJob (filename, new PdfDevice(), options).run();
		}
	}
	
	public static void generateFullGraphSolution(Dijkstra d, String filename, boolean pdf) {
		try {
        	FileWriter f = new FileWriter(filename);
        	f.write("\\documentclass{article}\n");
        	f.write("\\usepackage{tikz}\n");
        	f.write("\\usepackage{verbatim}\n");
        	f.write("\\usetikzlibrary{arrows,shapes}\n");
        	f.write("\\begin{document}\n");
        	f.write("\\pgfdeclarelayer{background}\n");
        	f.write("\\pgfsetlayers{background,main}\n");
        	f.write("\\tikzstyle{vertex}=[circle,fill=black!25,minimum size=20pt,inner sep=0pt]\n");
        	f.write("\\tikzstyle{selected vertex} = [vertex, fill=red!24]\n");
        	f.write("\\tikzstyle{edge} = [draw,thick,-]\n");
        	f.write("\\tikzstyle{weight} = [font=\\small]\n");
        	f.write("\\tikzstyle{selected edge} = [draw,line width=5pt,-,red!50]");
        	f.write("\\begin{flushleft}\n");
        	f.write("\\textbf{Solution}: These following distances are found, by Dijkstra's algorithm.\n");
        	f.write("\\end{flushleft}\n");
        	
        	ArrayList<Integer> S = new ArrayList<>();
        	S.add(1);
        	int curr = 1;
        	
        	int[] distances = new int[d.getVertices()];
        	String[] paths = new String[d.getVertices()];
        	for (Edge e : d.getGraph().adjacencylist[0]) {
        		distances[e.end] = e.weight;
        		int dest = e.end + 1;
        		paths[e.end] = "v_{1} \\rightarrow v_{" + dest + "}";
        	}
        	
        	for (int c=1; c<d.getVertices()+1; c++) {
	        	f.write("\\begin{center}\n");
	        	f.write("\\begin{tabular}{| c c c |}\n");
	        	f.write("\\hline\n");
	        	f.write("vertex & shortest path & length \\\\\n");
	        	f.write("\\hline\\hline\n");
	        	String[] stringDistances = new String[d.getVertices()];
	        	stringDistances[0] = "0";
	        	int min = Integer.MAX_VALUE;
	        	for (int i=1; i<stringDistances.length; i++) {
	        		if(distances[i] == 0) {
	        			stringDistances[i] = "$\\infty$";
	        		}
	        		else {
	        			stringDistances[i] = Integer.toString(distances[i]);
	        		}
	        		
	        		if (distances[i] < min && !S.contains(i+1) && distances[i] > 0) {
	        			min = distances[i];
	        			curr = i+1;
	        		}
	        	}
	        	for (int i=2; i<=d.getVertices(); i++) {
	        		f.write("{$v_{" + Integer.toString(i) + "}$} & $" + 
	        				paths[i-1] + "$ & " + 
	        				stringDistances[i-1] + "\\\\ \n");
	        	}
	        	f.write("\\hline\n");
	        	f.write("\\end{tabular}\n");
	        	f.write("\\end{center}\n");
	        	f.write("\\begin{tikzpicture}[auto]\n");
	        	f.write("\\def \\radius {7cm}\n");
	        	int n = d.getGraph().vertices;
	        	for (int i=1; i<=n; i++) {
	        		f.write("\\node[vertex] (" + Integer.toString(i) 
	        		+ ") at ({360/" + Integer.toString(n) + " * (" + Integer.toString(i-1) 
	        		+ " )}:\\radius) {$v_{" + Integer.toString(i) + "}$};\r\n");
	        	}
	        	for (int i=0; i<n; i++) {
	        		LinkedList<Edge> list = d.getGraph().dirAdjacencylist[i];
	        		for (int j=0; j<list.size(); j++) {
	        			f.write("\\path[edge] (" + Integer.toString(i+1) 
	        			+ ") -- node[weight] {$\\textcolor{red}{" + Integer.toString(list.get(j).weight) 
	        			+ "}$} (" + Integer.toString(list.get(j).end+1) + ");\r\n");
	        		}
	        	}
	        	
	        	for (Integer val : S) {
	        		f.write("\\path node[selected vertex] at (" + Integer.toString(val) + 
	        				") {$v_{" + Integer.toString(val) + "}$};\n");
	        	}
	        	S.add(curr);
	        	
	        	for (Edge e : d.getGraph().adjacencylist[curr-1]) {
	        		int dest = e.end+1;
	        		if (e.end != 0) {
	        			if (distances[e.end] != 0) {
	        				if (distances[e.end] > distances[e.start]+e.weight) {
	        					distances[e.end] = distances[e.start]+e.weight;
	        					paths[e.end] = paths[e.start] + " \\rightarrow v_" + dest;
	        				}
	        			}
	        			else {
	        				distances[e.end] = distances[e.start]+e.weight;
	        				paths[e.end] = paths[e.start] + " \\rightarrow v_" + dest;
	        			}
	        		}
	        	}
	        	
	        	f.write("\\end{tikzpicture}\n");
	        	f.write("\\pagebreak\n");
        	}
        	f.write("\\end{document}\n");
        	f.close();
        }
        catch (IOException e) {
        	System.out.println("Error");
        	e.printStackTrace();
        }
        
		if (pdf) {
	        TeXOptions options = TeXOptions.consoleAppOptions(TeXConfig.objectLaTeX());
	        options.setInputWorkingDirectory(new InputFileSystemDirectory("solutions/tex"));
	        options.setOutputWorkingDirectory(new OutputFileSystemDirectory("solutions/pdf"));
	        options.setTerminalOut(new OutputMemoryTerminal());
	        options.setSaveOptions(new PdfSaveOptions());
	        
	        new TeXJob (filename, new PdfDevice(), options).run();
		}
	}
	
	public static void generateString(KMP k, String filename, boolean pdf) {
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
        
		if (pdf) {
	        TeXOptions options = TeXOptions.consoleAppOptions(TeXConfig.objectLaTeX());
	        options.setInputWorkingDirectory(new InputFileSystemDirectory("exercises/tex"));
	        options.setOutputWorkingDirectory(new OutputFileSystemDirectory("exercises/pdf"));
	        options.setTerminalOut(new OutputMemoryTerminal());
	        options.setSaveOptions(new PdfSaveOptions());
	        
	        new TeXJob (filename, new PdfDevice(), options).run();
		}
	}
	
	public static void generateStringSolution(KMP k, String filename, boolean pdf) {
		try {
        	FileWriter f = new FileWriter(filename);
        	f.write("\\documentclass{article}\n");
        	f.write("\\begin{document}\n");
        	f.write("\\textbf{Solution}: The border table is given by:\n");

        	f.write("\\begin{center}\n");
        	f.write(k.getString() + "\n");
        	String c = "|c";
        	String j = "j";
        	String b = "B(j)";
        	int border[] = k.constructBorderTable();
        	for(int i=0; i<k.getLength(); i++) {
        		c += "|c";
        		j += " & " + Integer.toString(i);
        		b += " & " + Integer.toString(border[i]);
        	}
        	f.write("\\begin{tabular}{" + c + "|}\n");
        	f.write("\\hline\n");
        	f.write(j + " \\\\\n");
        	f.write("\\hline\n");
        	f.write(b + " \\\\\n");
        	f.write("\\hline\n");
        	f.write("\\end{tabular}\n");
        	f.write("\\end{center}\n");
        	f.write("\\end{document}\n");
        	f.close();
        }
        catch (IOException e) {
        	System.out.println("Error");
        	e.printStackTrace();
        }
        
		if (pdf) {
	        TeXOptions options = TeXOptions.consoleAppOptions(TeXConfig.objectLaTeX());
	        options.setInputWorkingDirectory(new InputFileSystemDirectory("solutions/tex"));
	        options.setOutputWorkingDirectory(new OutputFileSystemDirectory("solutions/pdf"));
	        options.setTerminalOut(new OutputMemoryTerminal());
	        options.setSaveOptions(new PdfSaveOptions());
	        
	        new TeXJob (filename, new PdfDevice(), options).run();
		}
	}
	
	public static void generateFullStringSolution(KMP k, String filename, boolean pdf) {
		try {
        	FileWriter f = new FileWriter(filename);
        	f.write("\\documentclass{article}\n");
        	f.write("\\usepackage{xcolor}\n");
        	f.write("\\begin{document}\n");
        	f.write("\\textbf{Solution}: The border table is built as follows:\n");
        	f.write("\\\\\n");
        	String c = "|c";
        	String j = "j";
        	int border[] = k.constructBorderTable();
        	for(int i=0; i<k.getLength(); i++) {
        		c += "|c";
        		j += " & " + Integer.toString(i);
        	}
        	
        	for (int i=0; i<k.getLength(); i++) {
        		f.write(Integer.toString(i+1) + ".");
        		String b = "B(j)";
        		String s = k.getString();
        		String finalS = "\\textcolor{red}{";
        		for (int j1=0; j1<i; j1++) {
        			b += " & " + Integer.toString(border[j1]);
        			finalS += s.charAt(j1);
        		}
        		finalS += "}";
        		for (int j1=i; j1<k.getLength(); j1++) {
        			b += " & 0"; 
        			finalS += s.charAt(j1);
        		}
        		f.write("\\begin{center}\n");
        		f.write(finalS + "\n");
            	f.write("\\begin{tabular}{" + c + "|}\n");
            	f.write("\\hline\n");
            	f.write(j + " \\\\\n");
            	f.write("\\hline\n");
            	f.write(b + " \\\\\n");
            	f.write("\\hline\n");
            	f.write("\\end{tabular}\n");
            	f.write("\\\\\n");
            	f.write("\\end{center}\n");
        	}
        	
        	f.write("\\end{document}\n");
        	f.close();
        }
        catch (IOException e) {
        	System.out.println("Error");
        	e.printStackTrace();
        }
        
		if (pdf) {
	        TeXOptions options = TeXOptions.consoleAppOptions(TeXConfig.objectLaTeX());
	        options.setInputWorkingDirectory(new InputFileSystemDirectory("solutions/tex"));
	        options.setOutputWorkingDirectory(new OutputFileSystemDirectory("solutions/pdf"));
	        options.setTerminalOut(new OutputMemoryTerminal());
	        options.setSaveOptions(new PdfSaveOptions());
	        
	        new TeXJob (filename, new PdfDevice(), options).run();
		}
	}

    public static void main(String[] args) throws IOException {
    	File exTex = new File("exercises/tex");
    	exTex.mkdirs();
    	FileUtils.cleanDirectory(exTex);
    	File exPdf = new File("exercises/pdf");
    	exPdf.mkdirs();
    	FileUtils.cleanDirectory(exPdf);
    	File solTex = new File("solutions/tex");
    	solTex.mkdirs();
    	FileUtils.cleanDirectory(solTex);
    	File solPdf = new File("solutions/pdf");
    	solPdf.mkdirs();
    	FileUtils.cleanDirectory(solPdf);
    	
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
        	
        	System.out.println("Overlapping? (t/f) ");
        	while (true) {
	        	String oString = sc.nextLine();
	        	if (oString.equals("t")) {
	        		o = true;
	        		break;
	        	}
	        	else if (oString.equals("f")) {
	        		o = false;
	        		break;
	        	}
        	}
    	}
    	
    	boolean sol = false;
    	boolean fullSol = false;
    	System.out.println("Would you like to generate solutions? (y/n)");
    	while (true) {
        	String solString = sc.nextLine();
        	if (solString.equals("y")) {
        		sol = true;
        		
            	System.out.println("Would you like to generate step-by-step solutions? (y/n)");
            	while (true) {
                	String fullSolString = sc.nextLine();
                	if (fullSolString.equals("y")) {
                		fullSol = true;
                		break;
                	}
                	else if (fullSolString.equals("n")) {
                		fullSol = false;
                		break;
                	}
            	}
        		break;
        	}
        	else if (solString.equals("n")) {
        		sol = false;
        		break;
        	}
    	}
    	
    	boolean pdf = false;
    	System.out.println("Would you like to render to PDF? (y/n)");
    	while (true) {
        	String pdfString = sc.nextLine();
        	if (pdfString.equals("y")) {
        		pdf = true;
        		break;
        	}
        	else if (pdfString.equals("n")) {
        		pdf = false;
        		break;
        	}
    	}
    	
    	sc.close();
    	
    	System.out.println("Generating...");
    	
    	for(int i = 0; i <dijkstraLimit; i++) {
			Dijkstra test = new Dijkstra(v, e);
			generateGraph(test, "exercises/tex/dijkstra"+Integer.toString(i+1)+".tex", pdf);
			if (sol && !fullSol) {
				generateGraphSolution(test, "solutions/tex/dijkstra_answer"+Integer.toString(i+1)+".tex", pdf);
			}
			if (fullSol) {
				generateFullGraphSolution(test, "solutions/tex/dijkstra_answer"+Integer.toString(i+1)+".tex", pdf);
			}
		}
    	
    	for(int i = 0; i <kmpLimit; i++) {
			KMP test = new KMP(s, lb, o);
			generateString(test, "exercises/tex/kmp"+Integer.toString(i+1)+".tex", pdf);
			if (sol && !fullSol) {
				generateStringSolution(test, "solutions/tex/kmp_answer"+Integer.toString(i+1)+".tex", pdf);
			}
			if (fullSol) {
				generateFullStringSolution(test, "solutions/tex/kmp_answer"+Integer.toString(i+1)+".tex", pdf);
			}
		}
    	
    	System.out.println("Generation complete.");
    	
    	if (pdf) {
    		Desktop.getDesktop().open(exPdf);
    	}
    	
    	if (sol) {
    		Desktop.getDesktop().open(solPdf);
    	}
    }
}