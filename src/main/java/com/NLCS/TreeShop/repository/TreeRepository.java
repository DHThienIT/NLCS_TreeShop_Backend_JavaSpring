package com.NLCS.TreeShop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.NLCS.TreeShop.models.EColor;
import com.NLCS.TreeShop.models.Tree;

@Repository
public interface TreeRepository extends JpaRepository<Tree, Long> {
	@Query(value = "SELECT * FROM tree WHERE tree_name LIKE BINARY CONCAT('%',:filterText,'%')", nativeQuery = true)
	List<Tree> findByNameLike(@Param("filterText") String filterText);

	boolean existsByTreeName(String name);

	List<Tree> findByStatus(boolean b);

	@Query(value = "SELECT * FROM tree WHERE price > :min", nativeQuery = true)
	List<Tree> findByMinPrice(@Param("min") int min);

	@Query(value = "SELECT * FROM tree WHERE price > :min AND price <= :max", nativeQuery = true)
	List<Tree> findByMaxMinPrice(@Param("min") int min, @Param("max") int max);

	@Query(value = "SELECT * FROM tree WHERE color in :listColor", nativeQuery = true)
	List<Tree> findByColor(@Param("listColor") String[] listColor);
}
