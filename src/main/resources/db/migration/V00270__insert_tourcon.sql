CREATE SEQUENCE TOUR_CODE_SEQ
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9999999999
    START WITH 1
    NO CYCLE
;

INSERT INTO TOURCON VALUES (LPAD(nextval('TOUR_CODE_SEQ'), 10, 0), LPAD(nextval('TOUR_CON_CODE_SEQ'), 10, 0), 'エヌティティ　太郎', 'ntt1@example.com');
INSERT INTO TOURCON VALUES (LPAD(nextval('TOUR_CODE_SEQ'), 10, 0), LPAD(nextval('TOUR_CON_CODE_SEQ'), 10, 0), 'エヌティティ　太郎', 'ntt1@example.com');
INSERT INTO TOURCON VALUES (LPAD(nextval('TOUR_CODE_SEQ'), 10, 0), LPAD(nextval('TOUR_CON_CODE_SEQ'), 10, 0), 'エヌティティ　太郎', 'ntt1@example.com');
INSERT INTO TOURCON VALUES (LPAD(nextval('TOUR_CODE_SEQ'), 10, 0), LPAD(nextval('TOUR_CON_CODE_SEQ'), 10, 0), 'エヌティティ　太郎', 'ntt1@example.com');
INSERT INTO TOURCON VALUES (LPAD(nextval('TOUR_CODE_SEQ'), 10, 0), LPAD(nextval('TOUR_CON_CODE_SEQ'), 10, 0), 'エヌティティ　太郎', 'ntt1@example.com');
INSERT INTO TOURCON VALUES (LPAD(nextval('TOUR_CODE_SEQ'), 10, 0), LPAD(nextval('TOUR_CON_CODE_SEQ'), 10, 0), 'エヌティティ　太郎', 'ntt1@example.com');
INSERT INTO TOURCON VALUES (LPAD(nextval('TOUR_CODE_SEQ'), 10, 0), LPAD(nextval('TOUR_CON_CODE_SEQ'), 10, 0), 'エヌティティ　太郎', 'ntt1@example.com');
INSERT INTO TOURCON VALUES (LPAD(nextval('TOUR_CODE_SEQ'), 10, 0), LPAD(nextval('TOUR_CON_CODE_SEQ'), 10, 0), 'エヌティティ　太郎', 'ntt1@example.com');
INSERT INTO TOURCON VALUES (LPAD(nextval('TOUR_CODE_SEQ'), 10, 0), LPAD(nextval('TOUR_CON_CODE_SEQ'), 10, 0), 'エヌティティ　太郎', 'ntt1@example.com');
INSERT INTO TOURCON VALUES (LPAD(nextval('TOUR_CODE_SEQ'), 10, 0), LPAD(nextval('TOUR_CON_CODE_SEQ'), 10, 0), 'エヌティティ　太郎', 'ntt1@example.com');
INSERT INTO TOURCON VALUES (LPAD(nextval('TOUR_CODE_SEQ'), 10, 0), LPAD(nextval('TOUR_CON_CODE_SEQ'), 10, 0), 'エヌティティ　次郎', 'ntt2@example.com');
INSERT INTO TOURCON VALUES (LPAD(nextval('TOUR_CODE_SEQ'), 10, 0), LPAD(nextval('TOUR_CON_CODE_SEQ'), 10, 0), 'エヌティティ　次郎', 'ntt2@example.com');
INSERT INTO TOURCON VALUES (LPAD(nextval('TOUR_CODE_SEQ'), 10, 0), LPAD(nextval('TOUR_CON_CODE_SEQ'), 10, 0), 'エヌティティ　次郎', 'ntt2@example.com');
INSERT INTO TOURCON VALUES (LPAD(nextval('TOUR_CODE_SEQ'), 10, 0), LPAD(nextval('TOUR_CON_CODE_SEQ'), 10, 0), 'エヌティティ　次郎', 'ntt2@example.com');
INSERT INTO TOURCON VALUES (LPAD(nextval('TOUR_CODE_SEQ'), 10, 0), LPAD(nextval('TOUR_CON_CODE_SEQ'), 10, 0), 'エヌティティ　次郎', 'ntt2@example.com');
INSERT INTO TOURCON VALUES (LPAD(nextval('TOUR_CODE_SEQ'), 10, 0), LPAD(nextval('TOUR_CON_CODE_SEQ'), 10, 0), 'エヌティティ　次郎', 'ntt2@example.com');
INSERT INTO TOURCON VALUES (LPAD(nextval('TOUR_CODE_SEQ'), 10, 0), LPAD(nextval('TOUR_CON_CODE_SEQ'), 10, 0), 'エヌティティ　次郎', 'ntt2@example.com');
INSERT INTO TOURCON VALUES (LPAD(nextval('TOUR_CODE_SEQ'), 10, 0), LPAD(nextval('TOUR_CON_CODE_SEQ'), 10, 0), 'エヌティティ　次郎', 'ntt2@example.com');
INSERT INTO TOURCON VALUES (LPAD(nextval('TOUR_CODE_SEQ'), 10, 0), LPAD(nextval('TOUR_CON_CODE_SEQ'), 10, 0), 'エヌティティ　次郎', 'ntt2@example.com');
INSERT INTO TOURCON VALUES (LPAD(nextval('TOUR_CODE_SEQ'), 10, 0), LPAD(nextval('TOUR_CON_CODE_SEQ'), 10, 0), 'エヌティティ　次郎', 'ntt2@example.com');

COMMIT;

DROP SEQUENCE TOUR_CODE_SEQ;
