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
@Table(name = "egresso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Egresso.findAll", query = "SELECT e FROM Egresso e"),
    @NamedQuery(name = "Egresso.findByIdegresso", query = "SELECT e FROM Egresso e WHERE e.idegresso = :idegresso"),
    @NamedQuery(name = "Egresso.findByFormadoano", query = "SELECT e FROM Egresso e WHERE e.formadoano = :formadoano"),
    @NamedQuery(name = "Egresso.findByCidade", query = "SELECT e FROM Egresso e WHERE e.cidade = :cidade"),
    @NamedQuery(name = "Egresso.findByIdpessoa", query = "SELECT e FROM Egresso e WHERE e.idpessoa = :idpessoa"),
    @NamedQuery(name = "Egresso.findByLocaltrabalho", query = "SELECT e FROM Egresso e WHERE e.localtrabalho = :localtrabalho"),
    @NamedQuery(name = "Egresso.findByDescricao", query = "SELECT e FROM Egresso e WHERE e.descricao = :descricao")})
public class Egresso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDEGRESSO")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idegresso;
    @Column(name = "FORMADOANO")
    private Integer formadoano;
    @Column(name = "CIDADE")
    private String cidade;
    @Basic(optional = false)
    @Column(name = "IDPESSOA")
    private int idpessoa;
    @Column(name = "LOCALTRABALHO")
    private String localtrabalho;
    @Column(name = "DESCRICAO")
    private String descricao;

    public Egresso() {
    }

    public Egresso(Integer idegresso) {
        this.idegresso = idegresso;
    }

    public Egresso(Integer idegresso, int idpessoa) {
        this.idegresso = idegresso;
        this.idpessoa = idpessoa;
    }

    public Integer getIdegresso() {
        return idegresso;
    }

    public void setIdegresso(Integer idegresso) {
        this.idegresso = idegresso;
    }

    public Integer getFormadoano() {
        return formadoano;
    }

    public void setFormadoano(Integer formadoano) {
        this.formadoano = formadoano;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public int getIdpessoa() {
        return idpessoa;
    }

    public void setIdpessoa(int idpessoa) {
        this.idpessoa = idpessoa;
    }

    public String getLocaltrabalho() {
        return localtrabalho;
    }

    public void setLocaltrabalho(String localtrabalho) {
        this.localtrabalho = localtrabalho;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idegresso != null ? idegresso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Egresso)) {
            return false;
        }
        Egresso other = (Egresso) object;
        if ((this.idegresso == null && other.idegresso != null) || (this.idegresso != null && !this.idegresso.equals(other.idegresso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.materjunior.entities.Egresso[ idegresso=" + idegresso + " ]";
    }
    
}
