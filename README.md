# spring0527
-게시판 테이블-

BOARD_TYPE    NOT NULL VARCHAR2(5)    
BOARD_NUM     NOT NULL NUMBER(4)      
BOARD_TITLE            VARCHAR2(50)   
BOARD_COMMENT          VARCHAR2(1000) 
CREATOR                VARCHAR2(15)   
CREATE_TIME            VARCHAR2(20)   
MODIFIER               VARCHAR2(15)   
MODIFIED_TIME          VARCHAR2(20)   
FILE_ROOT              VARCHAR2(200)  
BOARD_HITS             VARCHAR2(200)  
BOARD_REF              NUMBER(4)      
BOARD_LEV              NUMBER(4)      
BOARD_STEP             NUMBER(4)      
FILE_SIZE              NUMBER(11) 

--코드테이블--

CODE_TYPE     NOT NULL VARCHAR2(10) 
CODE_ID       NOT NULL VARCHAR2(10) 
CODE_NAME              VARCHAR2(20) 
CREATOR                VARCHAR2(8)  
CREATE_TIME            VARCHAR2(14) 
MODIFIER               VARCHAR2(8)  
MODIFIED_TIME          VARCHAR2(14) 

--회원테이블--

USER_ID       NOT NULL VARCHAR2(15)  
USER_PW                VARCHAR2(16)  
USER_NAME              VARCHAR2(15)  
USER_PHONE1            VARCHAR2(3)   
USER_PHONE2            VARCHAR2(4)   
USER_PHONE3            VARCHAR2(4)   
USER_ADDR1             VARCHAR2(8)   
USER_ADDR2             VARCHAR2(150) 
USER_COMPANY           VARCHAR2(60)  
CREATOR                VARCHAR2(15)  
CREATE_TIME            VARCHAR2(14)  
MODIFIER               VARCHAR2(15)  
MODIFIED_TIME          VARCHAR2(14)  
SESSIONKEY    NOT NULL VARCHAR2(50)  
SESSIONLIMIT           TIMESTAMP(6)  


--파일 테이블--
FILE_ID        NOT NULL VARCHAR2(20)  
FILE_WRITER             VARCHAR2(20)  
FILE_NUM                NUMBER(11)    
FILE_GROUP              VARCHAR2(236) 
FILE_ORG_NAME           VARCHAR2(236) 
FILE_SAVE_NAME          VARCHAR2(236) 
FILE_TYPE               VARCHAR2(11)  
FILE_SIZE               NUMBER        
FILE_PATH               VARCHAR2(256) 
FILE_REG_DTTM           VARCHAR2(14)  
BOARD_NUM      NOT NULL NUMBER(11) 