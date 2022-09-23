package sumakruray.model.inventario.managers;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

	/**
	 * METODO PARA CALCULAR LA VIDA UTIL
	 * 
	 * @throws ParseException
	 * 
	 */
	public String[] CalcularFechaEntreFechas(Timestamp fechaAdquisicion, Timestamp fechaActual, double precio)
			throws ParseException {
		SimpleDateFormat format3 = new SimpleDateFormat("dd/MM/yyyy");
		double valorDepreciados;
		DecimalFormat formato5 = new DecimalFormat("#.00");
		// Conversional formato "dd/mm/yyyy"
		String[] a = new String[2];
		String enlace = "";
		String valorDepreciado = "";
		String fechaActual1 = format3.format(fechaActual);
		System.out.println(fechaActual1 + "fecha Actual");
		Date fechaActual2 = format3.parse(fechaActual1);

		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		SimpleDateFormat format1 = new SimpleDateFormat("MM");
		SimpleDateFormat format2 = new SimpleDateFormat("dd");
		// Fecha Adquisicion
		String anios1 = format.format(fechaAdquisicion);
		String mess1 = format1.format(fechaAdquisicion);
		String dias1 = format2.format(fechaAdquisicion);

		int anio1 = Integer.parseInt(anios1);
		int mes1 = Integer.parseInt(mess1);
		int dia1 = Integer.parseInt(dias1);

		// Fecha Actual
		String anios2 = format.format(fechaActual2);
		String mess2 = format1.format(fechaActual2);
		String dias2 = format2.format(fechaActual2);

		int anio2 = Integer.parseInt(anios2);
		int mes2 = Integer.parseInt(mess2);
		int dia2 = Integer.parseInt(dias2);

		LocalDate FechaAdquisicion = LocalDate.of(anio1, mes1, dia1);
		LocalDate FechaActual2 = LocalDate.of(anio2, mes2, dia2);

		Period periodo = Period.between(FechaAdquisicion, FechaActual2);

		enlace += periodo.getYears() + " anios, ";
		enlace += periodo.getMonths() + " meses, ";
		enlace += periodo.getDays() + " dias ";
		valorDepreciados = precio - (periodo.getYears() * precio) / 5;
		valorDepreciado = formato5.format(valorDepreciados);

		System.out.println(valorDepreciado + "--" + periodo.getYears() + "..." + periodo.getYears() * precio);

		a[0] = enlace;
		a[1] = valorDepreciado;

		return a;
	}
}
