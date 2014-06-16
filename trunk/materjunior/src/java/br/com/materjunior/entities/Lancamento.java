/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.materjunior.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author wolmir
 */
@Entity
@Table(name = "lancamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lancamento.findAll", query = "SELECT l FROM Lancamento l"),
    @NamedQuery(name = "Lancamento.findByIdlancamento", query = "SELECT l FROM Lancamento l WHERE l.idlancamento = :idlancamento"),
    @NamedQuery(name = "Lancamento.findByIdprojeto", query = "SELECT l FROM Lancamento l WHERE l.idprojeto = :idprojeto"),
    @NamedQuery(name = "Lancamento.findByValor", query = "SELECT l FROM Lancamento l WHERE l.valor = :valor"),
    @NamedQuery(name = "Lancamento.findByDescricao", query = "SELECT l FROM Lancamento l WHERE l.descricao = :descricao"),
    @NamedQuery(name = "Lancamento.findByDebcred", query = "SELECT l FROM Lancamento l WHERE l.debcred = :debcred")})
public class Lancamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDLANCAMENTO")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idlancamento;
    @Basic(optional = false)
    @Column(name = "IDPROJETO")
    private int idprojeto;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "VALOR")
    private Double valor;
    @Column(name = "DESCRICAO")
    private String descricao;
    @Column(name = "DEBCRED")
    private String debcred;

    public Lancamento() {
    }

    public Lancamento(Integer idlancamento) {
        this.idlancamento = idlancamento;
    }

    public Lancamento(Integer idlancamento, int idprojeto) {
        this.idlancamento = idlancamento;
        this.idprojeto = idprojeto;
    }

    public Integer getIdlancamento() {
        return idlancamento;
    }

    public void setIdlancamento(Integer idlancamento) {
        this.idlancamento = idlancamento;
    }

    public int getIdprojeto() {
        return idprojeto;
    }

    public void setIdprojeto(int idprojeto) {
        this.idprojeto = idprojeto;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDebcred() {
        return debcred;
    }

    public void setDebcred(String debcred) {
        this.debcred = debcred;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idlancamento != null ? idlancamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lancamento)) {
            return false;
        }
        Lancamento other = (Lancamento) object;
        if ((this.idlancamento == null && other.idlancamento != null) || (this.idlancamento != null && !this.idlancamento.equals(other.idlancamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.materjunior.entities.Lancamento[ idlancamento=" + idlancamento + " ]";
    }
    
}
