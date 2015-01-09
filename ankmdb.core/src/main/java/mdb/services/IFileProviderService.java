package mdb.services;

import java.io.File;

public interface IFileProviderService {

	public String CLIENT_PROG_NAME_WITH_LOC = "ankmdb.client/target/AnkMdbClient-Zip.zip";

	String getResourceUrl();

	File getFile(String fileName);

	String getClientProgramUrl();

	File getCLientProgramFile();
}
