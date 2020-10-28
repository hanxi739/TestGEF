package view.editor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.ui.parts.GraphicalEditor;

import model.HelloModel;
import part.PartFactory;

public class DiagramEditor extends GraphicalEditor {

	public static final String EDITOR_ID = "testGEF.view.editor.DiagramEditor";
	GraphicalViewer viewer;
	public DiagramEditor() {
        setEditDomain(new DefaultEditDomain(this));
  }
	@Override
	protected void initializeGraphicalViewer() {
		viewer.setContents(new HelloModel());
	}
	
	protected void configureGraphicalViewer(){
        
        super.configureGraphicalViewer();
        viewer = getGraphicalViewer();
        viewer.setEditPartFactory(new PartFactory());   
    }
	@Override
	public void doSave(IProgressMonitor monitor) {
		// TODO Auto-generated method stub

	}

}
