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
package org.brunocunha.anttasks.xpath;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

import org.brunocunha.anttasks.xpath.impl.XPathTools;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Some XPath tests
 * @author Bruno Candido Volpato da Cunha
 *
 */
public class XPathChangeTest {

	private XPathTools tools;
	
	
	@Before
	public void setUp() {
		this.tools = new XPathTools();
	}
	
	@Test
	public void changeSimpleTest() throws Exception {
		File file = getTestFile("xpath.xml");
		File tempFile = File.createTempFile("temp", ".xml");
		System.out.println("Temp File #1: " + tempFile.getAbsolutePath() );
		
		Document doc = tools.doChange(file, "//pessoas//pessoa[@id=1]/nome", "@brunocvcunha");
		
		tools.saveDocument(doc, tempFile);
		String nodeValue = tools.getNodeValue(tempFile, "//pessoas/pessoa[@id=1]/nome");
		assertEquals("@brunocvcunha", nodeValue);
		
		String attrValue = tools.getNodeValue(tempFile, "//pessoas/pessoa/@id");
		assertEquals("1", attrValue);
		
	}
	
	@Test
	public void addSimpleTest() throws Exception {
		File file = getTestFile("xpath.xml");
		File tempFile = File.createTempFile("temp", ".xml");
		System.out.println("Temp File #2: " + tempFile.getAbsolutePath() );
		
		Document doc = tools.getDocument(new FileInputStream(file));
		
		Element profissao = doc.createElement("profissao");
		profissao.setTextContent("Desenvolvedor");
		
		tools.addNode(doc, "//pessoas/pessoa[@id=1]", profissao);
		
		tools.saveDocument(doc, tempFile);
		String nodeValue = tools.getNodeValue(tempFile, "//pessoas/pessoa[@id=1]/profissao");
		assertEquals("Desenvolvedor", nodeValue);
		
	}
	
	
	@Test
	public void addFragmentTest() throws Exception {
		File file = getTestFile("xpath.xml");
		File tempFile = File.createTempFile("temp", ".xml");
		System.out.println("Temp File #2: " + tempFile.getAbsolutePath() );
		
		Document doc = tools.getDocument(new FileInputStream(file));
		
		tools.addNode(doc, "//pessoas/pessoa[@id=1]", "<profissao>Desenvolvedor</profissao>");
		
		tools.saveDocument(doc, tempFile);
		String nodeValue = tools.getNodeValue(tempFile, "//pessoas/pessoa[@id=1]/profissao");
		assertEquals("Desenvolvedor", nodeValue);
		
	}
	
	@Test
	public void changeLog4jTest() throws Exception {
		File file = getTestFile("jboss-log4j.xml");
		File tempFile = File.createTempFile("log4j", ".xml");
		System.out.println("Temp File #3: " + tempFile.getAbsolutePath() );
		
		Document doc = tools.doChange(file, "//appender/param[@name='Threshold']/@value", "INFO");
		
		System.out.println(tools.getDocumentString(doc));
		
		tools.saveDocument(doc, tempFile);
		/*
		XPathTools.saveDocument(doc, tempFile);
		String nodeValue = XPathTools.getNodeValue(tempFile, "//pessoas/pessoa[@id=1]/nome");
		assertEquals("Bruno da TOTVS", nodeValue);
		
		String attrValue = XPathTools.getNodeValue(tempFile, "//pessoas/pessoa/@id");
		assertEquals("1", attrValue);
		*/
		
	}
	
	private File getTestFile(String name) {
		URL url = Thread.currentThread().getContextClassLoader().getResource(name);
		return new File(url.getPath());
	}
}
