package commands;
//删除连接线的命令
import org.eclipse.gef.commands.Command;

import model.AbstractConnectionModel;

public class DeleteConnectionCommand extends Command {
	private AbstractConnectionModel connection;
	
	@Override
	public void execute() {//删除连接
		connection.detachSource();
		connection.detachTarget();
	}
	
	public void setConnectionModel(Object model) {//？？？不知道什么时候调用这个函数
		connection = (AbstractConnectionModel)model;
	}
	
	public void undo() {
		connection.attachSource();
		connection.attachTarget();
	}
}
