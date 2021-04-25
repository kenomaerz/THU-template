/**
 * 
 */
package util;

/**
 * @author Keno Maerz
 * 
 * This configuration file contains information that should not go to the repository.
 * It is ignored in git, so your changes won't be committed.
 * 
 * To use it: Copy this file (The original should rename, otherwise you could 
 * accidentally remove it from git!), then rename the copy to "Config.java" and
 * replace the empty strings with the correct values.
 * You should now have two files: Config.txt and Config.java. The latter is ignored,
 * and changes there will not be tracked by git.
 * 
 * DO NOT COMMIT THE TOKEN OR THE REDCAP API URL! I'LL HAVE TO CHANGE THEM FOR EVERYONE!
 *
 * In case you want to add your own secret entries, add them to the config.txt,
 * then people from you project can pull the new Config.txt and copy the new lines to their
 * Config.java.
 *
 */
 
public class Config {
	public static final String REDCAP_TOKEN = "";
	public static final String REDCAP_API_URL = "";
	public static final String SERVER_BASE_URL = "http://localhost:8080/";
}
