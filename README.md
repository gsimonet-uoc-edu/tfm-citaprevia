1. Ubicar-se a la carpeta arrel del projecte clonat
Ex: C:\Users\simonet\git-09-2025\tfm-citaprevia>

2. Encendre Docker Desktop

3. Construir i iniciar l'entorn
C:\Users\simonet\git-09-2025\tfm-citaprevia>docker compose up --build

4. URLs per accedir als serveis:

- Back-end (Swagger): http://localhost:8080/citapreviaapi/swagger-ui/index.html

- Consola BBDD H2: http://localhost:8082/login.jsp
	- Credencials:
		- URL JDBC: jdbc:h2:tcp://localhost:1521/mem:citaprevia
		- User: sa
		- Password
	
- Front-end:
 - Part privada: http://localhost:8081/citapreviafront/private/login
 	- Credencials:
 		- Ajuntament de Barcelona (AJB)
 			- Usuari: JGOMEZ    Password: 1234 Perfil: TECNIC
 			- Usuari: GSIMONET  Password: 1234 Perfil: ADMINISTRADOR
 			- Usuari: AGINARD   Password: 1234 Perfil: TECNIC
 			- Usuari: JENSENYAT Password: 1234 Perfil: TECNIC
 		- Centre de Fisioterapia FisioCat (FIS)
  			- Usuari: FISIO1    Password: 1234 Perfil: TECNIC
 			- Usuari: FISIO2    Password: 1234 Perfil: ADMINISTRADOR
 		- Acadèmia Oposicions Oposite
 			- Usuari: GRAMON    Password: 1234 Perfil: ADMINISTRADOR	
 - Part pública:
 	- Ajuntament de Barcelona: 		   		    http://localhost:8081/citapreviafront/public/AJB
 	- Centre de Fisioterapia FisioCat: 		    http://localhost:8081/citapreviafront/public/FIS
 	- Acadèmia Oposicions Oposite (app buida):  http://localhost:8081/citapreviafront/public/ACA
 	
 5. Tancar els desplegaments
 
 C:\Users\simonet\git-09-2025\tfm-citaprevia>docker compose down -v