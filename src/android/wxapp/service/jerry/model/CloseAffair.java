package android.wxapp.service.jerry.model;

public class CloseAffair {
	String id, aid, lot;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getLot() {
		return lot;
	}

	public void setLot(String lot) {
		this.lot = lot;
	}

	public CloseAffair(String id, String aid, String lot) {
		super();
		this.id = id;
		this.aid = aid;
		this.lot = lot;
	}

	public CloseAffair() {
		super();
	}

}
