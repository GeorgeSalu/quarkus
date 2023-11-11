package org.acme;

import java.time.LocalDateTime;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
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
	
	@POST
	@Path("/nome")
	public String nomeProduto(String nomeProduto) {
		LocalDateTime dateTime = LocalDateTime.now();
		return nomeProduto+" foi processado em : "+dateTime;
	}
	
	@PUT
	@Path("/nome")
	public String putNomeProduto(String nomeProduto) {
		LocalDateTime dateTime = LocalDateTime.now();
		return nomeProduto+" foi processado em : "+dateTime;
	}
	
}
