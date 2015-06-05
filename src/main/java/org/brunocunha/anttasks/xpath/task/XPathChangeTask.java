package org.brunocunha.anttasks.xpath.task;

import java.io.File;

import javax.xml.xpath.XPathExpressionException;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.brunocunha.anttasks.xpath.impl.XPathTools;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Task that changes a node in a XML Document, using XPath to select parent node
 * 
 * @author bruno.cunha
 *
 */
public class XPathChangeTask extends Task {

	private XPathTools tools = new XPathTools();

	private File file;
	private String node;
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

			Document doc = tools.doChange(file, node, value);
			tools.saveDocument(doc, file);

		} catch (XPathExpressionException e) {
			throw new BuildException("XPath Clause Invalid", e);
		} catch (SAXException e) {
			throw new BuildException("Error parsing XML file", e);
		} catch (Exception e) {
			throw new BuildException("Error executing XPath changes", e);
		}
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
