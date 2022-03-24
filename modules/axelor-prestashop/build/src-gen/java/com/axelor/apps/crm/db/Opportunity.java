package com.axelor.apps.crm.db;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.axelor.apps.base.db.Company;
import com.axelor.apps.base.db.Currency;
import com.axelor.apps.base.db.Partner;
import com.axelor.apps.base.db.Source;
import com.axelor.apps.base.db.TradingName;
import com.axelor.apps.sale.db.SaleOrder;
import com.axelor.auth.db.AuditableModel;
import com.axelor.auth.db.User;
import com.axelor.db.annotations.EqualsInclude;
import com.axelor.db.annotations.Track;
import com.axelor.db.annotations.TrackEvent;
import com.axelor.db.annotations.TrackField;
import com.axelor.db.annotations.TrackMessage;
import com.axelor.db.annotations.Widget;
import com.axelor.team.db.Team;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "CRM_OPPORTUNITY", indexes = { @Index(columnList = "name"), @Index(columnList = "partner"), @Index(columnList = "currency"), @Index(columnList = "opportunity_type"), @Index(columnList = "source"), @Index(columnList = "company"), @Index(columnList = "lead"), @Index(columnList = "user_id"), @Index(columnList = "team"), @Index(columnList = "lost_reason"), @Index(columnList = "trading_name") })
@Track(fields = { @TrackField(name = "name") }, messages = { @TrackMessage(message = "Opportunity created", condition = "true", on = TrackEvent.CREATE), @TrackMessage(message = "Opportunity won", condition = "salesStageSelect == 5", tag = "success", fields = "salesStageSelect"), @TrackMessage(message = "Opportunity lost", condition = "salesStageSelect == 6", tag = "warning", fields = "salesStageSelect") })
public class Opportunity extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CRM_OPPORTUNITY_SEQ")
	@SequenceGenerator(name = "CRM_OPPORTUNITY_SEQ", sequenceName = "CRM_OPPORTUNITY_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "Name")
	@NotNull
	private String name;

	@Widget(title = "Customer")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Partner partner;

	@Widget(title = "Currency")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Currency currency;

	@Widget(title = "Expected close date")
	private LocalDate expectedCloseDate;

	@Widget(title = "Amount")
	private BigDecimal amount = BigDecimal.ZERO;

	@Widget(title = "Type of need")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private OpportunityType opportunityType;

	@Widget(title = "Best case")
	private BigDecimal bestCase = BigDecimal.ZERO;

	@Widget(title = "Worst case")
	private BigDecimal worstCase = BigDecimal.ZERO;

	@Widget(title = "Sales stage", selection = "crm.opportunity.sales.stage.select")
	private Integer salesStageSelect = 0;

	@Widget(title = "Probability (%)")
	private BigDecimal probability = BigDecimal.ZERO;

	@Widget(title = "Next step")
	private String nextStep;

	@Widget(title = "Description")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String description;

	@Widget(title = "Customer Description")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String customerDescription;

	@Widget(title = "Source")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Source source;

	@Widget(title = "Company")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Company company;

	private Integer orderByState = 0;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String memo;

	@Widget(title = "Lead")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Lead lead;

	@Widget(title = "Assigned to")
	@JoinColumn(name = "user_id")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private User user;

	@Widget(title = "Team")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Team team;

	@Widget(title = "Lost reason")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private LostReason lostReason;

	@Widget(title = "Lost reason")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String lostReasonStr;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private TradingName tradingName;

	@EqualsInclude
	@Widget(title = "Reference", readonly = true)
	@Column(unique = true)
	private String opportunitySeq;

	@Widget(title = "Sale orders")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "opportunity", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SaleOrder> saleOrderList;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public Opportunity() {
	}

	public Opportunity(String name) {
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

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public LocalDate getExpectedCloseDate() {
		return expectedCloseDate;
	}

	public void setExpectedCloseDate(LocalDate expectedCloseDate) {
		this.expectedCloseDate = expectedCloseDate;
	}

	public BigDecimal getAmount() {
		return amount == null ? BigDecimal.ZERO : amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public OpportunityType getOpportunityType() {
		return opportunityType;
	}

	public void setOpportunityType(OpportunityType opportunityType) {
		this.opportunityType = opportunityType;
	}

	public BigDecimal getBestCase() {
		return bestCase == null ? BigDecimal.ZERO : bestCase;
	}

	public void setBestCase(BigDecimal bestCase) {
		this.bestCase = bestCase;
	}

	public BigDecimal getWorstCase() {
		return worstCase == null ? BigDecimal.ZERO : worstCase;
	}

	public void setWorstCase(BigDecimal worstCase) {
		this.worstCase = worstCase;
	}

	public Integer getSalesStageSelect() {
		return salesStageSelect == null ? 0 : salesStageSelect;
	}

	public void setSalesStageSelect(Integer salesStageSelect) {
		this.salesStageSelect = salesStageSelect;
	}

	public BigDecimal getProbability() {
		return probability == null ? BigDecimal.ZERO : probability;
	}

	public void setProbability(BigDecimal probability) {
		this.probability = probability;
	}

	public String getNextStep() {
		return nextStep;
	}

	public void setNextStep(String nextStep) {
		this.nextStep = nextStep;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCustomerDescription() {
		return customerDescription;
	}

	public void setCustomerDescription(String customerDescription) {
		this.customerDescription = customerDescription;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Integer getOrderByState() {
		return orderByState == null ? 0 : orderByState;
	}

	public void setOrderByState(Integer orderByState) {
		this.orderByState = orderByState;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Lead getLead() {
		return lead;
	}

	public void setLead(Lead lead) {
		this.lead = lead;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public LostReason getLostReason() {
		return lostReason;
	}

	public void setLostReason(LostReason lostReason) {
		this.lostReason = lostReason;
	}

	public String getLostReasonStr() {
		return lostReasonStr;
	}

	public void setLostReasonStr(String lostReasonStr) {
		this.lostReasonStr = lostReasonStr;
	}

	public TradingName getTradingName() {
		return tradingName;
	}

	public void setTradingName(TradingName tradingName) {
		this.tradingName = tradingName;
	}

	public String getOpportunitySeq() {
		return opportunitySeq;
	}

	public void setOpportunitySeq(String opportunitySeq) {
		this.opportunitySeq = opportunitySeq;
	}

	public List<SaleOrder> getSaleOrderList() {
		return saleOrderList;
	}

	public void setSaleOrderList(List<SaleOrder> saleOrderList) {
		this.saleOrderList = saleOrderList;
	}

	/**
	 * Add the given {@link SaleOrder} item to the {@code saleOrderList}.
	 *
	 * <p>
	 * It sets {@code item.opportunity = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addSaleOrderListItem(SaleOrder item) {
		if (getSaleOrderList() == null) {
			setSaleOrderList(new ArrayList<>());
		}
		getSaleOrderList().add(item);
		item.setOpportunity(this);
	}

	/**
	 * Remove the given {@link SaleOrder} item from the {@code saleOrderList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeSaleOrderListItem(SaleOrder item) {
		if (getSaleOrderList() == null) {
			return;
		}
		getSaleOrderList().remove(item);
	}

	/**
	 * Clear the {@code saleOrderList} collection.
	 *
	 * <p>
	 * If you have to query {@link SaleOrder} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearSaleOrderList() {
		if (getSaleOrderList() != null) {
			getSaleOrderList().clear();
		}
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
		if (!(obj instanceof Opportunity)) return false;

		final Opportunity other = (Opportunity) obj;
		if (this.getId() != null || other.getId() != null) {
			return Objects.equals(this.getId(), other.getId());
		}

		return Objects.equals(getOpportunitySeq(), other.getOpportunitySeq())
			&& (getOpportunitySeq() != null);
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
			.add("expectedCloseDate", getExpectedCloseDate())
			.add("amount", getAmount())
			.add("bestCase", getBestCase())
			.add("worstCase", getWorstCase())
			.add("salesStageSelect", getSalesStageSelect())
			.add("probability", getProbability())
			.add("nextStep", getNextStep())
			.add("orderByState", getOrderByState())
			.add("opportunitySeq", getOpportunitySeq())
			.omitNullValues()
			.toString();
	}
}
