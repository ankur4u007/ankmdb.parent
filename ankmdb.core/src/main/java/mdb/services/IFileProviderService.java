package mdb.services;

import java.io.File;

public interface IFileProviderService {

	String getResourceUrl();

	File getFile(String fileName);
}
