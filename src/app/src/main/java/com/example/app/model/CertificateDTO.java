package com.example.app.model;

public class CertificateDTO {
    private String idCertificate, name, content;
    public CertificateDTO(String idCertificate, String name, String content) {
        this.idCertificate = idCertificate;
        this.name = name;
        this.content = content;
    }

    public String getIdCertificate() {
        return idCertificate;
    }

    public void setIdCertificate(String idCertificate) {
        this.idCertificate = idCertificate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ScheduleDTO{" +
                "idCertificate='" + idCertificate + '\'' +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
