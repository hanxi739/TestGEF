package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IImageFigure;
import org.eclipse.draw2d.Layer;
import org.eclipse.draw2d.LayeredPane;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
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
import org.eclipse.gef.ui.parts.ContentOutlinePage;
import org.eclipse.gef.ui.parts.GraphicalEditor;
import org.eclipse.gef.ui.parts.GraphicalEditorWithPalette;
import org.eclipse.gef.ui.parts.TreeViewer;
import org.eclipse.gef.ui.properties.UndoablePropertySheetEntry;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheetPage;

import constant.IImageConstant;
import model.AbstractConnectionModel;
import model.ArrowConnectionModel;
import model.Component;
import model.ContentsModel;
import model.CustomPropertySheetPage;
import model.ComponentModel;
import model.LineConnectionModel;
import model.Node;
import model.Parameter;
import model.Property;
import model.ResourceInfo;
import model.Waveform;
import part.CustomSimpleFactory;
import part.PartFactory;
import part.TreeEditPartFactory;
import testgef.Application;
import tools.ResolveXML;

public class DiagramEditor extends GraphicalEditorWithPalette {
	//public static String openFilePath = null;
	public static String saveFilePath = null;
	public static String XMLFilePath = null;
	public static boolean openFile = false;
	public static final String EDITOR_ID = "testGEF.DiagramEditor";
	GraphicalViewer viewer;//绘制、显示图形
	private ContentsModel contentsModel ;
	private ResolveXML resolve ;
	private List<String> componentIDs = new ArrayList<String>();//存储组件库里的所有模型
	static Image BG_IMAGE=new Image(null,IImageConstant.EDITORBG);
	
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
		String fileName = saveFilePath.trim();
		fileName = fileName.substring(fileName.lastIndexOf("\\")+1);
		super.setPartName(fileName);//设置编辑器的名称为当前文件的名字
		viewer = getGraphicalViewer();
		//viewer.getControl().setBackground(ColorConstants.lightGray);
		//viewer.getControl().setBackgroundImage(BG_IMAGE);
		ScalableRootEditPart rootEditPart = new ScalableRootEditPart();
		
		/*
		//------------------------------------给编辑器添加背景图片-------------------------------------
		//覆盖ScalableFreeformRootEditPart的createlayers方法以便增加自己的层
		ScalableFreeformRootEditPart rootEditPart =  new ScalableFreeformRootEditPart() {
			protected void createLayers(LayeredPane layeredPane) {
				Layer layer = new FreeformLayer() {
	                protected void paintFigure(Graphics graphics) {
	                    super.paintFigure(graphics);
	                    //在层上绘制图片，也可以绘制其他图形作为背景，GEF的网格线就是一例
	                    graphics.drawImage(BG_IMAGE,0,0);
	                }
				};
            layeredPane.add(layer);
            super.createLayers(layeredPane);
			}
		};
	*/
		viewer.setRootEditPart(rootEditPart);
				
		//----------------------9.1提供图形的缩放功能---------------------------------
		/*首先设置根图形的RootEditPart为ScalableRootEditPart（具有缩放功能的），那么依附于此的所有子图形都具有了缩放能力。
		 * 并且这里我们所使用的ScalableRootEditPart提供了一个ZoomManager类，可以被用来管理图形的最大化、最小化等操作。
		 * 对图形的缩放操作实际上是通过这个ZoomManager实现的。如果说ZoomManager还是个幕后主使的话，那么ZoomInAction和ZoomOutAction就是实际操作图形缩放的类 
		 * */
	

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
		contentsModel =  new ContentsModel();
		//如果是打开diagram文件，就读入输入流将旧模型写入editor，否则打开空白的editor
		if(openFile) {
			try {
				ObjectInputStream in = new ObjectInputStream(new FileInputStream(saveFilePath));
				contentsModel = (ContentsModel) in.readObject();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		/*
		//不知道为什么这里添加了矩形约束后才显示的图形，上一步结束后没有显示
		List<String> ids = new ArrayList<String>();
		ids = getComponentIDs();
		List <Property> pros = resolve.getPropertyList(ids.get(0));
		ComponentModel child0 = new ComponentModel(pros);//创建一个子模型
		child0.setObjName("123");
		child0.setConstraint(new Rectangle(-1,-1,250,80));//长宽设置为-1可以使矩形随着里面的文字变化大小
		contentsModel.addChild(child0);//将子模型添加到父模型中
	
		List <Property> pros1 = resolve.getPropertyList(ids.get(1));
		ComponentModel child1 = new ComponentModel(pros1);//创建一个子模型
		child1.setObjName("456");
		child1.setConstraint(new Rectangle(180,200,250,80));//长宽设置为-1可以使矩形随着里面的文字变化大小
		contentsModel.addChild(child1);//将子模型添加到父模型中
		
		
		List <Property> pros2 = resolve.getPropertyList(ids.get(2));
		ComponentModel child2 = new ComponentModel(pros2);//创建一个子模型
		child2.setConstraint(new Rectangle(300,400,250,80));//长宽设置为-1可以使矩形随着里面的文字变化大小
		child2.setObjName("789");
		contentsModel.addChild(child2);//将子模型添加到父模型中
		
		ArrowConnectionModel conx1 =  new ArrowConnectionModel();
		conx1.setSource(child0);
		conx1.setTarget(child1);
		conx1.attachSource();
		conx1.attachTarget();
		ArrowConnectionModel conx2= new ArrowConnectionModel();
		conx2.setSource(child1);
		conx2.setTarget(child2);
		conx2.attachSource();
		conx2.attachTarget();
		*/
		viewer.setContents(contentsModel);//第二步,设置GraphicalViewer中显示的内容
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
		XMLFilePath = saveFilePath.replace("diagram", "xml");
		System.out.println(System.getProperty("user.dir"));
        //-------------------------------------具体执行--------------------------
     	getCommandStack().markSaveLocation();
     	//-----------------------------------获得diagram的节点及连接信息----------------------------------
		  ContentsModel diagram = contentsModel; //如何获得父模型对象呢？
		  try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(saveFilePath));
			out.writeObject(diagram);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      List<ComponentModel> nodes = new  ArrayList<ComponentModel>();
	      nodes = diagram.getChildren();
	      int nodeCount = nodes.size();//节点的个数
	      
	     
	      List<Node> nodeObjects = getNodeInfo(nodes);//获取diagram中出现的所有节点信息
	      List<Component> componentObjects = getCompoInfo(nodes) ;//获取diagram中出现的组件对象的具体信息
	      Waveform wave = new Waveform(nodeCount,nodeObjects,componentObjects);
	      resolve = new ResolveXML();
	      resolve.saveWaveform(wave,XMLFilePath);
     //------------------------------------执行完毕-----------------------------

	}
	
	public List<Node> getNodeInfo(List<ComponentModel> obj){
		List<ComponentModel> nodes = new ArrayList<ComponentModel>();
		nodes = obj;
		
		List<Node> nodeList = new ArrayList<Node>();
		 //获取每个节点的参数信息和整个diagram的连接信息	
		for(ComponentModel node:nodes) {
		  String nodeId = node.getObjName();
		  String componentId = node.getPropertyList().get(0).getValue();
		  String in = "";
		  String out = "";
		  List<ComponentModel> inputs = getInputs(node);
		  for(ComponentModel input:inputs) {
			  if(in !="") {
				  in = in+";"+input; 
			  }
			  else {
				  in = in+input;
			  }
		  }
		  
		  List<ComponentModel> outputs = getOutputs(node);
		  for(ComponentModel output:outputs) {
			  if(out !="") {
				  out = out+";"+output; 
			  }
			  else {
				  out = out+output;
			  }
		  }
		  
		  Node nodeinfo = new Node(nodeId,componentId,in,out);
		  nodeList.add(nodeinfo);
		 }
		return nodeList;
	}
	
	public List<Component> getCompoInfo(List<ComponentModel> obj){
		List<ComponentModel> nodes = new ArrayList<ComponentModel>();
		nodes = obj;
	
		List<Component> compoList = new ArrayList<Component>();
		for(ComponentModel node:nodes) {
			Component compo = getComponentObject(node);
			compoList.add(compo);
		}
		return compoList;
	}
	
	//--------------------------------------定义获得某个ComponentModel的全部参数信息以及inputs和outputs的方法-------------------------------------------
	//获取当前组件对象的信息，创建一个Component对象
	public Component getComponentObject(ComponentModel node) {
	    
        List<ResourceInfo> resourceInfoList = new ArrayList<ResourceInfo>();
        List<Parameter> paraList = new ArrayList<Parameter>();
        
        List<Property> propertyList = node.getPropertyList();
        String id = propertyList.get(0).getValue();
        String status = propertyList.get(1).getValue();
        //获得当前对象的各种参数信息
        for(int i= 2; i<propertyList.size(); ) {
      	  if(propertyList.get(i).getName().equals("category")) {
      		  String category = propertyList.get(i).getValue();
      		  String codeLocation = propertyList.get(i+1).getValue();
      		  String resourceUsed = propertyList.get(i+2).getValue();
      		  String type = propertyList.get(i+3).getValue();
      		  ResourceInfo info = new ResourceInfo(category,codeLocation,resourceUsed,type);
      		  resourceInfoList.add(info);
      		  i = i+4;
      		  
      	  }
      	  else {
      		  String name = propertyList.get(i).getName();
      		  String value = propertyList.get(i).getValue();
      		  Parameter para = new Parameter(name,value);
      		  paraList.add(para);
      		  i = i+1;            		  
      	  }
        }
        Component compo = new Component(id,status,resourceInfoList,paraList);
        compo.setObjId(node.getObjName());
        System.out.println("diagram editor中的component objId:"+node.getObjName());
        return compo;
	}
	
	
	public List<ComponentModel> getInputs(ComponentModel node){
		List<ComponentModel> inputs = new ArrayList<ComponentModel>();
		List<AbstractConnectionModel>  targetConnections = new  ArrayList<AbstractConnectionModel>();//把这个节点作为target的连线的集合，由此可以得到该节点对应的所有输入端口
		targetConnections =  node.getModelTargetConnections();
		 //遍历把这个节点作为target的连线的集合，由此可以得到该节点对应的所有输入端口
        for(int inputNum=0;  inputNum<targetConnections.size();inputNum++) {
           AbstractConnectionModel connection =  (AbstractConnectionModel)  targetConnections.get(inputNum);
           ComponentModel model_connx =  connection.getSource();
           inputs.add(model_connx);
         }
        return inputs;
	}
	
	public List<ComponentModel> getOutputs(ComponentModel node){
		List<ComponentModel> outputs = new ArrayList<ComponentModel>();
		List<AbstractConnectionModel>  sourceConnections = new  ArrayList<AbstractConnectionModel>();//把这个节点作为source的连线的集合，由此可以得到该节点对应的所有输出端口
        sourceConnections =  node.getModelSourceConnections();
       //遍历把这个节点作为source的连线的集合，由此可以得到该节点对应的所有输出端口
        for(int outputNum=0;  outputNum<sourceConnections.size();outputNum++) {
           AbstractConnectionModel connection =  (AbstractConnectionModel)  sourceConnections.get(outputNum);
           ComponentModel model_connx =  connection.getTarget();
           outputs.add(model_connx);
          }
        return outputs;
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
		
		//（6）创建“创建HelloModel模型”工具。这些模型对象都是在点击界面上对应的Entry时就创建了

		List<String> componentIdList = new ArrayList<String>();
		componentIdList= resolve.getComponnetsId();
		
		for(String componentId:componentIdList) {
			CreationToolEntry creationEntry = new CreationToolEntry(componentId,//The character string displayed on a palette
					"创建"+componentId+"模型",//Tool提示
					new CustomSimpleFactory(ComponentModel.class,componentId),//创建模型的factory，原来这些模型对象都是在点击界面上对应的Entry时就创建了
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
	 private PropertySheetPage propertySheetPage;
		//----------------------------------------------调整属性页属性排序-----------------------------------------------------
	 public Object getAdapter(Class type) {
		 if (type == IPropertySheetPage.class){
			  propertySheetPage = new CustomPropertySheetPage();
			  //下边这句话非常必要,如果不设置,Properties View更新时候,资源不能自动更新...
			  propertySheetPage.setRootEntry(new UndoablePropertySheetEntry(this.getCommandStack()));
			  return propertySheetPage;
		 }
		 //
		 if (type == ZoomManager.class) {
			 return getGraphicalViewer().getProperty(ZoomManager.class.toString());
		 }
		 
		 //如果是IContentOutlinePage类型，则返回该ContentOutlinePage
		 if(type == IContentOutlinePage.class) {
			 return new CustomContentOutlinePage();
		 }
		 return super.getAdapter(type);
	 }
	
	//--------------------------------------------实现Outline1：先创建内部类ContentOutlinePage-------------------------------------------
			class CustomContentOutlinePage extends ContentOutlinePage{
				private SashForm sash;//使用SashForm把Outline视图分为两部分：显示大纲和显示鹰眼
				public CustomContentOutlinePage() {
					super(new TreeViewer());
					// TODO Auto-generated constructor stub
				}
				@Override
				public void createControl(Composite compositeParent) {
					sash = new SashForm(compositeParent,SWT.VERTICAL);//创建SashForm
					
					getViewer().createControl(sash);//添加分割条
					getViewer().setEditDomain(getEditDomain());//设置编辑域
					getViewer().setEditPartFactory(new TreeEditPartFactory());//设置EditPartFactory
					getViewer().setContents(contentsModel);//本视图中对应于ContentsModel的内容
					getSelectionSynchronizer().addViewer(getViewer());//选择同步：在Graphical editor中选择图形，则大纲视图选择对应的节点，反之亦然。
				}
				
				
				@Override
				public Control getControl() {//当大纲视图是当前（active）视图时，返回聚焦的控件
					return sash;
				}
				
				public void dispose() {//从TreeViewer中删除SelectionSynchronizer
					getSelectionSynchronizer().removeViewer(getViewer());
					super.dispose();
				}
				
				//---------------------------------------outline----------------------------------------------------------------------
				
			}
	
}

