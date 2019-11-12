
package pl.B4GU5.Controllers;

public class modsListClass {
	
	int id;
    String name;
    Boolean addon;
    String md5;

    public modsListClass() {
        
    }

    public modsListClass(int id, String name, String addon, String md5) {
    	this.id = id;
        this.name = name;
        this.addon = new Boolean(addon);
        this.md5 = md5;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAddon() {
        return addon;
    }

    public void setAddon(String addon) {
        this.addon = new Boolean(addon);
    }

    public String getHash() {
        return md5;
    }

    public void setHash(String md5) {
        this.md5 = md5;
    }

}
