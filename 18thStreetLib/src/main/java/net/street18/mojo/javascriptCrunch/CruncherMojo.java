package net.street18.mojo.javascriptCrunch;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * Minimizes all the javascript files (*.js) in the given directory, putting
 * the result in a folder called 'output'.
 * @goal crunch
*/
public class CruncherMojo extends AbstractMojo {
	private JSMinCruncher jsMinCruncher = new JSMinCruncher();
	/**
	 * The directory of javascript files.
	 * @parameter default-value="."
	 */
		private String path;
	/**
	 * The directory of javascript files.
	 * @parameter default-value="false"
	 */
		private String debug;
			public void execute() throws MojoExecutionException {

		try {
				jsMinCruncher.process(path, debug.equals("true") ? true : false);
		}
		 	catch (CruncherException e) {
				 throw new MojoExecutionException(e.getMessage());
		 }
	 }
}
