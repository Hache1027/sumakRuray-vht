package sumakruray.model.inventario.managers;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import sumakruray.model.auditoria.managers.ManagerAuditoria;
import sumakruray.model.core.entities.Accesorio;
import sumakruray.model.core.entities.AccesorioAtributo;
import sumakruray.model.core.entities.Atributo;
import sumakruray.model.core.entities.EquipoAtributo;
import sumakruray.model.core.managers.ManagerDAO;
import sumakruray.model.seguridades.dtos.LoginDTO;

/**
 * Session Bean implementation class ManagerAtributo
 */
@Stateless
@LocalBean
public class ManagerAtributo {
	@EJB
	private ManagerDAO mDAO;
	@EJB
	private ManagerAuditoria mAuditoria;
	@EJB
	private ManagerBitacora managerBitacora;

	/**
	 * Default constructor.
	 */
	public ManagerAtributo() {
		// TODO Auto-generated constructor stub
	}

	// *********************************ATRIBUTOS*******************************************************************************************
	/**
	 * Consultar todos los registros de la tabla Atributos
	 */
	public List<Atributo> findAllAtributos() {
		return mDAO.findAll(Atributo.class, null);
	}

	/**
	 * Actualizar un registro de atributo
	 */
	public void actualizarAtributo(LoginDTO loginDTO, Atributo edicionAtributo) throws Exception {
		Atributo atributo = (Atributo) mDAO.findById(Atributo.class, edicionAtributo.getAtriId());
		atributo.setAtriNombre(edicionAtributo.getAtriNombre());
		mDAO.actualizar(atributo);
		mAuditoria.mostrarLog(loginDTO, getClass(), "actualizarAtributo",
				"se actualiz� al Atributo " + atributo.getAtriNombre());
	}

	/**
	 * Actualizar un registro de Accesorio Atributo
	 */
	public void actualizarAccesorioAtributo(LoginDTO loginDTO, AccesorioAtributo edicionAccesorioAtributo)
			throws Exception {
		mDAO.actualizar(edicionAccesorioAtributo);
		managerBitacora.mostrarLogAccesorio(loginDTO, edicionAccesorioAtributo.getAccesorio(),
				"ActualizarCaracteristica",
				"Se actualiz� la caracteristica " + edicionAccesorioAtributo.getAtributo().getAtriNombre() + " a : "
						+ edicionAccesorioAtributo.getAtriDescripcion());
	}

	/**
	 * Actualizar un registro de Equipo Atributo
	 */
	public void actualizarEquipoAtributo(LoginDTO loginDTO, EquipoAtributo edicionEquipoAtributo) throws Exception {
		mDAO.actualizar(edicionEquipoAtributo);
		managerBitacora.mostrarLogEquipo(loginDTO, edicionEquipoAtributo.getEquipo(), "ActualizarCaracteristica",
				"Se actualiz� la caracteristica " + edicionEquipoAtributo.getAtributo().getAtriNombre() + " a : "
						+ edicionEquipoAtributo.getAtriDescripcion());
	}

	/**
	 * Insertar un nuevo registro de Atributo
	 */
	public void insertarAtributo(Atributo nuevoAtributo) throws Exception {
		mDAO.insertar(nuevoAtributo);
	}

	/**
	 * Consultar un registro de atributo por su AtriId
	 */
	public Atributo findByIdAtributo(int atriId) throws Exception {
		return (Atributo) mDAO.findById(Atributo.class, atriId);
	}
	// *********************************ATRIBUTOS*********************************************************************************************
	
	// CAMBIOS REALIZADOS POR HACHE 
	
	// Metodo para Consultar los Atributos de un Equipo

		public List<Atributo> findBitacoraEquipoAtributosLista() {
			String consulta1 = "select DISTINCT a from EquipoAtributo ea, Atributo a where ea.atributo.atriId = a.atriId ";
			Query q = mDAO.getEntityManager().createQuery(consulta1, Atributo.class);
			return q.getResultList();

		}
		// Metodo para Consultar los Atributos de un Accesorio

		public List<Atributo> findBitacoraAccesorioAtributosLista() {
			String consulta1 = "select DISTINCT a from AccesorioAtributo aa, Atributo a where aa.atributo.atriId = a.atriId ";
			Query q = mDAO.getEntityManager().createQuery(consulta1, Atributo.class);
			return q.getResultList();

		}
	
	/**
	 * Consultar todos los registros de la tabla AccesriosAtributos
	 */
	public List<AccesorioAtributo> findAllAccesoriosAtributos() {
		return mDAO.findAll(AccesorioAtributo.class, null);
	}

	/**
	 * Consultar todos los registros de la tabla EquiposAtributos
	 */
	public List<EquipoAtributo> findAllEquiposAtributos() {
		return mDAO.findAll(EquipoAtributo.class, null);
	}
	
	// ----------------------------------------------------------------- HACHE ------------------------------------------------

}
