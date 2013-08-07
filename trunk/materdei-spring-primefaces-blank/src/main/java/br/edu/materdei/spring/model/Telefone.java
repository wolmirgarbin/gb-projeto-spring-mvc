package br.edu.materdei.spring.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author duduso
 */
@Entity
@Table(name = "telefone")
public class Telefone implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;
    @Column(name = "ddd", length = 4)
    private String ddd;
    @Column(name = "numero", length = 9)
    private String numero;
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    public Telefone() {
    }

    public Telefone(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public boolean equals(Object obj) {
        final Telefone other = (Telefone) obj;
        if (other == null) {
            return false;
        }
        if (getClass() != other.getClass()) {
            return false;
        }
        if (this.getId() == null && other.getId() != null) {
            return false;
        }
        if (this.getId() != null && other.getId() == null) {
            return false;
        }
        if (this.getId() == null && other.getId() == null) {
            return (this == other);
        }
        if (this.getId().equals(other.getId())) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

}
