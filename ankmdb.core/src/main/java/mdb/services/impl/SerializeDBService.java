package mdb.services.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import mdb.bo.EntryBO;
import mdb.services.IFileProviderService;
import mdb.services.ISerializeDBService;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component("sp-serializeDBService")
public class SerializeDBService implements ISerializeDBService {

	@Value("${db.name}")
	private String dbName;

	@Autowired
	@Qualifier(value = "sp-fileProviderService")
	private IFileProviderService fileProviderService;

	@Override
	@Cacheable(value = "db.map", key = "#root.methodName")
	public synchronized Set<EntryBO> getListFromDBFile() throws IOException {
		Set<EntryBO> entryBOSet = null;
		final File dbFile = fileProviderService.getFile(dbName);
		if (dbFile.exists()) {
			try (final BufferedInputStream bis = new BufferedInputStream(new FileInputStream(dbFile))) {
				entryBOSet = new HashSet<EntryBO>();
				entryBOSet = SerializationUtils.deserialize(bis);
			} catch (final FileNotFoundException e) {
				throw e;
			}
		}
		return entryBOSet;
	}

	@Override
	@CacheEvict(value = "db.map", allEntries = true)
	public synchronized void saveListToDBFile(final Set<EntryBO> entryBOSet) throws IOException {
		if (entryBOSet != null) {
			final File dbFile = fileProviderService.getFile(dbName);
			try (final BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(dbFile))) {
				SerializationUtils.serialize((Serializable) entryBOSet, bos);
				bos.flush();
			} catch (final FileNotFoundException e) {
				throw e;
			}

		}
	}

	@Override
	@CacheEvict(value = "db.map", allEntries = true, condition = "#result == false", beforeInvocation = false)
	public boolean checkSerFileExists() {
		final File dbFile = fileProviderService.getFile(dbName);
		return dbFile.exists();
	}
}
