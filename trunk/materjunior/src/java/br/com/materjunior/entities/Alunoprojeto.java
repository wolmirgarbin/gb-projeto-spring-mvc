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
@Table(name = "alunoprojeto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Alunoprojeto.findAll", query = "SELECT a FROM Alunoprojeto a"),
    @NamedQuery(name = "Alunoprojeto.findByIdprojeto", query = "SELECT a FROM Alunoprojeto a WHERE a.idprojeto = :idprojeto"),
    @NamedQuery(name = "Alunoprojeto.findByQtdhoras", query = "SELECT a FROM Alunoprojeto a WHERE a.qtdhoras = :qtdhoras"),
    @NamedQuery(name = "Alunoprojeto.findByValorrateio", query = "SELECT a FROM Alunoprojeto a WHERE a.valorrateio = :valorrateio"),
    @NamedQuery(name = "Alunoprojeto.findByIdpessoa", query = "SELECT a FROM Alunoprojeto a WHERE a.idpessoa = :idpessoa")})
public class Alunoprojeto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDPROJETO")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idprojeto;
    @Column(name = "QTDHORAS")
    private Integer qtdhoras;
    @Column(name = "VALORRATEIO")
    private Long valorrateio;
    @Basic(optional = false)
    @Column(name = "IDPESSOA")
    private Integer idpessoa;

    public Alunoprojeto() {
    }

    public Alunoprojeto(Integer idprojeto) {
        this.idprojeto = idprojeto;
    }

    public Alunoprojeto(Integer idprojeto, int idpessoa) {
        this.idprojeto = idprojeto;
        this.idpessoa = idpessoa;
    }

    public Integer getIdprojeto() {
        return idprojeto;
    }

    public void setIdprojeto(Integer idprojeto) {
        this.idprojeto = idprojeto;
    }

    public Integer getQtdhoras() {
        return qtdhoras;
    }

    public void setQtdhoras(Integer qtdhoras) {
        this.qtdhoras = qtdhoras;
    }

    public Long getValorrateio() {
        return valorrateio;
    }

    public void setValorrateio(Long valorrateio) {
        this.valorrateio = valorrateio;
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
        hash += (idprojeto != null ? idprojeto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Alunoprojeto)) {
            return false;
        }
        Alunoprojeto other = (Alunoprojeto) object;
        if ((this.idprojeto == null && other.idprojeto != null) || (this.idprojeto != null && !this.idprojeto.equals(other.idprojeto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.materjunior.entities.Alunoprojeto[ idprojeto=" + idprojeto + " ]";
    }
    
}
