package model;

public class Node {
	private String nodeId;
	private String componentId;
	private String inputs;
	private String outputs;
	public Node(String nodeId, String componentId, String inputs, String outputs) {
		super();
		this.nodeId = nodeId;
		this.componentId = componentId;
		this.inputs = inputs;
		this.outputs = outputs;
	}
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public String getComponentId() {
		return componentId;
	}
	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}
	public String getInputs() {
		return inputs;
	}
	public void setInputs(String inputs) {
		this.inputs = inputs;
	}
	public String getOutputs() {
		return outputs;
	}
	public void setOutputs(String outputs) {
		this.outputs = outputs;
	}

	
	public static void main(String [] args) {
		Node node = new Node("123","234","2,3","5,6");
		System.out.println(node.toString());
	}
	
}
