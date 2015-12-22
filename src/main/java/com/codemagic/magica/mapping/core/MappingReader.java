package com.codemagic.magica.mapping.core;

import java.util.List;

import org.xml.sax.InputSource;

public interface MappingReader {
	List<Mapping> parse(InputSource source) throws Exception;
}
