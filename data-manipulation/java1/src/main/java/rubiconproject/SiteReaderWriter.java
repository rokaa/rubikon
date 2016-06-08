package rubiconproject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import rubiconproject.model.SiteIn;
import rubiconproject.model.SiteOut;

public class SiteReaderWriter {
	
	/**
	 * Read SiteIn objects from CSV or JSON file 
	 * and write to generator as SiteCollection objects
	 * @param fl
	 * @param generator
	 * @param jFac
	 * @throws JsonGenerationException
	 * @throws IOException
	 */
	public void doIOFromFile(File fl, JsonGenerator generator, JsonFactory jFac)
			throws JsonGenerationException, IOException {
		SiteReaderWriter srw = new SiteReaderWriter();
		generator.writeStartObject();
		generator.writeStringField("collectionId", fl.getName());
		generator.writeFieldName("sites");
		generator.writeStartArray();
		if (fl.getName().equals("input2.json")) {
			srw.doIOSiteFromJson(fl, generator, jFac);
		} else {
			srw.doIOSiteFromCsv(fl, generator);
		}
		generator.writeEndArray();
		generator.writeEndObject();
		generator.writeRaw('\n');
	}
	
	/**
	 * Read list of SiteIn objects from JSON file
	 * and write as SiteOut objects to generator
	 * @param fl - JSON file
	 * @param generator - generator to write to
	 * @param jFac
	 * @throws JsonParseException
	 * @throws IOException
	 */
	public void doIOSiteFromJson(File fl, JsonGenerator generator, JsonFactory jFac) 
			throws JsonParseException, IOException {
		JsonParser jp = jFac.createJsonParser(fl);
		JsonToken current = jp.nextToken();
		SiteOut sout = new SiteOut();
		if (current == JsonToken.START_ARRAY) {
			while (jp.nextToken() != JsonToken.END_ARRAY) {
				SiteIn s = jp.readValuesAs(SiteIn.class).next();
				sout.siteCopy(s);
				sout.setKeywords(sout.genKeywords(s));
				generator.writeObject(sout);
			}
		}
	}

	/**
	 * Read SiteIn objects from CSV file
	 * and write as SiteOut objects to generator
	 * @param fl - CSV file
	 * @param generator - generator to write to
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void doIOSiteFromCsv(File fl, JsonGenerator generator) throws FileNotFoundException, IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(fl.getAbsolutePath()))) {
			SiteOut sout = new SiteOut();
			String line = "";
			String splitChar = ",";
			// read out meta-data
			br.readLine();
			line = br.readLine();
			while (line != null && line.length() > 0) {
				String[] row = line.split(splitChar);
				sout.arrayCopy(row);
				sout.setKeywords(sout.genKeywords(sout));
				generator.writeObject(sout);
				line = br.readLine();
			}
		}
	}

}
