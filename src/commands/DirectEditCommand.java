package commands;

import org.eclipse.gef.commands.Command;

import model.ComponentModel;

public class DirectEditCommand extends Command {

	private String oldText,newText;
	private ComponentModel helloModel;
	/*
	@Override
	public void execute() {
		oldText = helloModel.getText();//记录旧文本
		helloModel.setText(newText);//设置为新的文本		
	}
	
	
	public void setModel(Object model) {
		helloModel = (ComponentModel)model;
	}
	
	public void setText(String text) {
		newText = text;
	}
	
	@Override
	public void undo() {
		helloModel.setText(oldText);
	}
	
	*/
}
