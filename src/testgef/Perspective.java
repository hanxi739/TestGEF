package testgef;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class Perspective implements IPerspectiveFactory {

	public static final String PERSPECTIVE_ID = "gef.tutorial.step.perspective";
	@Override	
	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(true);
        layout.setFixed(true);
	}
}