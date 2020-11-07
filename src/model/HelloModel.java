package model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class HelloModel extends AbstractModel{

	private String text = "Hello World";
	private int code = 1;
	private String id_value="19873429878570780";
	private Rectangle constraint;//约束
	
	
	public static final String P_CONSTRAINT = "_constraint";//通过字符串来辨识改变的属性种类
	public static final String P_TEXT = "_text";//添加字符串的ID，这样改变图形的文本时可以通知其EditPart
	public static final String P_CODE = "_code";//添加字符串的ID，这样改变图形的文本时可以通知其EditPart
	public static final String P_ID = "_id";//添加字符串的ID，这样改变图形的文本时可以通知其EditPart
	
	//connection:管理连接信息//////////////////////////////
	public static final String P_SOURCE_CONNECTION="_source_connection";
	public static final String P_TARGET_CONNECTION="_target_connection";
	private List sourceConnection = new ArrayList();
	private List targetConnection = new ArrayList();
	
	public void addSourceConnection(Object connx) {//添加输入
		sourceConnection.add(connx);
		firePropertyChange(P_SOURCE_CONNECTION,null,null);//通知editPart添加了一个输入
	}
	
	public void addTargetConnection(Object connx) {//添加输出
		targetConnection.add(connx);
		firePropertyChange(P_TARGET_CONNECTION,null,null);//通知editPart添加了一个输入
	}
	
	public List getModelSourceConnections() {//获得输入节点
		return sourceConnection;
	}
	
	public List getModelTargetConnections() {//获得输出节点
		return targetConnection;
	}
	
	public void removeSourceConnection(Object connx) {//移除输入
		sourceConnection.remove(connx);
		firePropertyChange(P_SOURCE_CONNECTION,null,null);
	}
	
	public void removeTargetConnection(Object connx) {//移除输出
		targetConnection.remove(connx);
		firePropertyChange(P_TARGET_CONNECTION,null,null);
	}
	
	public String getId() {
        return id_value;
    }

    public void setId(String id) {
        this.id_value = id;
        firePropertyChange(P_TEXT,null,id);//图形文本改变时通知其EditPart
    }
    
	public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        firePropertyChange(P_TEXT,null,text);//图形文本改变时通知其EditPart
    }
    
    public int getCode() {
        return code;
    }

    public void setText(int code) {
        this.code = code;
        firePropertyChange(P_CODE,null,code);//图形文本改变时通知其EditPart
    }
    

    //下面是重载IPropertySource接口的方法
    /*
     * 其实，Property View中用TableView来显示属性。第一列是属性名称，第二列是属性值。
     * IPropertyDescriptor[]数组就是用来设置属性名称的，这里我们只提供了一个属性，并命名为Greeting
     * */
    public IPropertyDescriptor[] getPropertyDescriptors() {
    	String []values = {"1","2","3"}; 
    	IPropertyDescriptor[] descriptors = new IPropertyDescriptor[] {
    			new TextPropertyDescriptor(P_TEXT,"Greeting"),
    			new ComboBoxPropertyDescriptor(P_CODE, "code", values),
    			new PropertyDescriptor(P_ID,"id")};
    	return descriptors;
    }
    
    //使用属性的ID来获得该属性在属性视图的值
    public Object getPropertyValue(Object id) {
    	if(id.equals(P_TEXT)) {//这里获得Property view文本属性的值
    		return text;
    	}
    	if(id.equals(P_CODE)) {
    		return code;
    	}
    	if(id.equals(P_ID)) {
    		return id_value;
    	}
    	return null;
    }
    //判断属性视图中的属性值是否改变，如果没有指定的属性则返回false
    public boolean isPropertySet(Object id) {
    	if(id.equals(P_ID)) {
    		return true;
    	}
    	if(id.equals(P_TEXT)) {
    		return true;
    	}
    	if(id.equals(P_CODE)) {
    		return true;
    	}
    	else {
    		return false;
    	}	
    }
    
    //设置指定ID的属性值，如果该属性不能改变或者没有这个属性，则不做任何事情
    public void setPropertyValue(Object id, Object value) {
    	if(id.equals(P_TEXT)) {//改变文本时设置文本的属性值
    		setText((String)value);
    	}
    	if(id.equals(P_CODE)) {//改变code时设置文本的属性值
    		setText((int)value);
    	}
    }
    
	public Rectangle getConstraint() {
		return constraint;
	}

	public void setConstraint(Rectangle rec) {
		this.constraint = rec;
		firePropertyChange(P_CONSTRAINT,null,constraint);
	}
    
}
