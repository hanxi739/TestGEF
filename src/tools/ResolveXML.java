package tools;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import constant.IImageConstant;
import model.Component;
import model.Node;
import model.Parameter;
import model.Property;
import model.ResourceInfo;
import model.Waveform;

public  class ResolveXML {
	

	public  List<Component> getComponents() {
		List<Component> compoList = new ArrayList<Component>();
		
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read("components.xml");
			Element components = document.getRootElement();
			List<Element> componentList = components.elements();
			
			String componentId,status,category,codeLocation,resourceUsed,type,name,value,type1,note;
			for(Element component: componentList) {	
				
				componentId = component.element("componentId").getText();
				status = component.element("status").getText();
				//遍历节点添加资源信息
				List<ResourceInfo> infoList = new ArrayList<ResourceInfo>();//存储所有资源信息对象
				List<Element> infos = component.element("resourceInfo").elements();
				for(Element info:infos) {
					category = info.element("category").getText();
					codeLocation = info.element("codeLocation").getText();
					resourceUsed = info.element("resourceUsed").getText();
					type = info.element("type").getText();
					ResourceInfo resourceInfo = new ResourceInfo(category,codeLocation,resourceUsed,type) ;
					infoList.add(resourceInfo);
				}
				
				//遍历节点添加个性化参数信息
				List<Parameter> paraList = new ArrayList<Parameter>();//存储所有个性化参数对象
				List<Element> paras = component.element("parameters").elements();
				for(Element para:paras) {
					name = para.element("name").getText();
					value = para.element("value").getText();
					type1 = para.element("type").getText();
					int type2 = Integer.valueOf(type1);
					note = para.element("note").getText();
					Parameter parameter = new Parameter(name,value,type2,note) ;
					paraList.add(parameter);
				}
				Component compo = new Component(componentId,status,infoList,paraList);
			
				
				compoList.add(compo);
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return compoList;
	}
	
	public int saveWaveform(Waveform obj,String XMLfilePath) {
		List<Node> nodeObjects = obj.getNodeObjects();
		List<Component> componentObjects = obj.getComponentObjects();
		try {
			DocumentFactory factory = DocumentFactory.getInstance();
			Document document = factory.createDocument();
			Element waveform = document.addElement("waveform");
			Element diagram = waveform.addElement("diagram");
			Element count = diagram.addElement("count");
			count.setText(Integer.toString(obj.getCount()));
			Element nodes = diagram.addElement("nodes");
			
			
			for(Node nodeObj:nodeObjects) {
				Element node = nodes.addElement("node");
				
				Element nodeId = node.addElement("nodeId");
				nodeId.setText(nodeObj.getNodeId());
				Element componentId = node.addElement("componentId");
				componentId.setText(nodeObj.getComponentId());
				Element inputs = node.addElement("inputs");
				inputs.setText(nodeObj.getInputs());
				Element outputs = node.addElement("outputs");
				outputs.setText(nodeObj.getOutputs());
			}
			Element components = waveform.addElement("components");
			for(Component compoObj:componentObjects) {
				
				Element component = components.addElement("component");
				Element objId = component.addElement("objId");
				objId.setText(compoObj.getObjId());
				Element componenId = component.addElement("componenId");
				componenId.setText(compoObj.getId());
				Element status = component.addElement("status");
				status.setText(compoObj.getStatus());
				//写资源信息
				Element resourceInfo = component.addElement("resourceInfo");			
				List<ResourceInfo> resourceList = compoObj.getResourceInfoList();
				for(ResourceInfo res:resourceList) {
					Element info = resourceInfo.addElement("info");
					
					Element category = info.addElement("category");
					category.setText(res.getCategory());
					Element codeLocation = info.addElement("codeLocation");
					codeLocation.setText(res.getCodeLocation());
					Element resourceUsed = info.addElement("resourceUsed");
					resourceUsed.setText(res.getResourceUsed());
					Element type = info.addElement("type");
					type.setText(res.getType());
					
				}
				//写个性化参数
				Element parameters = component.addElement("parameters");
				List<Parameter> paraList = compoObj.getParaList();
				for(Parameter para:paraList) {
					Element parameter = parameters.addElement("parameter");
					Element name = parameter.addElement("name");
					name.setText(para.getName());
					Element value = parameter.addElement("value");
					value.setText(para.getValue());
				}			
			}
			OutputFormat format = new OutputFormat("\t",true,"utf-8");
			FileWriter fw = new FileWriter(XMLfilePath);
			XMLWriter writer = new XMLWriter(fw,format);
			writer.write(document);
			writer.close();
			System.out.println(" generate XMLByDOM successfully! ");
			return 1;
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	//获得组件库里所有组件的id
	public  List<String> getComponnetsId(){
		List<String> idList = new ArrayList<String>();
		
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read("components.xml");
			Element components = document.getRootElement();
			List<Element> componentList = components.elements();
			String componentId;
			for(Element component: componentList) {	
				componentId = component.element("componentId").getText();
				idList.add(componentId);
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return idList;
	}
	
	//根据组件Id获得该组件所有信息
	public  Component getComponent(String componentId) {
		List <Component> compoList = getComponents();
		for(Component compo:compoList) {
			if(compo.getId().equals(componentId)) {
				return compo;//返回该组件信息
			}
		}
		return null;//组件不存在
	}
	
	//根据组件id获得其所有property-value的列表
	public  List<Property> getPropertyList(String componentId) {
		List<Property> propertyList = new ArrayList<Property>();
		Component compo = getComponent(componentId);
		Property pro0 = new Property("id",compo.getId());
		Property pro1 = new Property("status",compo.getStatus());
		propertyList.add(pro0);
		propertyList.add(pro1);
		List<ResourceInfo> infos = compo.getResourceInfoList();
		for(ResourceInfo info:infos) {
			Property pro2 = new Property("category",info.getCategory());
			Property pro3 = new Property("codeLocation",info.getCodeLocation());
			Property pro4 = new Property("resourceUsed",info.getResourceUsed());
			Property pro5 = new Property("type",info.getType());
			propertyList.add(pro2);
			propertyList.add(pro3);
			propertyList.add(pro4);
			propertyList.add(pro5);
		}
		List<Parameter> paras = compo.getParaList();
		for(Parameter para:paras) {
			Property pro6 = new Property(para.getName(),para.getValue(),para.getType(),para.getNote());
			propertyList.add(pro6);
		}
		return propertyList;
	}
	/*
	public Map getPropertiesMap(String componentId) {
		Map pvMAP = new HashMap(); 
		Component compo = getComponent(componentId);
		String id = compo.getId();
		pvMAP.put(1, id);
		String status = compo.getStatus();
		pvMAP.put(2, status);
		
		List<ResourceInfo> infos = compo.getResourceInfoList();
		int num1,num2,num3,num4,num5,num6,sum;
		for(int i=0; i<infos.size(); i++) {
			num1 = 1+(i*4)+1;
			String category = infos.get(i).getCategory();
			pvMAP.put(num1, category);
			num2 = 1+(i*4)+2;
			String codeLocation = infos.get(i).getCodeLocation();
			pvMAP.put(num2, category);
			num3 = 1+(i*4)+3;
			String resourceUsed = infos.get(i).getResourceUsed();
			pvMAP.put(num3, resourceUsed);
			num4 = 1+(i*4)+4;
			String type = infos.get(i).getResourceUsed();
			pvMAP.put(num4, type);
		}
		sum = 1+infos.size()*4;
		List<Parameter> paras = compo.getParaList();
		for(int j=0; j<paras.size();j++) {
			num5 = sum+(j*2)+1;
			String name = paras.get(j).getName();
			num6 = sum+(j*2)+2;
			String value = paras.get(j).getValue();
		}
		return property_value;
	}*/
	
	/*
	public static void main(String[] args) {  
		ResolveXML resolve = new ResolveXML();
		List<Component> compoList = resolve.getComponents();
		System.out.println(compoList.size());
		List<String> IDList = resolve.getComponnetsId();
		System.out.println(IDList.size());
		
		Component compo = resolve.getComponent("0x00010000：BCH192_64800-DVBS2");
		System.out.println(compo);
		List<Property> props = resolve.getPropertyList("0x00010000：BCH192_64800-DVBS2");
		for(Property pro:props) {
			System.out.println(pro.getName()+":"+pro.getValue());
		}
    }  */
	
}
