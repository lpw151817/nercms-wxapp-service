package android.wxapp.service.jerry.model.person;

import com.imooc.treeview.utils.annotation.TreeNodeId;
import com.imooc.treeview.utils.annotation.TreeNodeLabel;
import com.imooc.treeview.utils.annotation.TreeNodePid;

public class Org {
	@TreeNodeId
	String id;
	@TreeNodePid
	String pid;
	@TreeNodeLabel
	String Title;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public Org(String id, String pid, String title) {
		this.id = id;
		this.pid = pid;
		Title = title;
	}

}
