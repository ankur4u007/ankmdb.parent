package mdb.services.impl;

import java.io.File;

import mdb.services.IFileProviderService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("sp-fileProviderService")
public class FileProviderService implements IFileProviderService {

	@Value("#{'server'.equals('${env.machine}')}")
	private boolean isServerEnv;

	@Override
	public String getResourceUrl() {
		String path = null;
		if (isServerEnv) {
			path = System.getenv("OPENSHIFT_DATA_DIR") + '/';
		} else {
			path = "" + '/';
		}
		return path;
	}

	@Override
	public File getFile(final String fileName) {
		final String path = getResourceUrl();
		File file = null;
		if (path != null) {
			file = new File(path + fileName);
		} else {
			file = new File(fileName);
		}
		return file;
	}

}
