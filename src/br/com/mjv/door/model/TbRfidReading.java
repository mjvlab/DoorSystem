package br.com.mjv.door.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the tb_rfid_reading database table.
 * 
 */
@Entity
@Table(name="tb_rfid_reading")
@NamedQuery(name="TbRfidReading.findAll", query="SELECT t FROM TbRfidReading t")
public class TbRfidReading implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_rfid_reading")
	private int idRfidReading;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_reading")
	private Date dtReading;

	@Column(name="nm_id_rfid")
	private String nmIdRfid;

	//uni-directional many-to-one association to TbAction
	@ManyToOne
	@JoinColumn(name="id_action")
	private TbAction tbAction;

	//uni-directional many-to-one association to TbUser
	@ManyToOne
	@JoinColumn(name="id_user")
	private TbUser tbUser;

	public TbRfidReading() {
	}

	public int getIdRfidReading() {
		return this.idRfidReading;
	}

	public void setIdRfidReading(int idRfidReading) {
		this.idRfidReading = idRfidReading;
	}

	public Date getDtReading() {
		return this.dtReading;
	}

	public void setDtReading(Date dtReading) {
		this.dtReading = dtReading;
	}

	public String getNmIdRfid() {
		return this.nmIdRfid;
	}

	public void setNmIdRfid(String nmIdRfid) {
		this.nmIdRfid = nmIdRfid;
	}

	public TbAction getTbAction() {
		return this.tbAction;
	}

	public void setTbAction(TbAction tbAction) {
		this.tbAction = tbAction;
	}

	public TbUser getTbUser() {
		return this.tbUser;
	}

	public void setTbUser(TbUser tbUser) {
		this.tbUser = tbUser;
	}

}