
package org.tmf.openapi.troubleticket.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@XmlRootElement
@JsonFilter("troubleTicketFilter")
public class TroubleTicket implements Serializable, Comparable<TroubleTicket> {

	private static final long serialVersionUID = -8788148471610700410L;

	@Id
	@Column(name = "TT_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String correlationId;

	@NotEmpty(message = "Description is Mandatory")
	private String description;

	@NotNull(message = "Severity is Mandatory")
	private Severity severity;

	@NotEmpty(message = "Type is Mandatory")
	private String type;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssz")
	private Date creationDate;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssz")
	private Date targetResolutionDate;

	@Enumerated(EnumType.STRING)
	private Status status;

	private SubStatus subStatus;
	private String statusChangeReason;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssz")
	private Date statusChangeDate;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssz")
	private Date resolutionDate;

	@ElementCollection
	@CollectionTable(name = "RELATED_OBJECT", joinColumns = @JoinColumn(name = "OWNER_ID"))
	private List<RelatedObject> relatedObjects;
	@ElementCollection
	@CollectionTable(name = "NOTES", joinColumns = @JoinColumn(name = "OWNER_ID"))
	private List<Note> notes;
	@ElementCollection
	@CollectionTable(name = "RELATED_PARTY", joinColumns = @JoinColumn(name = "OWNER_ID"))
	private List<RelatedParty> relatedParties;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Severity getSeverity() {
		return severity;
	}

	public void setSeverity(Severity severity) {
		this.severity = severity;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public SubStatus getSubStatus() {
		return subStatus;
	}

	public void setSubStatus(SubStatus subStatus) {
		this.subStatus = subStatus;
	}

	public String getStatusChangeReason() {
		return statusChangeReason;
	}

	public void setStatusChangeReason(String statusChangeReason) {
		this.statusChangeReason = statusChangeReason;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getTargetResolutionDate() {
		return targetResolutionDate;
	}

	public void setTargetResolutionDate(Date targetResolutionDate) {
		this.targetResolutionDate = targetResolutionDate;
	}

	public Date getStatusChangeDate() {
		return statusChangeDate;
	}

	public void setStatusChangeDate(Date statusChangeDate) {
		this.statusChangeDate = statusChangeDate;
	}

	public Date getResolutionDate() {
		return resolutionDate;
	}

	public void setResolutionDate(Date resolutionDate) {
		this.resolutionDate = resolutionDate;
	}

	public List<RelatedObject> getRelatedObjects() {
		if (relatedObjects == null) {
			relatedObjects = new ArrayList<>();
		}
		return this.relatedObjects;
	}

	public void setRelatedObjects(List<RelatedObject> relatedObjects) {
		this.relatedObjects = relatedObjects;
	}

	public List<Note> getNotes() {
		if (notes == null) {
			notes = new ArrayList<>();
		}
		return this.notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	public List<RelatedParty> getRelatedParties() {

		if (relatedParties == null) {
			relatedParties = new ArrayList<>();
		}
		return this.relatedParties;
	}

	public void setRelatedParties(List<RelatedParty> relatedParties) {
		this.relatedParties = relatedParties;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TroubleTicket other = (TroubleTicket) obj;
		return (id == other.id);

	}

	@Override
	public String toString() {
		return "TroubleTicket [id=" + id + ", correlationId=" + correlationId + ", description=" + description
				+ ", severity=" + severity + ", type=" + type + ", creationDate=" + creationDate
				+ ", targetResolutionDate=" + targetResolutionDate + ", status=" + status + ", subStatus=" + subStatus
				+ ", statusChangeReason=" + statusChangeReason + ", statusChangeDate=" + statusChangeDate
				+ ", resolutionDate=" + resolutionDate + ", relatedObjects=" + relatedObjects + ", notes=" + notes
				+ ", relatedParties=" + relatedParties + "]";
	}

	@Override
	public int compareTo(TroubleTicket o) {

		return (int) (this.id - o.id);

	}

}