package es.ubu.lsi.avrela.apm.adapter.github.model;

import com.google.gson.annotations.SerializedName;

/**
 * Models a GitHub issue event type.
 */
public enum GitHubIssueEventType {

  @SerializedName("labeled") LABELED,
  @SerializedName("milestoned") MILESTONED,
  @SerializedName("assigned") ASSIGNED,
  @SerializedName("referenced") REFERENCED,
  @SerializedName("commented") COMMENTED,
  @SerializedName("closed") CLOSED

}
