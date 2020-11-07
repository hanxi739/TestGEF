package part;
import model.AbstractModel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

public abstract class EditPartWithListener extends AbstractGraphicalEditPart implements PropertyChangeListener {

	public void activate() {
		super.activate();
		((AbstractModel)getModel()).addPropertyChangeListener(this);//EditPart把自己注册为监听器
	}
	
	public void deactivate() {
		super.activate();
		((AbstractModel)getModel()).removePropertyChangeListener(this);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	protected IFigure createFigure() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub

	}

}
