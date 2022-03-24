package com.axelor.apps.base.db;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.axelor.apps.account.db.Invoice;
import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "BASE_ALARM", indexes = { @Index(columnList = "alarm_engine"), @Index(columnList = "partner"), @Index(columnList = "invoice") })
public class Alarm extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BASE_ALARM_SEQ")
	@SequenceGenerator(name = "BASE_ALARM_SEQ", sequenceName = "BASE_ALARM_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "Date")
	@NotNull
	@Column(name = "date_val")
	private ZonedDateTime date;

	@Widget(title = "Alarm engine")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private AlarmEngine alarmEngine;

	@Widget(title = "Suspend Alarm")
	private Boolean acquitOk = Boolean.FALSE;

	@Widget(title = "Message")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String content;

	@Widget(title = "Partner")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Partner partner;

	@Widget(title = "Invoice")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Invoice invoice;

	@Widget(title = "Due balance debt recovery")
	private BigDecimal balanceDueDebtRecovery = BigDecimal.ZERO;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public Alarm() {
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public ZonedDateTime getDate() {
		return date;
	}

	public void setDate(ZonedDateTime date) {
		this.date = date;
	}

	public AlarmEngine getAlarmEngine() {
		return alarmEngine;
	}

	public void setAlarmEngine(AlarmEngine alarmEngine) {
		this.alarmEngine = alarmEngine;
	}

	public Boolean getAcquitOk() {
		return acquitOk == null ? Boolean.FALSE : acquitOk;
	}

	public void setAcquitOk(Boolean acquitOk) {
		this.acquitOk = acquitOk;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public BigDecimal getBalanceDueDebtRecovery() {
		return balanceDueDebtRecovery == null ? BigDecimal.ZERO : balanceDueDebtRecovery;
	}

	public void setBalanceDueDebtRecovery(BigDecimal balanceDueDebtRecovery) {
		this.balanceDueDebtRecovery = balanceDueDebtRecovery;
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
		if (!(obj instanceof Alarm)) return false;

		final Alarm other = (Alarm) obj;
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
			.add("date", getDate())
			.add("acquitOk", getAcquitOk())
			.add("balanceDueDebtRecovery", getBalanceDueDebtRecovery())
			.omitNullValues()
			.toString();
	}
}
