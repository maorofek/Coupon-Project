
package CompanyServiceTest;

import java.io.StringWriter;
import java.sql.SQLException;
import java.util.Collection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.eclipse.persistence.jaxb.MarshallerProperties;

import dbdao.CompanyDBDAO;
import main.Company;

//--------------------------------http://localhost:8080/CouponProject/rest/allcompany/--------------------------------
@Path("/allcompany")
public class CompanyService {

	@GET
	@Produces(MediaType.APPLICATION_JSON)

	public String getJsonCompanies() throws SQLException, InterruptedException {
		CompanyDBDAO com = new CompanyDBDAO();
		Collection<Company> companies = null;
		JAXBContext jaxbContext;
		StringWriter sw = new StringWriter();
		try {
			companies = com.getAllCompanies();
			
			jaxbContext = JAXBContext.newInstance(Company.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			
			marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
			marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, true);
			marshaller.marshal(companies, sw);
		} catch (JAXBException e) {
			
			e.printStackTrace();
		}	
		return sw.toString();
	}
		
}
