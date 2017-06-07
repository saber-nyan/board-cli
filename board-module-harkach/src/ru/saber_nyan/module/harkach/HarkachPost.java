package ru.saber_nyan.module.harkach;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.saber_nyan.parser.File;
import ru.saber_nyan.parser.Post;

import java.util.ArrayList;

public class HarkachPost extends Post {

	private static final String KEY_POST_NUMBER = "num";
	private static final String KEY_POST_BANNED = "banned";
	private static final String KEY_POST_CLOSED = "closed";
	private static final String KEY_POST_COMMENT = "comment";
	private static final String KEY_POST_DATE = "date";
	private static final String KEY_POST_EMAIL = "email";
	private static final String KEY_POST_NAME = "name";
	private static final String KEY_POST_SUBJECT = "subject";
	private static final String KEY_POST_TRIPCODE = "trip";
	private static final String KEY_POST_OP = "op";
	private static final String KEY_POST_STICKY = "sticky";
	private static final String KEY_POST_FILES = "files";

	public HarkachPost(JSONObject post) {
		this.setNumber(post.getLong(KEY_POST_NUMBER));
		this.setBanned(post.getInt(KEY_POST_BANNED) != 0);
		this.setClosed(post.getInt(KEY_POST_CLOSED) != 0);
		this.setOp(post.getInt(KEY_POST_OP) != 0);
		this.setSticky(post.getInt(KEY_POST_STICKY) != 0);
		this.setComment(post.getString(KEY_POST_COMMENT));
		this.setDate(post.getString(KEY_POST_DATE));
		this.setEmail(post.getString(KEY_POST_EMAIL));
		this.setName(post.getString(KEY_POST_NAME));
		this.setSubject(post.getString(KEY_POST_SUBJECT));
		this.setTripcode(post.getString(KEY_POST_TRIPCODE));

		JSONArray filesArray = post.getJSONArray(KEY_POST_FILES);

		ArrayList<File> files = new ArrayList<>();

		for (int i = 0; i < filesArray.length(); i++) {
			File newFile = new HarkachFile(filesArray.getJSONObject(i));
			files.add(newFile);
		}

		this.setFiles(files);
	}
}
