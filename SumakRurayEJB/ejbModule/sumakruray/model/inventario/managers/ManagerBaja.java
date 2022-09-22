package sumakruray.model.inventario.managers;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import sumakruray.model.auditoria.managers.ManagerAuditoria;
import sumakruray.model.core.entities.AccesorioBaja;
import sumakruray.model.core.entities.EquipoBaja;
import sumakruray.model.core.managers.ManagerDAO;
import sumakruray.model.seguridades.dtos.LoginDTO;

/**
 * Session Bean implementation class ManagerBaja
 */
@Stateless
@LocalBean
public class ManagerBaja {
	@EJB
	private ManagerDAO mDAO;
	@EJB
	private ManagerAuditoria mAuditoria;
	@EJB
	private ManagerBitacora managerBitacora;

	/**
	 * Default constructor.
	 */
	public ManagerBaja() {
		// TODO Auto-generated constructor stub
	}

	// *********************************--__BAJA__-*********************************************************************************************
	// -------ACCESORIO BAJA--------------
	/**
	 * Consultar todos los registros de la tabla Bajas de Accesorios
	 */
	// Consulta a la Tabla de Accesorio Bajos
	public List<AccesorioBaja> findAllAccesorioBajas() {
		return mDAO.findAll(AccesorioBaja.class, null);
	}

	/**
	 * Insertar un nuevo registro de Accesorio de Baja
	 */
	// Crear un nuevo registro de accesorio en bodega
	public void insertarAccesorioBaja(LoginDTO loginDTO, AccesorioBaja nuevoBajaAccesorio) throws Exception {
		mDAO.insertar(nuevoBajaAccesorio);
		managerBitacora.mostrarLogAccesorio(loginDTO, nuevoBajaAccesorio.getAccesorio(), "InseertarBajaAccesorio",
				"Baja al accesorio " + nuevoBajaAccesorio.getAccesorio().getAcceNombre() + " por "
						+ nuevoBajaAccesorio.getBajaDescripcion());

	}

	/**
	 * Consultar todos los registros de la tabla Bajas de Equipos
	 */
	// -------Equipo BAJA--------------
	// Consulta a la Tabla de Equipo Bajos
	public List<EquipoBaja> findAllEquipoBajas() {
		return mDAO.findAll(EquipoBaja.class, null);
	}

	/**
	 * Insertar un nuevo registro de Equipos de Baja
	 */
	// Crear un nuevo registro de Equipo en bodega
	public void insertarEquipoBaja(EquipoBaja nuevoBajaEquipo) throws Exception {
		mDAO.insertar(nuevoBajaEquipo);
	}

	// -------------------------------------------------------------------------------------------------------------------------------------

}
