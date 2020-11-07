package part;
//给连接加上连接的修饰——箭头
//不是很懂，为什么不直接让这个类继承AbstractConnectionEditPart，
//而是让CustomAbstractConnectionEditPart继承AbstractConnectionEditPart，然后再让ArrowConnectionEditPart继承CustomAbstractConnectionEditPart？
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.gef.EditPart;

public class ArrowConnectionEditPart extends CustomAbstractConnectionEditPart{
	protected IFigure createFigure() {
		PolylineConnection connection = new PolylineConnection();//还是多段线连接
		connection.setTargetDecoration(new PolygonDecoration());//不过这里加上了箭头修饰
		return connection;
	}

}
