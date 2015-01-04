package mdb.services;

import java.io.IOException;
import java.util.Set;

import mdb.bo.EntryBO;

public interface ISerializeDBService {

	Set<EntryBO> getListFromDBFile() throws IOException;

	void saveListToDBFile(Set<EntryBO> entryBOSet) throws IOException;

	boolean checkSerFileExists();
}
