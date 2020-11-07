package part;

import java.beans.PropertyChangeEvent;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Layer;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;


import model.ContentsModel;
import policies.CustomXYLayoutEditPolicy;

public class ContentsEditPart extends EditPartWithListener {

	@Override
	protected IFigure createFigure() {
		Layer figure = new Layer();//设置一个透明的图层
		figure.setLayoutManager(new XYLayout());//布局管理器
		return figure;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.LAYOUT_ROLE,new CustomXYLayoutEditPolicy());//安装了这个policy就可以选择图形的句柄了。接下来就是设置相关的command
		/*
		 * 不是很懂什么是LAYOUT_ROLE
		教程言：这个role是一个字符串变量，指定了安装的edting policy的角色。这里用了editpolicy的一个常量来表示这个editing policy的角色。
		之所以设置这个变量是因为：一个EditPart可以安装很多的policies,如果它们的角色都相同，那么就只有最后安装的一个policy是有效的。其实这个字符串常量可以为任意值，用EditPolicy的常量是为了统一、清楚。  ？？不懂
		 */
	}
	
	protected List getModelChildren() {
		return ((ContentsModel) getModel()).getChildren();
	}
	
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals(ContentsModel.P_CHILDREN)){//模型改变时通知
			refreshChildren();//因此子模型改变，要刷新子模型的EditPart显示其改变。  ！注意这里是利用refreshChildren方法来刷新子模型的EditPart，但是要真的删除图形，还是要有相应的Command
		}
	}

}
