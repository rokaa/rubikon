package rubiconproject;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.MappingJsonFactory;

public class JsonIO {

	/**
	 * Generate JSON file of SiteCollection objects from files. 
	 * One object per line.
	 * @param dirName - the directory to read input files
	 * @param fileOut - the name of the output file
	 */
	public void generateJsonSiteFile(String dirName, String fileOut) {
		SiteReaderWriter srw = new SiteReaderWriter();
		JsonFactory jFac = new MappingJsonFactory();
		try (JsonGenerator generator = jFac.createJsonGenerator(new File(fileOut), JsonEncoding.UTF8)) {
			String[] filesToParse = new String[2];
			filesToParse[0] = dirName + "/" + "input2.json";
			filesToParse[1] = dirName + "/" + "input1.csv";
			for (String fName : filesToParse) {
				srw.doIOFromFile(new File(fName), generator, jFac);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
