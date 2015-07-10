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
package org.brunocunha.anttasks.xpath.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.tools.ant.filters.StringInputStream;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Some boilerplate code to work with XPath
 * 
 * @author Bruno Candido Volpato da Cunha
 *
 */
public class XPathTools {

	/**
	 * Effectively changes a node with a value
	 * 
	 * @param file
	 *            XML File to Change
	 * @param node
	 *            Node selector
	 * @param value
	 *            New file
	 * @return The {@link Document} instance
	 * @throws XPathExpressionException
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 * @throws FileNotFoundException
	 * @throws Exception
	 */
	public Document doChange(File file, String node, String value)
			throws XPathExpressionException, FileNotFoundException,
			SAXException, IOException, ParserConfigurationException {

		Document doc = getDocument(new FileInputStream(file));
		XPath xpath = XPathFactory.newInstance().newXPath();

		NodeList nodes = (NodeList) xpath.evaluate(node, doc,
				XPathConstants.NODESET);

		for (int idx = 0; idx < nodes.getLength(); idx++) {
			nodes.item(idx).setTextContent(value);
		}

		return doc;
	}

	/**
	 * Gets a value by XPath Node
	 * 
	 * @param file
	 *            XML File
	 * @param node
	 *            Node selector
	 * @return Value in {@link String}
	 * @throws XPathExpressionException
	 * @throws FileNotFoundException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public String getNodeValue(File file, String node)
			throws XPathExpressionException, FileNotFoundException,
			SAXException, IOException, ParserConfigurationException {
		Document doc = getDocument(new FileInputStream(file));
		XPath xpath = XPathFactory.newInstance().newXPath();

		return (String) xpath.evaluate(node, doc, XPathConstants.STRING);
	}

	/**
	 * Gets the document
	 * 
	 * @param is
	 *            InputStream to Read
	 * @return {@link Document}
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public Document getDocument(InputStream is) throws SAXException,
			IOException, ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory
				.newInstance();
		docFactory.setValidating(false);
		docFactory.setNamespaceAware(true);
		docFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		docFactory.setFeature("http://xml.org/sax/features/validation", false);
		docFactory
				.setFeature(
						"http://apache.org/xml/features/nonvalidating/load-dtd-grammar",
						false);
		docFactory
				.setFeature(
						"http://apache.org/xml/features/nonvalidating/load-external-dtd",
						false);

		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		Document doc = docBuilder.parse(is);

		return doc;
	}

	/**
	 * Save document to file system
	 * 
	 * @param doc
	 *            {@link Document} instance
	 * @param file
	 *            {@link File} to save
	 * @throws TransformerException
	 * @throws IOException
	 */
	public void saveDocument(Document doc, File file)
			throws TransformerException, IOException {
		docToStream(doc, new FileWriter(file));
	}

	/**
	 * Gets XML Document string representation
	 * 
	 * @param doc
	 *            {@link Document} instance
	 * @return String representation
	 * @throws TransformerException
	 */
	public String getDocumentString(Document doc) throws TransformerException {
		StringWriter sw = new StringWriter();
		docToStream(doc, sw);

		return sw.toString();
	}

	/**
	 * Write document to stream
	 * 
	 * @param doc
	 *            {@link Document} instance
	 * @param os
	 * @throws TransformerException
	 */
	public void docToStream(Document doc, Writer os)
			throws TransformerException {
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);

		StreamResult result = new StreamResult(os);
		transformer.transform(source, result);
	}

	/**
	 * Adds a node element as a child of another node
	 * 
	 * @param doc
	 *            {@link Document} instance
	 * @param nodeStr
	 *            Parent node selector
	 * @param el
	 *            Element to add
	 * @throws XPathExpressionException
	 */
	public void addNode(Document doc, String nodeStr, Element el)
			throws XPathExpressionException {
		XPath xpath = XPathFactory.newInstance().newXPath();

		Node node = (Node) xpath.evaluate(nodeStr, doc, XPathConstants.NODE);
		node.appendChild(el);
	}

	/**
	 * Adds a node element (string representation) as a child of another node
	 * 
	 * @param doc
	 *            {@link Document} instance
	 * @param nodeStr
	 *            Parent node selector
	 * @param xmlFragment
	 *            Element string to add
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws XPathExpressionException
	 */
	public void addNode(Document doc, String nodeStr, String xmlFragment)
			throws SAXException, IOException, ParserConfigurationException,
			XPathExpressionException {
		XPath xpath = XPathFactory.newInstance().newXPath();

		Node node = (Node) xpath.evaluate(nodeStr, doc, XPathConstants.NODE);

		Element xml = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new StringInputStream(xmlFragment)).getDocumentElement();

		node.appendChild(doc.importNode(xml, true));
	}
}
