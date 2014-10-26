package polak.dataclasses;

public class DeathBirthParsedData {

	public String birthDate = null;
	public String deathDate = null;

	public DeathBirthParsedData(String birthDate, String deathDate) {
		this.birthDate = birthDate;
		this.deathDate = deathDate;
	}
	
	public DeathBirthParsedData(String birthDate) {
		this.birthDate = birthDate;
	}

	/* Empty constructor (for creating just some of the data) */
	public DeathBirthParsedData() {
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public void setDeathDate(String deathDate) {
		this.deathDate = deathDate;
	}

}
