package ru.saber_nyan;

public interface Post {
	long number = 0;
	boolean banned = false;
	boolean closed = false;
	String comment = null;
	String date = null;
	String email = null;
	File[] files = null;
	String name = null;
	boolean op = false;
	boolean sticky = false;
	String subject = null;
	String tripcode = null;

	boolean isSeen = true;
}
