package android.wxapp.service.thread;

import java.util.ArrayList;

import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.wxapp.service.dao.DAOFactory;
import android.wxapp.service.dao.PersonDao;
import android.wxapp.service.handler.MessageHandlerManager;
import android.wxapp.service.model.ContactModel;
import android.wxapp.service.model.CustomerContactModel;
import android.wxapp.service.model.CustomerModel;
import android.wxapp.service.model.OrgNodeModel;
import android.wxapp.service.model.OrgNodeStaffModel;
import android.wxapp.service.model.OrgStaffModel;
import android.wxapp.service.util.Constant;
import android.wxapp.service.util.MyJsonParseUtil;

public class SaveAllPersonThread extends Thread {

	private Context context;
	private JSONObject resultJsonObject;

	private static DAOFactory daoFactory = DAOFactory.getInstance();

	private ArrayList<OrgNodeModel> orgNodeList;
	private ArrayList<OrgNodeStaffModel> orgNodeStaffList;
	private ArrayList<OrgStaffModel> orgStaffList;
	private ArrayList<ContactModel> contactList;

	private ArrayList<CustomerModel> customerList;
	private ArrayList<CustomerContactModel> customerContactList;

	public SaveAllPersonThread(JSONObject resultJsonObject, Context context) {
		this.resultJsonObject = resultJsonObject;
		this.context = context;
	}

	public boolean clean() {
		PersonDao dao = null;
		dao = daoFactory.getPersonDao(context);
		try {
			return dao.cleanAllPersonInfo();
		} finally {
		}
	}

	@Override
	public void run() {

		// 清空以前保留的联系人信息
		if (clean()) {
			Log.v("CleanPerson", "清空联系人完成");

			// json对象转换成相应的对象列表
			orgNodeList = MyJsonParseUtil.getOrgNodeList(resultJsonObject);
			orgNodeStaffList = MyJsonParseUtil
					.getOrgNodeStaffList(resultJsonObject);
			orgStaffList = MyJsonParseUtil.getOrgStaffList(resultJsonObject);
			contactList = MyJsonParseUtil.getContactList(resultJsonObject);

			// 2014-5-5
			customerList = MyJsonParseUtil.getCustomerList(resultJsonObject);
			customerContactList = MyJsonParseUtil
					.getCustomerContactList(resultJsonObject);

			// 保存到数据库
			for (int i = 0; i < orgNodeList.size(); i++) {
				OrgNodeModel orgNode = orgNodeList.get(i);
				orgNode.save(context);
			}

			for (int i = 0; i < orgNodeStaffList.size(); i++) {
				OrgNodeStaffModel orgNodeStaff = orgNodeStaffList.get(i);
				orgNodeStaff.save(context);
			}

			for (int i = 0; i < orgStaffList.size(); i++) {
				OrgStaffModel orgStaff = orgStaffList.get(i);
				orgStaff.save(context);
			}

			for (int i = 0; i < contactList.size(); i++) {
				ContactModel contact = contactList.get(i);
				contact.save(context);
			}

			for (int i = 0; i < customerList.size(); i++) {
				CustomerModel customer = customerList.get(i);
//				customer.save(context);
			}

			for (int i = 0; i < customerContactList.size(); i++) {
				CustomerContactModel customerContact = customerContactList
						.get(i);
				customerContact.save(context);
			}

			MessageHandlerManager.getInstance().sendMessage(
					Constant.SAVE_ALL_PERSON_SUCCESS, "Login");
		} else {
			Log.v("CleanPerson", "清空联系人失败");
		}

	}

}
