package com.kike.colegio.controladores.matriculaciones;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kike.colegio.dao.MatriculacionDAO;
import com.kike.colegio.dao.impl.MatriculacionDAOImpl;
import com.kike.colegio.dao.implhib.MatriculacionDAOImplHib;
import com.kike.colegio.dtos.MatriculacionDTO;

/**
 * Servlet Implation class FormularioBorrarMatriculacionesController
 */
@WebServlet("/formularioborrarmatriculaciones")
public class FormularioBorrarMatriculacionesController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FormularioBorrarMatriculacionesController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher d = request.getServletContext().getRequestDispatcher("/WEB-INF/vistas/matriculaciones/borrarMatriculaciones.jsp");
		d.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String  idAsig = request.getParameter("idAsig");
		String  nombreAsig = request.getParameter("nombreAsig");
		String  idAlum = request.getParameter("idAlum");
		String  nombreAlum = request.getParameter("nombreAlum");
		String  fecha = request.getParameter("fecha");
		String  activo = request.getParameter("activo");
		
		MatriculacionDAO m = new MatriculacionDAOImplHib();
		List<MatriculacionDTO> listaMatriculaciones = m.obtenerMatriculacionesPorIdasigNombreAsigIdalumNombrealumFechaActivo(idAsig, nombreAsig, idAlum, nombreAlum, fecha, activo);
		
		request.setAttribute("lista", listaMatriculaciones);
		
		RequestDispatcher d = request.getServletContext().getRequestDispatcher("/WEB-INF/vistas/matriculaciones/borrarMatriculaciones.jsp");
		d.forward(request, response);
	}

}
