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
@Table(name = "aluno")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Aluno.findAll", query = "SELECT a FROM Aluno a"),
    @NamedQuery(name = "Aluno.findByDtinicio", query = "SELECT a FROM Aluno a WHERE a.dtinicio = :dtinicio"),
    @NamedQuery(name = "Aluno.findByDtfim", query = "SELECT a FROM Aluno a WHERE a.dtfim = :dtfim"),
    @NamedQuery(name = "Aluno.findByIdpessoa", query = "SELECT a FROM Aluno a WHERE a.idpessoa = :idpessoa")})
public class Aluno implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "DTINICIO")
    @Temporal(TemporalType.DATE)
    private Date dtinicio;
    @Column(name = "DTFIM")
    @Temporal(TemporalType.DATE)
    private Date dtfim;
    @Id
    @Basic(optional = false)
    @Column(name = "IDPESSOA")
    private Integer idpessoa;

    public Aluno() {
    }

    public Aluno(Integer idpessoa) {
        this.idpessoa = idpessoa;
    }

    public Date getDtinicio() {
        return dtinicio;
    }

    public void setDtinicio(Date dtinicio) {
        this.dtinicio = dtinicio;
    }

    public Date getDtfim() {
        return dtfim;
    }

    public void setDtfim(Date dtfim) {
        this.dtfim = dtfim;
    }

    public Integer getIdpessoa() {
        return idpessoa;
    }

    public void setIdpessoa(Integer idpessoa) {
        this.idpessoa = idpessoa;
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
        if (!(object instanceof Aluno)) {
            return false;
        }
        Aluno other = (Aluno) object;
        if ((this.idpessoa == null && other.idpessoa != null) || (this.idpessoa != null && !this.idpessoa.equals(other.idpessoa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.materjunior.entities.Aluno[ idpessoa=" + idpessoa + " ]";
    }
    
}
