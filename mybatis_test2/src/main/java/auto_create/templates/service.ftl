package lanlyc.module.${modelName}.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lanlyc.module.${modelName}.dao.${poName}Dao;

@Service
public class ${poName}Service {

	@Autowired
	private ${poName}Dao ${daoName}Dao;

	public ${poName}Dao get${poName}Dao() {
		return ${daoName}Dao;
	}
}