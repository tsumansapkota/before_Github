DECLARE SUB add ()
DECLARE SUB display ()
DECLARE SUB SEARCH ()
DECLARE SUB dele ()
DECLARE SUB edit ()
DECLARE SUB quit ()


SCREEN 11, 0
FOR A = 1 TO 600 STEP 5
    LINE (1, 300)-(A, 300)
    LINE (1, 303)-(A, 303)
    FOR B = 1 TO 9990
    NEXT
    LOCATE 20, 5: PRINT "LOADING PLEASE WAIT !!!"
    LOCATE 24, 30: PRINT "|| DESIGNED NAMUS ATOKPAS ||"
    LOCATE 2, 5: PRINT "======  ||===  ||    ||===  ||===   ||  ||  =====  ||\  ||  ||==="
    LOCATE 3, 5: PRINT "  ||    ||___  ||    ||___  ||__||  ||__||  || ||  ||\\ ||  ||___"
    LOCATE 4, 5: PRINT "  ||    ||     ||    ||     ||---   ||  ||  || ||  || \\||  ||"
    LOCATE 5, 5: PRINT "  ||    ||===  ====  ||===  ||      ||  ||  =====  ||  \||  ||==="
    LOCATE 10, 5: PRINT "||==    ======     __     ||===   \\  //"
    LOCATE 11, 5: PRINT "||  \\    ||      //\\    ||__||   \\//"
    LOCATE 12, 5: PRINT "||  //    ||     //==\\   ||\\-     ||"
    LOCATE 13, 5: PRINT "||==    ======  //    \\  ||  \\    || "

NEXT
SLEEP
DO
LOOP UNTIL INKEY$ = ""



SCREEN 0
WHILE CH < 6
    CLS
    STRT:
    A = 1
    TOP:
    Z$ = UCASE$(INKEY$)
    IF Z$ = CHR$(13) THEN GOTO BACK:
    IF Z$ = CHR$(0) + CHR$(80) THEN
        A = A + 1
    ELSEIF Z$ = CHR$(0) + CHR$(72) THEN
        A = A - 1
    END IF
    IF A > 6 THEN A = 6
    IF SGN(A) = -1 OR SGN(A) = 0 THEN A = 1

    IF A = 1 THEN

        COLOR 2
        LOCATE 2, 30: PRINT "Main Menu"
        COLOR 3
        LOCATE 3, 25: PRINT "Telephone Directory"
        COLOR 4
        LOCATE 8, 8: PRINT SPACE$(4); "ADD NEW RECORD\S          "
        COLOR 14
        LOCATE 10, 8: PRINT "DELETE THE RECORD\S      "
        LOCATE 12, 8: PRINT "DISPLAY THE RECORD\S      "
        LOCATE 14, 8: PRINT "EDIT THE RECORDS\S      "
        LOCATE 16, 8: PRINT "SEARCH                  "
        LOCATE 18, 8: PRINT "EXIT                    "


    ELSEIF A = 2 THEN
        COLOR 2
        LOCATE 2, 30: PRINT "Main Menu"
        COLOR 3
        LOCATE 3, 25: PRINT "Telephone Directory"
        COLOR 14
        LOCATE 8, 8: PRINT "ADD NEW RECORD\S          "
        COLOR 4
        LOCATE 10, 8: PRINT SPACE$(4); "DELETE THE RECORD\S      "
        COLOR 14
        LOCATE 12, 8: PRINT "DISPLAY THE RECORD\S      "
        LOCATE 14, 8: PRINT "EDIT THE RECORDS\S      "
        LOCATE 16, 8: PRINT "SEARCH                  "
        LOCATE 18, 8: PRINT "EXIT                    "


    ELSEIF A = 3 THEN
        COLOR 2
        LOCATE 2, 30: PRINT "Main Menu"
        COLOR 3
        LOCATE 3, 25: PRINT "Telephone Directory"
        COLOR 14
        LOCATE 8, 8: PRINT "ADD NEW RECORD\S          "
        LOCATE 10, 8: PRINT "DELETE THE RECORD\S      "
        COLOR 4
        LOCATE 12, 8: PRINT SPACE$(4); "DISPLAY THE RECORD\S      "
        COLOR 14
        LOCATE 14, 8: PRINT "EDIT THE RECORDS\S      "
        LOCATE 16, 8: PRINT "SEARCH                  "
        LOCATE 18, 8: PRINT "EXIT                    "


    ELSEIF A = 4 THEN
        COLOR 2
        LOCATE 2, 30: PRINT "Main Menu"
        COLOR 3
        LOCATE 3, 25: PRINT "Telephone Directory"
        COLOR 14
        LOCATE 8, 8: PRINT "ADD NEW RECORD\S          "
        LOCATE 10, 8: PRINT "DELETE THE RECORD\S      "
        LOCATE 12, 8: PRINT "DISPLAY THE RECORD\S      "
        COLOR 4
        LOCATE 14, 8: PRINT SPACE$(4); "EDIT THE RECORDS\S      "
        COLOR 14
        LOCATE 16, 8: PRINT "SEARCH                  "
        LOCATE 18, 8: PRINT "EXIT                    "


    ELSEIF A = 5 THEN
        COLOR 2
        LOCATE 2, 30: PRINT "Main Menu"
        COLOR 3
        LOCATE 3, 25: PRINT "Telephone Directory"
        COLOR 14
        LOCATE 8, 8: PRINT "ADD NEW RECORD\S          "
        LOCATE 10, 8: PRINT "DELETE THE RECORD\S      "
        LOCATE 12, 8: PRINT "DISPLAY THE RECORD\S      "
        LOCATE 14, 8: PRINT "EDIT THE RECORDS\S      "
        COLOR 4
        LOCATE 16, 8: PRINT SPACE$(4); "SEARCH                  "
        COLOR 14
        LOCATE 18, 8: PRINT "EXIT                    "


    ELSEIF A = 6 THEN
        COLOR 2
        LOCATE 2, 30: PRINT "Main Menu"
        COLOR 3
        LOCATE 3, 25: PRINT "Telephone Directory"
        COLOR 14
        LOCATE 8, 8: PRINT "ADD NEW RECORD\S          "
        LOCATE 10, 8: PRINT "DELETE THE RECORD\S      "
        LOCATE 12, 8: PRINT "DISPLAY THE RECORD\S      "
        LOCATE 14, 8: PRINT "EDIT THE RECORDS\S      "
        LOCATE 16, 8: PRINT "SEARCH                  "
        COLOR 4
        LOCATE 18, 8: PRINT SPACE$(4); "EXIT                    "
    END IF
    COLOR 26
    LOCATE 22, 10: PRINT "SELECT THE CHOICE USING "; CHR$(24); " "; CHR$(25); " AND PRESS "; CHR$(27); "-| TO CHOOSE IT"
    COLOR 2
    LOCATE 1, 1: PRINT STRING$(80, 24)
    LOCATE 23, 1: PRINT STRING$(80, 25)
    LOCATE 6, 6: PRINT STRING$(30, 16)
    LOCATE 20, 6: PRINT STRING$(30, 17)
    FOR V = 7 TO 19
        LOCATE V, 6: PRINT CHR$(24)
        LOCATE V, 35: PRINT CHR$(25)
    NEXT
    GOTO TOP:
    BACK:
    CH = A
    SELECT CASE CH
        CASE 1
            CALL add
        CASE 2
            CALL dele
        CASE 3
            CALL display
        CASE 4
            CALL edit
        CASE 5
            CALL SEARCH
        CASE 6
            CALL quit
    END SELECT
WEND
END

SUB add
    OPEN "suman.dat" FOR APPEND AS #1
    CLS
    A:
    COLOR 2
    CLS
    INPUT "ENTER THE NAME"; n$
    INPUT "ENTER THE ADDRESS "; ad$
    INPUT "ENTER THE CONTACT NUMBER"; p$
    WRITE #1, n$, ad$, p$
    PRINT "DO YOU WANT TO ADD OTHER RECORDS(Y/N)"
    B:
    IF UCASE$(INPUT$(1)) = "Y" THEN
        GOTO A
    ELSEIF UCASE$(INPUT$(1)) = "N" THEN
        GOTO C
    ELSE
        GOTO B
    END IF
    C:
    CLOSE #1
END SUB

SUB dele
    CLS
    COLOR 10
    INPUT "Enter the name to delete"; m$
    OPEN "suman.dat" FOR INPUT AS #1
    OPEN "sapkota.dat" FOR OUTPUT AS #2
    WHILE NOT EOF(1)
        INPUT #1, n$, ad$, p$
        IF UCASE$(n$) = UCASE$(m$) THEN
            PRINT "RECORD DELETED"
            tem = 1
        ELSE
            WRITE #2, n$, ad$, p$
        END IF
    WEND
    IF tem = 0 THEN
        PRINT "RECORD NOT FOUND"
    END IF
    CLOSE #1
    CLOSE #2
    KILL "suman.dat"
    NAME "sapkota.dat" AS "suman.dat"
    A$ = INPUT$(1)
END SUB

SUB display
    CLS
    COLOR 11
    PRINT "|NAME|", "|ADDRESS|", "|CONTACT NO.|"
    PRINT
    COLOR 1
    OPEN "suman.dat" FOR INPUT AS #1
    WHILE NOT EOF(1)
        INPUT #1, n$, ad$, p$
        PRINT "|"; n$; "|", "|"; ad$; "|", "|"; p$; "|"
    WEND
    A$ = INPUT$(1)
    CLOSE #1
END SUB

SUB edit
    CLS
    COLOR 9
    INPUT "ENTER THE NAME TO EDIT"; m$
    OPEN "suman.dat" FOR INPUT AS #1
    OPEN "namus.dat" FOR OUTPUT AS #2
    tem = 0
    WHILE NOT EOF(1)
        INPUT #1, n$, ad$, p$
        IF (m$ <> n$) THEN
            WRITE #2, n$, ad$, p$
        ELSE
            PRINT n$, ad$, p$
            tem = 1
            PRINT
            PRINT "IS THIS THE RECORD YOU WANT TO EDIT(Y/N)"
            H:
            IF UCASE$(INPUT$(1)) = "Y" THEN
                INPUT "ENTER THE NEW NAME"; n$
                INPUT "ENTER THE NEW ADDRESS"; ad$
                INPUT "ENTER THE NEW PHONE NUMBER"; p$
                WRITE #2, n$, ad$, p$
                PRINT "RECORD EDITED"
            ELSEIF UCASE$(INPUT$(1)) = "N" THEN
                WRITE #2, n$, ad$, p$
            ELSE GOTO H
            END IF
        END IF
    WEND
    IF tem = 0 THEN
        PRINT "RECORD NOT FOUND"
    END IF
    CLOSE #1, #2
    KILL "suman.dat"
    NAME "namus.dat " AS "suman.dat"
END SUB

SUB quit
    CLS
    COLOR 12
    LOCATE 10, 25: PRINT "THANK YOU FOR USING THE PROGRAM"
    LOCATE 12, 26: PRINT "PROGRAMMED BY SUMAN SAPKOTA"
END SUB

SUB SEARCH
    tem = 0
    OPEN "suman.dat" FOR INPUT AS #1
    CLS
    COLOR 13
    INPUT "ENTER THE NAME TO SEARCH"; m$
    CLS
    WHILE NOT EOF(1)
        INPUT #1, n$, ad$, p$
        IF UCASE$(m$) = UCASE$(n$) THEN
            PRINT "|NAME|", "|ADDRESS|", "|CONTACT NO.|"
            PRINT n$, ad$, p$
            tem = 1
        END IF
    WEND
    IF tem = 0 THEN
        PRINT "RECORD NOT FOUND"
    END IF
    CLOSE #1
    A$ = INPUT$(1)
END SUB
q
