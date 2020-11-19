package part;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.gef.editparts.AbstractTreeEditPart;

import model.AbstractModel;
//反映模型的改变
public class CustomTreeEditPart extends AbstractTreeEditPart implements PropertyChangeListener{
	
	@Override
	public void activate() {
		super.activate();
		((AbstractModel)getModel()).addPropertyChangeListener(this);
	}
	@Override
	public void deactivate() {
		((AbstractModel)getModel()).removePropertyChangeListener(this);
		super.deactivate();
	}
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}

}
