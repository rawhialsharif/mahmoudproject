// Concrete Implementor in the Bridge pattern
public class NonJordanianResidency implements Residency {

    private String residentialNumber;
    private String nationality;

    public NonJordanianResidency(String residentialNumber, String nationality) {
        this.residentialNumber = residentialNumber;
        this.nationality = nationality;
    }

    @Override
    public String getResidencyType() {
        return "Non-Jordanian";
    }

    @Override
    public String getResidencyInfo() {
        return "Residential No: " + residentialNumber + ", Nationality: " + nationality;
    }

    public String getResidentialNumber() { return residentialNumber; }
    public String getNationality() { return nationality; }
}
