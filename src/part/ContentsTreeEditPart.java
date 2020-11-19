package part;

import java.beans.PropertyChangeEvent;
import java.util.List;

import model.ContentsModel;

public class ContentsTreeEditPart extends CustomTreeEditPart {
	@Override
	protected List getModelChildren() {
		return ((ContentsModel)getModel()).getChildren();
	}
	
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals(ContentsModel.P_CHILDREN)) {
			refreshChildren();
		}
	}
}
