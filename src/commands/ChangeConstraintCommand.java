package commands;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

import model.ComponentModel;
//改变约束命令：重新设置label的位置和大小
public class ChangeConstraintCommand extends Command {
	private ComponentModel helloModel;//被本命令改变的模型
	private Rectangle constraint;//新的约束？
	private Rectangle oldConstraint;//以前的约束
	
	public void execute() {
		helloModel.setConstraint(constraint);//改变约束：设置新值
	}
	/*
	 * 从Command中可以看出，redo()就是执行execute()
	 * */
	
	public void setConstraint(Rectangle rect) {
		constraint = rect;
	}
	
	public void setModel(Object model) {
		helloModel = (ComponentModel) model;
		oldConstraint = helloModel.getConstraint();//记录旧值
	}
	
	public void undo() {
		helloModel.setConstraint(oldConstraint);//撤销操作：恢复旧值
	}
	
	/*
	 * 在Java中,undo/redo是Action，所以我们要有菜单或者工具按钮来执行这些Action
	 * */
}
