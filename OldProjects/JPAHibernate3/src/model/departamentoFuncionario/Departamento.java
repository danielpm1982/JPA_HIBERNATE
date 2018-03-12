package model.departamentoFuncionario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity @Table(name="DepartamentoTable")
public class Departamento implements Serializable{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id_Departamento")
    private Long id;
    @Column(name = "nome_departamento")
    private String nome;
    @OneToMany @JoinTable(name = "Dep_Func_JoinTable",joinColumns = @JoinColumn(name = "depto_Id"),inverseJoinColumns = @JoinColumn(name="func_Id"))
    private List<Funcionario> funcionarioList= new ArrayList<>();
    
    public Departamento() {
    }

    public Departamento(String nome, List<Funcionario> funcionarioList) {
        this.nome = nome;
        this.funcionarioList = funcionarioList;
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

    public List<Funcionario> getFuncionarioList() {
        return funcionarioList;
    }

    public void setFuncionarioList(List<Funcionario> funcionarioList) {
        this.funcionarioList = funcionarioList;
    }

    @Override
    public String toString() {
        return id+" "+(getNome()!=null&&(!getNome().equals(""))?getNome():"sem nome!")+" "+getFuncionariosListStringView(funcionarioList);
    }
    
    public static String getFuncionariosListStringView(List<Funcionario> list){
        if(list!=null&&!list.isEmpty()){
            return list.stream().map(x->x+" ").reduce("", (x,y)->x+y);
        } else{
            return "nenhum funcionário!";
        }
    }
}
