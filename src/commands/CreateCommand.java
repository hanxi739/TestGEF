package commands;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

import model.AbstractConnectionModel;
import model.ContentsModel;
import model.ComponentModel;

public class CreateCommand extends Command {

	private ContentsModel contentsModel;
	private ComponentModel helloModel;
	
	public void execute() {//step3

		contentsModel.addChild(helloModel);//添加节点
	}
	
	public void setContentsModel(Object model) {
		contentsModel = (ContentsModel) model;
	}
	
	public void setHelloModel(Object model) {
		helloModel = (ComponentModel)model;
		String []values = helloModel.getPropertyList().get(0).getValue().split(":"); 
		String objName0 = values[1];
		String str = helloModel.toString();
		int begin = str.indexOf('@');
		String objName1 = str.substring(begin);
		String objName = objName0 + objName1;
		helloModel.setObjName(objName);
	}
	
	@Override
	public void undo() {
		contentsModel.removeChild(helloModel);
	}
}
