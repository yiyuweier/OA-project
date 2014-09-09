package com.oa.action.util;

import java.util.Comparator;

import org.jbpm.api.task.Task;

public class ComparatorTask<Task> implements Comparator<Task>{

	@Override
	public int compare(Task o1, Task o2) {
		org.jbpm.api.task.Task task1 = (org.jbpm.api.task.Task) o1;
		org.jbpm.api.task.Task task2 = (org.jbpm.api.task.Task) o2;
		boolean flag = task2.getCreateTime().after(task1.getCreateTime());
		if(flag){
			return 1;
		}else{
			return -1;
		}	
	}

}
