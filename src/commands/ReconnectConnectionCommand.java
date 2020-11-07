package commands;
//重定向连接：感觉和CreateConnectionCommand没啥区别，以下代码是复制其
import org.eclipse.gef.commands.Command;

import model.AbstractConnectionModel;
import model.HelloModel;

public class ReconnectConnectionCommand extends Command {

	private HelloModel oldSource,oldTarget;//两个模型一个用于旧起点，一个用于旧终点
	private HelloModel newSource,newTarget;//两个模型一个用于新起点，一个用于新终点
	private AbstractConnectionModel connection;//连接的模型
	
	//首先判断能否执行连接
	public boolean canExecute() {
		if(newSource==null || newTarget ==null) {//很疑惑，这里的source和AbstractConnectionModel类里的source是不是老是重复呢，对象一直在变化？不会有问题吗
			return false;
		}
		if(newSource.equals(newTarget)) {
			return false;
		}
		return true;
	}
	
	//执行连接：连接起点和连接终点
	public void execute() {
		connection.attachSource();
		connection.attachTarget();
		System.out.println("执行时"+connection+"的新target是:"+connection.getTarget());
	}
	
	//在一个policy中一般先执行以下的设置操作，然后执行execute()方法
	public void setConnectionModel(Object model) {
		connection =(AbstractConnectionModel)model; 
	}
	
	public void setNewSource(Object model) {
		oldSource = connection.getSource();//记录旧头端
		connection.detachSource();//删除旧source中记录的连接信息
		newTarget = connection.getTarget();//新的尾端不变
		newSource = (HelloModel)model;
		connection.setSource(newSource);//设置新source
		
	}
	
	public void setNewTarget(Object model) {
		oldTarget = connection.getTarget();//记录旧尾端
		System.out.println(connection+"的旧target是:"+oldTarget);
		connection.detachTarget();//删除旧target中记录的连接信息
		newSource = connection.getSource();//新的头端不变
		newTarget = (HelloModel)model;
		connection.setTarget(newTarget);//设置新target
		System.out.println(connection+"的新target是:"+connection.getTarget());
		System.out.println(connection+"的新target是:"+newTarget);
	}
	
	//撤销连接：撤销起点和撤销终点。还没有梳理好逻辑，没有实现，先抓紧核心功能实现
	public void undo() {
		connection.detachSource();
		connection.detachTarget();
	}
}
