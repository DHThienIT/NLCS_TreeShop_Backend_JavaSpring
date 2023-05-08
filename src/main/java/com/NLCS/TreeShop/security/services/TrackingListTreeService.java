package com.NLCS.TreeShop.security.services;

import java.util.List;
import org.springframework.stereotype.Component;
import com.NLCS.TreeShop.models.Tree;

@Component
public interface TrackingListTreeService {
	List<Tree> updateTreeInTrackingList(Long userId, Long treeId); 
}
