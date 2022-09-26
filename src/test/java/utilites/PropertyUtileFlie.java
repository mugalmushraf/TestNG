package utilites;
import java.io.FileInputStream;
import java.util.Properties;
public class PropertyUtileFlie {
public static String getValueForKey(String key)throws Throwable{
	Properties configprop=new Properties();
	configprop.load(new FileInputStream("C:\\ECPLLISE2022\\ERP_Maven\\PropertyFiles\\Environment.properties"));
	return configprop.getProperty(key);
	
}
}
