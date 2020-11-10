package policies;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

import commands.ChangeConstraintCommand;
import commands.CreateCommand;
import model.ComponentModel;

public class CustomXYLayoutEditPolicy extends XYLayoutEditPolicy {

	//为什么request里会携带创建的模型对象并且请求类型是create child？？?
	@Override
	protected Command getCreateCommand(CreateRequest request) {//这里有CreateRequest，所以在这里调用创建模型的CreateCommand
		CreateCommand command = new CreateCommand();
		Rectangle constraint = (Rectangle)getConstraintFor(request);//产生创建图形的尺寸和位置
		ComponentModel model = (ComponentModel) request.getNewObject();//获得新创建的图形 
		System.out.println(model);
		Object type = request.getNewObjectType();
		model.setConstraint(constraint);//为该图形设置前面获得的位置和尺寸
		
		//将新创建的图形添加到模型中
		command.setContentsModel(getHost().getModel());
		command.setHelloModel(model);
		
		return command;/*因为我们在模型类中添加了监听器，并且将模型的改变通知给了其EditPart，EditPart收到事件后会知道属性发生了改变，所以执行了refreshVisuals操作，Graphical Editor中的图形也会发生改变*/
	}
	
	/*
	//重载getCommand方法，在控制台输出拖动句柄和拖动图形时的请求
	public Command getCommand(Request request) {
		System.out.println(request.getType());
		return super.getCommand(request);
	}
*/
	
	/*
	 * 创建了commands.ChangeConstraintCommand，就要在EditingPolicy的框架下运行命令了。
	XYLayoutEditPolicy的getCommand方法得到的请求类型是REQ_MOVE_CHILDREN或REQ_RESIZE_CHILDREN时，就会执行createChangeConstraintCommand方法。
	所以我们的ChangeConstraintCommand要放在这个方法中执行
	*/
	protected Command createChangeConstraintCommand(EditPart child,Object constraint) {
		ChangeConstraintCommand command = new ChangeConstraintCommand();//创建一个命令
		command.setModel(child.getModel());//设置要编辑的对象
		command.setConstraint((Rectangle) constraint);//设置新的约束
		return command;//返回命令
	}
	/*
	 * 至此，运行程序后还是不能改变图形位置和尺寸，这是因为模型虽然改变了，但是视图并不知道，所以要通知EditPart模型已经改变了，再由EditPart改变视图。这就涉及到监听机制。
	 * */

}
