package model;
public enum CountryDDIEnum {

    ÁFRICA_DO_SUL(27),
    ALEMANHA(49),
    ARÁBIA_SAUDITA(966),
    ARGENTINA(54),
    AUSTRÁLIA(61),
    ÁUSTRIA(43),
    BAHAMAS(1),
    BANGLADESH(880),
    BÉLGICA(32),
    BOLÍVIA(591),
    BRASIL(55),
    BULGÁRIA(359),
    CANADÁ(1),
    CHILE(56),
    CHINA(86),
    CHIPRE(357),
    COLÔMBIA(57),
    CORÉIA_DO_SUL(82),
    COSTA_RICA(506),
    CROÁCIA(385),
    DINAMARCA(45),
    ESPANHA(34),
    ESTADOS_UNIDOS(1),
    FILIPINAS(63),
    FINLÂNDIA(358),
    FRANÇA(33),
    GRÉCIA(30),
    HOLANDA(31),
    HONG_KONG(852),
    HUNGRIA(36),
    ÍNDIA(91),
    INDONÉSIA(62),
    IRLANDA(353),
    ISLÂNDIA(354),
    ISRAEL(972),
    ITÁLIA(39),
    JAPÃO(81),
    LUXEMBURGO(352),
    MÉXICO(52),
    MÔNACO(377),
    NORUEGA(47),
    NOVA_ZELÂNDIA(64),
    PARAGUAI(595),
    PERU(51),
    POLINÉSIA_FRANCESA(689),
    POLÔNIA(48),
    PORTO_RICO(1),
    PORTUGAL(351),
    REINO_UNIDO(44),
    RÚSSIA(7),
    SUÉCIA(46),
    SUÍÇA(41),
    TAIWAN(886),
    TURQUIA(90),
    URUGUAI(598),
    VATICANO(379),
    VENEZUELA(58);

    private final int DDI;

    private CountryDDIEnum(int DDI) {
        this.DDI=DDI;
    }

    public int getDDI() {
        return DDI;
    }

    @Override
    public String toString() {
        return name()+" ("+DDI+")";
    }
}
