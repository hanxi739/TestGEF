package model;
//为显控平台准备着
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Component extends AbstractModel {

	private String id;
	private String name;
	private Map prop_value;//该如何存储一个长度不定的属性名-属性值表呢
	protected List inputPorts = new ArrayList(6);//记录输入端口
	protected List outputPorts = new ArrayList(6);//记录输出端口连接的其他端口，6代表最多连6个组件
    
	
}
