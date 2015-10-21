package android.wxapp.service.model;

import java.util.ArrayList;

/**
 * �ṹ�������Աģ��
 * 
 * @author WEIHAO
 * 
 */

public class StructuredStaffModel {

	private String contactID; // ��ϵ��ID
	private String orgCode; // �����ڵ����
	private String orgDescription; // �����ڵ�����
	private String sequence; // �ڵ�����ϵ������
	private String name; // ����
	private String position;// ְ��
	private String rank; // ְ��
	private ArrayList<ContactModel> contact; // ����ϵ��ʽ�б��Ǳ���

	// δ������ϵ��ʽlist�Ĺ��캯��
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

	// ������ϵ��ʽlist�Ĺ��캯��
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
