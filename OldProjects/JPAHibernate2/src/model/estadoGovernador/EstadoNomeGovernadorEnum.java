package model.estadoGovernador;

public enum EstadoNomeGovernadorEnum {
    ACRE("Tião Viana"),
    ALAGOAS("Renan Filho"),
    AMAPÁ("Waldez"),
    AMAZONAS("José Melo"),
    BAHIA("Rui Costa"),
    CEARÁ("Camilo Santana"),
    DISTRITO_FEDERAL("Rollemberg"),
    ESPÍRITO_SANTO("Paulo Hartung"),
    GOIÁS("Marconi Perrillo"),
    MARANHÃO("Flávio Dino"),
    MATO_GROSSO("Pedro Taques"),
    MATO_GROSSO_DO_SUL("Reinado Azambuja"),
    MINAS_GERAIS("Fernando Pimentel"),
    PARÁ("Simão Jatene"),
    PARAÍBA("Ricardo Coutinho"),
    PARANÁ("Beto Richa"),
    PERNAMBUCO("Paulo Câmara"),
    PIAUÍ("Wellington Dias"),
    RIO_DE_JANEIRO("Luiz Pezão"),
    RIO_GRANDE_DO_NORTE("Robinson Faria"),
    RIO_GRANDE_DO_SUL("José Ivo Sartori"),
    RONDÔNIA("Confúcio Moura"),
    RORAIMA("Suely Campos"),
    SANTA_CATARINA("Raimundo Colombo"),
    SÃO_PAULO("Geraldo Alckmin"),
    SERGIPE("Jackson Barreto"),
    TOCANTINS("Marcelo Miranda");

    private final String nomeGoverandor;

    private EstadoNomeGovernadorEnum(String nomeGovernador) {
        this.nomeGoverandor=nomeGovernador;
    }

    public String getNomeGovernador() {
        return nomeGoverandor;
    }
}
