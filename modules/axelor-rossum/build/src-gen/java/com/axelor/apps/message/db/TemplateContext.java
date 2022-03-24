package com.axelor.apps.message.db;

import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.axelor.apps.base.db.PrintTemplateLine;
import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "MESSAGE_TEMPLATE_CONTEXT", indexes = { @Index(columnList = "name"), @Index(columnList = "template"), @Index(columnList = "print_template_line") })
public class TemplateContext extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MESSAGE_TEMPLATE_CONTEXT_SEQ")
	@SequenceGenerator(name = "MESSAGE_TEMPLATE_CONTEXT_SEQ", sequenceName = "MESSAGE_TEMPLATE_CONTEXT_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "Name")
	private String name;

	@Widget(title = "Expr. value")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String value;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Template template;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private PrintTemplateLine printTemplateLine;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public TemplateContext() {
	}

	public TemplateContext(String name) {
		this.name = name;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public PrintTemplateLine getPrintTemplateLine() {
		return printTemplateLine;
	}

	public void setPrintTemplateLine(PrintTemplateLine printTemplateLine) {
		this.printTemplateLine = printTemplateLine;
	}

	public String getAttrs() {
		return attrs;
	}

	public void setAttrs(String attrs) {
		this.attrs = attrs;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (this == obj) return true;
		if (!(obj instanceof TemplateContext)) return false;

		final TemplateContext other = (TemplateContext) obj;
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
			.add("name", getName())
			.omitNullValues()
			.toString();
	}
}
