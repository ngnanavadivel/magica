package com.codemagic.magica.mapping.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.xml.sax.InputSource;

public class PropertiesMappingReader implements MappingReader {

	public List<Mapping> parse(InputSource source) throws Exception {
		List<Mapping> mappings = new ArrayList<Mapping>();
		Properties props = new Properties();
		props.load(source.getCharacterStream());
		Set<Entry<Object, Object>> entries = props.entrySet();
		for (Entry<Object, Object> mpg : entries) {
			String tgtProperty = (String) mpg.getKey();
			String srcExpression = (String) mpg.getValue();
			Mapping mpgNode = new Mapping();
			mpgNode.setTarget(tgtProperty);
			mpgNode.setSource(new Parser().parse(srcExpression));
			mappings.add(mpgNode);
		}
		return mappings;
	}

}
