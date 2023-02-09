package exercise_generation;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
        			+ "from vertex \\textit{$v_1$} to each of the other vertices in the graph shown below. "
        			+ "\\textit{(Weights appear off the centre point of corresponding edge.)}\n");
        	f.write("\\end{flushleft}\n");
        	
        	//adjacency matrix
        	f.write("\\begin{center}\n");
        	String c = "|c|";
        	String v = "";
        	for (int i=0; i<d.getVertices(); i++) {
        		c += "|c";
        		v += " & \\textit{$v_{" + Integer.toString(i+1) + "}$}";
        	}
        	c += "|";
        	v += "\\\\";
        	f.write("\\begin{tabular}{ " + c + "}\n");
           	f.write("\\hline\n");
           	f.write(v + "\n");
           	f.write("\\hline\\hline\n");
        	for (int i=0; i<d.getVertices(); i++) {
        		String line = "\\textit{$v_{" + Integer.toString(i+1) + "}$} & ";
        		int[] weights = new int[d.getVertices()];
        		for (Edge edge : d.getGraph().adjacencylist[i]) {
        			weights[edge.end] = edge.weight;
        		}
        		String strWeights = Arrays.toString(weights).replaceAll(",", "&");
        		strWeights = strWeights.substring(1, strWeights.length()-1);
        		line += strWeights;
        		f.write(line + "\\\\\n");
        		f.write("\\hline\n");
        	}
        	f.write("\\end{tabular}\n");
        	f.write("\\end{center}\n");
        	
        	drawGraph(f, d);
        	f.write("\\end{tikzpicture}\n");
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
	    	f.write("\\def \\radius {6.5cm}\n");
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
	    			+ ") -- node[weight] {$" + Integer.toString(list.get(j).weight) 
	    			+ "$} (" + Integer.toString(list.get(j).end+1) + ");\r\n");
	    		}
	    	}
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
        	f.write("\\end{tikzpicture}\n");
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
        	f.write("\\tikzstyle{selected vertex} = [vertex, fill=red!24]\n");
        	f.write("\\tikzstyle{selected edge} = [draw,line width=5pt,-,red!50]");
        	
        	ArrayList<Integer> S = new ArrayList<>();
        	S.add(1);
        	int curr = 1;
        	
        	int[] distances = new int[d.getVertices()];
        	String[] calculations = new String[d.getVertices()];
        	Arrays.fill(calculations, "");
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
	        	int prev = curr;
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
	        				stringDistances[i-1] + calculations[i-1] + "\\\\ \n");
	        	}
	        	f.write("\\hline\n");
	        	f.write("\\end{tabular}\n");
	        	f.write("\\end{center}\n");
	        	drawGraph(f, d);
	        	
	        	for (Integer val : S) {
	        		f.write("\\path node[selected vertex] at (" + Integer.toString(val) + 
	        				") {$v_{" + Integer.toString(val) + "}$};\n");
	        	}
	        	
	        	LinkedList<Edge> prevEdges = d.getGraph().adjacencylist[prev-1];
	        	f.write("\\begin{pgfonlayer}{background}");
	        	for (Edge e : prevEdges) {
	        		f.write("\\path [selected edge] ( " + Integer.toString(e.start+1) + ".center) -- (" +
	        				Integer.toString(e.end+1) + ".center);\n");
	        	}
	        	f.write("\\end{pgfonlayer}");
	        	
	        	f.write("\\end{tikzpicture}\n");
	        	
	        	f.write("\\begin{center}\n");
	        	f.write("Vertices explored from $v_{" + Integer.toString(prev) 
	        	+ "}$. Next vertex is $v_{" + Integer.toString(curr) + "}$. Edge relaxations shown in \\textbf{bold}.\n");
	        	f.write("\\end{center}\n");
	        	
	        	S.add(curr);
	        	
	        	Arrays.fill(calculations, "");
	        	for (Edge e : d.getGraph().adjacencylist[curr-1]) {
	        		int dest = e.end+1;
	        		if (e.end != 0) {
	        			if (distances[e.end] != 0) {
	        				if (distances[e.end] > distances[e.start]+e.weight) {
	        					calculations[e.end] = " = \\textbf{min\\{" + distances[e.end] +
	        							", " + distances[e.start] + "+" + e.weight + "\\}}";
	        					distances[e.end] = distances[e.start]+e.weight;
	        					paths[e.end] = paths[e.start] + " \\rightarrow v_{" + dest + "}";
	        				}
	        				else if (distances[e.end] < distances[e.start]+e.weight) {
	        					calculations[e.end] = " = min\\{" + distances[e.end] +
	        							", " + distances[e.start] + "+" + e.weight + "\\}";
	        				}
	        			}
	        			else {
	        				calculations[e.end] = " = min\\{$\\infty$, " + distances[e.start] + "+" + e.weight + "\\}";
	        				distances[e.end] = distances[e.start]+e.weight;
	        				paths[e.end] = paths[e.start] + " \\rightarrow v_{" + dest + "}";
	        			}
	        		}
	        	}
	        	
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
        		int borderLength = 0;
        		for (int j1=0; j1<i; j1++) {
        			b += " & \\textbf{" + Integer.toString(border[j1]) + "}";
        			
        			borderLength = border[i-1];
        			if (j1 < borderLength || j1 >= (i-borderLength)) {
        				finalS += "\\textbf{" + s.charAt(j1) + "}";
        			}
        			else {
        				finalS += s.charAt(j1);
        			}
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
            	f.write("\\begin{center}\n");
            	if (borderLength == 0) {
            		f.write("No border found.\n");
            	}
            	else {
            		f.write("A border of length \\textbf{" + borderLength + "} is found.\n");
            	}
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
    	
    	HashMap<String, Boolean> algorithms = new HashMap<String, Boolean>();
    	algorithms.put("(1) Dijkstra", false);
    	algorithms.put("(2) KMP", false);
    	
    	System.out.println("Which algorithm/s would you like to generate for? (Can choose multiple, "
    			+ "enter to confirm)");
    	System.out.println("Available algorithms: " + algorithms.keySet().toString());
    	while (true) {
        	String algString = sc.nextLine().toLowerCase();
        	switch (algString) {
        		case "1":
        		case "dijkstra":
        			algorithms.put("(1) Dijkstra", true);
        			continue;
        		case "2":
        		case "kmp":
        			algorithms.put("(2) KMP", true);
        			continue;
        		case "":
        			break;
        		default:
        			continue;
        	}
        	break;
    	}
    	
    	int dijkstraLimit = 0;	    	
    	int v = 0;
    	int e = 0;
    	if (algorithms.get("(1) Dijkstra")) {
	    	System.out.println("How many Dijkstra graphs would you like to generate? ");
	    	dijkstraLimit = sc.nextInt();
	    	if(dijkstraLimit != 0) {
	    		System.out.println("How many vertices? ");
	        	v = sc.nextInt();
	        	
	        	System.out.println("How many edge-relaxations? ");
	        	e = sc.nextInt();
	    	}
    	}
    	
    	int kmpLimit = 0;
    	int s = 0;
    	int lb = 0;
    	boolean o = false;
    	if (algorithms.get("(2) KMP")) {
	    	System.out.println("How many KMP strings would you like to generate? ");
	    	kmpLimit = sc.nextInt();
	    	
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
    	}
    	
    	sc.close();
    	
    	System.out.println("Generating...");
    	
    	if (algorithms.get("(1) Dijkstra")) {
    		int d = 0;
	    	while (d < dijkstraLimit) {
	    		try {
	    			int possibleRelaxations = Dijkstra.getRelaxations(v);
	    			if (e > possibleRelaxations) {
	    				System.out.println("Too many relaxations.");
	    				break;
	    			}
	    			Dijkstra test = new Dijkstra(v, e);
					generateGraph(test, "exercises/tex/dijkstra"+Integer.toString(d+1)+".tex", pdf);
					if (sol && !fullSol) {
						generateGraphSolution(test, "solutions/tex/dijkstra_answer"+Integer.toString(d+1)+".tex", pdf);
					}
					if (fullSol) {
						generateFullGraphSolution(test, "solutions/tex/dijkstra_answer"+Integer.toString(d+1)+".tex", pdf);
					}
					d++;
	    		}
	    		catch (Exception ex) {
	    			continue;
	    		}
			}
    	}
    	
    	if (algorithms.get("(2) KMP")) {
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