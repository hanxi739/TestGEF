package model;

import org.eclipse.ui.views.properties.PropertySheetPage;

public class CustomPropertySheetPage extends PropertySheetPage {
	
	public CustomPropertySheetPage(){
		  this.setSorter(new CustomPropertySheetSorter());
	}
}
