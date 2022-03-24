package com.axelor.apps.rossum.db.repo;

import com.axelor.apps.rossum.db.Annotation;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class AnnotationRepository extends JpaRepository<Annotation> {

	public AnnotationRepository() {
		super(Annotation.class);
	}

	public Annotation findByUrl(String annotationUrl) {
		return Query.of(Annotation.class)
				.filter("self.annotationUrl = :annotationUrl")
				.bind("annotationUrl", annotationUrl)
				.fetchOne();
	}

}

