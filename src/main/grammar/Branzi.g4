grammar Branzi;

program
    : block EOF ;

block
    : '{' statement* '}';

statement
    : if_statement
    | while_statement
    | expr ;

if_statement
    : 'if' '(' expr ')' block ;

while_statement
    : 'while' '(' expr ')' block ;

/////////////////7

expr
    : IDENTIFIER '=' expr                             # exprAssign
    | IDENTIFIER ':' type '=' expr                    # declareNewVar
    | '('   Exp = expr   ')'                          # Parens
    | Left = expr Op = (TIMES | DIV)  Right = expr    # MulDiv
    | Left = expr Op = (PLUS  | MINUS) Right = expr   # AddSub
    | IDENTIFIER                                      # ExprVar
    | NUMBER                                          # ExprNum
    ;

TIMES : '*';
DIV : '/';
PLUS : '+';
MINUS : '-';
//////////////////////

T_INT : 'int';
T_BOOL : 'bool';
type : (T_INT|T_BOOL) ;

IDENTIFIER : (LETTER (LETTER | DIGIT)*) ;

STRING : '"' (LETTER | DIGIT)+ '"';

NUMBER : (DIGIT)+ ;

WHITESPACE : [ \r\n\t] + -> channel (HIDDEN);

DIGIT : '0'..'9';

LETTER : LOWER | UPPER ;

LOWER : ('a'..'z') ;
UPPER : ('A'..'Z') ;

