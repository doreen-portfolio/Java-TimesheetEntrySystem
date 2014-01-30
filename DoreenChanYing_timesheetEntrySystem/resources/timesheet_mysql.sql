CREATE DATABASE timesheet;

GRANT ALL PRIVILEGES ON timesheet.* TO stock@localhost IDENTIFIED BY 'check';
GRANT ALL PRIVILEGES ON timesheet.* TO stock@"%" IDENTIFIED BY 'check';

USE timesheet;

DROP TABLE IF EXISTS Timesheets;
CREATE TABLE Timesheets(UserID int, WeekNumber int, WeekEnding TINYTEXT, TotalHours DECIMAL(3, 1), TotalSat DECIMAL(3, 1), TotalSun DECIMAL(3, 1), TotalMon DECIMAL(3, 1), TotalTue DECIMAL(3, 1), TotalWed DECIMAL(3, 1), TotalThu DECIMAL(3, 1), TotalFri DECIMAL(3, 1));

DROP TABLE IF EXISTS TimesheetEntries;
CREATE TABLE TimesheetEntries(UserID int, WeekNumber int, RowID int, Project int, WP TINYTEXT, TotalHours DECIMAL(3, 1), Sat DECIMAL(3, 1), Sun DECIMAL(3, 1), Mon DECIMAL(3, 1), Tue DECIMAL(3, 1), Wed DECIMAL(3, 1), Thu DECIMAL(3, 1), Fri DECIMAL(3, 1), Notes TINYTEXT);

DROP TABLE IF EXISTS Users;
CREATE TABLE Users(UserID int, UserName TINYTEXT, FirstName TINYTEXT, LastName TINYTEXT, Password TINYTEXT, SuperuserStatus BOOLEAN);

INSERT INTO Users VALUES(1,"superuser", "Super", "User", "superpass", true);
INSERT INTO Timesheets VALUES(1, 1, "01-04-2013", 7.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0);
INSERT INTO Timesheets VALUES(1, 30, "07-26-2013", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
INSERT INTO Timesheets VALUES(2, 15, "04-12-2013", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
INSERT INTO Timesheets VALUES(2, 30, "07-26-2013", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);

