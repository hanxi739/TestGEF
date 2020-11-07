package part;
//图形上直接编辑相关的类
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Text;

public class CustomCellEditorLocator implements CellEditorLocator {

	private IFigure figure;//(1)Text控件要处于Figure所在的位置
	
	public CustomCellEditorLocator(IFigure f) {//因此要在构造函数中得到为哪一个Figure对象设置Text控件
		figure = f;
	}
	@Override
	public void relocate(CellEditor cellEditor) {//
		Text text = (Text) cellEditor.getControl();
		//Text控件尺寸和figure一样
		Rectangle rect = figure.getBounds().getCopy();
		figure.translateToAbsolute(rect);
		text.setBounds(rect.x,rect.y,rect.width,rect.height);
		

	}

}
