package com.kike.colegio.negocio;


import com.kike.colegio.dao.AlumnoDAO;
import com.kike.colegio.dao.AsignaturaDAO;
import com.kike.colegio.dao.impl.AlumnoDAOImpl;
import com.kike.colegio.dao.impl.AsignaturaDAOImpl;
import com.kike.colegio.dao.implhib.AlumnoDAOImplHib;
import com.kike.colegio.dao.implhib.AsignaturaDAOImplHib;

public class NegocioImpl implements INegocio{
	
	@Override
	public Double calcularTasa(String idAlumno, String idAsignatura) {
		
		AsignaturaDAO asig = new AsignaturaDAOImplHib();
		AlumnoDAO al = new AlumnoDAOImplHib();
		
		int numAsigMatriculadas = asig.obtenerNumeroAsignaturasMatriculadas(idAlumno); 
		Double tasa =asig.obtenerTasaAsignatura(idAsignatura);
		
		if ((numAsigMatriculadas >= 3) && (numAsigMatriculadas <= 5)) { 
			tasa = tasa * 0.7;
		}else if(numAsigMatriculadas >= 6) { 
			tasa = tasa * 0.5;
		}
		
		if(al.esFamiliaNumerosa(idAlumno)) {
			tasa = tasa * 0.5; 
		}		
		
		return tasa;
		
	}
}
