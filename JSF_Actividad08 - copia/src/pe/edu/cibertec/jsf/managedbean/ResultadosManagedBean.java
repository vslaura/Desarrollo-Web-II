package pe.edu.cibertec.jsf.managedbean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import pe.edu.cibertec.jsf.domain.Resultados;
import pe.edu.cibertec.jsf.util.JPAUtil;
import pe.edu.cibertec.jsf.viewmodel.ResultadoViewModel;

@ManagedBean(name="result")
public class ResultadosManagedBean {

	private ResultadoViewModel viewModel = new ResultadoViewModel();

//	@PostConstruct
//	public void init(){
//		JPAUtil.getEntityManager();
//	}
	
	public String guardar(){
		// aqui logica de calculo
		
		EntityManager em = JPAUtil.getEntityManager();
		
		Resultados resultados1 = null;
		Resultados resultados2 = null;
		
		List<Resultados> listaResult1 = em.createQuery("from Resultados", 
				Resultados.class)
				.getResultList();
		
		
		for (Resultados r : listaResult1) {
				if(viewModel.getPais1().equals(r.getPais())){
					resultados1 = r;
				}
				if(viewModel.getPais2().equals(r.getPais())){
					resultados2 = r;
				}
		}		
		
		// Se obtiene el registro por pais
//		Resultados resultados1 = 
//				em.createQuery("from Resultados where pais = ?", 
//						Resultados.class)
//						.setParameter(1, viewModel.getPais1())
//						.getSingleResult();
//		
//		Resultados resultados2 = 
//				em.createQuery("from Resultados where pais = ?", 
//						Resultados.class)
//						.setParameter(1, viewModel.getPais2())
//						.getSingleResult();
		
		if(resultados1 == null) resultados1 = Resultados.inicializa();
		if(resultados2 == null) resultados2 = Resultados.inicializa();
		
		resultados1.setPais(viewModel.getPais1());
		resultados1.setPartidosJugados(resultados1.getPartidosJugados()+1);
		resultados1.setGolesAnotados(
				resultados1.getGolesAnotados()+viewModel.getGoles1());
		
		
		resultados2.setPais(viewModel.getPais2());
		resultados2.setPartidosJugados(resultados2.getPartidosJugados()+1);
		resultados2.setGolesAnotados(
				resultados2.getGolesAnotados()+viewModel.getGoles2());
		
		if(viewModel.getGoles1()>viewModel.getGoles2()){
			resultados1.setPartidosGanados(
					resultados1.getPartidosGanados()+1);
			resultados2.setPartidosPerdidos(
					resultados2.getPartidosPerdidos()+1);
		}
		
		
		if(viewModel.getGoles1()<viewModel.getGoles2()){
			resultados2.setPartidosGanados(
					resultados2.getPartidosGanados()+1);
			resultados1.setPartidosPerdidos(
					resultados1.getPartidosPerdidos()+1);
		}
		
		if(viewModel.getGoles1() == viewModel.getGoles2()){
			resultados1.setPartidosEmpatados(
					resultados1.getPartidosEmpatados()+1);
			resultados2.setPartidosEmpatados(
					resultados2.getPartidosEmpatados()+1);
		}
		
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
			em.persist(resultados1);
			em.persist(resultados2);
		tx.commit();
		
		viewModel = new ResultadoViewModel();
		
		return null;
	}

	public ResultadoViewModel getViewModel() {
		return viewModel;
	}

	public void setViewModel(ResultadoViewModel viewModel) {
		this.viewModel = viewModel;
	}
	
	
	
	
}
