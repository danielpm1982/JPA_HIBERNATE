package model.pedidoCliente;

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
import javax.persistence.Table;

@Entity @Table(name="PedidoTable")
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

    public Pedido(String pedido, LocalDateTime dataHoraPedido, Cliente cliente) {
        this.pedido = pedido;
        this.dataHoraPedido = dataHoraPedido;
        this.cliente = cliente;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPedido() {
        return pedido;
    }

    public void setPedido(String pedido) {
        this.pedido = pedido;
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
        String dataHoraPedidoStringView = getDataHoraPedido()!=null?getDataHoraPedido().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)):"dataHora inexistente";
        return id+" "+(pedido!=null&&(!pedido.equals(""))?pedido:"sem descrição!")+" "+dataHoraPedidoStringView+" "+(cliente!=null?cliente:"cliente inexistente");
    }
    
}
