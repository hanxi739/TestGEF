package commands;

import org.eclipse.gef.commands.Command;

import model.AbstractConnectionModel;
import model.ComponentModel;

public class CreateConnectionCommand extends Command {
	private ComponentModel source,target;//两个模型一个用于起点，一个用于终点
	private AbstractConnectionModel connection;//连接的模型
	
	//首先判断能否执行连接
	public boolean canExecute() {
		if(source==null || target ==null) {//很疑惑，这里的source和AbstractConnectionModel类里的source是不是老是重复呢，对象一直在变化？不会有问题吗
			return false;
		}
		if(source.equals(target)) {
			return false;
		}
		return true;
	}
	
	//执行连接：连接起点和连接终点
	public void execute() {
		connection.attachSource();
		connection.attachTarget();
	}
	
	public void setConnection(Object model) {
		connection =(AbstractConnectionModel)model; 
	}
	
	public void setSource(Object model) {
		source = (ComponentModel)model;
		connection.setSource(source);
	}
	
	public void setTarget(Object model) {
		target = (ComponentModel)model;
		connection.setTarget(target);
	}
	
	//撤销连接：撤销起点和撤销终点
	public void undo() {
		connection.detachSource();
		
	}
	
}
