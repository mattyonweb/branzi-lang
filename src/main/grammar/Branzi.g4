grammar Branzi;

program
    : block EOF ;

block
    : '{' statement* '}';

statement
    : if_statement
    | while_statement
    | declare_statement
    | update_statement
    | expr_el ;

if_statement
    : 'if' '(' expr ')' block ;

while_statement
    : 'while' '(' expr ')' block ;

declare_statement
    : IDENTIFIER ':' type ':=' expr_el;

update_statement
    : lvalue '=' expr_el;

/////////////////7

expr_el
    : expr EL ;

expr
    : '('   Exp = expr   ')'                          # Parens
    | Left = expr Op = (TIMES | DIV)  Right = expr    # MulDiv
    | Left = expr Op = (PLUS  | MINUS) Right = expr   # AddSub
    | array_explicit                                  # ExprArrayExplicit
    | array_access                                    # ExprArrayAccess
    | boolean_expr                                    # ExprBool
    | IDENTIFIER                                      # ExprVar
    | NUMBER                                          # ExprNum
    ;

lvalue
    : array_access  # lvalueArrayAccess
    | IDENTIFIER    # lvalueSimpleVar
    ;

//////////////////////

array_access
    : IDENTIFIER '[' expr ']' ;

array_explicit
    : '[' ']' # emptyArray
    | '[' fst = expr (',' elements+=expr)* ']'  # nonEmptyArray
    ;
///////////////////////

TIMES : '*';
DIV : '/';
PLUS : '+';
MINUS : '-';
//////////////////////

T_INT : 'int';
T_BOOL : 'bool';
T_LIST : 'list';
T_ANY  : 'any';

type
    : '(' type ')'   # parensType
    | (T_INT|T_BOOL|T_ANY) # simpleType
    | T_LIST type    # arrayType
    ;

//////////////////////
B_TRUE : 'true';
B_FALSE : 'false';
B_AND : 'and' ;
B_OR  : 'or'  ;
B_NOT : 'not' ;
B_XOR : 'xor' ;

boolean_expr
    : B_TRUE  # boolTrue
    | B_FALSE # boolFalse
    | '(' boolean_expr ')' # boolParens
    | B_NOT boolean_expr   # boolNot
    | Left = boolean_expr Op = (B_AND|B_OR|B_XOR) Right = boolean_expr # boolBinop
    ;
///////////////////////////

IDENTIFIER : (LETTER (LETTER | DIGIT)*) ;

STRING : '"' (LETTER | DIGIT)+ '"';

NUMBER : (DIGIT)+ ;

WHITESPACE : [ \r\n\t] + -> channel (HIDDEN);

DIGIT : '0'..'9';

LETTER : LOWER | UPPER ;

LOWER : ('a'..'z') ;
UPPER : ('A'..'Z') ;

EL : ';';

