package commands;
//删除节点的相关命令
import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.commands.Command;

import model.AbstractConnectionModel;
import model.ContentsModel;
import model.ComponentModel;

public class DeleteCommand extends Command {

	private ContentsModel contentsModel;
	private ComponentModel helloModel;
	
	//Connection List---------------------------
	private List sourceConnections = new ArrayList();
	private List targetConnections = new ArrayList();
	//----------------------------------------
	
	
	public void execute() {
		//----------------删除连接
		//在删除一个模型对象前，搜索把这个模型对象作为起点和终点的所有连接
		sourceConnections.addAll(helloModel.getModelSourceConnections());//这个helloModel对象是如何初始化（实例化）的呢？
		targetConnections.addAll(helloModel.getModelTargetConnections());
		
		//删除该模型对象作为source节点对应的 所有连接的头端和尾端
		

		for(int i=0; i<sourceConnections.size();i++) {
			AbstractConnectionModel model = (AbstractConnectionModel)sourceConnections.get(i);
			model.detachSource();
			model.detachTarget();			
			//System.out.println("作为source的连接模型"+model+"已删除");
			
		}
		//删除该模型对象作为target节点对应的所有连接的头端和尾端
		
		for(int i=0; i<targetConnections.size();i++) {
			AbstractConnectionModel model = (AbstractConnectionModel)targetConnections.get(i);
			model.detachSource();
			model.detachTarget();
			//System.out.println("作为target的连接模型"+model+"已删除");
		}
		contentsModel.removeChild(helloModel);//删除模型
	}
	
	public void setContentsModel(Object model) {
		contentsModel = (ContentsModel) model;
	}
	
	public void setHelloModel(Object model) {
		helloModel = (ComponentModel)model;
	}
	
	@Override
	public void undo() {
		contentsModel.addChild(helloModel);
		//-------------------撤销连接的删除操作-------------
		for(int i=0; i<sourceConnections.size();i++) {
			AbstractConnectionModel model = (AbstractConnectionModel)sourceConnections.get(i);
			model.attachSource();
			model.attachTarget();
		}
		for(int i=0; i<targetConnections.size();i++) {
			AbstractConnectionModel model = (AbstractConnectionModel)targetConnections.get(i);
			model.attachSource();
			model.attachTarget();
		}
		//清除记录，这些记录用于恢复
		sourceConnections.clear();
		targetConnections.clear();
		//----------------------------------------------
	}
}
