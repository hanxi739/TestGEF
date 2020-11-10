package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import tools.ResolveXML;

public class ComponentModel extends AbstractModel{

	private List<Property> propertyList;
	private Rectangle constraint;//约束
	
	
	public static final String P_CONSTRAINT = "_constraint";//通过字符串来辨识改变的属性种类

	
	//connection:管理连接信息//////////////////////////////
	public static final String P_SOURCE_CONNECTION="_source_connection";
	public static final String P_TARGET_CONNECTION="_target_connection";
	private List sourceConnection = new ArrayList();
	private List targetConnection = new ArrayList();
	
	public ComponentModel() {
		super();
	}
	
	public ComponentModel(List<Property> propertyList) {
		super();
		this.propertyList = propertyList;
	}

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
	
	public List getSourceConnection() {
		return sourceConnection;
	}

	public void setSourceConnection(List sourceConnection) {
		this.sourceConnection = sourceConnection;
	}

	public List getTargetConnection() {
		return targetConnection;
	}

	public void setTargetConnection(List targetConnection) {
		this.targetConnection = targetConnection;
	}
	
	//-----------------------------------管理参数信息---------------------------
	public List<Property> getPropertyList() {
			return propertyList;
		}

	public void setPropertyList(List<Property> propertyList) {
			this.propertyList = propertyList;
		}
		
		
	//------------------------重载IPropertySource接口的方法，显示组件的参数-------------------------
    /*
     * 其实，Property View中用TableView来显示属性。第一列是属性名称，第二列是属性值。
     * IPropertyDescriptor[]数组就是用来设置属性名称的，这里我们只提供了一个属性，并命名为Greeting
     * */
		
	public IPropertyDescriptor[] getPropertyDescriptors() {
    	ResolveXML resolve = new ResolveXML();
    	//setPropertyList(resolve.getPropertyList(id));
    	ArrayList<IPropertyDescriptor>descriptorList =new ArrayList<IPropertyDescriptor>();
    	
    	for(int i=0; i<propertyList.size();i++) {
    		Property pro = propertyList.get(i);

    		if(pro.getName().equals("id")){//id
    			descriptorList.add(new PropertyDescriptor(i,"id"));
    			}
    		else if(pro.getName().equals("status")) {//status
    			descriptorList.add(new TextPropertyDescriptor(i,"status"));
    			}
    		else {
    			switch(pro.getType()) {

    				case 1://只读
    					descriptorList.add(new PropertyDescriptor(i,pro.getName()));
    					break;
    				case 2://下拉框
    					String [] values =pro.getNote().split(";"); 
    					descriptorList.add(new ComboBoxPropertyDescriptor(i,pro.getName(),values));
    					break;
    				case 3://可编辑
    					descriptorList.add(new TextPropertyDescriptor(i,pro.getName()));
    					break;
    				default:
    					descriptorList.add(new TextPropertyDescriptor(i,pro.getName()));
    			}
    		}
    	}
    	int size = descriptorList.size();
    	IPropertyDescriptor[] descriptos = descriptorList.toArray(new IPropertyDescriptor[size]);
    	return descriptos;
    }
    
  

	//使用属性的ID来获得该属性在属性视图的值
    public Object getPropertyValue(Object id) {
    	if((Integer)id<propertyList.size()) {
    		if(propertyList.get((Integer)id).getType()==2) {//ComboBox返回的是序号需要转换为Integer类型。否则可能不显示可选条目。
    			Property pro = propertyList.get((Integer)id);
    			String value = pro.getValue();
    			String [] values =pro.getNote().split(";"); 
    			int index = 0;
    			for(int i=0;i<values.length;i++) {
    				if(value.equals(values[i])) {
    					index = i;
    				}
    			}
    			return index;
    		}
    		else {
    			return propertyList.get((Integer)id).getValue();
    		}
    	}
    	return null;
    }
    //判断属性视图中的属性值是否改变，如果没有指定的属性则返回false
    public boolean isPropertySet(Object id) {
    	return true;
    }
    
    //设置指定ID的属性值，如果该属性不能改变或者没有这个属性，则不做任何事情
    public void setPropertyValue(Object id, Object value) {
    	if((Integer)id<propertyList.size()) {
    		if(propertyList.get((Integer)id).getType()==2) {//value为ComboBox中值的序号，而不是ComboBox中显示的名字    			
    			Property pro = propertyList.get((Integer)id);
    			String [] values =pro.getNote().split(";"); 
    			String trueValue = values[(Integer)value];
    			pro.setValue(trueValue);
    			propertyList.set((Integer)id, pro);
    		}
    		else {
    			Property pro = propertyList.get((Integer)id);
        		pro.setValue((String)value);
        		propertyList.set((Integer)id, pro);
    		}
    		for(Property pro:propertyList) {
    			System.out.println(pro.getName()+":"+pro.getValue());
    		}
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
