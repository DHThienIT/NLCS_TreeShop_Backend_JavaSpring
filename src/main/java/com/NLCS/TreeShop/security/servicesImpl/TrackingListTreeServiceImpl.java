package com.NLCS.TreeShop.security.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NLCS.TreeShop.models.Tree;
import com.NLCS.TreeShop.models.User;
import com.NLCS.TreeShop.repository.TreeRepository;
import com.NLCS.TreeShop.repository.UserRepository;
import com.NLCS.TreeShop.security.services.TrackingListTreeService;

@Service
public class TrackingListTreeServiceImpl implements TrackingListTreeService{
	@Autowired
	UserRepository userRepository;
	@Autowired
	TreeRepository treeRepository;
	
	@Override
	public List<Tree> updateTreeInTrackingList(Long userId, Long treeId) {
		Tree tree = treeRepository.findById(treeId).orElseThrow();
		User user = userRepository.findById(userId).orElseThrow();
		List<Tree> trackingListTree = user.getTrackingListTree();
		if(trackingListTree.isEmpty() || !trackingListTree.contains(tree)) {
			trackingListTree.add(tree);
		} else trackingListTree.remove(tree);
		user.setTrackingListTree(trackingListTree);
		userRepository.save(user);
		return user.getTrackingListTree();
	}
}
