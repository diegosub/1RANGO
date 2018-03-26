package br.com.rango.ngc.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Proxy;

/** 
 * @author Carlos Diego
 * @since 26/01/2016
 */
@Entity
@Table(name = "tb_usuario", schema="rng")
@Proxy(lazy = true)
public class Usuario implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "SQ_USUARIO", sequenceName = "SQ_USUARIO", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_USUARIO")
	@Column(name = "id_usuario")
	private BigInteger idUsuario;
	
	@Column(name = "ds_nome")
	private String dsNome;
	
	@Column(name = "ds_login")
	private String dsLogin;

	@Column(name = "ds_senha")
	private String dsSenha;
	
	@Column(name = "ds_email")
	private String dsEmail;
	
	@Column(name = "ds_cpf")
	private String dsCpf;
	
	@Column(name = "nm_telefone")
	private String nmTelefone;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dt_nascimento")
	private Date dtNascimento;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_cadastro")
	private Date dtCadastro;
	
	@Column(name = "id_usuario_cad")
	private BigInteger idUsuarioCad;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario_cad", insertable = false, updatable = false)
	private Usuario usuarioCad;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_alteracao")
	private Date dtAlteracao;
	
	@Column(name = "id_usuario_alt")
	private BigInteger idUsuarioAlt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario_alt", insertable = false, updatable = false)
	private Usuario usuarioAlt;
	
	@Column(name = "fg_ativo")
	private String fgAtivo;

	@Column(name = "ds_cep")
	private String dsCep;

	@Column(name = "ds_logradouro")
	private String dsLogradouro;
	
	@Column(name = "nm_endereco")
	private BigInteger nmEndereco;
	
	@Column(name = "ds_bairro")
	private String dsBairro;
	
	@Column(name = "ds_cidade")
	private String dsCidade;
	
	@Column(name = "id_estado")
	private BigInteger idEstado;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_estado", insertable = false, updatable = false)
	private Estado estado;
	
	@Column(name = "ds_complemento")
	private String dsComplemento;
	
	@Column(name = "ds_referencia")
	private String dsReferencia;
	
	@Column(name = "id_grupo")
	private BigInteger idGrupo;
	
	@Column(name = "fg_sexo")
	private String fgSexo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_grupo", insertable = false, updatable = false)
	private Grupo grupo;
	
	@Column(name = "id_estabelecimento")
	private BigInteger idEstabelecimento;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_estabelecimento", insertable = false, updatable = false)
	private Estabelecimento estabelecimento;
	
	@Transient
	private HashMap<String, Object> filtroMap;
	
	@Transient
	private String desCliente;
	
	@Transient
	private String novaSenha;

	public BigInteger getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(BigInteger idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getDsNome() {
		return dsNome;
	}

	public void setDsNome(String dsNome) {
		this.dsNome = dsNome;
	}

	public String getDsLogin() {
		return dsLogin;
	}

	public void setDsLogin(String dsLogin) {
		this.dsLogin = dsLogin;
	}

	public String getDsSenha() {
		return dsSenha;
	}

	public void setDsSenha(String dsSenha) {
		this.dsSenha = dsSenha;
	}

	public String getDsEmail() {
		return dsEmail;
	}

	public void setDsEmail(String dsEmail) {
		this.dsEmail = dsEmail;
	}

	public String getDsCpf() {
		return dsCpf;
	}

	public void setDsCpf(String dsCpf) {
		this.dsCpf = dsCpf;
	}

	public String getNmTelefone() {
		return nmTelefone;
	}

	public void setNmTelefone(String nmTelefone) {
		this.nmTelefone = nmTelefone;
	}

	public Date getDtNascimento() {
		return dtNascimento;
	}

	public void setDtNascimento(Date dtNascimento) {
		this.dtNascimento = dtNascimento;
	}

	public Date getDtCadastro() {
		return dtCadastro;
	}

	public void setDtCadastro(Date dtCadastro) {
		this.dtCadastro = dtCadastro;
	}

	public BigInteger getIdUsuarioCad() {
		return idUsuarioCad;
	}

	public void setIdUsuarioCad(BigInteger idUsuarioCad) {
		this.idUsuarioCad = idUsuarioCad;
	}

	public Usuario getUsuarioCad() {
		return usuarioCad;
	}

	public void setUsuarioCad(Usuario usuarioCad) {
		this.usuarioCad = usuarioCad;
	}

	public Date getDtAlteracao() {
		return dtAlteracao;
	}

	public void setDtAlteracao(Date dtAlteracao) {
		this.dtAlteracao = dtAlteracao;
	}

	public BigInteger getIdUsuarioAlt() {
		return idUsuarioAlt;
	}

	public void setIdUsuarioAlt(BigInteger idUsuarioAlt) {
		this.idUsuarioAlt = idUsuarioAlt;
	}

	public Usuario getUsuarioAlt() {
		return usuarioAlt;
	}

	public void setUsuarioAlt(Usuario usuarioAlt) {
		this.usuarioAlt = usuarioAlt;
	}

	public String getFgAtivo() {
		return fgAtivo;
	}

	public void setFgAtivo(String fgAtivo) {
		this.fgAtivo = fgAtivo;
	}

	public String getDsCep() {
		return dsCep;
	}

	public void setDsCep(String dsCep) {
		this.dsCep = dsCep;
	}

	public String getDsLogradouro() {
		return dsLogradouro;
	}

	public void setDsLogradouro(String dsLogradouro) {
		this.dsLogradouro = dsLogradouro;
	}

	public BigInteger getNmEndereco() {
		return nmEndereco;
	}

	public void setNmEndereco(BigInteger nmEndereco) {
		this.nmEndereco = nmEndereco;
	}

	public String getDsBairro() {
		return dsBairro;
	}

	public void setDsBairro(String dsBairro) {
		this.dsBairro = dsBairro;
	}

	public String getDsCidade() {
		return dsCidade;
	}

	public void setDsCidade(String dsCidade) {
		this.dsCidade = dsCidade;
	}

	public BigInteger getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(BigInteger idEstado) {
		this.idEstado = idEstado;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public String getDsComplemento() {
		return dsComplemento;
	}

	public void setDsComplemento(String dsComplemento) {
		this.dsComplemento = dsComplemento;
	}

	public String getDsReferencia() {
		return dsReferencia;
	}

	public void setDsReferencia(String dsReferencia) {
		this.dsReferencia = dsReferencia;
	}

	public BigInteger getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(BigInteger idGrupo) {
		this.idGrupo = idGrupo;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public HashMap<String, Object> getFiltroMap() {
		return filtroMap;
	}

	public void setFiltroMap(HashMap<String, Object> filtroMap) {
		this.filtroMap = filtroMap;
	}

	public String getDesCliente() {
		return desCliente;
	}

	public void setDesCliente(String desCliente) {
		this.desCliente = desCliente;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getFgSexo() {
		return fgSexo;
	}

	public void setFgSexo(String fgSexo) {
		this.fgSexo = fgSexo;
	}

	public BigInteger getIdEstabelecimento() {
		return idEstabelecimento;
	}

	public void setIdEstabelecimento(BigInteger idEstabelecimento) {
		this.idEstabelecimento = idEstabelecimento;
	}

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}
}