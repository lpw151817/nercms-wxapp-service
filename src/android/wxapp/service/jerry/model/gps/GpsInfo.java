package android.wxapp.service.jerry.model.gps;

public class GpsInfo {

	// ����
	private double mLongitude;
	// γ��
	private double mLatitude;
	// ����
	private double mAltitude;
	// ����
	private float mAccuracy;
	// �ٶ�
	private float mSpeed;
	// ʱ��
	private long mTime;
	// �߶�
	private float mHeight;

	public float getmHeight() {
		return mHeight;
	}

	public void setmHeight(float mHeight) {
		this.mHeight = mHeight;
	}

	public double getmLongitude() {
		return mLongitude;
	}

	public void setmLongitude(double mLongitude) {
		this.mLongitude = mLongitude;
	}

	public double getmLatitude() {
		return mLatitude;
	}

	public void setmLatitude(double mLatitude) {
		this.mLatitude = mLatitude;
	}

	public double getmAltitude() {
		return mAltitude;
	}

	public void setmAltitude(double mAltitude) {
		this.mAltitude = mAltitude;
	}

	public float getmAccuracy() {
		return mAccuracy;
	}

	public void setmAccuracy(float mAccuracy) {
		this.mAccuracy = mAccuracy;
	}

	public float getmSpeed() {
		return mSpeed;
	}

	public void setmSpeed(float mSpeed) {
		this.mSpeed = mSpeed;
	}

	public long getmTime() {
		return mTime;
	}

	public void setmTime(long mTime) {
		this.mTime = mTime;
	}

	public GpsInfo(double mLongitude, double mLatitude, double mAltitude, float mAccuracy, float mSpeed,
			long mTime) {
		super();
		this.mLongitude = mLongitude;
		this.mLatitude = mLatitude;
		this.mAltitude = mAltitude;
		this.mAccuracy = mAccuracy;
		this.mSpeed = mSpeed;
		this.mTime = mTime;
	}

	public GpsInfo() {
		super();
	}

}
