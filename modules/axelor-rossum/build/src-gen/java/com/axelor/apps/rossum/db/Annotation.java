package com.axelor.apps.rossum.db;

import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.NameColumn;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "ROSSUM_ANNOTATION", indexes = { @Index(columnList = "annotationUrl"), @Index(columnList = "queue_url") })
public class Annotation extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROSSUM_ANNOTATION_SEQ")
	@SequenceGenerator(name = "ROSSUM_ANNOTATION_SEQ", sequenceName = "ROSSUM_ANNOTATION_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "Status", readonly = true, selection = "rossum.annotation.status.select")
	private String statusSelect;

	@Widget(title = "Annotation id", readonly = true)
	private Integer annotationId = 0;

	@Widget(title = "Annotation url", readonly = true)
	@NameColumn
	private String annotationUrl;

	@Widget(title = "Annotation result")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String annotationResult;

	@Widget(title = "Queue")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Queue queueUrl;

	public Annotation() {
	}

	public Annotation(String statusSelect, Integer annotationId, String annotationUrl, Queue queueUrl) {
		this.statusSelect = statusSelect;
		this.annotationId = annotationId;
		this.annotationUrl = annotationUrl;
		this.queueUrl = queueUrl;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getStatusSelect() {
		return statusSelect;
	}

	public void setStatusSelect(String statusSelect) {
		this.statusSelect = statusSelect;
	}

	public Integer getAnnotationId() {
		return annotationId == null ? 0 : annotationId;
	}

	public void setAnnotationId(Integer annotationId) {
		this.annotationId = annotationId;
	}

	public String getAnnotationUrl() {
		return annotationUrl;
	}

	public void setAnnotationUrl(String annotationUrl) {
		this.annotationUrl = annotationUrl;
	}

	public String getAnnotationResult() {
		return annotationResult;
	}

	public void setAnnotationResult(String annotationResult) {
		this.annotationResult = annotationResult;
	}

	public Queue getQueueUrl() {
		return queueUrl;
	}

	public void setQueueUrl(Queue queueUrl) {
		this.queueUrl = queueUrl;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (this == obj) return true;
		if (!(obj instanceof Annotation)) return false;

		final Annotation other = (Annotation) obj;
		if (this.getId() != null || other.getId() != null) {
			return Objects.equals(this.getId(), other.getId());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return 31;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("id", getId())
			.add("statusSelect", getStatusSelect())
			.add("annotationId", getAnnotationId())
			.add("annotationUrl", getAnnotationUrl())
			.omitNullValues()
			.toString();
	}
}
