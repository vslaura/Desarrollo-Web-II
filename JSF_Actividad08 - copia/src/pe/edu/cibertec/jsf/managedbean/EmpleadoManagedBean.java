package pe.edu.cibertec.jsf.managedbean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import pe.edu.cibertec.jsf.domain.Empleado;
import pe.edu.cibertec.jsf.util.JPAUtil;

@ManagedBean
@SessionScoped
public class EmpleadoManagedBean {

	private Empleado empleado = new Empleado();
	
	private List<Empleado> empleados = new ArrayList<Empleado>();

//	@PostConstruct
//	public void init(){
//		JPAUtil.getEntityManager();
//	}
	
	public void guardarEmpleado(){
		// Obtenemos el entity Manager
				EntityManager em = JPAUtil.getEntityManager();
				EntityTransaction tx = em.getTransaction();

				// mando a guardar
				tx.begin();
					em.persist(empleado);
				tx.commit();
	}
	
	public void guardar(){
		
		guardarEmpleado();
		// Inicializo para tener el formulario limpio (para el sgte ingreso)
		empleado = new Empleado();

	}

	public String guardarYEnviar(){
		guardarEmpleado();
		return "lista_empleados";
	}
	
	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public List<Empleado> getEmpleados() {
		EntityManager em = JPAUtil.getEntityManager();
		
		empleados = em.createQuery("from Empleado", 
				Empleado.class).getResultList();
		
		return empleados;
	}

	public void setEmpleados(List<Empleado> empleados) {
		this.empleados = empleados;
	}
	
	
	
}
