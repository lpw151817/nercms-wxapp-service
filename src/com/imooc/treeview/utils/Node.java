package com.imooc.treeview.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Node implements Serializable {
	private String id;
	/**
	 * 跟节点的pid=0
	 */
	private String pId = 0 + "";
	private String name;
	/**
	 * 树的层级
	 */
	private int level;
	/**
	 * 是否是展开
	 */
	private boolean isExpand = false;
	private int icon;

	private Node parent;
	private List<Node> children = new ArrayList<Node>();

	public Node() {
	}

	public Node(String id, String pId, String name) {
		this.id = id;
		this.pId = pId;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public List<Node> getChildren() {
		return children;
	}

	public void setChildren(List<Node> children) {
		this.children = children;
	}

	/**
	 * 属否是根节点
	 * 
	 * @return
	 */
	public boolean isRoot() {
		return parent == null;
	}

	/**
	 * 判断当前父节点的收缩状态
	 * 
	 * @return
	 */
	public boolean isParentExpand() {
		if (parent == null)
			return false;
		return parent.isExpand();
	}

	/**
	 * 是否是叶子节点
	 * 
	 * @return
	 */
	public boolean isLeaf() {
		return children.size() == 0;
	}

	/**
	 * 得到当前节点的层级
	 * 
	 * @return
	 */
	public int getLevel() {
		return parent == null ? 0 : parent.getLevel() + 1;
	}

	public boolean isExpand() {
		return isExpand;
	}

	public void setExpand(boolean isExpand) {
		this.isExpand = isExpand;

		if (!isExpand) {
			for (Node node : children) {
				node.setExpand(false);
			}
		}
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "Node [id=" + id + ", pId=" + pId + ", name=" + name + ", level=" + level + ", isExpand="
				+ isExpand + ", icon=" + icon + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((pId == null) ? 0 : pId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (pId == null) {
			if (other.pId != null)
				return false;
		} else if (!pId.equals(other.pId))
			return false;
		return true;
	}

	// @Override
	// public int describeContents() {
	// return 0;
	// }
	//
	// public static final Parcelable.Creator<Node> CREATOR = new
	// Creator<Node>() {
	// @Override
	// public Node[] newArray(int size) {
	// return new Node[size];
	// }
	//
	// @Override
	// public Node createFromParcel(Parcel in) {
	// return new Node(in);
	// }
	// };
	//
	// @Override
	// public void writeToParcel(Parcel dest, int flags) {
	// dest.writeTypedList(children);
	// dest.writeInt(icon);
	// dest.writeString(id);
	// // true 1
	// dest.writeByte((byte) (isExpand ? 1 : 0));
	// dest.writeInt(level);
	// dest.writeString(name);
	// dest.writeValue(parent);
	// dest.writeString(pId);
	// }
	//
	// public Node(Parcel in) {
	// // this.children=in.readTypedList(list, c);
	// }
}
