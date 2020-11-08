package part;

import java.beans.PropertyChangeEvent;
import java.util.EventObject;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.Border;
import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.CompoundBorder;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.SimpleRaisedBorder;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.ui.IEditorPart;

import model.HelloModel;
import policies.CustomComponentEditPolicy;
import policies.CustomDirectEditPolicy;
import policies.CustomGraphicalNodeEditPolicy;

public class HelloEditorPart extends EditPartWithListener implements NodeEditPart{

	//private CustomDirectEditManager directManager = null;//直接编辑图形上文本的相关操作
	
	
	//直接编辑文本功能有点问题，暂时不实现
	/*
	@Override
	public void performRequest(Request req) {
		if(req.getType().equals(RequestConstants.REQ_DIRECT_EDIT)) {
			performDirectEdit();
			return;			
		}
		super.performRequest(req);
	}
	
	
	
	private void performDirectEdit() {
		if(directManager == null) {
			//如果还没有directManager,则创建一个，类型是Text,位置由图形决定
			directManager = new CustomDirectEditManager(this,TextCellEditor.class,new CustomCellEditorLocator(getFigure()) );
		}
		directManager.show();//显示这个directManager
	}
	*/
	@Override
	protected IFigure createFigure() {
		HelloModel model = (HelloModel)getModel();
		Label label = new Label();
		label.setText(model.getText());
		//设置标签颜色
		//label.setBorder(new CompoundBorder(new LineBorder(), new MarginBorder(5)));
		label.setBorder(new SimpleRaisedBorder(5));//draw2d的border修改label的样式
		label.setBackgroundColor(ColorConstants.lightGray);
		
		label.setOpaque(true);
        return label;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE,new CustomComponentEditPolicy());//安装删除的policy	
		//installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE,new CustomDirectEditPolicy());直接编辑功能尚不完善，暂时取消
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,new CustomGraphicalNodeEditPolicy());//安装连接建立与删除的policy
	}
	
	protected void refreshVisuals() {
		Rectangle constraint = ((HelloModel)getModel()).getConstraint();
		((GraphicalEditPart) getParent()).setLayoutConstraint(this,getFigure(),constraint);
	}
	
	public void propertyChange(PropertyChangeEvent event) {
		if(event.getPropertyName().equals(HelloModel.P_CONSTRAINT)) {//the model of change show change of the position on a model
			refreshVisuals();//更新视图
		}
		else if(event.getPropertyName().equals(HelloModel.P_TEXT)) {//当图形模型的文本属性改变时，在Graphical Editor中的图形文本也改变
			Label label = (Label)getFigure();
			label.setText((String)event.getNewValue());
		}
		else if(event.getPropertyName().equals(HelloModel.P_SOURCE_CONNECTION)) {//refreshSourceConnections();和refreshTargetConnections();暗地里调用getModelSourceConnections()和target...
			refreshSourceConnections();
		}
		else if(event.getPropertyName().equals(HelloModel.P_TARGET_CONNECTION)) {
			refreshTargetConnections();
		}
	}
	
	protected List getModelSourceConnections() {
		return ((HelloModel)getModel()).getModelSourceConnections();//重写这个方法，看起来是用一个对象调用这个方法，将这个操作简单化一些
	}
	
	protected List getModelTargetConnections() {
		return ((HelloModel)getModel()).getModelTargetConnections();
	}
//HelloEditPart就是用于管理连接的锚点Anchor的。缺省情况下，使用的是org.eclipse.draw2d.ChopboxAnchor锚点。这样，我们就看到了一些情况：连接LineConnectionModel不是直接和节点相连的，而是通过锚点和节点联系起来的。
	//不是很懂，为什么四个函数的返回都是一样的呢，返回的是什么
	@Override
	public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart connection) {
		return new ChopboxAnchor(getFigure());
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		return new ChopboxAnchor(getFigure());
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart connection) {
		return new ChopboxAnchor(getFigure());
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		return new ChopboxAnchor(getFigure());
	}

	
}
