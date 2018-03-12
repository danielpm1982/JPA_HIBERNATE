package modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToMany;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@NamedQueries({
                @NamedQuery(name = "Cliente.findAll",query = "select c from Cliente c"),
                @NamedQuery(name = "Cliente.findFirstLetter",query = "select c from Cliente c where c.nome like :firstLetter"),
                @NamedQuery(name = "Cliente.findOrdinal",query = "select c from Cliente c where c.id = ?1"),
                @NamedQuery(name = "Cliente.findAllName",query = "select c.nome from Cliente c"),
                @NamedQuery(name = "Cliente.findStatistics1",query = "select COUNT(c) from Cliente c"),
                @NamedQuery(name = "Cliente.findStatistics2",query = "select MAX(c.dataNascimento) from Cliente c"),
                @NamedQuery(name = "Cliente.findStatistics3",query = "select MIN(c.dataNascimento) from Cliente c"),
                @NamedQuery(name = "Cliente.findStatistics4",query = "select AVG(c.id) from Cliente c"),
                @NamedQuery(name = "Cliente.findStatistics5",query = "select SUM(c.id) from Cliente c"),
                @NamedQuery(name = "Cliente.findNomeDataNasc",query = "select c.nome,c.dataNascimento from Cliente c"),
                @NamedQuery(name = "Cliente.findNomeDataCliente",query = "select new modelo.NomeDataCliente(c.nome,c.dataNascimento) from Cliente c"),
                @NamedQuery(name = "Cliente.findAllFetchPedido",query = "select distinct (c) from Cliente c left join fetch c.pedido"),
                @NamedQuery(name = "Cliente.bulkUpdate",query = "update Pedido p set p.cliente=null"),
                @NamedQuery(name = "Cliente.bulkUpdate2",query = "delete Pedido p")
})

@NamedStoredProcedureQueries({
                @NamedStoredProcedureQuery( name = "buscaMaiorNumeroPedidos",
                                            resultClasses = Cliente.class,
                                            procedureName = "BUSCA_MAIOR_NUMERO_PEDIDOS"
                ),
                @NamedStoredProcedureQuery( name = "buscaNumeroMinimoPedidos",
                                            resultClasses = Cliente.class,
                                            procedureName = "BUSCA_NUMERO_MINIMO_PEDIDOS",
                                            parameters = {
                                                @StoredProcedureParameter(
                                                        mode = ParameterMode.IN,
                                                        name = "MINIMO_PEDIDOS",
                                                        type = Integer.class
                                                )
                                            }
                )
})

@Entity @Table(name="ClienteTable")
public class Cliente implements Serializable{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id_cliente")
    private Long id;
    @Column(name = "nome_cliente")
    private String nome;
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;
    @OneToMany(mappedBy = "cliente",fetch = FetchType.LAZY)
    private List<Pedido> pedido=new ArrayList<>();
    
    public Cliente() {
    }

    public Cliente(String nome, LocalDate dataNascimento) {
        this.nome = nome;
        this.dataNascimento=dataNascimento;
    }    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Pedido> getPedido() {
        return pedido;
    }

    public void setPedido(List<Pedido> pedido) {
        this.pedido = pedido;
    }
    
    @Override
    public String toString() {
        return id+" "+(nome!=null&&(!nome.equals(""))?nome:"sem nome!")+" "+((dataNascimento!=null)?dataNascimento:"sem data de nascimento!");
    }
}
