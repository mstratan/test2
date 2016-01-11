/**
 * 
 */
package md.stratan.eye;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * This class is used for Read/Write values in properties file
 * @author Manet Yim (manet.yim at gmail dot com)
 */
public class PropertiesIO {
 
    private String propertiesFile = "conf";
    private Properties p;
 
    /**
     * Initialize this class and load properties file at the same time
     * @param file to load
     */
    public PropertiesIO(){
        this.p = new Properties();
        this.loadProperties();
    }
 
    /**
     * Load content of properties file into memory
     */
    public void loadProperties(){
        
            try {
				p.load(new FileInputStream(propertiesFile));
			} catch (FileNotFoundException e) {
				try {
					p.load(this.getClass().getResourceAsStream("/default"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
        
    }
 
    /**
     * Read value that matched the key
     * @param key use to search in properties file
     * @return value that matched key
     */
    public String readProperty(String key){
        return p.getProperty(key);
    }
 
    /**
     * Write key/value pair into properties file
     * @param key
     * @param value
     */
    public void writeProperty(String key, String value){
        p.setProperty(key, value);
        try {
            p.store(new FileOutputStream(propertiesFile), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
