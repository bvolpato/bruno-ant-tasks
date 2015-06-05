package org.brunocunha.anttasks.prop.task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.brunocunha.anttasks.prop.impl.CommentedProperties;

/**
 * Property Change task. It is responsible for changing properties file key
 * without changing undesired content
 * 
 * @author Bruno Candido Volpato da Cunha
 *
 */
public class PropChangeTask extends Task {

	private File file;
	private String prop;
	private String value;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.tools.ant.Task#execute()
	 */
	public void execute() throws BuildException {

		if (file == null || !file.exists()) {
			throw new BuildException("File '" + file + "' doesn't exist.");
		}

		try {
			CommentedProperties properties = new CommentedProperties();
			FileInputStream in = new FileInputStream(file);
			properties.load(in);
			in.close();

			properties.put(prop, value);

			FileOutputStream os = new FileOutputStream(file);
			properties.store(os, "");
			os.close();
		} catch (Exception e) {
			throw new BuildException("Error executing property changes", e);
		}
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getProp() {
		return prop;
	}

	public void setProp(String prop) {
		this.prop = prop;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
