package view;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.IImageFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.editparts.ScalableRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.palette.ConnectionCreationToolEntry;
import org.eclipse.gef.palette.CreationToolEntry;
import org.eclipse.gef.palette.MarqueeToolEntry;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.SelectionToolEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.requests.SimpleFactory;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.AlignmentAction;
import org.eclipse.gef.ui.actions.DirectEditAction;
import org.eclipse.gef.ui.actions.ZoomInAction;
import org.eclipse.gef.ui.actions.ZoomOutAction;
import org.eclipse.gef.ui.parts.GraphicalEditor;
import org.eclipse.gef.ui.parts.GraphicalEditorWithPalette;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import constant.IImageConstant;
import model.AbstractConnectionModel;
import model.ArrowConnectionModel;
import model.Component;
import model.ContentsModel;
import model.ComponentModel;
import model.LineConnectionModel;
import model.Property;
import part.CustomSimpleFactory;
import part.PartFactory;
import testgef.Application;
import tools.ResolveXML;

public class DiagramEditor extends GraphicalEditorWithPalette {
	public static final String EDITOR_ID = "testGEF.DiagramEditor";
	GraphicalViewer viewer;//绘制、显示图形
	private ContentsModel parent ;
	private ResolveXML resolve ;
	private List<String> componentIDs = new ArrayList<String>();//存储组件库里的所有模型
	
	public DiagramEditor() {
		setEditDomain(new DefaultEditDomain(this));
	}

	public List<String> getComponentIDs() {
		return componentIDs;
	}

	public void setComponentIDs(List<String> componentIDs) {
		this.componentIDs = componentIDs;
	}

	protected void configureGraphicalViewer() {//第一步，把模型和控制器在视图GraphicalViewer中连接起来
		super.configureGraphicalViewer();
		viewer = getGraphicalViewer();
		//viewer.getControl().setBackground(new Color(null, 200 , 200 , 200));
		
		//----------------------9.1提供图形的缩放功能---------------------------------
		/*首先设置根图形的RootEditPart为ScalableRootEditPart（具有缩放功能的），那么依附于此的所有子图形都具有了缩放能力。
		 * 并且这里我们所使用的ScalableRootEditPart提供了一个ZoomManager类，可以被用来管理图形的最大化、最小化等操作。
		 * 对图形的缩放操作实际上是通过这个ZoomManager实现的。如果说ZoomManager还是个幕后主使的话，那么ZoomInAction和ZoomOutAction就是实际操作图形缩放的类 
		 * */
		ScalableRootEditPart rootEditPart = new ScalableRootEditPart();
		viewer.setRootEditPart(rootEditPart);
		ZoomManager manager= rootEditPart.getZoomManager();//获得ZoomManager
		//注册放大Action
		IAction action = new ZoomInAction(manager);
		getActionRegistry().registerAction(action);
		//注册缩小Action
		action = new ZoomOutAction(manager);
		getActionRegistry().registerAction(action);
		
		viewer.setEditPartFactory(new PartFactory());
		
	}

	protected void initializeGraphicalViewer() {
		parent =  new ContentsModel();
		//viewer.setContents(new HelloModel());//第二步,设置GraphicalViewer中显示的内容
		//ContentsModel parent = new ContentsModel();
		/*
		//不知道为什么这里添加了矩形约束后才显示的图形，上一步结束后没有显示
		List<String> ids = new ArrayList<String>();
		ids = getComponentIDs();
		List <Property> pros = resolve.getPropertyList(ids.get(0));
		ComponentModel child0 = new ComponentModel(pros);//创建一个子模型
		child0.setConstraint(new Rectangle(-1,-1,250,80));//长宽设置为-1可以使矩形随着里面的文字变化大小
		parent.addChild(child0);//将子模型添加到父模型中
	
		List <Property> pros1 = resolve.getPropertyList(ids.get(1));
		ComponentModel child1 = new ComponentModel(pros1);//创建一个子模型
		child1.setConstraint(new Rectangle(180,200,250,80));//长宽设置为-1可以使矩形随着里面的文字变化大小
		parent.addChild(child1);//将子模型添加到父模型中
		
		
		List <Property> pros2 = resolve.getPropertyList(ids.get(2));
		ComponentModel child2 = new ComponentModel(pros2);//创建一个子模型
		child2.setConstraint(new Rectangle(300,400,250,80));//长宽设置为-1可以使矩形随着里面的文字变化大小
		parent.addChild(child2);//将子模型添加到父模型中
		*/
		viewer.setContents(parent);
	}
	
	//----------------------实现图形的对齐---------------------------------
	protected void createActions() {
		super.createActions();
		ActionRegistry registry = getActionRegistry();
		IAction action = new DirectEditAction(this);
		registry.registerAction(action);
		//有必要renew 重新判断选中对象的action时
		getSelectionActions().add(action.getId());
		
		//水平方向对齐
		action = new AlignmentAction(this,PositionConstants.LEFT);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		
		action = new AlignmentAction(this,PositionConstants.CENTER);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		
		action = new AlignmentAction(this,PositionConstants.RIGHT);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		
		//垂直方向对齐
		action = new AlignmentAction(this,PositionConstants.TOP);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		
		action = new AlignmentAction(this,PositionConstants.MIDDLE);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		
		action = new AlignmentAction(this,PositionConstants.BOTTOM);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		}

	@SuppressWarnings("unchecked")//?什么意思

	//-----------------------------dirty check--------------------------
	
	public boolean isDirty() {
		return getCommandStack().isDirty();//返回true时在文档前面加一个*表示dirty
	}
	
	public void commandStackChanged(EventObject event) {
		firePropertyChange(IEditorPart.PROP_DIRTY);
		super.commandStackChanged(event);
	}
	@Override
	public void doSave(IProgressMonitor monitor) {
		getCommandStack().markSaveLocation();
		//-----------------------------------获得diagram的节点及连接信息----------------------------------
		  ContentsModel diagram =parent; //如何获得父模型对象呢？
          List<ComponentModel> nodes = new  ArrayList<ComponentModel>();
          nodes = diagram.getChildren();
          int nodeCount = nodes.size();//节点的个数
          System.out.println(diagram+"的子节点有"+nodeCount+"个"+"分别是：");
          for(ComponentModel node:nodes) {
              System.out.println(node);
              List<AbstractConnectionModel>  sourceConnections = new  ArrayList<AbstractConnectionModel>();//把这个节点作为source的连线的集合，由此可以得到该节点对应的所有输出端口
              List<AbstractConnectionModel>  targetConnections = new  ArrayList<AbstractConnectionModel>();//把这个节点作为target的连线的集合，由此可以得到该节点对应的所有输入端口
              sourceConnections =  node.getModelSourceConnections();
              targetConnections =  node.getModelTargetConnections();
            //遍历把这个节点作为target的连线的集合，由此可以得到该节点对应的所有输入端口
              System.out.print(node+"的输入节点有：");
              if(targetConnections.size()==0) {
                System.out.println(0);
              }
              for(int inputNum=0;  inputNum<targetConnections.size();inputNum++) {
                AbstractConnectionModel connection =  (AbstractConnectionModel)  targetConnections.get(inputNum);
                ComponentModel model_connx =  connection.getTarget();
                System.out.println(model_connx);
               }
              
            //遍历把这个节点作为source的连线的集合，由此可以得到该节点对应的所有输出端口
              System.out.print(node+"的输出节点有：");
              if(sourceConnections.size()==0) {
                System.out.println(0);
              }
               for(int outputNum=0;  outputNum<sourceConnections.size();outputNum++) {
                    AbstractConnectionModel connection =  (AbstractConnectionModel)  sourceConnections.get(outputNum);
                ComponentModel model_connx =  connection.getTarget();
                System.out.println(model_connx);
               }
          }
         
	}

//----------------------------------------初始化palette----------------------------------
	@Override
	protected PaletteRoot getPaletteRoot() {
		resolve = new ResolveXML();
		setComponentIDs(resolve.getComponnetsId());
		// 后面要重载的方法，在Palette中加上tools
		//（1）首先要创建一个palette的route
		PaletteRoot root = new PaletteRoot();
		
		//(2)接着创建一个工具组用于放置常规tools
		PaletteGroup toolGroup = new PaletteGroup("工具");
		
		//(3)创建一个GEF提供的“selection”工具并将其放到toolGroup中
		ToolEntry tool = new SelectionToolEntry();
		toolGroup.add(tool);
		root.setDefaultEntry(tool);//选择工具是默认选择的工具
		
		//(4)创建一个GEF提供的“Marquee多选”工具并将其放到toolGroup中
		tool = new MarqueeToolEntry();
		toolGroup.add(tool);
		
		//(5)创建一个Drawer(抽屉)放置绘图工具，该抽屉名称为“组件”
		PaletteDrawer drawer = new PaletteDrawer("组件");
		//指定"创建HelloModel模型"工具所对应的图标
		ImageDescriptor descriptor = AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, IImageConstant.COMPONENT);
		
		//（6）创建“创建HelloModel模型”工具

		List<String> componentIdList = new ArrayList<String>();
		componentIdList= resolve.getComponnetsId();
		
		for(String componentId:componentIdList) {
			CreationToolEntry creationEntry = new CreationToolEntry(componentId,//The character string displayed on a palette
					"创建"+componentId+"模型",//Tool提示
					new CustomSimpleFactory(ComponentModel.class,componentId),//创建模型的factory
					descriptor,//The image of 16*16 displayed on a palette
					descriptor);//The image of 24*24 displayed on a palette
			drawer.add(creationEntry);
		}
		
	
		//添加连接工具drawer
		PaletteDrawer connectionDrawer = new PaletteDrawer("连接");
		
		//创建连接工具1：lineConnection
		ImageDescriptor  lineConnectionDescriptor = AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, IImageConstant.LINECONNECTION);//指定图标
		//将连接工具放入drawer
		ConnectionCreationToolEntry connxCreationEntry1 = new ConnectionCreationToolEntry(
				"简单连接",//显示在palette上的文字
				"创建最简单的连接",//鼠标指在连接工具上时的文字提示
				new SimpleFactory(LineConnectionModel.class),//Thw factory which creates a model
				lineConnectionDescriptor,//在palette上的16*16图标
				lineConnectionDescriptor);//在palette上的24*24图标
		
		//创建连接工具2：arrowConnection
		ImageDescriptor  arrowConnectionDescriptor = AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, IImageConstant.ARROWCONNECTION);//指定图标
		//将连接工具放入drawer
		ConnectionCreationToolEntry connxCreationEntry2 = new ConnectionCreationToolEntry(
				"箭头连接",//显示在palette上的文字
				"创建带箭头的连接",//鼠标指在连接工具上时的文字提示
				new SimpleFactory(ArrowConnectionModel.class),//Thw factory which creates a model
				arrowConnectionDescriptor,//在palette上的16*16图标
				arrowConnectionDescriptor);//在palette上的24*24图标
		
		connectionDrawer.add(connxCreationEntry1);//连接工具1放入drawer
		connectionDrawer.add(connxCreationEntry2);//连接工具2放入drawer
		
		//(8)最后将创建的两组工具加到root上
		root.add(toolGroup);
		root.add(drawer);
		root.add(connectionDrawer);
		return root;
	}

}
