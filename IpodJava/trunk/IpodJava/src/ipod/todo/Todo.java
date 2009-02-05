/**
 * (c) 2007 by Axel Sammet
 *
 * This file is under the CPL. For details see the enclosed file cpl.txt.
 */
package ipod.todo;

import ipod.ui.list.Sectionable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Todo implements Sectionable {
	public static SimpleDateFormat YYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");

	public int id;
	public String title;
	public Date duedate;
	public int priority;
	public Date completed;

	public Todo(String title, int priority, Date duedate, Date completed) {
		this.title = title;
		this.duedate = duedate;
		this.completed = completed;
		this.priority = 2;
	}

	public boolean isFinished() {
		return completed != null;
	}

	public Todo(String title, int priority, Date duedate) {
		this(title, priority, duedate, null);
	}

	@Override
	public String toString() {
		return "Todo[" + id + ", " + title + ", " + YYYYMMDD.format(duedate) + ", " + isFinished()
				+ "]";
	}
}
