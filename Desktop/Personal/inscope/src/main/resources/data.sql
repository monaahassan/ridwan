DELETE
FROM issue;

DELETE
FROM projects;

DELETE
FROM users;

DELETE
FROM statustypes;

DELETE
FROM issuetypes;

DELETE
FROM roletypes;

INSERT INTO roletypes (roletypeid, roletype)
VALUES (1, 'Project Manager'),
       (2, 'Developer');

INSERT INTO issuetype (issuetypeid, issuetype)
VALUES (1, 'Bug'),
       (2, 'Task');

INSERT INTO statustypes (statustypeid, status)
VALUES (1, 'Open Ticket'),
       (2, 'Assigned to Developer'),
       (3, 'In Progress'),
       (4, 'Testing'),
       (5, 'Completed');

INSERT INTO users (userid, username, roletypeid, email, password, createdby, createddate, lastmodifiedby,
                   lastmodifieddate)
VALUES (1, 'Mona Hassan', 1, 'monaehassan@gmail.com', 'test1234', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM',
        CURRENT_TIMESTAMP),
       (2, 'Rhea Manuel', 2, 'rhea@gmail.com', 'password', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM',
        CURRENT_TIMESTAMP),
       (3, 'Stephanie Enciso', 2, 'stephanie@gmail.com', 'password', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM',
        CURRENT_TIMESTAMP);

INSERT INTO projects (projectid, projectname, projectdescription, createdby, createddate, lastmodifiedby,
                      lastmodifieddate)
VALUES (1, 'In Scope Project Management System', 'Project Management System for Developers', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM',
        CURRENT_TIMESTAMP),
       (2, 'Secret Family Recipe', 'Personal Recipe Keeper', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP),
       (3, 'Water My Plant', 'App for Water Plant Reminder', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP);

INSERT INTO issue (issueid, issuename, issuedescription, issuetypeid, projectid, userid, statustypeid,
                      createdby, createddate, lastmodifiedby, lastmodifieddate)
VALUES (1, 'Error Issue', 'Error Issue', 1, 1, 1, 1, 'SYSTEM', CURRENT_TIMESTAMP,
        'SYSTEM', CURRENT_TIMESTAMP),
       (2, 'Finish Styling', 'Dashboard needs fixing', 1, 1, 1, 1, 'SYSTEM', CURRENT_TIMESTAMP,
        'SYSTEM', CURRENT_TIMESTAMP);

/*
We must tell hibernate the ids that have already been used.
The number must be larger than the last used id.
20 > 5 so we are good!
 */

alter sequence hibernate_sequence restart with 20;