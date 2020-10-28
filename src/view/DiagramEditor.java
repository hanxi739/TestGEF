package view;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.ui.parts.GraphicalEditor;

import model.HelloModel;
import part.PartFactory;

public class DiagramEditor extends GraphicalEditor {
	public static final String EDITOR_ID = "testGEF.DiagramEditor";
	GraphicalViewer viewer;//绘制、显示图形

	public DiagramEditor() {
		setEditDomain(new DefaultEditDomain(this));
	}
	
	protected void configureGraphicalViewer() {//第一步，把模型和控制器在视图GraphicalViewer中连接起来
		super.configureGraphicalViewer();
		viewer = getGraphicalViewer();
		viewer.setEditPartFactory(new PartFactory());
	}

	protected void initializeGraphicalViewer() {
		viewer.setContents(new HelloModel());//第二步,设置GraphicalViewer中显示的内容

	}

	@Override
	public void doSave(IProgressMonitor monitor) {
	
	}

}
