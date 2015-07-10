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
package org.brunocunha.anttasks.prop;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.brunocunha.anttasks.prop.impl.CommentedProperties;
import org.junit.Test;

/**
 * Some Props tests
 * @author Bruno Candido Volpato da Cunha
 *
 */
public class PropChangeTest {

	@Test
	public void testRuntimeMode() throws FileNotFoundException, IOException {
		File file = getTestFile("framework.properties");
		File tempFile = File.createTempFile("framework", ".properties");

		CommentedProperties prop = new CommentedProperties();
		prop.load(new FileInputStream(file));
		prop.put("runtime.mode", "development");

		prop.store(new FileOutputStream(tempFile), "");

		Properties newProp = new Properties();
		newProp.load(new FileInputStream(tempFile));

		assertEquals("development", newProp.get("runtime.mode"));
	}

	private File getTestFile(String name) {
		URL url = Thread.currentThread().getContextClassLoader()
				.getResource(name);
		return new File(url.getPath());
	}

}
