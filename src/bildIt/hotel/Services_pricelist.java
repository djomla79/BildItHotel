package bildIt.hotel;

/**
 * @author Dijana Markovic
 *
 */
public class Services_pricelist {
	private int apartmant = 60;
	private int gym = 10;
	private int one_bed = 20;
	private int two_beds = 40;
	private int sauna = 10;
	private int pool = 10;
	private int restourant = 20;

	public Services_pricelist() {

	}

	public Services_pricelist(int apartmant, int gym, int one_bed,
			int two_beds, int sauna, int pool, int restourant) {
		super();
		this.apartmant = apartmant;
		this.gym = gym;
		this.one_bed = one_bed;
		this.two_beds = two_beds;
		this.sauna = sauna;
		this.pool = pool;
		this.restourant = restourant;
	}

	public int getApartmant() {
		return apartmant;
	}

	public void setApartmant(int apartmant) {
		this.apartmant = apartmant;
	}

	public int getGym() {
		return gym;
	}

	public void setGym(int gym) {
		this.gym = gym;
	}

	public int getOne_bed() {
		return one_bed;
	}

	public void setOne_bed(int one_bed) {
		this.one_bed = one_bed;
	}

	public int getTwo_beds() {
		return two_beds;
	}

	public void setTwo_beds(int two_beds) {
		this.two_beds = two_beds;
	}

	public int getSauna() {
		return sauna;
	}

	public void setSauna(int sauna) {
		this.sauna = sauna;
	}

	public int getPool() {
		return pool;
	}

	public void setPool(int pool) {
		this.pool = pool;
	}

	public int getRestourant() {
		return restourant;
	}

	public void setRestourant(int restourant) {
		this.restourant = restourant;
	}

}