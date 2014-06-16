/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.materjunior.entities;

import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "cliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"),
    @NamedQuery(name = "Cliente.findByIdcliente", query = "SELECT c FROM Cliente c WHERE c.idcliente = :idcliente"),
    @NamedQuery(name = "Cliente.findByRazaosocial", query = "SELECT c FROM Cliente c WHERE c.razaosocial = :razaosocial"),
    @NamedQuery(name = "Cliente.findByFantasia", query = "SELECT c FROM Cliente c WHERE c.fantasia = :fantasia"),
    @NamedQuery(name = "Cliente.findByIdentificacao", query = "SELECT c FROM Cliente c WHERE c.identificacao = :identificacao"),
    @NamedQuery(name = "Cliente.findBySexo", query = "SELECT c FROM Cliente c WHERE c.sexo = :sexo"),
    @NamedQuery(name = "Cliente.findByInscestadual", query = "SELECT c FROM Cliente c WHERE c.inscestadual = :inscestadual"),
    @NamedQuery(name = "Cliente.findByEmail", query = "SELECT c FROM Cliente c WHERE c.email = :email"),
    @NamedQuery(name = "Cliente.findByTelefone", query = "SELECT c FROM Cliente c WHERE c.telefone = :telefone"),
    @NamedQuery(name = "Cliente.findByCelular", query = "SELECT c FROM Cliente c WHERE c.celular = :celular"),
    @NamedQuery(name = "Cliente.findByEndereco", query = "SELECT c FROM Cliente c WHERE c.endereco = :endereco"),
    @NamedQuery(name = "Cliente.findByBairro", query = "SELECT c FROM Cliente c WHERE c.bairro = :bairro"),
    @NamedQuery(name = "Cliente.findByCidade", query = "SELECT c FROM Cliente c WHERE c.cidade = :cidade"),
    @NamedQuery(name = "Cliente.findByDescricao", query = "SELECT c FROM Cliente c WHERE c.descricao = :descricao"),
    @NamedQuery(name = "Cliente.findByIdcontato", query = "SELECT c FROM Cliente c WHERE c.idcontato = :idcontato")})
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDCLIENTE")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idcliente;
    @Column(name = "RAZAOSOCIAL")
    private String razaosocial;
    @Column(name = "FANTASIA")
    private String fantasia;
    @Column(name = "IDENTIFICACAO")
    private String identificacao;
    @Column(name = "SEXO")
    private String sexo;
    @Column(name = "INSCESTADUAL")
    private String inscestadual;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "TELEFONE")
    private String telefone;
    @Column(name = "CELULAR")
    private String celular;
    @Column(name = "ENDERECO")
    private String endereco;
    @Column(name = "BAIRRO")
    private String bairro;
    @Column(name = "CIDADE")
    private BigInteger cidade;
    @Column(name = "DESCRICAO")
    private String descricao;
    @Basic(optional = false)
    @Column(name = "IDCONTATO")
    private long idcontato;

    public Cliente() {
    }

    public Cliente(Integer idcliente) {
        this.idcliente = idcliente;
    }

    public Cliente(Integer idcliente, long idcontato) {
        this.idcliente = idcliente;
        this.idcontato = idcontato;
    }

    public Integer getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Integer idcliente) {
        this.idcliente = idcliente;
    }

    public String getRazaosocial() {
        return razaosocial;
    }

    public void setRazaosocial(String razaosocial) {
        this.razaosocial = razaosocial;
    }

    public String getFantasia() {
        return fantasia;
    }

    public void setFantasia(String fantasia) {
        this.fantasia = fantasia;
    }

    public String getIdentificacao() {
        return identificacao;
    }

    public void setIdentificacao(String identificacao) {
        this.identificacao = identificacao;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getInscestadual() {
        return inscestadual;
    }

    public void setInscestadual(String inscestadual) {
        this.inscestadual = inscestadual;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public BigInteger getCidade() {
        return cidade;
    }

    public void setCidade(BigInteger cidade) {
        this.cidade = cidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public long getIdcontato() {
        return idcontato;
    }

    public void setIdcontato(long idcontato) {
        this.idcontato = idcontato;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcliente != null ? idcliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.idcliente == null && other.idcliente != null) || (this.idcliente != null && !this.idcliente.equals(other.idcliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.materjunior.entities.Cliente[ idcliente=" + idcliente + " ]";
    }
    
}
