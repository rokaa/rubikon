package rubiconproject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingJsonFactory;

/**
 * Assumptions made: 
 * [true = 1, false = 0]
 * [score is within Integer range]
 * [input files can be big, so i used streaming API of Jackson library, fastest one]
 * [preference were given to performance during implementation, not readability]
 * [-----output all objects from JSON file as one JSON object with collectionId = input2.json, 
 * not stated clearly in the problem how to output. 
 * One might infer that each 2 sites should be as one JSON object]
 * [-----output all objects from CSV file as one JSON object with collectionId = input1.csv, 
 * not stated clearly in the problem how to output. 
 * One might infer that each 2 sites should be as one JSON object]
 */
public class GeneratorMain {
	/**
	 * keywords generator
	 */
	private static KeywordServiceImpl keyGen = new KeywordServiceImpl();

	public static void main(String[] args) {
		BufferedReader br = null;
		JsonGenerator generator = null;
		JsonFactory f = new MappingJsonFactory();
		try {
			generator = f.createJsonGenerator(new File(args[1]), JsonEncoding.UTF8);
			File folder = new File(args[0]);
			File[] files = folder.listFiles();
			for (File fl : files) {
				if (fl.getName().equals("input2.json")) {
					generateFromJson(generator, f, fl);
				} else if (fl.getName().equals("input1.csv")) {
					generateFromCsv(generator, br, fl);
				}
			}
		} catch (JsonParseException e) {
			e.printStackTrace();

		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeResources(br, generator);
		}
	}

	/**
	 * Generate output file from JSON file
	 * 
	 * @param generator
	 * @param f
	 * @param fl
	 * @throws JsonGenerationException
	 * @throws IOException
	 */
	private static void generateFromJson(JsonGenerator generator, JsonFactory f, File fl)
			throws JsonGenerationException, IOException {
		generator.writeStartObject();
		generator.writeStringField("collectionId", fl.getName());
		generator.writeFieldName("sites");
		generator.writeStartArray();
		JsonParser jp = f.createJsonParser(fl);
		JsonToken current = jp.nextToken();
		if (current == JsonToken.START_ARRAY) {
			while (jp.nextToken() != JsonToken.END_ARRAY) {
				// {"site_id": "13000", "name": "example.com/json1",
				// "mobile": 1, "score": 21 } - format
				JsonNode node = jp.readValueAsTree();
				generator.writeStartObject();
				generator.writeStringField("id", node.get("site_id").asText());
				generator.writeStringField("name", node.get("name").asText());
				// assuming mobile value is within Integer range
				generator.writeNumberField("mobile", node.get("mobile").asInt());
				// passing node object to assumed function
				generator.writeStringField("keywords", keyGen.resolveKeywords(node));
				// assuming score value is within Integer range.
				generator.writeNumberField("score", node.get("score").asInt());
				generator.writeEndObject();
			}
		}
		generator.writeEndArray();
		generator.writeEndObject();
		generator.writeRaw('\n');
	}

	/**
	 * Generate output file from CSV file
	 * 
	 * @param generator
	 * @param br
	 * @param fl
	 * @throws JsonGenerationException
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	private static void generateFromCsv(JsonGenerator generator, BufferedReader br, File fl)
			throws JsonGenerationException, IOException {
		generator.writeStartObject();
		generator.writeStringField("collectionId", fl.getName());
		generator.writeFieldName("sites");
		generator.writeStartArray();
		// id,name,is mobile,score - format
		String line = "";
		String splitChar = ",";
		br = new BufferedReader(new FileReader(fl.getAbsolutePath()));
		// read meta-data
		br.readLine();
		line = br.readLine();
		// handle '\n' at the end of file
		// by checking length of line
		while (line != null && line.length() > 0) {
			String[] row = line.split(splitChar);
			generator.writeStartObject();
			generator.writeStringField("id", row[0]);
			generator.writeStringField("name", row[1]);
			// I am assuming that 1 - true, 0 - false.
			if (row[2].equals("true")) {
				generator.writeNumberField("mobile", 1);
			} else if (row[2].equals("false")) {
				generator.writeNumberField("mobile", 0);
			} else {
				// this case just in case, we write -1
				generator.writeNumberField("mobile", -1);
			}
			// passing node object to assumed function
			generator.writeStringField("keywords", keyGen.resolveKeywords(line));
			// assuming score value is within Integer range.
			generator.writeNumberField("score", Integer.parseInt(row[3]));
			generator.writeEndObject();
			line = br.readLine();
		}
		generator.writeEndArray();
		generator.writeEndObject();
		generator.writeRaw('\n');

	}

	/**
	 * Close all resources
	 * 
	 * @param br
	 * @param generator
	 */
	private static void closeResources(BufferedReader br, JsonGenerator generator) {
		if (br != null) {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (generator != null) {
			try {
				generator.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
