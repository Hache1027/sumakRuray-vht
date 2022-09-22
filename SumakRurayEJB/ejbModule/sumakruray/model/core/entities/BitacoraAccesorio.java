package sumakruray.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the bitacora_accesorio database table.
 * 
 */
@Entity
@Table(name="bitacora_accesorio")
@NamedQuery(name="BitacoraAccesorio.findAll", query="SELECT b FROM BitacoraAccesorio b")
public class BitacoraAccesorio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="bit_acce_id", unique=true, nullable=false)
	private Integer bitAcceId;

	@Column(name="bit_acce_evento", length=100)
	private String bitAcceEvento;

	@Column(name="bit_acce_fecha_crea")
	private Timestamp bitAcceFechaCrea;

	@Column(name="bit_acce_observacion", length=1000)
	private String bitAcceObservacion;

	@Column(name="bit_acce_usuario_crea", length=100)
	private String bitAcceUsuarioCrea;

	//bi-directional many-to-one association to Accesorio
	@ManyToOne
	@JoinColumn(name="acce_id")
	private Accesorio accesorio;

	public BitacoraAccesorio() {
	}

	public Integer getBitAcceId() {
		return this.bitAcceId;
	}

	public void setBitAcceId(Integer bitAcceId) {
		this.bitAcceId = bitAcceId;
	}

	public String getBitAcceEvento() {
		return this.bitAcceEvento;
	}

	public void setBitAcceEvento(String bitAcceEvento) {
		this.bitAcceEvento = bitAcceEvento;
	}

	public Timestamp getBitAcceFechaCrea() {
		return this.bitAcceFechaCrea;
	}

	public void setBitAcceFechaCrea(Timestamp bitAcceFechaCrea) {
		this.bitAcceFechaCrea = bitAcceFechaCrea;
	}

	public String getBitAcceObservacion() {
		return this.bitAcceObservacion;
	}

	public void setBitAcceObservacion(String bitAcceObservacion) {
		this.bitAcceObservacion = bitAcceObservacion;
	}

	public String getBitAcceUsuarioCrea() {
		return this.bitAcceUsuarioCrea;
	}

	public void setBitAcceUsuarioCrea(String bitAcceUsuarioCrea) {
		this.bitAcceUsuarioCrea = bitAcceUsuarioCrea;
	}

	public Accesorio getAccesorio() {
		return this.accesorio;
	}

	public void setAccesorio(Accesorio accesorio) {
		this.accesorio = accesorio;
	}

}