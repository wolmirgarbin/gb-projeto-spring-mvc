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
@Table(name = "contato")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contato.findAll", query = "SELECT c FROM Contato c"),
    @NamedQuery(name = "Contato.findByIdcontato", query = "SELECT c FROM Contato c WHERE c.idcontato = :idcontato"),
    @NamedQuery(name = "Contato.findByNome", query = "SELECT c FROM Contato c WHERE c.nome = :nome"),
    @NamedQuery(name = "Contato.findByEmail", query = "SELECT c FROM Contato c WHERE c.email = :email"),
    @NamedQuery(name = "Contato.findByFone", query = "SELECT c FROM Contato c WHERE c.fone = :fone"),
    @NamedQuery(name = "Contato.findByFuncao", query = "SELECT c FROM Contato c WHERE c.funcao = :funcao")})
public class Contato implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "IDCONTATO")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idcontato;
    
    @Column(name = "NOME")
    private String nome;
    
    @Column(name = "EMAIL")
    private String email;
    
    @Column(name = "FONE")
    private String fone;
    
    @Column(name = "FUNCAO")
    private String funcao;

    public Contato() {
    }

    public Contato(Long idcontato) {
        this.idcontato = idcontato;
    }

    public Long getIdcontato() {
        return idcontato;
    }

    public void setIdcontato(Long idcontato) {
        this.idcontato = idcontato;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcontato != null ? idcontato.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contato)) {
            return false;
        }
        Contato other = (Contato) object;
        if ((this.idcontato == null && other.idcontato != null) || (this.idcontato != null && !this.idcontato.equals(other.idcontato))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.materjunior.entities.Contato[ idcontato=" + idcontato + " ]";
    }
    
}
