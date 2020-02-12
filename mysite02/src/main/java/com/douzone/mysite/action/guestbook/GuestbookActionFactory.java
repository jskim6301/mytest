package com.douzone.mysite.action.guestbook;

import com.douzone.web.action.Action;
import com.douzone.web.action.ActionFactory;

public class GuestbookActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		switch(actionName) {
			case "deleteform":
				return new deleteformAction();
			case "delete":
				return new deleteAction();
			case "insert":
				return new insertAction();
			default :
				return new listAction();
		}
	}

}
