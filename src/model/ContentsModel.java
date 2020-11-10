package model;
import java.util.ArrayList;
import java.util.List;
public class ContentsModel extends AbstractModel{
	public static final String P_CHILDREN = "_children";//定义该字符串用于标识该模型结构（Children）改变
	private List<ComponentModel> children = new ArrayList<ComponentModel>();//子模型列表
	
	public void addChild(Object child) {//添加子模型
		children.add((ComponentModel) child);
		firePropertyChange(P_CHILDREN,null,null);//添加子模型后通知EditPart
	}
	
	public List<ComponentModel> getChildren() {//获得子模型
		return children;
	}
	


	//删除一个子模型
	public void removeChild(Object child) {
		children.remove(child);//删除一个子模型
		firePropertyChange(P_CHILDREN,null,null);//删除子模型后通知EditPart
	}
}
