package br.com.mjv.door.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the tb_user database table.
 * 
 */
@Entity
@Table(name="tb_user")
@NamedQuery(name="TbUser.findAll", query="SELECT t FROM TbUser t")
public class TbUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_user")
	private int idUser;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_craeate")
	private Date dtCraeate;

	@Column(name="fg_is_master_user")
	private int fgIsMasterUser;

	@Column(name="nm_id_rfid")
	private String nmIdRfid;

	@Column(name="nm_user")
	private String nmUser;

	public TbUser() {
	}

	public int getIdUser() {
		return this.idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public Date getDtCraeate() {
		return this.dtCraeate;
	}

	public void setDtCraeate(Date dtCraeate) {
		this.dtCraeate = dtCraeate;
	}

	public int getFgIsMasterUser() {
		return this.fgIsMasterUser;
	}

	public void setFgIsMasterUser(int fgIsMasterUser) {
		this.fgIsMasterUser = fgIsMasterUser;
	}

	public String getNmIdRfid() {
		return this.nmIdRfid;
	}

	public void setNmIdRfid(String nmIdRfid) {
		this.nmIdRfid = nmIdRfid;
	}

	public String getNmUser() {
		return this.nmUser;
	}

	public void setNmUser(String nmUser) {
		this.nmUser = nmUser;
	}

}