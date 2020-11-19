package part;

import java.beans.PropertyChangeEvent;

import model.ComponentModel;

public class ComponentTreeEditPart extends CustomTreeEditPart {
	@Override
	protected void refreshVisuals() {
		ComponentModel model = (ComponentModel)getModel();
		setWidgetText(model.getObjName());
	}
	
	/*
	 * 组件显示的名称不改变，所以不需要改函数
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals("id")) {
			refreshVisuals();
		}
	}
	*/
}
