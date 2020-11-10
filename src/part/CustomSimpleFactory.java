package part;


import java.util.List;

import org.eclipse.gef.requests.SimpleFactory;

import model.ComponentModel;
import model.Property;
import tools.ResolveXML;

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
		System.out.println(model);
		return model;
	}
	 
	

}
