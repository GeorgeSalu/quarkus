package org.acme;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("/produto")
public class ProdutoResource {

	@GET
	@Path("/nome")
	public String produto() {
		return "laptop";
	}
	
	@GET
	@Path("/nome/{id}")
	public String produtoById(@PathParam("id") Integer id) {
		if(id == 1) return "laptop";
		return "para esse id nao existe produto";
	}
	
}
