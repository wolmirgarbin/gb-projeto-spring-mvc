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
@Table(name = "usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByIdusuario", query = "SELECT u FROM Usuario u WHERE u.idusuario = :idusuario"),
    @NamedQuery(name = "Usuario.findByIdpessoa", query = "SELECT u FROM Usuario u WHERE u.idpessoa = :idpessoa"),
    @NamedQuery(name = "Usuario.findByRole", query = "SELECT u FROM Usuario u WHERE u.role = :role"),
    @NamedQuery(name = "Usuario.findByUsuario", query = "SELECT u FROM Usuario u WHERE u.usuario = :usuario"),
    @NamedQuery(name = "Usuario.findBySenha", query = "SELECT u FROM Usuario u WHERE u.senha = :senha"),
    @NamedQuery(name = "Usuario.findByDtcad", query = "SELECT u FROM Usuario u WHERE u.dtcad = :dtcad"),
    @NamedQuery(name = "Usuario.findByDtinativo", query = "SELECT u FROM Usuario u WHERE u.dtinativo = :dtinativo"),
    @NamedQuery(name = "Usuario.findByCadastradopor", query = "SELECT u FROM Usuario u WHERE u.cadastradopor = :cadastradopor")})
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @Column(name = "IDUSUARIO")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idusuario;
    @Basic(optional = false)
    @Column(name = "IDPESSOA")
    private int idpessoa;
    @Basic(optional = false)
    @Column(name = "ROLE")
    private String role;
    @Basic(optional = false)
    @Column(name = "USUARIO")
    private String usuario;
    @Basic(optional = false)
    @Column(name = "SENHA")
    private String senha;
    @Column(name = "DTCAD")
    @Temporal(TemporalType.DATE)
    private Date dtcad;
    @Column(name = "DTINATIVO")
    @Temporal(TemporalType.DATE)
    private Date dtinativo;
    @Column(name = "CADASTRADOPOR")
    private Integer cadastradopor;

    public Usuario() {
    }

    public Usuario(Integer idusuario) {
        this.idusuario = idusuario;
    }

    public Usuario(Integer idusuario, int idpessoa, String role, String usuario, String senha) {
        this.idusuario = idusuario;
        this.idpessoa = idpessoa;
        this.role = role;
        this.usuario = usuario;
        this.senha = senha;
    }

    public Integer getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Integer idusuario) {
        this.idusuario = idusuario;
    }

    public int getIdpessoa() {
        return idpessoa;
    }

    public void setIdpessoa(int idpessoa) {
        this.idpessoa = idpessoa;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Date getDtcad() {
        return dtcad;
    }

    public void setDtcad(Date dtcad) {
        this.dtcad = dtcad;
    }

    public Date getDtinativo() {
        return dtinativo;
    }

    public void setDtinativo(Date dtinativo) {
        this.dtinativo = dtinativo;
    }

    public Integer getCadastradopor() {
        return cadastradopor;
    }

    public void setCadastradopor(Integer cadastradopor) {
        this.cadastradopor = cadastradopor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idusuario != null ? idusuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.idusuario == null && other.idusuario != null) || (this.idusuario != null && !this.idusuario.equals(other.idusuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.materjunior.entities.Usuario[ idusuario=" + idusuario + " ]";
    }
    
}
