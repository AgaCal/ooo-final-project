class Lab {
	private String crn;
	private String classroom;

	public String getCrn() {
		return crn;
	}

	public void setCrn(String crn) {
		this.crn = crn;
	}

	public String getClassroom() {
		return classroom;
	}

	public void setClassroom(String classroom) {
		this.classroom = classroom;
	}

	@Override
	public String toString() {
		return crn + "," + classroom;
	}

	public Lab(String crn, String classroom) {
		this.crn = crn;
		this.classroom = classroom;
	}
}//end of class Lab
