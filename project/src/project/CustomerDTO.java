package project;

public class CustomerDTO {
	private String name;
    private String id;
    private String password;
	private String sex;
	private int age;
	private double height, shoulder, chest, waist, leg;

	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	public String getId() {return id;}
    public void setId(String id) {this.id = id;}
    public String getPassword() {return password;}
	public void setPassword(String password) {this.password = password;}
	public String getSex() {return sex;}
	public void setSex(String sex) {this.sex = sex;}
	public int getAge() {return age;}
	public void setAge(int age) {this.age = age;}
	public double getHeight() {return height;}
	public void setHeight(double height) {this.height = height;}
	public double getShoulder() {return shoulder;}
	public void setShoulder(double shoulder) {this.shoulder = shoulder;}
	public double getChest() {return chest;}
	public void setChest(double chest) {this.chest = chest;}
	public double getWaist() {return waist;}
	public void setWaist(double waist) {this.waist = waist;}
	public double getLeg() {return leg;}
	public void setLeg(double leg) {this.leg = leg;}

	@Override
	   public String toString() {
	      return "CustomerDTO [name=" + name + ", id=" + id + ", password=" + password + ", sex=" + sex + ", age=" + age
	            + ", height=" + height + ", shoulder=" + shoulder + ", chest=" + chest + ", waist=" + waist + ", leg="
	            + leg + "]";
	   }

	}