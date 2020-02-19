package com.douzone.mysite.action.board;

import com.douzone.web.action.Action;
import com.douzone.web.action.ActionFactory;

public class BoardActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		switch(actionName) {// 
			case "writeform":
				return new writeformAction();
			case "write":
				return new writeAction();
			case "viewform":
				return new viewformAction();
			case "modifyform":
				return new modifyformAction();
			case "modify":
				return new modifyAction();
			case "search":
				return new searchAction();				
			case "delete":
				return new deleteAction();				
			default :
				return new boardlistAction();
		}
	}
}
