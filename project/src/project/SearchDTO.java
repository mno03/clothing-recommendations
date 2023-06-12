package project;


public class SearchDTO {
	private String name;
	private String id;
	private String password;
	private String gender;
	private double height;
	private double shoulder;
	private double chest;
	private double waist;
	private double leg;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String sex) {
		this.gender = sex;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public double getShoulder() {
		return shoulder;
	}
	public void setShoulder(double shoulder) {
		this.shoulder = shoulder;
	}
	public double getChest() {
		return chest;
	}
	public void setChest(double chest) {
		this.chest = chest;
	}
	public double getWaist() {
		return waist;
	}
	public void setWaist(double waist) {
		this.waist = waist;
	}
	public double getLeg() {
		return leg;
	}
	public void setLeg(double leg) {
		this.leg = leg;
	}
	@Override
	public String toString() {
		return "ScoreDTO [name=" + name + ", id=" + id + ", password=" + password + ", gender=" + gender + ", height=" + height + ", shoulder="
				+ shoulder + ", chest=" + chest + ", waist=" + waist+ ", leg=" + leg +" ]";
	}
	
	
	
}

