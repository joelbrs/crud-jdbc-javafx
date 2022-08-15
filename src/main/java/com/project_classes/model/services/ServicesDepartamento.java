package com.project_classes.model.services;

import com.project_classes.model.entities.Department;

import java.util.ArrayList;
import java.util.List;

public class ServicesDepartamento {

    public class DepartmentService {

    }

    public List<Department> findAll() {
        List<Department> list = new ArrayList<>();
        list.add(new Department(1, "Livros"));
        list.add(new Department(2, "Computadores"));
        list.add(new Department(3, "Eletrônicos"));

        return list;
    }

}
