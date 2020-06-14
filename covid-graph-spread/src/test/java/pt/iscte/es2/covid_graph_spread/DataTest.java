package pt.iscte.es2.covid_graph_spread;

import java.util.Date;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class DataTest {

	@Test
	void testConstructor() {
		Data data = null;
		Assert.assertNull(data);
		
		data = new Data("fileName", "fileTag", "tagDescription", "spreadVisualizationLink", new Date());
		Assert.assertNotNull(data);
	}
	
	@Test
	void testGetters() {
		String fileName = "fileName";
		String fileTag = "fileTag";
		String tagDescription = "tagDescription";
		String spreadVisualizationLink = "spreadVisualizationLink";
		Date date = new Date();
		
		Data data = new Data(fileName, fileTag, tagDescription, spreadVisualizationLink, date);
		Assert.assertNotNull(data);
		
		Assert.assertEquals(fileName, data.getFileName());
		Assert.assertEquals(fileTag, data.getFileTag());
		Assert.assertEquals(tagDescription, data.getTagDescription());
		Assert.assertEquals(spreadVisualizationLink, data.getSpreadVisualizationLink());
		Assert.assertEquals(date, data.getDate());
	}
	
	@Test
	void testToString() {
		String fileName = "fileName";
		String fileTag = "fileTag";
		String tagDescription = "tagDescription";
		String spreadVisualizationLink = "spreadVisualizationLink";
		Date date = new Date();
		
		Data data = new Data(fileName, fileTag, tagDescription, spreadVisualizationLink, date);
		Assert.assertNotNull(data);
		
		String expectedToString = "Data [fileName=" + fileName + ", fileTag=" + fileTag + ", tagDescription=" + tagDescription
		+ ", spreadVisualizationLink=" + spreadVisualizationLink + ", date=" + date + "]";
		
		Assert.assertEquals(expectedToString, data.toString());
	}

}
