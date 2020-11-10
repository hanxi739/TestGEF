package model;
//资源信息类
public class ResourceInfo {
	private String category;
	private String codeLocation;
	private String resourceUsed;
	private String type;
	
	
	public ResourceInfo(String category, String codeLocation, String resourceUsed, String type) {
		super();
		this.category = category;
		this.codeLocation = codeLocation;
		this.resourceUsed = resourceUsed;
		this.type = type;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCodeLocation() {
		return codeLocation;
	}
	public void setCodeLocation(String codeLocation) {
		this.codeLocation = codeLocation;
	}
	public String getResourceUsed() {
		return resourceUsed;
	}
	public void setResourceUsed(String resourceUsed) {
		this.resourceUsed = resourceUsed;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
