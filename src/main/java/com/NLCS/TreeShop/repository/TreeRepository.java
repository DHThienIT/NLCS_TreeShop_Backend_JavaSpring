package com.NLCS.TreeShop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.NLCS.TreeShop.models.Tree;

@Repository
public interface TreeRepository extends JpaRepository<Tree, Long> {
	@Query(value="SELECT * FROM tree WHERE tree_name LIKE BINARY CONCAT('%',:text,'%')", nativeQuery = true)
	List<Tree> findByNameLike(@Param("text") String text);

	boolean existsByTreeName(String name);

	List<Tree> findByStatus(boolean b);
}
