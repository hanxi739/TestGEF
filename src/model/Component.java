package model;
//为显控平台准备着
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Component extends AbstractModel {

	private String id;
	private String status;
	private List<ResourceInfo> resourceInfoList;
	private List<Parameter> paraList;
    
//--------------------------------------需要设置和获得的参数信息------------------------------------
    

 
	public String getId() {
		return id;
	}

	public Component(String id, String status, List<ResourceInfo> resourceInfoList, List<Parameter> paraList) {
	super();
	this.id = id;
	this.status = status;
	this.resourceInfoList = resourceInfoList;
	this.paraList = paraList;
}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ResourceInfo> getResourceInfoList() {
		return resourceInfoList;
	}

	public void setResourceInfoList(List<ResourceInfo> resourceInfoList) {
		this.resourceInfoList = resourceInfoList;
	}

	public List<Parameter> getParaList() {
		return paraList;
	}

	public void setParaList(List<Parameter> paraList) {
		this.paraList = paraList;
	}
	
}
