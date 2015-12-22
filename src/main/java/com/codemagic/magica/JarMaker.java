package com.codemagic.magica;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

public class JarMaker {
	public void createJar(String srcDirectory, String jarFilePath) throws IOException {
		Manifest manifest = new Manifest();
		manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
		JarOutputStream target = new JarOutputStream(new FileOutputStream(jarFilePath), manifest);
		add(new File(srcDirectory), target, srcDirectory);
		target.close();
	}

	private void add(File source, JarOutputStream target, String toBeChoppedOf) throws IOException {
		BufferedInputStream in = null;
		try {
			
			String name = source.getPath().replace("\\", "/");
			System.out.println("Before			: " + name);
			System.out.println("toBeChoppedOf	: " + toBeChoppedOf);
			name = name.replace(toBeChoppedOf, "");
			name = name.length() > 1 ? name.substring(1) : name;
			System.out.println("After chopping : " + name);
			if (source.isDirectory()) {
				
				if (!name.isEmpty() && !name.equals(toBeChoppedOf)) {
					if (!name.endsWith("/"))
						name += "/";
					JarEntry entry = new JarEntry(name);
					entry.setTime(source.lastModified());
					target.putNextEntry(entry);
					target.closeEntry();
				}
				for (File nestedFile : source.listFiles())
					add(nestedFile, target, toBeChoppedOf);
				return;
			}

			JarEntry entry = new JarEntry(name);
			entry.setTime(source.lastModified());
			target.putNextEntry(entry);
			in = new BufferedInputStream(new FileInputStream(source));

			byte[] buffer = new byte[1024];
			while (true) {
				int count = in.read(buffer);
				if (count == -1)
					break;
				target.write(buffer, 0, count);
			}
			target.closeEntry();
		} finally {
			if (in != null)
				in.close();
		}
	}
	
	public static void main(String[] args) {
		System.out.println("C:/Users/gnanavad/Documents/stash-all-in-one/magica/src/main/compiled/com/magica/person".replace("C:/Users/gnanavad/Documents/stash-all-in-one/magica/src/main/compiled/", ""));
	}
}
