package part;

import org.eclipse.gef.editparts.AbstractConnectionEditPart;
//和HelloModel不同的是，连接的模型是从org.eclipse.gef.editparts.AbstractConnectionEditPart派生来的。这样派生出来的控制器EditPart会默认绘制多段线连接PolylineConnection
public class LineConnectionEditPart extends CustomAbstractConnectionEditPart {

}
