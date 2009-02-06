/**
 * (c) 2007 by Axel Sammet
 *
 * This file is under the CPL. For details see the enclosed file cpl.txt.
 */
package ipod.todo;

import ipod.base.Logger;
import ipod.ui.list.ListModel;

import java.util.Date;

import SQLite.Exception;

/**
 * CREATE TABLE Task ( ROWID INTEGER PRIMARY KEY AUTOINCREMENT, summary TEXT, priority INTEGER,
 * due_date INTEGER, completion_date INTEGER, calendar_id INTEGER, external_id TEXT,
 * external_mod_tag TEXT, external_id_tag TEXT, external_rep TEXT )
 * 
 */
public class TodoDao {

	protected static TodoDao instance;
	protected SQLite.Database db;

	private TodoDao() {
	}

	public void createTodo(Todo todo) {
		Logger.info("TodoDao.creatTodo() with id " + todo.id);
		try {
			openDB();
			db.exec("insert into Task values(" + todo.id + ",'" + todo.title + "','"
					+ todo.priority + "','" + Todo.YYYYMMDD.format(todo.duedate)
					+ "',NULL, NULL, NULL, NULL, NULL, NULL)", null);
		}
		catch (Exception e) {
			Logger.error(e);
		}
		finally {
			closeDB();
		}
	}

	public void update(Todo todo) {
		Logger.info("TodoDao.update() todo with id " + todo.id);
		try {
			openDB();
			String completed = todo.completed != null ? "'" + Todo.YYYYMMDD.format(todo.completed)
					+ "'" : null;
			db.exec("update Task set summary = '" + todo.title + "', priority='" + todo.priority
					+ "',due_date='" + Todo.YYYYMMDD.format(todo.duedate) + "', completion_date="
					+ completed + " where rowid =" + todo.id, null);
		}
		catch (Exception e) {
			Logger.error(e);
		}
		finally {
			closeDB();
		}
	}

	public void remove(Todo todo) {
		Logger.info("TodoDao.remove() todo for id " + todo.id);
		try {
			openDB();
			db.exec("delete from Task where rowid = " + todo.id, null);
		}
		catch (Exception e) {
			Logger.error(e);
		}
		finally {
			closeDB();
		}
	}

	public void loadTodos(ListModel<Todo> model) {
		Logger.info("TodoDao.loadTodos()");
		try {
			openDB();
			db.exec("update Task set due_date='2009-02-06' where rowid=11", null);
			SQLite.Stmt stmt = db.prepare("select * from Task");
			// SQLite.Stmt stmt =
			// db.prepare("select name, sql from sqlite_master where type = 'table'");
			model.clear();
			while (stmt.step()) {
				Date completed = null;
				try {
					completed = stmt.column_string(4) == null ? null : Todo.YYYYMMDD.parse(stmt
							.column_string(4));
				}
				catch (Exception e) {
					String columns = "";
					for (int i = 0; i < stmt.column_count(); i++) {
						columns += stmt.column_string(i) + " | ";
					}
					Logger.error("Table entry:  " + columns);
					Logger.error(e);
				}
				int id = Integer.parseInt(stmt.column_string(0));
				Todo todo = new Todo(stmt.column_string(1), stmt.column_int(2), Todo.YYYYMMDD
						.parse(stmt.column_string(3)), completed, id);
				Logger.debug("TodoDao.loadTodos() read: " + todo);
				model.addSilently(todo);
			}
		}
		catch (Throwable e) {
			Logger.error(e);
		}
		finally {
			closeDB();
		}
	}

	private void openDB() throws Exception {
		db = new SQLite.Database();
		db.open("/private/var/mobile/Library/Calendar/Calendar.sqlitedb", 0666);
	}

	private void closeDB() {
		try {
			db.close();
		}
		catch (Exception e) {
			Logger.error(e);
		}
	}

	public static TodoDao getInstance() {
		if (instance != null) {
			return instance;
		}
		else {
			return new TodoDao();
		}
	}

}
