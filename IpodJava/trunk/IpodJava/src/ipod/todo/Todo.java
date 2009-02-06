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

	public Todo(String title, int priority, Date duedate, Date completed, int id) {
		this.title = title;
		this.duedate = duedate;
		this.completed = completed;
		this.priority = 2;
		this.id = id;
		Sequence.initMax(id);
	}

	public boolean isFinished() {
		return completed != null;
	}

	public Todo(String title, int priority, Date duedate) {
		this(title, priority, duedate, null, Sequence.next());
	}

	@Override
	public String toString() {
		return "Todo[" + id + ", " + title + ", " + YYYYMMDD.format(duedate) + ", " + isFinished()
				+ "]";
	}

	public String getSectionName() {
		if (isFinished()) {
			return "Erledigt";
		}
		switch (priority) {
		case 2:
			return "Wichtig";
		case 1:
			return "Normal";
		default:
			return "Unkategorisiert";
		}
	}
}
