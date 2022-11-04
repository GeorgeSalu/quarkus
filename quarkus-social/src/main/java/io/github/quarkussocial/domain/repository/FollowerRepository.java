package io.github.quarkussocial.domain.repository;

import javax.enterprise.context.ApplicationScoped;

import io.github.quarkussocial.domain.model.Follower;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class FollowerRepository implements PanacheRepository<Follower>{

}
