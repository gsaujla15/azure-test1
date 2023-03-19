public class Patient {

	private int file_id;
	private String name;
	private String phone;
	private String assigned_doctor;
	private String username;
	private String password;
	private String blood_group;
	

	public String getBlood_group() {
		return blood_group;
	}

	public void setBlood_group(String blood_group) {
		this.blood_group = blood_group;
	}

	public int getFile_id() {
		return file_id;
	}

	public void setFile_id(int file_id) {
		this.file_id = file_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAssigned_doctor() {
		return assigned_doctor;
	}

	public void setAssigned_doctor(String assigned_doctor) {
		this.assigned_doctor = assigned_doctor;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	

	

	public Patient(int id, String name, String phone, String supervisor, String username, String password) {
		this.setFile_id(id);
		this.setName(name);
		this.setPhone(phone);
		this.setAssigned_doctor(assigned_doctor);
		this.setUsername(username);
		this.setPassword(password);
		this.setBlood_group(blood_group);
		
	}

	public Patient() {
		// nothing here
	}

}