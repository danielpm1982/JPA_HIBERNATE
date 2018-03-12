package jpa;
public class ClientNameDepName {
    private String clientName;
    private String depName;
    public ClientNameDepName(String clientName, String depName) {
        this.clientName = clientName;
        this.depName = depName;
    }
    public String getClientName() {
        return clientName;
    }
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
    public String getDepName() {
        return depName;
    }
    public void setDepName(String depName) {
        this.depName = depName;
    }
    @Override
    public String toString() {
        return "ClientName: "+clientName+" DepartmentName: "+depName;
    }
}
