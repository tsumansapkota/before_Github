$SCREENHIDE
ON ERROR GOTO handler
BEGIN:
CLS
PRINT "Start"
PRINT TIME$
COMMON SHARED state
OPEN "routine.txt" FOR INPUT AS #1
dno = weekno + 1
IF dno = 7 THEN
    INPUT #1, da2$
    FOR m = 2 TO 7
        INPUT #1, da1$
    NEXT
ELSEIF dno <> 7 THEN
    FOR m = 1 TO dno
        INPUT #1, da1$
    NEXT
    INPUT #1, da2$

END IF
CLOSE #1

PRINT da1$, da2$

h1 = VAL(MID$(da1$, 5, 2))
m1 = VAL(MID$(da1$, 8, 2))
h2 = VAL(MID$(da1$, 11, 2))
m2 = VAL(MID$(da1$, 14, 2))
h3 = VAL(MID$(da1$, 17, 2))
m3 = VAL(MID$(da1$, 20, 2))
h4 = VAL(MID$(da1$, 23, 2))
m4 = VAL(MID$(da1$, 27, 2))
h5 = VAL(MID$(da2$, 5, 2))
m5 = VAL(MID$(da2$, 8, 2))

IF h1 > 12 THEN
    hoo1 = h1 - 12
    z1$ = "PM"
ELSE
    hoo1 = h1
    z1$ = "AM"
END IF
IF h2 > 12 THEN
    hoo2 = h2 - 12
    z2$ = "PM"
ELSE
    hoo2 = h2
    z2$ = "AM"
END IF
IF h3 > 12 THEN
    z3$ = "PM"
    hoo3 = h3 - 12
ELSE
    hoo3 = h3
    z3$ = "AM"
END IF
IF h4 > 12 THEN
    hoo4 = h4 - 12
    z4$ = "PM"
ELSE
    hoo4 = h4
    z4$ = "AM"
END IF

sch1$ = leng$(hoo1) + " : " + leng$(m1) + z1$ + " - " + leng$(hoo2) + " : " + leng$(m2) + z2$
sch2$ = leng$(hoo3) + " : " + leng$(m3) + z3$ + " - " + leng$(hoo4) + " : " + leng$(m4) + z4$


hour$ = LEFT$(TIME$, 2)
HH = VAL(hour$)
minute$ = MID$(TIME$, 4, 2)
MM = VAL(minute$)


'--------------------

IF (HH = h1 AND MM < m1) OR HH < h1 THEN
    PRINT "i m at 1"
    state = 1
    hour = h1
    minute = m1
    GOTO GT:
ELSEIF (HH = h2 AND MM < m2) OR HH < h2 THEN
    PRINT "i m at 2"
    state = 0
    hour = h2
    minute = m2
    GOTO GT:
ELSEIF (HH = h3 AND MM < m3) OR HH < h3 THEN
    PRINT "i m at 3"
    state = 1
    hour = h3
    minute = m3

    GOTO GT:
ELSEIF (HH = h4 AND MM < m4) OR HH < h4 THEN
    PRINT "i m at 4"
    state = 0
    hour = h4
    minute = m4
    GOTO GT:
ELSE

    h5 = 24 + h5
    m5 = m5
    PRINT "i m at 5"
    state = 1
    hour = h5
    minute = m5
    GOTO GT:
END IF

GT:
second$ = RIGHT$(TIME$, 2)
SS = VAL(second$)
sec = 60 - SS
_DELAY sec

second$ = RIGHT$(TIME$, 2)
SS = VAL(second$)

remain = tim(hour, minute)
IF LEN(STR$(remain)) = 6 THEN
    rh = VAL(LEFT$(STR$(remain), 3))
ELSE
    rh = VAL(LEFT$(STR$(remain), 2))
END IF
rm = VAL(RIGHT$(STR$(remain), 3))
IF remain < 100 THEN rh = 0
IF SS > 58 THEN
    GOTO BEGIN:
ELSE
    hour$ = leng$(rh)
    minu$ = leng$(rm)
    OPEN "remain.txt" FOR OUTPUT AS #1
    PRINT #1, "[Variables]"
    PRINT #1, "Rem=" + hour$ + ":" + minu$
    PRINT #1, "sch1=" + sch1$
    PRINT #1, "sch2=" + sch2$
    PRINT #1, "img=" + STR$(state) + ".png"
    CLOSE #1
    GOTO BEGIN:
END IF
'............................
handler:
RESUME NEXT
'............................



'<<------SUBS------>>
'--------------------------------------------------------
FUNCTION tim (ho, mi)
hour$ = LEFT$(TIME$, 2)
HH = VAL(hour$)
minute$ = MID$(TIME$, 4, 2)
MM = VAL(minute$)
IF (HH <= ho AND MM <= mi) THEN
    h = ho - HH
    m = mi - MM
ELSEIF (HH <= ho AND MM > mi) THEN
    h = ho - HH - 1
    m = mi + 60 - MM
ELSE
    h = 0
    m = 0
END IF
tim = h * 1000 + m
END FUNCTION
'--------------------------------------------------------
FUNCTION weekno ()
month$ = LEFT$(DATE$, 2): m = VAL(month$)
day$ = MID$(DATE$, 4, 2): d = VAL(day$)
day$ = STR$(d)
year$ = RIGHT$(DATE$, 4): Y = VAL(year$)
IF m < 3 THEN m = m + 12: Y = Y - 1
C = Y \ 100: Y = Y MOD 100
S1 = (C \ 4) - (2 * C) - 1
S2 = (5 * Y) \ 4
S3 = 26 * (m + 1) \ 10
WkDay = (S1 + S2 + S3 + d) MOD 7
IF WkDay < 0 THEN WkDay = WkDay + 7
weekno = WkDay
END FUNCTION
'--------------------------------------------------------
FUNCTION leng$ (tt)
IF tt < 10 THEN
    leng$ = "0" + RIGHT$(STR$(tt), 1)
ELSE
    leng$ = RIGHT$(STR$(tt), 2)
END IF
END FUNCTION


'--------------------------------------------------------
