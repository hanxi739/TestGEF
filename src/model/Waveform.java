package model;

import java.util.List;

public class Waveform {
	private String name;
	private int count;
	private List<Node> nodeObjects;
	private List<Component> componentObjects;
	public Waveform(int count, List<Node> nodeObjects, List<Component> componentObjects) {
		super();
		this.count = count;
		this.nodeObjects = nodeObjects;
		this.componentObjects = componentObjects;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<Node> getNodeObjects() {
		return nodeObjects;
	}
	public void setNodeObjects(List<Node> nodeObjects) {
		this.nodeObjects = nodeObjects;
	}
	public List<Component> getComponentObjects() {
		return componentObjects;
	}
	public void setComponentObjects(List<Component> componentObjects) {
		this.componentObjects = componentObjects;
	}
	
	
}
