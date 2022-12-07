package exercise_generation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class KMP {
	private int length;
	private int largestBorder;
	private Boolean overlapping;
	private List<Character> alphabet;
	private String string;
	
	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getLargestBorder() {
		return largestBorder;
	}

	public void setLargestBorder(int largestBorder) {
		this.largestBorder = largestBorder;
	}

	public Boolean getOverlapping() {
		return overlapping;
	}

	public void setOverlapping(Boolean overlapping) {
		this.overlapping = overlapping;
	}

	public List<Character> getAlphabet() {
		return alphabet;
	}

	public void setAlphabet(List<Character> alphabet) {
		this.alphabet = alphabet;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public KMP(int length, int largestBorder, Boolean overlapping) {
		if (length > 0) {
			this.length = length;
		}
		else {
			throw new IllegalArgumentException("Invalid string length.");
		}
		
		if (largestBorder > length-1) {
			throw new IllegalArgumentException("Largest border too long.");
		}
		else if ((largestBorder > (length-1)/2) && !overlapping) {
			this.largestBorder = largestBorder;
			this.overlapping = true;
		}
		else {
			this.largestBorder = largestBorder;
			this.overlapping = overlapping;
		}
		
		this.alphabet = generateAlphabet();
		this.string = generateString();
	}
	
	public List<Character> generateAlphabet() {
		char[] alphabet = {'A', 'C', 'G', 'U', 'T'};
		List<Character> result = new ArrayList<>();
		
		int counter = 0;
		while (counter < 3) {
			char random = alphabet[ThreadLocalRandom.current().nextInt(0, alphabet.length)];
			if (result.contains(random)) {
				continue;
			}
			else {
				result.add(random);
				counter++;
			}
		}
		Collections.sort(result);
		return result;
	}
	
	public String generateString() {
		String[] result = new String[this.length];
		String longestBorder = "";
		int borderStart;
		if (this.overlapping) {
			int overlapLength;
			if (this.largestBorder > (this.length-1)/2) {
				overlapLength = ThreadLocalRandom.current().nextInt(this.largestBorder-(this.length-1)/2, (this.largestBorder-1)/2);
			}
			else {
				int upper = (int) (Math.ceil((this.largestBorder-1)/2.0));
				overlapLength = ThreadLocalRandom.current().nextInt(1, upper);
			}
			int count = 0;
			String overlap = "";
			for (int i=0; i < overlapLength; i++) {
				char random = getRandomChar();
				overlap += random;
				result[count] = Character.toString(random);
				count++;
			}
			
			String middle = "";
			longestBorder += overlap;
			for (int i=0; i < this.largestBorder-(2*overlapLength); i++) {
				char random = getRandomChar();
				middle += random;
				result[count] = Character.toString(random);
				count++;
			}
			longestBorder += middle;
			longestBorder += overlap;
			
			for (int i = 0; i < overlapLength; i++) {
				result[count] = Character.toString(overlap.charAt(i));
				count++;
			}
			
			for (int i = 0; i < middle.length(); i++) {
				result[count] = Character.toString(middle.charAt(i));
				count++;
			}
			
			for (int i = 0; i < overlapLength; i++) {
				result[count] = Character.toString(overlap.charAt(i));
				count++;
			}
			
			borderStart = overlapLength*3 + middle.length()*2;
		}
		else {
			borderStart = ThreadLocalRandom.current().nextInt(this.largestBorder, this.length-this.largestBorder);
			for (int i=0; i < this.largestBorder; i++) {
				char random = getRandomChar();
				longestBorder += random;
				
				String c = Character.toString(random);
				result[i] = c;
				result[borderStart] = c;
				borderStart++;
			}
		}
		
		String afterFirst;
		if (result[this.largestBorder] == null) {
			afterFirst = Character.toString(getRandomChar());
			result[this.largestBorder] = afterFirst;
		}
		else {
			afterFirst = result[this.largestBorder];
		}
		
		while (true) {
			String afterSecond = Character.toString(getRandomChar());
			if (!afterSecond.equals(afterFirst)) {
				result[borderStart] = afterSecond;
				break;
			}
		}
		
		for (int i=this.largestBorder; i < result.length; i++) {
			if (result[i] == null) {
				result[i] = Character.toString(getRandomChar());
			}
		}
		
		String string = String.join("", result);
		int index = 0;
		while (true) {
			index = string.indexOf(longestBorder, index+1);
			if (index != borderStart - this.largestBorder && index != -1) {
				while (true) {
					String ran = Character.toString(getRandomChar());
					if (!ran.equals(result[index])) {
						result[index] = ran;
						break;
					} 
				}
			}
			else {
				break;
			}
		}
		return String.join("", result);
	}
	
	public char getRandomChar() {
		return this.alphabet.get(ThreadLocalRandom.current().nextInt(0, this.alphabet.size()));
	}
	
	public static void main(String[] args) {
		for(int i = 0; i <1; i++) {
			KMP test = new KMP(13, 4, false);
			System.out.println(test.alphabet);
		}
	}
}
