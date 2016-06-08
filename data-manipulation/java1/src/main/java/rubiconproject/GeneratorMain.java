package rubiconproject;

/**
 * Assumptions made:
 * [first argument - path to directory, second argument - name of output file]
 * [true = 1, false = 0]
 * [score is within Integer range]
 * [input files can be big, so i used streaming API of Jackson library, fastest one]
 * [preference were given to performance during implementation, readability considered]
 * [-----output all objects from JSON file as one JSON object with collectionId = input2.json, 
 * not stated clearly in the problem how to output. 
 * One might infer that each 2 sites should be as one JSON object]
 * [-----output all objects from CSV file as one JSON object with collectionId = input1.csv, 
 * not stated clearly in the problem how to output. 
 * One might infer that each 2 sites should be as one JSON object]
 */
public class GeneratorMain {

	public static void main(String[] args) {
		JsonIO js = new JsonIO();
		js.generateJsonSiteFile(args[0], args[1]);	
	}
}
