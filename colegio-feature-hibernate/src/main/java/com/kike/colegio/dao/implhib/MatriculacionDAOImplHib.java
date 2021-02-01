package com.kike.colegio.dao.implhib;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.kike.colegio.dao.AlumnoDAO;
import com.kike.colegio.dao.MatriculacionDAO;
import com.kike.colegio.dtos.AlumnoDTO;
import com.kike.colegio.dtos.MatriculacionDTO;
import com.kike.colegio.entities.AlumnoEntity;
import com.kike.colegio.utils.DBUtils;

public class MatriculacionDAOImplHib implements MatriculacionDAO {

	@Override
	public List<MatriculacionDTO> obtenerMatriculacionesPorIdasigNombreAsigIdalumNombrealumFechaActivo(String idAsig,
			String nombreAsig, String idAlum, String nombreAlum, String fecha, String activo) {
		String jpql = "select new com.kike.colegio.dtos.MatriculacionDTO "
					+ " (asig.id, asig.nombre, a.id, a.nombre, m.fecha, m.activo) "
					+ " FROM  AsignaturasEntity asig, MatriculacionesEntity m, AlumnoEntity a "
					+ " WHERE m.alumnos.id=a.id and  m.asignaturas.id=asig.id and n.alumnos.id=a.id and  n.asignaturas.id=asig.id and CAST( asig.id AS string ) LIKE :idAsig AND asig.nombre LIKE :nombreAsig"
					+ " AND CAST( a.id AS string ) LIKE :idAlum  AND a.nombre LIKE :aNombre  AND m.fecha LIKE :fecha and  CAST( m.activo AS string ) LIKE :activo";
		
		
		

			SessionFactory factory = DBUtils.creadorSessionFactory();
			Session s = factory.getCurrentSession();
			s.beginTransaction();
			
			
			Query query = s.createQuery(jpql).setParameter("idAsig", "%" + idAsig + "%")
					.setParameter("nombreAsig", "%" + nombreAsig + "%")
					.setParameter("idAlum", "%" + idAlum + "%")
					.setParameter("aNombre", "%" + nombreAlum + "%")
					.setParameter("fecha", "%" + fecha + "%")
					.setParameter("activo", "%" + activo + "%");
			
			List<MatriculacionDTO> lista = query.getResultList();

			s.close(); // Cerramos la sesión

			return lista;
	}

	@Override
	public Integer insertarMatriculacion(String idAsignatura, String idAlumno, String tasa, String fecha) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer borrarMatriculacion(String idMatricula) {
		SessionFactory factory = DBUtils.creadorSessionFactory();
		Session s = factory.getCurrentSession();
		
		s.beginTransaction();
		Query query = s.createQuery("DELETE FROM MatriculacionesEntity where id = :id").setParameter("id", Integer.parseInt(idMatricula));
		int result = query.executeUpdate();		
		s.close();		
		return result;
	}

	
}