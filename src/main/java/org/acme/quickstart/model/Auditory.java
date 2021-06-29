package org.acme.quickstart.model;




public class Auditory {

    private final Integer terminalNumber;
    private final Integer serialNumber;
    private final String company;

    public Auditory(Integer terminalNumber, Integer serialNumber, String company) {
        this.terminalNumber = terminalNumber;
        this.serialNumber = serialNumber;
        this.company = company;
    }

    public Integer getTerminalNumber() {
        return terminalNumber;
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public String getCompany() {
        return company;
    }
}
