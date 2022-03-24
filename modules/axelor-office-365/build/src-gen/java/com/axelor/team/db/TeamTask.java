package com.axelor.team.db;

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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.axelor.apps.base.db.Frequency;
import com.axelor.auth.db.AuditableModel;
import com.axelor.auth.db.User;
import com.axelor.db.annotations.Track;
import com.axelor.db.annotations.TrackEvent;
import com.axelor.db.annotations.TrackField;
import com.axelor.db.annotations.TrackMessage;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "TEAM_TASK", indexes = { @Index(columnList = "team"), @Index(columnList = "name"), @Index(columnList = "assigned_to"), @Index(columnList = "frequency"), @Index(columnList = "next_team_task") })
@Track(fields = { @TrackField(name = "name"), @TrackField(name = "status"), @TrackField(name = "priority"), @TrackField(name = "taskDate"), @TrackField(name = "taskDuration"), @TrackField(name = "taskDeadline"), @TrackField(name = "assignedTo") }, messages = { @TrackMessage(message = "Task created", condition = "true", on = TrackEvent.CREATE), @TrackMessage(message = "Task closed", condition = "status == 'closed'", tag = "success", on = TrackEvent.UPDATE, fields = "status"), @TrackMessage(message = "Urgent", condition = "priority == 'urgent'", tag = "important", on = TrackEvent.UPDATE, fields = "priority") }, contents = { @TrackMessage(message = "#{description}", condition = "true", fields = "description") }, files = true)
public class TeamTask extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TEAM_TASK_SEQ")
	@SequenceGenerator(name = "TEAM_TASK_SEQ", sequenceName = "TEAM_TASK_SEQ", allocationSize = 1)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Team team;

	@NotNull
	private String name;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String description;

	@Widget(selection = "team.task.status")
	private String status;

	@Widget(selection = "team.task.priority")
	private String priority;

	private Integer sequence = 0;

	private LocalDate taskDate;

	private Integer taskDuration = 0;

	private LocalDate taskDeadline;

	private String relatedModel;

	private String relatedName;

	private Long relatedId = 0L;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private User assignedTo;

	@Widget(title = "Frequency")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Frequency frequency;

	@Widget(title = "Next task", readonly = true)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private TeamTask nextTeamTask;

	@Widget(title = "First", readonly = true)
	private Boolean isFirst = Boolean.FALSE;

	@Widget(title = "Apply modifications to next tasks")
	private Boolean doApplyToAllNextTasks = Boolean.FALSE;

	@Widget(title = "Date or frequency changed", readonly = true)
	private Boolean hasDateOrFrequencyChanged = Boolean.FALSE;

	@Widget(title = "Type", selection = "team.task.type.select")
	private String typeSelect = "task";

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public TeamTask() {
	}

	public TeamTask(String name) {
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

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Integer getSequence() {
		return sequence == null ? 0 : sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public LocalDate getTaskDate() {
		return taskDate;
	}

	public void setTaskDate(LocalDate taskDate) {
		this.taskDate = taskDate;
	}

	public Integer getTaskDuration() {
		return taskDuration == null ? 0 : taskDuration;
	}

	public void setTaskDuration(Integer taskDuration) {
		this.taskDuration = taskDuration;
	}

	public LocalDate getTaskDeadline() {
		return taskDeadline;
	}

	public void setTaskDeadline(LocalDate taskDeadline) {
		this.taskDeadline = taskDeadline;
	}

	public String getRelatedModel() {
		return relatedModel;
	}

	public void setRelatedModel(String relatedModel) {
		this.relatedModel = relatedModel;
	}

	public String getRelatedName() {
		return relatedName;
	}

	public void setRelatedName(String relatedName) {
		this.relatedName = relatedName;
	}

	public Long getRelatedId() {
		return relatedId == null ? 0L : relatedId;
	}

	public void setRelatedId(Long relatedId) {
		this.relatedId = relatedId;
	}

	public User getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(User assignedTo) {
		this.assignedTo = assignedTo;
	}

	public Frequency getFrequency() {
		return frequency;
	}

	public void setFrequency(Frequency frequency) {
		this.frequency = frequency;
	}

	public TeamTask getNextTeamTask() {
		return nextTeamTask;
	}

	public void setNextTeamTask(TeamTask nextTeamTask) {
		this.nextTeamTask = nextTeamTask;
	}

	public Boolean getIsFirst() {
		return isFirst == null ? Boolean.FALSE : isFirst;
	}

	public void setIsFirst(Boolean isFirst) {
		this.isFirst = isFirst;
	}

	public Boolean getDoApplyToAllNextTasks() {
		return doApplyToAllNextTasks == null ? Boolean.FALSE : doApplyToAllNextTasks;
	}

	public void setDoApplyToAllNextTasks(Boolean doApplyToAllNextTasks) {
		this.doApplyToAllNextTasks = doApplyToAllNextTasks;
	}

	public Boolean getHasDateOrFrequencyChanged() {
		return hasDateOrFrequencyChanged == null ? Boolean.FALSE : hasDateOrFrequencyChanged;
	}

	public void setHasDateOrFrequencyChanged(Boolean hasDateOrFrequencyChanged) {
		this.hasDateOrFrequencyChanged = hasDateOrFrequencyChanged;
	}

	public String getTypeSelect() {
		return typeSelect;
	}

	public void setTypeSelect(String typeSelect) {
		this.typeSelect = typeSelect;
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
		if (!(obj instanceof TeamTask)) return false;

		final TeamTask other = (TeamTask) obj;
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
			.add("status", getStatus())
			.add("priority", getPriority())
			.add("sequence", getSequence())
			.add("taskDate", getTaskDate())
			.add("taskDuration", getTaskDuration())
			.add("taskDeadline", getTaskDeadline())
			.add("relatedModel", getRelatedModel())
			.add("relatedName", getRelatedName())
			.add("relatedId", getRelatedId())
			.add("isFirst", getIsFirst())
			.omitNullValues()
			.toString();
	}
}
