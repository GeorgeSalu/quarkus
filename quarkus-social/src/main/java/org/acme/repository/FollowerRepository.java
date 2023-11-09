package org.acme.repository;

import java.util.List;
import java.util.Optional;

import org.acme.model.Follower;
import org.acme.model.User;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FollowerRepository implements PanacheRepository<Follower> {

	public boolean follows(User follower,User user) {
		var params = Parameters.with("follower", follower).and("user", user);
		
		PanacheQuery<Follower> query = find("follower = :follower and user = :user", params);
		Optional<Follower> result = query.firstResultOptional();
		
		return result.isPresent();
	}
	
	public List<Follower> findByUSer(Long userId) {
		PanacheQuery<Follower> query = find("user.id", userId);
		return query.list();
	}
	
}
