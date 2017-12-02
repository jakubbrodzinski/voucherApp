insert into USER_PROFILE values (1,'COMPANY'),(2,'ADMIN');


INSERT INTO COMPANY(companyId, addressDetails, city, postalCode, companyName)
	VALUES
    (1, "addressA", "cityA", 11111, "companyA"), (2, "addressB", "cityB", 22222, "companyB");
    
INSERT INTO SURVEY(id, creationDate, surveyName, companyId)
	VALUES
	  (1, curdate(), "surveyA", 1), (2, curdate(), "surveyB", 1), (3, curdate(), "surveyC", 2), (4, curdate(), "surveyD", 2);
    
INSERT INTO QUESTIONS(id, questionBody, questionType, surveyId)
	VALUES
    (1, "questionBodyA1", 1, 1), (2, "questionBodyA2", 1, 1), (3, "questionBodyA3", 2, 1), (4, "questionBodyA4", 3, 1),
    (5, "questionBodyB1", 1, 2), (6, "questionBodyB2", 1, 2), (7, "questionBodyB3", 2, 2), (8, "questionBodyB4", 3, 2),
    (9, "questionBodyC1", 1, 3), (10, "questionBodyC2", 1, 3), (11, "questionBodyC3", 2, 3), (12, "questionBodyC4", 3, 3),
    (13, "questionBodyD1", 1, 4), (14, "questionBodyD2", 1, 4), (15, "questionBodyD3", 2, 4), (16, "questionBodyD4", 3, 4);
    
INSERT INTO QUESTION_POSSIBLEANSWER(possibleAnswerA, possibleAnswerB, possibleAnswerC, possibleAnswerD, QuestionId)
	VALUES
    ("A", "B", "C", "D", 1), ("A", "B", "C", "D", 2), ("A", "B", "C", "D", 3),
    ("A", "B", "C", "D", 5), ("A", "B", "C", "D", 6), ("A", "B", "C", "D", 7), ("A", "B", "C", "D", 8),
    ("A", "B", "C", "D", 9), ("A", "B", "C", "D", 10), ("A", "B", "C", "D", 11), ("A", "B", "C", "D", 12),
    ("A", "B", "C", "D", 13), ("A", "B", "C", "D", 14), ("A", "B", "C", "D", 15), ("A", "B", "C", "D", 16);

INSERT INTO ANSWEREDSURVEYS(Id, date, age, surveyId)
    VALUES
      (1, curdate(), 1,  1),
      (2, curdate(), 1,  2),
      (3, curdate(), 1,  3),
      (4, curdate(), 1,  4);
      
INSERT INTO ANSWERS(Id, answer, answeredSurveyId, questionId)
	VALUES
	  (1, "A", 1, 1), (2, "B", 1, 2), (3, "C", 1, 3), (4, "D", 1, 4),
	  (5, "A", 1, 5), (6, "B", 1, 6), (7, "C", 1, 7), (8, "D", 1, 8),
    (9, "A", 1, 9), (10, "B", 1, 10), (11, "C", 1, 11), (12, "D", 1, 12),
    (13, "A", 1, 13), (14, "B", 1, 14), (15, "C", 1, 15), (16, "D", 1, 16);

INSERT INTO VOUCHER(Id, discountAmount, discountType, startDate, endDate, details,surveyId)
VALUES
  (1, 10, 1, curdate(), curdate(), "details1", 1),(2, 10, 1, curdate(), curdate(), "details1", 2),(3, 10, 1, curdate(), curdate(), "details1", 3);;

INSERT INTO VOUCHER_CODE(Id,ammountOfUses,ammountOfBlocked,voucherId,voucherCode) VALUES (1,0,0,1,"code1"),(2,1,0,1,"code2"),(3,1,0,2,"code3"),(4,0,0,3,"code4");
/* to be delted */

delete from hibernate_sequence;

insert into hibernate_sequence(next_val) VALUES (100),(100),(100),(100),(100),(100),(100),(100),(100),(100);

insert into USER_COMPANY (Id, encodedPassword , isEnabled, userName, companyId) VALUES (1,'66b27417d37e024c46526c2f6d358a754fc552f3',1,'xyz@xyz.com',1);
insert into USER_COMPANY (Id, encodedPassword, isEnabled, userName, companyId) VALUES (2,'66b27417d37e024c46526c2f6d358a754fc552f3',0,'xyz2@xyz.com',1);

insert into APP_USER_USER_PROFILE (USER_ID, USER_PROFILE_ID) VALUE (1,1)