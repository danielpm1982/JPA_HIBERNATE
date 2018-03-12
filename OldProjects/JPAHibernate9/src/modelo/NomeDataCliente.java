package modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class NomeDataCliente {
    private String nome;
    private LocalDate dataNascimento;

    public NomeDataCliente(String nome, LocalDate dataNascimento) {
        this.nome = nome;
        this.dataNascimento=dataNascimento;
    }    

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    @Override
    public String toString() {
        return (nome!=null&&(!nome.equals(""))?nome:"sem nome!")+" "+((dataNascimento!=null)?dataNascimento.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)):"sem data de nascimento!");
    }
}
