/**
 * Copyright (C) 2015 Bruno Candido Volpato da Cunha (brunocvcunha@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
 * @author Bruno Candido Volpato da Cunha
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
