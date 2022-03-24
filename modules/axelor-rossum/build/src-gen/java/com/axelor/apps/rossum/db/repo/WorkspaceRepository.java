package com.axelor.apps.rossum.db.repo;

import com.axelor.apps.rossum.db.Workspace;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class WorkspaceRepository extends JpaRepository<Workspace> {

	public WorkspaceRepository() {
		super(Workspace.class);
	}

	public Workspace findByUrl(String workspaceUrl) {
		return Query.of(Workspace.class)
				.filter("self.workspaceUrl = :workspaceUrl")
				.bind("workspaceUrl", workspaceUrl)
				.fetchOne();
	}

}

