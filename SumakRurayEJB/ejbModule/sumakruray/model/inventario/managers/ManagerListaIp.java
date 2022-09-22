package sumakruray.model.inventario.managers;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import sumakruray.model.auditoria.managers.ManagerAuditoria;
import sumakruray.model.core.entities.Equipo;
import sumakruray.model.core.entities.EquipoMantenimiento;
import sumakruray.model.core.entities.ListaIp;
import sumakruray.model.core.managers.ManagerDAO;
import sumakruray.model.seguridades.dtos.LoginDTO;

/**
 * Session Bean implementation class ManagerListaIp
 */
@Stateless
@LocalBean
public class ManagerListaIp {
	@EJB
	private ManagerDAO mDAO;
	@EJB
	private ManagerAuditoria mAuditoria;
	@EJB
	private ManagerBitacora managerBitacora;

	/**
	 * Default constructor.
	 */
	public ManagerListaIp() {
		// TODO Auto-generated constructor stub
	}

	// *********************************IPS*******************************************************************************************
	/**
	 * Consultar todos los registros de la tabla ListaIP
	 */
	public List<ListaIp> findAllListaIps() {
		return mDAO.findAll(ListaIp.class, null);
	}

	/**
	 * Consultar un registro de Equipo Mantenimiento
	 */
	public ListaIp findListaIpByEquiId(int equiId) throws Exception {

		List<ListaIp> ListaIp = mDAO.findWhere(ListaIp.class, "equi_id=" + equiId + "", null);
		if (ListaIp.size() > 0) {
			return ListaIp.get(0);
		}
		return (new ListaIp());
	}

	/**
	 * Actualizar un rgistro de un ListaIP
	 */
	public void actualizarListaIp(LoginDTO loginDTO, ListaIp edicionListaIp) throws Exception {
		ListaIp listaIp = (ListaIp) mDAO.findById(ListaIp.class, edicionListaIp.getIpsId());
		listaIp.setIpsIp(edicionListaIp.getIpsIp());
		listaIp.setIpsPuertaEnlace(edicionListaIp.getIpsPuertaEnlace());
		listaIp.setIpsObservacion(edicionListaIp.getIpsObservacion());
		listaIp.setEquipo(edicionListaIp.getEquipo());
		mDAO.actualizar(listaIp);
		mAuditoria.mostrarLog(loginDTO, getClass(), "actualizarListaIp",
				"se actualizó la ListaIp " + listaIp.getIpsIp());
	}

	/**
	 * Insertar un nuevo registro de ListaIp
	 */
	public void insertarListaIp(ListaIp nuevoListaIp) throws Exception {
		mDAO.insertar(nuevoListaIp);
	}

	/**
	 * Consultar un registro de Ip con su ipsId
	 */
	public ListaIp findByIdListaIp(int ipsId) throws Exception {
		return (ListaIp) mDAO.findById(ListaIp.class, ipsId);
	}

	/**
	 * Cambiar de estado a un registro de una ListaIp
	 */
	public void cambiarEstadoListaIp(LoginDTO loginDTO, ListaIp listaIp, String estado) throws Exception {

		if (estado.equals("Activo")) {
			mDAO.actualizar(listaIp);
			managerBitacora.mostrarLogEquipo(loginDTO, listaIp.getEquipo(), "AsignarIp",
					" la Ip: " + listaIp.getIpsIp() + " es asignado al equipo: " + listaIp.getEquipo().getEquiNombre()
							+ " - " + listaIp.getEquipo().getEquiNroSerie());

		} else if (estado.equals("Inactivo")) {
			mDAO.actualizar(listaIp);

			managerBitacora.mostrarLogEquipo(loginDTO, listaIp.getEquipo(), "QuitarIp",
					" la Ip: " + listaIp.getIpsIp() + " es fue  quitado equipo: " + listaIp.getEquipo().getEquiNombre()
							+ " - " + listaIp.getEquipo().getEquiNroSerie());

		}
	}

	/**
	 * Consultar la ultima IP Insertada
	 */
	public ListaIp ConsultarUltimaListaIp() throws Exception {
		int NumeroMaximo = (int) mDAO.obtenerValorMax(ListaIp.class, "ipsId");
		System.out.println("NumeroMaximo +" + NumeroMaximo);
		ListaIp listaIp = (ListaIp) mDAO.findById(ListaIp.class, NumeroMaximo);
		return listaIp;
	}

	/**
	 * Consultas de registros de los IpS no asignados
	 */
	public List<ListaIp> findIdSnEquipo() throws Exception {
		String ips_estado = "Inactivo";
		return mDAO.findWhere(ListaIp.class, "ipsEstado= '" + ips_estado + "'", null);

	}
	// *********************************IPS*********************************************************************************************

}
