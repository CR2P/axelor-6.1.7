package com.axelor.apps.sale.db;

import java.math.BigDecimal;
import java.time.LocalDate;
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
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.axelor.apps.account.db.Move;
import com.axelor.apps.account.db.PaymentMode;
import com.axelor.apps.base.db.Currency;
import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.Track;
import com.axelor.db.annotations.TrackEvent;
import com.axelor.db.annotations.TrackField;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "SALE_ADVANCE_PAYMENT", indexes = { @Index(columnList = "sale_order"), @Index(columnList = "currency"), @Index(columnList = "payment_mode"), @Index(columnList = "move") })
@Track(fields = { @TrackField(name = "amount", on = TrackEvent.UPDATE), @TrackField(name = "advancePaymentDate", on = TrackEvent.UPDATE), @TrackField(name = "saleOrder", on = TrackEvent.UPDATE), @TrackField(name = "currency", on = TrackEvent.UPDATE), @TrackField(name = "statusSelect", on = TrackEvent.UPDATE) })
public class AdvancePayment extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SALE_ADVANCE_PAYMENT_SEQ")
	@SequenceGenerator(name = "SALE_ADVANCE_PAYMENT_SEQ", sequenceName = "SALE_ADVANCE_PAYMENT_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "Amount")
	@NotNull
	private BigDecimal amount = new BigDecimal("0");

	@Widget(title = "Date")
	@NotNull
	private LocalDate advancePaymentDate;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private SaleOrder saleOrder;

	@Widget(title = "Currency")
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Currency currency;

	@Widget(title = "Status", selection = "advance.payment.status.select")
	private Integer statusSelect = 0;

	@Widget(title = "Amount remaining to use", readonly = true)
	private BigDecimal amountRemainingToUse = BigDecimal.ZERO;

	@Widget(title = "Payment mode")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private PaymentMode paymentMode;

	@Widget(title = "Move", readonly = true)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Move move;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public AdvancePayment() {
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return amount == null ? BigDecimal.ZERO : amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public LocalDate getAdvancePaymentDate() {
		return advancePaymentDate;
	}

	public void setAdvancePaymentDate(LocalDate advancePaymentDate) {
		this.advancePaymentDate = advancePaymentDate;
	}

	public SaleOrder getSaleOrder() {
		return saleOrder;
	}

	public void setSaleOrder(SaleOrder saleOrder) {
		this.saleOrder = saleOrder;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public Integer getStatusSelect() {
		return statusSelect == null ? 0 : statusSelect;
	}

	public void setStatusSelect(Integer statusSelect) {
		this.statusSelect = statusSelect;
	}

	public BigDecimal getAmountRemainingToUse() {
		return amountRemainingToUse == null ? BigDecimal.ZERO : amountRemainingToUse;
	}

	public void setAmountRemainingToUse(BigDecimal amountRemainingToUse) {
		this.amountRemainingToUse = amountRemainingToUse;
	}

	public PaymentMode getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(PaymentMode paymentMode) {
		this.paymentMode = paymentMode;
	}

	public Move getMove() {
		return move;
	}

	public void setMove(Move move) {
		this.move = move;
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
		if (!(obj instanceof AdvancePayment)) return false;

		final AdvancePayment other = (AdvancePayment) obj;
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
			.add("amount", getAmount())
			.add("advancePaymentDate", getAdvancePaymentDate())
			.add("statusSelect", getStatusSelect())
			.add("amountRemainingToUse", getAmountRemainingToUse())
			.omitNullValues()
			.toString();
	}
}
