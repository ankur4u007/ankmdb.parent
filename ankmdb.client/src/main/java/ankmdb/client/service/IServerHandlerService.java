package ankmdb.client.service;

import java.io.IOException;
import java.util.List;

import mdb.bo.JsonBO;

public interface IServerHandlerService {

	public String getSha1ListMethod = "getSha1List";
	public String saveSha1ListMethod = "saveSha1List";

	List<String> getSha1ListOnServer(String source, String ankMdbServerURL) throws IOException;

	String saveMediaMapOnServer(final JsonBO jsonBOToPost, String ankMdbServerURL) throws IOException;
}
