package com.axelor.apps.base.db;

import java.time.ZonedDateTime;
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

import com.axelor.apps.account.db.AccountingBatch;
import com.axelor.apps.account.db.InvoiceBatch;
import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.Widget;
import com.axelor.meta.db.MetaFile;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "BASE_BATCH", indexes = { @Index(columnList = "alarm_engine_batch"), @Index(columnList = "base_batch"), @Index(columnList = "mail_batch"), @Index(columnList = "meta_file"), @Index(columnList = "invoice_batch"), @Index(columnList = "accounting_batch") })
public class Batch extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BASE_BATCH_SEQ")
	@SequenceGenerator(name = "BASE_BATCH_SEQ", sequenceName = "BASE_BATCH_SEQ", allocationSize = 1)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private AlarmEngineBatch alarmEngineBatch;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private BaseBatch baseBatch;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private MailBatch mailBatch;

	@Widget(title = "Start Date", readonly = true)
	private ZonedDateTime startDate;

	@Widget(title = "End Date", readonly = true)
	private ZonedDateTime endDate;

	@Widget(title = "Duration", readonly = true)
	private Long duration = 0L;

	@Widget(title = "Succeeded")
	private Integer done = 0;

	@Widget(title = "Anomaly")
	private Integer anomaly = 0;

	@Widget(title = "Comments")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String comments;

	@Widget(title = "File")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private MetaFile metaFile;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private InvoiceBatch invoiceBatch;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private AccountingBatch accountingBatch;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public Batch() {
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public AlarmEngineBatch getAlarmEngineBatch() {
		return alarmEngineBatch;
	}

	public void setAlarmEngineBatch(AlarmEngineBatch alarmEngineBatch) {
		this.alarmEngineBatch = alarmEngineBatch;
	}

	public BaseBatch getBaseBatch() {
		return baseBatch;
	}

	public void setBaseBatch(BaseBatch baseBatch) {
		this.baseBatch = baseBatch;
	}

	public MailBatch getMailBatch() {
		return mailBatch;
	}

	public void setMailBatch(MailBatch mailBatch) {
		this.mailBatch = mailBatch;
	}

	public ZonedDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(ZonedDateTime startDate) {
		this.startDate = startDate;
	}

	public ZonedDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(ZonedDateTime endDate) {
		this.endDate = endDate;
	}

	public Long getDuration() {
		return duration == null ? 0L : duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public Integer getDone() {
		return done == null ? 0 : done;
	}

	public void setDone(Integer done) {
		this.done = done;
	}

	public Integer getAnomaly() {
		return anomaly == null ? 0 : anomaly;
	}

	public void setAnomaly(Integer anomaly) {
		this.anomaly = anomaly;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public MetaFile getMetaFile() {
		return metaFile;
	}

	public void setMetaFile(MetaFile metaFile) {
		this.metaFile = metaFile;
	}

	public InvoiceBatch getInvoiceBatch() {
		return invoiceBatch;
	}

	public void setInvoiceBatch(InvoiceBatch invoiceBatch) {
		this.invoiceBatch = invoiceBatch;
	}

	public AccountingBatch getAccountingBatch() {
		return accountingBatch;
	}

	public void setAccountingBatch(AccountingBatch accountingBatch) {
		this.accountingBatch = accountingBatch;
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
		if (!(obj instanceof Batch)) return false;

		final Batch other = (Batch) obj;
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
			.add("startDate", getStartDate())
			.add("endDate", getEndDate())
			.add("duration", getDuration())
			.add("done", getDone())
			.add("anomaly", getAnomaly())
			.omitNullValues()
			.toString();
	}
}
