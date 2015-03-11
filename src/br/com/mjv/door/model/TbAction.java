package br.com.mjv.door.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tb_action database table.
 * 
 */
@Entity
@Table(name="tb_action")
@NamedQuery(name="TbAction.findAll", query="SELECT t FROM TbAction t")
public class TbAction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_action")
	private int idAction;

	@Column(name="nm_action")
	private String nmAction;

	public TbAction() {
	}

	public int getIdAction() {
		return this.idAction;
	}

	public void setIdAction(int idAction) {
		this.idAction = idAction;
	}

	public String getNmAction() {
		return this.nmAction;
	}

	public void setNmAction(String nmAction) {
		this.nmAction = nmAction;
	}

}