package testgef;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class Perspective implements IPerspectiveFactory {

	
	@Override	
	public void createInitialLayout(IPageLayout layout) {
		final String properties = "org.eclipse.ui.views.PropertySheet";
		final String outline = "org.eclipse.ui.views.ContentOutline";
		final String editorArea = layout.getEditorArea();
		IFolderLayout BottomFolder = layout.createFolder("Bottom", IPageLayout.BOTTOM, 0.5f, editorArea);
		IFolderLayout rightTopFolder = layout.createFolder("RightTop", IPageLayout.RIGHT, 0.8f, editorArea);
		BottomFolder.addView(properties);
		
		rightTopFolder.addView(outline);
		
		
		layout.setEditorAreaVisible(true);
	}
}
