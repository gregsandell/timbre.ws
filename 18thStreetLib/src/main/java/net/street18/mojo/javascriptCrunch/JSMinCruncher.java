package net.street18.mojo.javascriptCrunch;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class JSMinCruncher {
	// private static final String tempFileName = "_tempJs_";
	/**
	 * If you want files to be altered in place, leave outputDir blank.  To instead
	 * write them to another directory, give the directory name here with a trailing
	 * "/" here.  (Such as "output/")
	 */
	private static final String outputDir = "uncrunched/";
	private boolean debug = false;

	public void process(String path, boolean debug) throws CruncherException {
		this.debug = debug;
		try {
			/*  Accumulate list of files to alter */
			path = path.trim();
			if (path.charAt(path.length() - 1) != '/') {
				path += "/";
			}
			File directory = new File(path);
			if (this.debug) System.out.println("path is " + directory.getAbsolutePath());
			String[] fileNames = directory.list();
			List<String> jsFiles = new ArrayList<String>();
			FileOutputStream targetOutputStream = null;
			File outputDirFile = new File(path + outputDir);
			if (!outputDirFile.exists()) {
				if (this.debug) System.out.println("Creating directory '" + outputDirFile.getAbsolutePath() + "'");
				outputDirFile.mkdir();
			}
			for (int i = 0; i < fileNames.length; i++) {
				String[] splitFile = fileNames[i].split("\\.");
				if (splitFile.length < 2) continue;
				if (splitFile[splitFile.length - 1].equals("js")) {
					jsFiles.add(fileNames[i]);
					if (this.debug) System.out.println("Adding file " + i + ", '" + fileNames[i] + "' to list of files to crunch");
					File originalFile = new File(path + fileNames[i]);
					File safeCopy = new File(path + outputDir + fileNames[i]);
					FileUtils.copyFile(originalFile, safeCopy);
				}
			}

			/* Process all files */
			for (int i = 0; i < jsFiles.size(); i++) {
				/* erase targetFile file */
				String sourceFilename = jsFiles.get(i);
				String targetFilename = path + sourceFilename;
				File targetFile = new File(targetFilename);
				System.out.println("Crunching file " + i + " of " + jsFiles.size() + ", '" + sourceFilename + "'");
				if (targetFile.exists()) {
					if (this.debug) System.out.println(targetFile.getAbsolutePath() + " exists, deleting");
					deleteFile(targetFile);
				}

				/* Transform current file, write it to the targetFile file */
				FileInputStream sourceInputStream = new FileInputStream(path + outputDir + sourceFilename);
				targetOutputStream = new FileOutputStream(targetFilename);
				JSMin jsMin = new JSMin(sourceInputStream, targetOutputStream);
				jsMin.jsmin();
				targetOutputStream.close();
				targetOutputStream = null;
				sourceInputStream.close();
				sourceInputStream = null;

			}
		}
			catch (FileNotFoundException e) {
				throw new CruncherException("", e);
			}
			catch (IOException e) {
				throw new CruncherException("", e);
			}
			catch (Exception e) {
				throw new CruncherException("", e);
			}
	}
	private void deleteFile(File temp) throws CruncherException {
		if (this.debug) System.out.println("Deleting file '" + temp.getAbsolutePath() + "'");
		if (!temp.delete()) {
			throw new CruncherException("File '" + temp.getName() + "' could not be deleted");
		}
	}
	private void moveFile(File temp, File newFile) throws MojoExecutionException {
		if (this.debug) System.out.println("Moving file from '" + temp.getName() + "' to '" + newFile.getAbsolutePath() + "'");
		if (!temp.renameTo(newFile)) {
			throw new MojoExecutionException("File '" + temp.getName() + "' could not be renamed to '" + newFile.getName() + "'");
		}
	}
	public static void main(String[] argv) {
		JSMinCruncher jsMinCruncher = new JSMinCruncher();
		try {
				jsMinCruncher.process("c:\\downloads\\dojo-rhino", true);
		}
			catch (CruncherException e) {
				System.out.println("Error:  " + e.getMessage());
				e.printStackTrace();	//To change body of catch statement use File | Settings | File Templates.
			}
	}
}
