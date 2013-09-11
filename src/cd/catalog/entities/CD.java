package cd.catalog.entities;

public class CD {
	private static final String CSV_DELIMITER = ",";
	private String cdLabel;
	private String series;
	private String serial;
	private String performer;
	private String composer;
	private String freeText;
	
	public CD() {
		
	}
	
	public String getCdLabel() {
		return cdLabel;
	}
	public void setCdLabel(String cdLabel) {
		this.cdLabel = cdLabel;
	}
	public String getSeries() {
		return series;
	}
	public void setSeries(String series) {
		this.series = series;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public String getPerformer() {
		return performer;
	}
	public void setPerformer(String performer) {
		this.performer = performer;
	}
	public String getComposer() {
		return composer;
	}
	public void setComposer(String composer) {
		this.composer = composer;
	}
	public String getFreeText() {
		return freeText;
	}
	public void setFreeText(String freeText) {
		this.freeText = freeText;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cdLabel == null) ? 0 : cdLabel.hashCode());
		result = prime * result + ((serial == null) ? 0 : serial.hashCode());
		result = prime * result + ((series == null) ? 0 : series.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CD other = (CD) obj;
		if (cdLabel == null) {
			if (other.cdLabel != null)
				return false;
		} else if (!cdLabel.equals(other.cdLabel))
			return false;
		if (serial == null) {
			if (other.serial != null)
				return false;
		} else if (!serial.equals(other.serial))
			return false;
		if (series == null) {
			if (other.series != null)
				return false;
		} else if (!series.equals(other.series))
			return false;
		return true;
	}
	
	public void setFromCSV() {
		
	}
	
	public String formatToCsv() {
		StringBuilder builder = new StringBuilder();
		
		builder.append(getCdLabel()).append(CSV_DELIMITER)
			   .append(getSeries()).append(CSV_DELIMITER)
			   .append(getSerial()).append(CSV_DELIMITER)
			   .append(getPerformer()).append(CSV_DELIMITER)
			   .append(getComposer()).append(CSV_DELIMITER)
			   .append(getFreeText());
		
		return builder.toString();
	}
	
	
	
	@Override
	public String toString() {
		return "CD [cdLabel=" + cdLabel + ", series=" + series + ", serial="
				+ serial + ", performer=" + performer + ", composer="
				+ composer + ", freeText=" + freeText + "]";
	}

	public CD cloneCD() {
		CD cd = new CD();

		cd.setCdLabel(getCdLabel());
		cd.setSeries(getSeries());
		cd.setSerial(getSerial());
		cd.setPerformer(getPerformer());
		cd.setComposer(getComposer());
		cd.setFreeText(getFreeText());
		
		return cd;
	}
}
