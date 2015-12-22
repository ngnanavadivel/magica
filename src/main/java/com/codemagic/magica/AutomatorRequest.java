package com.codemagic.magica;

import java.util.Properties;

public class AutomatorRequest {
	public enum FORMAT {XML, JSON, FLAT};
	public FORMAT	srcFormat;
	public FORMAT	destFormat;
	public String	tgtRoot;
	public String	srcRoot;
	public String	service;
	public String	srcSchemaName;
	public String	tgtSchemaName;
	public String	baseDir;
	public String	sourceInstance;
	public Properties operations;

	public String getBasePkg() {
		return "com." + service;
	}

	public String getSourcePkg() {
		return getBasePkg() + ".source";
	}

	public String getTargetPkg() {
		return getBasePkg() + ".target";
	}

	public String getResourcesDir() {
		return baseDir + "/resources";
	}

	public String getGeneratedDir() {
		return baseDir + "/generated";
	}

	public String getCompiledDir() {
		return baseDir + "/compiled";
	}

	public String getJarDir() {
		return baseDir + "/jar";
	}

	public String getJarFile() {
		return getJarDir() + "/sources.jar";
	}

	public String getMappingFile() {
		return getResourcesDir() + "/mapping.properties";
	}

	public String getOperationsFile() {
		return getResourcesDir() + "/operations.properties";
	}

	public String[] getSourceArgs() {
		return new String[] { "-p", getSourcePkg(),
				getResourcesDir() + "/" + srcSchemaName, "-d",
				getGeneratedDir() };
	}

	public String[] getTargetArgs() {
		return new String[] { "-p", getTargetPkg(),
				getResourcesDir() + "/" + tgtSchemaName, "-d",
				getGeneratedDir() };
	}

}