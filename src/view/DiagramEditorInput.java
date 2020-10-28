package view;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPathEditorInput;
import org.eclipse.ui.IPersistableElement;

public class DiagramEditorInput implements IPathEditorInput{
	
	private IPath path;
	
	public DiagramEditorInput (IPath path) {
		this.path = path;
	}
	@Override
	public boolean exists() {
		return path.toFile().exists();
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return path.toString();
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public String getToolTipText() {
		return path.toString();
	}

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPath getPath() {
		return path;
	}
	
	public int hashCode() {
		return path.hashCode();
	}

}
