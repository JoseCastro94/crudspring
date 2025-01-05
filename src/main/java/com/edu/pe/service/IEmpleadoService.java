package com.edu.pe.service;

import java.util.List;

import com.edu.pe.models.Empleado;

public interface IEmpleadoService {

	public List<Empleado> ListarTodos();
	
	public boolean Guardar(Empleado empleado);
	
	public Empleado BuscarPorId(int id);
	
	public boolean Eliminar(int id);
}
