package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.ui.views.properties.ColorPropertyDescriptor;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import tools.ResolveXML;

public class ComponentModel extends AbstractModel{

	private List<Property> propertyList;//维护一个属性列表，用于展示该组件的属性信息。注意对该对象的初始化和修改要同步到component对象中，或者直接在最终保存文件的时候利用该对象信息对component进行初始化
	//private Component component;//维护一个完整的组件类对象，用于之后生成波形配置文件
	private Rectangle constraint;//约束
	private String objName;
	
	
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
	

	public String getObjName() {
		return objName;
	}


	public void setObjName(String objName) {
		this.objName = objName;
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
    			PropertyDescriptor id = new PropertyDescriptor(i,"id");
    			id.setCategory("Standard");
    			descriptorList.add(id);
    			}
    		else if(pro.getName().equals("status")) {//status
    			TextPropertyDescriptor status= new TextPropertyDescriptor(i,"status");
    			status.setCategory("Standard");
    			descriptorList.add(status);
    			}
    		else {
    			switch(pro.getType()) {

    				case 1://只读    
    					PropertyDescriptor pro1 = new PropertyDescriptor(i,pro.getName());
    					pro1.setCategory("Parameters");
    					descriptorList.add(pro1);
    					break;
    				case 2://下拉框
    					String [] values =pro.getNote().split(";");
    					ComboBoxPropertyDescriptor pro2= new ComboBoxPropertyDescriptor(i,pro.getName(),values);
    					pro2.setCategory("Parameters");
    					descriptorList.add(pro2);
    					break;
    				case 3://可编辑
    					TextPropertyDescriptor pro3 = new TextPropertyDescriptor(i,pro.getName());
    					pro3.setCategory("Parameters");
    					descriptorList.add(pro3);
    					break;
    				default:
    					PropertyDescriptor proDesc1 = new TextPropertyDescriptor(i,pro.getName());
    					String name1 =proDesc1.getDisplayName();
    	    			if(name1.equals("category")|| name1.equals("codeLocation")||name1.equals("resourceUsed")|| name1.equals("type")) {
    	        			proDesc1.setCategory("resourceInfo");
    	        		}
    					descriptorList.add(proDesc1);
    					
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
