package part;



import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import model.HelloModel;

public class HelloEditorPart extends AbstractGraphicalEditPart {

	@Override
	protected IFigure createFigure() {
		HelloModel model = (HelloModel)getModel();
		Label label = new Label();
		label.setText(model.getText());
        return label;
	}

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub
		
	}

}
