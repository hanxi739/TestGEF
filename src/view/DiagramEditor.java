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
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import constant.IImageConstant;
import model.AbstractConnectionModel;
import model.ArrowConnectionModel;
import model.ContentsModel;
import model.HelloModel;
import model.LineConnectionModel;
import part.PartFactory;
import testgef.Application;

public class DiagramEditor extends GraphicalEditorWithPalette {
	public static final String EDITOR_ID = "testGEF.DiagramEditor";
	Rectangle rec = new Rectangle(-1,-1,80,50);
	GraphicalViewer viewer;//绘制、显示图形
	private ContentsModel parent ;
	public DiagramEditor() {
		setEditDomain(new DefaultEditDomain(this));
	}
	
	protected void configureGraphicalViewer() {//第一步，把模型和控制器在视图GraphicalViewer中连接起来
		super.configureGraphicalViewer();
		viewer = getGraphicalViewer();
		
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
		
		HelloModel child0 = new HelloModel();//创建一个子模型
		child0.setConstraint(rec);//长宽设置为-1可以使矩形随着里面的文字变化大小
		parent.addChild(child0);//将子模型添加到父模型中
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
	@Override
	public void doSave(IProgressMonitor monitor) {
		getCommandStack().markSaveLocation();
		//-----------------------------------获得配置文件信息----------------------------------
		  ContentsModel diagram =parent; //如何获得父模型对象呢？
          List<HelloModel> nodes = new  ArrayList<HelloModel>();
          nodes = diagram.getChildren();
          int nodeCount = nodes.size();//节点的个数
          System.out.println(diagram+"的子节点有"+nodeCount+"个"+"分别是：");
          for(HelloModel node:nodes) {
              System.out.println(node);
              List<AbstractConnectionModel>  sourceConnections = new  ArrayList<AbstractConnectionModel>();//把这个节点作为source的连线的集合，由此可以得到该节点对应的所有输出端口
              List<AbstractConnectionModel>  targetConnections = new  ArrayList<AbstractConnectionModel>();//把这个节点作为target的连线的集合，由此可以得到该节点对应的所有输入端口
              sourceConnections =  node.getModelSourceConnections();
              targetConnections =  node.getModelTargetConnections();
            //遍历把这个节点作为target的连线的集合，由此可以得到该节点对应的所有输入端口
              System.out.print(node+"的输入端口有：");
              if(targetConnections.size()==0) {
                System.out.println(0);
              }
              for(int inputNum=0;  inputNum<targetConnections.size();inputNum++) {
                AbstractConnectionModel connection =  (AbstractConnectionModel)  targetConnections.get(inputNum);
                HelloModel model_connx =  connection.getTarget();
                System.out.println(model_connx);
               }
              
            //遍历把这个节点作为source的连线的集合，由此可以得到该节点对应的所有输出端口
              System.out.print(node+"的输出端口有：");
              if(sourceConnections.size()==0) {
                System.out.println(0);
              }
               for(int outputNum=0;  outputNum<sourceConnections.size();outputNum++) {
                    AbstractConnectionModel connection =  (AbstractConnectionModel)  sourceConnections.get(outputNum);
                HelloModel model_connx =  connection.getTarget();
                System.out.println(model_connx);
               }
          }
         
	}
		
	public boolean isDirty() {
		return getCommandStack().isDirty();//返回true时在文档前面加一个*表示dirty
	}
	
	public void commandStackChanged(EventObject event) {
		firePropertyChange(IEditorPart.PROP_DIRTY);
		super.commandStackChanged(event);
	}
	
	
//---------------------------------------------------------------------------------
	@Override
	protected PaletteRoot getPaletteRoot() {
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
		
		ArrayList<String> componentList = new ArrayList();
		componentList.add("com1");
		componentList.add("com2");
		componentList.add("com3");
		componentList.add("com4");
		
		for(int i=0;i<componentList.size();i++) {
			CreationToolEntry creationEntry = new CreationToolEntry(componentList.get(i),//The character string displayed on a palette
					"创建"+componentList.get(i)+"模型",//Tool提示
					new SimpleFactory(HelloModel.class),//创建模型的factory
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
