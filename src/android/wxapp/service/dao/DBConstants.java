package android.wxapp.service.dao;

public class DBConstants {

	// ÿ�β�ѯ����������
	public static final int QUERY_LIMIT = 10;

	// �������ͣ�������ʾ��֪ͨ��
	public static final int AFFAIR_TASK = 1;
	public static final int AFFAIR_REQUEST = 2;
	public static final int AFFAIR_NOTICE = 3;

	// ����״̬��δ���/�����У�����ɣ���ʱ��
	public static final int AFFAIR_UNCOMPLETED = 1;
	public static final int AFFAIR_COMPLETED = 2;
	public static final int AFFAIR_DELAYED = 3;

	// ���ݿ���
	public static final String DATABASE_NAME = "App.db";

	// ��ϵ��������ݱ���
	public static final String ORG_NODE_TABLE_NAME = "org_node";
	public static final String ORG_NODE_STAFF_TABLE_NAME = "org_node_staff";
	public static final String ORG_STAFF_TABLE_NAME = "org_staff";
	public static final String CONTACT_TABLE_NAME = "contact";

	public static final String CUSTOMER_TABLE_NAME = "customer";
	public static final String CUSTOMER_CONTACT_TABLE_NAME = "customer_contact";

	// �����ڵ���������� ����
	public static final String[] ORG_NODE_ALL_COLUMNS = { "org_code",
			"description" };

	// �����ڵ��Ա���������� ����
	public static final String[] ORG_NODE_STAFF_ALL_COLUMNS = { "org_code",
			"contact_id", "sequence" };

	// ����ȫ����ϵ�˱��������� ����
	public static final String[] ORG_STAFF_ALL_COLUMNS = { "contact_id",
			"name", "position", "rank" };

	// ��ϵ��ʽ���������� ����
	public static final String[] CONTACT_ALL_COLUMNS = { "contact_id", "type",
			"content" };

	// �ͻ����������� ����
	public static final String[] CUSTOMER_ALL_COLUMNS = { "customer_id",
			"name", "unit", "description", "contact_id" };

	// �ͻ���ϵ��ʽ���������� ����
	public static final String[] CUSTOMER_CONTACT_ALL_COLUMNS = {
			"customer_id", "type", "content" };

	// ����������ݱ���
	public static final String AFFAIRS_TABLE_NAME = "affairs_info";
	public static final String PERSON_ON_DUTY_TABLE_NAME = "person_on_duty";
	public static final String AFFAIR_ATTACHMENT_TABLE_NAME = "affair_attachment";
	public static final String FEEDBACK_TABLE_NAME = "affair_feedback";
	public static final String FEEDBACK_ATTACHMENT_TABLE_NAME = "feedback_attachment";
	public static final String AFFAIR_OPERATE_LOG_TABLE_NAME = "affair_operate_log";

	// ��Ϣ����
	public static final String MESSAGE_TABLE_NAME = "message";

	// �绰����
	public static final String PHONE_TABLE_NAME = "phone";

	// �������
	public static final String CONFERENCE_TABLE_NAME = "conference";
	// ��������߱���
	public static final String CONFERENCE_PERSON_TABLE_NAME = "conference_person";

	// ������������� ����
	public static final String[] AFFAIR_ALL_COLUMNS = { "affair_id", "type",
			"sponsor_id", "title", "description", "begin_time", "end_time",
			"complete_time", "last_operate_type", "last_operate_time",
			"is_read", "status" };

	// �����˱��������� ����
	public static final String[] PERSON_ON_DUTY_ALL_COLUMNS = { "affair_id",
			"person_id" };

	// ���񸽼����������� ����
	public static final String[] AFFAIR_ATTACHMENT_ALL_COLUMNS = { "affair_id",
			"attachment_type", "url" };

	// �������������� ����
	public static final String[] AFFAIR_FEEDBACK_ALL_COLUMNS = { "feedback_id",
			"affair_id", "person_id", "feedback_time", "content", "is_read" };

	// ������������������ ����
	public static final String[] FEEDBACK_ATTACHMENT_ALL_COLUMNS = {
			"feedback_id", "attachment_type", "url" };

	// ������־���������� ����
	public static final String[] AFFAIR_LOG_ALL_COLUMNS = { "affair_id",
			"type", "time_stamp" };

	// ��Ϣ���������� ����
	public static final String[] MESSAGE_ALL_COLUMNS = { "message_id",
			"sender_id", "receiver_id", "send_time", "content",
			"attachment_type", "attachment_url", "is_group", "is_read" };

	// �绰���������� ����
	public static final String[] PHONE_ALL_COLUMNS = { "phone_id", "type",
			"caller_id", "callee_id", "start_time", "is_answered", "end_time",
			"duration", "is_read" };
	// �绰���������� ����
	public static final String[] CONFERENCE_ALL_COLUMNS = { "conference_id",
			"conference_name", "type", "sponsor_id", "create_time",
			"reservation_time", "start_time", "end_time", "status" };
	// �绰���������� ����
	public static final String[] CONFERENCE_PERSON_ALL_COLUMNS = {
			"conference_id", "person_id", "is_sponsor", "is_speaker",
			"is_video_sharer", "join_time", "leave_time" };

}
