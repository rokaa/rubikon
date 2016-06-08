package rubiconproject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.MappingJsonFactory;

import rubiconproject.model.SiteIn;
import rubiconproject.model.SiteOut;

public class JsonIO {

	private KeywordServiceImpl keyGen = new KeywordServiceImpl();

	/**
	 * Generate file with JSON objects
	 * @param dirName - the directory to read input files
	 * @param fileOut - the name of the output file
	 */
	public void generateJsonFile(String dirName, String fileOut) {
		JsonFactory jFac = new MappingJsonFactory();
		try (JsonGenerator generator = jFac.createJsonGenerator(new File(fileOut), JsonEncoding.UTF8)) {
			String[] filesToParse = new String[2];
			filesToParse[0] = dirName + "/" + "input2.json";
			filesToParse[1] = dirName + "/" + "input1.csv";
			for (String fName : filesToParse) {
				generateJsonFileHelper(new File(fName), generator, jFac);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Helper to generateJsonFile
	 * @param fl
	 * @param generator
	 * @param jFac
	 * @throws JsonGenerationException
	 * @throws IOException
	 */
	private void generateJsonFileHelper(File fl, JsonGenerator generator, JsonFactory jFac)
			throws JsonGenerationException, IOException {
		generator.writeStartObject();
		generator.writeStringField("collectionId", fl.getName());
		generator.writeFieldName("sites");
		generator.writeStartArray();
		SiteOut sout = new SiteOut();
		if (fl.getName().equals("input2.json")) {
			readObjectsFromJson(fl, generator, jFac, sout);
		} else {
			readObjectsFromCsv(fl, generator, sout);
		}
		generator.writeEndArray();
		generator.writeEndObject();
		generator.writeRaw('\n');
	}
	
	/**
	 * Read objects from JSON file
	 * @param fl
	 * @param generator
	 * @param jFac
	 * @param sout
	 * @throws JsonParseException
	 * @throws IOException
	 */
	private void readObjectsFromJson(File fl, JsonGenerator generator, JsonFactory jFac, SiteOut sout) 
			throws JsonParseException, IOException {
		JsonParser jp = jFac.createJsonParser(fl);
		JsonToken current = jp.nextToken();
		if (current == JsonToken.START_ARRAY) {
			while (jp.nextToken() != JsonToken.END_ARRAY) {
				SiteIn s = jp.readValuesAs(SiteIn.class).next();
				sout.siteCopy(s);
				sout.setKeywords(keyGen.resolveKeywords(s));
				generator.writeObject(sout);
			}
		}
	}

	/**
	 * Read objects from CSV file
	 * @param fl
	 * @param generator
	 * @param sout
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void readObjectsFromCsv(File fl, JsonGenerator generator, SiteOut sout) throws FileNotFoundException, IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(fl.getAbsolutePath()))) {
			String line = "";
			String splitChar = ",";
			// read out meta-data
			br.readLine();
			line = br.readLine();
			while (line != null && line.length() > 0) {
				String[] row = line.split(splitChar);
				sout.arrayCopy(row);
				sout.setKeywords(keyGen.resolveKeywords(sout));
				generator.writeObject(sout);
				line = br.readLine();
			}
		}
	}

}
