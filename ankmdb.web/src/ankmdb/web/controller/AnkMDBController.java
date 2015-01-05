package ankmdb.web.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import mdb.bo.EntryBO;
import mdb.bo.JsonBO;
import mdb.bo.MediaBO;
import mdb.services.IIndexService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping("/")
public class AnkMDBController {

	@Autowired
	@Qualifier(value = "sp-indexService")
	private IIndexService indexService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String init(final ModelMap model) {
		final String postUrl = indexService.getPostUrl();
		if (postUrl != null) {
			model.addAttribute("url", postUrl);
		}
		return "load";
	}

	@RequestMapping(value = "/getSha1List", method = RequestMethod.POST)
	public String getSha1List(@RequestBody final String objectRequest, final ModelMap model) {
		final Gson gson = new Gson();
		final JsonBO jsonBORecieved = gson.fromJson(objectRequest, new TypeToken<JsonBO>() {
		}.getType());
		final String source = jsonBORecieved.getSource();
		model.addAttribute("message", gson.toJson(indexService.getSha1ListBySource(source), new TypeToken<List<String>>() {
		}.getType()));
		return "message";
	}

	@RequestMapping(value = "/saveSha1List", method = RequestMethod.POST)
	public String saveSha1List(@RequestBody final String objectRequest, final ModelMap model) {
		final Gson gson = new Gson();
		int addedRecords = 0;
		int deletedRecords = 0;
		final JsonBO jsonBORecieved = gson.fromJson(objectRequest, new TypeToken<JsonBO>() {
		}.getType());
		final String source = jsonBORecieved.getSource();
		final List<String> sha1ListToRemove = jsonBORecieved.getSha1keyList();
		final Map<String, MediaBO> mediaAndSha1KeyMap = jsonBORecieved.getMediaAndSha1KeyMap();

		if (sha1ListToRemove != null && !sha1ListToRemove.isEmpty()) {
			indexService.performDelete(sha1ListToRemove, source);
			deletedRecords = sha1ListToRemove.size();
		}
		if (mediaAndSha1KeyMap != null && !mediaAndSha1KeyMap.isEmpty()) {
			indexService.performSave(mediaAndSha1KeyMap, source);
			addedRecords = mediaAndSha1KeyMap.size();
		}

		model.addAttribute("message", deletedRecords + " records were deleted;" + addedRecords + " records were added");
		return "message";
	}

	@RequestMapping(value = "/getAllMedia", method = RequestMethod.GET)
	public String getAllMedia(final ModelMap model) {
		final Set<EntryBO> resultList = indexService.getAllMediaFromDBFile();
		if (resultList != null && !resultList.isEmpty()) {
			final Gson gson = new Gson();
			model.addAttribute("message", gson.toJson(resultList));
		}
		return "message";
	}
}
