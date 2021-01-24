package com.kike.colegio.dao.implhib;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.kike.colegio.dao.AsignaturaDAO;
import com.kike.colegio.dtos.AlumnoDTO;
import com.kike.colegio.dtos.AsignaturaDTO;
import com.kike.colegio.entities.AlumnoEntity;
import com.kike.colegio.entities.AsignaturasEntity;
import com.kike.colegio.utils.DBUtils;

public class AsignaturaDAOImplHib implements AsignaturaDAO{


	@Override
	public List<AsignaturaDTO> obtenerAsignaturaPorIdNombreCursoTasa(String id, String nombre, String curso,
			String tasa) {
		String jpql = " select new com.kike.colegio.dtos.AsignaturaDTO String id, String nombre, String curso, String tasa"
				+ " (a.id, a.nombre, a.curso, a.tasa)"
				+ "FROM AlumnoEntity a";

		SessionFactory factory = DBUtils.creadorSessionFactory();
		Session s = factory.getCurrentSession();
		s.beginTransaction();

		Query query = s.createQuery(jpql).setParameter("id", "%" + id + "%").setParameter("nombre", "%" + nombre + "%").setParameter("curso", "%" + curso + "%").setParameter("tasa", "%" + tasa + "%");;
		List<AsignaturaDTO> lista = query.getResultList();

		s.close(); // Cerramos la sesión

		return lista;
	}

	@Override
	public Integer insertarAsignatura(String id, String nombre, String curso, String tasa) {

		AsignaturasEntity a = new AsignaturasEntity(Integer.parseInt(id), nombre, Integer.parseInt(curso), Double.parseDouble(tasa));
		
		SessionFactory factory = DBUtils.creadorSessionFactory();
		Session s = factory.getCurrentSession();
		s.beginTransaction();
		
	
		 Integer idPk = (Integer) s.save(a);


		s.getTransaction().commit();

		return idPk;
	}

	@Override
	public Integer actualizarAsignatura(String idOld, String idNew, String nombre, String curso, String tasa) {
		//Conversión famNumerosa a Integer (0 o 1), ya que viene a "on" o null
				AsignaturasEntity a = new AsignaturasEntity(Integer.parseInt(idNew), nombre, Integer.parseInt(curso), Double.parseDouble(tasa));
				
				SessionFactory factory = DBUtils.creadorSessionFactory();
				Session s = factory.getCurrentSession();
				
				s.beginTransaction();
				s.update(a);
				s.getTransaction().commit();

				return a.getId();
	}

	@Override
	public Integer eliminarAsignatura(String id) {
		SessionFactory factory = DBUtils.creadorSessionFactory();
		Session s = factory.getCurrentSession();
		
		s.beginTransaction();
		//Hibernate recupera la Entidad a borrar
		AsignaturasEntity a = s.get(AsignaturasEntity.class, Integer.parseInt(id));
		//Borra la entidad
	    if (a != null) {
	        s.delete(a);
	    	s.close();
	        return 1;
	    }
		s.close();
	    return 0;
	}

	@Override
	public int obtenerNumeroAsignaturasMatriculadas(String idAlumno) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double obtenerTasaAsignatura(String idAsignatura) {
		return 0;
	}
		
}
