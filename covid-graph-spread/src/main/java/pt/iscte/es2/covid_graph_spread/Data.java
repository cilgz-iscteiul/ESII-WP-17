package pt.iscte.es2.covid_graph_spread;

import java.util.Date;

/**
 * 
 * @author Catarina Lopes Garcez
 *
 */
public class Data {
	
	/*
	 * fileName - Nome do ficheiro
	 */
	private String fileName;
	
	/**
	 * fileTag - Nome da tag associada ao ficheiro
	 */
	private String fileTag;
	
	/**
	 * tagDescription - Descricao da tag associada ao ficheiro
	 */
	private String tagDescription;
	
	/**
	 * spreadVisualizationLink - Link para a plataforma de visualizacao
	 */
	private String spreadVisualizationLink;
	
	/**
	 * date - Data do commit
	 */
	private Date date;
	
	/**
	 * Cria uma nova instancia da classe Data
	 * 
	 * @param fileName - Nome do ficheiro
	 * @param fileTag - Nome da tag associada ao ficheiro
	 * @param tagDescription - Descricao da tag associada ao ficheiro
	 * @param spreadVisualizationLink - Link para a plataforma de visualizacao
	 * @param date - Data do commit
	 */
	public Data(String fileName, String fileTag, String tagDescription, String spreadVisualizationLink, Date date) {
		super();
		this.fileName = fileName;
		this.fileTag = fileTag;
		this.tagDescription = tagDescription;
		this.spreadVisualizationLink = spreadVisualizationLink;
		this.date = date;
	}

	/**
	 * Devolve o nome do ficheiro
	 * 
	 * @return nome do ficheiro
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Devolve o nome da tag associada ao ficheiro
	 * 
	 * @return nome da tag
	 */
	public String getFileTag() {
		return fileTag;
	}

	/**
	 * Devolve a descricao da tag associada ao ficheiro
	 * 
	 * @return descricao da tag
	 */
	public String getTagDescription() {
		return tagDescription;
	}

	/**
	 * Devolve o link de visualizacao
	 * 
	 * @return link de visualizacao 
	 */
	public String getSpreadVisualizationLink() {
		return spreadVisualizationLink;
	}

	/**
	 * Devolve a data do commit
	 * 
	 * @return data do commit
	 */
	public Date getDate() {
		return date;
	}

	@Override
	public String toString() {
		return "Data [fileName=" + fileName + ", fileTag=" + fileTag + ", tagDescription=" + tagDescription
				+ ", spreadVisualizationLink=" + spreadVisualizationLink + ", date=" + date + "]";
	}
}
