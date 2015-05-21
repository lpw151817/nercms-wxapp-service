package android.wxapp.service.model;

import java.util.ArrayList;

/**
 * 结构化后的人员模型
 * 
 * @author WEIHAO
 * 
 */

public class StructuredStaffModel {

	private String contactID; // 联系人ID
	private String orgCode; // 机构节点代码
	private String orgDescription; // 机构节点名称
	private String sequence; // 节点内联系人排序
	private String name; // 姓名
	private String position;// 职务
	private String rank; // 职衔
	private ArrayList<ContactModel> contact; // （联系方式列表）非必须

	// 未包含联系方式list的构造函数
	public StructuredStaffModel(String contactID, String orgCode,
			String orgDescription, String sequence, String name,
			String position, String rank) {
		super();
		this.contactID = contactID;
		this.orgCode = orgCode;
		this.orgDescription = orgDescription;
		this.sequence = sequence;
		this.name = name;
		this.position = position;
		this.rank = rank;
	}

	// 包含联系方式list的构造函数
	public StructuredStaffModel(String contactID, String orgCode,
			String orgDescription, String sequence, String name,
			String position, String rank, ArrayList<ContactModel> contact) {
		super();
		this.contactID = contactID;
		this.orgCode = orgCode;
		this.orgDescription = orgDescription;
		this.sequence = sequence;
		this.name = name;
		this.position = position;
		this.rank = rank;
		this.contact = contact;
	}

	public String getContactID() {
		return contactID;
	}

	public void setContactID(String contactID) {
		this.contactID = contactID;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgDescription() {
		return orgDescription;
	}
	public void setOrgDescription(String orgDescription) {
		this.orgDescription = orgDescription;
	}
	public String getSequence() {
		return sequence;
	}
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public ArrayList<ContactModel> getContact() {
		return contact;
	}
	public void setContact(ArrayList<ContactModel> contact) {
		this.contact = contact;
	}

}
