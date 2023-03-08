package com.NLCS.TreeShop.security.services;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.NLCS.TreeShop.models.Tree;
import com.NLCS.TreeShop.payload.request.TreeRequest;

@Component
public interface TreeService {
	Tree createTree(TreeRequest productRequest);

	Optional<Tree> updateTree(Long treeId, TreeRequest treeRequest);

	void softDeleteTree(Long treeId);

	void hardDeleteTree(Long treeId);

	List<Tree> getAllTreeForShowHome();

	List<Tree> getAllTreeForManage();

	Tree treeReactivationById(Long treeId);

	Tree getTree(Long id);

}
