package com.axelor.apps.base.db;

import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.axelor.apps.account.db.Account;
import com.axelor.apps.account.db.Journal;
import com.axelor.apps.base.db.repo.BankDetailsListener;
import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.NameColumn;
import com.axelor.db.annotations.VirtualColumn;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "BASE_BANK_DETAILS", indexes = { @Index(columnList = "partner"), @Index(columnList = "bank"), @Index(columnList = "bank_address"), @Index(columnList = "currency"), @Index(columnList = "fullName"), @Index(columnList = "code"), @Index(columnList = "company"), @Index(columnList = "bank_account"), @Index(columnList = "journal") })
@EntityListeners({  BankDetailsListener.class })
public class BankDetails extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BASE_BANK_DETAILS_SEQ")
	@SequenceGenerator(name = "BASE_BANK_DETAILS_SEQ", sequenceName = "BASE_BANK_DETAILS_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "Partner")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Partner partner;

	@Widget(title = "Owner name")
	@Size(max = 255)
	private String ownerName;

	@Widget(title = "BIC (Bank Identifier)")
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Bank bank;

	@Widget(title = "SWIFT address")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private BankAddress bankAddress;

	@Widget(title = "IBAN / BBAN")
	@NotNull
	private String iban;

	@Widget(title = "Currency", massUpdate = true)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Currency currency;

	@Widget(title = "Bank Code")
	@Size(max = 255)
	private String bankCode;

	@Widget(title = "Sort Code")
	@Size(max = 255)
	private String sortCode;

	@Widget(title = "Account Number")
	@Size(max = 255)
	private String accountNbr;

	@Widget(title = "BBan Key")
	@Size(max = 255)
	private String bbanKey;

	@Widget(title = "IBAN / BBAN + BIC")
	@Size(max = 255)
	private String ibanBic;

	@Widget(title = "Label")
	private String label;

	@Widget(title = "Default")
	private Boolean isDefault = Boolean.FALSE;

	@Widget(title = "Bank Details", search = { "ownerName" })
	@NameColumn
	@VirtualColumn
	@Access(AccessType.PROPERTY)
	private String fullName;

	@Widget(title = "Active", massUpdate = true)
	private Boolean active = Boolean.TRUE;

	private String code;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Company company;

	@Widget(title = "Specific note on invoice")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String specificNoteOnInvoice;

	@Widget(title = "Bank accounting account")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Account bankAccount;

	@Widget(title = "Journal")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Journal journal;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public BankDetails() {
	}

	public BankDetails(String code) {
		this.code = code;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public BankAddress getBankAddress() {
		return bankAddress;
	}

	public void setBankAddress(BankAddress bankAddress) {
		this.bankAddress = bankAddress;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getSortCode() {
		return sortCode;
	}

	public void setSortCode(String sortCode) {
		this.sortCode = sortCode;
	}

	public String getAccountNbr() {
		return accountNbr;
	}

	public void setAccountNbr(String accountNbr) {
		this.accountNbr = accountNbr;
	}

	public String getBbanKey() {
		return bbanKey;
	}

	public void setBbanKey(String bbanKey) {
		this.bbanKey = bbanKey;
	}

	public String getIbanBic() {
		return ibanBic;
	}

	public void setIbanBic(String ibanBic) {
		this.ibanBic = ibanBic;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Boolean getIsDefault() {
		return isDefault == null ? Boolean.FALSE : isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public String getFullName() {
		try {
			fullName = computeFullName();
		} catch (NullPointerException e) {
			Logger logger = LoggerFactory.getLogger(getClass());
			logger.error("NPE in function field: getFullName()", e);
		}
		return fullName;
	}

	protected String computeFullName() {
		String str = "";
		if (!(ownerName == null || ownerName.isEmpty())) {
			str = ownerName;
			if (!(ibanBic == null || ibanBic.isEmpty())) {
				str = str.concat(" - ").concat(ibanBic);
			} else if (!(iban == null || iban.isEmpty())) {
				str = str.concat(" - ").concat(iban);
			} else if (!(bankCode == null || bankCode.isEmpty())
					&& !(sortCode == null || sortCode.isEmpty())
					&& !(accountNbr == null || accountNbr.isEmpty())
					&& !(bbanKey == null || bbanKey.isEmpty())) {
				str = str.concat(" - ").concat(bankCode)
									   .concat(sortCode)
									   .concat(accountNbr)
									   .concat(bbanKey);
			}
			if (!(code == null || code.isEmpty())) {
				str = str.concat(" - ").concat(code);
			}
		}
		return str;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Boolean getActive() {
		return active == null ? Boolean.FALSE : active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getSpecificNoteOnInvoice() {
		return specificNoteOnInvoice;
	}

	public void setSpecificNoteOnInvoice(String specificNoteOnInvoice) {
		this.specificNoteOnInvoice = specificNoteOnInvoice;
	}

	public Account getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(Account bankAccount) {
		this.bankAccount = bankAccount;
	}

	public Journal getJournal() {
		return journal;
	}

	public void setJournal(Journal journal) {
		this.journal = journal;
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
		if (!(obj instanceof BankDetails)) return false;

		final BankDetails other = (BankDetails) obj;
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
			.add("ownerName", getOwnerName())
			.add("iban", getIban())
			.add("bankCode", getBankCode())
			.add("sortCode", getSortCode())
			.add("accountNbr", getAccountNbr())
			.add("bbanKey", getBbanKey())
			.add("ibanBic", getIbanBic())
			.add("label", getLabel())
			.add("isDefault", getIsDefault())
			.add("active", getActive())
			.add("code", getCode())
			.omitNullValues()
			.toString();
	}
}
