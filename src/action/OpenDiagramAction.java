package action;

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

public class OpenDiagramAction extends Action implements ISelectionListener,IWorkbenchAction{
	private final IWorkbenchWindow window ;
	public final static String ID = "testGEF.action.OpenDiagramAction";
	private IStructuredSelection selection;
	
	public OpenDiagramAction(IWorkbenchWindow window) {
	        this.window = window;
	        setId(ID);
	        setText("Open Diagram");
	        setToolTipText("Open a waveform diagram.");
	        setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, IImageConstant.EDITORTITLE));
	        window.getSelectionService().addSelectionListener(this);
	}

	@Override
	public void dispose() {
		window.getSelectionService().removeSelectionListener(this);
		
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		// TODO Auto-generated method stub
		
	}
	
	private String openFileDialog() {
        FileDialog dialog = new FileDialog(window.getShell(), SWT.OPEN);
        dialog.setText("打开波形文件");
        //dialog.setFilterExtensions(new String[] { ".diagram" });
        return dialog.open();
    }
	
	public void run() {
        String path = openFileDialog();
        DiagramEditor.saveFilePath = path;
        DiagramEditor.openFile = true;
        System.out.println("open path："+path);
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
