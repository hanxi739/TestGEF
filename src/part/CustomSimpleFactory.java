package part;


import java.util.List;

import org.eclipse.gef.requests.SimpleFactory;

import model.ComponentModel;
import model.Property;
import tools.ResolveXML;
//自定义一个简单工厂类，在初始化palette时放在CreationToolEntry中用来创建模型。自定义主要是为了增加一个id参数用来区分不同的模型
public class CustomSimpleFactory extends SimpleFactory {
	private String componentId;
	public CustomSimpleFactory(Class aClass,String id) {
		super(aClass);
		this.componentId = id;
		// TODO Auto-generated constructor stub
	}
	
	public Object getNewObject() {
		ComponentModel model = (ComponentModel) super.getNewObject();
		ResolveXML resolve = new ResolveXML();
		List<Property> pros = resolve.getPropertyList(componentId);
		model.setPropertyList(pros);
		//System.out.println(model);
		return model;
	}
	 
	

}
