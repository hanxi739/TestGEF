package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;


/**
 *每个Model有责任把自己的改变通知给EditPart，由于所有的model都要通知对应的EditPart，而这些代码中借本都是重复语句，所以可以创建一个抽象类AbstractModel作为这些模型类的超类。 
 * 在AbstractModel类中使用java.beans.PropertyChangeSupport和java.beans.PropertyChangeListener类完成这个工作。
 * PropertyChangeSupport：工具类，绑定模型属性、管理监听器、产生属性变化事件
 * PropertyChangeListener：可以根据属性名注册到各种属性上
 */

/*
 * 属性视图：
 * 之前的例子中图形的文本都是固定的，可以使用属性视图（property view）来修改图形的属性，属性视图是通过org.eclipse.ui.views.properties.IPropertySource接口实现的。
 * 在GEF中，使用属性视图修改的是图形模型的属性（两者互相绑定，同时变化）。因此，只需要把IPropertySource接口和图形模型类结合起来就行了，这就需要图形模型类实现IPropertySource接口。
 * */


//用属性视图来对应于所有的图形模型，让AbstractModel来实现IPropertySource接口，并重载这个接口中的方法
public class AbstractModel implements IPropertySource{

	//listeners列表
	private PropertyChangeSupport listeners = new PropertyChangeSupport(this);
	
	//添加listeners
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		listeners.addPropertyChangeListener(listener);
	}
	
	//通知模型的改变
	public void firePropertyChange(String proName, Object oldValue, Object newValue) {
		listeners.firePropertyChange(proName,oldValue,newValue);
	}
	
	//删除listeners
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		listeners.removePropertyChangeListener(listener);
	}

	@Override
	public Object getEditableValue() {
		return this;//返回模型自身作为可编辑的属性值
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return new IPropertyDescriptor[0];//如果在抽象模型中返回null会出现异常，因此这里会返回一个长度为0的数组
	}

	@Override
	public Object getPropertyValue(Object id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPropertySet(Object id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void resetPropertyValue(Object id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		// TODO Auto-generated method stub
		
	}
}
