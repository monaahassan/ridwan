package com.mmia.inscope.views;

public interface IssueList
{
    long getIssueid();
    String getProjectname();
    String getIssuename();
    String getIssuedescription();
    String getAssignedto();
    String getAssignedtoemail();
    String getIssuetype();
    String getStatus();
}
