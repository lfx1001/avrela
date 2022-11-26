package es.ubu.lsi.avrela.apm.port;

import es.ubu.lsi.avrela.apm.domain.model.Issue;

public interface IssueFinder {

  Issue findById(String repoOwner, String repoName, String issueId);

}
