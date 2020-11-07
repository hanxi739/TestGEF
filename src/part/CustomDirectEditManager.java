package part;
//图形上直接编辑相关的类
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.swt.widgets.Text;

import model.HelloModel;

public class CustomDirectEditManager extends DirectEditManager {

	private HelloModel helloModel;//要修改该模型的文本属性
	public CustomDirectEditManager(GraphicalEditPart source, Class editorType, CellEditorLocator locator) {
		super(source, editorType, locator);
		helloModel = (HelloModel)source.getModel();//获得HelloModel模型，在后面的initCellEditor()中将HelloModel的文本值设为cell editor的初始值
	}

	@Override
	protected void initCellEditor() {
		getCellEditor().setValue(helloModel.getText());//在显示一个cell editor之前，先给它设置一个值，这里的值是获得图形模型的文本
		Text text = (Text)getCellEditor().getControl();//在所选的TextCellEditor的Text控件的所有文本都显示为选择状态
		text.selectAll();
	}

}
