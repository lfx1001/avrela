package es.ubu.lsi.avrela.client.github;


import com.google.gson.annotations.SerializedName;

public enum GitHubItemState {

  @SerializedName("open") OPEN,
  @SerializedName("closed") CLOSED

}
