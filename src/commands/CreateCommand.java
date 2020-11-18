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
		/*
		for(int i=0; i<contentsModel.getChildren().size(); i++) {
			HelloModel helloModel_child = (HelloModel) contentsModel.getChildren().get(i);//搜索把这个节点作为起点和终点的所有连线
			List sourceConnections = new ArrayList();//把这个节点作为source的连线的集合，由此可以得到该节点对应的所有输出端口
			List targetConnections = new ArrayList();//把这个节点作为target的连线的集合，由此可以得到该节点对应的所有输入端口
			sourceConnections.addAll(helloModel_child.getModelSourceConnections());
	        targetConnections.addAll(helloModel_child.getModelTargetConnections());
	        
	      //遍历把这个节点作为target的连线的集合，由此可以得到该节点对应的所有输入端口
	        System.out.println(helloModel_child+"的输入端口有：");
	        for(int inputNum=0; i<targetConnections.size();inputNum++) {
	        	AbstractConnectionModel connection = (AbstractConnectionModel) sourceConnections.get(inputNum);
	        	HelloModel model_connx = connection.getTarget();
	        	System.out.println(model_connx);
			}
	        
	      //遍历把这个节点作为source的连线的集合，由此可以得到该节点对应的所有输出端口
	        System.out.println(helloModel_child+"的输出端口有：");
			for(int outputNum=0; i<sourceConnections.size();outputNum++) {
				AbstractConnectionModel connection = (AbstractConnectionModel) sourceConnections.get(outputNum);
	        	HelloModel model_connx = connection.getTarget();
	        	System.out.println(model_connx);
			}
		}
		*/
	}
	
	public void setContentsModel(Object model) {
		contentsModel = (ContentsModel) model;
	}
	
	public void setHelloModel(Object model) {
		helloModel = (ComponentModel)model;
	}
	
	@Override
	public void undo() {
		contentsModel.removeChild(helloModel);
	}
}
