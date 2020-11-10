package part;
//连接模型与控制器
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import model.ArrowConnectionModel;
import model.ContentsModel;
import model.ComponentModel;
import model.LineConnectionModel;

public class PartFactory implements EditPartFactory {

	

	/**
     * Maps an object to an EditPart. 
     * @throws RuntimeException if no match was found (programming error)
     */
    private EditPart getPartForElement(Object modelElement) {
        //第一步，根据模型类创建其控制器
    	if(modelElement instanceof ContentsModel) {
    		return new ContentsEditPart();
    	}
    	else if (modelElement instanceof ComponentModel)
            return new HelloEditorPart();
    	else if(modelElement instanceof LineConnectionModel) {
    		return new LineConnectionEditPart();
    	}
    	else if(modelElement instanceof ArrowConnectionModel) {
    		return new ArrowConnectionEditPart();
    	}
        throw new RuntimeException(
                "Can't create part for model element: "
                        + ((modelElement != null) ? modelElement.getClass().getName() : "null"));
    }
    @Override
	public EditPart createEditPart(EditPart context, Object model) {//第二步，连接模型和其控制器
		//get EditPart for model element
        EditPart part = getPartForElement(model);
        //store model element in EditPart
        part.setModel(model); 
        return part;
	}
}
