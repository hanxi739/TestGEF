package policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

import commands.CreateConnectionCommand;
import commands.ReconnectConnectionCommand;

public class CustomGraphicalNodeEditPolicy extends GraphicalNodeEditPolicy {
	
	//------------------------关于完成连线的策略------------------------------------
	//getConnectionCreateCommand用于创建连接的命令，getConnectionCompleteCommand用于完成连接。
	//一般（A）在getConnectionCreateCommand中生成一个创建连接的命令CreateConnectionCommand；
	//（B）然后把命令用setStartCommand保存在堆栈中；
	//（C）在getConnectionCompleteCommand中用getStartCommand方法再把CreateConnectionCommand命令取出来，一旦寻找到了终点，这个连接就创建了。
	@Override
	protected Command getConnectionCompleteCommand(CreateConnectionRequest request) {
		//命令是从request中获得
		CreateConnectionCommand command = (CreateConnectionCommand)request.getStartCommand();
		command.setTarget(getHost().getModel());//设置尾端
		return command;
	}

	@Override
	protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
		CreateConnectionCommand command = new CreateConnectionCommand();//(A)
		command.setConnection(request.getNewObject());//建立连接模型
		command.setSource(getHost().getModel());//建立头端
		request.setStartCommand(command);//创建连接的命令被记录（B）
		return command;
	}

	
	//-----------------------------关于重定向的策略----------------------------------
	@Override//拖动句柄重新连接头端
	protected Command getReconnectSourceCommand(ReconnectRequest request) {
		ReconnectConnectionCommand command = new ReconnectConnectionCommand();
		command.setConnectionModel(request.getConnectionEditPart().getModel());
		command.setNewSource(getHost().getModel());
		return command;
	}

	@Override//拖动句柄重新连接尾端
	protected Command getReconnectTargetCommand(ReconnectRequest request) {
		ReconnectConnectionCommand command = new ReconnectConnectionCommand();
		command.setConnectionModel(request.getConnectionEditPart().getModel());
		command.setNewTarget(getHost().getModel());
		return command;
	}

}
