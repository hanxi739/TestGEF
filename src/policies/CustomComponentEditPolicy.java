package policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import commands.DeleteCommand;

public class CustomComponentEditPolicy extends ComponentEditPolicy {

	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		//调用DeleteCommand
		DeleteCommand deleteCommand = new DeleteCommand();
		deleteCommand.setContentsModel(getHost().getParent().getModel());
		//这里getHost()方法得到的是HelloModel的EditPart,这是因为这个CustomComponentEditPolicy要安装到HelloModel对应的HelloEditPart中??不是很懂
		deleteCommand.setHelloModel(getHost().getModel());
		return deleteCommand;
	}
}
