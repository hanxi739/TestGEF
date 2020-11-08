package action;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.gef.ui.actions.ActionBarContributor;
import org.eclipse.gef.ui.actions.AlignmentRetargetAction;
import org.eclipse.gef.ui.actions.DeleteRetargetAction;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.actions.RedoRetargetAction;
import org.eclipse.gef.ui.actions.UndoRetargetAction;
import org.eclipse.gef.ui.actions.ZoomInRetargetAction;
import org.eclipse.gef.ui.actions.ZoomOutRetargetAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.actions.ActionFactory;
//添加工具按钮来执行undo/redo Action
public class DiagramActionBarContributor extends ActionBarContributor {

	@Override
	protected void buildActions() {
		addRetargetAction(new UndoRetargetAction());
		addRetargetAction(new RedoRetargetAction());
		addRetargetAction(new DeleteRetargetAction());
		
		//retarget缩放Action
		addRetargetAction(new ZoomInRetargetAction());
		addRetargetAction(new ZoomOutRetargetAction());
		
		//retarget对齐Action
		addRetargetAction(new AlignmentRetargetAction(PositionConstants.LEFT));
		addRetargetAction(new AlignmentRetargetAction(PositionConstants.CENTER));
		addRetargetAction(new AlignmentRetargetAction(PositionConstants.RIGHT));
		addRetargetAction(new AlignmentRetargetAction(PositionConstants.TOP));
		addRetargetAction(new AlignmentRetargetAction(PositionConstants.MIDDLE));
		addRetargetAction(new AlignmentRetargetAction(PositionConstants.BOTTOM));
	}

	@Override
	protected void declareGlobalActionKeys() {
		// TODO Auto-generated method stub

	}
	
	public void contributeToToolBar(IToolBarManager toolBarManager) {
		toolBarManager.add(getAction(ActionFactory.UNDO.getId()));
		toolBarManager.add(getAction(ActionFactory.REDO.getId()));
		toolBarManager.add(getAction(ActionFactory.DELETE.getId()));
		
		//加上分割条
		toolBarManager.add(new Separator());
		//加上水平对齐按钮
		toolBarManager.add(getActionRegistry().getAction(GEFActionConstants.ALIGN_LEFT));
		toolBarManager.add(getActionRegistry().getAction(GEFActionConstants.ALIGN_CENTER));
		toolBarManager.add(getActionRegistry().getAction(GEFActionConstants.ALIGN_RIGHT));
		//加上垂直对齐按钮
		toolBarManager.add(getActionRegistry().getAction(GEFActionConstants.ALIGN_TOP));
		toolBarManager.add(getActionRegistry().getAction(GEFActionConstants.ALIGN_MIDDLE));
		toolBarManager.add(getActionRegistry().getAction(GEFActionConstants.ALIGN_BOTTOM));
		
		//加上分割条
		toolBarManager.add(new Separator());
		//加上 缩放按钮，注意这里的缩放Action的ID在GEF中已经定义了一些常数
		toolBarManager.add(getActionRegistry().getAction(GEFActionConstants.ZOOM_IN));
		toolBarManager.add(getActionRegistry().getAction(GEFActionConstants.ZOOM_OUT));
	}

	//为什么没有显示工具条的菜单呢？?
	//因为要将ApplicationWorkbenchWindowAdvisor类中的 configure.setShowCoolBar(false)改为true
}
