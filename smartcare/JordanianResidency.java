// Concrete Implementor in the Bridge pattern
public class JordanianResidency implements Residency {

    private String nationalNumber;
    private String idCardNumber;

    public JordanianResidency(String nationalNumber, String idCardNumber) {
        this.nationalNumber = nationalNumber;
        this.idCardNumber = idCardNumber;
    }

    @Override
    public String getResidencyType() {
        return "Jordanian";
    }

    @Override
    public String getResidencyInfo() {
        return "National No: " + nationalNumber + ", ID Card: " + idCardNumber;
    }

    public String getNationalNumber() { return nationalNumber; }
    public String getIdCardNumber() { return idCardNumber; }
}
