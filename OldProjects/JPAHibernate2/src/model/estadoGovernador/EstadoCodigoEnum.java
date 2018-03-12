package model.estadoGovernador;

public enum EstadoCodigoEnum {
    ACRE("AC"),
    ALAGOAS("AL"),
    AMAPÁ("AP"),
    AMAZONAS("AM"),
    BAHIA("BA"),
    CEARÁ("CE"),
    DISTRITO_FEDERAL("DF"),
    ESPÍRITO_SANTO("ES"),
    GOIÁS("GO"),
    MARANHÃO("MA"),
    MATO_GROSSO("MT"),
    MATO_GROSSO_DO_SUL("MS"),
    MINAS_GERAIS("MG"),
    PARÁ("PA"),
    PARAÍBA("PB"),
    PARANÁ("PR"),
    PERNAMBUCO("PE"),
    PIAUÍ("PI"),
    RIO_DE_JANEIRO("RJ"),
    RIO_GRANDE_DO_NORTE("RN"),
    RIO_GRANDE_DO_SUL("RS"),
    RONDÔNIA("RO"),
    RORAIMA("RR"),
    SANTA_CATARINA("SC"),
    SÃO_PAULO("SP"),
    SERGIPE("SE"),
    TOCANTINS("TO");

    private String codigo;

    private EstadoCodigoEnum(String codigo) {
        this.codigo=codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
