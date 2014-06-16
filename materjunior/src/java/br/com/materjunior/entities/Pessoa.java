/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.materjunior.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author wolmir
 */
@Entity
@Table(name = "pessoa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pessoa.findAll", query = "SELECT p FROM Pessoa p"),
    @NamedQuery(name = "Pessoa.findByIdpessoa", query = "SELECT p FROM Pessoa p WHERE p.idpessoa = :idpessoa"),
    @NamedQuery(name = "Pessoa.findByNome", query = "SELECT p FROM Pessoa p WHERE p.nome = :nome"),
    @NamedQuery(name = "Pessoa.findByEmail", query = "SELECT p FROM Pessoa p WHERE p.email = :email"),
    @NamedQuery(name = "Pessoa.findByDtinicio", query = "SELECT p FROM Pessoa p WHERE p.dtinicio = :dtinicio"),
    @NamedQuery(name = "Pessoa.findByDtfinal", query = "SELECT p FROM Pessoa p WHERE p.dtfinal = :dtfinal"),
    @NamedQuery(name = "Pessoa.findByTrabalhaem", query = "SELECT p FROM Pessoa p WHERE p.trabalhaem = :trabalhaem"),
    @NamedQuery(name = "Pessoa.findByTipo", query = "SELECT p FROM Pessoa p WHERE p.tipo = :tipo"),
    @NamedQuery(name = "Pessoa.findByFone", query = "SELECT p FROM Pessoa p WHERE p.fone = :fone"),
    @NamedQuery(name = "Pessoa.findByAniversario", query = "SELECT p FROM Pessoa p WHERE p.aniversario = :aniversario"),
    @NamedQuery(name = "Pessoa.findByCpf", query = "SELECT p FROM Pessoa p WHERE p.cpf = :cpf"),
    @NamedQuery(name = "Pessoa.findByRg", query = "SELECT p FROM Pessoa p WHERE p.rg = :rg")})
public class Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDPESSOA")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idpessoa;
    @Column(name = "NOME")
    private String nome;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "DTINICIO")
    @Temporal(TemporalType.DATE)
    private Date dtinicio;
    @Column(name = "DTFINAL")
    @Temporal(TemporalType.DATE)
    private Date dtfinal;
    @Column(name = "TRABALHAEM")
    private String trabalhaem;
    @Column(name = "TIPO")
    private String tipo;
    @Column(name = "FONE")
    private String fone;
    @Column(name = "ANIVERSARIO")
    private String aniversario;
    @Column(name = "CPF")
    private String cpf;
    @Column(name = "RG")
    private String rg;

    public Pessoa() {
    }

    public Pessoa(Integer idpessoa) {
        this.idpessoa = idpessoa;
    }

    public Integer getIdpessoa() {
        return idpessoa;
    }

    public void setIdpessoa(Integer idpessoa) {
        this.idpessoa = idpessoa;
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

    public Date getDtinicio() {
        return dtinicio;
    }

    public void setDtinicio(Date dtinicio) {
        this.dtinicio = dtinicio;
    }

    public Date getDtfinal() {
        return dtfinal;
    }

    public void setDtfinal(Date dtfinal) {
        this.dtfinal = dtfinal;
    }

    public String getTrabalhaem() {
        return trabalhaem;
    }

    public void setTrabalhaem(String trabalhaem) {
        this.trabalhaem = trabalhaem;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public String getAniversario() {
        return aniversario;
    }

    public void setAniversario(String aniversario) {
        this.aniversario = aniversario;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpessoa != null ? idpessoa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pessoa)) {
            return false;
        }
        Pessoa other = (Pessoa) object;
        if ((this.idpessoa == null && other.idpessoa != null) || (this.idpessoa != null && !this.idpessoa.equals(other.idpessoa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.materjunior.entities.Pessoa[ idpessoa=" + idpessoa + " ]";
    }
    
}
