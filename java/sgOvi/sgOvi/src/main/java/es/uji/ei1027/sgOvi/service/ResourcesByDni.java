package es.uji.ei1027.sgOvi.service;

import java.util.List;
import java.util.Map;

public interface ResourcesByDni {
    //Interfaz para obtener recursos a partir del dni
    List<Map<String, Object>> getContractsByDni(String dni);
}