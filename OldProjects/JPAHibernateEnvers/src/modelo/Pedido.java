package modelo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.hibernate.envers.Audited;

@NamedQuery(name = "Pedido.findAll",query = "select p from Pedido p")
@Entity @Table(name="PedidoTable")
@Audited
public class Pedido implements Serializable{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id_pedido")
    private Long id;
    @Column(name = "pedido")
    private String pedido;
    @Column(name = "data_hora_do_pedido")
    private LocalDateTime dataHoraPedido;
    @ManyToOne @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    public Pedido() {
    }

    public Pedido(String pedido, LocalDateTime dataHoraPedido) {
        this.pedido = pedido;
        this.dataHoraPedido = dataHoraPedido;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataHoraPedido() {
        return dataHoraPedido;
    }

    public void setDataHoraPedido(LocalDateTime dataHoraPedido) {
        this.dataHoraPedido = dataHoraPedido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        String dataHoraPedidoStringView = getDataHoraPedido()!=null?getDataHoraPedido().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)):"dataHora inexistente";
        return id+" "+(pedido!=null&&(!pedido.equals(""))?pedido:"sem descrição!")+" "+dataHoraPedidoStringView;
    }
    
}
