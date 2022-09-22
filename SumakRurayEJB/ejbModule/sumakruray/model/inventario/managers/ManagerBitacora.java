package sumakruray.model.inventario.managers;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import sumakruray.model.core.entities.Accesorio;
import sumakruray.model.core.entities.AudBitacora;
import sumakruray.model.core.entities.BitacoraAccesorio;
import sumakruray.model.core.entities.BitacoraEquipo;
import sumakruray.model.core.entities.Equipo;
import sumakruray.model.core.managers.ManagerDAO;
import sumakruray.model.seguridades.dtos.LoginDTO;

/**
 * Session Bean implementation class ManagerBitacora
 */
@Stateless
@LocalBean
public class ManagerBitacora {
	@EJB
	private ManagerDAO mDAO;

	/**
	 * Default constructor.
	 */
	public ManagerBitacora() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Metodo registro de pistas de auditoria.
	 * 
	 * @param clase        Informacion de la clase que se esta depurando.
	 * @param nombreMetodo Metodo que genera el mensaje para depuracion.
	 * @param mensaje      El mensaje a desplegar.
	 */
	public void mostrarLogAccesorio(final LoginDTO loginDTO, Accesorio accesorio, String nombreMetodo, String mensaje) {
		Timestamp tiempo = new Timestamp(System.currentTimeMillis());
		System.out.println(tiempo + " [" + loginDTO.getCorreo() + "@" + loginDTO.getDireccionIP() + ":"
				+ accesorio.getAcceNombre() + "/" + nombreMetodo + "]: " + mensaje);

		BitacoraAccesorio pista = new BitacoraAccesorio();
		pista.setAccesorio(accesorio);
		pista.setBitAcceFechaCrea(tiempo);
		pista.setBitAcceEvento(nombreMetodo);
		pista.setBitAcceObservacion(mensaje);
		pista.setBitAcceUsuarioCrea("" + loginDTO.getCorreo());
		try {
			mDAO.insertar(pista);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<BitacoraAccesorio> findBitacoraAccesorioByFecha(Date fechaInicio, Date fechaFin) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		System.out.println("fecha inicio: " + format.format(fechaInicio));
		System.out.println("fecha fin: " + format.format(fechaFin));
		String consulta = "select b from BitacoraAccesorio b where b.bitAcceFechaCrea between :fechaInicio and :fechaFin order by b.bitAcceFechaCrea";
		
		Query q = mDAO.getEntityManager().createQuery(consulta, BitacoraAccesorio.class);
		q.setParameter("fechaInicio", new Timestamp(fechaInicio.getTime()));
		q.setParameter("fechaFin", new Timestamp(fechaFin.getTime()));
		return q.getResultList();

	}
	
	/**
	 * Metodo registro de pistas de auditoria.
	 * 
	 * @param clase        Informacion de la clase que se esta depurando.
	 * @param nombreMetodo Metodo que genera el mensaje para depuracion.
	 * @param mensaje      El mensaje a desplegar.
	 */
	public void mostrarLogEquipo(final LoginDTO loginDTO, Equipo Equipo, String nombreMetodo, String mensaje) {
		Timestamp tiempo = new Timestamp(System.currentTimeMillis());
		System.out.println(tiempo + " [" + loginDTO.getCorreo() + "@" + loginDTO.getDireccionIP() + ":"
				+ Equipo.getEquiNombre() + "/" + nombreMetodo + "]: " + mensaje);

		BitacoraEquipo pista = new BitacoraEquipo();
		pista.setEquipo(Equipo);
		pista.setBitEquiFechaCrea(tiempo);
		pista.setBitEquiEvento(nombreMetodo);
		pista.setBitEquiObservacion(mensaje);
		pista.setBitEquiUsuarioCrea("" + loginDTO.getCorreo());
		try {
			mDAO.insertar(pista);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<BitacoraEquipo> findBitacoraEquipoByFecha(Date fechaInicio, Date fechaFin) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		System.out.println("fecha inicio: " + format.format(fechaInicio));
		System.out.println("fecha fin: " + format.format(fechaFin));
		String consulta = "select b from BitacoraEquipo b where b.bitEquiFechaCrea between :fechaInicio and :fechaFin order by b.bitEquiFechaCrea";
		
		Query q = mDAO.getEntityManager().createQuery(consulta, BitacoraEquipo.class);
		q.setParameter("fechaInicio", new Timestamp(fechaInicio.getTime()));
		q.setParameter("fechaFin", new Timestamp(fechaFin.getTime()));
		return q.getResultList();

	}

}
