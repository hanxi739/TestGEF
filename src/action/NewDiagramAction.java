package action;
//创建action DiagramAction
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import constant.IImageConstant;
import testgef.Application;
import view.DiagramEditor;
import view.DiagramEditorInput;

public class NewDiagramAction extends Action implements ISelectionListener,IWorkbenchAction {
	private final IWorkbenchWindow window ;
	public final static String ID = "testGEF.action.NewDiagramAction";
	
	public NewDiagramAction(IWorkbenchWindow window) {
	        this.window = window;
	        setId(ID);
	        setText("New Diagram");
	        setToolTipText("Draw a waveform diagram.");
	        setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, IImageConstant.EDITORTITLE));
	        window.getSelectionService().addSelectionListener(this);
	}

	 
	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		window.getSelectionService().removeSelectionListener(this);
		
	}
	
	private String openFileDialog() {
        FileDialog dialog = new FileDialog(window.getShell(), SWT.OPEN);
        dialog.setText("新建波形文件");
        dialog.setFilterExtensions(new String[] { ".diagram" });
        return dialog.open();
    }
	
	public void run() {
        String path = openFileDialog();
        DiagramEditor.saveFilePath = path+".diagram";
        System.out.println("path："+path);
        if (path != null) {
            IEditorInput input = new DiagramEditorInput(new Path(path));
            IWorkbenchPage page = window.getActivePage();
            try {
                page.openEditor(input,DiagramEditor.EDITOR_ID,true);
            } catch (PartInitException e) {
                // handle error
            }
        }
    }

}
