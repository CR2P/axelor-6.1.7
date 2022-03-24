package com.axelor.apps.rossum.db;

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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.axelor.apps.account.db.Invoice;
import com.axelor.apps.base.db.Company;
import com.axelor.apps.base.db.Currency;
import com.axelor.apps.base.db.Partner;
import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.NameColumn;
import com.axelor.db.annotations.Widget;
import com.axelor.meta.db.MetaFile;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "ROSSUM_INVOICE_OCR_TEMPLATE", indexes = { @Index(columnList = "template_file"), @Index(columnList = "name"), @Index(columnList = "currency"), @Index(columnList = "supplier"), @Index(columnList = "company"), @Index(columnList = "invoice"), @Index(columnList = "invoiceOcrTemplateId"), @Index(columnList = "queue"), @Index(columnList = "exported_file") })
public class InvoiceOcrTemplate extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROSSUM_INVOICE_OCR_TEMPLATE_SEQ")
	@SequenceGenerator(name = "ROSSUM_INVOICE_OCR_TEMPLATE_SEQ", sequenceName = "ROSSUM_INVOICE_OCR_TEMPLATE_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "Template", help = "Please upload file with max. 32 pages")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private MetaFile templateFile;

	@Widget(title = "Timeout (Sec.)", help = "Please enter seconds in multiple of 5")
	private Integer timeout = 60;

	@Widget(title = "Template Name")
	private String name;

	@Widget(title = "Export type", selection = "rossum.export.type.select")
	private String exportTypeSelect = "csv";

	@Widget(title = "Invoice document Type", selection = "iinvoice.operation.type.select")
	private Integer invoiceOperationTypeSelect = 0;

	@Widget(title = "Invoice document Subtype", selection = "iinvoice.operation.sub.type.select")
	private Integer invoiceOperationSubTypeSelect = 1;

	@Widget(title = "Order number")
	private String orderNumber;

	@Widget(title = "Invoice number")
	private String invoiceNumber;

	@Widget(title = "Issue date")
	private LocalDate issueDate;

	@Widget(title = "Due date")
	private LocalDate dueDate;

	@Widget(title = "Total amount")
	private BigDecimal totalAmount = BigDecimal.ZERO;

	@Widget(title = "Total without tax")
	private BigDecimal totalWithoutTax = BigDecimal.ZERO;

	@Widget(title = "Total tax")
	private BigDecimal totalTax = BigDecimal.ZERO;

	@Widget(title = "Dms file url")
	private String inlineUrl;

	@Widget(title = "Annotation url")
	private String annotaionUrl;

	@Widget(title = "Sender name")
	private String senderName;

	@Widget(title = "Vendor VAT number")
	private String vendorVatNumber;

	@Widget(title = "Customer name")
	private String customerName;

	@Widget(title = "Customer VAT number")
	private String customerVatNumber;

	@Widget(title = "Currency")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Currency currency;

	@Widget(title = "Supplier")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Partner supplier;

	@Widget(title = "Company")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Company company;

	@Widget(title = "Invoice")
	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Invoice invoice;

	@Widget(title = "Invoice ocr template N°", readonly = true)
	@NameColumn
	private String invoiceOcrTemplateId;

	@Widget(title = "Queue")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Queue queue;

	@Widget(title = "CSV file")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private MetaFile exportedFile;

	@Widget(title = "Consolidate invoice lines")
	private Boolean isInvoiceLineConsolidated = Boolean.FALSE;

	@Widget(title = "Validated")
	private Boolean isValidated = Boolean.FALSE;

	@Widget(title = "Corrected")
	private Boolean isCorrected = Boolean.FALSE;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public InvoiceOcrTemplate() {
	}

	public InvoiceOcrTemplate(String name) {
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

	/**
	 * Please upload file with max. 32 pages
	 *
	 * @return the property value
	 */
	public MetaFile getTemplateFile() {
		return templateFile;
	}

	public void setTemplateFile(MetaFile templateFile) {
		this.templateFile = templateFile;
	}

	/**
	 * Please enter seconds in multiple of 5
	 *
	 * @return the property value
	 */
	public Integer getTimeout() {
		return timeout == null ? 0 : timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExportTypeSelect() {
		return exportTypeSelect;
	}

	public void setExportTypeSelect(String exportTypeSelect) {
		this.exportTypeSelect = exportTypeSelect;
	}

	public Integer getInvoiceOperationTypeSelect() {
		return invoiceOperationTypeSelect == null ? 0 : invoiceOperationTypeSelect;
	}

	public void setInvoiceOperationTypeSelect(Integer invoiceOperationTypeSelect) {
		this.invoiceOperationTypeSelect = invoiceOperationTypeSelect;
	}

	public Integer getInvoiceOperationSubTypeSelect() {
		return invoiceOperationSubTypeSelect == null ? 0 : invoiceOperationSubTypeSelect;
	}

	public void setInvoiceOperationSubTypeSelect(Integer invoiceOperationSubTypeSelect) {
		this.invoiceOperationSubTypeSelect = invoiceOperationSubTypeSelect;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public LocalDate getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(LocalDate issueDate) {
		this.issueDate = issueDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount == null ? BigDecimal.ZERO : totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getTotalWithoutTax() {
		return totalWithoutTax == null ? BigDecimal.ZERO : totalWithoutTax;
	}

	public void setTotalWithoutTax(BigDecimal totalWithoutTax) {
		this.totalWithoutTax = totalWithoutTax;
	}

	public BigDecimal getTotalTax() {
		return totalTax == null ? BigDecimal.ZERO : totalTax;
	}

	public void setTotalTax(BigDecimal totalTax) {
		this.totalTax = totalTax;
	}

	public String getInlineUrl() {
		return inlineUrl;
	}

	public void setInlineUrl(String inlineUrl) {
		this.inlineUrl = inlineUrl;
	}

	public String getAnnotaionUrl() {
		return annotaionUrl;
	}

	public void setAnnotaionUrl(String annotaionUrl) {
		this.annotaionUrl = annotaionUrl;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getVendorVatNumber() {
		return vendorVatNumber;
	}

	public void setVendorVatNumber(String vendorVatNumber) {
		this.vendorVatNumber = vendorVatNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerVatNumber() {
		return customerVatNumber;
	}

	public void setCustomerVatNumber(String customerVatNumber) {
		this.customerVatNumber = customerVatNumber;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public Partner getSupplier() {
		return supplier;
	}

	public void setSupplier(Partner supplier) {
		this.supplier = supplier;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public String getInvoiceOcrTemplateId() {
		return invoiceOcrTemplateId;
	}

	public void setInvoiceOcrTemplateId(String invoiceOcrTemplateId) {
		this.invoiceOcrTemplateId = invoiceOcrTemplateId;
	}

	public Queue getQueue() {
		return queue;
	}

	public void setQueue(Queue queue) {
		this.queue = queue;
	}

	public MetaFile getExportedFile() {
		return exportedFile;
	}

	public void setExportedFile(MetaFile exportedFile) {
		this.exportedFile = exportedFile;
	}

	public Boolean getIsInvoiceLineConsolidated() {
		return isInvoiceLineConsolidated == null ? Boolean.FALSE : isInvoiceLineConsolidated;
	}

	public void setIsInvoiceLineConsolidated(Boolean isInvoiceLineConsolidated) {
		this.isInvoiceLineConsolidated = isInvoiceLineConsolidated;
	}

	public Boolean getIsValidated() {
		return isValidated == null ? Boolean.FALSE : isValidated;
	}

	public void setIsValidated(Boolean isValidated) {
		this.isValidated = isValidated;
	}

	public Boolean getIsCorrected() {
		return isCorrected == null ? Boolean.FALSE : isCorrected;
	}

	public void setIsCorrected(Boolean isCorrected) {
		this.isCorrected = isCorrected;
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
		if (!(obj instanceof InvoiceOcrTemplate)) return false;

		final InvoiceOcrTemplate other = (InvoiceOcrTemplate) obj;
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
			.add("timeout", getTimeout())
			.add("name", getName())
			.add("exportTypeSelect", getExportTypeSelect())
			.add("invoiceOperationTypeSelect", getInvoiceOperationTypeSelect())
			.add("invoiceOperationSubTypeSelect", getInvoiceOperationSubTypeSelect())
			.add("orderNumber", getOrderNumber())
			.add("invoiceNumber", getInvoiceNumber())
			.add("issueDate", getIssueDate())
			.add("dueDate", getDueDate())
			.add("totalAmount", getTotalAmount())
			.add("totalWithoutTax", getTotalWithoutTax())
			.omitNullValues()
			.toString();
	}
}
