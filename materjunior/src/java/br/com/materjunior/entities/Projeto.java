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
@Table(name = "projeto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Projeto.findAll", query = "SELECT p FROM Projeto p"),
    @NamedQuery(name = "Projeto.findByIdprojeto", query = "SELECT p FROM Projeto p WHERE p.idprojeto = :idprojeto"),
    @NamedQuery(name = "Projeto.findByIdcliente", query = "SELECT p FROM Projeto p WHERE p.idcliente = :idcliente"),
    @NamedQuery(name = "Projeto.findByDtinicio", query = "SELECT p FROM Projeto p WHERE p.dtinicio = :dtinicio"),
    @NamedQuery(name = "Projeto.findByDtfinal", query = "SELECT p FROM Projeto p WHERE p.dtfinal = :dtfinal"),
    @NamedQuery(name = "Projeto.findByDescricao", query = "SELECT p FROM Projeto p WHERE p.descricao = :descricao"),
    @NamedQuery(name = "Projeto.findByTitulo", query = "SELECT p FROM Projeto p WHERE p.titulo = :titulo"),
    @NamedQuery(name = "Projeto.findByHorasprojeto", query = "SELECT p FROM Projeto p WHERE p.horasprojeto = :horasprojeto")})
public class Projeto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDPROJETO")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idprojeto;
    @Basic(optional = false)
    @Column(name = "IDCLIENTE")
    private int idcliente;
    @Column(name = "DTINICIO")
    @Temporal(TemporalType.DATE)
    private Date dtinicio;
    @Column(name = "DTFINAL")
    private String dtfinal;
    @Column(name = "DESCRICAO")
    private String descricao;
    @Column(name = "TITULO")
    private String titulo;
    @Column(name = "HORASPROJETO")
    private Integer horasprojeto;

    public Projeto() {
    }

    public Projeto(Integer idprojeto) {
        this.idprojeto = idprojeto;
    }

    public Projeto(Integer idprojeto, int idcliente) {
        this.idprojeto = idprojeto;
        this.idcliente = idcliente;
    }

    public Integer getIdprojeto() {
        return idprojeto;
    }

    public void setIdprojeto(Integer idprojeto) {
        this.idprojeto = idprojeto;
    }

    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public Date getDtinicio() {
        return dtinicio;
    }

    public void setDtinicio(Date dtinicio) {
        this.dtinicio = dtinicio;
    }

    public String getDtfinal() {
        return dtfinal;
    }

    public void setDtfinal(String dtfinal) {
        this.dtfinal = dtfinal;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getHorasprojeto() {
        return horasprojeto;
    }

    public void setHorasprojeto(Integer horasprojeto) {
        this.horasprojeto = horasprojeto;
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
        if (!(object instanceof Projeto)) {
            return false;
        }
        Projeto other = (Projeto) object;
        if ((this.idprojeto == null && other.idprojeto != null) || (this.idprojeto != null && !this.idprojeto.equals(other.idprojeto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.materjunior.entities.Projeto[ idprojeto=" + idprojeto + " ]";
    }
    
}
