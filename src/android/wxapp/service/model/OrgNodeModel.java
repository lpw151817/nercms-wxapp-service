package android.wxapp.service.model;

import android.content.Context;
import android.wxapp.service.dao.DAOFactory;
import android.wxapp.service.dao.PersonDao;

/**
 * 机构节点模型
 * 
 * @author WEIHAO
 * 
 */
public class OrgNodeModel {
	private String orgCode; // 机构节点代码
	private String description;// 机构节点名称

	private static DAOFactory daoFactory = DAOFactory.getInstance();

	public OrgNodeModel(String orgCode, String description) {
		super();
		this.orgCode = orgCode;
		this.description = description;
	}

	/** 保存 **/
	public boolean save(Context context) {
		PersonDao dao = null;
		dao = daoFactory.getPersonDao(context);
		try {
			return dao.saveOrgNode(this);
		} finally {
		}
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
