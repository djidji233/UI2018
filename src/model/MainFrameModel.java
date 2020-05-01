package model;

public class MainFrameModel {
	
	public static final String 	imgLocation = "images/";
    public static final String 	imgsufix =".png";
    
    
    private Database activeDatabase;
    
    
    public Database getActiveDatabase() {
		return activeDatabase;
	}


	public void setActiveDatabase(Database activeDatabase) {
		this.activeDatabase = activeDatabase;
	}


	public static String getImagePath(String file) {
    	return imgLocation+file+imgsufix;
    }
    

}
