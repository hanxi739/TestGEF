package part;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import model.ComponentModel;
import model.ContentsModel;
//editpart工厂，将这些TreeEditPart和相应的模型联系起来
public class TreeEditPartFactory implements EditPartFactory {

	@Override
	public EditPart createEditPart(EditPart context, Object model) {
		EditPart part =null;
		if(model instanceof ContentsModel) {
			part = new ContentsTreeEditPart();
		}
		else if(model instanceof ComponentModel) {
			part = new ComponentTreeEditPart();
		}
		
		if(part != null) {
			part.setModel(model);
		}
		return part;
	}

}
