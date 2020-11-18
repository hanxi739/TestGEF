package model;
//正常情况下,Properties View中的propety(category)是按照字母排序的,跟我们要求不相符,效果不好
//新建一个Class 继承 PropertySheetSorter,分别实现compare和compareCategories方法,让两个方法都return 0,这样,原来的自动排序就失效了,就可以按照property添加时候的顺序排列,达到我们要求的效果.
import org.eclipse.ui.views.properties.IPropertySheetEntry;
import org.eclipse.ui.views.properties.PropertySheetSorter;

public class CustomPropertySheetSorter extends PropertySheetSorter {
	
	
	 public int compare(IPropertySheetEntry entryA, IPropertySheetEntry entryB) {
	     return 0;
	   }
	 
	 public int compareCategories(String categoryA, String categoryB) {
	  return 0;
	 }
}
