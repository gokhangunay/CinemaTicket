package cinema.model;

public enum Hour {
	
	TEN(10), TWELVE(12), FOURTEEN(14), SIXTEEN(16),
	EIGHTEEN(18), TWENTY(20), TWENTYTWO(22);
	
	public int hour;
	
	private Hour(int hour){
		this.hour = hour;
	}
	
	public int getHour() {
		return hour;
	}

}
