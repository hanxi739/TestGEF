package model;
//连接的模型
//实现对连接线的一些操作，其实这想方法只是调用了起点和终点（这里是HelloModel）中的一些方法而已。因为下述操作对所有的连接都有效，所以我们将他们添加到AbstractConnectionModel中
public class AbstractConnectionModel {
	private HelloModel source,target;
	
	//连接线的头端添加到source
	public void attachSource() {
		if(!source.getModelSourceConnections().contains(this)) {//这里的this是指啥呢?是指AbstractConnectionModel这个对象本身吗
			source.addSourceConnection(this);//回答：对，但是这个AbstractConnectionModel对象本身不会包含两个HelloModel对象吗？还是说，本身就需要这两个HelloModel对象来标识一个连接
			//回答：是的，连接的起点和终点信息就是在连接模型中管理的，所以必须有这两个节点对象
		}
	}
	
	//连接线的尾端添加到target
	public void attachTarget() {
		if(!target.getModelTargetConnections().contains(this)) {
			target.addTargetConnection(this);
		}
	}
	
	//移除连接线的头端
	public void detachSource() {
		source.removeSourceConnection(this);
	}
	
	//移除连接线的尾端
	public void detachTarget() {
		target.removeTargetConnection(this);
	}
	
	public HelloModel getSource() {
		return source;
	}

	public HelloModel getTarget() {
		return target;
	}

	public void setTarget(HelloModel target) {
		this.target = target;
	}

	public void setSource(HelloModel source) {
		this.source = source;
	}
	
	

}
