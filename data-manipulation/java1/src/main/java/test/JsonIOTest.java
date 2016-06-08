package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import rubiconproject.SiteReaderWriter;
import rubiconproject.model.SiteIn;

public class JsonIOTest {
	
	static JsonFactory jFac;
	JsonGenerator generator;
	SiteReaderWriter srw = new SiteReaderWriter();
	ObjectMapper mapper = new ObjectMapper();
	
	File dir, out, input;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		jFac = new MappingJsonFactory();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		dir = new File("temp");
		dir.mkdir();
		out = new File(dir.getAbsolutePath() + "/rubik");
		generator = jFac.createJsonGenerator(out, JsonEncoding.UTF8);
		input = new File(dir.getAbsolutePath() + "/test");
		
	}

	@After
	public void tearDown() throws Exception {
		if(generator != null){
			try{
				generator.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		out.delete();
		input.delete();
		dir.delete();
	}

	private void populateSite(SiteIn s) {
		s.setId("1");
		s.setMobile(1);
		s.setName("example.com");
		s.setScore(100);
	}
	
	//-----------Tests of "doIOSiteFromCsv" function---------------
	//--Name of tests are self-explanatory I hope
	@Test
	public void testdoIOSiteFromCsvEmptyFile() throws FileNotFoundException, IOException {
		input.createNewFile();
		srw.doIOSiteFromCsv(input, generator);
		assertEquals(0, out.length());
	}
	
	@Test
	public void testdoIOSiteFromCsvOneRow() throws JsonGenerationException, JsonMappingException, IOException {
		fail("Not yet implemented");
	}

	@Test
	public void testdoIOSiteFromCsvMultipleRows() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testdoIOSiteFromCsvImproperFormat() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testdoIOSiteFromCsvJustMeta() {
		fail("Not yet implemented");
	}
	
	@Test(expected = Exception.class)
	//more specific exception can be added
	public void testdoIOSiteFromCsvOnException() {
		fail("Not yet implemented");
	}
	
	//-----------Tests of "doIOSiteFromJson" function---------------
	//--Name of tests are self-explanatory i hope
	@Test
	public void testdoIOSiteFromJsonEmptyFile(){
		fail("Not yet implemented");
	}
	
	@Test
	public void testdoIOSiteFromJsonEmptyList(){
		fail("Not yet implemented");
	}
	
	@Test
	public void testdoIOSiteFromJsonOneRowInList(){
		fail("Not yet implemented");
	}
	
	@Test
	public void testdoIOSiteFromJsonMultipleRowsInList(){
		fail("Not yet implemented");
	}
	
	@Test
	public void testdoIOSiteFromJsonImproperFormat(){
		fail("Not yet implemented");
	}
	
	@Test(expected = Exception.class)
	//more specific exception can be added
	public void testdoIOSiteFromJsonException(){
		fail("Not yet implemented");
	}
	
	//-----------Tests of "testdoIOFromFile" function---------------
	//--Name of tests are self-explanatory i hope
	@Test
	public void testdoIOFromFileEmptyFile(){
		fail("Not yet implemented");
	}
	
	@Test
	public void testdoIOFromFileOneRowJson(){
		fail("Not yet implemented");
	}
	
	@Test
	public void testdoIOFromFileOneRowCsv(){
		fail("Not yet implemented");
	}
	
	@Test
	public void testdoIOFromFileMultipleRowCsv(){
		fail("Not yet implemented");
	}
	
	@Test
	public void testdoIOFromFileMultipleRowJson(){
		fail("Not yet implemented");
	}
	
	@Test
	public void testdoIOFromFileImproperRow(){
		fail("Not yet implemented");
	}
	
	@Test(expected = Exception.class)
	//more specific exception can be added
	public void testdoIOFromFileException(){
		fail("Not yet implemented");
	}

}



