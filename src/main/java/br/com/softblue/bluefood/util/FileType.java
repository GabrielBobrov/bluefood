package br.com.softblue.bluefood.util;

public enum FileType {

	PNG("image/png", "png"),
	JPG("image/jpeg", "jpg");
	
	String mimeType;
	String extension;
	
	FileType(String mimeType, String extension) {//mimetype exemplo : image/png, auxilia a definir a extensao
		this.mimeType = mimeType;
		this.extension = extension;
	}
	
	public String getExtension() {
		return extension;
	}
	
	public String getMimeType() {
		return mimeType;
	}
	
	public boolean sameOf(String mimeType) {
		return this.mimeType.equalsIgnoreCase(mimeType);
	}
	
	public static FileType of(String mimeType) {
		for (FileType fileType : values()) {
			if (fileType.sameOf(mimeType)) {
				return fileType;
			}
		}
		
		return null;
	}
}
