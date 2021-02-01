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
		String jpql = " select new com.kike.colegio.dtos.AsignaturaDTO "
				+ " (a.id, a.nombre, a.curso, a.tasa)"
				+ "FROM AsignaturasEntity a where  CAST( a.id AS string )  LIKE :id  AND a.nombre LIKE :nombre  AND CAST( a.curso AS string )  LIKE :curso  AND CAST( a.tasa AS string )  LIKE :tasa ";

		SessionFactory factory = DBUtils.creadorSessionFactory();
		Session s = factory.getCurrentSession();
		s.beginTransaction();

		Query query = s.createQuery(jpql).setParameter("id", "%" + id + "%")
				.setParameter("nombre", "%" + nombre + "%")
				.setParameter("curso", "%" + curso + "%")
				.setParameter("tasa", "%" + tasa + "%");;
		List<AsignaturaDTO> lista = query.getResultList();

		s.close(); // Cerramos la sesión

		return lista;
	}

	@Override
	public Integer insertarAsignatura(String id, String nombre, String curso, String tasa) {

		AsignaturasEntity a = new AsignaturasEntity(Integer.parseInt(id), nombre, Integer.parseInt(curso), Integer.parseInt(tasa));
		
		SessionFactory factory = DBUtils.creadorSessionFactory();
		Session s = factory.getCurrentSession();
		s.beginTransaction();
		
	
		 Integer idPk = (Integer) s.save(a);


		s.getTransaction().commit();

		return idPk;
	}

	@Override
	public Integer actualizarAsignatura(String idOld, String idNew, String nombre, String curso, String tasa) {
		
				AsignaturasEntity a = new AsignaturasEntity(Integer.parseInt(idNew), nombre, Integer.parseInt(curso), Integer.parseInt(tasa));
				
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
		Query query = s.createQuery("DELETE FROM AsignaturasEntity where id = :id").setParameter("id", Integer.parseInt(id));
		int result = query.executeUpdate();		
		s.close();		
		return result;
	}

	@Override
	public int obtenerNumeroAsignaturasMatriculadas(String idAlumno) {
		
		String jpql = " select count(*) "
				+ "FROM MatriculacionesEntity m where  CAST( m.alumnos.id AS string )  LIKE :idAlumno";

		SessionFactory factory = DBUtils.creadorSessionFactory();
		Session s = factory.getCurrentSession();
		s.beginTransaction();

		Query query = s.createQuery(jpql).setParameter("idAlumno", "%" + idAlumno + "%");
		
		int numAsigMatriculadas = query.getFirstResult();
		s.close();	
		return numAsigMatriculadas;
		
		
	}

	@Override
	public double obtenerTasaAsignatura(String idAsignatura) {
		String jpql = " select  (a.tasa) "
				+ "FROM AsignaturasEntity a where  CAST( a.id AS string )  LIKE :idAsignatura ";

		SessionFactory factory = DBUtils.creadorSessionFactory();
		Session s = factory.getCurrentSession();
		s.beginTransaction();

		Query query = s.createQuery(jpql).setParameter("idAsignatura", "%" + idAsignatura + "%");
		
		Double numtasa =(double) query.getFirstResult();
		s.close();	
		return numtasa;
	}
		
}
